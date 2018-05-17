package tilings.ds

import geometry.ds.dcel.DCEL
import java.util.*

open class TreeNode<Data> (valueInc : Data, parentNode: TreeNode<Data>? = null,
                      childNodes : MutableList<TreeNode<Data>>? = null) {
    var value : Data
    var parent : TreeNode<Data>?
    val children = mutableListOf<TreeNode<Data>>()

    init {
        value = valueInc
        parent = parentNode
        if(childNodes != null) {
            children.addAll(childNodes)
        }
    }

    fun siblings () : MutableList<TreeNode<Data>>? {
        return parent?.children
    }

}

class TileNode<VertexData, EdgeData, FaceData, ValueData> (valueInc : ValueData, parentNode: TreeNode<ValueData>? = null,
                      childNodes : MutableList<TreeNode<ValueData>>? = null)
    : TreeNode<ValueData> (valueInc, parentNode, childNodes) {

    fun depthFirstSearch (face : DCEL<VertexData, EdgeData, FaceData>.Face) :
            TreeNode<ValueData>? {
        val stack = Stack<TreeNode<ValueData>>()
        var retNode : TreeNode<ValueData>

        for (child in children) {
            stack.push(child)
        }

        while (!stack.empty()) {
            retNode = stack.pop()
            if (retNode.value == face) {
                return retNode;
            }

            for (child in retNode.children) {
                stack.push(child)
            }
        }

        return null;
    }
}