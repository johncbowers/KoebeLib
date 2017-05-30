package geometry.primitives.Spherical2

import geometry.primitives.determinant

/**
 * Created by johnbowers on 5/22/17.
 *
 * Represents the coaxial family of disks oriented from source towards target.
 */

class CoaxialFamilyS2(
        val a: Double,
        val b: Double,
        val c: Double,
        val d: Double,
        val e: Double,
        val f: Double
) {

    /* Constructors */

    constructor (cf: CoaxialFamilyS2) : this(cf.a, cf.b, cf.c, cf.d, cf.e, cf.f)

    /* Enums */
    enum class Type {PARABOLIC, ELLIPTIC, HYPERBOLIC}

    /* Properties */
    val type by lazy { Type.HYPERBOLIC } // TODO

    val isHyperbolic by lazy { type == Type.HYPERBOLIC }
    val isParabolic by lazy { type == Type.PARABOLIC }
    val isElliptic by lazy { type == Type.ELLIPTIC }

    /* Conversions */

}