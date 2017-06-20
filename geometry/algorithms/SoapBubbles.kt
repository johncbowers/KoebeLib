package geometry.algorithms

import geometry.primitives.OrientedProjective2.CircleArcOP2
import geometry.primitives.OrientedProjective2.LineOP2
import geometry.primitives.OrientedProjective2.PointOP2
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Created by sarahciresi on 6/19/17.
 */


class SoapBubbles() {

    fun bubbleIntersections1DImage(arcs: ArrayList<CircleArcOP2>, xMin: Double, xMax: Double,
                            theta: Double, numDivisions: Int): ArrayList<Int> {

        var numIntsList = ArrayList<Int>()
        var currLoc = xMin
        val stepSize = (xMax-xMin)/numDivisions

        for (i in 0..numDivisions - 1) {

            var numIntersections = 0
            val line = LineOP2(Math.cos(theta), Math.sin(theta), -1.0 * currLoc)

            for (arc in arcs) {
                // get number of intersection points of each arc with current line, and add to number of total intersections
                numIntersections += (arc.intersectWith(line)).size
            }
            numIntsList.add(numIntersections)
            currLoc+= stepSize
        }
        return numIntsList
    }

    fun saveImage(oneDimImage: ArrayList<Int>, filePath: String) {

        // Scale 1D array to a range between 0 and 255
        val min = oneDimImage.min()?.toInt()
        val max = oneDimImage.max()?.toInt()

        if ( min!= null && max!= null) {
            // Interpolation function is (x - min) * (255 / (max - min) )
            for (i in 0..oneDimImage.size - 1) {
                oneDimImage[i] = (oneDimImage[i] - min) * (255 / (max - min))
            }
        }

        val img = BufferedImage(oneDimImage.size, oneDimImage.size, BufferedImage.TYPE_INT_RGB )
        val outputFile: File = File(filePath)

        // how to save the twoDimImage?
        for ( x in 0..oneDimImage.size - 1 ) {
            for ( y in 0..oneDimImage.size -1 ) {
                img.setRGB(x,y, Color(255 - oneDimImage[y],255 - oneDimImage[y],255 - oneDimImage[y]).rgb)
            }
        }

        // write the image to the output file
        ImageIO.write(img, "jpeg", outputFile)
    }

    // an array of PointOP2s of all intersection points
    fun bubbleIntersections(arcs: ArrayList<CircleArcOP2>, xMin: Double, xMax: Double,
                            theta: Double, numDivisions: Int): ArrayList<PointOP2> {

        var allIntersections = ArrayList<PointOP2>()
        var currLoc = xMin
        val stepSize = (xMax-xMin)/numDivisions

        for (i in 0..numDivisions - 1) {

            val line = LineOP2(Math.cos(theta), Math.sin(theta), -1.0 * currLoc)

            for (arc in arcs) {
                // get intersection points of each arc with current line, and add to list of all intersections
                allIntersections.addAll(arc.intersectWith(line))
            }
            currLoc+= stepSize
        }
        return allIntersections
    }


    fun renderBubbleImage ( intersectionPts: ArrayList<PointOP2>, filePath: String) {

    }
}