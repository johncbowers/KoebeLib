package geometry.algorithms

import geometry.ds.dcel.*
import geometry.primitives.Euclidean3.*
import geometry.primitives.OrientedProjective3.*
import geometry.primitives.determinant

/**
 * Created by johnbowers on 6/1/17.
 */


/* Type Aliases */

typealias ConvexHull<PointT> = DCEL<PointT, Unit, Unit>

internal typealias CHVertex<PointT> = DCEL<PointT, Unit, Unit>.Vertex
internal typealias CHDart<PointT> = DCEL<PointT, Unit, Unit>.Dart
internal typealias CHEdge<PointT> = DCEL<PointT, Unit, Unit>.Edge
internal typealias CHFace<PointT> = DCEL<PointT, Unit, Unit>.Face

/**
 * A convenience wrapper for easily exposing the convex hull functionality to the Jython interpreter. (Could also be
 * used by the Kotlin code of course, but this is relatively unnecessary.
 */
class IncrementalConvexHullAlgorithms() {

    /* Functions for computing the convex hull in different geometries */
    fun computeOP3(points: List<PointOP3>): ConvexHull<PointOP3> = incrConvexHull(points, ::orientationPointOP3)
    fun computeE3(points: List<PointE3>): ConvexHull<PointE3> = incrConvexHull(points, ::orientationPointE3)

    /* Calling the addPoint function directly */
    fun addPointOP3(ch: ConvexHull<PointOP3>, p: PointOP3) {
        addPoint(ch, p, ::orientationPointOP3)
    }

    fun addPointE3(ch: ConvexHull<PointE3>, p: PointE3) {
        addPoint(ch, p, ::orientationPointE3)
    }
}

/* Public functions */

/**
 * Compute the 3D convex hull of a list of points. Since this method is generic, it also takes as input the appropriate
 * orientation test for tetrahedra constructed out of the generic PointT type. A point is inside the convex hull iff.
 * it has positive orientation with respect to each triangle of the hull.
 *
 * Currently assumes no four points are collinear (i.e. for no 4 points does orientation(p1, p2, p3, p4) return 0).
 * TODO Remove this assumption.
 *
 * @param points The list of points in some 3D point type PointT.
 * @param orientation The orientation test for PointT type. Should return a positive number for positive orientation and a negative number for negative orientation.
 * @return The convex hull of points as a DCEL.
 */
fun <PointT> incrConvexHull(
        points: List<PointT>,
        orientation: (PointT, PointT, PointT, PointT) -> Double
): ConvexHull<PointT> {

    if (points.size < 2) {
        return ConvexHull<PointT>()
    } else if (points.size == 3) {
        return incrConvexHull<PointT>(points[0], points[1], points[2])
    } else {
        val ch = incrConvexHull<PointT>(points[0], points[1], points[2], points[3], orientation)
        points.slice(4..points.lastIndex).forEach { point -> addPoint(ch, point, orientation) }
        return ch
    }
}

/* Helper Functions */

/**
 * @param face The face of a DCEL to check from.
 * @param p The point to check.
 * @param orientation The orientation function for the PointT type.
 * @return True if p is visible from face, false otherwise.
 */
internal fun <PointT> isVisible(
        face: CHFace<PointT>,
        p: PointT,
        orientation: (PointT, PointT, PointT, PointT) -> Double
): Boolean {

    val corners = face.darts()

    val p1 = corners[0].origin?.data
    val p2 = corners[1].origin?.data
    val p3 = corners[2].origin?.data

    if (p1 !== null && p2 !== null && p3 !== null) {
        return orientation(p1, p2, p3, p) < 0.0
    } else {
        throw MalformedDCELException("Convex Hull requires that .origin and .data are set.")
    }
}

/**
 * Adds a point to the convex hull. If the point is already inside the convex hull, it is discarded. Otherwise, the
 * convex hull is extended to include it.
 *
 * Assumes no four points are coplanar.
 */
internal fun <PointT> addPoint(
        ch: ConvexHull<PointT>,
        p: PointT,
        orientation: (PointT, PointT, PointT, PointT) -> Double
) {

    val isFaceVisible = mutableMapOf<CHFace<PointT>, Boolean>()
    val isDartVisible = mutableMapOf<CHDart<PointT>, Boolean>()

    ch.faces.forEach { face -> isFaceVisible.put(face, isVisible(face, p, orientation)) }
    ch.darts.forEach { dart -> isDartVisible.put(dart, isFaceVisible[dart.face] as Boolean)}

    val shadowBoundary = mutableListOf<CHDart<PointT>>()

    // Delete all visibile vertices, edges, darts, and faces, and add any
    // shadow darts to the shadow boundary list:
    ch.faces.removeIf { face -> isFaceVisible[face] as Boolean }
    ch.verts.removeIf {
        vertex ->
        // Get all the faces next to the vertex and check if all faces are visible
        // If so, this vertex must be removed
        vertex  .outDarts()
                .map { dart -> dart.face }
                .filter { face -> !(isFaceVisible[face] as Boolean) }
                .size == 0
    }
    ch.darts.removeIf { dart -> isDartVisible[dart] as Boolean }
    ch.edges.removeIf { edge -> (isDartVisible[edge.aDart] as Boolean) && (isDartVisible[edge.aDart?.twin] as Boolean)}

    val shadowEdges = ch.edges.filter {
        edge ->
        isDartVisible[edge?.aDart] as Boolean || isDartVisible[edge?.aDart?.twin] as Boolean
    }

    // TODO Finish from here.
}

