package tilings.algorithms

import geometry.ds.dcel.DCEL
import geometry.primitives.Euclidean2.PointE2
import java.awt.Point

fun makeChairDCEL (s: ArrayList<PointE2>) : DCEL<PointE2, Unit, Unit> {

    val chairSize = 8

    val chair = DCEL<PointE2, Unit, Unit>()
    val vertices = ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>()
    val edges = ArrayList<DCEL<PointE2, Unit, Unit>.Edge>()
    val innerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
    val outerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()

    if (s.size < chairSize)
        return chair

    val inner = chair.Face( data = Unit )

    for (k in 0..chairSize-1) {
        vertices.add(chair.Vertex( data = s[k]))
    }

    for (k in 0..chairSize-1) {

        edges.add(chair.Edge(data = Unit))

        // inner darts
        if (k > 0) {
            innerDarts.add(chair.Dart(edge = edges[k], origin = vertices[k], face = inner, prev = innerDarts[k-1]))
        } else {
            innerDarts.add(chair.Dart(edge = edges[k], origin = vertices[k], face = inner))
        }

        // outer darts
        if (k > 0) {
            outerDarts.add(chair.Dart(edge = edges[k], origin = vertices[(k + 1) % chairSize], face = chair.outerFace,
                    next = outerDarts[k - 1] ,twin = innerDarts[k]))
        } else {
            outerDarts.add(chair.Dart(edge = edges[k], origin = vertices[(k + 1) % chairSize], face = chair.outerFace,
                    twin = innerDarts[k]))
        }

    }

    for (k in 0..chairSize-1) {
        if (k == 0) {
            innerDarts[k].makePrev(innerDarts[(k + chairSize - 1) % chairSize])
            outerDarts[k].makeNext(outerDarts[(k + chairSize - 1) % chairSize])
        }

        //inner darts
        innerDarts[k].makeTwin(outerDarts[k])
        innerDarts[k].makeNext(innerDarts[(k + 1) % chairSize])

        //outer darts
        outerDarts[k].makePrev(innerDarts[(k + 1) % chairSize])
    }

    chair.faces[0].aDart = innerDarts[0]
    println (innerDarts[0])
    println (innerDarts[0].prev)
    println (innerDarts[7].next)
    println (innerDarts[7])
    //chair.outerFace?.aDart = outerDarts[0]

    return chair

}

