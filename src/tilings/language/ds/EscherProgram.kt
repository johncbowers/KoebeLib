package tilings.language.ds

import geometry.ds.dcel.DCEL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EscherProgram () {

    val protoTiles : MutableMap<String, DCEL<Unit, Unit, String>>
    val subdivisions : MutableMap<String, Subdivision<Unit, Unit, String>>
    var mainGraph : DCEL<Unit, Unit, String>


    init {
        protoTiles = mutableMapOf()
        subdivisions = mutableMapOf()
        mainGraph = DCEL()
    }

    fun addChild (subName: String, childType : String, vertices : ArrayList<String> ) {
        subdivisions[subName]?.addChild(childType, vertices)
    }

    fun addSplit (name : String, vertex1 : Int, vertex2 : Int, numEdges : Int) {
        subdivisions[name]?.addSplit(vertex1 = vertex1, vertex2 = vertex2, numEdges = numEdges)
    }

    fun addVertex (subName : String, vertexName : String) {
        subdivisions[subName]?.addVertex(vertexName)
    }

    fun makeProtoTile (name : String, numVerts : Int) : Boolean {

        if (protoTiles.containsKey(name)) {
            return false
        }

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
            darts[k].makeNext(darts[(k+1) % numVerts])
            darts.add(proto.Dart(origin = verts[k], face = proto.outerFace))
            darts[k].makeTwin(darts[darts.size-1])
            if (k > 0) {
                darts[darts.size-2].makePrev(darts[darts.size-1])
            }
        }
        darts[darts.size-1].makePrev(darts[numVerts])

        //Anchor
        face.aDart = darts[0]

        protoTiles.put(name, proto)

        return true
    }

    fun makeSubdivision (name : String) {
        subdivisions.put(name, Subdivision())
    }

    fun subdivide (proto : String, iter : Int) {

        mainGraph = copyProtoTile(protoTiles[proto]!!)

        for (k in 0..iter) {
            subdivideMain()
        }

    }

    private fun copyProtoTile (proto : DCEL<Unit, Unit, String>) : DCEL<Unit, Unit, String>{
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

    private fun subdivideFace (currFace : DCEL<Unit, Unit, String>.Face) {
        val subdivision = subdivisions[currFace.data]

        val edgeVerts = ArrayList<DCEL<Unit, Unit, String>.Vertex>()
        val interiorVerts = ArrayList<DCEL<Unit, Unit, String>.Vertex>()
        val childFaces = ArrayList<DCEL<Unit, Unit, String>.Face>()


        // Adding Vertices
        for (split in subdivision!!.splits) {
            for (k in 0..split.third) {
                edgeVerts.add(mainGraph.Vertex(data = Unit))
            }
        }

        for (k in edgeVerts.size..subdivision.vertices.size) {
            interiorVerts.add(mainGraph.Vertex(data = Unit))
        }

        //Adding Faces
        for (child in subdivision.children) {
            childFaces.add(mainGraph.Face(data = child.first))
        }

    }

    private fun subdivideMain () {
        for (k in 0..mainGraph.faces.lastIndex) {
            subdivideFace(mainGraph.faces[k])
        }
    }

}