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
    //chair.outerFace?.aDart = outerDarts[0]

    return chair

}

fun subDivideChair(parent : DCEL<PointE2, Unit, Unit>) : DCEL<PointE2, Unit, Unit> {

    val allChair = DCEL<PointE2, Unit, Unit>()
    val chairs = ArrayList<DCEL<PointE2, Unit, Unit>>()



    return allChair
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