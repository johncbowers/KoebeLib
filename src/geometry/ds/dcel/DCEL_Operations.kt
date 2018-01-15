package geometry.ds.dcel

/**
 * Created by johnbowers on 5/31/17.
 */
//    fun splitFace(e1: Dart, e2: Dart, newEdgeData: EdgeData? = null, newFaceData: FaceData? = null) {
//        if (e1.face != e2.face || e1.next == e2 || e1.prev == e2 || e1 == e2) return
//
//
//        // 1. Create a new face for the second half of the split.
//        val newFace = Face(data = newFaceData)
//
//        // 2. Create new darts for the splitting edge:
//        val e = Edge(data = newEdgeData)
//        val s1 = Dart(edge = e, origin = e2.origin, face = e1.face)
//        val s2 = Dart(edge = e, origin = e1.origin, face = newFace, twin = s1)
//
//        // 3. Insert your new darts into the cyclic lists:
//        e2.prev?.makeNext(s1)
//        e1.prev?.makeNext(s2)
//        s1.makeNext(e1)
//        s2.makeNext(e2)
//
//        // 4. Make sure that all the darts point to the right face
//        e2.cycle().forEach { it.face = newFace }
//
//        // 5. Make sure that each face correctly refers to an incident half-edge
//        newFace.aDart = e2
//        e1.face?.aDart = e1
//    }