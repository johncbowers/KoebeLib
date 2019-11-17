package tilings.algorithms

import geometry.ds.dcel.DCEL
import komplex.KData
import packing.PackData
import rePack.EuclPacker

class EuclidPack () {

    fun pack (tiling : DCEL<Unit, Unit, Unit>) : PackData {
        return pack(tiling, 1000)
    }

    fun pack (tiling : DCEL<Unit, Unit, Unit>, iterations : Int) : PackData {

        // Find the max degree vertex and swap it to last index:
        var maxDegree = tiling.verts[tiling.verts.size-1].edges().size
        var maxDegreeIdx = tiling.verts.size - 1
        tiling.verts.map { v -> v.edges().size }.forEachIndexed {
            idx, degree ->
            if (degree > maxDegree) {
                maxDegree = degree
                maxDegreeIdx = idx
            }
        }
        val tmp = tiling.verts[maxDegreeIdx]
        tiling.verts[maxDegreeIdx] = tiling.verts[tiling.verts.size - 1]
        tiling.verts[tiling.verts.size - 1] = tmp

        var packing = PackData(null)  //packData is class that holds the packing information, combinatorics, etc..

        packing.alloc_pack_space(tiling.verts.size, true)
        packing.status = true

        // Create a map from a vertex to an index
        var vtoi = mutableMapOf<DCEL<Unit, Unit, Unit>.Vertex, Int> ()
        for ( i in 0..tiling.verts.lastIndex ) {
            vtoi[tiling.verts[i]] = i+1
        }

        // Set nodeCount equal to number of vertices
        packing.nodeCount = tiling.verts.size

        // Set rData (array containing radii)
        for ( i in 0 .. tiling.verts.lastIndex )
            packing.rData[i+1].rad = 1.0


        // Set kData (the array with combinatoric information, in particular, the flower: the counterclockwise list
        // of neighbors to each vertex
        packing.kData = arrayOfNulls<KData>(tiling.verts.size+1)

        for (i in 0..tiling.verts.lastIndex) {

            val v = tiling.verts[i]
            val inDarts = v.inDarts()

            packing.kData[i+1] = KData()
            packing.kData[i+1].num = inDarts.size
            packing.kData[i+1].bdryFlag = 0

            packing.kData[i+1].flower = IntArray(inDarts.size+1)

            for (j in 0..inDarts.lastIndex) {
                val other = inDarts[j].origin

                packing.kData[i+1].flower[j] = vtoi[other] as Int
            }
            //add the first vertex again to indiciate a cycle
            //TODO This should only happen for interior vertices
            packing.kData[i+1].flower[inDarts.size] = vtoi[inDarts[0].origin] as Int
        }

        // Spherical Geometry
        packing.hes = 1


        // After inputting data, must call
        packing.setCombinatorics()
        packing.setGamma(packing.nodeCount)

        // Determine how many iterations N you want in the radius computation, then call repack_call
        // new centers and radii should be stored in packing
        //packing.repack_call(1000,false,false);
        this.repack_call(packing, iterations, false)

        /*for (i in 1..tiling.verts.size) {
            val rData = packing.rData[i]
            val center = rData.center
            val rad = rData.rad
            val theta = center.x
            val phi = center.y
            val radScale = Math.cos(rad)
            val a = radScale*Math.cos(theta)* Math.sin(phi)
            val b = radScale*Math.sin(theta) * Math.sin(phi)
            val c = radScale*Math.cos(phi)
            val disk = DiskS2(a, b, c, -(a*a + b*b + c*c))
            convHull.verts[i-1].data = disk
        }*/

        return packing
    }

    fun repack_call(packing: PackData, passes: Int, useC: Boolean) {

        val packer = EuclPacker(packing, useC)
        //packer.setPassLimit(passes)
        //packer.load()
        //packer.startRiffle()
        EuclPacker.oldReliable(packing, passes)
        //packer.continueRiffle(passes)
    }

}