package tilings.algorithms

import dcel.*
import geometry.ds.dcel.DCEL
import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.Spherical2.DiskS2
import packing.PackData
import geometry.algorithms.CirclePack
import geometry.algorithms.IncrementalConvexHullAlgorithms
import geometry.ds.dcel.DCELH
import tilings.ds.ChairTile
import tilings.ds.PentagonalTwistTile
import tilings.language.algorithms.TileFactory

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

    val ICH = IncrementalConvexHullAlgorithms()
    val hull = ICH.randomConvexHullDiskS2WithHighDegreeVertex(8, 2)
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
            combinatorics.edges[i].aDart = dart
            i++
            darts.add(dart)
            darts.add(dart.twin!!)
        }

        if (dart.face == combinatorics.outerFace) {
            dart.face = combinatorics.faces[combinatorics.faces.size-1]
            combinatorics.faces[combinatorics.faces.size-1].aDart = dart
        }
    }


//    val sk = SphericalSketch()
//
//    sk.viewSettings.showSphere = true
//    sk.viewSettings.showDualPoint = false
//    sk.viewSettings.showBoundingBox = false
//    sk.viewSettings.showCircleCentersAndNormals = false

    val cp = CirclePack()

//    sk.objects.add(cp)

    cp.pack(hull)
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
                    face = /*comb.faces[0]*/comb.outerFace)
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

fun makeSphere(graph: DCEL<PointE2, Unit, Unit>) {
    val newVert = graph.Vertex( data = PointE2(0.0, 0.0))
    graph.verts.removeAt(graph.verts.lastIndex)
    val newFaces = ArrayList<DCEL<PointE2, Unit, Unit>.Face>()
    val bdryVerts = ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>()
    val bdryDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
    val newDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
    var vtoi = mutableMapOf<DCEL<PointE2, Unit, Unit>.Vertex, Int>()
    for ( i in 0..graph.verts.lastIndex ) {
        vtoi[graph.verts[i]] = i+1
    }

    println ("Finding Bdry Darts")
    for (dart in graph.darts) {
        if (dart.face == graph.outerFace) {
            bdryDarts.add(dart)
        }
    }
    println ("Done Finding Bdry Darts")

    println ("Adding New Darts")
    for (vert in graph.verts) {
        if (!isInterior(vert, graph)) {
            println("WE HAVE A BDRY VERT")
            bdryVerts.add(vert)
            newDarts.add(graph.Dart(origin = newVert))
            newDarts.add(graph.Dart(origin = vert))

            newDarts[newDarts.size-1].makeTwin(newDarts[newDarts.size-2])

            newFaces.add(graph.Face(data = Unit))
        }
    }
    println ("Done Adding New Darts")

    graph.verts.add(newVert)

    var nextDart : DCEL<PointE2, Unit, Unit>.Dart
    var prevDart : DCEL<PointE2, Unit, Unit>.Dart
    nextDart = bdryDarts[0] // Placeholder values
    prevDart = bdryDarts[0]
    var count = 0
    for (bdryDart in bdryDarts) {
        for (newDart in newDarts) {
            if (newDart.origin == bdryDart.dest) {
                bdryDart.makeNext(newDart)
                nextDart = newDart
                count++
            }

            if (newDart.dest == bdryDart.origin) {
                bdryDart.makePrev(newDart)
                prevDart = newDart
                count++
            }

            if (count == 2) {
                count = 0
                nextDart.makeNext(prevDart)
                newFaces[0].aDart = bdryDart
                newFaces.removeAt(0)
            }
        }
    }


}

fun isInterior(vert : DCEL<PointE2, Unit, Unit>.Vertex, dcel : DCEL<PointE2, Unit, Unit>) : Boolean {

    //println ("Starting In Darts")
    var inDarts = vert.inDarts()
    //println ("Starting Out Darts")
    var outDarts = vert.outDarts()

    //println ("Starting In Darts")
    /*for (dart in inDarts) {
        if (dart.face == dcel.outerFace) {
            return false
        }
    }*/
    //println ("Done With End Darts")

    //println ("Starting Out Darts")
    for (dart in outDarts) {
        if (dart.face == dcel.outerFace) {
            return false
        }
    }
    //println ("Done With Out Darts")

    return true
}

