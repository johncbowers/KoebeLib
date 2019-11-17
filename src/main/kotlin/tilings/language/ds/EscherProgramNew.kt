package tilings.language.ds

import geometry.ds.dcel.DCELH
import tilings.ds.TilingVertex
import tilings.ds.TilingFace
import tilings.ds.TilingEdge
import tilings.ds.TreeNode
import tilings.language.algorithms.Subdivider
import tilings.language.algorithms.TileFactory

class EscherProgramNew () {

    val protoTiles = mutableMapOf<String, DCELH<TilingVertex, TilingEdge, TilingFace>>()
    val subdivisions = mutableMapOf<String, Subdivision>()
    val tileFactory = TileFactory<TilingVertex, TilingEdge, TilingFace>()
    val graphs = mutableMapOf<String, DCELH<TilingVertex, TilingEdge, TilingFace>>()
    var mainGraph = DCELH<TilingVertex, TilingEdge, TilingFace>()

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