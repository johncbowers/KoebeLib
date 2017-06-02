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

class SphericalSketchTestbed : SphericalSketch() {

    /**
     * Sets up the drawing canvas.
     */
    override fun setup() {

        background(255)

        arcball = Arcball(this)

        jyFrame.setSize(800, 600)
        jyFrame.setVisible(true)
        jyFrame.setup()

        // EDIT FROM HERE. Add to objects list to draw items.

        val p1 = PointOP3( -1.1, -1.3,  1.0, 1.0)
        val p2 = PointOP3( 1.00, 1.00,  0.0, 1.0)

        val line = LineOP3(p1, p2)
        // TODO

        objects.add(p1)
        objects.add(p2)
    }
}

/**
 * Start the sketch.
 */
fun main(passedArgs : Array<String>) {
    val appletArgs = arrayOf("sketches.SphericalSketchTestbed")
    if (passedArgs != null) {
        PApplet.main(PApplet.concat(appletArgs, passedArgs))
    } else {
        PApplet.main(appletArgs)
    }
}