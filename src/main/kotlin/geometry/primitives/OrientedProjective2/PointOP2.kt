package geometry.primitives.OrientedProjective2

import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.OrientedProjective3.*
import geometry.primitives.Euclidean3.*


/**
 * Created by sarahciresi on 6/13/17.
 */


class PointOP2(val hx: Double, val hy: Double, val hw: Double = 1.0) {

    fun distSqTo(p: PointOP2): Double {
        val dx = p.hx/p.hw - hx/hw
        val dy = p.hy/p.hw - hy/hw
        return dx*dx + dy*dy
    }

    /**
     * Distance from this to point p.
     */
    fun distTo(p: PointOP2) = Math.sqrt(distSqTo(p))

    /** Bisector line between two PointOPs
     *
     */
    fun bisector(pt2: PointOP2): LineOP2  {

        val p1 = VectorE3(this.hx, this.hy, this.hw)
        val p2 = VectorE3(pt2.hx, pt2.hy, pt2.hw)

        val p1Hat = p1.normalize()
        val p2Hat = p2.normalize()

        return LineOP2(p2Hat.x - p1Hat.x, p2Hat.y - p1Hat.y, p2Hat.z - p1Hat.z )
    }
}