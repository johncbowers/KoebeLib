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
            if (proto.holes.contains(proto.faces[k])) {
                copy.holes.add(copy.faces[k])
            }
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


        //Add hole faces to the holes list
        for (k in 0..proto.faces.lastIndex) {

            // Add hole darts to faces
            if (proto.faces[k].holeDarts.size > 0) {
                for (dart in proto.faces[k].holeDarts) {
                    copy.faces[k].holeDarts.add(copy.darts[proto.darts.indexOf(dart)])
                }
            }
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
            if (proto.holes.contains(proto.faces[k])) {
                copy.holes.add(copy.faces[k])
            }
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


        //Add hole faces to the holes list
        for (k in 0..proto.faces.lastIndex) {

            // Add hole darts to faces
            if (proto.faces[k].holeDarts.size > 0) {
                for (dart in proto.faces[k].holeDarts) {
                    copy.faces[k].holeDarts.add(copy.darts[proto.darts.indexOf(dart)])
                }
            }
        }


        return copy
    }

    fun defineProtoTile (name : String, numVerts : MutableList<Int>)
            : Pair<String, DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>> {

        val proto = DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>(FaceData())
        val verts = ArrayList<DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>.Vertex>()
        val darts = ArrayList<DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>.Dart>()
        val face = proto.Face(data = FaceData())
        face.data.tileType = name


        //Inner Darts
        for (k in 0..numVerts[0]-1) {
            verts.add(proto.Vertex(data = VertexData()))
            if (k > 0) {
                darts.add(proto.Dart(origin = verts[verts.size-2], face = face))
                //darts.add(proto.Dart(origin = verts[verts.size-1], face = proto.holes[0]))
            }
        }
        darts.add(proto.Dart(origin = verts[verts.size-1], face = face))

        //Outer Darts
        for (k in 0..numVerts[0]-1) {
            darts[k].makeNext(darts[(k+1) % numVerts[0]])
            darts.add(proto.Dart(origin = verts[k], face = proto.holes[0]))
            darts[(numVerts[0] + k - 1) % numVerts[0]].makeTwin(darts[darts.size-1])
            if (k > 0) {
                darts[darts.size-2].makeNext(darts[darts.size-1])
            }
        }
        darts[darts.size-1].makeNext(darts[numVerts[0]])

        /*//Anchor
        face.aDart = darts[0]*/

        //Add Holes -------------------------------------------------------
        val holeVerts = ArrayList<DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>.Vertex>()
        val holeDarts = ArrayList<DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>.Dart>()
        var holeFace : DCELH<tilings.ds.VertexData, tilings.ds.EdgeData, tilings.ds.FaceData>.Face
        for (k in 1..numVerts.lastIndex) {
            holeFace = proto.Face( data = FaceData() )
            proto.holes.add(holeFace)
            //Inner Darts
            for (j in 0..numVerts[k]-1) {
                holeVerts.add(proto.Vertex( data = VertexData() ))
                if (j > 0) {
                    holeDarts.add(proto.Dart(origin = holeVerts[holeVerts.size-2], face = face))
                }
            }
            holeDarts.add(proto.Dart(origin = holeVerts[holeVerts.size-1], face = face))

            //Outer Darts
            for (j in 0..numVerts[k]-1) {
                holeDarts[j].makeNext(holeDarts[(j+1) % numVerts[k]])
                holeDarts.add(proto.Dart(origin = holeVerts[j], face = proto.holes[k]))
                holeDarts[(numVerts[k] + j - 1) % numVerts[k]].makeTwin(holeDarts[holeDarts.size-1])
                if (j > 0) {
                    holeDarts[holeDarts.size-2].makePrev(holeDarts[holeDarts.size-1])
                }
            }
            holeDarts[holeDarts.size-1].makePrev(holeDarts[numVerts[k]])

            //Anchor
            holeFace.aDart = holeDarts[numVerts[k]]
            face.holeDarts.add(holeFace!!.aDart!!.twin!!.next!!)


            holeVerts.clear()
            holeDarts.clear()

        }

        //Anchor
        face.aDart = darts[0]

        return Pair(name, proto)
    }
}