package sketches

/**
 * Created by johnbowers on 5/20/17.
 */


import com.processinghacks.arcball.Arcball
import processing.core.PApplet

import geometry.primitives.Euclidean3.*
import geometry.primitives.Spherical2.*
import geometry.primitives.isZero
import geometry.algorithms.*


class VoronoiSketch : PApplet() {

    internal var arcball: Arcball? = null
    internal var applyToCamera = true

    internal var points = mutableListOf<PointS2>()
    internal var circles = mutableListOf<DiskS2>()
    internal var ConvexHull = mutableListOf<Triangle>()
    internal var conicalCaps = mutableListOf<PointE3>()
    internal var arcs = mutableListOf<CircleArcS2>()

    internal var showBoundingBox = true
    internal var showCircleCentersAndNormals = true
    internal var showSphere = true
    internal var showDualPoint = true
    internal var showEuclideanDisks = false

    /**
     * Sets up the drawing canvas.
     */
    override fun setup() {
        background(255)
        arcball = Arcball(this)

        points = mutableListOf<PointS2>()

        // create 4 different disks, using 12 pts, use disk data structure to get teal caps , get convex hull
        points.add(PointS2(-1.0, 0.5, 1.0))
        points.add(PointS2(-1.0, 0.8, 0.8))
        points.add(PointS2(-1.0, -0.0, 0.7))
        points.add(PointS2(-.7, 1.0, -0.7))
        points.add(PointS2(0.0, 1.0, -0.5))
        points.add(PointS2(0.0, 0.5, 0.2))
        points.add(PointS2(0.0, 0.8, 0.5))
        points.add(PointS2(0.5, 0.5, 0.0))
        points.add(PointS2(-0.5, -0.5, 0.0))
        points.add(PointS2(0.0, -1.0, -1.0))
        points.add(PointS2(-1.0, 0.0, 0.0))
        points.add(PointS2(-0.2, 1.0, -0.7))
        points.add(PointS2(-1.5, -.5, -.5))


        circles = mutableListOf<DiskS2>()
        circles.add(DiskS2(points[0], points[1], points[2]))      // circles[0]
        circles.add(DiskS2(points[5], points[6], points[7]))      // circles[1]
        circles.add(DiskS2(points[3], points[4], points[11]))     // circles[2]
        circles.add(DiskS2(points[8], points[9], points[10]))     // circles[3]


        arcs = mutableListOf<CircleArcS2>()
        arcs.add(CircleArcS2(points[0], points[2], circles[0]))
        arcs.add(CircleArcS2(points[5], points[7], circles[1]))
        arcs.add(CircleArcS2(points[3], points[11], circles[2]))
        arcs.add(CircleArcS2(points[8], points[10], circles[3]))



        // compute the complex hull of conical cap points

        // get the 4 conical cap points
        for (disk in circles) {
            val dual = disk.dualPointOP3.toPointE3()
            conicalCaps.add(dual)
        }

        ConvexHull = NaiveConvexHull(conicalCaps)

    }

    fun drawPointS2(p: PointS2) {

        pushMatrix()
        pushStyle()

        val d = p.directionE3
        translate(d.v.x.toFloat(), d.v.y.toFloat(), d.v.z.toFloat())
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
        stroke(0)
        fill(0)
        ellipse(0.0f, 0.0f, diameter, diameter)
        popStyle()

        popMatrix()

        // Draw Euclidean Center
        if (showCircleCentersAndNormals) {
            val ctr = disk.centerE3
            pushMatrix()
            pushStyle()

            lights()

            pushMatrix()
            fill(255.0f, 0.0f, 0.0f)
            translate(ctr.x.toFloat(), ctr.y.toFloat(), ctr.z.toFloat())
            sphere(0.035f)
            popMatrix()

            pushMatrix()
            fill(0.0f, 255.0f, 0.0f)
            val dir = DirectionE3(VectorE3(disk.a, disk.b, disk.c))
            translate(dir.v.x.toFloat(), dir.v.y.toFloat(), dir.v.z.toFloat())
            sphere(0.035f)
            popMatrix()

            popStyle()
            popMatrix()
        }

        if (showDualPoint) {
            val dual = disk.dualPointOP3.toPointE3()
            pushMatrix()
            pushStyle()

            lights()

            fill(0.0f, 255.0f, 255.0f)
            translate(dual.x.toFloat(), dual.y.toFloat(), dual.z.toFloat())
            sphere(0.035f)

            popStyle()
            popMatrix()
        }

    }


    fun drawConvexHull() {
        stroke(0)
        noFill()

        // draw convex hull

        // for each triangle in convex hull, draw the triangle
        for (tri in ConvexHull) {

            val v1 = tri.vertices.get(0)
            val v2 = tri.vertices.get(1)
            val v3 = tri.vertices.get(2)

            beginShape()
            vertex(v1.x.toFloat(), v1.y.toFloat(), v1.z.toFloat())
            vertex(v2.x.toFloat(), v2.y.toFloat(), v2.z.toFloat())
            vertex(v3.x.toFloat(), v3.y.toFloat(), v3.z.toFloat())
            vertex(v1.x.toFloat(), v1.y.toFloat(), v1.z.toFloat())
            endShape()
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
        if (showEuclideanDisks)
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
        if (showCircleCentersAndNormals) {
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

        if (showDualPoint) {
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
        scale(250.0f)
        strokeWeight(0.005f)

        pushStyle()

        noStroke()
        lights()

        if (showSphere) sphere(1.0f)

        // Draw the points
        points.forEach { drawPointS2(it) }
        //circles.forEach { drawCircleS2(it) }
        arcs.forEach { drawCircleArcS2(it) }

        // Draw the convex hull
        drawConvexHull()

        // should change function to drawTriangle function, where within iterate over each triangle in convexhull
        // drawTriangle(ConvexHull)
        // -- within fn --> ConvexHull.forEach { drawTriangle(it) }

        popStyle()
    }

    internal var eps = 0.0
    fun update() {


    }

    /**
     * Sets up the sketches.main graphics settings for the PApplet window
     */
    override fun settings() {
        size(1024, 768, P3D)
        smooth(4)
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
    val appletArgs = arrayOf("sketches.VoronoiSketch")
    if (passedArgs != null) {
        PApplet.main(PApplet.concat(appletArgs, passedArgs))
    } else {
        PApplet.main(appletArgs)
    }
}