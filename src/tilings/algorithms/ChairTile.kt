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

fun subDivideChair (parent : DCEL<PointE2, Unit, Unit>) {

    val len = parent.faces.size
    val lonelyDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()

    for (k in 0..len-1) {
        subDivideChairFace(parent, parent.faces[k], lonelyDarts)
    }

    pairLonelyDarts(lonelyDarts)

    for (k in 0..len-1) {
        parent.faces.removeAt(0)
    }

}

fun subDivideChairFace (parent : DCEL<PointE2, Unit, Unit>, face : DCEL<PointE2, Unit, Unit>.Face,
                        lonelyDarts : ArrayList<DCEL<PointE2, Unit, Unit>.Dart>) {
    val numFaces = 4
    val newFaces = ArrayList<DCEL<PointE2, Unit, Unit>.Face>()

    val newVerts = ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>()
    val newDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
    val outerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()

    for (k in 0..numFaces-1) {
        newFaces.add(parent.Face(data = Unit))
    }

    // Exterior Darts and Vertices
    val darts = face.darts()
    val len = darts.size
    for (k in 0..len-1) {
        newVerts.add(parent.Vertex(data = PointE2((darts[k].origin!!.data.x + darts[(k+1) % len].origin!!.data.x) / 2.0,
                (darts[k].origin!!.data.y + darts[(k+1) % len].origin!!.data.y) / 2.0)))
        newDarts.add(parent.Dart(origin = darts[k].origin))
        darts[k].origin!!.aDart = newDarts[newDarts.size-1]
        newDarts.add(parent.Dart(origin = newVerts[newVerts.size-1]))

        if (darts[k].twin != null && darts[k].twin!!.face == parent.outerFace) {
            outerDarts.add(parent.Dart(origin = newVerts[newVerts.size-1], twin = newDarts[newDarts.size-2],
                    face = parent.outerFace))
            outerDarts.add(parent.Dart(origin = darts[k].dest, prev = outerDarts[outerDarts.size-1],
                    twin = newDarts[newDarts.size-1], face = parent.outerFace))
            outerDarts[outerDarts.size-1].makePrev(outerDarts[outerDarts.size-2])
            //parent.darts.remove(darts[k].twin)
            //println("HELLOLOLOLOLO")
        }
        else {
            lonelyDarts.add(newDarts[newDarts.size-2])
            lonelyDarts.add(newDarts[newDarts.size-1])
        }

        //parent.darts.remove(darts[k])

    }

    // Face 0
    newDarts[0].face = newFaces[0]
    newDarts[1].face = newFaces[0]
    newDarts[14].face = newFaces[0]
    newDarts[15].face = newFaces[0]

    newDarts[0].makeNext(newDarts[1])
    newDarts[14].makeNext(newDarts[15])
    newDarts[15].makeNext(newDarts[0])

    // Face 1
    newDarts[2].face = newFaces[1]
    newDarts[3].face = newFaces[1]
    newDarts[4].face = newFaces[1]
    newDarts[5].face = newFaces[1]

    newDarts[2].makeNext(newDarts[3])
    newDarts[3].makeNext(newDarts[4])
    newDarts[4].makeNext(newDarts[5])
    newDarts[5].makeNext(newDarts[6])

    // Face 2
    newDarts[6].face = (newFaces[2])
    newDarts[7].face = newFaces[2]
    newDarts[8].face = newFaces[2]
    newDarts[9].face = newFaces[0]

    newDarts[7].makeNext(newDarts[8])

    // Face 3
    newDarts[10].face = newFaces[3]
    newDarts[11].face = newFaces[3]
    newDarts[12].face = newFaces[3]
    newDarts[13].face = newFaces[3]

    newDarts[9].makeNext(newDarts[10])
    newDarts[10].makeNext(newDarts[11])
    newDarts[11].makeNext(newDarts[12])
    newDarts[12].makeNext(newDarts[13])

    //Interior Vertices and Darts
    val innerVerts = ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>()

    // We need to use midpoints because of rotations between tiles
    /*innerVerts.add(parent.Vertex(data = PointE2(
            newDarts[1].origin!!.data.x,
            newDarts[15].origin!!.data.y)))
    innerVerts.add(parent.Vertex(data = PointE2(
            newDarts[2].origin!!.data.x,
            newDarts[15].origin!!.data.y)))
    innerVerts.add(parent.Vertex(data = PointE2(
            newDarts[3].origin!!.data.x,
            newDarts[15].origin!!.data.y)))
    innerVerts.add(parent.Vertex(data = PointE2(
            newDarts[1].origin!!.data.x,
            newDarts[13].origin!!.data.y)))
    innerVerts.add(parent.Vertex(data = PointE2(
            newDarts[1].origin!!.data.x,
            newDarts[14].origin!!.data.y)))*/

    innerVerts.add(parent.Vertex(data = PointE2(
            (newDarts[0].origin!!.data.x + newDarts[8].origin!!.data.x) / 2.0,
            (newDarts[0].origin!!.data.y + newDarts[8].origin!!.data.y) / 2.0
    )))
    innerVerts.add(parent.Vertex(data = PointE2(
            (newDarts[2].origin!!.data.x + newDarts[8].origin!!.data.x) / 2.0,
            (newDarts[2].origin!!.data.y + newDarts[8].origin!!.data.y) / 2.0
    )))
    innerVerts.add(parent.Vertex(data = PointE2(
            (newDarts[3].origin!!.data.x + newDarts[7].origin!!.data.x) / 2.0,
            (newDarts[3].origin!!.data.y + newDarts[7].origin!!.data.y) / 2.0
    )))
    innerVerts.add(parent.Vertex(data = PointE2(
            (newDarts[9].origin!!.data.x + newDarts[13].origin!!.data.x) / 2.0,
            (newDarts[9].origin!!.data.y + newDarts[13].origin!!.data.y) / 2.0
    )))
    innerVerts.add(parent.Vertex(data = PointE2(
            (newDarts[1].origin!!.data.x + newDarts[11].origin!!.data.x) / 2.0,
            (newDarts[1].origin!!.data.y + newDarts[11].origin!!.data.y) / 2.0
    )))

    val innerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
    innerDarts.add(parent.Dart(origin = innerVerts[0], face = newFaces[2]))
    innerDarts.add(parent.Dart(origin = innerVerts[1], twin = innerDarts[innerDarts.size-1], face = newFaces[0]))

    innerDarts.add(parent.Dart(origin = innerVerts[1], face = newFaces[2]))
    innerDarts.add(parent.Dart(origin = innerVerts[2], twin = innerDarts[innerDarts.size-1], face = newFaces[1]))

    innerDarts.add(parent.Dart(origin = innerVerts[2], face = newFaces[2]))
    innerDarts.add(parent.Dart(origin = newVerts[3], twin = innerDarts[innerDarts.size-1], face = newFaces[1]))

    innerDarts.add(parent.Dart(origin = newVerts[4], face = newFaces[2]))
    innerDarts.add(parent.Dart(origin = innerVerts[3], twin = innerDarts[innerDarts.size-1], face = newFaces[3]))

    innerDarts.add(parent.Dart(origin = innerVerts[3], face = newFaces[2]))
    innerDarts.add(parent.Dart(origin = innerVerts[4], twin = innerDarts[innerDarts.size-1], face = newFaces[1]))

    innerDarts.add(parent.Dart(origin = innerVerts[4], face = newFaces[2], prev = innerDarts[innerDarts.size-2]))
    innerDarts.add(parent.Dart(origin = innerVerts[0], twin = innerDarts[innerDarts.size-1], face = newFaces[0]))

    innerDarts.add(parent.Dart(origin = innerVerts[1], face = newFaces[1]))
    innerDarts.add(parent.Dart(origin = newDarts[2].origin, twin = innerDarts[innerDarts.size-1], face = newFaces[0]))

    innerDarts.add(parent.Dart(origin = innerVerts[4], face = newFaces[0]))
    innerDarts.add(parent.Dart(origin = newDarts[14].origin, twin = innerDarts[innerDarts.size-1], face = newFaces[3]))

    newFaces[2].aDart = innerDarts[0]
    newFaces[3].aDart = newDarts[12]
    newFaces[1].aDart = newDarts[4]
    newFaces[0].aDart = newDarts[0]

    innerDarts[0].makeNext(innerDarts[2])
    innerDarts[1].makeNext(innerDarts[11])
    innerDarts[2].makeNext(innerDarts[4])
    innerDarts[3].makeNext(innerDarts[12])
    innerDarts[4].makeNext(newDarts[7])
    innerDarts[5].makeNext(innerDarts[3])
    innerDarts[5].makePrev(newDarts[6])
    innerDarts[6].makeNext(innerDarts[8])
    innerDarts[6].makePrev(newDarts[8])
    innerDarts[7].makeNext(newDarts[9])
    innerDarts[8].makeNext(innerDarts[10])
    innerDarts[9].makeNext(innerDarts[7])
    innerDarts[10].makeNext(innerDarts[0])
    innerDarts[11].makeNext(innerDarts[14])
    innerDarts[12].makeNext(newDarts[2])
    innerDarts[13].makeNext(innerDarts[1])
    innerDarts[13].makePrev(newDarts[1])
    innerDarts[14].makeNext(newDarts[14])
    innerDarts[15].makeNext(innerDarts[9])
    innerDarts[15].makePrev(newDarts[13])


}