fun subDivideChair(parent : DCEL<PointE2, Unit, Unit>) : DCEL<PointE2, Unit, Unit> {

    val numFaces = 4
    val numEdges = 8
    val numExterior = 16
    var count = 0

    val allChair = DCEL<PointE2, Unit, Unit>()
    val chairs = ArrayList<DCEL<PointE2, Unit, Unit>>()

    val faces = ArrayList<DCEL<PointE2, Unit, Unit>.Face>()

    // Add faces
    for (k in 0..numFaces-1) {
        chairs.add(DCEL<PointE2, Unit, Unit>())
        faces.add(allChair.Face(data = Unit))
        chairs.get(k).faces.add(faces.get(k))
    }


    val verts = ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>()
    var currDart = parent.faces[0].aDart
    val startDart = currDart

    // Add exterior vertices
    verts.add(allChair.Vertex(data = PointE2(currDart!!.origin!!.data.x, currDart.origin!!.data.y)))
    do {
        //subDivideEdge(currDart!!, parent, allChair)

        verts.add(allChair.Vertex(data = PointE2((currDart!!.origin!!.data.x + currDart!!.dest!!.data.x) / 2.0,
                (currDart!!.origin!!.data.y + currDart!!.dest!!.data.y) / 2.0)))

        verts.add(allChair.Vertex(data = PointE2(currDart!!.dest!!.data.x, currDart.dest!!.data.y)))

        currDart = currDart!!.next
        count = count + 1

    } while (currDart != startDart)

    // Temporary fix
    verts.removeAt(16)
    allChair.verts.removeAt(16)

    // Add interior vertices

    verts.add(allChair.Vertex(data = PointE2((allChair.verts[0].data.x + allChair.verts[8].data.x) / 2.0,
            (allChair.verts[0].data.y + allChair.verts[8].data.y) / 2.0)))
    verts.add(allChair.Vertex(data = PointE2((allChair.verts[2].data.x + allChair.verts[8].data.x) / 2.0,
            (allChair.verts[2].data.y + allChair.verts[8].data.y) / 2.0)))
    verts.add(allChair.Vertex(data = PointE2((allChair.verts[3].data.x + allChair.verts[7].data.x) / 2.0,
            (allChair.verts[3].data.y + allChair.verts[7].data.y) / 2.0)))
    verts.add(allChair.Vertex(data = PointE2((allChair.verts[13].data.x + allChair.verts[9].data.x) / 2.0,
            (verts[13].data.y + allChair.verts[9].data.y) / 2.0)))
    verts.add(allChair.Vertex(data = PointE2((allChair.verts[14].data.x + allChair.verts[8].data.x) / 2.0,
            (allChair.verts[14].data.y + allChair.verts[8].data.y) / 2.0)))


    // Edges
    for (k in 0..numEdges*numFaces+2*numEdges) {
        allChair.Edge(data = Unit)
    }

    // Face 1
    allChair.Dart(edge = allChair.edges[0], origin = allChair.verts[0], face = faces[0])
    allChair.Dart(edge = allChair.edges[1], origin = allChair.verts[1], face = faces[0])
    allChair.Dart(edge = allChair.edges[2], origin = allChair.verts[2], face = faces[0])
    allChair.Dart(edge = allChair.edges[3], origin = allChair.verts[17], face = faces[0])
    allChair.Dart(edge = allChair.edges[4], origin = allChair.verts[16], face = faces[0])
    allChair.Dart(edge = allChair.edges[5], origin = allChair.verts[20], face = faces[0])
    allChair.Dart(edge = allChair.edges[6], origin = allChair.verts[14], face = faces[0])
    allChair.Dart(edge = allChair.edges[7], origin = allChair.verts[15], face = faces[0])



    for (k in 0..7) {
        allChair.darts[k].makeNext(allChair.darts[(k+1) % numEdges])
    }

    // Face 2
    allChair.Dart(edge = allChair.edges[8], origin = allChair.verts[4], face = faces[1])
    allChair.Dart(edge = allChair.edges[9], origin = allChair.verts[5], face = faces[1])
    allChair.Dart(edge = allChair.edges[10], origin = allChair.verts[6], face = faces[1])
    allChair.Dart(edge = allChair.edges[11], origin = allChair.verts[7], face = faces[1])
    allChair.Dart(edge = allChair.edges[12], origin = allChair.verts[18], face = faces[1])
    allChair.Dart(edge = allChair.edges[13], origin = allChair.verts[17], face = faces[1])
    allChair.Dart(edge = allChair.edges[14], origin = allChair.verts[2], face = faces[1])
    allChair.Dart(edge = allChair.edges[15], origin = allChair.verts[3], face = faces[1])

    for (k in 8..14) {
        allChair.darts[k].makeNext(allChair.darts[(k+1) % (numEdges*2)])
    }
    allChair.darts[15].makeNext(allChair.darts[8])

    // Face 3
    allChair.Dart(edge = allChair.edges[16], origin = allChair.verts[16], face = faces[2])
    allChair.Dart(edge = allChair.edges[17], origin = allChair.verts[17], face = faces[2])
    allChair.Dart(edge = allChair.edges[18], origin = allChair.verts[18], face = faces[2])
    allChair.Dart(edge = allChair.edges[19], origin = allChair.verts[7], face = faces[2])
    allChair.Dart(edge = allChair.edges[20], origin = allChair.verts[8], face = faces[2])
    allChair.Dart(edge = allChair.edges[21], origin = allChair.verts[9], face = faces[2])
    allChair.Dart(edge = allChair.edges[22], origin = allChair.verts[19], face = faces[2])
    allChair.Dart(edge = allChair.edges[23], origin = allChair.verts[20], face = faces[2])

    for (k in 16..22) {
        allChair.darts[k].makeNext(allChair.darts[(k+1) % (numEdges*3)])
    }
    allChair.darts[23].makeNext(allChair.darts[16])

    // Face 4
    allChair.Dart(edge = allChair.edges[24], origin = allChair.verts[12], face = faces[3])
    allChair.Dart(edge = allChair.edges[25], origin = allChair.verts[13], face = faces[3])
    allChair.Dart(edge = allChair.edges[26], origin = allChair.verts[14], face = faces[3])
    allChair.Dart(edge = allChair.edges[27], origin = allChair.verts[20], face = faces[3])
    allChair.Dart(edge = allChair.edges[28], origin = allChair.verts[19], face = faces[3])
    allChair.Dart(edge = allChair.edges[29], origin = allChair.verts[9], face = faces[3])
    allChair.Dart(edge = allChair.edges[30], origin = allChair.verts[10], face = faces[3])
    allChair.Dart(edge = allChair.edges[31], origin = allChair.verts[11], face = faces[3])

    for (k in 24..30) {
        allChair.darts[k].makeNext(allChair.darts[(k+1) % (numEdges*4)])
    }
    allChair.darts[31].makeNext(allChair.darts[24])


    // Exterior Darts
    allChair.Dart(edge = allChair.edges[32], origin = allChair.verts[1], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[33], origin = allChair.verts[0], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[34], origin = allChair.verts[15], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[35], origin = allChair.verts[14], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[36], origin = allChair.verts[13], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[37], origin = allChair.verts[11], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[38], origin = allChair.verts[12], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[39], origin = allChair.verts[10], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[40], origin = allChair.verts[9], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[41], origin = allChair.verts[8], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[42], origin = allChair.verts[7], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[43], origin = allChair.verts[6], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[44], origin = allChair.verts[5], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[45], origin = allChair.verts[4], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[46], origin = allChair.verts[3], face = allChair.outerFace)
    allChair.Dart(edge = allChair.edges[47], origin = allChair.verts[2], face = allChair.outerFace)

    // Set interior twins
    allChair.darts[2].makeTwin(allChair.darts[13])
    allChair.darts[3].makeTwin(allChair.darts[16])
    allChair.darts[4].makeTwin(allChair.darts[23])
    allChair.darts[5].makeTwin(allChair.darts[26])

    allChair.darts[11].makeTwin(allChair.darts[18])
    allChair.darts[12].makeTwin(allChair.darts[17])

    allChair.darts[21].makeTwin(allChair.darts[28])
    allChair.darts[22].makeTwin(allChair.darts[27])

    // Set exterior twins
    allChair.darts[32].makeTwin(allChair.darts[0])
    allChair.darts[33].makeTwin(allChair.darts[7])
    allChair.darts[34].makeTwin(allChair.darts[6])
    allChair.darts[35].makeTwin(allChair.darts[25])
    allChair.darts[36].makeTwin(allChair.darts[24])
    allChair.darts[37].makeTwin(allChair.darts[31])
    allChair.darts[38].makeTwin(allChair.darts[30])
    allChair.darts[39].makeTwin(allChair.darts[29])
    allChair.darts[40].makeTwin(allChair.darts[20])
    allChair.darts[41].makeTwin(allChair.darts[19])
    allChair.darts[42].makeTwin(allChair.darts[10])
    allChair.darts[43].makeTwin(allChair.darts[9])
    allChair.darts[44].makeTwin(allChair.darts[8])
    allChair.darts[45].makeTwin(allChair.darts[15])
    allChair.darts[46].makeTwin(allChair.darts[14])
    allChair.darts[47].makeTwin(allChair.darts[1])


    return allChair
}

