package geometry.primitives.OrientedProjective3

import geometry.primitives.determinant
import geometry.primitives.isZero
/**
 * Created by johnbowers on 5/23/17.
 */

class PlaneOP3(val X: Double, val Y: Double, val Z: Double, val W: Double) {

    constructor (p1: PointOP3, p2: PointOP3, p3: PointOP3) : this(
            X = + determinant(
                    p1.hy, p1.hz, p1.hw,
                    p2.hy, p2.hz, p2.hw,
                    p3.hy, p3.hz, p3.hw
            ),
            Y = - determinant(
                    p1.hx, p1.hz, p1.hw,
                    p2.hx, p2.hz, p2.hw,
                    p3.hx, p3.hz, p3.hw
            ),
            Z = + determinant(
                    p1.hx, p1.hy, p1.hw,
                    p2.hx, p2.hy, p2.hw,
                    p3.hx, p3.hy, p3.hw
            ),
            W = - determinant(
                    p1.hx, p1.hy, p1.hz,
                    p2.hx, p2.hy, p2.hz,
                    p3.hx, p3.hy, p3.hz
            )
    )

    fun isIncident(p: PointOP3) = isZero((X * p.hx + Y * p.hy + Z * p.hz + W * p.hw))
}