class DCELTransform<VertexData, EdgeData, FaceData> () {

    var packing : PackData
    var combinatorics : DCELH<DiskS2, Unit, Unit>

    init {
        packing = PackData(null)
        combinatorics = DCELH<DiskS2, Unit, Unit>()
    }

    fun findCombinatorics () : DCELH<DiskS2, Unit, Unit> {
        return  combinatorics
    }


    fun makeSphere(graph: DCELH<Unit, Unit, Unit>) {
        val newVert = graph.Vertex( data = Unit)
        graph.verts.removeAt(graph.verts.lastIndex)
        val newFaces = ArrayList<DCELH<Unit, Unit, Unit>.Face>()
        val bdryVerts = ArrayList<DCELH<Unit, Unit, Unit>.Vertex>()
        val bdryDarts = ArrayList<DCELH<Unit, Unit, Unit>.Dart>()
        val newDarts = ArrayList<DCELH<Unit, Unit, Unit>.Dart>()
        var vtoi = mutableMapOf<DCELH<Unit, Unit, Unit>.Vertex, Int>()
        for ( i in 0..graph.verts.lastIndex ) {
            vtoi[graph.verts[i]] = i+1
        }

        println ("Finding Bdry Darts")
        for (dart in graph.darts) {
            if (dart.face == graph.holes[0]) {
                bdryDarts.add(dart)
            }
        }
        println ("Done Finding Bdry Darts")

        println ("Adding New Darts")
        for (vert in graph.verts) {
            if (!isInterior(vert, graph)) {
                println("WE HAVE A BDRY VERT")
                bdryVerts.add(vert)
                newDarts.add(graph.Dart(origin = newVert))
                newDarts.add(graph.Dart(origin = vert))

                newDarts[newDarts.size-1].makeTwin(newDarts[newDarts.size-2])

                newFaces.add(graph.Face(data = Unit))
            }
        }
        println ("Done Adding New Darts")

        graph.verts.add(newVert)

        var nextDart : DCELH<Unit, Unit, Unit>.Dart
        var prevDart : DCELH<Unit, Unit, Unit>.Dart
        nextDart = bdryDarts[0] // Placeholder values
        prevDart = bdryDarts[0]
        var count = 0
        for (bdryDart in bdryDarts) {
            for (newDart in newDarts) {
                if (newDart.origin == bdryDart.dest) {
                    bdryDart.makeNext(newDart)
                    nextDart = newDart
                    count++
                }

                if (newDart.dest == bdryDart.origin) {
                    bdryDart.makePrev(newDart)
                    prevDart = newDart
                    count++
                }

                if (count == 2) {
                    count = 0
                    nextDart.makeNext(prevDart)
                    newFaces[0].aDart = bdryDart
                    newFaces.removeAt(0)
                }
            }
        }


    }

    //TODO
    /*fun oursToKens(ourDCEL : DCEL<VertexData, EdgeData, FaceData>) : PackDCEL {

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
    }*/

    fun toSphericalRepresentation (graph: DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>)
            : DCELH<DiskS2, Unit, Unit> {
        //TODO

        val triangulation = Triangulation<VertexData, EdgeData, FaceData>()
        val tileFactory = TileFactory<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>()
        val unitGraph = tileFactory.copyUnitTile(graph)
        triangulation.triangulateDCEL(unitGraph)

        this.makeSphere(unitGraph)
        toDiskDCEL(unitGraph, combinatorics)

        val cp = CirclePack()

        //cp.pack(hull)
        //packing = cp.pack(combinatorics)

        return  combinatorics
    }


