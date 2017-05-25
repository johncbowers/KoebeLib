package geometry.primitives.OrientedProjective3

import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.are_dependent
import geometry.primitives.inner_product

/**
 * Created by johnbowers on 5/22/17.
 */

class PointOP3(val hx: Double, val hy: Double, val hz: Double, val hw: Double = 1.0) {

    constructor (p: PointOP3) : this(p.hx, p.hy, p.hz, p.hw)
    constructor (p: PointE3) : this(p.x, p.y, p.z, 1.0)

    companion object {
        val O = PointOP3(0.0, 0.0, 0.0, 1.0)
    }

    operator fun minus(p: PointOP3) : VectorOP3 = VectorOP3(
            hx * p.hw - p.hx * hw,
            hy * p.hw - p.hy * hw,
            hz * p.hw - p.hz * hw,
            hw * p.hw
    )

    operator fun minus(v: VectorOP3) : PointOP3 = PointOP3(
            hx * v.hw - v.hx * hw,
            hy * v.hw - v.hy * hw,
            hz * v.hw - v.hz * hw,
            hw * v.hw
    )

    operator fun plus(v: VectorOP3) : PointOP3 = PointOP3(
            hx * v.hw + v.hx * hw,
            hy * v.hw + v.hy * hw,
            hz * v.hw + v.hz * hw,
            hw * v.hw
    )

    operator override fun equals(p : Any?) = this === p || (
            p is PointOP3 &&
            are_dependent(hx, hy, hz, hw, p.hx, p.hy, p.hz, p.hw) &&
            inner_product(hx, hy, hz, hw, p.hx, p.hy, p.hz, p.hw) > 0
            )

    fun normSq() = (hx*hx + hy*hy + hz*hz) / (hw * hw)
    fun norm() = Math.sqrt(normSq())

    fun isIdeal() = hw == 0.0
    fun toPointE3() = PointE3(hx / hw, hy / hw, hz / hw)

    fun unitSphereInversion(): PointOP3 {
        // OLD VERSION WHEN PART OF PointE3
        //val invLen = 1.0 / distTo(PointE3.O)
        //return (this.toVectorE3().normalize() * invLen).toPointE3()
        // TODO Check this in a.m.
        return PointOP3(hx, hy, hz, hw * normSq())
    }
}