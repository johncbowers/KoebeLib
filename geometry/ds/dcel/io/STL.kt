package geometry.ds.dcel.io

import geometry.ds.dcel.DCEL
import geometry.primitives.Euclidean3.*
import java.io.*
import java.util.Scanner
/**
 * Created by johnbowers on 7/13/17.
 */

internal typealias STLMesh = DCEL<PointE3, Unit, VectorE3>

fun readSTL(path: String) : STLMesh {
    val mesh = STLMesh()
    // TODO Eventually we should handle both binary and ASCII STL. For now we're handling ASCII only
    val input = Scanner(FileInputStream(File(path)))
    if (!input.nextLine().startsWith("solid"))
        throw MalformedSTLException("The file '$path' is not a valid STL file.")

    // TODO

    return mesh
}

fun writeSTL(path: String, mesh: STLMesh) {

}

class MalformedSTLException(msg:String) : Exception(msg)