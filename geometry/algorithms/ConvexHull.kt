package geometry.algorithms

import java.util.Collections
import geometry.ds.dcel.DCEL;
import geometry.primitives.Euclidean2.*;
import geometry.primitives.Euclidean3.PointE3
import java.util.ArrayList



/**
 * Created by sarahciresi on 5/25/17.
 */

// computes the convex hull by testing whether a triple of points is on hull by computing signed volume
// of those 3 points with all other points; if all volumes positive or all negative, this triangulation is on c.h.
fun NaiveConvexHull(s: MutableList<PointE3>): MutableList<Triangle> {

    var ConvexHull: MutableList<Triangle> = mutableListOf()
    var allSameSide = true;


    for ( i in 0..s.size - 3) {
        for ( j in (i + 1).. s.size - 2) {
            for ( k in (j + 1).. s.size - 1) {

                    // cant have any two points equal if i != j, i!=k ..

                 allSameSide = allSameSide( s[i], s[j], s[k], s);

                // if allSameSide is true, this triangle is on the convex hull
                // add to array list of triangles
                if (allSameSide) {
                    ConvexHull.add( Triangle(s[i], s[j], s[k] ) )
                }
            }
         }
    }

    return ConvexHull
}


// compute determinant of a 2x2
fun determinant2(a: Double, b: Double, c: Double, d: Double): Double {
    return a * d - b * c
}

//compute determinant of a 3x3
fun determinant3(a: Double, b: Double, c: Double, d: Double, e: Double, f: Double, g: Double, h: Double, i: Double): Double {
    var firstTerm = a * determinant2(e, f, h, i)
    var secondTerm = b * determinant2(d, f, g, i)
    var thirdTerm = c * determinant2(d, e, g, h)

    return firstTerm - secondTerm + thirdTerm
}


// iterate through all other points and test that are on same side by computing
// signed area of polygon between points A,B,C and current point

// test whether determinant (signed area of a polgyon with 4 vertices) is positive
fun detIsPositive(p1: PointE3, p2: PointE3, p3: PointE3, queryPoint: PointE3): Boolean {
    var entry1 = p2.x - p1.x
    var entry2 = p2.y - p1.y
    var entry3 = p2.z - p1.z

    var entry4 = p3.x - p1.x
    var entry5 = p3.y - p1.y
    var entry6 = p3.z - p1.z

    var entry7 = queryPoint.x - p1.x
    var entry8 = queryPoint.y - p1.y
    var entry9 = queryPoint.z - p1.z


    var det = determinant3(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9)

    return det > 0
}


fun allSameSide(p1: PointE3, p2: PointE3, p3: PointE3, queryPoints: MutableList<PointE3>): Boolean {
    if (queryPoints.size <= 3) return true

    var isPositive: Boolean
    var allSameSide = true
    var posArea = ArrayList<Boolean>()

    for (queryPoint in queryPoints) {
        // check that query point is not equal to any of other points
        if (p1 !== queryPoint && p2 !== queryPoint && p3 !== queryPoint) {

            isPositive = detIsPositive(p1, p2, p3, queryPoint)

            // append to list of signed areas
            posArea.add(isPositive)
        }
    }

    // loop through array to see if all positive or negative
    // by comparing with first value in array
    val firstAreaPos = posArea[0]

    for (isPos in posArea) {
        if (isPos != firstAreaPos) {
            allSameSide = false
        }
    }
    return allSameSide

}


class Triangle(V1: PointE3, V2: PointE3, V3: PointE3) {

    // three vertices of triangle
    var vertices = mutableListOf<PointE3>()

    init {
        this.vertices.add(V1)
        this.vertices.add(V2)
        this.vertices.add(V3)
    }


}