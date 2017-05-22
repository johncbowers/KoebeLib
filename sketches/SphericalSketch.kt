package sketches

/**
 * Created by johnbowers on 5/20/17.
 */


import com.processinghacks.arcball.Arcball
import processing.core.PApplet

import geometry.primitives.Euclidean3.*
import geometry.primitives.Spherical2.*
import geometry.primitives.isZero

class SphericalSketch : PApplet() {

    internal var arcball: Arcball? = null
    internal var applyToCamera = true

    internal var points = mutableListOf<PointS2>()
    internal var circles = mutableListOf<CircleS2>()

    internal var showBoundingBox = true
    internal var showCircleCentersAndNormals = false
    internal var showSphere = true
    /**
     * Sets up the drawing canvas.
     */
    override fun setup() {
        background(255)
        arcball = Arcball(this)
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

    fun drawCircleS2(circle: CircleS2) {
        pushMatrix()
            // Get the basis vectors for the circle
            val b1 = DirectionE3(circle.basis1())
            val b2 = DirectionE3(circle.basis2())
            val b3 = DirectionE3(circle.basis3())

            // Compute the distance from the origin to the circle's Euclidean center and its euclidean radius
            val centerDist = circle.centerE3.distTo(O).toFloat()
            val diameter = circle.radiusE3.toFloat() * 2.0f

            // Rotate the e1, e2, e3 basis vectors to b1, b2, b3
            applyMatrix(
                b1.v.x.toFloat(), b2.v.x.toFloat(), b3.v.x.toFloat(), 0.0f,
                b1.v.y.toFloat(), b2.v.y.toFloat(), b3.v.y.toFloat(), 0.0f,
                b1.v.z.toFloat(), b2.v.z.toFloat(), b3.v.z.toFloat(), 0.0f,
                0.0f,             0.0f,             0.0f,             1.0f
            )

            // Translate the drawing frame up to the distance from the circle center
            if (b3.dot(DirectionE3(circle.centerE3.toVectorE3())) > 0.0)
                translate(0.0f, 0.0f, centerDist)
            else
                translate(0.0f, 0.0f, -centerDist)

            // Draw the circle
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
            pushMatrix()
            pushStyle()
                lights()


            pushMatrix()
            fill(255.0f, 0.0f, 0.0f)
            translate(circle.centerE3.x.toFloat(), circle.centerE3.y.toFloat(), circle.centerE3.z.toFloat())
            sphere(0.035f)
            popMatrix()

            pushMatrix()
            fill(0.0f, 255.0f, 0.0f)
            translate(circle.N.x.toFloat(), circle.N.y.toFloat(), circle.N.z.toFloat())
            sphere(0.035f)
            popMatrix()

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
            circles.forEach { drawCircleS2(it) }

            if (showBoundingBox) {
                noFill()
                stroke(0)
                box(2.0f)
            }

        popStyle()
    }

    internal var eps = 0.0
    fun update() {

        points = mutableListOf<PointS2>()
//        points.add(PointS2(0.0, 1.0, 0.0))
//        //points.add(PointS2(0.0, 0.0, 1.0))
//        points.add(PointS2(eps, 0.0,-1.0))
//        points.add(PointS2(1.0, eps, eps))

        points.add(PointS2(-1.0,-1.0, 1.0))
        points.add(PointS2( 1.0,-1.0, 1.0))
        points.add(PointS2( 1.0, 1.0, 1.0))

        circles = mutableListOf<CircleS2>()
        //circles.add(CircleS2(points[0], points[1], points[2]))
        circles.add(CircleS2(points[0], points[2], points[1]))

        eps += 0.001
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
    val appletArgs = arrayOf("sketches.SphericalSketch")
    if (passedArgs != null) {
        PApplet.main(PApplet.concat(appletArgs, passedArgs))
    } else {
        PApplet.main(appletArgs)
    }
}