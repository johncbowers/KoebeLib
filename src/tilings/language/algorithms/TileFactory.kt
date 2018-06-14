package tilings.language.algorithms

import geometry.ds.dcel.DCEL

class TileFactory<VertexData, EdgeData, FaceData> () {

    init {

    }

    fun copyUnitTile (proto : DCEL<VertexData, EdgeData, FaceData>) : DCEL<Unit, Unit, Unit> {
        val copy = DCEL<Unit, Unit, Unit>()

        // Set Vertices
        for (k in 0..proto.verts.size-1) {
            copy.Vertex( data = Unit )
        }

        // Set Faces
        for (k in 0..proto.faces.size-1) {
            copy.Face( data = Unit )
        }

        // Set Half Edges
        for (k in 0..proto.darts.size-1) {


            if (proto.darts[k].face == proto.outerFace) {
                copy.Dart(origin = copy.verts[proto.verts.indexOf(proto.darts[k].origin)],
                        face = copy.outerFace)
            } else {
                copy.Dart(origin = copy.verts[proto.verts.indexOf(proto.darts[k].origin)],
                        face = copy.faces[proto.faces.indexOf(proto.darts[k].face)])
            }

        }

        for (k in 0..proto.darts.size-1) {
            copy.darts[k].makeTwin(copy.darts[proto.darts.indexOf(proto.darts[k].twin)])
            copy.darts[k].makeNext(copy.darts[proto.darts.indexOf(proto.darts[k].next)])
            copy.darts[k].makePrev(copy.darts[proto.darts.indexOf(proto.darts[k].prev)])
        }

        // Set Anchor Points
        // Set Faces
        for (k in 0..proto.faces.size-1) {
            copy.faces[k].aDart = copy.darts[proto.darts.indexOf(proto.faces[k].aDart)]
        }


        return copy
    }

    fun copyProtoTile (proto : DCEL<Unit, Unit, String>) : DCEL<Unit, Unit, String> {
        val copy = DCEL<Unit, Unit, String>()

        // Set Vertices
        for (k in 0..proto.verts.size-1) {
            copy.Vertex( data = Unit )
        }

        // Set Faces
        for (k in 0..proto.faces.size-1) {
            copy.Face( data = proto.faces[k].data )
        }

        // Set Half Edges
        for (k in 0..proto.darts.size-1) {


            if (proto.darts[k].face == proto.outerFace) {
                copy.Dart(origin = copy.verts[proto.verts.indexOf(proto.darts[k].origin)],
                        face = copy.outerFace)
            } else {
                copy.Dart(origin = copy.verts[proto.verts.indexOf(proto.darts[k].origin)],
                        face = copy.faces[proto.faces.indexOf(proto.darts[k].face)])
            }

        }

        for (k in 0..proto.darts.size-1) {
            copy.darts[k].makeTwin(copy.darts[proto.darts.indexOf(proto.darts[k].twin)])
            copy.darts[k].makeNext(copy.darts[proto.darts.indexOf(proto.darts[k].next)])
            copy.darts[k].makePrev(copy.darts[proto.darts.indexOf(proto.darts[k].prev)])
        }

        // Set Anchor Points
        // Set Faces
        for (k in 0..proto.faces.size-1) {
            copy.faces[k].aDart = copy.darts[proto.darts.indexOf(proto.faces[k].aDart)]
        }


        return copy
    }

    fun defineProtoTile (name : String, numVerts : Int) : Pair<String, DCEL<Unit, Unit, String>> {

        val proto = DCEL<Unit, Unit, String>()
        val verts = ArrayList<DCEL<Unit, Unit, String>.Vertex>()
        val darts = ArrayList<DCEL<Unit, Unit, String>.Dart>()
        val face = proto.Face(data = name)


        //Inner Darts
        for (k in 0..numVerts-1) {
            verts.add(proto.Vertex(data = Unit))
            if (k > 0) {
                darts.add(proto.Dart(origin = verts[verts.size-2], face = face))
                //darts.add(proto.Dart(origin = verts[verts.size-1], face = proto.outerFace))
            }
        }
        darts.add(proto.Dart(origin = verts[verts.size-1], face = face))

        //Outer Darts
        for (k in 0..numVerts-1) {
            //destinations.put(darts[k], verts[(k+1) % numVerts]) // setting inner dart destinations
            darts[k].makeNext(darts[(k+1) % numVerts])
            darts.add(proto.Dart(origin = verts[k], face = proto.outerFace))
            //destinations.put(darts[darts.size-1], verts[(numVerts - 1 + k) % numVerts])
            darts[k].makeTwin(darts[darts.size-1])
            if (k > 0) {
                darts[darts.size-2].makePrev(darts[darts.size-1])
            }
        }
        darts[darts.size-1].makePrev(darts[numVerts])

        //Anchor
        face.aDart = darts[0]

        return Pair(name, proto)
    }
}