/*fun subDivideEdge(dart : DCEL<PointE2, Unit, Unit>.Dart,
                  parent : DCEL<PointE2, Unit, Unit>, newChair : DCEL<PointE2, Unit, Unit>) {
    val new_x = (dart.origin!!.data.x + dart.dest!!.data.x) / 2.0
    val new_y = (dart.origin!!.data.y + dart.dest!!.data.y) / 2.0


    val origin = newChair.Vertex(data = PointE2(dart.origin!!.data.x, dart.origin!!.data.y))
    val dest = newChair.Vertex(data = PointE2(new_x, new_y))

    val e = newChair.Edge(data = Unit)
    val d = newChair.Dart(edge = e, origin = origin, face = )

}*/

class ChairTile (s : ArrayList<PointE2>) {
    val chairSize = 8
    val chair = DCEL<PointE2, Unit, Unit>()

    init {
        val inner = chair.Face( data = Unit )

        addVertices(inner, s)
    }


    private fun addVertices (f : DCEL<PointE2, Unit, Unit>.Face, s : ArrayList<PointE2>) {
        val v = ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>()

        for (k in 0..chairSize-1) {
            v.add(chair.Vertex( data = s[k]))
        }

        addEdges(f, v)
    }

    private fun addEdges (f : DCEL<PointE2, Unit, Unit>.Face, v : ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>) {
        val e = ArrayList<DCEL<PointE2, Unit, Unit>.Edge>()

        for (k in 0..chairSize-1) {
            e.add(chair.Edge( data = Unit))
        }

        addHalfEdges(f, v, e)
    }