fun pairLonelyDarts (lonelyDarts: ArrayList<DCEL<PointE2, Unit, Unit>.Dart>) {
    val len = lonelyDarts.size
    var stepSize = 0.0


    if (lonelyDarts.size > 0) {
        stepSize = lonelyDarts[0].origin!!.data.distTo(lonelyDarts[1].origin!!.data)
    }

    while (lonelyDarts.size > 0) {
        for (j in 0..lonelyDarts.size-1) {
            if (lonelyDarts[0].face != lonelyDarts[j].face) {
                //println(lonelyDarts[0].origin!!.data.distTo(lonelyDarts[j].origin!!.data))
            }
            if (areOpposite(lonelyDarts[0], lonelyDarts[j])
                    && lonelyDarts[0].origin!!.data.distTo(lonelyDarts[j].origin!!.data) == stepSize
                    &&  lonelyDarts[0].origin!!.data.distTo(lonelyDarts[j].next!!.origin!!.data) == 0.0
                    && lonelyDarts[0].face != lonelyDarts[j].face) {
                lonelyDarts[0].makeTwin(lonelyDarts[j])
                lonelyDarts.removeAt(j)
                lonelyDarts.removeAt(0)
                break
            }
        }
    }

}

fun areOpposite(dart1 : DCEL<PointE2, Unit, Unit>.Dart, dart2 : DCEL<PointE2, Unit, Unit>.Dart) : Boolean{
    val dx1 = dart1.next!!.origin!!.data.x - dart1.origin!!.data.x
    val dx2 = dart2.next!!.origin!!.data.x - dart2.origin!!.data.x

    val dy1 = dart1.next!!.origin!!.data.y - dart1.origin!!.data.y
    val dy2 = dart2.next!!.origin!!.data.y - dart2.origin!!.data.y

    return (dx1 == -dx2 && dy1 == -dy2)
}


