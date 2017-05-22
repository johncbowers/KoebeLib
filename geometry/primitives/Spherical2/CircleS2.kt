package geometry.primitives.Spherical2

import geometry.primitives.Euclidean3.DirectionE3
import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.Euclidean3.VectorE3
import geometry.primitives.Euclidean3.PlaneE3
import geometry.primitives.Euclidean3.least_dominant
import geometry.primitives.isZero

class CircleS2(val N: VectorE3 = VectorE3(0.0, 0.0, 1.0), val d:Double = 0.0) {

    constructor (p1: PointS2, p2: PointS2, p3: PointS2) :
            this((p2.directionE3-p1.directionE3).cross(p3.directionE3-p1.directionE3).v,
                    (-p1.directionE3).dot((p2.directionE3-p1.directionE3).cross(p3.directionE3-p1.directionE3)))

    constructor (p1: PointS2, p2: PointS2) :
            this (VectorE3(p1.x, p1.y, p1.z).cross(VectorE3(p2.x, p2.y, p2.z)), 0.0)

    fun basis1() : VectorE3 = least_dominant(N).vec.cross(N)
    fun basis2() : VectorE3 = N.cross(basis1())
    fun basis3() = N

    fun contains(p: PointS2): Boolean = isZero(N.dot(p.directionE3.v))

    val directionE3: DirectionE3 get() = DirectionE3(N)

    //val dualPlane: PlaneE3 = PlaneE3()
    val centerE3: PointE3 get() = PlaneE3(N, d).pointClosestOrigin()
    val radiusE3: Double get() = Math.sqrt(1.0 - VectorE3(centerE3).normSq())
}