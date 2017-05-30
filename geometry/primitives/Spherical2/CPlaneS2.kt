package geometry.primitives.Spherical2

import geometry.primitives.OrientedProjective3.PointOP3

/**
 * Created by johnbowers on 5/25/17.
 *
 * Class representing a c-Plane (a.k.a. Bundle of circles) in the space of circles on the
 * Riemann sphere S2.
 */
class CPlaneS2(val a: Double, val b: Double, val c: Double, val d: Double) {

    val dualDiskS2: DiskS2 by lazy { DiskS2(-a, -b, -c, d) }

    /* Enums */
    enum class Type {PARABOLIC, ELLIPTIC, HYPERBOLIC}

    /* Properties */
    val type: Type by lazy {

        val dSq = d*d
        val dot_abc = a*a + b*b + c*c
        val scale = dSq / dot_abc
        val aSc = a * scale
        val bSc = b * scale
        val cSc = c * scale

        when (aSc*aSc + bSc*bSc + cSc*cSc) {
            1.0 -> Type.PARABOLIC
            in 0.0..1.0 -> Type.HYPERBOLIC
            else -> Type.ELLIPTIC
        }
    }

    val isHyperbolic by lazy { type == Type.HYPERBOLIC }
    val isParabolic by lazy { type == Type.PARABOLIC }
    val isElliptic by lazy { type == Type.ELLIPTIC }
}