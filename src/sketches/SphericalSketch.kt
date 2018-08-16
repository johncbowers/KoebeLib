package sketches

/**
 * Created by johnbowers on 5/20/17.
 */


import com.processinghacks.arcball.Arcball
import geometry.algorithms.IncrementalConvexHullAlgorithms
import processing.core.PApplet

import geometry.primitives.Euclidean2.*
import geometry.primitives.Euclidean3.*
import geometry.primitives.Spherical2.*
import geometry.primitives.OrientedProjective3.*
import geometry.construction.Construction
import geometry.algorithms.incrConvexHull
import geometry.algorithms.orientationPointOP3
import geometry.algorithms.addPoint
import geometry.ds.dcel.*
import geometry.primitives.OrientedProjective2.*
import geometry.primitives.isZero

import gui.JythonFrame
import processing.core.PConstants
import processing.event.MouseEvent
import processing.opengl.PGraphicsOpenGL

import javax.swing.*

class Color(val x: Float, val y: Float, val z: Float, val a: Float = 255.0f) {
    companion object {
        val noColor = Color(-1.0f, -1.0f, -1.0f, -1.0f)
    }
    override fun equals(c: Any?) = (c is Color && c.x == x && c.y == y && c.z == z && c.a == a)
}

class Style(
        val stroke: Color?,
        val fill: Color?
) {
    fun set(p: PApplet) {
        if (stroke != null && stroke != Color.noColor) p.stroke(stroke.x, stroke.y, stroke.z, stroke.a)
        else if (stroke == Color.noColor) p.noStroke()

        if (fill != null && fill != Color.noColor) p.fill(fill.x, fill.y, fill.z, fill.a)
        else if (fill == Color.noColor) p.noFill()
    }

    var drawVertices = true
}

open class SphericalSketch : PApplet() {

    internal var arcball: Arcball? = null
    internal var applyToCamera = true

    var construction = Construction()
    val objects = mutableListOf<Any>()
    val objectStyles = mutableMapOf<Any, Style>()

    val constructionLock = Any()

    val viewSettings = SphericalSketchViewSettings()

    var zoom = 1.0f

    class SphericalSketchViewSettings() {
        var showBoundingBox = true
        var showCircleCentersAndNormals = true
        var showSphere = true
        var showDualPoint = true
        var showEuclideanDisks = false
    }

    internal val jyFrame = JythonFrame(this)

    /**
     * Sets up the sketches.main graphics settings for the PApplet window
     */
    override fun settings() {
        size(600, 600, P3D)
        smooth(4)
    }

    /**
     * Sets up the drawing canvas.
     */
    override fun setup() {

        background(255)

        arcball = Arcball(this)

        jyFrame.setSize(800, 600)
        jyFrame.setVisible(true)
        jyFrame.setup()

        this.frame.setResizable(true)
    }

    fun drawPointE2(p: PointE2) {

        pushMatrix()
        translate(p.x.toFloat(), p.y.toFloat(), 1.0f)
        sphere(0.035f)
        popMatrix()
    }


    fun drawPointE3(p: PointE3) {

        pushMatrix()
        translate(p.x.toFloat(), p.y.toFloat(), p.z.toFloat())
        sphere(0.035f)
        popMatrix()
    }

    fun drawPointOP2(p: PointOP2) {

        pushMatrix()
        translate(p.hx.toFloat()/p.hw.toFloat(), p.hy.toFloat()/p.hw.toFloat(), 1.0f)
        sphere(0.015f)
        popMatrix()
    }

    fun drawCircleE2(disk: DiskE2) {

        pushMatrix()
        translate(0.0f, 0.0f, 1.0f)

        /*
        //find bisectors of p1, p2 and p2, p3
        val midPt12 = PointE2( (disk.p1.x + disk.p2.x)/2.0, (disk.p1.y + disk.p2.y) / 2.0 )
        val midPt23 = PointE2( (disk.p2.x + disk.p3.x)/2.0, (disk.p2.y + disk.p3.y) / 2.0 )
        val slope12 = -1.0 / ( (disk.p2.y - disk.p1.y) / (disk.p2.x- disk.p1.x) )
       // val slope23 = -1.0 / ( (disk.p3.y - disk.p2.y) / (disk.p3.x- disk.p2.x) )
        val slope23 = 100000

        val centX = (slope12 * midPt12.x - slope23 * midPt23.x + midPt23.y - midPt12.y) / (slope12 - slope23)
        val centY =  slope12 * (centX - midPt12.x) + midPt12.y
        val center = PointE2(centX, centY)
        val rad = center.distTo(disk.p1)

        ellipse( center.x.toFloat(), center.y.toFloat(), (2.0*rad).toFloat(), (2.0*rad).toFloat())
        popMatrix()
        */

        //find bisectors of p1, p2 and p2, p3
//        val bis12 = disk.p1.bisector(disk.p2)
//        val bis23 = disk.p2.bisector(disk.p3)
//
//        // find intersection of two bisector lines to find circle center
//        val center = bis12.intersection(bis23)
//
//        // compute radius of circle
//        val rad = center.distTo(disk.p1)
//
//        ellipse( center.hx.toFloat(), center.hy.toFloat(), (2.0*rad).toFloat(), (2.0*rad).toFloat())
        popMatrix()
    }

