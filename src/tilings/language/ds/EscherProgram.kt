package tilings.language.ds

import geometry.ds.dcel.DCEL
import org.python.antlr.ast.Str
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EscherProgram () {

    val protoTiles : MutableMap<String, DCEL<Unit, Unit, String>>
    val subdivisions : MutableMap<String, Subdivision>
    var mainGraph : DCEL<Unit, Unit, String>

    val destinations : MutableMap<DCEL<Unit, Unit, String>.Dart, DCEL<Unit, Unit, String>.Vertex>

    init {
        protoTiles = mutableMapOf()
        subdivisions = mutableMapOf()
        mainGraph = DCEL()

        destinations = mutableMapOf()
    }

    fun addChild (subName: String, childType : String, vertices : ArrayList<String> ) {
        subdivisions[subName]?.addChild(childType, vertices)
    }

    fun addSplit (name : String, vertex1 : Int, vertex2 : Int, numEdges : Int) {
        subdivisions[name]?.addSplit(vertex1 = vertex1, vertex2 = vertex2, numEdges = numEdges)
    }

    fun addVertex (subName : String, vertexName : String) {
        subdivisions[subName]?.addVertex(vertexName)
    }

    fun defineProtoTile (name : String, numVerts : Int) : Boolean {

        if (protoTiles.containsKey(name)) {
            return false
        }

        val proto = DCEL<Unit, Unit, String>()
        val verts = ArrayList<DCEL<Unit, Unit, String>.Vertex>()
        val darts = ArrayList<DCEL<Unit, Unit, String>.Dart>()
        val face = proto.Face(data = name)


        //Inner Darts
        for (k in 0..numVerts-1) {
            verts.add(proto.Vertex(data = Unit))
            if (k > 0) {
                darts.add(proto.Dart(origin = verts[verts.size-2], face = face))
                //darts.add(proto.Dart(origin = verts[verts.size-1], face = proto.outerFace))
            }
        }
        darts.add(proto.Dart(origin = verts[verts.size-1], face = face))

        //Outer Darts
        for (k in 0..numVerts-1) {
            //destinations.put(darts[k], verts[(k+1) % numVerts]) // setting inner dart destinations
            darts[k].makeNext(darts[(k+1) % numVerts])
            darts.add(proto.Dart(origin = verts[k], face = proto.outerFace))
            //destinations.put(darts[darts.size-1], verts[(numVerts - 1 + k) % numVerts])
            darts[k].makeTwin(darts[darts.size-1])
            if (k > 0) {
                darts[darts.size-2].makePrev(darts[darts.size-1])
            }
        }
        darts[darts.size-1].makePrev(darts[numVerts])

        //Anchor
        face.aDart = darts[0]

        protoTiles.put(name, proto)

        return true
    }

    fun defineSubdivision (name : String) {
        subdivisions.put(name, Subdivision())
    }

    fun subdivide (proto : String, iter : Int) {

        mainGraph = copyProtoTile(protoTiles[proto]!!)

        for (k in 0..iter) {
            subdivideMain()
        }

    }

    private fun checkSplit (split : Triple<Int, Int, Int>, face: DCEL<Unit, Unit, String>.Face)
            : ArrayList<DCEL<Unit, Unit, String>.Vertex> {

        var targetDart  = face.aDart
        var distance = 0
        var noSplit = false
        val splitVerts = ArrayList<DCEL<Unit, Unit, String>.Vertex>()

        val source = findVertex(split.first, face)
        val goal = findVertex(split.second, face)
        val targetDistance = split.third - 1

        for (dart in source.outDarts()) {
            if (destinations[dart] == goal) {
                targetDart = dart
                if (targetDart.twin!!.origin == goal && destinations[targetDart!!.twin] == source) {
                    noSplit = true
                }
            }
        }

        if (noSplit) {
            //Add Vertices for split
            for (k in 0..targetDistance) {
                splitVerts.add(mainGraph.Vertex(data = Unit))
            }
        }
        else {
            //TODO
            //Find the vertices from source to goal IN ORDER
            //Check that the path distance is equal to the target distance
            targetDart = targetDart!!.twin
            while (destinations[targetDart] != source) {
                splitVerts.add(destinations[targetDart]!!)
                targetDart = targetDart!!.next
            }

        }


        println(splitVerts.size)

        if (splitVerts.size != targetDistance) {
            println ("Error: Split size is not equal on both half edges.")
        }

        return splitVerts
    }

    private fun distFrom (source : DCEL<Unit, Unit, String>.Vertex, goal : DCEL<Unit, Unit, String>.Vertex,
                          twin : DCEL<Unit, Unit, String>.Dart) : Int {
        var distance = 0
        val face = twin.face
        val darts = face!!.darts()
        var currDart = darts.indexOf(twin)
        while (darts[currDart].origin != goal) {
            currDart++
            distance++
        }

        if (distance > darts.size / 2.0) {
            distance = darts.size - distance
        }

        return distance
    }

    private fun findVertex (idx : Int, face : DCEL<Unit, Unit, String>.Face) : DCEL<Unit, Unit, String>.Vertex {
        val darts = face.darts()

        return darts[idx % darts.size].origin!!

    }

    private fun copyProtoTile (proto : DCEL<Unit, Unit, String>) : DCEL<Unit, Unit, String>{
        val copy = DCEL<Unit, Unit, String>()

        // Set Vertices
        for (k in 0..proto.verts.size-1) {
            copy.Vertex( data = Unit )
        }

        // Set Faces
        for (k in 0..proto.faces.size-1) {
            copy.Face( data = proto.faces[k].data )
        }

        // Set Half Edges
        for (k in 0..proto.darts.size-1) {


            if (proto.darts[k].face == proto.outerFace) {
                copy.Dart(origin = copy.verts[proto.verts.indexOf(proto.darts[k].origin)],
                        face = copy.outerFace)
            } else {
                copy.Dart(origin = copy.verts[proto.verts.indexOf(proto.darts[k].origin)],
                        face = copy.faces[proto.faces.indexOf(proto.darts[k].face)])
            }

            if (k < proto.verts.size) {
                destinations.put(copy.darts[copy.darts.lastIndex], copy.verts[(k + 1) % copy.verts.lastIndex])
            }
            else {
                destinations.put(copy.darts[copy.darts.lastIndex], copy.verts[(proto.verts.lastIndex + k - 1) % copy.verts.lastIndex])
            }

        }

        for (k in 0..proto.darts.size-1) {
            copy.darts[k].makeTwin(copy.darts[proto.darts.indexOf(proto.darts[k].twin)])
            copy.darts[k].makeNext(copy.darts[proto.darts.indexOf(proto.darts[k].next)])
            copy.darts[k].makePrev(copy.darts[proto.darts.indexOf(proto.darts[k].prev)])
        }

        // Set Anchor Points
        // Set Faces
        for (k in 0..proto.faces.size-1) {
            copy.faces[k].aDart = copy.darts[proto.darts.indexOf(proto.faces[k].aDart)]
        }


        return copy
    }

    private fun makeChild (subdivision: Subdivision, childFace : ArrayList<DCEL<Unit, Unit, String>.Face>,
                           oldDarts : List<DCEL<Unit, Unit, String>.Dart>,
                           newVerts : ArrayList<DCEL<Unit, Unit, String>.Vertex>) {
        //TODO
        var currFace : DCEL<Unit, Unit, String>.Face
        var childVerts = ArrayList<DCEL<Unit, Unit, String>.Vertex>()
        var splitVerts = ArrayList<DCEL<Unit, Unit, String>.Vertex>()
        var childDarts = ArrayList<DCEL<Unit, Unit, String>.Dart>()
        for (child in subdivision.children) {
            for (face in childFace) {
                if (child.first == face.data) {

                    // Group child vertices
                    for (vert in child.second) {
                        if (vert.toIntOrNull() != null && vert.toInt() >= 0 ) {
                            childVerts.add(findVertex(vert.toInt(), oldDarts[0].face!!))
                        }
                        else {
                            childVerts.add(newVerts[subdivision.vertices.indexOf(vert)])
                            if (subdivision.vertices.indexOf(vert) < subdivision.splitVerts) {
                                splitVerts.add(newVerts[subdivision.vertices.indexOf(vert)])
                            }
                        }
                    }

                    // Attach child vertices
                    for (k in 0..childVerts.size-1) {
                        childDarts.add(mainGraph.Dart(origin = childVerts[k], face = face))
                        destinations.put(childDarts[childDarts.size-1], childVerts[(k+1) % childVerts.size])
                        if (k > 0) {
                            childDarts[childDarts.size-1].makePrev(childDarts[childDarts.size-2])
                        }

                    }
                    childDarts[0].makePrev(childDarts[childDarts.size-1])

                    // Make twins for split darts so we can check for splits in adj face
                    for (k in 0..childDarts.size-1) {
                        if (!splitVerts.contains(childDarts[0].origin) &&
                                splitVerts.contains(destinations[childDarts[0]]!!)) {
                            for (dart in oldDarts) {
                                if (dart.origin == childDarts[0].origin) {
                                    childDarts[k].makeTwin(dart.twin)
                                }
                            }
                        }
                    }

                }
                splitVerts.clear()
                childDarts.clear()
                childVerts.clear()
            }
        }
    }

    private fun subdivideFace (currFace : DCEL<Unit, Unit, String>.Face) {
        val subdivision = subdivisions[currFace.data]
        val darts = currFace.darts()

        val newVerts = ArrayList<DCEL<Unit, Unit, String>.Vertex>()
        val edgeVerts = ArrayList<DCEL<Unit, Unit, String>.Vertex>()
        val interiorVerts = ArrayList<DCEL<Unit, Unit, String>.Vertex>()
        val childFaces = ArrayList<DCEL<Unit, Unit, String>.Face>()


        // Adding Vertices
        for (split in subdivision!!.splits) {
            for (k in 1..split.third) {
                newVerts.addAll(checkSplit(split, currFace))
                //edgeVerts.add(mainGraph.Vertex(data = Unit))
                //newVerts.add(edgeVerts[edgeVerts.size-1])
            }
        }

        for (k in edgeVerts.size..subdivision.vertices.size) {
            interiorVerts.add(mainGraph.Vertex(data = Unit))
            newVerts.add(interiorVerts[interiorVerts.size-1])
        }

        //Adding Faces
        for (child in subdivision.children) {
            childFaces.add(mainGraph.Face(data = child.first))
            makeChild(subdivision, childFaces, darts, newVerts)
        }

        // Remove old darts
        for (dart in darts) {
            mainGraph.darts.remove(dart)
        }

        // Remove old face
        mainGraph.faces.remove(currFace)


    }

    private fun subdivideMain () {
        for (k in 0..mainGraph.faces.lastIndex) {
            subdivideFace(mainGraph.faces[k])
        }
    }

}