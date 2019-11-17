package geometry.primitives.OrientedProjective3

import geometry.primitives.Euclidean3.VectorE3
import geometry.primitives.are_dependent
import geometry.primitives.inner_product

/**
 * Created by johnbowers on 5/22/17.
 */

class VectorOP3(val hx: Double, val hy: Double, val hz: Double, val hw: Double = 1.0) {

    constructor (v: VectorOP3) : this(v.hx, v.hy, v.hz, v.hw)
    constructor (v: VectorE3) : this(v.x, v.y, v.z)

    operator override fun equals(v : Any?) = this === v || (v is VectorOP3 &&
            are_dependent(hx, hy, hz, hw, v.hx, v.hy, v.hz, v.hw) &&
            inner_product(hx, hy, hz, hw, v.hx, v.hy, v.hz, v.hw) > 0
            )

    operator fun plus(v: VectorOP3) = VectorOP3(
            hx * v.hw + v.hx * hw,
            hy * v.hw + v.hy * hw,
            hz * v.hw + v.hz * hw,
            hw * v.hw
    )

    operator fun times(d: Double) = VectorOP3(hx * d, hy * d, hz * d, hw)

    fun isIdeal() = hw == 0.0
    fun toVectorE3() = VectorE3(hx / hw, hy / hw, hz / hw)
}

operator fun Double.times(v: VectorOP3) = VectorOP3(v.hx * this, v.hy * this, v.hz * this, v.hw)