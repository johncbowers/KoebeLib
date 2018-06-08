package geometry.primitives.Euclidean2

import geometry.primitives.DiskOrientation
import geometry.primitives.determinant
import geometry.primitives.*

/**
 * Stores a disk in the plane by the coefficients of the equation a(x^2+y^2) + bx + cy + d = 0.
 *
 * Created by sarahciresi on 6/12/17.
 */

// vs PointE2s...
class DiskE2(val a: Double, val b: Double, val c: Double, val d: Double) {

    constructor (p1: PointE2, p2: PointE2, p3: PointE2) :
            this (
                    a = determinant(
                            p1.x, p1.y, 1.0,
                            p2.x, p2.y, 1.0,
                            p3.x, p3.y, 1.0
                    ),
                    b = - determinant(
                            p1.x*p1.x + p1.y*p1.y, p1.y, 1.0,
                            p2.x*p2.x + p2.y*p2.y, p2.y, 1.0,
                            p3.x*p3.x + p3.y*p3.y, p3.y, 1.0
                    ),
                    c = determinant(
                            p1.x*p1.x + p1.y*p1.y, p1.x, 1.0,
                            p2.x*p2.x + p2.y*p2.y, p2.x, 1.0,
                            p3.x*p3.x + p3.y*p3.y, p3.x, 1.0
                    ),
                    d = - determinant(
                            p1.x*p1.x + p1.y*p1.y, p1.x, p1.y,
                            p2.x*p2.x + p2.y*p2.y, p2.x, p2.y,
                            p3.x*p3.x + p3.y*p3.y, p3.x, p3.y
                    )
            )

    constructor (center: PointE2, radius: Double) :
            this (
                    a = 1.0,
                    b = -2.0 * center.x,
                    c = -2.0 * center.y,
                    d = center.x * center.x + center.y * center.y - radius * radius
            )

    fun relativeOrientationOf(p: PointE2): DiskOrientation {
        val eval = (a * (p.x * p.x + p.y * p.y) + b * p.x + c * p.y + d) * a
        if (isZero(eval)) return DiskOrientation.COCIRCULAR
        else if (eval < 0) return DiskOrientation.INSIDE
        else return DiskOrientation.OUTSIDE
    }

    val center: PointE2 by lazy { val inv2A = 0.5 / a;PointE2(-b * inv2A, -c * inv2A) }
    val radiusSq: Double by lazy { (b*b + c*c - 4 * d * a) / (4 * a * a) }
    val radius: Double by lazy { Math.sqrt(radiusSq)}

    fun intersects(s: SegmentE2): Boolean = s.closestPointE2To(center).distSqTo(center) < radiusSq

    fun contains(p: PointE2): Boolean = relativeOrientationOf(p) == DiskOrientation.INSIDE
}
