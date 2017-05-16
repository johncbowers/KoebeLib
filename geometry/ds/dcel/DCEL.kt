package geometry.ds.dcel

import java.util.ArrayList;
import geometry.ds.dcel.DCEL.Face



/**
 * Created by John C. Bowers on 5/13/17.
 */
public class DCEL<VertexData, EdgeData, FaceData> {

    val verts = ArrayList<Vertex>()
    val darts = ArrayList<Dart>()
    val edges = ArrayList<Edge>()
    val faces = ArrayList<Face>()

    internal val _vertData = mutableMapOf<Vertex, VertexData?>()
    internal val _edgeData = mutableMapOf<Edge, EdgeData?>()
    internal val _faceData = mutableMapOf<Face, FaceData?>()

    val outerFace: Face

    init {
        outerFace = Face()
    }

    fun splitFace(e1: Dart, e2: Dart, newEdgeData: EdgeData? = null, newFaceData: FaceData? = null) {
        if (e1.face != e2.face || e1.next == e2 || e1.prev == e2 || e1 == e2) return


        // 1. Create a new face for the second half of the split.
        val newFace = Face(data = newFaceData)

        // 2. Create new darts for the splitting edge:
        val e = Edge(data = newEdgeData)
        val s1 = Dart(edge = e, origin = e2.origin, face = e1.face)
        val s2 = Dart(edge = e, origin = e1.origin, face = newFace, twin = s1)

        // 3. Insert your new darts into the cyclic lists:
        e2.prev?.makeNext(s1)
        e1.prev?.makeNext(s2)
        s1.makeNext(e1)
        s2.makeNext(e2)

        // 4. Make sure that all the darts point to the right face
        e2.cycle().forEach { it.face = newFace }

        // 5. Make sure that each face correctly refers to an incident half-edge
        newFace.aDart = e2
        e1.face?.aDart = e1
    }

    inner class Vertex(var aDart: Dart? = null, data : VertexData? = null) {

        init {
            verts.add(this)
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
            darts.add(this)
            edge?.aDart = this
            origin?.aDart = this
            face?.aDart = this

            prev?.next = this
            next?.prev = this
            twin?.twin = this
        }

        val dest: Vertex?
            get() = twin?.origin

        /**
         * Convenience method to make this the prev of newNext (sets two pointers)
         */
        fun makeNext(newNext: Dart) {
            next = newNext
            newNext.prev = this
        }

        /**
         * Convenience method to make this a twin of newTwin (sets two pointers)
         */
        fun makeTwin(newTwin: Dart) {
            twin = newTwin
            newTwin.twin = this
        }

        /**
         * Convenience method to make this the next of newPrev (sets two pointers)
         */
        fun makePrev(newPrev: Dart) {
            prev = newPrev
            newPrev.next = this
        }

        /**
         * Get the cycle of edges starting from this (follows next pointers)
         */
        fun cycle(): ArrayList<Dart> {
            var darts = ArrayList<Dart>()
            var curDart: Dart? = this
            if (curDart != null) {
                do {
                    darts.add(curDart as Dart)
                    curDart = curDart.next
                } while (curDart != this && curDart != null)
            }
            return darts
        }

        /**
         * Get the cycle of edges starting from this (follows prev pointers)
         */
        fun reverse_cycle(): ArrayList<Dart> {
            var darts = ArrayList<Dart>()
            var curDart: Dart? = this
            if (curDart != null) {
                do {
                    darts.add(curDart as Dart)
                    curDart = curDart.prev
                } while (curDart != this && curDart != null)
            }
            return darts
        }
    }

    inner class Edge(var aDart: Dart? = null, data: EdgeData? = null) {

        init {
            edges.add(this)
            _edgeData.put(this, data)
        }

        var data: EdgeData?
            get() = _edgeData.get(this)
            set(value) { _edgeData.put(this, value) }
    }

    inner class Face(var aDart: Dart? = null, data: FaceData? = null) {
        init {
            faces.add(this)
            _faceData.put(this, data)
        }

        /**
         * @return The cycle of dart incident this face.
         */
        fun darts(): ArrayList<Dart> {
            var darts = ArrayList<Dart>()
            var curDart = aDart
            if (curDart != null) {
                do {
                    darts.add(curDart as Dart)
                    curDart = curDart.next
                } while (curDart != aDart && curDart != null)
            }
            return darts
        }

        var data: FaceData?
            get() = _faceData.get(this)
            set(value) { _faceData.put(this, value) }
    }
}

