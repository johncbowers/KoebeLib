package geometry.ds.dcel

import java.util.ArrayList;

/**
 * Created by John C. Bowers on 5/13/17.
 */
public class DCEL<VertexData, EdgeData, FaceData> {

    internal var _verts = ArrayList<Vertex>()
    internal var _darts = ArrayList<Dart>()
    internal var _edges = ArrayList<Edge>()
    internal var _faces = ArrayList<Face>()

    internal var _vertData = mutableMapOf<Vertex, VertexData?>()
    internal var _edgeData = mutableMapOf<Edge, EdgeData?>()
    internal var _faceData = mutableMapOf<Face, FaceData?>()

    inner class Vertex(var aDart: Dart? = null, data : VertexData) {

        init {
            _verts.add(this)
            _vertData.put(this, data)
        }

        /**
         * Returns a counter-clockwise list of the outgoing darts from an arbitrary starting point.
         */
        fun edges(): ArrayList<Edge?> {
            var edges = ArrayList<Edge?>()
            var curDart = aDart
            if (curDart == null || curDart.prev == null) return edges
            do {
                edges.add(curDart?.edge)
                curDart = curDart!!.prev!!.twin
            } while (curDart !== aDart && curDart != null && curDart.prev != null)
            return edges
        }

        /**
         * Returns a counter-clockwise list of the outgoing darts from an arbitrary starting point.
         */
        fun outDarts(): ArrayList<Dart?> {
            var darts = ArrayList<Dart?>()
            var curDart = aDart
            if (curDart == null || curDart.prev == null) return darts
            do {
                darts.add(curDart)
                curDart = curDart!!.prev!!.twin
            } while (curDart !== aDart && curDart != null && curDart.prev != null)
            return darts
        }

        /**
         * Returns a counter-clockwise list of the incoming darts from an arbitrary starting point.
         */
        fun inDarts(): ArrayList<Dart?> {
            var darts = ArrayList<Dart?>()
            var curDart = aDart
            if (curDart == null || curDart.prev == null || curDart.twin == null) return darts
            do {
                darts.add(curDart!!.twin)
                curDart = curDart!!.prev!!.twin
            } while (curDart !== aDart && curDart != null && curDart.prev != null && curDart.twin != null)
            return darts
        }

        var data: VertexData?
            get() = _vertData.get(this)
            set(value) { _vertData.put(this, value) }
    }

    inner class Dart(
                     var edge: Edge,
                     var origin: Vertex? = null,
                     var face: Face? = null,

                     var prev: Dart? = null,
                     var next: Dart? = null,
                     var twin: Dart? = null
                    )
    {
        init {
            _darts.add(this)
            edge?.aDart = this
            origin?.aDart = this
            face?.aDart = this

            prev?.next = this
            next?.prev = this
            twin?.twin = this
        }

    }

    inner class Edge(var aDart: Dart? = null, data: EdgeData) {

        init {
            _edges.add(this)
            _edgeData.put(this, data)
        }

        var data: EdgeData?
            get() = _edgeData.get(this)
            set(value) { _edgeData.put(this, value) }
    }

    inner class Face(var aDart: Dart? = null, data: FaceData) {
        init {
            _faces.add(this)
            _faceData.put(this, data)
        }

        /**
         * @return The cycle of dart incident this face.
         */
        fun darts(): ArrayList<Dart?> {
            var darts = ArrayList<Dart?>()
            var curDart = aDart
            if (curDart == null) return darts
            do {
                darts.add(curDart)
                curDart = curDart!!.next
            } while (curDart !== aDart && curDart != null)
            return darts
        }

        var data: FaceData?
            get() = _faceData.get(this)
            set(value) { _faceData.put(this, value) }
    }
}