    fun drawLineOP2(line: LineOP2) {

        pushMatrix()
        translate(0.0f, 0.0f, 1.0f)
        noLights()
        strokeWeight(0.01f)
        noFill()

        // Draw four bounding box lines
        line(-2.0f, 2.0f, 2.0f, 2.0f)   // y = 2
        line(-2.0f, 2.0f, -2.0f, -2.0f) // x = -2
        line(-2.0f, -2.0f, 2.0f, -2.0f) // y = -2
        line(2.0f,-2.0f, 2.0f, 2.0f)    // x = 2

        val boundLine1 = LineOP2(0.0, 1.0, -2.0) // y = 2
        val boundLine2 = LineOP2(1.0, 0.0, 2.0)  // x = -2
        val boundLine3 = LineOP2(0.0, 1.0, 2.0)   // y = -2
        val boundLine4 = LineOP2(1.0, 0.0, -2.0)  // x = 2


        // Get 2-4 intersection points between line and bound lines (lines 2 & 4)
        var intPts = mutableListOf<PointOP2>()
        var srcPt: PointOP2
        var trgPt: PointOP2

        // If line is horizontal line, only intersect with two vertical lines (lines 1 & 3)
        if ( line.a == 0.0 ) {
            srcPt = line.intersection(boundLine2)
            trgPt = line.intersection(boundLine4)
//            endPts.addAll(intPts)
        }

        // If line is a vertical line, only intersect with two horizontal lines
        else if (line.b == 0.0) {
            srcPt = line.intersection(boundLine1)
            trgPt= line.intersection(boundLine3)
        }
        else { // If line is not a vertical or horizontal lines
            val intPt1 =  line.intersection(boundLine1)   //p1
            val intPt2 = line.intersection(boundLine2)    //p2
            val intPt3 = line.intersection(boundLine3)    //p3
            val intPt4 = line.intersection(boundLine4)    //p4

            // Find which two are on the boundary box
            /*for (pt in intPts) {
                if ((isZero(2 - pt.hx / pt.hw) || isZero(-2 - pt.hx / pt.hw)) && Math.abs(pt.hy / pt.hw) <= 2.0)
                    endPts.add(pt)
                else if ((isZero(2 - pt.hy / pt.hw) || isZero(-2 - pt.hy / pt.hw)) && Math.abs(pt.hx / pt.hw) <= 2.0)
                    endPts.add(pt)
            }*/

            // if sign(line.a)!= sign(line.b)
            if ( line.a * line.b < 0 ) {
                srcPt =
                        if( intPt1.hx * intPt4.hw < intPt4.hx * intPt1.hw)    intPt1
                        else intPt4
                trgPt =
                        if( intPt2.hx * intPt3.hw > intPt3.hx  * intPt2.hw)   intPt2
                        else intPt3
            }
            else {
                srcPt =
                        if( intPt3.hx * intPt4.hw < intPt4.hx *intPt3.hw) intPt3
                        else intPt4
                trgPt =
                        if( intPt1.hx * intPt2.hw > intPt2.hx  * intPt1.hw) intPt1
                        else intPt2
            }

        }

        val srcX = srcPt.hx/srcPt.hw
        val srcY = srcPt.hy/srcPt.hw
        val trgX = trgPt.hx/trgPt.hw
        val trgY = trgPt.hy/trgPt.hw

        line(srcX.toFloat(),srcY.toFloat(), trgX.toFloat(), trgY.toFloat())
        popMatrix()

        // Draw endpoints of line
        drawPointOP2(srcPt)
        drawPointOP2(trgPt)

    }

