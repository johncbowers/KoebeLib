package geometry.primitives.Spherical2

import geometry.primitives.*
import geometry.primitives.Euclidean3.DirectionE3
import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.Euclidean3.VectorE3
import geometry.primitives.Euclidean3.PlaneE3
import geometry.primitives.Euclidean3.least_dominant
import geometry.primitives.OrientedProjective2.DiskOP2
import geometry.primitives.OrientedProjective2.PointOP2
import geometry.primitives.OrientedProjective3.PointOP3
import geometry.primitives.OrientedProjective3.PlaneOP3


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

    /* Constructors */

    constructor (disk: DiskS2) : this(disk.a, disk.b, disk.c, disk.d)

    /**
     * Circle through points p1, p2, p3
     */
    constructor (p1: PointS2, p2: PointS2, p3: PointS2) :
            this(
                    a = + determinant(
                            p1.directionE3.v.y, p1.directionE3.v.z, 1.0,
                            p3.directionE3.v.y, p3.directionE3.v.z, 1.0,
                            p2.directionE3.v.y, p2.directionE3.v.z, 1.0
                    ),
                    b = - determinant(
                            p1.directionE3.v.x, p1.directionE3.v.z, 1.0,
                            p3.directionE3.v.x, p3.directionE3.v.z, 1.0,
                            p2.directionE3.v.x, p2.directionE3.v.z, 1.0
                    ),
                    c = + determinant(
                            p1.directionE3.v.x, p1.directionE3.v.y, 1.0,
                            p3.directionE3.v.x, p3.directionE3.v.y, 1.0,
                            p2.directionE3.v.x, p2.directionE3.v.y, 1.0
                    ),
                    d = - determinant(
                            p1.directionE3.v.x, p1.directionE3.v.y, p1.directionE3.v.z,
                            p3.directionE3.v.x, p3.directionE3.v.y, p3.directionE3.v.z,
                            p2.directionE3.v.x, p2.directionE3.v.y, p2.directionE3.v.z
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

    /* Properties */

    // Basis vectors for the disk
    val basis1 : VectorE3 by lazy { least_dominant(VectorE3(a, b, c)).vec.cross(VectorE3(a, b, c)) }
    val basis2 : VectorE3 by lazy { basis1.cross(VectorE3(a, b, c)) }
    val basis3 : VectorE3 by lazy { VectorE3(a, b, c) }

    // Normalized basis vectors as DirectionE3
    val normedBasis1 : DirectionE3 by lazy { DirectionE3(basis1) }
    val normedBasis2 : DirectionE3 by lazy { DirectionE3(basis2) }
    val normedBasis3 : DirectionE3 by lazy { DirectionE3(basis3) }

    // Unit vector pointing in direction of center of circle
    val directionE3: DirectionE3 by lazy{ DirectionE3(VectorE3(a, b, c)) }

    // Dual plane and point in oriented projective 3-space
    val dualPlaneOP3: PlaneOP3 by lazy { PlaneOP3(a, b, c, d) }
    val dualPointOP3: PointOP3 by lazy { PointOP3(-a, -b, -c, d) }// WAS: centerOP3.unitSphereInversion() }

    val dualCPlaneS2: CPlaneS2 by lazy { CPlaneS2(-a, -b, -c, d) }

    val centerE3: PointE3 by lazy { PointOP3(-a, -b, -c, (a*a + b*b + c*c) / d).toPointE3() }// PlaneE3(VectorE3(a, b, c), d).pointOP3ClosestOrigin() }

    // Euclidean radius of circle
    val radiusE3: Double by lazy { Math.sqrt(1.0 - (centerE3 - PointE3.O).normSq()) }

    /* Tests */

    // TODO Rethink this (is there a way to get rid of the norm?)
    fun contains(p: PointS2): Boolean = isZero(inner_product(p.x, p.y, p.z, VectorE3(p.x, p.y, p.z).norm(), a, b, c, d))

    override fun equals(that: Any?) = this === that ||
            (that is DiskS2 && are_dependent(a, that.a, b, that.b, c, that.c, d, that.d))

    /* Computations */

    // With our disks represented as the hyperplane ax + by + cz + dw = 0, it can be shown that the
    // the inversive distance between two disks (a1, b1, c1, d1) and (a2, b2, c2, d2) is simply the
    // cosine of the angle between the two disks under the (3, 1) Minkowski metric.
    // TODO We need to re-derive this to check that it all sorts out.
    fun inversiveDistTo(disk: DiskS2) : Double {
        val ip12 = inner_product31(a, b, c, d, disk.a, disk.b, disk.c, disk.d)
        val ip11 = inner_product31(a, b, c, d, a, b, c, d)
        val ip22 = inner_product31(disk.a, disk.b, disk.c, disk.d, disk.a, disk.b, disk.c, disk.d)
        return -ip12 / (Math.sqrt(ip11) * Math.sqrt(ip22))
    }

    /**
     * @return The CPlaneS2 containing the points of equal inversive distance between this and disk.
     */
    fun bisectorWith(disk: DiskS2): CPlaneS2 {

        // First we normalize our vectors with respect to the Minkowski 3,1 inner product:
        val minNorm1 = Math.sqrt(inner_product31(a, b, c, d, a, b, c, d))
        val minNorm2 = Math.sqrt(inner_product31(disk.a, disk.b, disk.c, disk.d, disk.a, disk.b, disk.c, disk.d))

        // Now find the coefficients (a, b, c, d) of the plane of equal Minkowski 3,1 inner product from
        // the normalized vectors of this and disk:
        val a = disk.a / minNorm2 - this.a / minNorm1
        val b = disk.b / minNorm2 - this.b / minNorm1
        val c = disk.c / minNorm2 - this.c / minNorm1
        val d = this.d / minNorm1 - disk.d / minNorm2

        // Return the resulting bisector plane
        return CPlaneS2(a, b, c, d)
    }

    fun get3PointsOnDisk(): List<PointS2> {

        // translate basis vectors to center of the disk and scale by radius
        val newBasis1 = normedBasis1.v * radiusE3 + centerE3.toVectorE3()
        val newBasis2 = normedBasis2.v * radiusE3 + centerE3.toVectorE3()
        val newBasis3 = - normedBasis1.v * radiusE3 + centerE3.toVectorE3()
        val newBasis4 = - normedBasis2.v * radiusE3 + centerE3.toVectorE3()

        val negZ = VectorE3(0.0, 0.0, -1.0)
        val isNegZ1 = isZero(1.0 - newBasis1.dot(negZ) / newBasis1.norm())
        val isNegZ2 = isZero(1.0 - newBasis2.dot(negZ) / newBasis2.norm())
        val isNegZ3 = isZero(1.0 - newBasis3.dot(negZ) / newBasis3.norm())
        val isNegZ4 = isZero(1.0 - newBasis4.dot(negZ) / newBasis4.norm())

        if (!(isNegZ1 || isNegZ2 || isNegZ3))
            return listOf<PointS2>(PointS2(newBasis1), PointS2(newBasis2), PointS2(newBasis3))
        else if (!(isNegZ2 || isNegZ3 || isNegZ4))
            return listOf<PointS2>(PointS2(newBasis2), PointS2(newBasis3), PointS2(newBasis4))
        else if (!(isNegZ3 || isNegZ4 || isNegZ1))
            return listOf<PointS2>(PointS2(newBasis3), PointS2(newBasis4), PointS2(newBasis1))
        else
            return listOf<PointS2>(PointS2(newBasis4), PointS2(newBasis1), PointS2(newBasis2))
    }

    fun invertThrough(disk: DiskS2): DiskS2 {

        val fact =
                inner_product31(a, b, c, d, disk.a, disk.b, disk.c, disk.d) /
                inner_product31(disk.a, disk.b, disk.c, disk.d, disk.a, disk.b, disk.c, disk.d)

        return DiskS2(
                a - 2 * fact * disk.a,
                b - 2 * fact * disk.b,
                c - 2 * fact * disk.c,
                d - 2 * fact * disk.d
        )
    }

    fun sgProjectToOP2(): DiskOP2 {
        var pointsOP2 = mutableListOf<PointOP2>()

        // Get three points on DiskS2
        var pointsE3 = this.get3PointsOnDisk()

        // Project the PointE3s to PointOP2s
        for (point in pointsE3) {
            pointsOP2.add(point.sgProjectToPointOP2())
        }
        // Return the diskOP2 through 3 projected PointOP2s
        return DiskOP2(pointsOP2[0], pointsOP2[1], pointsOP2[2])
    }
}