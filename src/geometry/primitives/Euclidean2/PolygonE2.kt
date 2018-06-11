package geometry.primitives.Euclidean2

import geometry.ds.dcel.DCEL

class PolygonE2(): DCEL<PointE2, SegmentE2, Unit>() {

    val mainFace = Face(data = Unit)
    /**
     * Constructs a polygon from a list of the coordinates of its vertices. By convention the vertex list vertices
     * should be specified in counter-clockwise order.
     */
    constructor(vertices: ArrayList<PointE2>): this() {

        val polygon_verts = vertices.map { this.Vertex(data = it) } // Create all of the vertices
        val polygon_edges = vertices.mapIndexed { idx, it -> this.Edge(data = SegmentE2(vertices[idx], vertices[(idx + 1) % vertices.size])) } // Create all the edges
        val polygon_ccw_darts = vertices.mapIndexed { idx, it -> this.Dart(origin = polygon_verts[idx], edge = polygon_edges[idx], face = mainFace) }
        val polygon_cw_darts = vertices.mapIndexed { idx, it -> this.Dart(origin = polygon_verts[(idx + 1) % polygon_verts.size], edge = polygon_edges[idx], face = this.outerFace) }

        polygon_ccw_darts.forEachIndexed { idx, it -> it.twin = polygon_cw_darts[idx] }
        polygon_ccw_darts.forEachIndexed { idx, it -> it.next = polygon_ccw_darts[(idx + 1) % polygon_ccw_darts.size] }
        polygon_cw_darts.forEachIndexed { idx, it -> it.prev = polygon_ccw_darts[(idx + 1) % polygon_cw_darts.size] }

    }
}