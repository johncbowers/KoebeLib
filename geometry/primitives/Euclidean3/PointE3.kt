package geometry.primitives.Euclidean3

/**
 * Created by johnbowers on 5/20/17.
 */

class PointE3(val x: Double, val y: Double, val z:Double) {

    companion object {
        val O = PointE3()
    }

    constructor () : this(0.0, 0.0, 0.0)
    constructor (p: PointE3) : this(p.x, p.y, p.z)

    operator fun plus(v: VectorE3) = PointE3(x + v.x, y + v.y, z + v.z)
    operator fun minus(p: PointE3) = VectorE3(x - p.x, y - p.y, z - p.z)

    /**
     * Compute the square of the distance from this to p.
     */
    fun distSqTo(p: PointE3): Double {
        val dx = p.x - x
        val dy = p.y - y
        val dz = p.z - z
        return dx*dx + dy*dy + dz*dz
    }

    /**
     * Distance from this to point p.
     */
    fun distTo(p: PointE3) = Math.sqrt(distSqTo(p))

    fun toVectorE3() = this - O

}
