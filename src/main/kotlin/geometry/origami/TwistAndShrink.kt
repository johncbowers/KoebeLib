package geometry.origami

import geometry.ds.dcel.DCEL
import geometry.primitives.Euclidean2.*;

/**
 * Created by johnbowers on 4/7/18.
 */

/**
 * Takes a crease pattern cp stored as a DCEL with vertex data containing the
 * 2D point for each vertex, and face data storing the center of rotation for the face.
 *
 * Returns the crease pattern formed by applying the twist and shrink method with scaling factor
 * s and rotation angle phi.
 */
fun twistAndShrink(cp: DCEL<PointE2, Unit?, PointE2>, phi: Double, s: Double): DCEL<PointE2, Unit?, Unit?>? {
    return null;
}