    /*
    fun drawLineOP3(line: LineOP3) {

        pushMatrix()
        translate(0.0f, 0.0f, 1.0f)
        noLights()
        strokeWeight(0.01f)
        noFill()

        // Get six intersection points between line and bound lines
          var intPts = mutableListOf<PointOP3>()
//        intPts.add(line.intersection(boundLine1))
//        intPts.add(line.intersection(boundLine2))
//        intPts.add(line.intersection(boundLine3))
//        intPts.add(line.intersection(boundLine4))


        // Find which two are on the boundary box
        var endPts = mutableListOf<PointOP3>()
        for (pt in intPts) {
            if ( (isZero(2 - pt.hx/pt.hw) || isZero(-2 - pt.hx/pt.hw))
                    && Math.abs(pt.hy/pt.hw) <= 2.0
                    && Math.abs(pt.hz/pt.hw) <= 2.0 )
                endPts.add(pt)

            else if ( (isZero(2 - pt.hy/pt.hw) || isZero(-2 - pt.hy/pt.hw))
                    && Math.abs(pt.hx/pt.hw) <= 2.0
                    && Math.abs(pt.hz/pt.hw) <= 2.0)

            else if ( (isZero(2 - pt.hz/pt.hw) || isZero(-2 - pt.hz/pt.hw))
                    && Math.abs(pt.hx/pt.hw) <= 2.0
                    && Math.abs(pt.hy/pt.hw) <= 2.0)

                endPts.add(pt)
        }

        val srcX = endPts[0].hx/endPts[0].hw
        val srcY = endPts[0].hy/endPts[0].hw
        val srcZ = endPts[0].hz/endPts[0].hw
        val trgX = endPts[1].hx/endPts[1].hw
        val trgY = endPts[1].hy/endPts[1].hw
        val trgZ = endPts[1].hz/endPts[1].hw

        // translate in the z direction then draw a 2D line?
        line(srcX.toFloat(),srcY.toFloat(), trgX.toFloat(), trgY.toFloat())

        popMatrix()

    } */

    fun drawDiskOP2(disk: DiskOP2) {
        pushMatrix()
        translate(0.0f, 0.0f, 1.0f)
        val diameter = disk.radius.toFloat() * 2.0f
        ellipse(disk.center.x.toFloat(),
                disk.center.y.toFloat(),
                diameter, diameter)
        popMatrix()
    }

    fun drawDiskE2(disk: DiskE2) {
        pushMatrix()
        translate(0.0f, 0.0f, 1.0f)
        val diameter = disk.radius.toFloat() * 2.0f
        ellipse(disk.center.x.toFloat(),
                disk.center.y.toFloat(),
                diameter, diameter)
        popMatrix()
    }

    fun drawSegmentE2(seg: SegmentE2) {
        pushMatrix()
        translate(0.0f, 0.0f, 1.0f)
        line(seg.source.x.toFloat(), seg.source.y.toFloat(), seg.target.x.toFloat(), seg.target.y.toFloat())
        popMatrix()
    }

    fun drawCircleArcOP2(arc: CircleArcOP2) {

        pushMatrix()

        translate(0.0f, 0.0f, 1.0f)

        pushStyle()
        noLights()
        strokeWeight(0.01f)
        noFill()

        val diameter = arc.radius.toFloat() * 2.0f

        val src = if (arc.disk.a >= 0.0) arc.source else arc.target
        val trg = if (arc.disk.a >= 0.0) arc.target else arc.source

        val srcX = src.hx.toFloat() / src.hw.toFloat()
        val srcY = src.hy.toFloat() / src.hw.toFloat()
        val trgX = trg.hx.toFloat() / trg.hw.toFloat()
        val trgY = trg.hy.toFloat() / trg.hw.toFloat()

        // If it is close to straight, let's just draw the line segment
        val ratio = Math.abs(Math.sqrt(src.distTo(trg)) / arc.radius)
        if (Math.abs(ratio) < 0.1) {
            line(srcX, srcY, trgX, trgY)
        }
        // otherwise, we'll draw a circular arc
        else {

            val radInv = 1.0 / arc.radius

            val srcV = VectorE2((srcX - arc.disk.center.x) * radInv, (srcY - arc.disk.center.y) * radInv)
            val trgV = VectorE2((trgX - arc.disk.center.x) * radInv, (trgY - arc.disk.center.y) * radInv)

            val srcAngle = srcV.angleFromXAxis.toFloat()
            var targetAngle = trgV.angleFromXAxis.toFloat()

            if (srcAngle > targetAngle) targetAngle += TWO_PI

            arc((arc.disk.center.x).toFloat(), (arc.disk.center.y).toFloat(), diameter, diameter, srcAngle, targetAngle)
        }
        popStyle()
        popMatrix()
    }

