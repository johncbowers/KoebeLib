package geometry.primitives.Euclidean2

/**
 * Created by johnbowers on 5/14/17.
 */


/**
 * @return true if A-B-C constitutes a left-hand turn; false otherwise.
 */
fun isLeftHandTurn(A: Point2d, B: Point2d, C: Point2d): Boolean {
    return areaOfParallelogram(A, B, C) > 0
}

/**
 * @return true if A-B-C constitutes a left-hand turn or collinear; false otherwise.
 */
fun isLeftHandTurnOrOn(A: Point2d, B: Point2d, C: Point2d): Boolean {
    return areaOfParallelogram(A, B, C) >= 0
}

/**
 * @return true if the set of points A-B-C is collinear; false otherwise.
 */
fun isCollinear(A: Point2d, B: Point2d, C: Point2d): Boolean {
    return areaOfParallelogram(A, B, C) == 0.0
}

/**
 * @return true if A-B-C constitutes a right-hand turn or collinear; false otherwise.
 */
fun isRightHandTurn(A: Point2d, B: Point2d, C: Point2d): Boolean {
    return areaOfParallelogram(A, B, C) < 0
}

/**
 * @return true if A-B-C constitutes a right-hand turn; false otherwise.
 */
fun isRightHandTurnOrOn(A: Point2d, B: Point2d, C: Point2d): Boolean {
    return areaOfParallelogram(A, B, C) <= 0
}

fun isSameSide(A: Point2d, B: Point2d, C: Point2d, D: Point2d): Boolean {
    return isLeftHandTurn(A, B, C) && isLeftHandTurn(A, B, D) || isRightHandTurn(A, B, C) && isRightHandTurn(A, B, D)
}

/**
 * @return the signed area of the parallelogram with consecutive vertices B, A, C
 */
fun areaOfParallelogram(A: Point2d, B: Point2d, C: Point2d): Double {
    return (B.x - A.x) * (C.y - A.y) - (C.x - A.x) * (B.y - A.y)
}

/**
 * @return the signed area of the triangle with corners A, B, C.
 */
fun areaOfTriangle(A: Point2d, B: Point2d, C: Point2d): Double {
    return 0.5 * areaOfParallelogram(A, B, C)
}

/**
 * Tests if point D is in the circle through A, B, C
 * Adapted from: https://www.cs.cmu.edu/afs/cs/project/quake/public/code/predicates.c
 */
fun inCircle(A: Point2d, B: Point2d, C: Point2d, D: Point2d): Boolean {

    val AD = A - D
    val BD = B - D
    val CD = C - D

    val abdet = AD.det(BD)
    val bcdet = BD.det(CD)
    val cadet = CD.det(AD)

    val alift = AD.normSq()
    val blift = BD.normSq()
    val clift = CD.normSq()

    return alift * bcdet + blift * cadet + clift * abdet > 0
}