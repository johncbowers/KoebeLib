package geometry.algorithms

import geometry.ds.dcel.DCEL;
import geometry.primitives.Euclidean2.*;
import geometry.primitives.Euclidean3.VectorE3


/**
 * Created by John Bowers on 5/15/17.
 */
//
//fun triangulateIncremental(s: ArrayList<PointE2>): DCEL<PointE2, Unit, Unit> {
//    s.sortWith(compareBy({it.x}, {it.y}))
//
//    val tri = getCCWTriangle(s[0], s[1], s[2])
//
//    for (k in 3..s.size-1) {
//        val pk = s[k]
//        val he = tri.outerFace?.aDart
//        val upperTangent: DCEL<PointE2, Unit, Unit>.Dart?
//        val lowerTangent: DCEL<PointE2, Unit, Unit>.Dart?
//
//        val tangents = tri.outerFace?.darts()?.filter {
//            val p = it?.origin?.data
//            val pNext = it?.next?.origin?.data
//            val pPrev = it?.prev?.origin?.data
//            p != null && pNext != null && pPrev != null && isSameSide(pk, p, pNext, pPrev)
//        }
//
//        if (tangents.size == 2) {
//            val newV = tri.Vertex(data = pk)
//
//            // The following is safe because the filter that creates tangents guarantees that all the various properties
//            // are not null, so there is no point in re-checking them.
//            val lht = isLeftHandTurn(pk, tangents[0]!!.origin!!.data as PointE2, tangents[0]!!.next!!.origin!!.data as PointE2)
//
//            upperTangent = if (lht) tangents[0] else tangents[1]
//            lowerTangent = if (lht) tangents[1] else tangents[0]
//
//            // Now modify the DCEL
//            val newFace = tri.Face()
//
//            val upperEdge = tri.Edge()
//            val upperDart1 = tri.Dart(edge = upperEdge, origin = newV, face = newFace)
//            val upperDart2 = tri.Dart(edge = upperEdge, origin = upperTangent.origin, face = tri.outerFace, twin = upperDart1)
//
//            val lowerEdge = tri.Edge()
//            val lowerDart1 = tri.Dart(edge = lowerEdge, origin = lowerTangent.origin, face = newFace)
//            val lowerDart2 = tri.Dart(edge = lowerEdge, origin = newV, face = tri.outerFace, twin = lowerDart1)
//
//            upperTangent.prev?.makeNext(upperDart2)
//            upperDart2.makeNext(lowerDart2)
//
//            upperDart1.makeNext(upperTangent)
//            lowerTangent.prev?.makeNext(lowerDart1)
//            lowerDart1.makeNext(upperDart1)
//
//            lowerDart2.makeNext(lowerTangent)
//
//            upperDart1.cycle().forEach { d -> d.face = newFace }
//            newFace.aDart = upperDart1
//            tri.outerFace.aDart = lowerDart1
//
////            var splitFromDart = upperDart1
////            while (splitFromDart.next?.next?.next != splitFromDart) {
////                val splitToDart = splitFromDart.next?.next
////                if (splitToDart != null && splitFromDart.prev?.twin != null) {
////                    tri.splitFace(splitFromDart, splitToDart);
////                    splitFromDart = splitFromDart?.prev?.twin as DCEL<PointE2, Unit, Unit>.Dart;
////                } else {
////                    break;
////                }
////            }
//        }
//    }
//
//    return tri
//}
//
///**
// * Creates a DCEL representing the triangle abc, sorted into counter-clockwise order
// */
//fun getCCWTriangle(a: PointE2, b: PointE2, c: PointE2): DCEL<PointE2, Unit, Unit> {
//
//    // DCEL container:
//    val tri = DCEL<PointE2, Unit, Unit>()
//
//    // Check if a to b to c is counter-clockwise or clockwise:
//    val ccw = isLeftHandTurn(a, b, c)
//
//    // Create the vertices:
//    val v1 = tri.Vertex(data = a)
//    val v2 = tri.Vertex(data = if (ccw) b else c)
//    val v3 = tri.Vertex(data = if (ccw) c else b)
//
//    // Create the inner face
//    val inner = tri.Face()
//
//    // The edges
//    val edge12 = tri.Edge()
//    val edge23 = tri.Edge()
//    val edge31 = tri.Edge()
//
//    // inner darts
//    val dart12 = tri.Dart(edge = edge12, origin = v1, face = inner)
//    val dart23 = tri.Dart(edge = edge23, origin = v2, face = inner, prev = dart12)
//    val dart31 = tri.Dart(edge = edge31, origin = v3, face = inner, prev = dart23, next = dart12)
//
//    // outer darts
//    val dart21 = tri.Dart(edge = edge12, origin = v2, face = tri.outerFace, twin = dart12)
//    val dart13 = tri.Dart(edge = edge31, origin = v1, face = tri.outerFace, twin = dart31, prev = dart21)
//    val dart32 = tri.Dart(edge = edge23, origin = v3, face = tri.outerFace, twin = dart23, prev = dart13, next = dart21)
//
//    return tri
//}

