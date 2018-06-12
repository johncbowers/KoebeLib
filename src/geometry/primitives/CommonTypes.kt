package geometry.primitives

/**
 * Created by johnbowers on 6/13/17.
 */


enum class Orientation(val value: Int) {NEGATIVE(-1), ZERO(0), POSITIVE(1)}
enum class DiskOrientation(val value: Int) {OUTSIDE(-1), COCIRCULAR(0), INSIDE(1)}