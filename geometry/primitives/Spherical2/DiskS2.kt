package geometry.primitives.Spherical2

import geometry.primitives.Euclidean3.DirectionE3
import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.Euclidean3.VectorE3
import geometry.primitives.Euclidean3.PlaneE3
import geometry.primitives.Euclidean3.least_dominant
import geometry.primitives.OrientedProjective3.PointOP3
import geometry.primitives.OrientedProjective3.PlaneOP3
import geometry.primitives.determinant
import geometry.primitives.inner_product
import geometry.primitives.isZero


/**
 * This class represents a disk whose boundary is a circle on S2. Some explanation of this class is probably
 * warranted, since the representation we use is less common.
 *
 * Given any circle on S2, there is a unique plane in R3 passing through the circle with the equation:
 *
 * a x + b y + c z + d = 0.
 *
 * However, it is often convenient to think of this as a hyperplane in R4 through the origin:
 *
 * a x + b y + c z + d w = 0
 *
 * The intersection of this plane with the w = 1 hyperplane in R4 gives us the usual plane in R3.
 */
class DiskS2(val a: Double, val b: Double, val c: Double, val d: Double) {


    constructor (disk: DiskS2) : this(disk.a, disk.b, disk.c, disk.d)

    /**
     * Circle through points p1, p2, p3
     */
    constructor (p1: PointS2, p2: PointS2, p3: PointS2) :
            // OLD R3 View:
            // this((p2.directionE3-p1.directionE3).cross(p3.directionE3-p1.directionE3).v,
            //        (-p1.directionE3).dot((p2.directionE3-p1.directionE3).cross(p3.directionE3-p1.directionE3)))
            // Same thing, but treating p1, p2, p3 as homogeneous equations (i.e. lines through the origin in R4):
            this(
                    a = + determinant(
                            p1.directionE3.v.y, p1.directionE3.v.z, 1.0,
                            p2.directionE3.v.y, p2.directionE3.v.z, 1.0,
                            p3.directionE3.v.y, p3.directionE3.v.z, 1.0
                    ),
                    b = - determinant(
                            p1.directionE3.v.x, p1.directionE3.v.z, 1.0,
                            p2.directionE3.v.x, p2.directionE3.v.z, 1.0,
                            p3.directionE3.v.x, p3.directionE3.v.z, 1.0
                    ),
                    c = + determinant(
                            p1.directionE3.v.x, p1.directionE3.v.y, 1.0,
                            p2.directionE3.v.x, p2.directionE3.v.y, 1.0,
                            p3.directionE3.v.x, p3.directionE3.v.y, 1.0
                    ),
                    d = - determinant(
                            p1.directionE3.v.x, p1.directionE3.v.y, p1.directionE3.v.z,
                            p2.directionE3.v.x, p2.directionE3.v.y, p2.directionE3.v.z,
                            p3.directionE3.v.x, p3.directionE3.v.y, p3.directionE3.v.z
                    )
            )

    /**
     * Great disk through p1 and p2
     */
    constructor (p1: PointS2, p2: PointS2) :
            this (
                    a = + determinant(p1.y, p1.z, p2.y, p2.z),
                    b = - determinant(p1.x, p1.z, p2.x, p2.z),
                    c = + determinant(p1.x, p1.y, p2.x, p2.y),
                    d = 0.0
            )

    // Basis vectors for the disk
    val basis1 : VectorE3 get() = least_dominant(VectorE3(a, b, c)).vec.cross(VectorE3(a, b, c))
    val basis2 : VectorE3 get() = VectorE3(a, b, c).cross(basis1)
    val basis3 : VectorE3 get() = VectorE3(a, b, c)

    // Normalized basis vectors as DirectionE3
    val normedBasis1 : DirectionE3 get() = DirectionE3(basis1)
    val normedBasis2 : DirectionE3 get() = DirectionE3(basis2)
    val normedBasis3 : DirectionE3 get() = DirectionE3(basis3)

    fun contains(p: PointS2): Boolean = isZero(inner_product(p.x, p.y, p.z, 1.0, a, b, c, d))
    //isZero(VectorE3(a, b, c).dot(p.directionE3.v))

    val directionE3: DirectionE3 get() = DirectionE3(VectorE3(a, b, c))

    val dualPlaneOP3: PlaneOP3 get() = PlaneOP3(a, b, c, d)

    val dualPointOP3: PointOP3
        get() {
            assert(d != 0.0)
            return centerOP3.unitSphereInversion()
        }

    val centerOP3: PointOP3
        get() {
            //PlaneE3(N, d).pointClosestOrigin()
            return PlaneE3(VectorE3(a, b, c), d).pointOP3ClosestOrigin()
        }

    val radiusE3: Double get() = Math.sqrt(1.0 - (centerOP3.toPointE3() - PointE3.O).normSq())
}