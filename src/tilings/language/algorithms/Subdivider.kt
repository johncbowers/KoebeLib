package tilings.language.algorithms

import geometry.ds.dcel.DCEL
import geometry.ds.dcel.DCELH
import tilings.ds.EdgeData
import tilings.ds.FaceData
import tilings.ds.TreeNode
import tilings.ds.VertexData
import tilings.language.ds.EscherProgramNew
import tilings.language.ds.Subdivision

class Subdivider (program: EscherProgramNew, graph : DCELH<VertexData, EdgeData, FaceData>, iter : Int) {

    val program : EscherProgramNew
    val graph : DCELH<VertexData, EdgeData, FaceData>
    val iter : Int
    var currentIteration : Int

    val splitMap : MutableMap<Pair<DCELH<VertexData, EdgeData, FaceData>.Vertex, DCELH<VertexData, EdgeData, FaceData>.Vertex>,
            ArrayList<DCELH<VertexData, EdgeData, FaceData>.Vertex>>
    val destination : MutableMap<DCELH<VertexData, EdgeData, FaceData>.Dart, DCELH<VertexData, EdgeData, FaceData>.Vertex>
    val lonelyDarts : ArrayList<DCELH<VertexData, EdgeData, FaceData>.Dart>
    val oldFaces : ArrayList<DCELH<VertexData, EdgeData, FaceData>.Face>

    init {
        this.program = program
        this.graph = graph
        this.iter = iter
        currentIteration = 0

        splitMap = mutableMapOf()
        destination = mutableMapOf()
        lonelyDarts = ArrayList()
        oldFaces = ArrayList()
    }

    fun start () {

        initialDestinations()

        for (i in 1..iter) {
            currentIteration = i
            val size = graph.faces.size
            for (k in 0..size - 1) {
                if (!graph.holes.contains(graph.faces[k])) {
                    subdivideFace(graph.faces[k])
                }
            }
            pairLonelyDarts()
            removeOldDarts()
            fixHoles()
            splitMap.clear()
        }
        println()
    }

    private fun checkSplit (split : Triple<Int, Int, Int>, face : DCELH<VertexData, EdgeData, FaceData>.Face) :
        ArrayList<DCELH<VertexData, EdgeData, FaceData>.Vertex> {
        val source = findVertex(split.first, face)
        val goal = findVertex(split.second, face)

        val splitVerts = ArrayList<DCELH<VertexData, EdgeData, FaceData>.Vertex>()

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
            splitVerts[splitVerts.lastIndex].data.level = currentIteration
        }

