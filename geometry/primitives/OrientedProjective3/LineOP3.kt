package geometry.primitives.OrientedProjective3

import geometry.primitives.Euclidean3.*
import geometry.primitives.determinant
import geometry.primitives.isZero

/**
 * Created by johnbowers on 5/25/17.
 */

class LineOP3(
        val a : Double, // p01
        val b : Double, // p02
        val c : Double, // p03
        val d : Double, // p12
        val e : Double, // p13
        val f : Double // p23
)
{

    constructor(p1: PointOP3, p2: PointOP3): this(
            determinant(p1.hx, p1.hy, p2.hx, p2.hy), // p01 
            determinant(p1.hx, p1.hz, p2.hx, p2.hz), // p02
            determinant(p1.hx, p1.hw, p2.hx, p2.hw), // p03
            determinant(p1.hy, p1.hz, p2.hy, p2.hz), // p12
            determinant(p1.hy, p1.hw, p2.hy, p2.hw), // p13
            determinant(p1.hz, p1.hw, p2.hz, p2.hw)  // p23
    )

    constructor(p1: PlaneOP3, p2: PlaneOP3): this(
            +determinant(p1.Z, p1.W, p2.Z, p2.W), // p01
            -determinant(p1.Y, p1.W, p2.Y, p2.W), // p02
            +determinant(p1.Y, p1.Z, p2.Y, p2.Z), // p03
            +determinant(p1.X, p1.W, p2.X, p2.W), // p12
            -determinant(p1.X, p1.Z, p2.X, p2.Z), // p13
            +determinant(p1.X, p1.Y, p2.X, p2.Y) // p23
    )
    
    val p01 = a
    val p02 = b
    val p03 = c
    val p12 = d
    val p13 = e 
    val p23 = f

    val dualLineOP3: LineOP3 by lazy { LineOP3(p23, -p13, p12, p03, -p02, p01) }
    
    fun getIntersectionWithUnit2Sphere(): List<PointE3> {

        // TODO Decide if this should be done with homogeneous coordinates
        val v = VectorE3( -p03, -p13, -p23)
        val m = VectorE3( -p12, p02,  -p01)
        
        // TODO (Sarah) Compute the intersections of this line with the 2-sphere: 

        var px: Double
        var py: Double
        var pz: Double

        // obtain a point on the line by choosing a point parallel to the x, y, or z plane
        // three points of intersection between the line and the x=0, y=0, and z=0 planes

        // ex: val basis1 : VectorE3 by lazy { least_dominant(VectorE3(a, b, c)).vec.cross(VectorE3(a, b, c)) }
        // larger than or equal to

        // x = 0 plane
        if (v.x >= v.y && v.x >= v.z) {
            px = 0.0
            py = m.z / v.x
            pz = -m.y / v.x
        }
        // y = 0 plane
        else if (v.y >= v.x && v.y >= v.z) {
            px = -m.z / v.y
            py = 0.0
            pz = m.x / v.y
        }
        // z = 0 plane
        else {
            px = m.y / v.z
            py = -m.x / v.z
            pz = 0.0
        }

        val p = VectorE3(px, py, pz)

        // want the point where p+tv intersects the unit sphere, i.e. where ||p|| = 1;  p.p = 1

        // determine whether line intersects sphere at 0, 1, or 2 points
        val rad = Math.sqrt( 4*Math.pow(p.dot(v), 2.0) - 4.0*(p.dot(p)- 1)*(v.dot(v)) )

        // if intersects at one point (line is tangent to the unit sphere)
        if (isZero(rad)) {

            val t = -p.dot(v)/v.dot(v)
            val u = (p + t * v).toPointE3()

            return listOf<PointE3>(u)
        }

        // if line intersects the sphere at two points
        else if (rad > 0.0 ) {

            val t1 = (-2 * (p.dot(v)) + Math.sqrt(4 * Math.pow(p.dot(v), 2.0) - 4.0 * (p.dot(p) - 1) * (v.dot(v)))) / (2 * v.dot(v))
            val t2 = (-2 * (p.dot(v)) - Math.sqrt(4 * Math.pow(p.dot(v), 2.0) - 4.0 * (p.dot(p) - 1) * (v.dot(v)))) / (2 * v.dot(v))

            // plugging back into the parametrized eq. for the line p + tv
            val u1 = (p + t1 * v).toPointE3()
            val u2 = (p + t2 * v).toPointE3()

            return listOf<PointE3>(u1, u2)
        }

        // if line does not intersect the sphere
        else {
            return listOf<PointE3>()
        }
    }
    
}
//
//fun join(p1: PointOP3, p2: PointOP3) = LineOP3(
//        determinant(p1.hx, p1.hy, p2.hx, p2.hy),
//        determinant(p1.hx, p1.hz, p2.hx, p2.hz),
//        determinant(p1.hy, p1.hz, p2.hy, p2.hz),
//        determinant(p1.hx, p1.hw, p2.hx, p2.hw),
//        determinant(p1.hy, p1.hw, p2.hy, p2.hw),
//        determinant(p1.hz, p1.hw, p2.hz, p2.hw))
//
//fun meet(p1: PlaneOP3, p2: PlaneOP3) = LineOP3(
//        determinant(p1.X, p1.Y, p2.X, p2.Y), // p01
//        determinant(p1.X, p1.Z, p2.X, p2.Z), // p02
//        determinant(p1.X, p1.W, p2.X, p2.W), // p03
//        determinant(p1.Y, p1.Z, p2.Y, p2.Z), // p12
//        determinant(p1.Y, p1.W, p2.Y, p2.W), // p13
//        determinant(p1.Z, p1.W, p2.Z, p2.W))  // p23