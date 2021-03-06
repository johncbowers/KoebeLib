package tilings.algorithms

import geometry.ds.dcel.DCEL
import geometry.ds.dcel.DCELH
import geometry.primitives.Euclidean2.PointE2

fun triangulateDCEL (graph : DCEL<PointE2, Unit, Unit>) {

    //print("Start: ")

    val len = graph.faces.size
    //println(len)
    for (k in 0..len-1) {

        //println(graph.faces[k].darts().size)
        triangulateFace(graph, graph.faces[k])

    }

    for (k in 0..len-1) {
        graph.faces.removeAt(0)
    }
}

/*
    Creates a vertex at the average x,y position of the face and connects all vertices to that face
    Modifies the original DCEL
 */
fun triangulateFace (graph : DCEL<PointE2, Unit, Unit>, face : DCEL<PointE2, Unit, Unit>.Face) {

    //println("Face")
    val faceVertex : DCEL<PointE2, Unit, Unit>.Vertex
    val faceCenter : PointE2
    var faceNew : DCEL<PointE2, Unit, Unit>.Face
    var lonelyDart : DCEL<PointE2, Unit, Unit>.Dart?
    var dart1 : DCEL<PointE2, Unit, Unit>.Dart?
    var dart2 : DCEL<PointE2, Unit, Unit>.Dart

    val darts = face.darts()
    //println(darts.size)

    faceCenter = calculateAvgPosition(darts)
    faceVertex = graph.Vertex(data = faceCenter)

    dart1 = null
    lonelyDart = null
    for (k in 0..darts.size-1) {
        faceNew = graph.Face(data = Unit)
        dart2 = graph.Dart(origin = faceVertex, face = faceNew)
        dart2.makeTwin(dart1)
        dart1 = graph.Dart(origin = darts[k].dest, face = faceNew)
        if (k == 0) {
            lonelyDart = dart2
            faceNew.aDart = darts[0]
        } else if (k == darts.size-1) {
            dart1.makeTwin(lonelyDart)
        }

        dart1.makeNext(dart2)
        dart1.makePrev(darts[k])

        dart2.makeNext(darts[k])
        darts[k].face = faceNew
    }

    //println(graph.faces[0])
    //graph.faces.removeAt(0)
    //println(graph.faces[0])
}

fun calculateAvgPosition(darts : List<DCEL<PointE2, Unit, Unit>.Dart>) : PointE2 {
    val retPoint : PointE2
    var xAvg = 0.0
    var yAvg = 0.0

    for (k in 0..darts.size-1) {
        xAvg = xAvg + darts[k].dest!!.data.x
        yAvg = yAvg + darts[k].dest!!.data.y
    }

    xAvg = xAvg / darts.size
    yAvg = yAvg / darts.size

    retPoint = PointE2(xAvg, yAvg)
    return retPoint
}

class Triangulation<VertexData, EdgeData, FaceData> () {

    init {

    }

    fun triangulateDCEL (graph : DCELH<Unit, Unit, Unit>) {

        //print("Start: ")

        val len = graph.faces.size
        //println(len)
        for (k in 1..len-1) {

            //println(graph.faces[k].darts().size)
            if (graph.faces[k] != graph.holes[0]) {
                triangulateFace(graph, graph.faces[k])
            }

        }

        for (k in 1..len-1) {
            graph.faces.removeAt(1)
        }
    }

    fun triangulateFace (graph : DCELH<Unit, Unit, Unit>, face : DCELH<Unit, Unit, Unit>.Face) {

        val faceVertex : DCELH<Unit, Unit, Unit>.Vertex
        val faceCenter : Unit
        var faceNew : DCELH<Unit, Unit, Unit>.Face
        var lonelyDart : DCELH<Unit, Unit, Unit>.Dart?
        var dart1 : DCELH<Unit, Unit, Unit>.Dart?
        var dart2 : DCELH<Unit, Unit, Unit>.Dart

        val darts = face.darts()
        //println(darts.size)

        faceCenter = Unit
        faceVertex = graph.Vertex(data = faceCenter)

        dart1 = null
        lonelyDart = null
        for (cycle in darts) {
            dart1 = null
            lonelyDart = null
            for (k in 0..cycle.size - 1) {
                faceNew = graph.Face(data = Unit)
                dart2 = graph.Dart(origin = faceVertex, face = faceNew)
                dart2.makeTwin(dart1)
                dart1 = graph.Dart(origin = cycle[k].dest, face = faceNew)
                if (k == 0) {
                    lonelyDart = dart2
                    //faceNew.aDart = cycle[0]
                } else if (k == cycle.size - 1) {
                    dart1.makeTwin(lonelyDart)
                }

                dart1.makeNext(dart2)
                dart1.makePrev(cycle[k])

                dart2.makeNext(cycle[k])
                cycle[k].face = faceNew
                faceNew.aDart = cycle[k]
            }
        }
    }
}

class Spherifier<VertexData, EdgeData, FaceData> {

    init {

    }

    fun spherify(graph : DCEL<VertexData, EdgeData, FaceData>) {
        //val newVert = graph.Vertex( data = )
        val bdryVerts = ArrayList<DCEL<VertexData, EdgeData, FaceData>.Vertex>()
        val bdryDarts = ArrayList<DCEL<VertexData, EdgeData, FaceData>.Dart>()
        var vtoi = mutableMapOf<DCEL<VertexData, EdgeData, FaceData>.Vertex, Int>()
        for ( i in 0..graph.verts.lastIndex ) {
            vtoi[graph.verts[i]] = i+1
        }



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
}