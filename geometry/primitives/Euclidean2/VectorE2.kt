package geometry.primitives.Euclidean2

class VectorE2(val x: Double, val y: Double) {

    constructor (p: PointE2): this(p.x, p.y)

    operator fun plus(v: VectorE2) = VectorE2(x + v.x, y + v.y)
    operator fun minus(p: PointE2) = VectorE2(x - p.x, y - p.y)
    operator fun times(a: Double) = VectorE2(x * a, y * a)
    operator fun div(a: Double) = VectorE2(x / a, y / a)
    operator fun unaryMinus() = VectorE2(-x, -y)

    fun dot(p: VectorE2) = x*p.x + y*p.y
    fun det(p: VectorE2) = x*p.y - y*p.x

    fun normSq() = x*x + y*y
    fun norm() = Math.sqrt(normSq())
}

/*** Extensions for interacting with Vectors ***/

operator fun Double.times(p : VectorE2) = VectorE2(p.x * this, p.y * this)