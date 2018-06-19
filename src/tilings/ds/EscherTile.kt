package tilings.ds

import geometry.ds.dcel.DCELH

class EscherTile (graph : DCELH<VertexData, EdgeData, FaceData>) {

    val graph : DCELH<VertexData, EdgeData, FaceData>

    init {
        this.graph = graph
    }

    fun collar (face : DCELH<VertexData, EdgeData, FaceData>.Face)
            : MutableList<DCELH<VertexData, EdgeData, FaceData>.Face> {
        val returnList = mutableListOf<DCELH<VertexData, EdgeData, FaceData>.Face>()

        for (dart in face.darts()[0]) {
            val vertex = dart.origin

            for (outDart in vertex!!.outDarts()) {
                if (outDart.face != face && !returnList.contains(outDart.face) && outDart.face != null) {
                    returnList.add(outDart.face!!)
                }
            }

        }

        return returnList

    }

    fun superTile (face : DCELH<VertexData, EdgeData, FaceData>.Face, steps : Int)
            : DCELH<VertexData, EdgeData, FaceData>.Face {
        var currFace = face
        var stepsTaken = 0
        while (currFace.data.node!!.parent != null && stepsTaken < steps) {
            stepsTaken++
            currFace = currFace.data!!.node!!.parent!!.value
        }

        return currFace
    }

    //TODO Determine if we actually want this function
    /*private fun dcelFromFace (face : DCEL<VertexData, EdgeData, FaceData>.Face) : DCEL<VertexData, EdgeData, FaceData> {
        val returnDCEL = DCEL<VertexData, EdgeData, FaceData>()



        return returnDCEL
    }*/
}
