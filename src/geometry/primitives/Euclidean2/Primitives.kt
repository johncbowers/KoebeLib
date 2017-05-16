package geometry.primitives.Euclidean2

/**
 * Contains classes for working in Euclidean 2-space.
 * Created by johnbowers on 5/13/17.
 */

class Point2d(var x: Double, var y: Double) {

    operator fun plus(v: Vector2d) = Point2d(x + v.x, y + v.y)
    operator fun minus(p: Point2d) = Vector2d(x - p.x, y - p.y)

    /**
     * Compute the square of the distance from this to p.
     */
    fun distSqTo(p: Point2d): Double {
        val dx = p.x - x
        val dy = p.y - y
        return dx*dx + dy*dy
    }

    /**
     * Distance from this to point p.
     */
    fun distTo(p: Point2d) = Math.sqrt(distSqTo(p))
}

class Vector2d(var x: Double, var y: Double) {

    constructor (p: Point2d): this(p.x, p.y)

    operator fun plus(v: Vector2d) = Vector2d(x + v.x, y + v.y)
    operator fun minus(p: Point2d) = Vector2d(x - p.x, y - p.y)
    operator fun times(a: Double) = Vector2d(x * a, y * a)
    operator fun div(a: Double) = Vector2d(x / a, y / a)

    fun dot(p: Vector2d) = x*p.x + y*p.y
    fun det(p: Vector2d) = x*p.y - y*p.x

    fun normSq() = x*x + y*y
    fun norm() = Math.sqrt(normSq())
}

/*** Extensions for interacting with Vectors ***/

operator fun Double.times(p : Vector2d) = Vector2d(p.x * this, p.y * this)
