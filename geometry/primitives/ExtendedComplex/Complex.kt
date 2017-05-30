package geometry.primitives.ExtendedComplex

/**
 * Created by johnbowers on 5/22/17.
 */

class Complex(val re:Double, val im:Double) {

    constructor (z: Complex) : this(z.re, z.im)

    companion object {
        val zero = Complex(0.0, 0.0)
        val one = Complex(1.0, 0.0)
        val i = Complex(0.0, 1.0)
    }

    operator fun plus(z: Complex) = Complex(re + z.re, im + z.im)
    operator fun minus(z: Complex) = Complex(re - z.re, im - z.im)
    operator fun times(z: Complex) = Complex(re * z.re - im * z.im, re * z.im + im * z.re)
    operator fun unaryMinus() = Complex(-re, -im)
    operator fun div(z: Complex) = this * z.reciprocal
    operator override fun equals(z: Any?) = z is Complex && (re == z.re && im == z.im)

    fun abs() = Math.hypot(re, im)
    fun absSq() = re*re + im*im

    val reciprocal: Complex by lazy {
        val scale = 1.0 / (re*re + im*im)
        Complex(re * scale, -im * scale)
    }

    val conjugate by lazy { Complex(re, -im) }
}