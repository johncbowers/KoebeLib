package geometry.primitives.Euclidean3

import geometry.primitives.Spherical2.PointS2
import geometry.primitives.OrientedProjective3.PointOP3

class PlaneE3(val N: VectorE3, val d:Double) {

        //p1: PointE3, p2: PointE3, p3: PointE3) {

    constructor (p : PlaneE3) : this(VectorE3(p.N), p.d)

    constructor () : this(VectorE3(0.0, 0.0, 1.0), 0.0)

    constructor (p1: PointE3, p2: PointE3, p3: PointE3) :
            this((p2-p1).cross(p3-p1),
                    (PointE3.O-p1).dot((p2-p1).cross(p3-p1)))


    fun pointE3ClosestOrigin() = PointE3(- N.x * d / N.normSq(), - N.y * d / N.normSq(), - N.z * d / N.normSq())
    fun pointOP3ClosestOrigin() = PointOP3(- N.x, - N.y, - N.z, N.normSq() / d)
}