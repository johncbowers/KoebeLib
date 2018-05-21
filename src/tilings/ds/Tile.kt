package tilings.ds

import geometry.ds.dcel.DCEL

/**
 * Parent class for all tilings
 */
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

    /**
     * Returns a list of faces that are adjacent to the given face
     *
     * Unsure if this is what a collar actually is
     */
    abstract fun collar (face : DCEL<VertexData, EdgeData, FaceData>.Face) :
            ArrayList<DCEL<VertexData, EdgeData, FaceData>.Face>

    /**
     *Subdivides the graph, adds a new layer to the tree, and updates the frontier
     */
    abstract fun subdivide ()

    /**
     *Returns a list of all of the faces produced by subdividing the passed face's parent
     */
    fun superTile (face : DCEL<VertexData, EdgeData, FaceData>.Face) :
            ArrayList<DCEL<VertexData, EdgeData, FaceData>.Face> {
        val superTile = ArrayList<DCEL<VertexData, EdgeData, FaceData>.Face>()


        if (root?.depthFirstSearch(face) != null) {
            takeFaces(superTile, root?.depthFirstSearch(face)!!.siblings()!!)
        }

        return  superTile
    }

    /**
     * Creates takes the faces from a list of Nodes and adds them to a list
     */
    protected fun takeFaces (faceList : ArrayList<DCEL<VertexData, EdgeData, FaceData>.Face>,
                           faceNodes : MutableList<TreeNode<DCEL<VertexData, EdgeData, FaceData>.Face>>) {
        for (node in faceNodes) {
            faceList.add(node.value)
        }
    }


}