    fun drawCircleS2(disk: DiskS2) {

        pushMatrix()

        // Get the basis vectors for the disk
        val b1 = disk.normedBasis1
        val b2 = disk.normedBasis2
        val b3 = disk.normedBasis3

        // Compute the distance from the origin to the disk's Euclidean center and its euclidean radius
        val centerDist = disk.centerE3.distTo(PointE3.O).toFloat()
        val diameter = disk.radiusE3.toFloat() * 2.0f

        // Rotate the e1, e2, e3 basis vectors to b1, b2, b3
        applyMatrix(
                b1.v.x.toFloat(), b2.v.x.toFloat(), b3.v.x.toFloat(), 0.0f,
                b1.v.y.toFloat(), b2.v.y.toFloat(), b3.v.y.toFloat(), 0.0f,
                b1.v.z.toFloat(), b2.v.z.toFloat(), b3.v.z.toFloat(), 0.0f,
                0.0f,             0.0f,             0.0f,             1.0f
        )

        // Translate the drawing frame up to the distance from the disk center
        // Here we need to check the orientation
        if (disk.d < 0)
            translate(0.0f, 0.0f, centerDist)
        else
            translate(0.0f, 0.0f, -centerDist)

        // Draw the disk
        pushStyle()
        noLights()
        strokeWeight(0.01f / zoom)
        //stroke(0.0f, 0.0f, 0.0f)
        if (viewSettings.showEuclideanDisks)
            fill(0)
        else
            noFill()

        ellipse(0.0f, 0.0f, diameter, diameter)

        popStyle()

        popMatrix()

        // Draw Euclidean Center
        if (viewSettings.showCircleCentersAndNormals) {
            val ctr = disk.centerE3
            pushMatrix()
            pushStyle()

            noStroke()
            lights()

            pushMatrix()
            fill(255.0f, 0.0f, 0.0f)
            translate(ctr.x.toFloat(), ctr.y.toFloat(), ctr.z.toFloat())
            sphere(0.035f)
            popMatrix()

            pushMatrix()
            fill(0.0f, 255.0f, 0.0f)
            val dir = disk.directionE3//DirectionE3(VectorE3(disk.a, disk.b, disk.c))
            translate(dir.v.x.toFloat(), dir.v.y.toFloat(), dir.v.z.toFloat())
            sphere(0.035f)
            popMatrix()

            popStyle()
            popMatrix()
        }

        if (viewSettings.showDualPoint) {
            val dual = disk.dualPointOP3.toPointE3()

            pushMatrix()
            pushStyle()

            noStroke()
            lights()

            fill(0.0f, 255.0f, 255.0f)
            translate(dual.x.toFloat(), dual.y.toFloat(), dual.z.toFloat())
            sphere(0.035f)

            popStyle()
            popMatrix()
        }

    }

