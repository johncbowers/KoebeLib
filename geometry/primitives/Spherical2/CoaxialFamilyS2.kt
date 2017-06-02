package geometry.primitives.Spherical2

import geometry.primitives.determinant
import geometry.primitives.OrientedProjective3.LineOP3
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
    val type by lazy { Type.HYPERBOLIC } // TODO

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
        val retList = mutableListOf<PointS2>()
        // TODO fix this in a bit
        retList
    }

}