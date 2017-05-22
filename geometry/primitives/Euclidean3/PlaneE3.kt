package geometry.primitives.Euclidean3

import geometry.primitives.Spherical2.PointS2

class PlaneE3(val N: VectorE3 = VectorE3(0.0, 0.0, 1.0), val d:Double = 0.0) {

        //p1: PointE3, p2: PointE3, p3: PointE3) {

    constructor (p1: PointE3, p2: PointE3, p3: PointE3) :
            this((p2-p1).cross(p3-p1),
                    (PointE3.O-p1).dot((p2-p1).cross(p3-p1)))


    fun pointClosestOrigin() = PointE3(- N.x * d / N.normSq(), - N.y * d / N.normSq(), - N.z * d / N.normSq())
}