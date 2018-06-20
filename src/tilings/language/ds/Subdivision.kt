package tilings.language.ds

class Subdivision () {

    val splits : ArrayList<Triple<Int, Int, Int>>
    var splitVerts : Int
    val vertices : ArrayList<String>
    val children : ArrayList<Pair<String, ArrayList<ArrayList<String>>>>

    init {
        splits = ArrayList()
        vertices = ArrayList()
        children = ArrayList()
        splitVerts = 0
    }

    fun addSplit (vertex1 : Int, vertex2 : Int, numEdges : Int) {
        splits.add(Triple(vertex1, vertex2, numEdges))
        splitVerts += numEdges - 1
    }

    fun addVertex (name : String) {
        vertices.add(name)
    }

    fun addChild (tileType : String, vertices : ArrayList<ArrayList<String>>) {
        children.add(Pair(tileType, vertices))
    }
}