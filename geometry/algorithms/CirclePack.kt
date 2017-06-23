package geometry.algorithms

import geometry.ds.dcel.*
import geometry.primitives.Spherical2.DiskS2
import geometry.primitives.Euclidean3.*
import geometry.primitives.Spherical2.PointS2
import komplex.KData
import packing.*
import panels.CPScreen
import allMains.CirclePack.cpb
import rePack.SphPacker
import rePack.EuclPacker
import rePack.HypPacker
import rePack.GOpacker
import input.CommandStrParser
import java.io.IOException
import exceptions.CombException
import JNI.JNIinit
import allMains.CPBase
import packing.PackData
import rePack.RePacker
import rePack.SphPacker.SPH_GOPACK_THRESHOLD
import geometry.primitives.norm31
import geometry.primitives.inner_product31







/**
 * Created by sarahciresi on 6/9/17.
 */



// idea: to be able to turn our convex hull code into CirclePack type format in order to generate circle packings
//       from our hulls

// also, later to be able to then turn CirclePack code to code usable in our library


class CirclePack() {

    /* Functions for computing a Circle Packing  */
    fun pack( convHull: ConvexHull<DiskS2> ): PackData {

        // Find the max degree vertex and swap it to last index:
        var maxDegree = convHull.verts[convHull.verts.size-1].edges().size
        var maxDegreeIdx = convHull.verts.size - 1
        convHull.verts.map { v -> v.edges().size }.forEachIndexed {
            idx, degree ->
            if (degree > maxDegree) {
                maxDegree = degree
                maxDegreeIdx = idx
            }
        }
        val tmp = convHull.verts[maxDegreeIdx]
        convHull.verts[maxDegreeIdx] = convHull.verts[convHull.verts.size - 1]
        convHull.verts[convHull.verts.size - 1] = tmp

        var packing = PackData(null)  //packData is class that holds the packing information, combinatorics, etc..

        // Create a map from a vertex to an index
        var vtoi = mutableMapOf<DCEL<DiskS2, Unit, Unit>.Vertex, Int> ()
        for ( i in 0..convHull.verts.lastIndex ) {
            vtoi[convHull.verts[i]] = i+1
        }

        // Set nodeCount equal to number of vertices
        packing.nodeCount = convHull.verts.size

        // Set rData (array containing radii)
        for ( i in 0 .. convHull.verts.lastIndex )
            packing.rData[i+1].rad = 1.0


        // Set kData (the array with combinatoric information, in particular, the flower: the counterclockwise list
        // of neighbors to each vertex
        packing.kData = arrayOfNulls<KData>(convHull.verts.size+1)

        for (i in 0..convHull.verts.lastIndex) {

            val v = convHull.verts[i]
            val inDarts = v.inDarts()

            packing.kData[i+1] = KData()
            packing.kData[i+1].num = inDarts.size
            packing.kData[i+1].bdryFlag = 0

            packing.kData[i+1].flower = IntArray(inDarts.size+1)

            for (j in 0..inDarts.lastIndex) {
                val other = inDarts[j].origin

                packing.kData[i+1].flower[j] = vtoi[other] as Int
            }
            // add the first vertex again to indiciate a cycle
            packing.kData[i+1].flower[inDarts.size] = vtoi[inDarts[0].origin] as Int
        }

        // Spherical Geometry
        packing.hes = 1


        // After inputting data, must call
        packing.setCombinatorics()

        // Determine how many iterations N you want in the radius computation, then call repack_call
        // new centers and radii should be stored in packing
        //packing.repack_call(1000,false,false);
        this.repack_call(packing, 1000, false)

        for (i in 1..convHull.verts.size) {
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
        }

        return packing
    }

    /**
     * Function to create a circle pack using the gradient
     */
    fun gradientPackOnePass(convHull: ConvexHull<DiskS2>, delta:Double) {
        for (v in convHull.verts) {
            var a = v.data.a
            var b = v.data.b
            var c = v.data.c
            var d = v.data.d
            var updateA = 0.0
            var updateB = 0.0
            var updateC = 0.0
            var updateD = 0.0
            val normC1 = norm31(a,b,c,d);
            for (ver in v.neighbors()) {
                val a2 = ver.data.a
                val b2 = ver.data.b
                val c2 = ver.data.c
                val d2 = ver.data.d
                val normC2 = norm31(a2, b2, c2, d2);
                val dotProduct = inner_product31(a,b,c,d,a2,b2,c2,d2);
                val inverDist = (a*a2 + b*b2 + c*c2 - d*d2)/
                        (Math.sqrt(a*a + b*b + c*c - (d*d)) *Math.sqrt(a2*a2 + b2*b2 + c2*c2 - (d2*d2)))
                updateA += (1-(a/(normC1*normC2) - (a2*(dotProduct))/(normC1*(Math.pow(normC2,3.0)))))*inverDist
                updateB += (1-(b/(normC1*normC2) - (b2*(dotProduct))/(normC1*(Math.pow(normC2,3.0)))))*inverDist;
                updateC += (1-(c/(normC1*normC2) - (c2*(dotProduct))/(normC1*(Math.pow(normC2,3.0)))))*inverDist;
                updateD += (1-(-d/(normC1*normC2) + (d2*(dotProduct))/(normC1*(Math.pow(normC2,3.0)))))*inverDist;
            }
            println("updateA: " + updateA * delta)
            println("updateB: " + updateB * delta)
            println("updateC: " + updateC * delta)
            println("updateD: " + updateD * delta)
            v.data = DiskS2(a + delta*updateA, b + delta*updateB, c + delta*updateC, d + delta*updateD)
        }
    }

