package geometry.primitives.Euclidean3

import geometry.primitives.determinant

class VectorE3(val x: Double, val y: Double, val z: Double) {

    constructor (p: PointE3): this(p.x, p.y, p.z)

    operator fun plus(v: VectorE3) = VectorE3(x + v.x, y + v.y, z + v.z)
    operator fun minus(p: PointE3) = VectorE3(x - p.x, y - p.y, z - p.z)
    operator fun minus(v: VectorE3) = VectorE3(x - v.x, y - v.y, z - v.z)
    operator fun times(a: Double) = VectorE3(x * a, y * a, z * a)
    operator fun div(a: Double) = VectorE3(x / a, y / a, z / a)
    operator fun unaryMinus() = VectorE3(-x, -y, -z)

    fun dot(p: VectorE3) = x*p.x + y*p.y + z*p.z

    fun normSq() = x*x + y*y + z*z
    fun norm() = Math.sqrt(normSq())

    fun normalize(): VectorE3 {
        val invNorm = 1.0 / norm()
        return VectorE3(x * invNorm, y * invNorm, z * invNorm)
    }

    fun cross(v: VectorE3) = VectorE3(
            determinant(y, v.y, z, v.z),
            -determinant(x, v.x, z, v.z),
            determinant(x, v.x, y, v.y)
    )

    fun toPointE3() = PointE3(x, y, z)
}

/*** Extensions for interacting with Vectors ***/

operator fun Double.times(p : VectorE3) = VectorE3(p.x * this, p.y * this, p.z * this)