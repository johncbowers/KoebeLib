package geometry.ds.dcel

import java.util.ArrayList;
import geometry.ds.dcel.DCEL.Face



/**
 * DCEL with holes. 
 * 
 * Created by John C. Bowers on 5/13/17.
 */
open class DCELH<VertexData, EdgeData, FaceData>(outerFaceData: FaceData? = null) {

    val verts = mutableListOf<Vertex>()
    val darts = mutableListOf<Dart>()
    val edges = mutableListOf<Edge>()
    val faces = mutableListOf<Face>()

    val holes: MutableList<Face>


    /**
    * Creates the dual graph of a DCEL
     * @TODO This was copied from the DCEL and needs to be checked for correctness.
    */
    fun dualHull() : DCELH<FaceData, EdgeData, VertexData> {
        val dualFtoV = mutableMapOf<DCELH<VertexData, EdgeData, FaceData>.Face, DCELH<FaceData, EdgeData, VertexData>.Vertex>();
        val dualEtoE = mutableMapOf<DCELH<VertexData, EdgeData, FaceData>.Edge, DCELH<FaceData, EdgeData, VertexData>.Edge>();
        val dualVtoF = mutableMapOf<DCELH<VertexData, EdgeData, FaceData>.Vertex, DCELH<FaceData, EdgeData, VertexData>.Face>();
        val dualDtoD = mutableMapOf<DCELH<VertexData, EdgeData, FaceData>.Dart, DCELH<FaceData, EdgeData, VertexData>.Dart>();
        val dual = DCELH<FaceData, EdgeData, VertexData>();
        for (f in faces) {
            val A = dual.Vertex(data = f.data);
            dualFtoV.put(f, A);
        }
        for (v in verts) {
            val dualFace = dual.Face(data = v.data);
            dualVtoF.put(v, dualFace);
        }
        for (he in darts) {
            val origin = dualFtoV.get(he.face);
            val incident = dualVtoF.get(he.origin);
            val newHe = dual.Dart();
            newHe.origin = origin;
            newHe.face = incident;
            dualDtoD.put(he, newHe);
        }
        for (edge in edges) {
            val dualEdge = dual.Edge(data = edge.data);
            dualEtoE.put(edge, dualEdge);
        }
        for (he in darts) {
            val dualHe = dualDtoD.get(he);
            dualHe?.next = dualDtoD.get(he.twin?.prev);
            dualHe?.prev = dualDtoD.get(he.next?.twin);
            dualHe?.twin = dualDtoD.get(he.twin);
            dualHe?.edge = dualDtoD.get(he)?.edge;
        }
        return dual;
    }

    init {
        holes = mutableListOf<Face>()
        if (outerFaceData != null) holes.add(Face(data = outerFaceData))
    }

    inner class Vertex(var aDart: Dart? = null, var data : VertexData) {

        init {
            verts.add(this)
        }

        /**
         * Returns a counter-clockwise list of the outgoing darts from an arbitrary starting point.
         */
        fun edges(): List<Edge> {
            return outDarts().map {
                val edge = it.edge
                if (edge != null) edge else throw MalformedDCELHException("Dart has null edge.")
            }
        }

        /**
         * Returns a counter-clockwise list of the outgoing darts from an arbitrary starting point.
         */
        fun outDarts(): List<Dart> {
            val darts = mutableListOf<Dart>()

            tailrec fun collectOutDarts(curr: Dart, orig: Dart) {
                darts.add(curr)
                val next = curr.prev?.twin
                if (next == null) {
                    throw MalformedDCELHException("A dart has a null twin pointer.")
                } else if (next != orig) {
                    collectOutDarts(next, orig)
                }
            }

            val startDart = this.aDart

            if (startDart != null) {
                collectOutDarts(startDart, startDart)
                return darts
            }  else {
                throw MalformedDCELHException("Vertex has a null outgoing dart (.aDart).")
            }
        }

        /**
         * Returns a counter-clockwise list of the incoming darts from an arbitrary starting point.
         */
        fun inDarts(): List<Dart> {
            val darts = mutableListOf<Dart>()

            tailrec fun collectInDarts(curr: Dart, orig: Dart) {
                darts.add(curr)
                val next = curr.twin?.prev
                if (next == null) {
                    throw MalformedDCELHException("A dart has a null prev pointer.")
                } else if (next != orig) {
                    collectInDarts(next, orig)
                }
            }

            val startDart = this.aDart?.twin

            if (startDart != null) {
                collectInDarts(startDart, startDart)
                return darts
            }  else {
                throw MalformedDCELHException("Vertex has a null outgoing dart or its outgoing dart has a null twin.")
            }
        }
        fun neighbors(): List<Vertex> {
            return inDarts().map {
                dart ->
                val origin = dart?.origin
                if (origin!= null) {
                    origin
                } else {
                    throw MalformedDCELHException("Dart has a null vertex")
                }
            }
        }
    }

