package geometry.primitives.OrientedProjective3

import geometry.primitives.Euclidean3.*
import geometry.primitives.determinant
/**
 * Created by johnbowers on 5/25/17.
 */

class LineOP3(
        val a : Double, // p01
        val b : Double, // p02
        val c : Double, // p03
        val d : Double, // p12
        val e : Double, // p13
        val f : Double // p23
)
{

    constructor(p1: PointOP3, p2: PointOP3): this(
            determinant(p1.hx, p1.hy, p2.hx, p2.hy), // p01 
            determinant(p1.hx, p1.hz, p2.hx, p2.hz), // p02
            determinant(p1.hx, p1.hw, p2.hx, p2.hw), // p03
            determinant(p1.hy, p1.hz, p2.hy, p2.hz), // p12
            determinant(p1.hy, p1.hw, p2.hy, p2.hw), // p13
            determinant(p1.hz, p1.hw, p2.hz, p2.hw)  // p23
    )
    
    val p01 = a
    val p02 = b
    val p03 = c
    val p12 = d
    val p13 = e 
    val p23 = f
    
    fun getIntersectionWithUnit2Sphere(): List<PointE3> {
        val retList = mutableListOf<PointE3>()
        
        // TODO Decide if this should be done with homogeneous coordinates
        val v = VectorE3(-p03, -p13, -p23)
        val m = VectorE3( p12, -p02,  p01)
        
        // TODO (Sarah) Compute the intersections of this line with the 2-sphere: 
        
        return retList
    }
    
}