package tilings.ds

import geometry.ds.dcel.DCELH

class TilingVertex (var level : Int = 0)

class TilingEdge ()

class TilingFace (
    var node : TreeNode<DCELH<TilingVertex, TilingEdge, TilingFace>.Face>? = null,
    var tileType : String? = null
)