class ChairTile (s : ArrayList<PointE2>) {
    val chairSize = 8
    val chair = DCEL<PointE2, Unit, Unit>()

    init {
        val inner = chair.Face( data = Unit )

        addVertices(inner, s)
    }


    fun subDivideChair () {

        val len = chair.faces.size
        val lonelyDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()

        for (k in 0..len-1) {
            subDivideChairFace(chair, chair.faces[k], lonelyDarts)
        }

        pairLonelyDarts(lonelyDarts)

        for (k in 0..len-1) {
            chair.faces.removeAt(0)
        }

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

    private fun areOpposite(dart1 : DCEL<PointE2, Unit, Unit>.Dart, dart2 : DCEL<PointE2, Unit, Unit>.Dart) : Boolean {
        val dx1 = dart1.next!!.origin!!.data.x - dart1.origin!!.data.x
        val dx2 = dart2.next!!.origin!!.data.x - dart2.origin!!.data.x

        val dy1 = dart1.next!!.origin!!.data.y - dart1.origin!!.data.y
        val dy2 = dart2.next!!.origin!!.data.y - dart2.origin!!.data.y

        return (dx1 == -dx2 && dy1 == -dy2)
    }

    private fun pairLonelyDarts (lonelyDarts: ArrayList<DCEL<PointE2, Unit, Unit>.Dart>) {
        val len = lonelyDarts.size
        var stepSize = 0.0


        if (lonelyDarts.size > 0) {
            stepSize = lonelyDarts[0].origin!!.data.distTo(lonelyDarts[1].origin!!.data)
        }

        while (lonelyDarts.size > 0) {
            for (j in 0..lonelyDarts.size-1) {
                if (lonelyDarts[0].face != lonelyDarts[j].face) {
                    //println(lonelyDarts[0].origin!!.data.distTo(lonelyDarts[j].origin!!.data))
                }
                if (areOpposite(lonelyDarts[0], lonelyDarts[j])
                        && lonelyDarts[0].origin!!.data.distTo(lonelyDarts[j].origin!!.data) == stepSize
                        &&  lonelyDarts[0].origin!!.data.distTo(lonelyDarts[j].next!!.origin!!.data) == 0.0
                        && lonelyDarts[0].face != lonelyDarts[j].face) {
                    lonelyDarts[0].makeTwin(lonelyDarts[j])
                    lonelyDarts.removeAt(j)
                    lonelyDarts.removeAt(0)
                    break
                }
            }
        }

    }

    private fun subDivideChairFace (face : DCEL<PointE2, Unit, Unit>.Face,
                                    lonelyDarts : ArrayList<DCEL<PointE2, Unit, Unit>.Dart>) {
        val numFaces = 4
        val newFaces = ArrayList<DCEL<PointE2, Unit, Unit>.Face>()

        val newVerts = ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>()
        val newDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
        val outerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()

        for (k in 0..numFaces-1) {
            newFaces.add(chair.Face(data = Unit))
        }

        // Exterior Darts and Vertices
        val darts = face.darts()
        val len = darts.size
        for (k in 0..len-1) {
            newVerts.add(chair.Vertex(data = PointE2((darts[k].origin!!.data.x + darts[(k+1) % len].origin!!.data.x) / 2.0,
                    (darts[k].origin!!.data.y + darts[(k+1) % len].origin!!.data.y) / 2.0)))
            newDarts.add(chair.Dart(origin = darts[k].origin))
            newDarts.add(chair.Dart(origin = newVerts[newVerts.size-1]))

            if (darts[k].twin != null && darts[k].twin!!.face == chair.outerFace) {
                outerDarts.add(chair.Dart(origin = newVerts[newVerts.size-1], twin = newDarts[newDarts.size-2],
                        face = chair.outerFace))
                outerDarts.add(chair.Dart(origin = darts[k].dest, prev = outerDarts[outerDarts.size-1],
                        twin = newDarts[newDarts.size-1], face = chair.outerFace))
                //parent.darts.remove(darts[k].twin)
            }
            else {
                lonelyDarts.add(newDarts[newDarts.size-2])
                lonelyDarts.add(newDarts[newDarts.size-1])
            }

            //parent.darts.remove(darts[k])

        }

        // Face 0
        newDarts[0].face = newFaces[0]
        newDarts[1].face = newFaces[0]
        newDarts[14].face = newFaces[0]
        newDarts[15].face = newFaces[0]

        newDarts[0].makeNext(newDarts[1])
        newDarts[14].makeNext(newDarts[15])
        newDarts[15].makeNext(newDarts[0])

        // Face 1
        newDarts[2].face = newFaces[1]
        newDarts[3].face = newFaces[1]
        newDarts[4].face = newFaces[1]
        newDarts[5].face = newFaces[1]

        newDarts[2].makeNext(newDarts[3])
        newDarts[3].makeNext(newDarts[4])
        newDarts[4].makeNext(newDarts[5])
        newDarts[5].makeNext(newDarts[6])

        // Face 2
        newDarts[6].face = (newFaces[2])
        newDarts[7].face = newFaces[2]
        newDarts[8].face = newFaces[2]
        newDarts[9].face = newFaces[0]

        newDarts[7].makeNext(newDarts[8])

        // Face 3
        newDarts[10].face = newFaces[3]
        newDarts[11].face = newFaces[3]
        newDarts[12].face = newFaces[3]
        newDarts[13].face = newFaces[3]

        newDarts[9].makeNext(newDarts[10])
        newDarts[10].makeNext(newDarts[11])
        newDarts[11].makeNext(newDarts[12])
        newDarts[12].makeNext(newDarts[13])

        //Interior Vertices and Darts
        val innerVerts = ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>()

        innerVerts.add(chair.Vertex(data = PointE2(
                (newDarts[0].origin!!.data.x + newDarts[8].origin!!.data.x) / 2.0,
                (newDarts[0].origin!!.data.y + newDarts[8].origin!!.data.y) / 2.0
        )))
        innerVerts.add(chair.Vertex(data = PointE2(
                (newDarts[2].origin!!.data.x + newDarts[8].origin!!.data.x) / 2.0,
                (newDarts[2].origin!!.data.y + newDarts[8].origin!!.data.y) / 2.0
        )))
        innerVerts.add(chair.Vertex(data = PointE2(
                (newDarts[3].origin!!.data.x + newDarts[7].origin!!.data.x) / 2.0,
                (newDarts[3].origin!!.data.y + newDarts[7].origin!!.data.y) / 2.0
        )))
        innerVerts.add(chair.Vertex(data = PointE2(
                (newDarts[9].origin!!.data.x + newDarts[13].origin!!.data.x) / 2.0,
                (newDarts[9].origin!!.data.y + newDarts[13].origin!!.data.y) / 2.0
        )))
        innerVerts.add(chair.Vertex(data = PointE2(
                (newDarts[1].origin!!.data.x + newDarts[11].origin!!.data.x) / 2.0,
                (newDarts[1].origin!!.data.y + newDarts[11].origin!!.data.y) / 2.0
        )))

        val innerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
        innerDarts.add(chair.Dart(origin = innerVerts[0], face = newFaces[2]))
        innerDarts.add(chair.Dart(origin = innerVerts[1], twin = innerDarts[innerDarts.size-1], face = newFaces[0]))

        innerDarts.add(chair.Dart(origin = innerVerts[1], face = newFaces[2]))
        innerDarts.add(chair.Dart(origin = innerVerts[2], twin = innerDarts[innerDarts.size-1], face = newFaces[1]))

        innerDarts.add(chair.Dart(origin = innerVerts[2], face = newFaces[2]))
        innerDarts.add(chair.Dart(origin = newVerts[3], twin = innerDarts[innerDarts.size-1], face = newFaces[1]))

        innerDarts.add(chair.Dart(origin = newVerts[4], face = newFaces[2]))
        innerDarts.add(chair.Dart(origin = innerVerts[3], twin = innerDarts[innerDarts.size-1], face = newFaces[3]))

        innerDarts.add(chair.Dart(origin = innerVerts[3], face = newFaces[2]))
        innerDarts.add(chair.Dart(origin = innerVerts[4], twin = innerDarts[innerDarts.size-1], face = newFaces[1]))

        innerDarts.add(chair.Dart(origin = innerVerts[4], face = newFaces[2], prev = innerDarts[innerDarts.size-2]))
        innerDarts.add(chair.Dart(origin = innerVerts[0], twin = innerDarts[innerDarts.size-1], face = newFaces[0]))

        innerDarts.add(chair.Dart(origin = innerVerts[1], face = newFaces[1]))
        innerDarts.add(chair.Dart(origin = newDarts[2].origin, twin = innerDarts[innerDarts.size-1], face = newFaces[0]))

        innerDarts.add(chair.Dart(origin = innerVerts[4], face = newFaces[0]))
        innerDarts.add(chair.Dart(origin = newDarts[14].origin, twin = innerDarts[innerDarts.size-1], face = newFaces[3]))

        newFaces[2].aDart = innerDarts[0]
        newFaces[3].aDart = newDarts[12]
        newFaces[1].aDart = newDarts[4]
        newFaces[0].aDart = newDarts[0]

        innerDarts[0].makeNext(innerDarts[2])
        innerDarts[1].makeNext(innerDarts[11])
        innerDarts[2].makeNext(innerDarts[4])
        innerDarts[3].makeNext(innerDarts[12])
        innerDarts[4].makeNext(newDarts[7])
        innerDarts[5].makeNext(innerDarts[3])
        innerDarts[5].makePrev(newDarts[6])
        innerDarts[6].makeNext(innerDarts[8])
        innerDarts[6].makePrev(newDarts[8])
        innerDarts[7].makeNext(newDarts[9])
        innerDarts[8].makeNext(innerDarts[10])
        innerDarts[9].makeNext(innerDarts[7])
        innerDarts[10].makeNext(innerDarts[0])
        innerDarts[11].makeNext(innerDarts[14])
        innerDarts[12].makeNext(newDarts[2])
        innerDarts[13].makeNext(innerDarts[1])
        innerDarts[13].makePrev(newDarts[1])
        innerDarts[14].makeNext(newDarts[14])
        innerDarts[15].makeNext(innerDarts[9])
        innerDarts[15].makePrev(newDarts[13])


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