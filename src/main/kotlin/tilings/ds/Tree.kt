package tilings.ds

import geometry.ds.dcel.DCEL
import java.util.*

/**
 * Simple tree node structure with children and parents.
 */
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

    /**
     * Returns a list of all of the children of the node's parent
     */
    fun siblings () : MutableList<TreeNode<Data>>? {
        return parent?.children
    }

}

/**
 * More specific node that allows for depth first search
 *
 * I do not think I actually need this after looking at it more
 */
class TileNode<VertexData, EdgeData, FaceData, ValueData> (valueInc : ValueData, parentNode: TreeNode<ValueData>? = null,
                      childNodes : MutableList<TreeNode<ValueData>>? = null)
    : TreeNode<ValueData> (valueInc, parentNode, childNodes) {


    /**
     * Finds the node containing the given face using a traditional DFS on a the tree
     */
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