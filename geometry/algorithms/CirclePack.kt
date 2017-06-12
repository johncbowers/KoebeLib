package geometry.algorithms

import geometry.ds.dcel.*
import geometry.primitives.Spherical2.DiskS2
import geometry.primitives.Euclidean3.*
import komplex.KData
import packing.*
import panels.CPScreen

/**
 * Created by sarahciresi on 6/9/17.
 */



// idea: to be able to turn our convex hull code into CirclePack type format in order to generate circle packings
//       from our hulls

// also, later to be able to then turn CirclePack code to code usable in our library


class CirclePack() {

    /* Functions for computing a Circle Packing  */
    fun pack( convHull: ConvexHull<DiskS2> ) {

        var CPScreen = CPScreen()

        var packing = PackData(CPScreen)  //packData is class that holds the packing information, combinatorics, etc..

        // Create a map from a vertex to an index
        var vtoi = mutableMapOf<DCEL<DiskS2, Unit, Unit>.Vertex, Int> ()
        for ( i in 0..convHull.verts.size - 1 ) {
            vtoi[convHull.verts[i]] = i
        }

        // Set nodeCount equal to number of vertices
        packing.nodeCount = convHull.verts.size

        // Set rData (array containing radii)
        for ( i in 0 .. convHull.verts.size-1 )
            packing.rData[i].rad = 1.0


        // Set kData (the array with combinatoric information, in particular, the flower: the counterclockwise list
        // of neighbors to each vertex
        packing.kData = arrayOfNulls<KData>(convHull.verts.size)

        for (i in 0..convHull.verts.size-1) {

            val v = convHull.verts[i]
            val inDarts = v.inDarts()

            packing.kData[i] = KData()
            packing.kData[i].num = inDarts.size
            packing.kData[i].bdryFlag = 0



            packing.kData[i].flower = IntArray(inDarts.size+1)

            for (j in 0.. inDarts.size -  1) {
                val other = inDarts[j].origin

                packing.kData[i].flower[j] = vtoi[other] as Int
            }
            // add the first vertex again to indiciate a cycle
            packing.kData[i].flower[inDarts.size] = vtoi[inDarts[0].origin] as Int
        }

        // Spherical Geometry
        packing.hes = 1


        // After inputting data, must call
        packing.setCombinatorics()

        // Determine how many iterations N you want in the radius computation, then call repack_call
        // new centers and radii should be stored in packing
        packing.repack_call(1000,false,false);



    }




}