    fun toDiskDCEL (graph : DCELH<Unit, Unit, Unit>, comb : DCELH<DiskS2, Unit, Unit>) {

        // Set Vertices
        for (k in 0..graph.verts.size-1) {
            comb.Vertex( data = DiskS2(0.0, 0.0, 1.0, 0.0) )
        }

        // Set Faces
        for (k in 0..graph.faces.size-1) {
            comb.Face( data = Unit )
        }

        val edge = comb.Edge(data = Unit)
        // Set Half Edges
        for (k in 0..graph.darts.size-1) {


            if (graph.darts[k].face == graph.holes[0]) {
                comb.Dart(origin = comb.verts[graph.verts.indexOf(graph.darts[k].origin)],
                        face = /*comb.faces[0]*/comb.holes[0])
            } else {
                comb.Dart(origin = comb.verts[graph.verts.indexOf(graph.darts[k].origin)],
                        face = comb.faces[graph.faces.indexOf(graph.darts[k].face)])
            }

            comb.darts[comb.darts.size-1].edge = edge

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

    /*fun test (num : Int) : DCEL<DiskS2, Unit, Unit> {

        val points = ArrayList<PointE2>()


        // Chair
        /*points.add(PointE2(x = 100.0, y = 100.0))
        points.add(PointE2(x = 300.0, y = 100.0))
        points.add(PointE2(x = 500.0, y = 100.0))
        points.add(PointE2(x = 500.0, y = 300.0))
        points.add(PointE2(x = 300.0, y = 300.0))
        points.add(PointE2(x = 300.0, y = 500.0))
        points.add(PointE2(x = 100.0, y = 500.0))
        points.add(PointE2(x = 100.0, y = 300.0))*/

        // Pentagon
        points.add(PointE2(x = 200.0, y = 100.0))
        points.add(PointE2(x = 400.0, y = 100.0))
        points.add(PointE2(x = 500.0, y = 400.0))
        points.add(PointE2(x = 300.0, y = 550.0))
        points.add(PointE2(x = 100.0, y = 400.0))

        //val tile = ChairTile(points)
        val tile = PentagonalTwistTile(points)

        for (k in 1..num) {
            tile.subdivide()
        }

        var graph = tile.graph

        println("Starting Triang")
        triangulateDCEL(graph)
        println("Finishing Triang")

        println("Starting Sphere")
        makeSphere(graph)
        println("Finishing Sphere")

        println("Starting Comb")
        combinatorics = DCELH<DiskS2, Unit, Unit>()
        getCombinatorics(graph, combinatorics)
        println("Finishing Comb")

        val ICH = IncrementalConvexHullAlgorithms()
        val hull = ICH.randomConvexHullDiskS2WithHighDegreeVertex(8, 2)
        for (k in 0..((combinatorics.darts.size)/2)-1) {
            combinatorics.Edge(data = Unit)
        }

        val darts = ArrayList<DCELH<DiskS2, Unit, Unit>.Dart>()
        var i = 0
        //combinatorics.Face(data = Unit)
        for (dart in combinatorics.darts) {
            if (!darts.contains(dart)) {
                dart.edge = combinatorics.edges[i]
                dart.twin!!.edge = combinatorics.edges[i]
                combinatorics.edges[i].aDart = dart
                i++
                darts.add(dart)
                darts.add(dart.twin!!)
            }

            /*if (dart.face == combinatorics.outerFace) {
                dart.face = combinatorics.faces[combinatorics.faces.size-1]
                combinatorics.faces[combinatorics.faces.size-1].aDart = dart
            }*/
        }

        val cp = CirclePack()

        //cp.pack(hull)
        packing = cp.pack(combinatorics)
        return  combinatorics
    }*/


    /*private fun isInterior(vert : DCEL<VertexData, EdgeData, FaceData>.Vertex, dcel : DCEL<VertexData, EdgeData, FaceData>) : Boolean {

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
    }*/

    fun isInterior(vert : DCELH<Unit, Unit, Unit>.Vertex, dcel : DCELH<Unit, Unit, Unit>) : Boolean {

        //println ("Starting In Darts")
        var inDarts = vert.inDarts()
        //println ("Starting Out Darts")
        var outDarts = vert.outDarts()

        //println ("Starting In Darts")
        for (dart in inDarts) {
            if (dart.face == dcel.holes[0]) {
                return false
            }
        }
        //println ("Done With End Darts")

        //println ("Starting Out Darts")
        for (dart in outDarts) {
            if (dart.face == dcel.holes[0]) {
                return false
            }
        }
        //println ("Done With Out Darts")

        return true
    }

    private fun toArray(list : ArrayList<ArrayList<Int>>) : Array<out IntArray>{
        var out = ArrayList<IntArray>()
        //var a : Array<out IntArray>
        out.add(IntArray(1, {0}))
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
    //triangulateDCEL(graph)
    val transformer = DCELTransform<PointE2, Unit, Unit>()
    //transformer.oursToKens(graph)
    //transformer.test(1)
    println()
    //test()
}