/**
 * Generates a unit size penny packing of circles in E2.
 *
 * @param nRows The number of triangle rows. Must be greater than 0.
 * @param nCols The number of triangle columns. Must be greater than 1.
 * @param x0 The x-coordinate for the index-(0,0) disk
 * @param y0 The y-coordinate for the index-(0,0) disk
 * @param radius The radius of each penny
 */
fun genPennyPacking(nRows: Int, nCols: Int, x0: Double = 0.0, y0: Double = 0.0, radius: Double = 0.5): DCEL<DiskE2, Unit, Unit> {

    return genHexGrid<DiskE2>(
            nRows,
            nCols,
            {i, j -> DiskE2(PointE2(x0 + if ((j % 2) == 0) i * 2 * radius else radius + i * 2 * radius, y0 + radius * Math.sqrt(3.0) * j), radius)}
    )

}

fun <VertexData> genHexGrid(nRows: Int, nCols: Int, vData: (Int, Int) -> VertexData ): DCEL<VertexData, Unit, Unit> {
    val packing = DCEL<VertexData, Unit, Unit>()

    // Bad inputs get a blank DCEL
    if (nRows <= 0 || nCols <= 1) return packing

    // Create the first edge
    val v0 = packing.Vertex(data = vData(0, 0))
    val v1 = packing.Vertex(data = vData(1, 0))
    val e01 = packing.Edge(data = Unit)
    val dart01 = packing.Dart(edge = e01, origin = v0, face = packing.outerFace)
    packing.Dart(edge = e01, origin = v1, face = packing.outerFace, twin = dart01, next = dart01, prev = dart01)

    // Extend the edge out nCols - 2 times
    var lastDart = dart01
    for (i in 2..nCols-1) {
        val v = packing.Vertex(data = vData(i, 0))
        val e = packing.Edge(data = Unit)
        val dartR = packing.Dart(edge = e, origin = lastDart.dest, face = packing.outerFace, prev = lastDart)
        packing.Dart(edge = e, origin = v, face = packing.outerFace, twin = dartR, prev = dartR, next = lastDart.twin)
        lastDart = dartR
    }

    // Add each of the remaining rows

    var lastRowStart : DCEL<VertexData, Unit, Unit>.Dart?  = dart01 // Start of the last row of darts on which we will add triangles

    for (j in 1..nRows-1) {

        val u = packing.Vertex(data = vData(0, j))
        val e = packing.Edge(data = Unit)
        val dartU = packing.Dart(edge = e, origin = lastRowStart?.origin, face = packing.outerFace, prev = lastRowStart?.prev)
        val dartD = packing.Dart(edge = e, origin = u, face = packing.outerFace, twin = dartU, prev = dartU, next = lastRowStart)

        var consDart = dartD

        for (i in 1..nCols-1) {

            val w = packing.Vertex(data = vData(i, j))

            // Create the two faces
            val f0 = packing.Face(data = Unit)
            val f1 = packing.Face(data = Unit)

            // Create the three edges
            val e0 = packing.Edge(data = Unit)
            val e1 = packing.Edge(data = Unit)
            val e2 = packing.Edge(data = Unit)

            // Store references to the base of the first triangle and its next, will be used to tie up prev/next refs
            val base0 = consDart.next
            val base1 = base0?.next
            val consPrev = consDart.prev

            if ((j % 2) == 0) {

                val d0U = packing.Dart( edge = e0, origin = base0?.origin, face = f0, prev = consDart )
                val d1L = packing.Dart( edge = e1, origin = w, face = f0, prev = d0U, next = consDart )
                consDart.face = f0

                val d0D = packing.Dart( edge = e0, origin = w, face = f1, twin = d0U, next = base0)
                val d2U = packing.Dart( edge = e2, origin = base1?.origin, face = f1, prev = base0, next = d0D)
                base0?.face = f1

                val d1R = packing.Dart( edge = e1, origin = consDart.origin, face = packing.outerFace, twin = d1L, prev = consPrev)
                val d2D = packing.Dart( edge = e2, origin = w, face = packing.outerFace, twin = d2U, prev = d1R, next = base1)

                consDart = d2D

            } else {


                // Create the darts, you will need to draw a picture if you want to follow this.
                val d0U = packing.Dart( edge = e0, origin = base0?.dest, face = f0, prev = base0, next = consDart )

                // Since we've closed the first face, set the corresponding dart's faces correctly
                consDart.face = f0
                base0?.face = f0

                // Create the remaining darts.
                val d0D = packing.Dart( edge = e0, origin = consDart.origin, face = f1, twin = d0U )
                val d1U = packing.Dart( edge = e1, origin = d0U.origin, face = f1, prev = d0D )
                val d2L = packing.Dart( edge = e2, origin = w, face = f1, prev = d1U, next = d0D )
                val d1D = packing.Dart( edge = e1, origin = w, face = packing.outerFace, twin = d1U, next = base1 )
                packing.Dart( edge = e2, origin = consDart.origin, face = packing.outerFace, twin = d2L, prev = consPrev, next = d1D )

                // Prepare for next iteration
                consDart = d1D
            }
        }

        lastRowStart = dartU.next
    }

    return packing
}


