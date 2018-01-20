package geometry.IO
import dcel.Face
import dcel.Vertex
import geometry.algorithms.ConvexHull
import geometry.ds.dcel.DCEL
import geometry.primitives.Spherical2.DiskS2
import java.io.File
import java.io.PrintWriter
import java.util.*
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus

/**
 * Created by browermb on 6/23/2017.
 */
class ConvexHullIO {
    /**
     * File to save a convex hull into a file
     */
    fun saveConvexHull(convHull : ConvexHull<DiskS2>, filePath : String) {
        val vToIndex = mutableMapOf<DCEL<DiskS2, Unit, Unit>.Vertex, Int>()
        val dToIndex = mutableMapOf<DCEL<DiskS2, Unit, Unit>.Dart, Int>()
        val eToIndex = mutableMapOf<DCEL<DiskS2, Unit, Unit>.Edge, Int>()
        val fToIndex = mutableMapOf<DCEL<DiskS2, Unit, Unit>.Face, Int>()
        var vertexCount = 0;
        var dartCount= 0;
        var edgeCount = 0;
        var faceCount = 0;
        val writer = PrintWriter(filePath)
        for (v in convHull.verts) {
            vToIndex.put(v, vertexCount)
            vertexCount++
        }
        for (d in convHull.darts) {
            dToIndex.put(d, dartCount)
            dartCount++
        }
        for (e in convHull.edges) {
            eToIndex.put(e, edgeCount)
            edgeCount++
        }
        for (f in convHull.faces) {
            fToIndex.put(f, faceCount)
            faceCount++
        }
        writer.println("Vertices:: " + vertexCount)
        for (v in convHull.verts) {
            writer.print(vToIndex.get(v))
            writer.print("\t" + v.data.a)
            writer.print("\t" + v.data.b)
            writer.print("\t" + v.data.c)
            writer.print("\t" + v.data.d)
            val vADart = v.aDart
            if (vADart != null) {
                writer.println("\t" + dToIndex.get(vADart))
            } else {
                writer.println("\t" + -1)
            }
        }
        writer.println("Faces:: " + faceCount)
        for (f in convHull.faces) {
            writer.print(fToIndex.get(f))
            val fADart = f.aDart
            if (fADart!= null) {
                writer.println("\t" + dToIndex.get(fADart))
            } else {
                writer.println("\t" + -1)
            }
        }
        writer.println("Edges:: " + edgeCount)
        for (e in convHull.edges) {
            writer.print(eToIndex.get(e))
            val eADart = e.aDart
            if (eADart != null) {
                writer.println("\t" + dToIndex.get(eADart))
            } else {
                writer.println("\t" + -1)
            }
        }
        writer.println("Darts:: " + dartCount)
        for (d in convHull.darts) {
            writer.print(dToIndex.get(d))
            if (d != null) {
                writer.print("\t" + vToIndex.get(d.origin))
                writer.print("\t" + eToIndex.get(d.edge))
                writer.print("\t" + fToIndex.get(d.face))
                writer.print("\t" + dToIndex.get(d.prev))
                writer.print("\t" + dToIndex.get(d.next))
                writer.println("\t" + dToIndex.get(d.twin))
            } else {
                writer.print("\t" + -1)
                writer.print("\t" + -1)
                writer.print("\t" + -1)
                writer.print("\t" + -1)
                writer.print("\t" + -1)
                writer.println("\t" + -1)
            }
        }
        writer.close()
    }

    /**
     * Function to load a convex hull given a file
     */
    fun loadConvexHull(path: String) : ConvexHull<DiskS2> {
        val file = File(path)
        var convHull = DCEL<DiskS2, Unit, Unit>();
        val reader = Scanner(file) //to get counts
        val reader2 = Scanner(file) //to get data
        val reader3 = Scanner(file) //to get darts of vertices
        reader.next()
        var vertexCount = reader.next().toInt()
        var vertices = mutableMapOf<Int, DCEL<DiskS2, Unit, Unit>.Vertex>()
        for (i in 0..vertexCount) {
            reader.nextLine()
        }
        reader.next()
        var faceCount = reader.next().toInt()
        var faces = mutableMapOf<Int, DCEL<DiskS2, Unit, Unit>.Face>()
        for (i in 0..faceCount) {
            reader.nextLine()
        }
        reader.next()
        var edgeCount = reader.next().toInt()
        var edges = mutableMapOf<Int, DCEL<DiskS2, Unit, Unit>.Edge>()
        for (i in 0..edgeCount) {
            reader.nextLine()
        }
        reader.next()
        var dartCount = reader.next().toInt()
        var darts = mutableMapOf<Int, DCEL<DiskS2, Unit, Unit>.Dart>()
        reader2.next()
        reader2.next()
        for (i in 0..vertexCount - 1) {
            reader2.next()
            var a = reader2.next().toDouble()
            var b = reader2.next().toDouble()
            var c = reader2.next().toDouble()
            var d = reader2.next().toDouble()
            var disk = convHull.Vertex(data = DiskS2(a,b,c,d))
            vertices.put(i, disk)
            reader2.next()
            System.out.println()
        }
        reader2.next()
        reader2.next()
        for (i in 0..faceCount - 1) {
            reader2.next()
            reader2.next()
            var face = convHull.Face(data = Unit)
            faces.put(i, face)
        }

        reader2.next()
        reader2.next()
        for (i in 0..edgeCount - 1) {
            reader2.next()
            reader2.next()
            var edge = convHull.Edge(data = Unit)
            edges.put(i, edge)
        }
        reader2.next()
        reader2.next()
        for (i in 0..dartCount - 1) {
            reader2.next()
            var dart = convHull.Dart()
            var vertexIndex = reader2.next().toInt()
            var edgeIndex = reader2.next().toInt()
            var faceIndex = reader2.next().toInt()
            dart.origin = vertices.get(vertexIndex)
            dart.face = faces.get(faceIndex)
            dart.edge = edges.get(edgeIndex)
            darts.put(i, dart)
            reader2.next()
            reader2.next()
            reader2.next()
        }
        reader3.next()
        reader3.next()
        for (i in 0..vertexCount - 1) {
            reader3.next()
            reader3.next()
            reader3.next()
            reader3.next()
            reader3.next()
            var dartIndex = reader3.next().toInt()
            var vertex = vertices.get(i)
            vertex?.aDart = darts.get(dartIndex)
        }
        reader3.next()
        reader3.next()
        for (i in 0..faceCount - 1) {
            reader3.next()
            var dartIndex = reader3.next().toInt()
            var face = faces.get(i)
            face?.aDart = darts.get(dartIndex)
        }
        reader3.next()
        reader3.next()
        for (i in 0..edgeCount - 1) {
            reader3.next()
            var dartIndex = reader3.next().toInt()
            var edge = edges.get(i)
            edge?.aDart = darts.get(i)
        }
        reader3.next()
        reader3.next()
        for (i in 0..dartCount - 1) {
            reader3.next()
            reader3.next()
            reader3.next()
            reader3.next()
            var prevIndex = reader3.next().toInt()
            var nextIndex = reader3.next().toInt()
            var twinIndex = reader3.next().toInt()
            var dart = darts.get(i)
            dart?.prev = darts.get(prevIndex)
            dart?.next = darts.get(nextIndex)
            dart?.twin = darts.get(twinIndex)
        }
        return convHull;
    }
}