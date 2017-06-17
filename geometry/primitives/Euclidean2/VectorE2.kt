package geometry.primitives.Euclidean2

class VectorE2(val x: Double, val y: Double) {

    constructor (v: VectorE2): this(v.x, v.y)

    operator fun plus(v: VectorE2) = VectorE2(x + v.x, y + v.y)
    operator fun minus(p: PointE2) = VectorE2(x - p.x, y - p.y)
    operator fun times(a: Double) = VectorE2(x * a, y * a)
    operator fun div(a: Double) = VectorE2(x / a, y / a)
    operator fun unaryMinus() = VectorE2(-x, -y)

    fun dot(p: VectorE2) = x*p.x + y*p.y
    fun det(p: VectorE2) = x*p.y - y*p.x

    fun normSq() = x*x + y*y
    fun norm() = Math.sqrt(normSq())

    val angleFromXAxis by lazy {
        val normInv = 1.0 / norm()
        if (y >= 0)
            Math.acos(dot(VectorE2(1.0, 0.0)) * normInv)
        else
            2 * Math.PI - Math.acos(dot(VectorE2(1.0, 0.0)) * normInv)
    }
}

/*** Extensions for interacting with Vectors ***/

operator fun Double.times(p : VectorE2) = VectorE2(p.x * this, p.y * this)