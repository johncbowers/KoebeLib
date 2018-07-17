package tilings.language.algorithms

import geometry.algorithms.CirclePackH
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import tilings.algorithms.DCELTransform
import tilings.algorithms.Triangulation
import tilings.ds.EdgeData
import tilings.ds.FaceData
import tilings.ds.VertexData
import tilings.language.ds.MyEscherListener
import tilings.language.grammar.EscherLexer
import tilings.language.grammar.EscherParser
import java.io.*

fun main(args: Array<String>) {

    //TODO Read input file into a string object
    var file = ""
    val reader = BufferedReader(FileReader(args[0]))
    var line = reader.readLine()
    while (line != null) {
        file = file + line
        line = reader.readLine()
    }

    // Setup ANTLR4 Parser
    val lexer = EscherLexer(ANTLRInputStream(file))
    val tokens = CommonTokenStream(lexer)
    val parser = EscherParser(tokens)

    val fileContext = parser.file()
    val walker = ParseTreeWalker()
    val listener = MyEscherListener()
    walker.walk(listener, fileContext)

    // Run Program
    val graph = listener.program.mainGraph
    val tileFact = TileFactory<VertexData, EdgeData, FaceData>()
    val triangulation = Triangulation<Unit, Unit, String>()
    val dt = DCELTransform<Unit, Unit, String>()
    val cp = CirclePackH()

    val unitGraph = tileFact.copyUnitTile(graph)
    val numVerts = unitGraph.verts.size

    triangulation.triangulateDCEL(unitGraph)

    val combinatorics = dt.toSphericalRepresentation(graph)
    cp.pack(combinatorics)
    val planar = tileFact.copyDiskToPlane(combinatorics)


    // Output Files

    // Writing the Vertices
    var writer = BufferedWriter(FileWriter("verts"))
    for (vertex in planar.verts) {
        writer.write(vertex.data.x.toString() + "," + vertex.data.y.toString() + "\n")
    }
    writer.close()


    // Writing the Edges
    writer = BufferedWriter(FileWriter("edges"))
    for (dart in planar.darts) {
        writer.write(planar.verts.indexOf(dart.origin).toString() + "," + planar.verts.indexOf(dart.dest).toString() + "\n")
    }
    writer.close()

    // Writing the config file
    writer = BufferedWriter(FileWriter("config"))
    writer.write(numVerts.toString())
    writer.close()

}