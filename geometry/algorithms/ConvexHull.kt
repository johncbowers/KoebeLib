package geometry.algorithms

import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.determinant

/**
 * Created by Sarah Ciresi on 5/25/17.
 */

// computes the convex hull by testing whether a triple of points is on hull by computing signed volume
// of those 3 points with all other points; if all volumes positive or all negative, this triangulation is on c.h.
fun NaiveConvexHull(s: MutableList<PointE3>): MutableList<Triangle> {

    val ch = mutableListOf<Triangle>()

    for ( i in 0..s.lastIndex) {
        for ( j in 0.. s.lastIndex) {
            for ( k in 0.. s.lastIndex) {

                // as long as no two indices the same
                if ( i != j && j != k && i !=k )  {
                    // if allSameSide is true, this triangle is on the convex hull
                    // add to array list of triangles
                    if (allNegative( s[i], s[j], s[k], s)) {
                        ch.add(Triangle(s[i], s[j], s[k]))
                    }
                }
            }
         }
    }

    return ch
}


// iterate through all other points and test that are on same side by computing
// signed area of polygon between points A,B,C and current point

// test whether determinant (signed area of a polgyon with 4 vertices) is positive
/*
Found a sweet determinant trick. This 3x3 determinant can be done with a 4x4. See code below.

Here was the original:

internal fun detIsNegative(p1: PointE3, p2: PointE3, p3: PointE3, queryPoint: PointE3): Boolean {

    val entry1 = p2.x - p1.x
    val entry2 = p2.y - p1.y
    val entry3 = p2.z - p1.z

    val entry4 = p3.x - p1.x
    val entry5 = p3.y - p1.y
    val entry6 = p3.z - p1.z

    val entry7 = queryPoint.x - p1.x
    val entry8 = queryPoint.y - p1.y
    val entry9 = queryPoint.z - p1.z

    val det = determinant(
            entry1, entry2, entry3,
            entry4, entry5, entry6,
            entry7, entry8, entry9
    )

    return det < 0
}
*/

internal fun allNegative(p1: PointE3, p2: PointE3, p3: PointE3, queryPoints: MutableList<PointE3>): Boolean {

    // Check this out! It's a functional-programming version :-) :
    return !queryPoints
            .filter { queryPt -> queryPt !== p1 && queryPt !== p2 && queryPt !== p3 }
            .map {
                qPt -> determinant(
                        p1.x,  p1.y,  p1.z,  1.0,
                        p2.x,  p2.y,  p2.z,  1.0,
                        p3.x,  p3.y,  p3.z,  1.0,
                        qPt.x, qPt.y, qPt.z, 1.0
                ) < 0
            }
            .contains(true)
}

// if (queryPoints.size <= 3) return true
//    var isNegative: Boolean
//    var allNegative = true
//    var negArea = ArrayList<Boolean>()
//
//    for (queryPoint in queryPoints) {
//        // check that query point is not equal to any of other points
//        if (p1 !== queryPoint && p2 !== queryPoint && p3 !== queryPoint) {
//
//            isNegative = detIsNegative(p1, p2, p3, queryPoint)
//
//            //
//            if (!isNegative)
//            {
//                allNegative = false;
//            }
//        }
//    }
//
//    return allNegative



class Triangle(v1: PointE3, v2: PointE3, v3: PointE3) {
    val vertices = listOf<PointE3>(v1, v2, v3)
}

/*
Changes Made:
-   You can initialize the vertex list directly inline so that there is no need for an init {} block.
    this also allows vertices to be immutable, which is more correct since it shouldn't ever change once a triangle is
    set.
-   vertices should also be a val instead of a var so that it is read only.

Here's the original:

class Triangle(V1: PointE3, V2: PointE3, V3: PointE3) {

    // three vertices of triangle
    var vertices = listOf<PointE3>(V1, V2, V3)

    init {
        this.vertices.add(V1)
        this.vertices.add(V2)
        this.vertices.add(V3)
    }
}
*/