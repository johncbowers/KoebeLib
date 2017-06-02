//package sketches
//
///**
// * Created by johnbowers on 4/8/17.
// */
//import geometry.algorithms.triangulateIncremental
//import processing.*
//import processing.core.*
//import processing.data.*
//import processing.event.*
//import processing.opengl.*
//
//import java.io.BufferedReader
//import java.io.File
//import java.io.IOException
//import java.io.InputStream
//import java.io.OutputStream
//import java.io.PrintWriter
//import java.util.ArrayList
//import java.util.HashMap
//
//import geometry.ds.dcel.DCEL
//import geometry.primitives.Euclidean2.PointE2
//
//import java.util.Random
//
//class TriangulationSketch : PApplet() {
//
//    // A set of points S
//    internal val S = arrayListOf<PointE2>()
//
//    // The dcel for the delaunay triangulation of the point set S
//    internal var dcel = DCEL<PointE2, Unit, Unit>()
//
//    // if the user has clicked on a point, selectedPoint refers to it; otherwise it's null
//    internal var selectedPoint: PointE2? = null
//
//    internal val rand = Random() // Useful for making sure nothing is collinear.
//    internal val nPoints = 5 // Number of random points to generate
//
//    /**
//     * Sets up the drawing canvas.
//     */
//    override fun setup() {
//        background(255)
//
//        // Create some random points
//        for (i in 1..nPoints) S.add(PointE2(perturb(rand) + rand.nextDouble() * width - (0.5 * width),
//                perturb(rand) + rand.nextDouble() * height - (0.5 * height)))
//    }
//
//    internal fun perturb(r: Random) = (r.nextDouble() - 0.5) * 0.00001
//
//    /**
//     * The sketches.main drawing code, called in an infinite loop to continuously redraw the screen.
//     */
//    override fun draw() {
//        update()
//
//        background(255)
//
//        // Draw the edges of the triangulation
//        stroke(0)
//        dcel.edges.forEach {
//            e ->
//            val start = e?.aDart?.origin?.data
//            val end = e?.aDart?.dest?.data
//            if (start != null && end != null)
//                line(
//                    to_screen_x(start.x),
//                    to_screen_y(start.y),
//                    to_screen_x(end.x),
//                    to_screen_y(end.y)
//                )
//        }
//
//        // Draw the point set S
//        fill(255); stroke(0)
//        S.forEach {
//            p ->
//            if (p == selectedPoint) fill(255.0f,0.0f,0.0f) else fill(255);
//            ellipse(to_screen_x(p.x), to_screen_y(p.y), 8.0f, 8.0f)
//        }
//    }
//
//    fun update() {
////        selectedPoint?.x = from_screen_x(mouseX.toDouble() + perturb(rand))
////        selectedPoint?.y = from_screen_y(mouseY.toDouble() + perturb(rand))
//        val newPoint = PointE2(
//                from_screen_x(mouseX.toDouble() + perturb(rand)),
//                from_screen_y(mouseY.toDouble() + perturb(rand))
//        )
//        S.add(S.indexOf(selectedPoint), newPoint)
//        S.remove(selectedPoint)
//        selectedPoint = newPoint
//        dcel = triangulateIncremental(S)
//    }
//
//    /*
//     * The following two functions are used to transform a natural coordinate system, with the origin in the center and
//     * positive x towards the right and positive y upwards. The normal graphics drawing puts the origin in the top-left
//     * hand corner of the screen with the positive y-direction pointing downwards. This is largely a holdover from the
//     * time when vacuum tube screens started by shooting electrons at the top left of the screen and work rightwards and
//     * downwards to draw the whole screen. Nobody bothered to talk to a mathematician about whether this was a good idea
//     * :(.
//     */
//
//    /**
//     * Converts x values from our (sane) internal coordinates to screen coordinates.
//     */
//    internal fun to_screen_x(coordX: Double) = Math.round(coordX + (width / 2)).toFloat()
//    internal fun from_screen_x(x: Double) = x - (width / 2.0)
//
//    /**
//     * Converts y values from our (sane) internal coordinates to screen coordinates.
//     */
//    internal fun to_screen_y(coordY: Double) = Math.round((height / 2) - coordY).toFloat()
//    internal fun from_screen_y(y: Double) = (height / 2.0) - y
//
//    /**
//     * Sets up the sketches.main graphics settings for the PApplet window
//     */
//    override fun settings() {
//        size(800, 600)
//        smooth(4)
//    }
//
//
//    /*** Mouse Handling Code ***/
//
//    override fun mousePressed() {
//
//        val mousePoint = PointE2(from_screen_x(mouseX.toDouble()), from_screen_y(mouseY.toDouble()))
//        val closestPoint = S.minBy<PointE2, Double> { p -> p.distSqTo(mousePoint) }
//
//        selectedPoint = if (closestPoint != null && closestPoint.distSqTo(mousePoint) < 64.0) closestPoint else null
//    }
//
//    override fun mouseReleased() {
//        selectedPoint = null
//    }
//}
//
///**
// * Start the sketch.
// */
//fun main(passedArgs : Array<String>) {
//    val appletArgs = arrayOf("sketches.TriangulationSketch")
//    if (passedArgs != null) {
//        PApplet.main(PApplet.concat(appletArgs, passedArgs))
//    } else {
//        PApplet.main(appletArgs)
//    }
//}