    inner class Dart(
                     var edge: Edge? = null,
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

        val dest: Vertex? get() = twin?.origin

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
        fun makeTwin(newTwin: Dart?) {
            twin = newTwin
            if (newTwin != null)
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
        fun cycle(): List<Dart> {
            val cycle = mutableListOf<Dart>()

            tailrec fun collectCycle(curr: Dart, orig: Dart) {
                cycle.add(curr)
                val next = curr.next
                if (next == null) {
                    throw MalformedDCELHException("A dart has a null next pointer.")
                } else if (next != orig) {
                    collectCycle(next, orig)
                }
            }

            collectCycle(this, this)
            return cycle
        }

        /**
         * Get the cycle of edges starting from this (follows prev pointers)
         */
        fun reverse_cycle(): List<Dart> {
            return cycle().reversed()
        }


        /**
         * Introduces a zero area face between this dart and its twin.
         * The new edge data is given by eData and the new face's data
         * is given by fData.
         * The new edge data is used for the twin's new edge.
         */
        fun cut(eData: EdgeData, fData: FaceData) {
            // TODO
        }

        /**
         * Splits this dart (and twin) by the introduction of a vertex. The new vertex
         * data is given by vData, and the new edge data is given by eData.
         * The old dart is recycled to be the dart ccw before the new one.
         */
        fun split(vData: VertexData, eData: EdgeData) {
            // TODO
        }
    }

    inner class Edge(var aDart: Dart? = null, var data: EdgeData) {

        init {
            edges.add(this)
        }

        /**
         * Cuts this edge into two, by introducing a zero-area face.
         * The new edge data is given by eData and the new face's data
         * is given by fData.
         *
         * Note that this is just a convenience wrapper for the same
         * function defined on this edge's dart.
         */
        fun cut(eData: EdgeData, fData: FaceData) {
            aDart?.cut(eData, fData)
        }

        /**
         * Splits this edge by the introduction of a vertex. The new vertex
         * data is given by vData, and the new edge data is given by eData.
         *
         * Note that this is just a convenience wrapper for the same function
         * defined on this edge's dart.
         */
        fun split(vData: VertexData, eData: EdgeData) {
            aDart?.split(vData, eData)
        }

//  Turns out this is not so easy
//        The problem is that the operation can disconnect the graph in which case a face is no longer simple,
//          which is not allowed in our structure. Leaving it here for now but will need to rethink later.
//        fun remove() {
//
//            val dart0 = aDart
//            val dart1 = aDart?.twin
//
//            if (dart0 == null) throw MalformedDCELHException("Edge.aDart is null")
//            if (dart1 == null) throw MalformedDCELHException("Edge.aDart.twin is null")
//
//            val a = dart0.prev
//            val b = dart1.next
//            val c = dart1.prev
//            val d = dart0.next
//
//            if (a == null || b == null || c == null || d == null) {
//                throw MalformedDCELHException("DCEL contains null next/prev references.")
//            }
//
//
//            val f = if (dart0.face == outerFace) { faces.remove(dart1.face); outerFace }
//                    else { faces.remove(dart0.face); dart1.face }
//
//            f?.aDart = a
//
//            a.makeNext(b)
//            c.makeNext(d)
//
//            a.cycle().forEach { it.face = f }
//
//            darts.remove(dart0)
//            darts.remove(dart1)
//            edges.remove(this)
//        }
    }

    inner class Face(var aDart: Dart? = null, var data: FaceData) {

        val holeDarts = mutableListOf<Dart>()

        init {
            faces.add(this)
        }

        /**
         * @return The cycle of dart incident this face.
         */
        fun darts(): List<List<Dart>> {

            val outerCycle = aDart?.cycle()
            val retList = mutableListOf<List<Dart>>()

            if (outerCycle != null)
                retList.add(outerCycle)
            else
                throw MalformedDCELHException("Face.aDart is null")

            holeDarts.forEach { retList.add(it.cycle()) }

            return retList
        }

        fun vertices(): List<List<Vertex>> {
            val darts = darts()
            val vertices = darts.map { it.mapNotNull { it.origin } }
            if (darts.map { it.size } != vertices.map { it.size })
                throw MalformedDCELHException("A dart incident to this face has a null origin.")
            return vertices
        }
    }
}

class MalformedDCELHException(msg: String) : Exception(msg)