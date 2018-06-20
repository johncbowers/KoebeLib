package tilings.language.algorithms

import geometry.ds.dcel.DCEL
import geometry.ds.dcel.DCELH
import tilings.ds.EdgeData
import tilings.ds.FaceData
import tilings.ds.VertexData

class TileFactory<VertexData, EdgeData, FaceData> () {

    init {

    }

    fun copyUnitTile (proto : DCELH<VertexData, EdgeData, FaceData>) : DCELH<Unit, Unit, Unit> {
        val copy = DCELH<Unit, Unit, Unit>()

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


            if (proto.darts[k].face == proto.holes[0]) {
                copy.Dart(origin = copy.verts[proto.verts.indexOf(proto.darts[k].origin)],
                        face = copy.holes[0])
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

    fun copyProtoTile (proto : DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>)
            : DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData> {
        val copy = DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>()

        // Set Vertices
        for (k in 0..proto.verts.size-1) {
            copy.Vertex( data = tilings.ds.VertexData() )
        }

        // Set Faces
        for (k in 0..proto.faces.size-1) {
            copy.Face( data = FaceData() )
            copy.faces[k].data.tileType = proto.faces[k].data.tileType
        }

        // Set Half Edges
        for (k in 0..proto.darts.size-1) {


            if (proto.darts[k].face == proto.holes[0]) {
                copy.Dart(origin = copy.verts[proto.verts.indexOf(proto.darts[k].origin)],
                        face = copy.holes[0])
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

    fun defineProtoTile (name : String, numVerts : Int)
            : Pair<String, DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>> {

        val proto = DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>()
        val verts = ArrayList<DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>.Vertex>()
        val darts = ArrayList<DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>.Dart>()
        val face = proto.Face(data = FaceData())
        face.data.tileType = name


        //Inner Darts
        for (k in 0..numVerts-1) {
            verts.add(proto.Vertex(data = VertexData()))
            if (k > 0) {
                darts.add(proto.Dart(origin = verts[verts.size-2], face = face))
                //darts.add(proto.Dart(origin = verts[verts.size-1], face = proto.holes[0]))
            }
        }
        darts.add(proto.Dart(origin = verts[verts.size-1], face = face))

        //Outer Darts
        for (k in 0..numVerts-1) {
            //destinations.put(darts[k], verts[(k+1) % numVerts]) // setting inner dart destinations
            darts[k].makeNext(darts[(k+1) % numVerts])
            darts.add(proto.Dart(origin = verts[k], face = proto.holes[0]))
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