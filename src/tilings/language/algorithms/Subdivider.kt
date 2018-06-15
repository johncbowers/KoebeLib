package tilings.language.algorithms

import geometry.ds.dcel.DCEL
import tilings.ds.EdgeData
import tilings.ds.FaceData
import tilings.ds.VertexData
import tilings.language.ds.EscherProgramNew
import tilings.language.ds.Subdivision

class Subdivider (program: EscherProgramNew, graph : DCEL<VertexData, EdgeData, FaceData>, iter : Int) {

    val program : EscherProgramNew
    val graph : DCEL<VertexData, EdgeData, FaceData>
    val iter : Int

    val splitMap : MutableMap<Pair<DCEL<VertexData, EdgeData, FaceData>.Vertex, DCEL<VertexData, EdgeData, FaceData>.Vertex>,
            ArrayList<DCEL<VertexData, EdgeData, FaceData>.Vertex>>
    val destination : MutableMap<DCEL<VertexData, EdgeData, FaceData>.Dart, DCEL<VertexData, EdgeData, FaceData>.Vertex>
    val lonelyDarts : ArrayList<DCEL<VertexData, EdgeData, FaceData>.Dart>
    val oldFaces : ArrayList<DCEL<VertexData, EdgeData, FaceData>.Face>

    init {
        this.program = program
        this.graph = graph
        this.iter = iter

        splitMap = mutableMapOf()
        destination = mutableMapOf()
        lonelyDarts = ArrayList()
        oldFaces = ArrayList()
    }

    fun start () {

        initialDestinations()

        for (i in 1..iter) {
            val size = graph.faces.size
            for (k in 0..size - 1) {
                subdivideFace(graph.faces[k])
            }
            pairLonelyDarts()
            removeOldDarts()
            splitMap.clear()
        }
        println()
    }

    private fun checkSplit (split : Triple<Int, Int, Int>, face : DCEL<VertexData, EdgeData, FaceData>.Face) :
        ArrayList<DCEL<VertexData, EdgeData, FaceData>.Vertex> {
        val source = findVertex(split.first, face)
        val goal = findVertex(split.second, face)

        val splitVerts = ArrayList<DCEL<VertexData, EdgeData, FaceData>.Vertex>()

        if (splitMap.containsKey(Pair(source, goal))) {
            return splitMap[Pair(source, goal)]!!
        }

        if (splitMap.containsKey(Pair(goal, source))) {
            splitVerts.addAll(splitMap[Pair(goal, source)]!!)
            splitVerts.reverse()
            return splitVerts
        }


        for (k in 2..split.third) {
            splitVerts.add(graph.Vertex(data = VertexData()))
        }

        splitMap.put(Pair(source, goal), splitVerts)
        return splitVerts

    }

    private fun findVertex (idx : Int, face : DCEL<VertexData, EdgeData, FaceData>.Face)
            : DCEL<VertexData, EdgeData, FaceData>.Vertex {
        val darts = face.darts()

        return darts[idx % darts.size].origin!!

    }

    private fun initialDestinations () {
        for (k in 0..graph.darts.size-1) {

            if (k < graph.verts.size-1) {
                destination.put(graph.darts[k], graph.verts[(k+1) % graph.verts.size])
            }
            else {
                destination.put(graph.darts[k], graph.verts[(graph.verts.size + k - 1) % graph.verts.size])
            }
        }
    }

    private fun makeChild (subdivision: Subdivision, childFace : ArrayList<DCEL<VertexData, EdgeData, FaceData>.Face>,
                           oldDarts : List<DCEL<VertexData, EdgeData, FaceData>.Dart>,
                           newVerts : ArrayList<DCEL<VertexData, EdgeData, FaceData>.Vertex>) {
        var childVerts = ArrayList<DCEL<VertexData, EdgeData, FaceData>.Vertex>()
        var childDarts = ArrayList<DCEL<VertexData, EdgeData, FaceData>.Dart>()
        for (j  in 0..subdivision.children.size-1) {

            // Group child vertices
            for (vert in subdivision.children[j].second) {
                if (vert.toIntOrNull() != null && vert.toInt() >= 0 ) {
                    childVerts.add(findVertex(vert.toInt(), oldDarts[0].face!!))
                }
                else {
                    childVerts.add(newVerts[subdivision.vertices.indexOf(vert)])
                }
                println()
            }

            // connect child vertices
            for (k in 0..childVerts.size-1) {
                childDarts.add(graph.Dart(origin = childVerts[k], face = childFace[j]))

                lonelyDarts.add(childDarts[childDarts.size-1])
                destination.put(childDarts[childDarts.size-1], childVerts[(k+1) % childVerts.size])
                if (k > 0) {
                    childDarts[childDarts.size-1].makePrev(childDarts[childDarts.size-2])
                }

            }
            childDarts[0].makePrev(childDarts[childDarts.size-1])
            childFace[j].aDart = childDarts[0]

            childDarts.clear()
            childVerts.clear()
        }

    }

