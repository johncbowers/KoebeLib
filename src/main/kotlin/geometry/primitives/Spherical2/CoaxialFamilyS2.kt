package geometry.primitives.Spherical2

import geometry.primitives.determinant
import geometry.primitives.OrientedProjective3.LineOP3
import geometry.primitives.OrientedProjective3.PointOP3
import geometry.primitives.Euclidean3.VectorE3

/**
 * Created by johnbowers on 5/22/17.
 *
 * Represents the coaxial family of disks oriented from source towards target.
 */

class CoaxialFamilyS2(
        val source: DiskS2,
        val target: DiskS2
) {

    /* Constructors */

    constructor (cf: CoaxialFamilyS2) : this(cf.source, cf.target)

    /* Enums */
    enum class Type {PARABOLIC, ELLIPTIC, HYPERBOLIC}

    /* Properties */
    val type by lazy {
        when (LineOP3(source.dualPlaneOP3, target.dualPlaneOP3).getIntersectionWithUnit2Sphere().size) {
            0 -> Type.HYPERBOLIC
            1 -> Type.PARABOLIC
            else -> Type.ELLIPTIC
        }
    }

    val isHyperbolic by lazy { type == Type.HYPERBOLIC }
    val isParabolic by lazy { type == Type.PARABOLIC }
    val isElliptic by lazy { type == Type.ELLIPTIC }

    /* Conversions */

    /* Dual objects */ 
    val dualLineOP3 by lazy {
        // Should be the join of the two hyperplanes supporting the source and the target disks:
        LineOP3(
                determinant(source.a, source.b, target.a, target.b), // p01
                determinant(source.a, source.c, target.a, target.c), // p02
                determinant(source.a, source.d, target.a, target.d), // p03
                determinant(source.b, source.c, target.b, target.c), // p12
                determinant(source.b, source.d, target.b, target.d), // p13
                determinant(source.c, source.d, target.c, target.d)  // p23
        )
    }

    val dualCoaxialFamilyS2 by lazy {
        // TODO
        // Get the polyhedral cap, convert to homogeneous coordinates, connect with a line
    }

    val generatorPoints: List<PointS2> by lazy {
        val line =
                if (isElliptic) LineOP3(source.dualPlaneOP3, target.dualPlaneOP3)
                else LineOP3(source.dualPointOP3, target.dualPointOP3)
        line.getIntersectionWithUnit2Sphere().map { p -> PointS2(p.toVectorE3()) }
    }

    fun diskThroughPointS2(pt: PointS2): DiskS2 {

        val a1 = source.a
        val b1 = source.b
        val c1 = source.c
        val d1 = source.d

        val a2 = target.a
        val b2 = target.b
        val c2 = target.c
        val d2 = target.d

        val x = pt.directionE3.v.x
        val y = pt.directionE3.v.y
        val z = pt.directionE3.v.z

        return DiskS2(
                a2 * (d1 + b1 * y + c1 * z) - a1 * (d2 + b2 * y + c2 * z),
                b2 * (d1 + a1 * x + c1 * z) - b1 * (d2 + a2 * x + c2 * z),
                c2 * (d1 + a1 * x + b1 * y) - c1 * (d2 + a2 * x + b2 * y),
                -a2 * d1 * x + d2 * (a1 * x + b1 * y + c1 * z) - d1 * (b2 * y + c2 * z)
        )
    }
}

