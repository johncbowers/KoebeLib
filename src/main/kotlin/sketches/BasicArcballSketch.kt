package sketches

/**
 * Created by johnbowers on 5/20/17.
 */

import com.processinghacks.arcball.Arcball
import processing.*
import processing.core.*
import processing.data.*
import processing.event.*
import processing.opengl.*
import processing.core.PVector

class BasicArcballSketch : PApplet() {

    internal var arcball: Arcball? = null
    internal var applyToCamera = true

    /**
     * Sets up the drawing canvas.
     */
    override fun setup() {
        background(255)
        arcball = Arcball(this)
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

        pushStyle()

            noStroke()
            lights()
            sphere(250.0f)

            noFill()
            stroke(0)
            box(500.0f)

        popStyle()
    }

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
    val appletArgs = arrayOf("sketches.SphericalSketch")
    if (passedArgs != null) {
        PApplet.main(PApplet.concat(appletArgs, passedArgs))
    } else {
        PApplet.main(appletArgs)
    }
}