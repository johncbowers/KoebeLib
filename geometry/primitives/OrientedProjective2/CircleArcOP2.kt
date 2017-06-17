package geometry.primitives.OrientedProjective2

import geometry.primitives.Euclidean2.PointE2

/**
 * Created by Sarah Ciresi on 6/14/17.
 */


class CircleArcOP2(val source: PointOP2, val target: PointOP2, val disk: DiskOP2) {
/* Properties */

    // Construct the arc from p1 to p3 containing p2.
    constructor (p1: PointOP2, p2: PointOP2, p3: PointOP2) : this(p1, p3, DiskOP2(p1, p2, p3))

    val center: PointE2 by lazy { disk.center }

    // Euclidean radius of circle
    val radius: Double by lazy { disk.radius }

    fun intersectWith(line: LineOP2): List<PointOP2> {
        //Signed area of triangle ABC = (1/2)( (B.x - A.x) * (C.y - A.y) - (C.x - A.x) * (B.y - A.y) );
        return disk.intersectWith(line).filter {
            p ->
            val psx = p.hx * source.hw - source.hx * p.hw
            val tsy = target.hy * source.hw - source.hy * target.hw
            val tsx = target.hx * source.hw - source.hx * target.hw
            val psy = p.hy * source.hw - source.hy * p.hw
            0 < psx * tsy - tsx * psy
        }
    }

}

