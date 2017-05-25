package geometry.primitives.Spherical2

import geometry.primitives.determinant
/**
 * Created by johnbowers on 5/25/17.
 */


/**
 * Join of two disks.
 */
fun join(d1: DiskS2, d2: DiskS2) = CoaxialFamily(
        determinant(d1.a, d1.b, d2.a, d2.b),
        determinant(d1.a, d1.c, d2.a, d2.c),
        determinant(d1.b, d1.c, d2.b, d2.c),
        determinant(d1.a, d1.d, d2.a, d2.d),
        determinant(d1.b, d1.d, d2.b, d2.d),
        determinant(d1.c, d1.d, d2.c, d2.d)
)

/**
 * The meet of two disks. 
 */
fun meet(cp1: CPlaneS2, cp2: CPlaneS2) = CoaxialFamily(
        determinant(cp1.a, cp1.b, cp2.a, cp2.b),
        determinant(cp1.a, cp1.c, cp2.a, cp2.c),
        determinant(cp1.a, cp1.d, cp2.a, cp2.d),
        determinant(cp1.b, cp1.c, cp2.b, cp2.c),
        determinant(cp1.b, cp1.d, cp2.b, cp2.d),
        determinant(cp1.c, cp1.d, cp2.c, cp2.d)
)

fun join(cf: CoaxialFamily, d: DiskS2) : CPlaneS2 {
    return CPlaneS2(0.0, 0.0, 0.0, 0.0) // TODO
}

fun meet(cf: CoaxialFamily, cp2: CPlaneS2) : DiskS2 {
    return DiskS2(0.0, 0.0, 0.0, 0.0); // TODO
}

