package geometry.primitives.OrientedProjective2

import geometry.primitives.*
import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.Spherical2.DiskS2

/**
 * Created by johnbowers on 6/13/17.
 */

fun PointE2.toPointOP2() = PointOP2(this.x, this.y, 1.0)

class DiskOP2(val a: Double, val b: Double, val c: Double, val d: Double) {

    constructor (p1: PointOP2, p2: PointOP2, p3: PointOP2): this(
            a = + determinant(
                    p1.hx * p1.hw, p1.hy * p1.hw, p1.hw * p1.hw,
                    p2.hx * p2.hw, p2.hy * p2.hw, p2.hw * p2.hw,
                    p3.hx * p3.hw, p3.hy * p3.hw, p3.hw * p3.hw
            ),
            b = - determinant(
                    (p1.hx * p1.hx + p1.hy * p1.hy), p1.hy * p1.hw, p1.hw * p1.hw,
                    (p2.hx * p2.hx + p2.hy * p2.hy), p2.hy * p2.hw, p2.hw * p2.hw,
                    (p3.hx * p3.hx + p3.hy * p3.hy), p3.hy * p3.hw, p3.hw * p3.hw
            ),
            c = + determinant(
                    (p1.hx * p1.hx + p1.hy * p1.hy), p1.hx * p1.hw, p1.hw * p1.hw,
                    (p2.hx * p2.hx + p2.hy * p2.hy), p2.hx * p2.hw, p2.hw * p2.hw,
                    (p3.hx * p3.hx + p3.hy * p3.hy), p3.hx * p3.hw, p3.hw * p3.hw
            ),
            d = - determinant(
                    (p1.hx * p1.hx + p1.hy * p1.hy), p1.hx * p1.hw, p1.hy * p1.hw,
                    (p2.hx * p2.hx + p2.hy * p2.hy), p2.hx * p2.hw, p2.hy * p2.hw,
                    (p3.hx * p3.hx + p3.hy * p3.hy), p3.hx * p3.hw, p3.hy * p3.hw
            )
    )

    constructor (center: PointOP2, radius: Double) :
            this (
                    a = center.hw * center.hw,
                    b = -2.0 * center.hx * center.hw,
                    c = -2.0 * center.hy * center.hw,
                    d = center.hx * center.hx + center.hy * center.hy - radius * radius * center.hw * center.hw
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

    fun intersectWith(line: LineOP2): List<PointOP2> {

        if (!isZero(line.a)) {
            // x = -(By+C)/A , solve for Y, then X
            val alpha2 = -this.a * line.b * line.b + this.a * line.a * line.a - this.b * line.b * line.b
            val beta2 = -2 * this.a * line.b * line.c - 2 * this.b * line.b * line.c + this.c * line.a * line.a
            val gamma2 = -this.a * line.c * line.c - this.b * line.c * line.c + this.d * line.a * line.a

            /// derivation 2
            val alpha = this.a * line.b * line.b + this.a * line.a * line.a
            val beta = 2 * this.a * line.b * line.c - this.b * line.b * line.a + this.c * line.a * line.a
            val gamma = this.a * line.c * line.c - this.b * line.a * line.c + this.d * line.a * line.a

            // Test for number of intersection points
            // Case 1: 1 intersection point
            if ( isZero(beta*beta - 4*alpha*gamma) ) {
                val point1Y = -beta/(2*alpha)
                val point1X = (-line.b*point1Y-line.c)/line.a
                return listOf<PointOP2>( PointOP2(point1X, point1Y) )
            }
            // Case 1: 0 intersection points
            else if ( beta*beta - 4*alpha*gamma < 0.0 )
                return listOf<PointOP2>()
            // Case 3: 2 intersection points
            else {
                val point1Y = (-beta + Math.sqrt(beta*beta - 4*alpha*gamma))/(2 * alpha)
                val point2Y = (-beta - Math.sqrt(beta*beta - 4*alpha*gamma))/(2 * alpha)

                val point1X = (-line.b*point1Y-line.c)/line.a
                val point2X = (-line.b*point2Y-line.c)/line.a

                return listOf<PointOP2>( PointOP2(point1X, point1Y), PointOP2(point2X, point2Y)  )
            }

        }
        else { // if line.b != 0.0
            // y = -(Ax+C)/B, solve for X, then Y
            val alpha = this.a * line.b * line.b + this.a * line.a * line.a
            val beta =  2 * this.a * line.a * line.c + line.b * line.b * this.b - this.c * line.a * line.b
            val gamma = this.a * line.c * line.c - this.c * line.b * line.c + this.d * line.b * line.b

            // Test for number of intersection points
            // Case 1: 1 intersection point
            if ( isZero(beta * beta - 4 * alpha * gamma) ) {
                val point1X = -beta / (2 * alpha)
                val point1Y = (-line.a * point1X - line.c) / line.b
                return listOf<PointOP2>(PointOP2(point1X, point1Y))
            }
            // Case 2: 0 intersection points
            else if ( beta * beta - 4 * alpha * gamma < 0.0 ) {
                return listOf<PointOP2>()
            }
            // Case 3: 2 intersection points
            else {
                val point1X = (-beta + Math.sqrt(beta*beta - 4*alpha*gamma)) /(2 * alpha)
                val point2X = (-beta - Math.sqrt(beta*beta - 4*alpha*gamma)) /(2 * alpha)

                val point1Y = (-line.a*point1X-line.c)/line.b
                val point2Y = (-line.a*point2X-line.c)/line.b

                return listOf<PointOP2>( PointOP2(point1X, point1Y), PointOP2(point2X, point2Y)  )
            }
        }
    }

    fun intersectWith(disk2: DiskOP2): List<PointOP2> {

        val A = determinant(this.a, this.b, disk2.a, disk2.b)
        val B = determinant(this.a, this.c, disk2.a, disk2.c)
        val C = determinant(this.a, this.d, disk2.a, disk2.d)

        // if A & B are both = 0 -> circles are concentric; no points of intersection
        // return an empty list
        if ( A == 0.0 && B == 0.0 ) {
            return listOf<PointOP2>()
        }

        // otherwise, call intersectWith on lineOP2 with coefficients A,B, and C and return result
        return intersectWith( LineOP2(A, B, C) )

    }

    private fun funkyInnerProduct(disk1: DiskOP2, disk2: DiskOP2) = disk1.b*disk2.b + disk1.c*disk2.c - 2*disk2.a*disk1.d - 2*disk1.a*disk2.d

    fun inversiveDistTo(disk: DiskOP2) : Double {
        val ip12 = funkyInnerProduct(this, disk)
        val ip11 = funkyInnerProduct(this, this)
        val ip22 = funkyInnerProduct(disk, disk)
        return -ip12 / (Math.sqrt(ip11) * Math.sqrt(ip22))
    }

    fun invertThrough(disk: DiskOP2): DiskOP2 {

        val fact =
                funkyInnerProduct(this, disk) /
                        funkyInnerProduct(disk, disk)
        return DiskOP2(
                a - 2 * fact * disk.a,
                b - 2 * fact * disk.b,
                c - 2 * fact * disk.c,
                d - 2 * fact * disk.d
        )
    }

    fun inversiveNormalize(): DiskOP2 {
        val scale = 1.0 / inversiveDistTo(this)
        return DiskOP2(a * scale, b * scale, c * scale, d * scale)
    }

    fun toDiskS2(): DiskS2 = DiskS2(a - d, c, b, -(a + d))
}

