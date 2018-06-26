package geometry.construction

import geometry.primitives.Spherical2.*

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