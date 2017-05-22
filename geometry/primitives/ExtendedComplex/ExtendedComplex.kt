package geometry.primitives.ExtendedComplex

import geometry.primitives.Spherical2.PointS2

/**
 * Created by johnbowers on 5/22/17.
 */

open class ExtendedComplex(val z: Complex, val w: Complex) {

    init {
        assert(z != Complex.zero || w != Complex.zero);
    }

    fun toComplex() = z / w

    fun applyMobius(a:Complex, b:Complex, c:Complex, d:Complex) = ExtendedComplex(
            a * z + b * w,
            c * z + d * w
    )

    /**
     * Returns the point on the unit real sphere corresponding to this point on the
     * Reimann sphere
     */
    fun toPointS2(): PointS2 {
        val zwc = z * w.conjugate()
        val zcw = z.conjugate() * w
        val absSqz = z.absSq()
        val absSqw = w.absSq()
        val hypotSq = absSqz + absSqw
        return PointS2(
                ((zwc + zcw) / Complex(hypotSq, 0.0)).re,
                ((zwc - zcw) / Complex(0.0, hypotSq)).re,
                (Complex(absSqz - absSqw, hypotSq)).re
        )
    }
}
