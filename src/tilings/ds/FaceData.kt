package tilings.ds

import geometry.ds.dcel.DCEL

class FaceData () {

    var node : TreeNode<DCEL<VertexData, EdgeData, FaceData>.Face>?
    var tileType : String?

    init {
        node = null
        tileType = null
    }


}