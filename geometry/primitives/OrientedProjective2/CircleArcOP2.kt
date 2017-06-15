package geometry.primitives.OrientedProjective2

import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.Euclidean3.DirectionE3
import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.Euclidean3.VectorE3

/**
 * Created by sarahciresi on 6/14/17.
 */


class CircleArcOP2(val source: PointOP2, val target: PointOP2, val disk: DiskOP2) {
/* Properties */

    val center: PointE2 by lazy { disk.center }

    // Euclidean radius of circle
    val radius: Double by lazy { disk.radius }

    /* init {
        assert(source != target && source != -target)
        assert(disk.contains(source) && disk.contains(target))
    }
    */


}

