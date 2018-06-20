package tilings.ds

import geometry.ds.dcel.DCEL
import geometry.ds.dcel.DCELH

class FaceData () {

    var node : TreeNode<DCELH<VertexData, EdgeData, FaceData>.Face>?
    var tileType : String?

    init {
        node = null
        tileType = null
    }


}