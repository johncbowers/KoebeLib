package geometry.primitives.Euclidean2

/**
 * Contains classes for working in Euclidean 2-space.
 * Created by johnbowers on 5/13/17.
 */

class PointE2(val x: Double, val y: Double) {

    companion object {
        val O = PointE2()
    }

    constructor () : this(0.0, 0.0)
    constructor (p: PointE2) : this(p.x, p.y)

    operator fun plus(v: VectorE2) = PointE2(x + v.x, y + v.y)
    operator fun minus(p: PointE2) = VectorE2(x - p.x, y - p.y)

    // Square distance to various geometric objects

    fun distSqTo(p: PointE2): Double {
        val dx = p.x - x
        val dy = p.y - y
        return dx*dx + dy*dy
    }

    fun distSqTo(s: SegmentE2): Double {
        return distSqTo(s.closestPointE2To(this))
    }

    fun distTo(p: PointE2) = Math.sqrt(distSqTo(p))
    fun distTo(s: SegmentE2) = Math.sqrt(distSqTo(s))

    // TODO There has to be a nice analytical way to do this without evaluating the center and radius
    fun distTo(d: DiskE2): Double {
        return distTo(d.center) - d.radius
    }

}