package geometry.algorithms

import geometry.ds.dcel.*
import geometry.primitives.Euclidean3.*
import geometry.primitives.OrientedProjective3.*
import geometry.primitives.Spherical2.*
import geometry.primitives.determinant
import java.util.Random
import java.lang.Math

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

    var generator = Random()

    /* Functions for computing the convex hull in different geometries */
    fun computeOP3(points: List<PointOP3>): ConvexHull<PointOP3> = incrConvexHull(points, ::orientationPointOP3)
    fun computeE3(points: List<PointE3>): ConvexHull<PointE3> = incrConvexHull(points, ::orientationPointE3)
    fun computeDiskS2(disks: List<DiskS2>): ConvexHull<DiskS2> = incrConvexHull(disks, ::orientationDiskS2)

    /* Calling the addPoint function directly */
    fun addPointOP3(ch: ConvexHull<PointOP3>, p: PointOP3) {
        addPoint(ch, p, ::orientationPointOP3)
    }

    fun addPointE3(ch: ConvexHull<PointE3>, p: PointE3) {
        addPoint(ch, p, ::orientationPointE3)
    }

    // Compute a convex hull generated from random points
    fun randomConvexHullE3( numPoints: Int): ConvexHull<PointE3> {
        if (numPoints < 4) {
            throw MalformedDCELException("DCEL has not been constructed properly.")
        }

        var points = mutableListOf<PointE3>()

        for (pt in 1..numPoints) {
            val theta = generator.nextDouble() * 2 * Math.PI
            val phi = generator.nextDouble() * Math.PI
            var x = 1.01 * Math.cos(theta) * Math.sin(phi)
            var y = 1.01 * Math.sin(theta) * Math.sin(phi)
            var z = 1.01 * Math.cos(phi)
            points.add(PointE3(x, y, z))
        }
        return computeE3(points)

    }

    // Compute a convex hull generated from random points
    fun randomConvexHullE3UpperHemisphere( numPoints: Int ): ConvexHull<PointE3> {
        if (numPoints < 4) {
            throw MalformedDCELException("DCEL has not been constructed properly.")
        }

        var points = mutableListOf<PointE3>()
        for (pt in 1..numPoints-1) {
            val theta = Random().nextDouble() * 2 * Math.PI
            val phi = Random().nextDouble() * Math.PI
            var x = 1.1 * Math.cos(theta) * Math.sin(phi)
            var y = 1.1 * Math.sin(theta) * Math.sin(phi)
            var z = Math.abs(1.1 * Math.cos(phi))
            points.add(PointE3(x, y, z))
        }
        points.add(PointE3(0.0, 0.0, -1.1))
        return computeE3(points)

    }

    // Compute a convex hull generated from random points with a gauranteed vertex of degree highDegree
    fun randomConvexHullE3WithHighDegreeVertex( numPoints: Int, highDegree: Int ): ConvexHull<PointE3> {
        if (numPoints < 4) {
            throw MalformedDCELException("DCEL has not been constructed properly.")
        }

        // The degree to guarantee
        val deg = Math.min(numPoints - 1, highDegree)

        // The number of remaining vertices to generate
        val n = numPoints - deg - 1

        // Generate n points on the upper hemisphere
        var points = mutableListOf<PointE3>()
        for (pt in 1..n) {
            val theta = Random().nextDouble() * 2 * Math.PI
            val phi = Random().nextDouble() * Math.PI
            val x = 1.1 * Math.cos(theta) * Math.sin(phi)
            val y = 1.1 * Math.sin(theta) * Math.sin(phi)
            val z = Math.abs(1.1 * Math.cos(phi))
            points.add(PointE3(x, y, z))
        }

        // Generate deg points on the disk:
        for (pt in 1..deg) {
            val theta = Random().nextDouble() * 2 * Math.PI
            val x = 1.1 * Math.cos(theta)
            val y = 1.1 * Math.sin(theta)
            val z = (Random().nextDouble() - 0.5)  * 0.00001 // Small perturbation to keep from having coplanar points
            points.add(PointE3(x, y, z))
        }

        // Add the south pole
        points.add(PointE3(0.0, 0.0, -1.1))
        return computeE3(points)

    }

    fun convexHullE3toConvexHullDiskS2(inHull: ConvexHull<PointE3>): ConvexHull<DiskS2> {

        val newHull = DCEL<DiskS2, Unit, Unit>()

        // Create maps between old and new convex hulls
        val oldToNewVerts = mutableMapOf<DCEL<PointE3, Unit, Unit>.Vertex, DCEL<DiskS2, Unit, Unit>.Vertex> ()
        val oldToNewDarts = mutableMapOf<DCEL<PointE3, Unit, Unit>.Dart, DCEL<DiskS2, Unit, Unit>.Dart> ()
        val oldToNewEdges = mutableMapOf<DCEL<PointE3, Unit, Unit>.Edge, DCEL<DiskS2, Unit, Unit>.Edge> ()
        val oldToNewFaces = mutableMapOf<DCEL<PointE3, Unit, Unit>.Face, DCEL<DiskS2, Unit, Unit>.Face> ()

        // Copy data from old hull to new hull
        for ( vert in inHull.verts ) {

            //figure out what circle has that point as its cap
            val p = PointOP3(vert.data)
            val disk = DiskS2(-p.hx, -p.hy, -p.hz, p.hw)
            oldToNewVerts[vert] = newHull.Vertex(data = disk)
        }
        for ( dart in inHull.darts ) {
            oldToNewDarts[dart] = newHull.Dart()
        }
        for ( edge in inHull.edges ) {
            oldToNewEdges[edge] = newHull.Edge(data = Unit)
        }
        for ( face in inHull.faces ) {
            oldToNewFaces[face] = newHull.Face(data = Unit)
        }

        // Add appropriate links between data members
        for (vert in inHull.verts) {
            oldToNewVerts[vert]?.aDart = oldToNewDarts[vert.aDart]
        }

        for (dart in inHull.darts) {
            oldToNewDarts[dart]?.edge = oldToNewEdges[dart.edge]
            oldToNewDarts[dart]?.origin = oldToNewVerts[dart.origin]
            oldToNewDarts[dart]?.face = oldToNewFaces[dart.face]

            oldToNewDarts[dart]?.twin = oldToNewDarts[dart.twin]
            oldToNewDarts[dart]?.prev = oldToNewDarts[dart.prev]
            oldToNewDarts[dart]?.next = oldToNewDarts[dart.next]
        }

        for (edge in inHull.edges) {
            oldToNewEdges[edge]?.aDart = oldToNewDarts[edge.aDart]
        }

        for (face in inHull.faces) {
            oldToNewFaces[face]?.aDart = oldToNewDarts[face.aDart]
        }
        return newHull
    }

    fun randomConvexHullDiskS2(numPoints: Int) = convexHullE3toConvexHullDiskS2(this.randomConvexHullE3(numPoints))
    fun randomConvexHullDiskS2UpperHemisphere(numPoints: Int) = convexHullE3toConvexHullDiskS2(this.randomConvexHullE3UpperHemisphere(numPoints))
    fun randomConvexHullDiskS2WithHighDegreeVertex( numPoints: Int, highDegree: Int ) = convexHullE3toConvexHullDiskS2(this.randomConvexHullE3WithHighDegreeVertex( numPoints, highDegree ))
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

    // First let's filter the faces to obtain a set of all faces that are visible from the
    // insertion point p
    val visibleFaces = ch.faces.filter { isVisible(it, p, orientation) }.toMutableSet()

    // Next, check if there are any visible faces, because if there are not, then this
    // point is already inside the convex hull, so there is nothing left to do--just return
    if (visibleFaces.size == 0) {
        return
    }

    // Otherwise, we need to get the set of shadowFaces as well as the darts that are visible and in shadow.
    val shadowFaces = ch.faces.filter { !isVisible(it, p, orientation) }.toMutableSet()
    val visibleDarts = ch.darts.filter { visibleFaces.contains(it.face) }.toMutableSet()
    val shadowDarts = ch.darts.filter { shadowFaces.contains(it.face) }.toMutableSet()

    // Any visible objects are not part of the new convex hull, so they need to be removed:
    ch.faces.removeIf { visibleFaces.contains(it) }
    ch.verts.removeIf {
        vertex ->
        // If this vertex is not incident to a shadowed face then all its incident faces are visible, so it
        // must be removed:
        vertex  .outDarts()
                .map { dart -> dart.face }
                .filter { face -> shadowFaces.contains(face) }
                .size == 0
    }
    ch.darts.removeIf { dart -> visibleDarts.contains(dart) }
    ch.edges.removeIf { edge -> visibleDarts.contains(edge.aDart) && visibleDarts.contains(edge.aDart?.twin)}

    // Next, let's collect the darts that are on the boundary, meaning each shadow dart whose twin is a visible dart.
    val shadowBoundaryDarts = shadowDarts.filter { dart -> visibleDarts.contains(dart.twin) }

    val v = ch.Vertex(data = p)

    // For each shadow boundary dart, we now create a triangle connected to it via its twin with the new vertex v.
    // Note, however, that this loop is not able to create the necessary edges for the new darts to/from v, nor
    // create the twins. That has to be completed in a final step.
    shadowBoundaryDarts.forEach {
        dart ->
        // Create triangle abc where ab is the twin of dart and c is vertex v
        val a = dart.dest
        val b = dart.origin
        val c = v

        val abc = ch.Face(data = Unit)

        val eab = dart.edge

        val dab = ch.Dart(edge = eab, origin = a, face = abc, twin = dart)
        val dbc = ch.Dart(origin = b, face = abc, prev = dab)
        val dca = ch.Dart(origin = c, face = abc, prev = dbc, next = dab)
    }

    tailrec fun nextShadowDart(dart: CHDart<PointT>?): CHDart<PointT>? {
        if (dart == null) return null
        else if (shadowBoundaryDarts.contains(dart.prev)) return dart.prev
        else return nextShadowDart(dart.prev?.twin)
    }

    val start = shadowBoundaryDarts[0]
    var curr = start
    do {
        val next = nextShadowDart(curr)

        if (next == null) {
            throw MalformedDCELException("DCEL has not been constructed properly.")
        }

        val edge = ch.Edge(data = Unit)

        curr.twin?.next?.edge = edge
        next.twin?.prev?.edge = edge
        edge.aDart = curr.twin?.next

        curr.twin?.next?.makeTwin(next.twin?.prev)

        curr = next
    } while (curr != start)

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
            p4.hx, p4.hy, p4.hz, p4. hw
    )

fun orientationDiskS2(d1: DiskS2, d2: DiskS2, d3: DiskS2, d4: DiskS2) =
        orientationPointOP3(d1.dualPointOP3, d2.dualPointOP3, d3.dualPointOP3, d4.dualPointOP3)