    fun drawCircleArcS2(arc: CircleArcS2) {

        pushMatrix()

        // Get the basis vectors for the disk
        val b1 = arc.normedBasis1
        val b2 = arc.normedBasis2
        val b3 = arc.normedBasis3

        // Compute the distance from the origin to the disk's Euclidean center and its euclidean radius
        val centerDist = arc.centerE3.distTo(PointE3.O).toFloat()
        val diameter = arc.radiusE3.toFloat() * 2.0f

        // Rotate the e1, e2, e3 basis vectors to b1, b2, b3
        applyMatrix(
                b1.v.x.toFloat(), b2.v.x.toFloat(), b3.v.x.toFloat(), 0.0f,
                b1.v.y.toFloat(), b2.v.y.toFloat(), b3.v.y.toFloat(), 0.0f,
                b1.v.z.toFloat(), b2.v.z.toFloat(), b3.v.z.toFloat(), 0.0f,
                0.0f,             0.0f,             0.0f,             1.0f
        )

        // Translate the drawing frame up to the distance from the disk center
        // Here we need to check the orientation
        if (arc.disk.d < 0)
            translate(0.0f, 0.0f, centerDist)
        else
            translate(0.0f, 0.0f, -centerDist)

        // Draw the disk
        pushStyle()
        noLights()
        strokeWeight(0.01f / zoom)
        //stroke(0.0f, 0.0f, 0.0f)
        if (viewSettings.showEuclideanDisks)
            fill(0)
        else
            noFill()

        val srcVec = DirectionE3(arc.source.directionE3.endPoint - arc.disk.centerE3).v
        val srcAngle = if (b2.v.dot(srcVec) >= 0) acos(b1.v.dot(srcVec).toFloat()) else TWO_PI - acos(b1.v.dot(srcVec).toFloat())

        val targetVec = DirectionE3(arc.target.directionE3.endPoint - arc.disk.centerE3).v
        var targetAngle = if (b2.v.dot(targetVec) >= 0) acos(b1.v.dot(targetVec).toFloat()) else TWO_PI - acos(b1.v.dot(targetVec).toFloat())

        if (srcAngle > targetAngle) targetAngle += TWO_PI

        arc(0.0f, 0.0f, diameter, diameter, srcAngle, targetAngle)

        popStyle()

        popMatrix()

        // Draw Euclidean Center
        if (viewSettings.showCircleCentersAndNormals) {
            val ctr = arc.centerE3
            pushMatrix()
            pushStyle()

            noStroke()
            lights()

            pushMatrix()
            fill(255.0f, 0.0f, 0.0f)
            translate(ctr.x.toFloat(), ctr.y.toFloat(), ctr.z.toFloat())
            sphere(0.035f)
            popMatrix()

            pushMatrix()
            fill(0.0f, 255.0f, 0.0f)
            val dir = arc.disk.directionE3//DirectionE3(VectorE3(disk.a, disk.b, disk.c))
            translate(dir.v.x.toFloat(), dir.v.y.toFloat(), dir.v.z.toFloat())
            sphere(0.035f)
            popMatrix()

            popStyle()
            popMatrix()
        }

        if (viewSettings.showDualPoint) {
            val dual = arc.disk.dualPointOP3.toPointE3()

            pushMatrix()
            pushStyle()

            noStroke()
            lights()

            fill(0.0f, 255.0f, 255.0f)
            translate(dual.x.toFloat(), dual.y.toFloat(), dual.z.toFloat())
            sphere(0.035f)

            popStyle()
            popMatrix()
        }

    }

    fun drawDCEL(ch: DCEL<*,*,*>) {

//        println("DRAWING DCEL")
        pushStyle()
        strokeWeight(0.025f)
        ch.faces.forEach {
            face ->
//            println("\tFACE")
            beginShape()
            face.darts().forEach {
                dart ->
                val p = dart.origin?.data
                when (p) {
                    is PointOP3 -> {
                        val ptE3 = p?.toPointE3()
                        if (ptE3 !== null) {
                            vertex(ptE3.x.toFloat(), ptE3.y.toFloat(), ptE3.z.toFloat())
//                            println("\t\t${ptE3.x} ${ptE3.y} ${ptE3.z}")
                        }
                    }
                    is PointE3 -> {
                        vertex(p.x.toFloat(), p.y.toFloat(), p.z.toFloat())
                    }
                    is DiskS2 -> {
                        val ptE3 = p?.dualPointOP3?.toPointE3()
                        if (ptE3 !== null) {
                            vertex(ptE3.x.toFloat(), ptE3.y.toFloat(), ptE3.z.toFloat())
                        }
                    }
                    else -> {}
                }
            }
            endShape()
//            println("\tENDFACE")
        }
        popStyle()
//        println("DONE")
    }

    fun drawCoaxialFamilyS2(cf: CoaxialFamilyS2) {

    }

    fun drawHyperbolicCoaxialFamilyS2(cf: CoaxialFamilyS2) {

    }

    fun drawParabolicCoaxialFamilyS2(cf: CoaxialFamilyS2) {

    }

