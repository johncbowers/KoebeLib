package tilings.language.algorithms

import geometry.ds.dcel.DCELH
import tilings.language.ds.EscherProgramNew

class Subdivider2(
        val program: EscherProgramNew,
        val graph : DCELH<TVert, TEdge, TFace>,
        val depth : Int
 ) {


}