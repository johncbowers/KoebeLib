package geometry.primitives.Spherical2

import geometry.primitives.*;
/**
 * Created by johnbowers on 5/25/17.
 */


/**
 * Join of two disks. This intersects their dual planes to obtain the Plücker coordinates of the dual line.
 */
fun join(d1: DiskS2, d2: DiskS2) = CoaxialFamilyS2(
        determinant(d1.a, d1.b, d2.a, d2.b),
        determinant(d1.a, d1.c, d2.a, d2.c),
        determinant(d1.a, d1.d, d2.a, d2.d),
        determinant(d1.b, d1.c, d2.b, d2.c),
        determinant(d1.b, d1.d, d2.b, d2.d),
        determinant(d1.c, d1.d, d2.c, d2.d)
)

/**
 * The meet of two disks. This joints their dual points to obtain the Plücker coordinates of the dual line.
 */
fun meet(cp1: CPlaneS2, cp2: CPlaneS2) = CoaxialFamilyS2(
        determinant(cp1.a, cp1.b, cp2.a, cp2.b),
        determinant(cp1.a, cp1.c, cp2.a, cp2.c),
        determinant(cp1.b, cp1.c, cp2.b, cp2.c),
        determinant(cp1.a, cp1.d, cp2.a, cp2.d),
        determinant(cp1.b, cp1.d, cp2.b, cp2.d),
        determinant(cp1.c, cp1.d, cp2.c, cp2.d)
)

/**
 * The join of a coaxial family with a disk d. (In the dual is the meet of a line with a plane
 */
fun join(cf: CoaxialFamilyS2, disk: DiskS2) = CPlaneS2(
        cf.a * disk.c - cf.b * disk.b + cf.d * disk.a,
        cf.a * disk.d - cf.c * disk.b + cf.e * disk.a,
        cf.b * disk.d - cf.c * disk.c + cf.f * disk.a,
        cf.d * disk.d - cf.e * disk.c + cf.f * disk.b
)

fun meet(cf: CoaxialFamilyS2, cp: CPlaneS2) = DiskS2 (
        cf.a * cp.c - cf.b * cp.b + cf.c * cp.a,
        cf.a * cp.d - cf.d * cp.b + cf.e * cp.a,
        cf.b * cp.d - cf.d * cp.c + cf.f * cp.a,
        cf.c * cp.d - cf.e * cp.c + cf.f * cp.b
)

fun join(disk1: DiskS2, disk2: DiskS2, disk3: DiskS2) = CPlaneS2(
        a = - determinant(
                disk1.b, disk1.c, disk1.d,
                disk2.b, disk2.c, disk2.d,
                disk3.b, disk3.c, disk3.d),
        b = + determinant(
                disk1.a, disk1.c, disk1.d,
                disk2.a, disk2.c, disk2.d,
                disk3.a, disk3.c, disk3.d),
        c = - determinant(
                disk1.a, disk1.b, disk1.d,
                disk2.a, disk2.b, disk2.d,
                disk3.a, disk3.b, disk3.d),
        d = + determinant(
                disk1.a, disk1.b, disk1.c,
                disk2.a, disk2.b, disk2.c,
                disk3.a, disk3.b, disk3.c)
)

// TODO Ask Sprague if neural net can have signal input and combinatorial output


fun meet(plane1: CPlaneS2, plane2: CPlaneS2, plane3: CPlaneS2) = DiskS2(
        a = + determinant(plane1.b, plane1.c, plane1.d,
                plane2.b, plane2.c, plane2.d,
                plane3.b, plane3.c, plane3.d),
        b = - determinant(plane1.a, plane1.c, plane1.d,
                plane2.a, plane2.c, plane2.d,
                plane3.a, plane3.c, plane3.d),
        c = + determinant(plane1.a, plane1.b, plane1.d,
                plane2.a, plane2.b, plane2.d,
                plane3.a, plane3.b, plane3.d),
        d = - determinant(plane1.a, plane1.b, plane1.c,
                plane2.a, plane2.b, plane2.c,
                plane3.a, plane3.b, plane3.c)
)