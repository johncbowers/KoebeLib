package tilings.ds

import geometry.ds.dcel.DCELH

class EscherTile (graph : DCELH<TilingVertex, TilingEdge, TilingFace>) {

    val graph : DCELH<TilingVertex, TilingEdge, TilingFace>

    init {
        this.graph = graph
    }

    fun collar (face : DCELH<TilingVertex, TilingEdge, TilingFace>.Face)
            : MutableList<DCELH<TilingVertex, TilingEdge, TilingFace>.Face> {
        val returnList = mutableListOf<DCELH<TilingVertex, TilingEdge, TilingFace>.Face>()

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

    fun superTile (face : DCELH<TilingVertex, TilingEdge, TilingFace>.Face, steps : Int)
            : DCELH<TilingVertex, TilingEdge, TilingFace>.Face {
        var currFace = face
        var stepsTaken = 0
        while (currFace.data.node!!.parent != null && stepsTaken < steps) {
            stepsTaken++
            currFace = currFace.data!!.node!!.parent!!.value
        }

        return currFace
    }

    //TODO Determine if we actually want this function
    /*private fun dcelFromFace (face : DCEL<TilingVertex, TilingEdge, TilingFace>.Face) : DCEL<TilingVertex, TilingEdge, TilingFace> {
        val returnDCEL = DCEL<TilingVertex, TilingEdge, TilingFace>()



        return returnDCEL
    }*/
}