        splitMap.put(Pair(source!!, goal!!), splitVerts)
        return splitVerts

    }

    private fun findDart (orig : DCELH<VertexData, EdgeData, FaceData>.Vertex, startDart : DCELH<VertexData, EdgeData, FaceData>.Dart)
            : DCELH<VertexData, EdgeData, FaceData>.Dart? {
        val retOptions = ArrayList<DCELH<VertexData, EdgeData, FaceData>.Dart>()
        for (dart in graph.darts) {
            if (destination[dart] == orig) {
                retOptions.add(dart)
            }
        }

        if (retOptions.size == 1) {
            return findDart(startDart.next!!.origin!!, startDart.next!!)
        }

        for (dart in retOptions) {
            if (graph.holes.contains(dart.face)) {
                return dart
            }
        }

        return retOptions[0]
    }

    private fun findVertex (idx : Int, face : DCELH<VertexData, EdgeData, FaceData>.Face)
            : DCELH<VertexData, EdgeData, FaceData>.Vertex? {

        val darts = face.darts()
        var count = 0
        var prev = 0

        for (cycle in darts) {
            count = count + cycle.size
            if (count > idx) {
                return cycle[idx - prev].origin!!
            }
            prev = prev + cycle.size
        }

        return null!!
        //return darts[idx % darts.size].origin!!

    }

    private fun fixHoles () {
        val holeDarts = mutableMapOf<DCELH<VertexData, EdgeData, FaceData>.Face, ArrayList<DCELH<VertexData, EdgeData, FaceData>.Dart>>()

        for (dart in graph.darts) {
            if (graph.holes.contains(dart.face) && dart.face != graph.holes[0]) {
                holeDarts.putIfAbsent(dart.face!!, ArrayList())
                holeDarts[dart.face!!]!!.add(dart)
            }
        }

        for (list in holeDarts.values) {
            for (dart1 in list) {
                for (dart2 in list) {
                    if (dart2.origin == destination[dart1]) {
                        dart1.makeNext(dart2)
                    }
                }
            }
        }


    }

    private fun initialDestinations () {
        for (k in 0..graph.darts.size-1) {


            destination.put(graph.darts[k], graph.darts[k].next!!.origin!!)
            /*if (k < graph.verts.size-1) {
                destination.put(graph.darts[k], graph.verts[(k+1) % graph.verts.size])
            }
            else {
                destination.put(graph.darts[k], graph.verts[(graph.verts.size + k - 1) % graph.verts.size])
            }*/
        }
    }

    private fun makeChild (subdivision: Subdivision, childFace : ArrayList<DCELH<VertexData, EdgeData, FaceData>.Face>,
                           oldDarts : List<DCELH<VertexData, EdgeData, FaceData>.Dart>,
                           newVerts : ArrayList<DCELH<VertexData, EdgeData, FaceData>.Vertex>) {
        var childVerts = ArrayList<DCELH<VertexData, EdgeData, FaceData>.Vertex>()
        var childDarts = ArrayList<DCELH<VertexData, EdgeData, FaceData>.Dart>()
        var holeFaces = ArrayList<DCELH<VertexData, EdgeData, FaceData>.Face>()
        val holeDarts = ArrayList<DCELH<VertexData, EdgeData, FaceData>.Dart>()
        var faceDart : DCELH<VertexData, EdgeData, FaceData>.Dart
        for (j  in 0..subdivision.children.size-1) {

            if (subdivision.children[j].second.size > 1) {
                for (k in 1..subdivision.children[j].second.lastIndex) {
                    holeFaces.add(graph.Face(data = FaceData()))
                    graph.holes.add(holeFaces[k-1])
                    //TODO add information to data
                }
            }

            // Group child vertices
            //TODO Get hole vertices if too high
            for (vert in subdivision.children[j].second[0]) {
                // Parent Vertex
                if (vert.toIntOrNull() != null && vert.toInt() >= 0 ) {
                    childVerts.add(findVertex(vert.toInt(), oldDarts[0].face!!)!!)
                }
                // Newly created Vertex
                else {
                    childVerts.add(newVerts[subdivision.vertices.indexOf(vert)])
                }
                println()
            }

            // connect child vertices
            for (k in 0..childVerts.size-1) {
                childDarts.add(graph.Dart(origin = childVerts[k], face = childFace[j]))

                //TODO Figure out how to pair the new dart with the appropriate hole
                lonelyDarts.add(childDarts[childDarts.size-1])
                destination.put(childDarts[childDarts.size-1], childVerts[(k+1) % childVerts.size])
                if (k > 0) {
                    childDarts[childDarts.size-1].makePrev(childDarts[childDarts.size-2])
                }

            }
            childDarts[0].makePrev(childDarts[childDarts.size-1])
            faceDart = childDarts[0]
            //childFace[j].aDart = childDarts[0]


            childDarts.clear()
            childVerts.clear()

            //Add holes
            for (i in 1..subdivision.children[j].second.lastIndex) {
                // Group hole vertices
                for (vert in subdivision.children[j].second[i]) {
                    /*if (vert.toIntOrNull() != null && vert.toInt() >= 0) {
                        childVerts.add(findVertex(vert.toInt(), oldDarts[0].face!!))
                    } else {*/
                    childVerts.add(newVerts[subdivision.vertices.indexOf(vert)])
                    //}
                    println()
                }

                // connect hole vertices on dart pointing to face
                for (k in 0..childVerts.size - 1) {
                    childDarts.add(graph.Dart(origin = childVerts[k], face = childFace[j]))

                    //lonelyDarts.add(childDarts[childDarts.size - 1])
                    destination.put(childDarts[childDarts.size - 1], childVerts[(k + 1) % childVerts.size])
                    if (k > 0) {
                        childDarts[childDarts.size - 1].makePrev(childDarts[childDarts.size - 2])
                    }

                }
                childDarts[0].makePrev(childDarts[childDarts.size-1])
                holeFaces[i-1].aDart = childDarts[0]

                // TODO connect hold vertices on dart pointing to hole
                // connect hole vertices on dart pointing to face
                for (k in 0..childVerts.size - 1) {
                    holeDarts.add(graph.Dart(origin = childVerts[k], face = holeFaces[i-1]))
                    childDarts[k].makeTwin(holeDarts[k])
                    //lonelyDarts.add(childDarts[childDarts.size - 1])
                    destination.put(holeDarts[holeDarts.size - 1], childVerts[(childVerts.size + k - 1) % childVerts.size])
                    if (k > 0) {
                        holeDarts[holeDarts.size - 1].makeNext(holeDarts[holeDarts.size - 2])
                    }

                }
                holeDarts[0].makeNext(holeDarts[holeDarts.size-1])
                holeFaces[i-1].aDart = holeDarts[0]

                childDarts.clear()
                holeDarts.clear()
                childVerts.clear()
            }

            childFace[j].aDart = faceDart

            for (hole in holeFaces) {
                childFace[j].holeDarts.add(hole.aDart!!.twin!!)
            }


            holeFaces.clear()
        }

    }

    private fun pairLonelyDarts () {

        val lonlierDarts = ArrayList<DCELH<VertexData, EdgeData, FaceData>.Dart>()
        val vertMap = mutableMapOf<DCELH<VertexData, EdgeData, FaceData>.Vertex, Int>()
        var i = 0
        for (vert in graph.verts) {
            vertMap.put(vert, i)
            i++
        }

        while (lonelyDarts.size > 0) {
            val dart1 = lonelyDarts[0]
            var removed = false
            for (k in 1..lonelyDarts.size-1) {
                if (dart1.origin == destination[lonelyDarts[k]] &&
                        lonelyDarts[k].origin == destination[dart1]) {
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

        for (dart in lonlierDarts) {
            if (graph.holes.contains(dart.face))
                println (vertMap[dart.origin].toString() + " " + vertMap[destination[dart]])
        }


        // Pair the lonelier darts and set their orderings
        // A lonlier dart has a twin that is either on the outer edge or a hole
        //val outsideDarts = mutableMapOf<DCELH<VertexData, EdgeData, FaceData>.Face, ArrayList<DCELH<VertexData, EdgeData, FaceData>.Dart>>()
        val outsideDarts = ArrayList<DCELH<VertexData, EdgeData, FaceData>.Dart>()
        for (dart in lonlierDarts) {
            outsideDarts.add(graph.Dart(origin = destination[dart], face = graph.holes[0]))
            destination.put(outsideDarts[outsideDarts.lastIndex], dart.origin!!)
            dart.makeTwin(outsideDarts[outsideDarts.lastIndex])
        }

        for (dart1 in outsideDarts) {
            for (dart2 in outsideDarts) {
                if (dart2.origin == destination[dart1]) {
                    dart1.makeNext(dart2)
                }
            }
        }

        // Connect each set of darts
        /*for (cycle in outsideDarts.values) {
            for (dart1 in cycle) {
                for (dart2 in cycle) {
                    println(destination[dart1].toString() + " " + dart2.origin)
                    if (destination[dart1] == dart2.origin) {
                        dart1.makeNext(dart2)
                    }
                }
            }
        }*/
    }

    private fun removeOldDarts () {
        for (face in oldFaces) {
            graph.faces.remove(face)
            for (cycle in face.darts()) {
                for (dart in cycle) {
                    if (graph.holes.contains(dart.twin!!.face)) {
                        graph.darts.remove(dart!!.twin)
                        destination.remove(dart!!.twin)
                    }
                    graph.darts.remove(dart)
                    destination.remove(dart)
                }
            }
        }
        oldFaces.clear()

    }

    private fun splitHoles (holeFace : DCELH<VertexData, EdgeData, FaceData>.Face) {

        var splitVerts : ArrayList<DCELH<VertexData, EdgeData, FaceData>.Vertex>?
        var currDart : DCELH<VertexData, EdgeData, FaceData>.Dart
        var done = false
        for (dart in holeFace.darts()[0]) {
            splitVerts = splitMap[Pair(dart.origin, destination[dart])]

            if (splitVerts != null) {
                for (k in 0..splitVerts.lastIndex-1) {
                    currDart = graph.Dart(origin = splitVerts[(splitVerts.size - k - 1) % splitVerts.size], face = holeFace)
                    destination[currDart] = splitVerts[(splitVerts.size - k - 2) % splitVerts.size]
                    lonelyDarts.add(currDart)
                }
                done = true
            }

            splitVerts = splitMap[Pair(destination[dart], dart.origin)]

            if (splitVerts != null && !done) {
                for (k in 0..splitVerts.lastIndex-1) {
                    currDart = graph.Dart(origin = splitVerts[k % splitVerts.size], face = holeFace)
                    destination[currDart] = splitVerts[(k + 1) % splitVerts.size]
                    lonelyDarts.add(currDart)
                }
                done = true
            }

            currDart = graph.Dart(origin = dart.origin, face = holeFace)
            destination[currDart] = destination[dart]!!
            lonelyDarts.add(currDart)
            done = false
        }
    }

    private fun subdivideFace (face : DCELH<VertexData, EdgeData, FaceData>.Face) {
        val subdivision = program.subdivisions[face.data.tileType]
        val darts = face.darts()[0]

        val newVerts = ArrayList<DCELH<VertexData, EdgeData, FaceData>.Vertex>()
        val childFaces = ArrayList<DCELH<VertexData, EdgeData, FaceData>.Face>()


        // Adding Vertices
        for (split in subdivision!!.splits) {
            newVerts.addAll(checkSplit(split, face))
        }

        for (k in subdivision.splitVerts..subdivision.vertices.size-1) {
            //println (k)
            newVerts.add(graph.Vertex(data = VertexData()))
            newVerts[newVerts.lastIndex].data.level = currentIteration
        }

        //Adding Faces
        for (child in subdivision.children) {
            childFaces.add(graph.Face(data = FaceData()))
            childFaces[childFaces.lastIndex].data.tileType = child.first
            childFaces[childFaces.lastIndex].data.node = TreeNode(childFaces[childFaces.lastIndex], face.data.node)
            face.data.node!!.children.add(childFaces[childFaces.lastIndex].data.node!!)
        }
        makeChild(subdivision, childFaces, darts, newVerts)

        //Split the holes
        for (dart in face.holeDarts) {
            splitHoles(dart.twin!!.face!!)
        }

        val vertMap = mutableMapOf<DCELH<VertexData, EdgeData, FaceData>.Vertex, Int>()
        var i = 0
        for (vert in graph.verts) {
            vertMap.put(vert, i)
            i++
        }

        // Remove old face
        oldFaces.add(face)


    }
}
