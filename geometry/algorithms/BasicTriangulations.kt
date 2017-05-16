package geometry.algorithms

import geometry.ds.dcel.DCEL;
import geometry.primitives.Euclidean2.*;

/**
 * Created by John Bowers on 5/15/17.
 */

fun triangulateIncremental(S: ArrayList<Point2d>): DCEL<Point2d, Unit, Unit> {
    S.sortWith(compareBy({it.x}, {it.y}))

    val tri = getCCWTriangle(S[0], S[1], S[2])

    for (k in 3..S.size-1) {
        val pk = S[k]
        val he = tri.outerFace.aDart
        val upperTangent: DCEL<Point2d, Unit, Unit>.Dart?
        val lowerTangent: DCEL<Point2d, Unit, Unit>.Dart?

        val tangents = tri.outerFace.darts().filter {
            val p = it?.origin?.data
            val pNext = it?.next?.origin?.data
            val pPrev = it?.prev?.origin?.data
            p != null && pNext != null && pPrev != null && isSameSide(pk, p, pNext, pPrev)
        }

        if (tangents.size == 2) {
            val newV = tri.Vertex(data = pk)

            // The following is safe because the filter that creates tangents guarantees that all the various properties
            // are not null, so there is no point in re-checking them.
            val lht = isLeftHandTurn(pk, tangents[0]!!.origin!!.data as Point2d, tangents[0]!!.next!!.origin!!.data as Point2d)

            upperTangent = if (lht) tangents[0] else tangents[1]
            lowerTangent = if (lht) tangents[1] else tangents[0]

            // Now modify the DCEL
            val newFace = tri.Face()

            val upperEdge = tri.Edge()
            val upperDart1 = tri.Dart(edge = upperEdge, origin = newV, face = newFace)
            val upperDart2 = tri.Dart(edge = upperEdge, origin = upperTangent.origin, face = tri.outerFace, twin = upperDart1)

            val lowerEdge = tri.Edge()
            val lowerDart1 = tri.Dart(edge = lowerEdge, origin = lowerTangent.origin, face = newFace)
            val lowerDart2 = tri.Dart(edge = lowerEdge, origin = newV, face = tri.outerFace, twin = lowerDart1)

            upperTangent.prev?.makeNext(upperDart2)
            upperDart2.makeNext(lowerDart2)

            upperDart1.makeNext(upperTangent)
            lowerTangent.prev?.makeNext(lowerDart1)
            lowerDart1.makeNext(upperDart1)

            lowerDart2.makeNext(lowerTangent)

            upperDart1.cycle().forEach { d -> d.face = newFace }
            newFace.aDart = upperDart1
            tri.outerFace.aDart = lowerDart1

//            var splitFromDart = upperDart1
//            while (splitFromDart.next?.next?.next != splitFromDart) {
//                val splitToDart = splitFromDart.next?.next
//                if (splitToDart != null && splitFromDart.prev?.twin != null) {
//                    tri.splitFace(splitFromDart, splitToDart);
//                    splitFromDart = splitFromDart?.prev?.twin as DCEL<Point2d, Unit, Unit>.Dart;
//                } else {
//                    break;
//                }
//            }
        }
    }

    return tri
}

/**
 * Creates a DCEL representing the triangle abc, sorted into counter-clockwise order
 */
fun getCCWTriangle(a: Point2d, b: Point2d, c: Point2d): DCEL<Point2d, Unit, Unit> {

    // DCEL container:
    val tri = DCEL<Point2d, Unit, Unit>()

    // Check if a to b to c is counter-clockwise or clockwise:
    val ccw = isLeftHandTurn(a, b, c)

    // Create the vertices:
    val v1 = tri.Vertex(data = a)
    val v2 = tri.Vertex(data = if (ccw) b else c)
    val v3 = tri.Vertex(data = if (ccw) c else b)

    // Create the inner face
    val inner = tri.Face()

    // The edges
    val edge12 = tri.Edge()
    val edge23 = tri.Edge()
    val edge31 = tri.Edge()

    // inner darts
    val dart12 = tri.Dart(edge = edge12, origin = v1, face = inner)
    val dart23 = tri.Dart(edge = edge23, origin = v2, face = inner, prev = dart12)
    val dart31 = tri.Dart(edge = edge31, origin = v3, face = inner, prev = dart23, next = dart12)

    // outer darts
    val dart21 = tri.Dart(edge = edge12, origin = v2, face = tri.outerFace, twin = dart12)
    val dart13 = tri.Dart(edge = edge31, origin = v1, face = tri.outerFace, twin = dart31, prev = dart21)
    val dart32 = tri.Dart(edge = edge23, origin = v3, face = tri.outerFace, twin = dart23, prev = dart13, next = dart21)

    return tri
}