    private fun addHalfEdges (f : DCEL<PointE2, Unit, Unit>.Face, v : ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>,
                              e : ArrayList<DCEL<PointE2, Unit, Unit>.Edge>) {

        val innerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
        val outerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()

        for (k in 0..chairSize-1) {


            // inner darts
            if (k > 0) {
                innerDarts.add(chair.Dart(edge = e[k], origin = v[k], face = f, prev = innerDarts[k-1]))
            } else {
                innerDarts.add(chair.Dart(edge = e[k], origin = v[k], face = f))
            }

            // outer darts
            if (k > 0) {
                outerDarts.add(chair.Dart(edge = e[k], origin = v[(k + 1) % chairSize], face = chair.outerFace,
                        next = outerDarts[k - 1] ,twin = innerDarts[k]))
            } else {
                outerDarts.add(chair.Dart(edge = e[k], origin = v[(k + 1) % chairSize], face = chair.outerFace,
                        twin = innerDarts[k]))
            }

        }

        for (k in 0..chairSize-1) {
            if (k == 0) {
                innerDarts[k].makePrev(innerDarts[(k + chairSize - 1) % chairSize])
                outerDarts[k].makeNext(outerDarts[(k + chairSize - 1) % chairSize])
            }

            //inner darts
            innerDarts[k].makeTwin(outerDarts[k])
            innerDarts[k].makeNext(innerDarts[(k + 1) % chairSize])

            //outer darts
            outerDarts[k].makePrev(outerDarts[(k + 1) % chairSize])
        }

        chair.faces[0].aDart = innerDarts[0]

    }
}

fun main(passedArgs : Array<String>) {
    val points = ArrayList<PointE2>()

    points.add(PointE2(x = 0.0, y = 0.0))
    points.add(PointE2(x = 1.0, y = 0.0))
    points.add(PointE2(x = 2.0, y = 0.0))
    points.add(PointE2(x = 2.0, y = 1.0))
    points.add(PointE2(x = 1.0, y = 1.0))
    points.add(PointE2(x = 1.0, y = 2.0))
    points.add(PointE2(x = 0.0, y = 2.0))
    points.add(PointE2(x = 0.0, y = 1.0))

    val tile = makeChairDCEL(points)

    print("Vertices: ")
    println(tile?.verts.toString())

    print("Edges: ")
    println(tile?.edges.toString())

    print("Darts: ")
    println(tile?.darts.toString())

    print("Faces: ")
    println(tile?.faces.toString())
}