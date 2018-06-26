package geometry.primitives.OrientedProjective2

import geometry.primitives.Euclidean2.VectorE2
import geometry.primitives.are_dependent
import geometry.primitives.inner_product

/**
 * Created by sarahciresi on 6/20/17.
 */

class VectorOP2(val hx: Double, val hy: Double, val hw: Double = 1.0) {

    constructor (v: geometry.primitives.OrientedProjective2.VectorOP2) : this(v.hx, v.hy, v.hw)
    constructor (v: VectorE2) : this(v.x, v.y)

    operator override fun equals(v : Any?) = this === v || (v is geometry.primitives.OrientedProjective2.VectorOP2 &&
            are_dependent(hx, hy, hw, v.hx, v.hy, v.hw) &&
            inner_product(hx, hy, hw, v.hx, v.hy, v.hw) > 0
            )

    operator fun plus(v: geometry.primitives.OrientedProjective2.VectorOP2) = VectorOP2(
            hx * v.hw + v.hx * hw,
            hy * v.hw + v.hy * hw,
            hw * v.hw
    )

    operator fun times(d: Double) = VectorOP2(hx * d, hy * d, hw)

    fun isIdeal() = hw == 0.0
    fun toVectorE2() = VectorE2(hx / hw, hy / hw)
}

operator fun Double.times(v: geometry.primitives.OrientedProjective2.VectorOP2) = VectorOP2(v.hx * this, v.hy * this, v.hw)