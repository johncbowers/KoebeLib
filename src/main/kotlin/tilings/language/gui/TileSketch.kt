package tilings.language.gui

import com.processinghacks.arcball.Arcball
import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.Euclidean2.SegmentE2
import geometry.primitives.Spherical2.CoaxialFamilyS2
import geometry.primitives.Spherical2.DiskS2
import geometry.primitives.Spherical2.PointS2
import processing.core.PApplet
import sketches.SphericalSketch

class TileSketch : SphericalSketch() {

    /**
     * Sets up the drawing canvas.
     */
    override fun setup() {

        background(255)

        arcball = Arcball(this)
        viewSettings.showSphere = false
        viewSettings.showBoundingBox = false
        zoom = 0.1f


        val escherFrame = EscherFrame(this)
        escherFrame.setSize(800, 600)
        escherFrame.setVisible(true)
        escherFrame.setup()



    }

    fun getData(points : MutableList<PointE2>, segments : MutableList<SegmentE2>) {
        objects.clear()
        //objects.addAll(points)
        objects.addAll(segments)
    }
}

/**
 * Start the sketch.
 */
fun main(passedArgs : Array<String>) {
    val appletArgs = arrayOf("tilings.language.gui.TileSketch")
    if (passedArgs != null) {
        PApplet.main(PApplet.concat(appletArgs, passedArgs))
    } else {
        PApplet.main(appletArgs)
    }
}