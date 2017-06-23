package geometry.primitives

/**
 * Created by johnbowers on 5/20/17.
 */

fun isZero(x:Double) = Math.abs(x) < 1e-8

fun determinant(a:Double, b:Double, c:Double, d:Double) = a * d - b * c

fun determinant(a:Double, b:Double, c:Double,
                d:Double, e:Double, f:Double,
                g:Double, h:Double, i:Double) =
        a * determinant(e, f, h, i) -
        b * determinant(d, f, g, i) +
        c * determinant(d, e, g, h)

fun determinant(a:Double, b:Double, c:Double, d:Double,
                e:Double, f:Double, g:Double, h:Double,
                i:Double, j:Double, k:Double, l:Double,
                m:Double, n:Double, o:Double, p:Double) =
        a * determinant(f, g, h, j, k, l, n, o, p) -
        b * determinant(e, g, h, i, k, l, m, o, p) +
        c * determinant(e, f, h, i, j, l, m, n, p) -
        d * determinant(e, f, g, i, j, k, m, n, o)

fun inner_product(x1:Double, y1:Double, x2:Double, y2:Double) = x1*x2 + y1*y2

fun inner_product(x1:Double, y1:Double, z1:Double,
                  x2:Double, y2:Double, z2:Double) = x1*x2 + y1*y2 + z1*z2

fun inner_product(x1:Double, y1:Double, z1:Double, w1:Double,
                  x2:Double, y2:Double, z2:Double, w2:Double) = x1*x2 + y1*y2 + z1*z2 + w1*w2

fun inner_product31(x1:Double, y1:Double, z1:Double, w1:Double,
                  x2:Double, y2:Double, z2:Double, w2:Double) = x1*x2 + y1*y2 + z1*z2 - w1*w2
fun norm31(x1:Double, y1:Double, z1:Double, w1:Double) = x1*x1 + y1*y1 + z1*z1 - (w1*w1)

fun are_dependent(a1:Double, b1:Double, c1:Double,
                  a2:Double, b2:Double, c2:Double) =
        isZero(determinant(a1, b1, a2, b2)) &&
        isZero(determinant(a1, c1, a2, c2)) &&
        isZero(determinant(b1, c1, b2, c2))


fun are_dependent(a1:Double, b1:Double, c1:Double, d1:Double,
                  a2:Double, b2:Double, c2:Double, d2:Double) =
        isZero(determinant(a1, b1, a2, b2)) &&
        isZero(determinant(a1, c1, a2, c2)) &&
        isZero(determinant(a1, d1, a2, d2)) &&
        isZero(determinant(b1, c1, b2, c2)) &&
        isZero(determinant(b1, d1, b2, d2)) &&
        isZero(determinant(c1, d1, c2, d2))