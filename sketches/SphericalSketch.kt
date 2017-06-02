package sketches

/**
 * Created by johnbowers on 5/20/17.
 */


import com.processinghacks.arcball.Arcball
import geometry.algorithms.IncrementalConvexHullAlgorithms
import processing.core.PApplet

import geometry.primitives.Euclidean3.*
import geometry.primitives.Spherical2.*
import geometry.primitives.OrientedProjective3.*
import geometry.construction.Construction
import geometry.algorithms.incrConvexHull
import geometry.algorithms.orientationPointOP3
import geometry.algorithms.addPoint
import geometry.ds.dcel.*

import gui.JythonFrame
import processing.core.PConstants

import javax.swing.*

open class SphericalSketch : PApplet() {

    internal var arcball: Arcball? = null
    internal var applyToCamera = true

    var construction = Construction()
    val objects = mutableListOf<Any>()

    val constructionLock = Any()

    val viewSettings = SphericalSketchViewSettings()

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

        val p1 = PointOP3( 0.00, 0.00,  0.0, 1.0)
        val p2 = PointOP3( 1.00, 1.00,  0.0, 1.0)
        val p3 = PointOP3( 1.00, 0.00,  0.0, 1.0)
        val p4 = PointOP3( 0.25, 0.25,  1.0, 1.0)
        val p5 = PointOP3( 1.20,-0.20, -0.5, 1.0)

        val ch = incrConvexHull<PointOP3>(
                listOf<PointOP3>(p1, p2, p3, p4),
                {t1: PointOP3, t2: PointOP3, t3: PointOP3, t4: PointOP3->orientationPointOP3(t1, t2, t3, t4)}
        )
        addPoint(ch, p5, {t1: PointOP3, t2: PointOP3, t3: PointOP3, t4: PointOP3->orientationPointOP3(t1, t2, t3, t4)})

        objects.add(p1)
        objects.add(p2)
        objects.add(p3)
        objects.add(p4)
        objects.add(p5)

        objects.add(ch)
    }

    fun drawPointE3(p: PointE3) {

        pushMatrix()
        pushStyle()

        translate(p.x.toFloat(), p.y.toFloat(), p.z.toFloat())
        fill(100.0f, 125.0f, 255.0f)
        sphere(0.035f)

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
        strokeWeight(0.01f)
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
        strokeWeight(0.01f)
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
                    else -> {}
                }
            }
            endShape()
//            println("\tENDFACE")
        }
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
        scale(200.0f)
        strokeWeight(0.005f)

        pushStyle()

        noStroke()
        lights()

        if (viewSettings.showSphere) sphere(1.0f)

        // Draw the points
        synchronized(constructionLock, {
            listOf<MutableList<Any>>(
                    construction.getGeometricObjects(),
                    objects
            ).forEach {
                list ->
                list.forEach {
                    when (it) {
                        is PointS2 -> drawPointE3(it.directionE3.endPoint)
                        is PointE3 -> drawPointE3(it)
                        is PointOP3 -> if (!it.isIdeal()) drawPointE3(it.toPointE3())
                        is DiskS2 -> {
                            stroke(0)
                            drawCircleS2(it)
                        }
                        is CircleArcS2 -> {
                            stroke(0)
                            drawCircleArcS2(it)
                        }
                        is CPlaneS2 -> {
                            stroke(255.0f, 0.0f, 0.0f)
                            drawCircleS2(it.dualDiskS2)
                        }
                        is DCEL<*,*,*> -> {
                            stroke(0)
                            fill(255.0f, 255.0f, 255.0f)
                            drawDCEL(it)
                        }
                        else -> {
                        }
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
}

/**
 * Start the sketch.
 */
fun main(passedArgs : Array<String>) {
    val appletArgs = arrayOf("sketches.SphericalSketch")
    if (passedArgs != null) {
        PApplet.main(PApplet.concat(appletArgs, passedArgs))
    } else {
        PApplet.main(appletArgs)
    }
}