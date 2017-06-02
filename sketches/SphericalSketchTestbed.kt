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

//        val p1 = PointOP3( -1.1, -1.3,  1.0, 1.0)
//        val p2 = PointOP3( 1.00, 1.00,  0.0, 1.0)

//        val line = LineOP3(p1, p2)
//        val pointsOnSphere = line.getIntersectionWithUnit2Sphere()

        // TODO

//        objects.add(p1)
//        objects.add(p2)
//        objects.add(line)
//        objects.add(pointsOnSphere[0])
//        objects.add(pointsOnSphere[1])


        val p1 = PointS2(0.5, 0.3, 1.0)
        val p2 = PointS2(0.1, -0.5, 1.0)
        val p3 = PointS2(-0.5,-0.2,1.0)

        val p4 = PointS2(1.0, 0.5, 0.3)
        val p5 = PointS2(1.0, 0.1, -0.5)
        val p6 = PointS2(1.0, -0.5, -0.2)

        val disk1 = DiskS2(p1, p2, p3)
        val disk2 = DiskS2(p4, p5, p6)


        val coaxFam = CoaxialFamilyS2(disk1, disk2)

        val genPoints = coaxFam.generatorPoints
        val dualLine = coaxFam.dualLineOP3

        println(coaxFam.type)

        objects.add(disk1)
        objects.add(disk2)

        for (points in genPoints){
            objects.add(points)}
        //objects.add(genPoints[1])






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