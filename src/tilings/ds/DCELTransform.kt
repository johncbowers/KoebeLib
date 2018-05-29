package tilings.ds

import dcel.*
import geometry.ds.dcel.DCEL
import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.Spherical2.DiskS2
import komplex.KData
import packing.PackData
import geometry.algorithms.CirclePack
import panels.CPScreen
import rePack.EuclPacker
import rePack.SphPacker
import sketches.SphericalSketch
import sketches.SphericalSketchTestbed
import tilings.algorithms.triangulateDCEL

fun test () {

    val points = ArrayList<PointE2>()
    val packing = PackData(null)

    points.add(PointE2(x = 100.0, y = 100.0))
    points.add(PointE2(x = 300.0, y = 100.0))
    points.add(PointE2(x = 500.0, y = 100.0))
    points.add(PointE2(x = 500.0, y = 300.0))
    points.add(PointE2(x = 300.0, y = 300.0))
    points.add(PointE2(x = 300.0, y = 500.0))
    points.add(PointE2(x = 100.0, y = 500.0))
    points.add(PointE2(x = 100.0, y = 300.0))

    val tile = ChairTile(points)
    var graph = tile.graph

    val combinatorics = DCEL<DiskS2, Unit, Unit>()
    getCombinatorics(graph, combinatorics)
    for (k in 0..((combinatorics.darts.size)/2)-1) {
        combinatorics.Edge(data = Unit)
    }

    val darts = ArrayList<DCEL<DiskS2, Unit, Unit>.Dart>()
    var i = 0
    combinatorics.Face(data = Unit)
    for (dart in combinatorics.darts) {
        if (!darts.contains(dart)) {
            dart.edge = combinatorics.edges[i]
            dart.twin!!.edge = combinatorics.edges[i]
            i++
            darts.add(dart)
            darts.add(dart.twin!!)
        }

        if (dart.face == combinatorics.outerFace) {
            dart.face = combinatorics.faces[combinatorics.faces.size-1]
            combinatorics.faces[combinatorics.faces.size-1].aDart = dart
        }
    }


    val sk = SphericalSketch()

    sk.viewSettings.showSphere = true
    sk.viewSettings.showDualPoint = false
    sk.viewSettings.showBoundingBox = false
    sk.viewSettings.showCircleCentersAndNormals = false

    val cp = CirclePack()

    sk.objects.add(cp)

    cp.pack(combinatorics)

//    packing.alloc_pack_space(combinatorics.verts.size, true)
//    packing.status = true
//
//    var vtoi = mutableMapOf<DCEL<DiskS2, Unit, Unit>.Vertex, Int> ()
//    for ( i in 0..combinatorics.verts.lastIndex ) {
//        vtoi[combinatorics.verts[i]] = i+1
//    }
//
//    packing.nodeCount = combinatorics.verts.size
//
//    for ( i in 0 .. combinatorics.verts.lastIndex )
//        packing.rData[i+1].rad = 1.0
//
//    packing.kData = arrayOfNulls<KData>(combinatorics.verts.size+1)
//
//    for (i in 0..combinatorics.verts.lastIndex) {
//
//        val v = combinatorics.verts[i]
//        val inDarts = v.inDarts()
//
//        packing.kData[i+1] = KData()
//        packing.kData[i+1].num = inDarts.size
//        packing.kData[i+1].bdryFlag = 0
//
//        packing.kData[i+1].flower = IntArray(inDarts.size+1)
//
//        for (j in 0..inDarts.lastIndex) {
//            val other = inDarts[j].origin
//
//            packing.kData[i+1].flower[j] = vtoi[other] as Int
//        }
//        // add the first vertex again to indiciate a cycle
//        packing.kData[i+1].flower[inDarts.size] = vtoi[inDarts[0].origin] as Int
//    }
//
//    // Spherical Geometry
//    packing.hes = 1
//
//
//    // After inputting data, must call
//    //val screen = CPScreen()
//    //packing.cpScreen = screen
//    packing.setCombinatorics()
//    packing.setGamma(packing.nodeCount)
//
//    val packer = SphPacker(packing, true)
//    packer.maxPack(packing.nodeCount, 1000)
//
//    for (i in 1..combinatorics.verts.size) {
//        val rData = packing.rData[i]
//        val center = rData.center
//        val rad = rData.rad
//        val theta = center.x
//        val phi = center.y
//        val radScale = Math.cos(rad)
//        val a = radScale*Math.cos(theta)* Math.sin(phi)
//        val b = radScale*Math.sin(theta) * Math.sin(phi)
//        val c = radScale*Math.cos(phi)
//        val disk = DiskS2(a, b, c, -(a*a + b*b + c*c))
//        combinatorics.verts[i-1].data = disk
//    }

}