fun <EdgeData, FaceData> isPointE2InsideFace(p: PointE2, face: DCEL<PointE2, EdgeData, FaceData>.Face): Boolean {
    // TODO move these somewhere that makes sense
    fun signedSegmentAngleE2(p: PointE2, s: SegmentE2): Double {
        val U = VectorE3(s.source.x - p.x, s.source.y - p.y, 0.0)
        val V = VectorE3(s.target.x - p.x, s.target.y - p.y, 0.0)
        return Math.atan2(U.cross(V).z, U.dot(V))
    }

    return Math.round(face.darts().map {
        val u = it.origin?.data
        val v = it.dest?.data
        if (u != null && v != null) signedSegmentAngleE2(p, SegmentE2(u, v)) else 0.0
    }.sum()) != 0L
}

fun <EdgeData, FaceData> diskE2IntersectsFace(d: DiskE2, face: DCEL<PointE2, EdgeData, FaceData>.Face): Boolean {

    val center_is_in = isPointE2InsideFace(d.center, face)

    val intersects_side = face.darts().map {
        val u = it.origin?.data
        val v = it.dest?.data
        if (u != null && v != null) d.intersects(SegmentE2(u, v)) else false
    }.reduce { acc, b -> acc || b }

    // Its possible that the entire polygon is on the interior of the disk, which we can discover just by checking
    // whether any vertex is in the disk.
    val contains_a_vertex = face.darts().map {
        val u = it.origin?.data
        if (u != null) d.contains(u) else false
    }.reduce { acc, b -> acc || b }

    return center_is_in || intersects_side || contains_a_vertex
}

fun <EdgeData, FaceData> cookieCutterPennyPacking(vertices: ArrayList<PointE2>, radius: Double): DCEL<DiskE2, Unit, Unit> {

    val polygon = PolygonE2(vertices)



    return DCEL<DiskE2, Unit, Unit>()
}