/**
 * Returns the convex hull of four 3D points as a tetrahedron.
 *
 * Assumes the points are not coplanar.
 */
internal fun <PointT> incrConvexHull(
        p1: PointT,
        p2: PointT,
        p3: PointT,
        p4: PointT,
        orientation: (PointT, PointT, PointT, PointT) -> Double
): ConvexHull<PointT> {

    val ch = ConvexHull<PointT>()
    val orient = orientation(p1, p2, p3, p4)

    val v1 = ch.Vertex(data = if (orient > 0) p1 else p2)
    val v2 = ch.Vertex(data = if (orient > 0) p2 else p1)
    val v3 = ch.Vertex(data = p3)
    val v4 = ch.Vertex(data = p4)

    val e12 = ch.Edge(data = Unit)
    val e23 = ch.Edge(data = Unit)
    val e13 = ch.Edge(data = Unit)
    val e14 = ch.Edge(data = Unit)
    val e24 = ch.Edge(data = Unit)
    val e34 = ch.Edge(data = Unit)

    val f123 = ch.Face(data = Unit)
    val f134 = ch.Face(data = Unit)
    val f142 = ch.Face(data = Unit)
    val f243 = ch.Face(data = Unit)

    val d12 = ch.Dart(edge = e12, origin = v1, face = f123)
    val d23 = ch.Dart(edge = e23, origin = v2, face = f123, prev = d12)
    val d31 = ch.Dart(edge = e13, origin = v3, face = f123, prev = d23, next=d12)

    val d13 = ch.Dart(edge = e13, origin = v1, face = f134, twin = d31)
    val d34 = ch.Dart(edge = e34, origin = v3, face = f134, prev = d13)
    val d41 = ch.Dart(edge = e14, origin = v4, face = f134, prev = d34, next = d13)

    val d14 = ch.Dart(edge = e14, origin = v1, face = f142, twin = d41)
    val d42 = ch.Dart(edge = e24, origin = v4, face = f142, prev = d14)
    val d21 = ch.Dart(edge = e12, origin = v2, face = f142, prev = d42, next = d14, twin = d12)

    val d24 = ch.Dart(edge = e24, origin = v2, face = f243, twin = d42)
    val d43 = ch.Dart(edge = e34, origin = v4, face = f243, prev = d24, twin = d34)
    val d32 = ch.Dart(edge = e23, origin = v3, face = f243, prev = d43, next = d24, twin = d23)

    return ch
}

/**
 * Returns the convex hull of three 3D points. This is a degenerate convex hull with two triangular faces of opposite
 * orientations on top of one another.
 */
internal fun <PointT> incrConvexHull(
        p1: PointT,
        p2: PointT,
        p3: PointT
): ConvexHull<PointT> {

    val ch = ConvexHull<PointT>()

    val v1 = ch.Vertex(data = p1)
    val v2 = ch.Vertex(data = p2)
    val v3 = ch.Vertex(data = p3)

    val f123 = ch.Face(data = Unit)
    val f132 = ch.Face(data = Unit)

    val e12 = ch.Edge(data = Unit)
    val e23 = ch.Edge(data = Unit)
    val e13 = ch.Edge(data = Unit)

    val d12 = ch.Dart(edge = e12, origin = v1, face = f123)
    val d23 = ch.Dart(edge = e23, origin = v2, face = f123, prev = d12)
    val d31 = ch.Dart(edge = e13, origin = v3, face = f123, prev = d23, next = d12)

    val d13 = ch.Dart(edge = e13, origin = v1, face = f132, twin = d31)
    val d32 = ch.Dart(edge = e23, origin = v3, face = f132, prev = d13, twin = d23)
    val d21 = ch.Dart(edge = e12, origin = v2, face = f132, prev = d32, next = d13, twin = d12)

    return ch
}

/* Couple of built-in orientation tests */

fun orientationPointE3(p1: PointE3, p2: PointE3, p3: PointE3, p4: PointE3) =
    determinant(
            p1.x, p1.y, p1.z, 1.0,
            p2.x, p2.y, p2.z, 1.0,
            p3.x, p3.y, p3.z, 1.0,
            p4.x, p4.y, p4.z, 1.0
    )


fun orientationPointOP3(p1: PointOP3, p2: PointOP3, p3: PointOP3, p4: PointOP3) =
    determinant(
            p1.hx, p1.hy, p1.hz, p1.hw,
            p2.hx, p2.hy, p2.hz, p2.hw,
            p3.hx, p3.hy, p3.hz, p3.hw,
            p4.hx, p4.hy, p4.hz, p4.hw
    )

