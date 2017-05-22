package geometry.primitives.Spherical2

import geometry.primitives.*;
import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.Euclidean3.VectorE3
import geometry.primitives.Euclidean3.DirectionE3
import geometry.primitives.Euclidean3.least_dominant

/**
 * Created by johnbowers on 5/20/17.
 *
 * Code is adapted from Ghali's Introduction to Geometric Computing, Springer, 2008
 */

class PointS2(val x: Double = 1.0, val y:Double = 0.0, val z:Double = 0.0) {

    constructor (v: VectorE3) : this(v.x, v.y, v.z)

    operator fun unaryMinus() = antipode()

    operator override fun equals(p:Any?) =
            this === p ||
            p != null &&
            p is PointS2 &&
            are_dependent(x, y, z, p.x, p.y, p.z) &&
            inner_product(x, y, z, p.x, p.y, p.z) > 0

    fun antipode() = PointS2(-x, -y, -z)

    // val vectorE3: VectorE3 get() = VectorE3(x, y, z)
    val directionE3: DirectionE3 get() = DirectionE3(VectorE3(x, y, z))
}


