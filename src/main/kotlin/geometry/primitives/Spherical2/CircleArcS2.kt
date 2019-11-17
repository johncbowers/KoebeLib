package geometry.primitives.Spherical2


import geometry.primitives.Euclidean3.DirectionE3
import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.Euclidean3.VectorE3
import geometry.primitives.OrientedProjective2.CircleArcOP2
import geometry.primitives.OrientedProjective2.DiskOP2
import geometry.primitives.OrientedProjective2.PointOP2
import geometry.primitives.OrientedProjective3.PointOP3


//TODO

class CircleArcS2(val source: PointS2, val target: PointS2, val disk: DiskS2) {

//    constructor (p1: PointS2, p2: PointS2, p3: PointS2) :
//        this(p1, p3, circleSupportingArc(p1, p2, p3))
//
    /* Properties */

    // Basis vectors for the disk
    val basis1 : VectorE3 by lazy { disk.basis1 }
    val basis2 : VectorE3 by lazy { disk.basis2 }
    val basis3 : VectorE3 by lazy { disk.basis3 }

    // Normalized basis vectors as DirectionE3
    val normedBasis1 : DirectionE3 by lazy { disk.normedBasis1 }
    val normedBasis2 : DirectionE3 by lazy { disk.normedBasis2 }
    val normedBasis3 : DirectionE3 by lazy { disk.normedBasis3 }

    val centerE3: PointE3 by lazy { disk.centerE3 }

    // Euclidean radius of circle
    val radiusE3: Double by lazy { disk.radiusE3 }


    init {
        assert(source != target && source != -target)
        assert(disk.contains(source) && disk.contains(target))
    }


    fun sgToCircleArcOP2() : CircleArcOP2 {
        val pointsOP2 = mutableListOf<PointOP2>()

        // Get three points on DiskS2
        val pointsE3 = this.disk.get3PointsOnDisk()

        // Project the PointE3s to PointOP2s
        for (point in pointsE3) {
            pointsOP2.add(point.sgProjectToPointOP2())
        }
        // Get DiskOP2 through three points
        val diskOP2 = DiskOP2(pointsOP2[0], pointsOP2[1], pointsOP2[2])

        // Return the CircleArcOP2 through with the source and target PointOP2s and DiskOP2
        return CircleArcOP2(this.source.sgProjectToPointOP2(), this.target.sgProjectToPointOP2(), diskOP2)
    }

}

//fun circleSupportingArc(p1: PointS2, p2: PointS2, p3: PointS2): DiskS2 {
//    return DiskS2() // TODO
//}



