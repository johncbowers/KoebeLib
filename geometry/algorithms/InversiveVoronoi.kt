package geometry.algorithms

import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.Spherical2.*
import geometry.ds.dcel.*
import geometry.primitives.OrientedProjective3.PointOP3
import geometry.primitives.inner_product
import sketches.SphericalSketch
import geometry.primitives.isZero

/**
 * Created by sarahciresi on 6/5/17.
 */

class InversiveVoronoiDiagram(): ArrayList<CircleArcS2>()

/**
 * A convenience wrapper for easily exposing the convex hull functionality to the Jython interpreter. (Could also be
 * used by the Kotlin code of course, but this is relatively unnecessary.
 */
class InversiveVoronoiAlgorithms() {

    /* Functions for computing the Voronoi Diagram  */

    // helper function that takes a DCELs half edge and computes the V. arc
    fun computeVoronoiEdge(d1: DiskS2, d2: DiskS2, d3: DiskS2, d4: DiskS2): CircleArcS2 {

        var voronoiEdge: CircleArcS2

        // bisector planes
        val bisector12 = d1.bisectorWith(d2)
        val bisector23 = d1.bisectorWith(d3)
        val bisector24 = d1.bisectorWith(d4)

        // disks that are dual to bisector planes
        val dual12 = bisector12.dualDiskS2
        val dual23 = bisector23.dualDiskS2
        val dual24 = bisector24.dualDiskS2

        // coaxial family between dual disks
        val cfDual12a = CoaxialFamilyS2(dual12, dual23)
        val cfDual12b = CoaxialFamilyS2(dual12, dual24)

        // generator points of coaxial families
        val genPoints1 = cfDual12a.generatorPoints
        val genPoints2 = cfDual12b.generatorPoints

        val ortho123 = CPlaneS2(d1, d2, d3)
        val ortho142 = CPlaneS2(d1, d4, d2)

        val pt1 =
                if (genPoints1.size == 1 ||
                        isZero(inner_product(
                                ortho123.a, ortho123.b, ortho123.c, ortho123.d,
                                genPoints1[0].x, genPoints1[0].y, genPoints1[0].z, 1.0
                        )))
                    genPoints1[0]
                else
                    genPoints1[1]

        val pt2 =
                if (genPoints2.size == 1 ||
                        isZero(inner_product(
                                ortho142.a, ortho142.b, ortho142.c, ortho142.d,
                                genPoints2[0].x, genPoints2[0].y, genPoints2[0].z, 1.0
                        )))
                    genPoints2[1]
                else
                    genPoints2[0]

        //val pt1 = if (genPoints1.size == 2) genPoints1[1] else genPoints1[0]
        //val pt2 = genPoints2[0]

        voronoiEdge = CircleArcS2(pt1, pt2, dual12)

        return voronoiEdge
    }

    fun inversiveVoronoi( convHull: ConvexHull<DiskS2> ): InversiveVoronoiDiagram {

        var voronoiEdges = InversiveVoronoiDiagram()

        //for each edge of the convex hull, get the four disks necessary to compute that Voronoi edge
        for (edge in convHull.edges) {

            // list of darts for one cycle
            val disks123 = edge.aDart?.cycle()
            if (disks123 != null) {
                val disk1 = disks123[0].origin?.data
                val disk2 = disks123[1].origin?.data
                val disk3 = disks123[2].origin?.data

                //final disk in opposite face
                val disk4 = edge.aDart?.twin?.next?.dest?.data

                if (disk1 != null && disk2 != null && disk3 != null && disk4 != null) {
                    val vArc = computeVoronoiEdge(disk1, disk2, disk3, disk4)
                    val vVert1 = vArc.source
                    val vVert2 = vArc.target

                    voronoiEdges.add(vArc)

                } else {
                    throw MalformedDCELException("This DCEL contains null pointers.")
                }
            } else {
                throw MalformedDCELException("This DCEL contains null pointers.")
            }
        }

        return voronoiEdges
    }
}
