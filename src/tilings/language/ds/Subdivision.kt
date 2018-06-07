package tilings.language.ds

class Subdivision<VertexData, EdgeData, FaceData> () {

    val splits : ArrayList<Triple<Int, Int, Int>>
    val vertices : ArrayList<String>
    val children : ArrayList<Pair<String, ArrayList<String>>>

    init {
        splits = ArrayList()
        vertices = ArrayList()
        children = ArrayList()
    }

    fun addSplit (vertex1 : Int, vertex2 : Int, numEdges : Int) {
        splits.add(Triple(vertex1, vertex2, numEdges))
    }

    fun addVertex (name : String) {
        vertices.add(name)
    }

    fun addChild (tileType : String, vertices : ArrayList<String>) {
        children.add(Pair(tileType, vertices))
    }
}