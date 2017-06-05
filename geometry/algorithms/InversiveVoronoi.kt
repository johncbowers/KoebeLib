package geometry.algorithms

import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.Spherical2.*
import geometry.ds.dcel.*
import geometry.primitives.OrientedProjective3.PointOP3
import sketches.SphericalSketch

/**
 * Created by sarahciresi on 6/5/17.
 */

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

        voronoiEdge = CircleArcS2(genPoints1[1], genPoints2[0], dual12)

        return voronoiEdge
    }

    fun inversiveVoronoi( convHull: ConvexHull<DiskS2> ): MutableList<CircleArcS2> {

        var voronoiEdges: MutableList<CircleArcS2> = mutableListOf<CircleArcS2>()

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
