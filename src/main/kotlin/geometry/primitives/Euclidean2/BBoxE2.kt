package geometry.primitives.Euclidean2

class BBoxE2(val minX: Double, val maxX: Double, val minY: Double, val maxY: Double) {

    constructor(points: List<PointE2>) : this(
            ((points.minBy { it.x })?.x ?: 0.0),
            ((points.maxBy { it.x })?.x ?: 0.0),
            ((points.minBy { it.y })?.y ?: 0.0),
            ((points.maxBy { it.y })?.y ?: 0.0)
    )

    val width by lazy { maxX - minX }
    val height by lazy { maxY - minY }
}