    // This comes from Ken Stephenson's CirclePack, but we needed to edit without actually editing:
    fun repack_call(packing: PackData, passes: Int, useC: Boolean): Int {

        val sphpack = SphPacker(packing, useC)
        return  sphpack.maxPack(packing.nodeCount, passes)
    }

}

class SphPacker : RePacker {

    // Constructors
    constructor(pd: PackData, pass_limit: Int) : super(pd, pass_limit, false) { // pass_limit suggests using Java methods
    }

    constructor(pd: PackData, useC: Boolean) : super(pd, CPBase.RIFFLE_COUNT, useC) {}

    constructor(pd: PackData) : super(pd, CPBase.RIFFLE_COUNT, true) {}

    /**
     * Abstract methods not yet used here.
     */
    override fun load(): Int {
        return 1
    }

    override fun startRiffle(): Int {
        return 1
    }

    override fun reStartRiffle(passNum: Int): Int {
        return 1
    }

    override fun continueRiffle(passNum: Int): Int {
        return 1
    }

    override fun l2quality(crit: Double): Double {
        return 1.0
    }

    override fun reapResults() {}

    override fun setSparseC(useC: Boolean) { }

    /**
     * Generic call: use Java or C depending on what's available and preferred.
     * @param puncture_v, vertex to puncture if Java is used; if <=0, default to
     * * antipodal to 'alpha'
     * *
     * @param puncture_v int; if -1, default
     * *
     * @param cycles, repacking cycles if Java is used
     * *
     * @return
     */
    fun maxPack(puncture_v: Int, cycles: Int): Int {
        return maxPackJ_Sph(puncture_v, cycles)
    }

    /**
     * Spherical repack in Java (used if packing small or there is
     * no C library). Caution: if puncture_v<=0, choose puncture antipodal
     * to alpha. NOTE: This can be poor choice, so try to set v if results
     * are bad.
     * @param puncture_v int, if <=0, choose antipodal to alpha
     * *
     * @param cycles int, repack cycles
     * *
     * @return cycle count.
     */
    fun maxPackJ_Sph(puncture_v: Int, cycles: Int): Int {
        var puncture_v = puncture_v
        p.hes = 1
        if (puncture_v <= 0) puncture_v = p.antipodal_vert(p.alpha)
        if (puncture_v <= 0)
            throw RuntimeException("improper puncturing vertex")

        // save lists to restore later
        holdLists(p)

        // Method: puncture, max_pack in unit disc, project, then
        //   normalize. Note: swapping, so puncture is 'nodeCount',
        //   allows indices to be kept in tact.
        if (puncture_v != p.nodeCount) {
            p.swap_nodes(puncture_v, p.nodeCount)
        }
        if (p.puncture_vert(p.nodeCount) == 0) {
            // System.err.println("error in max pack puncture");
            throw CombException("puncture failed")
        }

        // max_pack in the disc
        p.geom_to_h()
        p.geometry = -1
        p.setCombinatorics()
        p.set_aim_default()

        // set bdry radii to 5.0 (preferrable to -.1 which can
        //   mess up placement of degree 2 bdry circles.
        try {
            CommandStrParser.jexecute(p, "set_rad 5.0 b")
        } catch (ex: Exception) {
        }

        val count = p.repack_call(cycles)
        p.fillcurves()
        try {
            p.comp_pack_centers(false, false, 2, CommandStrParser.LAYOUT_THRESHOLD)
        } catch (ex: IOException) {
        }

        // now project to the sphere and apply NSpole (tangency version)
        // TODO Comment these
        //CommandStrParser.jexecute(p, "geom_to_s") // TODO John proj
        //CommandStrParser.jexecute(p, "add_ideal")

        // TODO: continued problems (8/2016) with normalizations
        //		  double ctrd=NSpole.NSCentroid(p,1); // mode=2, tangency version
        //		  if (ctrd<-1.9) // error
        //			  CirclePack.cpb.errMsg("NSPole: tangency method failed");
        //		  else if (ctrd<0.0)
        //			  CirclePack.cpb.errMsg("NSPole: tangency method didn't give expected small error");

        p.proj_max_to_s(0, 1.0) // old action (but reliable)
        if (puncture_v != p.nodeCount) {
            p.swap_nodes(puncture_v, p.nodeCount)
            p.setCombinatorics()
        }
        restoreLists(p)
        return count
    }

    companion object {

        val SPH_GOPACK_THRESHOLD = 501  // for smaller packs, default to Java
    }

}