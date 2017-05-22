package geometry.primitives.Euclidean3

enum class DominantE3(val vec: VectorE3) {
    E3_POSX(VectorE3(1.0, 0.0, 0.0)),
    E3_NEGX(VectorE3(-1.0, 0.0, 0.0)),
    E3_POSY(VectorE3(0.0, 1.0, 0.0)),
    E3_NEGY(VectorE3(0.0, -1.0, 0.0)),
    E3_POSZ(VectorE3(0.0, 0.0, 1.0)),
    E3_NEGZ(VectorE3(0.0, 0.0, -1.0))
}

fun dominant(dx: Double, dy: Double, dz: Double): DominantE3
{
    val dxabs = if (dx >= 0.0) dx else -dx
    val dyabs = if (dy >= 0.0) dy else -dy
    val dzabs = if (dz >= 0.0) dz else -dz

    if (dxabs >= dyabs && dxabs >= dzabs)
        return if (dx > 0.0)  DominantE3.E3_POSX else DominantE3.E3_NEGX
    else if(dyabs >= dzabs)
        return if (dy > 0.0) DominantE3.E3_POSY else DominantE3.E3_NEGY
    else
        return if (dz > 0.0) DominantE3.E3_POSZ else DominantE3.E3_NEGZ
}

fun least_dominant(dx: Double, dy: Double, dz: Double): DominantE3
{
    val dxabs = if (dx >= 0.0) dx else -dx
    val dyabs = if (dy >= 0.0) dy else -dy
    val dzabs = if (dz >= 0.0) dz else -dz

    if (dxabs <= dyabs && dxabs <= dzabs)
        return if (dx >= 0.0)  DominantE3.E3_POSX else DominantE3.E3_NEGX
    else if(dyabs <= dzabs)
        return if (dy >= 0.0) DominantE3.E3_POSY else DominantE3.E3_NEGY
    else
        return if (dz >= 0.0) DominantE3.E3_POSZ else DominantE3.E3_NEGZ
}

fun least_dominant(v: VectorE3) = least_dominant(v.x, v.y, v.z)