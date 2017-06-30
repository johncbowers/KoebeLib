package geometry.algorithms

import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.Euclidean2.VectorE2
import geometry.primitives.OrientedProjective2.CircleArcOP2
import geometry.primitives.OrientedProjective2.DiskOP2
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

    fun reflectionAngle(arc: CircleArcOP2, line: LineOP2) : Double {

        // dealing with two intersection points on same arc?
        val intPt = arc.intersectWith(line)
        val lightVec = VectorE2(-line.b, line.a)
        val arcVec = VectorE2(PointE2(intPt[0].hx/intPt[0].hw, intPt[0].hy/intPt[0].hw ) - arc.center)
        var theta = Math.acos( lightVec.dot(arcVec) / (lightVec.norm()*arcVec.norm()) )

        if (theta > Math.PI/2)  theta = Math.PI - theta
        println("theta = " + theta)

        return theta
    }

    fun bubbleInts1DImageWithReflections(arcs: ArrayList<CircleArcOP2>, xMin: Double, xMax: Double,
                                         theta: Double, numDivisions: Int): ArrayList<Int> {

        // Array of Intensities, starts at 255 and is reduced when intersects based on angles of intersections
        var lightIntensitiesList = ArrayList<Int>()
        var currLoc = xMin
        val stepSize = (xMax-xMin)/numDivisions
        val soapRefractionIndex = 1.33

        // For each light ray
        for (i in 0..numDivisions - 1) {

            // Initial intensity of light ray is 255
            var intensity = 255.0
            var compoundedIntensityProp = 1.0
            val line = LineOP2(Math.cos(theta), Math.sin(theta), -1.0 * currLoc)

            for (arc in arcs) {
                // get reflection angle between light ray and current arc
                if ( arc.intersectWith(line).size > 0) {
                    val thetaIncident = reflectionAngle(arc, line)
                    val thetaTransmission = Math.sin(thetaIncident) / soapRefractionIndex

                    // Compute reflectance for polarized light
                    val Rp = Math.pow((1.0 * Math.cos(thetaIncident) - soapRefractionIndex * Math.cos(thetaTransmission)) /
                            (1.0 * Math.cos(thetaIncident) + soapRefractionIndex * Math.cos(thetaTransmission)), 2.0)

                    val Tp = (2*Math.cos(thetaIncident))/(Math.cos(thetaIncident)+soapRefractionIndex * Math.cos(thetaTransmission))

                    //println("Rp = " + Rp)
                    // Update the intensity proportion
                    compoundedIntensityProp = compoundedIntensityProp  * (Tp)
                }
            }
            println("compoundedIntensityProp = " + compoundedIntensityProp)

            // Compute the final intensity hitting the sensor
            intensity = (intensity * compoundedIntensityProp)
            lightIntensitiesList.add(intensity.toInt())
            currLoc+= stepSize

            println("intensity = " + intensity)

        }

        return lightIntensitiesList
    }

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


    fun renderBubbleImage ( intersectionPts: ArrayList<PointOP2>, filePath: String, imageSize: Int) {

        val img = BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB )
        val outputFile: File = File(filePath)

        // set pixels to black for intersection points, white if not
        for ( x in 0..imageSize - 1 ) {
            for ( y in 0..imageSize -1 ) {
                img.setRGB(x,y, Color(255, 255, 255).rgb)
            }
        }

        // toInt ?
        for ( pt in intersectionPts) {
            val scaledX = (pt.hx/pt.hw + 2)*(imageSize/4)
            val scaledY = (pt.hy/pt.hw + 2)*(imageSize/4)
            //print("scX = " + scaledX + "scY = " + scaledY)
            if( (0 <= scaledX && scaledX <= imageSize-1) && (0 <= scaledY && scaledY <= imageSize-1)) {
                img.setRGB(scaledX.toInt(), scaledY.toInt(), Color(0, 0, 0).rgb)
            }
        }

        // write the image to the output file
        ImageIO.write(img, "jpeg", outputFile)
    }
}