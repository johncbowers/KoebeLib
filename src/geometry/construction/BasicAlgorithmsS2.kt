package geometry.construction

import geometry.primitives.Euclidean3.VectorE3
import geometry.primitives.Spherical2.*
import geometry.primitives.OrientedProjective3.LineOP3
import java.awt.Point

/**
 * Created by johnbowers on 5/28/17.
 */

class ThreePointsToDiskS2() : IAlgorithm<DiskS2> {
    override fun run(node: ConstructionNode<DiskS2>): DiskS2 {

        if (node.incoming.size != 3)
            throw InvalidConstructionParametersException("ThreePointsToDiskS2 expects three PointS2s. ${node.incoming.size} given.")

        val p1 = node.incoming[0].getOutput()
        val p2 = node.incoming[1].getOutput()
        val p3 = node.incoming[2].getOutput()
        if (p1 is PointS2 && p2 is PointS2 && p3 is PointS2) {
            return DiskS2(p1, p2, p3)
        } else {
            throw InvalidConstructionParametersException("ThreePointsToDiskS2 expects three PointS2s.")
        }
    }
}

class ThreeDisksToCPlaneS2() : IAlgorithm<CPlaneS2> {
    override fun run(node: ConstructionNode<CPlaneS2>): CPlaneS2 {

        if (node.incoming.size != 3)
            throw InvalidConstructionParametersException("ThreePointsToDiskS2 expects three PointS2s. ${node.incoming.size} given.")

        val disk1 = node.incoming[0].getOutput()
        val disk2 = node.incoming[1].getOutput()
        val disk3 = node.incoming[2].getOutput()
        if (disk1 is DiskS2 && disk2 is DiskS2 && disk3 is DiskS2) {
            return join(disk1, disk2, disk3)
        } else {
            throw InvalidConstructionParametersException("ThreeDisksToCPlaneS2 expects three DiskS2s.")
        }
    }
}

class ThreePlaneToDisk() : IAlgorithm<DiskS2> {
    override fun run(node: ConstructionNode<DiskS2>): DiskS2 {

        if (node.incoming.size != 3)
            throw InvalidConstructionParametersException("ThreePointsToDiskS2 expects three PointS2s. ${node.incoming.size} given.")

        val plane1 = node.incoming[0].getOutput()
        val plane2 = node.incoming[1].getOutput()
        val plane3 = node.incoming[2].getOutput()
        if (plane1 is CPlaneS2 && plane2 is CPlaneS2 && plane3 is CPlaneS2) {
            return meet(plane1, plane2, plane3)
        } else {
            throw InvalidConstructionParametersException("ThreeDisksToCPlaneS2 expects three DiskS2s.")
        }
    }
}

class TwoPointsToCoaxialFamilyS2() : IAlgorithm<CoaxialFamilyS2> {
    override fun run(node: ConstructionNode<CoaxialFamilyS2>): CoaxialFamilyS2 {

        if (node.incoming.size != 3)
            throw InvalidConstructionParametersException("TwoPointsToCoaxialFamilyS2 expects two DiskS2s. ${node.incoming.size} given.")

        val disk1 = node.incoming[0].getOutput()
        val disk2 = node.incoming[1].getOutput()

        if (disk1 is DiskS2 && disk2 is DiskS2) {
            return CoaxialFamilyS2(disk1, disk2)
        } else {
            throw InvalidConstructionParametersException("TwoPointsToCoaxialFamilyS2 expects two DiskS2s.")
        }
    }
}

class TwoDisksPointCoaxialCircle() : IAlgorithm<DiskS2> {
    override fun run(node: ConstructionNode<DiskS2>): DiskS2 {
        if (node.incoming.size != 3) {
            throw InvalidConstructionParametersException("TwoDisksPointCoaxialCircle expects two DiskS2s and one PointS2. ${node.incoming.size}  given.")

        }

        val disk1 = node.incoming[0].getOutput() as DiskS2
        val disk2 = node.incoming[1].getOutput() as DiskS2
        val point = node.incoming[2].getOutput() as PointS2
        val pointVector = point.directionE3.v.normalize()
        val a1 = disk1.a
        val b1 = disk1.b
        val c1 = disk1.c
        val d1 = disk1.d
        val a2 = disk2.a
        val b2 = disk2.b
        val c2 = disk2.c
        val d2 = disk2.d
        val x = pointVector.x
        val y = pointVector.y
        val z = pointVector.z
        val w = 1

        val a = a2 * (d1 * w + b1 * y + c1 * z) - a1 * (d2 * w + b2 * y + c2 * z)
        val b = b2 * (d1 * w + a1 * x + c1 * z) - b1 * (d2 * w + a2 * x + c2 * z)
        val c = c2 * (d1 * w + a1 * x + b1 * y) - c1 * (d2 * w + a2 * x + b2 * y)
        val d = -a2 * d1 * x + d2 * (a1 * x + b1 * y + c1 * z) - d1 * (b2 * y + c2 * z)

        return DiskS2(a, b, c, d)
    }
}

class TwoDisksPointHyperbolicCircle(): IAlgorithm<DiskS2> {
    override fun run(node: ConstructionNode<DiskS2>) : DiskS2 {
        if (node.incoming.size != 3) {
            throw InvalidConstructionParametersException("TwoDisksPointCoaxialCircle expects two DiskS2s and one PointS2. ${node.incoming.size}  given.")

        }

        val disk1 = node.incoming[0].getOutput() as DiskS2
        val disk2 = node.incoming[1].getOutput() as DiskS2
        val point = node.incoming[2].getOutput() as PointS2
        val pointVector = point.directionE3.v.normalize()
        val a1 = disk1.dualPointOP3.hx
        val b1 = disk1.dualPointOP3.hy
        val c1 = disk1.dualPointOP3.hz
        val d1 = disk1.dualPointOP3.hw
        val a2 = disk2.dualPointOP3.hx
        val b2 = disk2.dualPointOP3.hy
        val c2 = disk2.dualPointOP3.hz
        val d2 = disk2.dualPointOP3.hw
        val x = pointVector.x
        val y = pointVector.y
        val z = pointVector.z
        val w = 1

        val a = a2 * (d1 * w + b1 * y + c1 * z) - a1 * (d2 * w + b2 * y + c2 * z)
        val b = b2 * (d1 * w + a1 * x + c1 * z) - b1 * (d2 * w + a2 * x + c2 * z)
        val c = c2 * (d1 * w + a1 * x + b1 * y) - c1 * (d2 * w + a2 * x + b2 * y)
        val d = -a2 * d1 * x + d2 * (a1 * x + b1 * y + c1 * z) - d1 * (b2 * y + c2 * z)

        return DiskS2(a, b, c, d)
    }
}

class TwoDisksIntersectionPoint() : IAlgorithm<List<PointS2>> {
    override fun run(node: ConstructionNode<List<PointS2>>): List<PointS2> {
        if (node.incoming.size < 2) {
            throw InvalidConstructionParametersException("TwoDisksIntersectionPoint expects two DiskS2s. ${node.incoming.size} given.")
        }

        val disk1 = node.incoming[0].getOutput() as DiskS2
        val disk2 = node.incoming[1].getOutput() as DiskS2

        var isectPoints = LineOP3(disk1.dualPlaneOP3, disk2.dualPlaneOP3).getIntersectionWithUnit2Sphere().map {
            p -> PointS2(p.toVectorE3())
        }
        return isectPoints

    }

}