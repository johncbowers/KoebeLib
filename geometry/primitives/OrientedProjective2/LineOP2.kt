package geometry.primitives.OrientedProjective2

import geometry.primitives.determinant

/**
 * Created by sarahciresi on 6/13/17.
 */
class LineOP2 (
        val a : Double,
        val b : Double,
        val c : Double){

    fun intersection(line2: LineOP2): PointOP2 {
        val detx = determinant(this.b, this.c, line2.b, line2.c)
        val dety = -determinant(this.a, this.c, line2.a, line2.c)
        val detw = determinant(this.a, this.b, line2.a, line2.b)
        return PointOP2(detx, dety, detw)
    }


    fun intersectWith(disk: DiskOP2): List<PointOP2> {
        return disk.intersectWith(this)
    }

}