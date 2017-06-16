package geometry.primitives.Spherical2

import geometry.primitives.*;
import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.Euclidean3.VectorE3
import geometry.primitives.Euclidean3.DirectionE3
import geometry.primitives.Euclidean3.least_dominant
import geometry.primitives.ExtendedComplex.*
import geometry.primitives.OrientedProjective2.PointOP2

/**
 * Created by johnbowers on 5/20/17.
 *
 * Code is adapted from Ghali's Introduction to Geometric Computing, Springer, 2008
 */

class PointS2(
        val x: Double,
        val y:Double,
        val z:Double
) {

    /* Constructors */

    constructor () : this(1.0, 0.0, 0.0)
    constructor (v: VectorE3) : this(v.x, v.y, v.z)

    /* Properties */

    val antipode : PointS2 get() = PointS2(-x, -y, -z)
    val directionE3: DirectionE3 get() = DirectionE3(VectorE3(x, y, z))

    /* Operations */

    operator fun unaryMinus() = antipode

    /* Tests */

    operator override fun equals(p:Any?) =
            this === p ||
            p != null &&
            p is PointS2 &&
            this.directionE3 == p.directionE3

    /* Conversions */

    /**
     * Returns the coordinates of this point using the normal homogeneous complex coordinates
     * on the Riemann sphere.
     */
    fun toExtendedComplex(): ExtendedComplex {
        val d = directionE3
        if (d.v.z < 0) {
            return ExtendedComplex(Complex(d.v.x, d.v.y), Complex(1 - d.v.z, 0.0))
        } else {
            return ExtendedComplex(Complex(1 + d.v.z, 0.0), Complex(d.v.x, -d.v.y))
        }
    }

    // takes a point in S2 and computes the corresponding point in E2 from projecting onto z=1 plane
    fun sgProjectToPointE2(): PointE2 {
        val invNorm = 1.0/(Math.sqrt(x*x + y*y + z*z))
        val X = x*invNorm
        val Y = y*invNorm
        val Z = z*invNorm

        return PointE2(X/(Z+1), Y/(Z+1))
    }

    // takes a point in S2 and computes the corresponding point in OP2 from projecting onto z=1 plane
    fun sgProjectToPointOP2(): PointOP2 {
        val invNorm = 1.0/(Math.sqrt(x*x + y*y + z*z))
        val X = x*invNorm
        val Y = y*invNorm
        val Z = z*invNorm
        return PointOP2(2*X, 2*Y, Z + 1)
    }

}


