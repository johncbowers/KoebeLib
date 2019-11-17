package geometry.primitives.Euclidean3

class DirectionE3 (vec: VectorE3) {

    constructor (d: DirectionE3) : this(VectorE3(d.v))

    companion object {
        val e1 = DirectionE3(VectorE3.e1)
        val e2 = DirectionE3(VectorE3.e2)
        val e3 = DirectionE3(VectorE3.e3)
    }

    val v: VectorE3 by lazy {
        val invd = 1 / vec.norm()
        VectorE3(vec.x * invd, vec.y * invd, vec.z * invd)
    }

    val endPoint: PointE3 by lazy { PointE3(this.v.x, v.y, v.z) }

    operator fun plus(d: DirectionE3) = DirectionE3(this.v + d.v)
    operator fun minus(d: DirectionE3) = DirectionE3(this.v - d.v)
    operator fun unaryMinus() = DirectionE3(-v)

    fun dot(d: DirectionE3) = v.dot(d.v)

    fun cross(d: DirectionE3) = DirectionE3(this.v.cross(d.v))

    override fun equals(d: Any?) = this === d ||
            d is DirectionE3 && this.v == d.v

}