    fun drawEllipticCoaxialFamilyS2(cf: CoaxialFamilyS2) {

    }
    /**
     * The sketches.main drawing code, called in an infinite loop to continuously redraw the screen.
     */
    override fun draw() {
        update()

        background(255)

        beginCamera()
        camera()
        if (applyToCamera && arcball != null) {
            translate((width / 2).toFloat(), (height / 2).toFloat())
            val axis = (arcball as Arcball).axis
            rotate((arcball as Arcball).angle, axis.x, axis.y, -axis.z)
            translate((-width / 2).toFloat(), (-height / 2).toFloat())
        }
        endCamera()

        translate(width / 2.0f, height / 2.0f, 0.0f)
        scale(1.0f, -1.0f, 1.0f);
        scale(200.0f * zoom)
        strokeWeight(0.005f / zoom)

        pushStyle()

        noStroke()
        lights()

        if (viewSettings.showSphere) {
            sphereDetail(200)
            sphere(1.0f)
            sphereDetail(30)
        }

        // Draw the points
        synchronized(constructionLock, {
            listOf<MutableList<Any>>(
                    construction.getGeometricObjects(),
                    objects
            ).forEach {
                list ->
                list.forEach {
                    val style = objectStyles[it]
                    when (it) {
                        is PointS2 -> {
                            if (style != null) style.set(this)
                            else {
                                noStroke()
                                fill(100.0f, 125.0f, 255.0f)
                            }
                            drawPointE3(it.directionE3.endPoint)
                        }
                        is PointE2 -> {
                            if (style != null) style.set(this)
                            else {
                                noStroke()
                                fill(100.0f, 125.0f, 255.0f)
                            }
                            drawPointE2(it)
                        }
                        is DiskOP2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0.0f, 0.0f, 0.0f)
                                noFill()
                            }
                            drawDiskOP2(it)
                        }
                        is DiskE2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0.0f, 0.0f, 0.0f)
                                noFill()
                            }
                            drawDiskE2(it)
                        }
                        is SegmentE2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0.0f, 0.0f, 255.0f)
                                noFill()
                            }
                            drawSegmentE2(it)
                        }
                        is LineOP2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0.0f, 0.0f, 0.0f)
                                noFill()
                            }
                            drawLineOP2(it)
                        }
                        is PointE3 -> {
                            if (style != null) style.set(this)
                            else {
                                noStroke()
                                fill(100.0f, 125.0f, 255.0f)
                            }
                            drawPointE3(it)
                        }
                        is PointOP2 -> {
                            if (style != null) style.set(this)
                            else {
                                noStroke()
                                fill(100.0f, 125.0f, 255.0f)
                            }
                            drawPointOP2(it)
                        }

                        is PointOP3 -> {
                            if (style != null) style.set(this)
                            else {
                                noStroke()
                                fill(100.0f, 125.0f, 255.0f)
                            }
                            if (!it.isIdeal()) drawPointE3(it.toPointE3())
                        }
                        is DiskS2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0)
                            }
                            drawCircleS2(it)
                        }
                        is DiskE2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0)
                            }
                            drawCircleE2(it)
                        }
                        is CircleArcOP2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0.0f, 0.0f, 255.0f)
                            }
                            drawCircleArcOP2(it)
                            if (style == null) {
                                noStroke()
                                fill(255.0f, 0.0f, 125.0f)
                                drawPointOP2(it.source)
                                drawPointOP2(it.target)
                            }
                        }
                        is CircleArcS2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0.0f, 0.0f, 255.0f)
                            }
                            drawCircleArcS2(it)
                            if (style == null) {
                                noStroke()
                                fill(255.0f, 0.0f, 125.0f)
                                drawPointE3(it.source.directionE3.endPoint)
                                drawPointE3(it.target.directionE3.endPoint)
                            }
                        }
                        is CPlaneS2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(255.0f, 0.0f, 0.0f)
                            }
                            drawCircleS2(it.dualDiskS2)
                        }
                        is DCEL<*,*,*> -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0)
                                fill(255.0f, 255.0f, 255.0f)
                            }
                            drawDCEL(it)
                        }
                        else -> {}
                    }
                }
            }
        })



        if (viewSettings.showBoundingBox) {
            noFill()
            stroke(0)
            box(2.0f)
        }

        popStyle()
    }

    fun update() {

    }

    /*** Mouse Handling Code ***/

    override fun mousePressed() {
        arcball?.mousePressed()
    }

    override fun mouseReleased() {
        arcball?.mouseReleased()
    }

    override fun mouseDragged() {
        arcball?.mouseDragged()
    }

    override fun mouseWheel(event: MouseEvent?) {
        if (event != null) {
            //val delta = if (event.count > 0) 1.01f else if (event.count < 0) 1.0f/1.01f else 1.0f
            val delta = if (event.count > 0)
                Math.pow(1.0005, event.count.toDouble()).toFloat()
            else if (event.count < 0)
                Math.pow(1.0005, event.count.toDouble()).toFloat()
            else 1.0f
            zoom *= delta
        }
    }

}

/**
 * Start the sketch.
 */
fun main(passedArgs : Array<String>) {
    val appletArgs = arrayOf("sketches.SphericalSketch")
    if (passedArgs != null && passedArgs.size > 0) {
        PApplet.main(PApplet.concat(appletArgs, passedArgs))
    } else {
        PApplet.main(appletArgs)
    }
}