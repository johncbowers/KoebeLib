package geometry.primitives.Euclidean3

class DirectionE3 (v: VectorE3) {

    val v: VectorE3

    init {
        val invd = 1 / v.norm()
        this.v = VectorE3(v.x * invd, v.y * invd, v.z * invd)
    }

    operator fun plus(d: DirectionE3) = DirectionE3(this.v + d.v)
    operator fun minus(d: DirectionE3) = DirectionE3(this.v - d.v)
    operator fun unaryMinus() = DirectionE3(-v)

    fun dot(d: DirectionE3) = v.dot(d.v)

    fun cross(d: DirectionE3) = DirectionE3(this.v.cross(d.v))

}