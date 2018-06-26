package tilings.language.ds

import geometry.ds.dcel.DCEL
import geometry.ds.dcel.DCELH
import tilings.ds.EdgeData
import tilings.ds.FaceData
import tilings.ds.TreeNode
import tilings.ds.VertexData
import tilings.language.algorithms.Subdivider
import tilings.language.algorithms.TileFactory

class EscherProgramNew () {

    val protoTiles : MutableMap<String, DCELH<VertexData, EdgeData, FaceData>>
    val subdivisions : MutableMap<String, Subdivision>
    val tileFactory : TileFactory<VertexData, EdgeData, FaceData>
    val graphs : MutableMap<String, DCELH<VertexData, EdgeData, FaceData>>
    var mainGraph : DCELH<VertexData, EdgeData, FaceData>


    init {
        protoTiles = mutableMapOf()
        subdivisions = mutableMapOf()
        tileFactory = TileFactory()
        graphs = mutableMapOf()
        mainGraph = DCELH()

        //destinations = mutableMapOf()
    }

    fun addChild (subName: String, childType : String, vertices : ArrayList<ArrayList<String>> ) {
        subdivisions[subName]?.addChild(childType, vertices)
    }

    fun addSplit (name : String, vertex1 : Int, vertex2 : Int, numEdges : Int) {
        subdivisions[name]?.addSplit(vertex1 = vertex1, vertex2 = vertex2, numEdges = numEdges)
    }

    fun addVertex (subName : String, vertexName : String) {
        subdivisions[subName]?.addVertex(vertexName)
    }

    fun defineProtoTile (name : String, numVerts : MutableList<Int>) : Boolean {
        val tile = tileFactory.defineProtoTile(name, numVerts)

        if (protoTiles.containsKey(name)) {
            return false
        }
        else {
            protoTiles.put(tile.first, tile.second)
        }

        return true
    }

    fun defineSubdivision (name : String) {
        subdivisions.put(name, Subdivision())
    }

    fun subdivide (proto : String, iter : Int) {
        var tile = tileFactory.copyProtoTile(protoTiles[proto]!!)
        tile.faces[1].data.node = TreeNode(tile.faces[1], null)
        val subdivider = Subdivider(this, tile, iter)
        subdivider.start()
        mainGraph = subdivider.graph
    }


    //TODO Implement these methods
    fun collar () {

    }

    fun superTile () {

    }

}