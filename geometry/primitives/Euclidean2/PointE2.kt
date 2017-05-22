package geometry.primitives.Euclidean2

/**
 * Contains classes for working in Euclidean 2-space.
 * Created by johnbowers on 5/13/17.
 */

class PointE2(val x: Double, val y: Double) {

    companion object {
        val O = PointE2(0.0, 0.0)
    }

    operator fun plus(v: VectorE2) = PointE2(x + v.x, y + v.y)
    operator fun minus(p: PointE2) = VectorE2(x - p.x, y - p.y)

    /**
     * Compute the square of the distance from this to p.
     */
    fun distSqTo(p: PointE2): Double {
        val dx = p.x - x
        val dy = p.y - y
        return dx*dx + dy*dy
    }

    /**
     * Distance from this to point p.
     */
    fun distTo(p: PointE2) = Math.sqrt(distSqTo(p))
}