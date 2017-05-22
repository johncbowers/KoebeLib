package geometry.primitives.Spherical2

class CircleArcS2(val source: PointS2, val target: PointS2, val circle: CircleS2) {

    constructor (p1: PointS2, p2: PointS2, p3: PointS2) :
        this(p1, p3, circleSupportingArc(p1, p2, p3))

    init {
        assert(source != target && source != -target)
        assert(circle.contains(source) && circle.contains(target))
    }
}

fun circleSupportingArc(p1: PointS2, p2: PointS2, p3: PointS2): CircleS2 {
    return CircleS2() // TODO
}