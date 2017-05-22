package geometry.primitives.Euclidean2

import geometry.primitives.*;
/**
 * Created by johnbowers on 5/14/17.
 */

fun isLeftHandTurn(a: PointE2, b: PointE2, c: PointE2) = areaOfParallelogram(a, b, c) > 0

fun isCollinear(a: PointE2, b: PointE2, c: PointE2) = isZero(areaOfParallelogram(a, b, c))

fun isRightHandTurn(a: PointE2, b: PointE2, c: PointE2) = areaOfParallelogram(a, b, c) < 0

fun isSameSide(a: PointE2, b: PointE2, c: PointE2, e: PointE2) =
        isLeftHandTurn(a, b, c) && isLeftHandTurn(a, b, e) || isRightHandTurn(a, b, c) && isRightHandTurn(a, b, e)

fun areaOfParallelogram(a: PointE2, b: PointE2, c: PointE2) = (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y)

fun areaOfTriangle(a: PointE2, b: PointE2, c: PointE2) = 0.5 * areaOfParallelogram(a, b, c)

/**
 * Tests if point e is in the circle through a, b, c
 * Adapted from: https://www.cs.cmu.edu/afs/cs/project/quake/public/code/predicates.c
 */
fun inCircle(a: PointE2, b: PointE2, c: PointE2, e: PointE2): Boolean {

    val ad = a - e
    val bd = b - e
    val cd = c - e

    val abdet = ad.det(bd)
    val bcdet = bd.det(cd)
    val cadet = cd.det(ad)

    val alift = ad.normSq()
    val blift = bd.normSq()
    val clift = cd.normSq()

    return alift * bcdet + blift * cadet + clift * abdet > 0
}