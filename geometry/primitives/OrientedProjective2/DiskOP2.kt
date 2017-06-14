package geometry.primitives.OrientedProjective2

import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.Orientation
import geometry.primitives.determinant
import geometry.primitives.inner_product
import geometry.primitives.isZero

/**
 * Created by johnbowers on 6/13/17.
 */

fun PointE2.toPointOP2() = PointOP2(this.x, this.y, 1.0)

class DiskOP2(val a: Double, val b: Double, val c: Double, val d: Double) {

    constructor (p1: PointOP2, p2: PointOP2, p3: PointOP2): this(
            a = + determinant(
                    (p1.hx / p1.hw), (p1.hy / p1.hw), (1.0),
                    (p2.hx / p2.hw), (p2.hy / p2.hw), (1.0),
                    (p3.hx / p3.hw), (p3.hy / p3.hw), (1.0)
            ),
            b = - determinant(
                    (p1.hx * p1.hx + p1.hy * p1.hy) / (p1.hw * p1.hw), (p1.hy / p1.hw), (1.0),
                    (p2.hx * p2.hx + p2.hy * p2.hy) / (p2.hw * p2.hw), (p2.hy / p2.hw), (1.0),
                    (p3.hx * p3.hx + p3.hy * p3.hy) / (p3.hw * p3.hw), (p3.hy / p3.hw), (1.0)
            ),
            c = + determinant(
                    (p1.hx * p1.hx + p1.hy * p1.hy) / (p1.hw * p1.hw), (p1.hx / p1.hw), (1.0),
                    (p2.hx * p2.hx + p2.hy * p2.hy) / (p2.hw * p2.hw), (p2.hx / p2.hw), (1.0),
                    (p3.hx * p3.hx + p3.hy * p3.hy) / (p3.hw * p3.hw), (p3.hx / p3.hw), (1.0)
            ),
            d = - determinant(
                    (p1.hx * p1.hx + p1.hy * p1.hy) / (p1.hw * p1.hw), (p1.hx / p1.hw), (p1.hy / p1.hw),
                    (p2.hx * p2.hx + p2.hy * p2.hy) / (p2.hw * p2.hw), (p2.hx / p2.hw), (p2.hy / p2.hw),
                    (p3.hx * p3.hx + p3.hy * p3.hy) / (p3.hw * p3.hw), (p3.hx / p3.hw), (p3.hy / p3.hw)
            )
    )

    /**
     * Returns Orientation.ZERO if p is on the boundary of the disk, Orientation.POSITIVE if it is on the positive side
     * or Orientation.NEGATIVE if p is on the negative side of the disk.
     */
    fun orientationOf(p: PointOP2): Orientation {
        val wSq = p.hw*p.hw
        val test = inner_product(
                a, b, c, d,
                p.hx*p.hx + p.hy*p.hy, p.hx * wSq, p.hy * wSq, wSq
        )
        return  if (isZero(test)) Orientation.ZERO
                else if (test > 0.0) Orientation.POSITIVE
                else Orientation.NEGATIVE
    }

    val isLine by lazy { isZero(a) }

    // TODO val toLineOP2 by lazy { LineOP2(b, c, d) }

    val center by lazy { PointE2(-b / (2.0 * a), -c  / (2.0 * a)) }
    val radiusSq by lazy { center.x*center.x + center.y * center.y - (d/a) }
    val radius by lazy { Math.sqrt(radiusSq) }
}