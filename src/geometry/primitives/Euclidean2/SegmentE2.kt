package geometry.primitives.Euclidean2

class SegmentE2(val source: PointE2, val target: PointE2) {

    val lengthSq by lazy { target.distSqTo(source) }
    val length by lazy { Math.sqrt(lengthSq) }

    fun closestPointE2To(p: PointE2): PointE2 {
        // Return minimum distance between line segment vw and point p
        //if (l2 == 0.0) return distance(p, v);   // v == w case
        // Consider the line extending the segment, parameterized as v + t (w - v).
        // We find projection of point p onto the line.
        // It falls where t = [(p-v) . (w-v)] / |w-v|^2
        // We clamp t from [0,1] to handle points outside the segment vw.
        val t = Math.max(0.0, Math.min(1.0, (p - source).dot(target - source) / lengthSq))
        return source + t * (target - source)
    }
}

