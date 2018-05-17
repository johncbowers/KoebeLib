package tilings.ds

import geometry.ds.dcel.DCEL

abstract class Tile<VertexData, EdgeData, FaceData> (graphInfo : DCEL<VertexData, EdgeData, FaceData>? = null) {

    var graph = DCEL<VertexData, EdgeData, FaceData>()
    val frontier = ArrayList<TreeNode<DCEL<VertexData, EdgeData, FaceData>.Face>>()
    var root : TileNode<VertexData, EdgeData, FaceData, DCEL<VertexData, EdgeData, FaceData>.Face>?

    init {
        if (graphInfo != null) {
            graph = graphInfo
        }

        root = null
    }

    /*
    Returns a list of faces adjacent to the passed face
     */
    abstract fun collar (face : DCEL<VertexData, EdgeData, FaceData>.Face) :
            ArrayList<DCEL<VertexData, EdgeData, FaceData>.Face>

    /*
    Subdivides the graph, adds a new layer to the tree, and updates the frontier
     */
    abstract fun subdivide ()

    /*
    Returns a list of all of the faces produced by subdividing the passed face's parent
     */
    abstract fun superTile (face : DCEL<VertexData, EdgeData, FaceData>.Face) :
            ArrayList<DCEL<VertexData, EdgeData, FaceData>.Face>


}