/**
 * Created by johnbowers on 4/8/17.
 */
import processing.*
import processing.core.*
import processing.data.*
import processing.event.*
import processing.opengl.*

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.util.ArrayList
import java.util.HashMap

import geometry.ds.dcel.DCEL;
import geometry.primitives.Euclidean2.Point2d;

class SimpleSketch : PApplet() {

    val dcel = DCEL<Point2d, Unit, Unit>()

    override fun setup() {
        background(255)
    }

    override fun draw() {
        background(255)
        stroke(0)
        line(0f, 0f, 800f, 600f)
    }

    override fun settings() {
        size(800, 600)
        smooth(4)
    }
}

fun main(passedArgs : Array<String>) {
    val appletArgs = arrayOf("SimpleSketch")
    if (passedArgs != null) {
        PApplet.main(PApplet.concat(appletArgs, passedArgs))
    } else {
        PApplet.main(appletArgs)
    }
}