    private fun pairLonelyDarts () {

        val lonlierDarts = ArrayList<DCEL<VertexData, EdgeData, FaceData>.Dart>()
        val vertMap = mutableMapOf<DCEL<VertexData, EdgeData, FaceData>.Vertex, Int>()
        var i = 0
        for (vert in graph.verts) {
            vertMap.put(vert, i)
            i++
        }

        while (lonelyDarts.size > 0) {
            val dart1 = lonelyDarts[0]
            var removed = false
            for (k in 1..lonelyDarts.size-1) {
                if (dart1!!.origin == destination[lonelyDarts[k]] &&
                        lonelyDarts[k]!!.origin == destination[dart1]) {
                    dart1.makeTwin(lonelyDarts[k])
                    //println("Origin: " + vertMap[dart1.origin] + " Destination: " + vertMap[destination[dart1]])
                    //println("Origin: " + vertMap[lonelyDarts[k].origin] + " Destination: " + vertMap[destination[lonelyDarts[k]]])
                    lonelyDarts.remove(lonelyDarts[k])
                    lonelyDarts.remove(dart1)
                    removed = true
                    break
                }
            }
            if (!removed) {
                lonlierDarts.add(dart1)
                //println("Origin: " + vertMap[dart1.origin] + " Destination: " + vertMap[destination[dart1]])
                lonelyDarts.remove(dart1)

            }
            removed = false
        }
        println(lonlierDarts.size)
        val outsideDarts = ArrayList<DCEL<VertexData, EdgeData, FaceData>.Dart>()
        for (dart in lonlierDarts) {
            outsideDarts.add(graph.Dart(origin = destination[dart], face = graph.outerFace))
            destination.put(outsideDarts[outsideDarts.size-1], dart.origin!!)
            dart.makeTwin(outsideDarts[outsideDarts.size-1])
        }
        for (dart1 in outsideDarts) {
            for (dart2 in outsideDarts) {
                if (destination[dart1] == dart2.origin) {
                    dart1.makeNext(dart2)
                }
            }
        }
    }

    private fun removeOldDarts () {
        for (face in oldFaces) {
            graph.faces.remove(face)
            for (dart in face.darts()) {
                if (dart.twin!!.face == graph.outerFace) {
                    graph.darts.remove(dart!!.twin)
                }
                graph.darts.remove(dart)
            }
        }
        oldFaces.clear()
    }

    private fun subdivideFace (face : DCEL<VertexData, EdgeData, FaceData>.Face) {
        val subdivision = program.subdivisions[face.data.tileType]
        val darts = face.darts()

        val newVerts = ArrayList<DCEL<VertexData, EdgeData, FaceData>.Vertex>()
        val childFaces = ArrayList<DCEL<VertexData, EdgeData, FaceData>.Face>()


        // Adding Vertices
        for (split in subdivision!!.splits) {
            newVerts.addAll(checkSplit(split, face))
        }

        for (k in subdivision.splitVerts..subdivision.vertices.size-1) {
            //println (k)
            newVerts.add(graph.Vertex(data = VertexData()))
        }

        //Adding Faces
        for (child in subdivision.children) {
            childFaces.add(graph.Face(data = FaceData()))
            childFaces[childFaces.size-1].data.tileType = child.first
        }
        makeChild(subdivision, childFaces, darts, newVerts)

        val vertMap = mutableMapOf<DCEL<VertexData, EdgeData, FaceData>.Vertex, Int>()
        var i = 0
        for (vert in graph.verts) {
            vertMap.put(vert, i)
            i++
        }

        // Remove old face
        oldFaces.add(face)


    }
}