fun getCombinatorics (graph : DCEL<PointE2, Unit, Unit>, comb : DCEL<DiskS2, Unit, Unit>) {

    // Set Vertices
    for (k in 0..graph.verts.size-1) {
        comb.Vertex( data = DiskS2(0.0, 0.0, 1.0, 0.0) )
    }

    // Set Faces
    for (k in 0..graph.faces.size-1) {
        comb.Face( data = Unit )
    }

    // Set Half Edges
    for (k in 0..graph.darts.size-1) {


        if (graph.darts[k].face == graph.outerFace) {
            comb.Dart(origin = comb.verts[graph.verts.indexOf(graph.darts[k].origin)],
                    face = comb.faces[0]/*comb.outerFace*/)
        } else {
            comb.Dart(origin = comb.verts[graph.verts.indexOf(graph.darts[k].origin)],
                    face = comb.faces[graph.faces.indexOf(graph.darts[k].face)])
        }

    }

    for (k in 0..graph.darts.size-1) {
        comb.darts[k].makeTwin(comb.darts[graph.darts.indexOf(graph.darts[k].twin)])
        comb.darts[k].makeNext(comb.darts[graph.darts.indexOf(graph.darts[k].next)])
        comb.darts[k].makePrev(comb.darts[graph.darts.indexOf(graph.darts[k].prev)])
    }

    // Set Anchor Points
    // Set Faces
    for (k in 0..graph.faces.size-1) {
        comb.faces[k].aDart = comb.darts[graph.darts.indexOf(graph.faces[k].aDart)]
    }






}

class DCELTransform<VertexData, EdgeData, FaceData> () {

    var packing : PackData

    init {
        packing = PackData(null)
    }

    fun oursToKens(ourDCEL : DCEL<VertexData, EdgeData, FaceData>) : PackDCEL {

        var boquet1 = Array(ourDCEL.verts.size+1, {IntArray(ourDCEL.verts.size+1) {0}})
        var boquet2 = ArrayList<ArrayList<Int>>()
        var vtoi = mutableMapOf<DCEL<VertexData, EdgeData, FaceData>.Vertex, Int> ()
        for ( i in 0..ourDCEL.verts.lastIndex ) {
            vtoi[ourDCEL.verts[i]] = i+1
        }

        var neighbors : List<DCEL<VertexData, EdgeData, FaceData>.Vertex>
        for (k in 0..ourDCEL.verts.size-1) {
            neighbors = ourDCEL.verts[k].neighbors()
            boquet2.add(ArrayList())
            for (neighbor in neighbors) {
                boquet1[vtoi[ourDCEL.verts[k]]!!][vtoi[neighbor]!!] = 1
                boquet2[k].add(vtoi[neighbor]!!)
            }

            if (isInterior(ourDCEL.verts[k], ourDCEL)) {
                boquet2[k].add(vtoi[neighbors[0]]!!)
            }
        }

        var kens = PackDCEL(toArray(boquet2))
        println()
        return kens
    }

    private fun isInterior(vert : DCEL<VertexData, EdgeData, FaceData>.Vertex, dcel : DCEL<VertexData, EdgeData, FaceData>) : Boolean {

        var inDarts = vert.inDarts()
        var outDarts = vert.outDarts()

        for (dart in inDarts) {
            if (dart.face == dcel.outerFace) {
                return false
            }
        }

        for (dart in outDarts) {
            if (dart.face == dcel.outerFace) {
                return false
            }
        }

        return true
    }

    private fun toArray(list : ArrayList<ArrayList<Int>>) : Array<out IntArray>{
        var out = ArrayList<IntArray>()
        //var a : Array<out IntArray>
        //out.add(IntArray(1, {0}))
        for (vert in list) {
            out.add(IntArray(vert.size, {0}))
            for (k in 0..vert.size-1) {
                out[out.size-1][k] = vert[k]
            }
        }

        var a = out.toTypedArray()
        println()
        return out.toTypedArray()
    }

}



fun main (passedArgs : Array<String>) {
//    val points = ArrayList<PointE2>()
//    val packing = PackData(null)

//    points.add(PointE2(x = 100.0, y = 100.0))
//    points.add(PointE2(x = 300.0, y = 100.0))
//    points.add(PointE2(x = 500.0, y = 100.0))
//    points.add(PointE2(x = 500.0, y = 300.0))
//    points.add(PointE2(x = 300.0, y = 300.0))
//    points.add(PointE2(x = 300.0, y = 500.0))
//    points.add(PointE2(x = 100.0, y = 500.0))
//    points.add(PointE2(x = 100.0, y = 300.0))
//
//    val tile = ChairTile(points)
//    var graph = tile.graph
//    triangulateDCEL(graph)
//    val transformer = DCELTransform<PointE2, Unit, Unit>()
//    transformer.oursToKens(graph)

    test()
}