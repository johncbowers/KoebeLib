package geometry.construction

import geometry.primitives.Euclidean3.VectorE3
import geometry.primitives.Spherical2.*
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

class TwoDisksIntersectionPoint() : IAlgorithm<PointS2> {
    override fun run(node: ConstructionNode<PointS2>): PointS2 {
        if (node.incoming.size != 2) {
            throw InvalidConstructionParametersException("TwoDisksIntersectionPoint expects two DiskS2s. ${node.incoming.size} given.")
        }

        val disk1 = node.incoming[0].getOutput() as DiskS2
        val disk2 = node.incoming[1].getOutput() as DiskS2

        val disk1Normal = VectorE3(disk1.a, disk1.b, disk1.c)
        val disk2Normal = VectorE3(disk2.a, disk2.b, disk2.c)
        val intersectionNormal = disk1Normal.cross(disk2Normal)
        val point = pointOnPlanes(disk1, disk2)

        return PointS2()


    }

    fun pointOnPlanes(disk1 : DiskS2, disk2 : DiskS2): PointS2 {
        var numerator = -1*(disk1.d*disk2.a + disk1.a*disk2.d)
        var denom = -1*(disk1.a*disk2.b + disk1.b*disk2.a)
        var y = numerator/denom
        var x = (-disk1.d - disk1.b*y)/disk1.a

        return PointS2(x, y, 0.0)
    }
}