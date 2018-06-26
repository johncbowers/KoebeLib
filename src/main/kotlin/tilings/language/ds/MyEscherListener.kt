package tilings.language.ds

import org.antlr.runtime.ANTLRInputStream
import org.antlr.runtime.ANTLRStringStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import tilings.algorithms.DCELTransform
import tilings.algorithms.Triangulation
import tilings.language.grammar.EscherBaseListener
import tilings.language.grammar.EscherLexer
import tilings.language.grammar.EscherParser
import java.io.InputStream

class MyEscherListener () : EscherBaseListener() {

    val program : EscherProgramNew
    var currSubdivision : String

    init {
        program = EscherProgramNew()
        currSubdivision = ""
    }

    override fun enterTileDefinition(ctx: EscherParser.TileDefinitionContext?) {
        super.enterTileDefinition(ctx)

        //println (ctx!!.text)
        /*if (!program.defineProtoTile(ctx!!.ID().text, ctx.NUMBER().text.toInt())) {
            println("Error: TileType " + ctx.ID().text + " already exists.")
            return
        }*/

        val vertList = mutableListOf<Int>()
        for (vertices in ctx!!.NUMBER()) {
            vertList.add(vertices.text.toInt())
        }

        program.defineProtoTile(ctx.ID().text, vertList)


        println()
    }

    override fun enterSubdivisionDefinition(ctx: EscherParser.SubdivisionDefinitionContext?) {
        super.enterSubdivisionDefinition(ctx)

        if (!program.protoTiles.containsKey(ctx!!.ID().text)) {
            println("Error: Cannot create subdivision rule. Tiletype " + ctx.ID().text + " does not exist.")
            return
        }

        currSubdivision = ctx.ID().text
        program.defineSubdivision(ctx.ID().text)

        println()
    }

    override fun enterSplitFunction(ctx: EscherParser.SplitFunctionContext?) {
        super.enterSplitFunction(ctx)

        program.addSplit(currSubdivision,ctx!!.node(0).NUMBER().text.toInt(), ctx!!.node(1).NUMBER().text.toInt(),
                ctx!!.NUMBER().text.toInt())

        println()
    }

    override fun enterVertexAssignment(ctx: EscherParser.VertexAssignmentContext?) {
        super.enterVertexAssignment(ctx)

        for (k in 0..ctx!!.ID().size-1) {

            program.addVertex(currSubdivision, ctx!!.ID(k).text)
        }

        println()

    }

    override fun enterTileFunction(ctx: EscherParser.TileFunctionContext?) {
        super.enterTileFunction(ctx)

        if (!program.protoTiles.containsKey(ctx!!.ID().text)) {
            println("Error: Cannot tile. TileType " + ctx!!.ID().text + " does not exist")
            return
        }

        program.subdivide(ctx!!.ID().text, ctx!!.NUMBER().text.toInt())

        println()

    }

    override fun enterChildAssignment(ctx: EscherParser.ChildAssignmentContext?) {
        super.enterChildAssignment(ctx)

        if (!program.protoTiles.containsKey(ctx!!.ID(1).text)) {
            println("Error: Cannot create child. TileType " + ctx!!.ID(1).text + " does not exist")
            return
        }

        val vertices = ArrayList<ArrayList<String>>()
        for (group in ctx.childList()) {
            vertices.add(arrayListOf())
            for (node in group.node()) {
                if (node.NUMBER() == null) {
                    vertices[vertices.lastIndex].add(node.ID().text)
                }
                else {
                    vertices[vertices.lastIndex].add(node.NUMBER().text)
                }
            }
        }


        program.addChild(currSubdivision, ctx!!.ID(1).text, vertices)

        println()
    }

}

fun main(args: Array<String>) {

    val file = "TILETYPE chair{8};\n" +
            "SUBDIVISION chair \n" +
            "\t{\n" +
            "\t VERTEX a = split(chair.vertex[0], chair.vertex[1], 2);\n" +
            "\t VERTEX b = split(chair.vertex[1], chair.vertex[2], 2);\n" +
            "\t VERTEX c = split(chair.vertex[2], chair.vertex[3], 2);\n" +
            "\t VERTEX d = split(chair.vertex[3], chair.vertex[4], 2);\n" +
            "\t VERTEX e = split(chair.vertex[4], chair.vertex[5], 2);\n" +
            "\t VERTEX f = split(chair.vertex[5], chair.vertex[6], 2);\n" +
            "\t VERTEX g = split(chair.vertex[6], chair.vertex[7], 2);\n" +
            "\t VERTEX h = split(chair.vertex[7], chair.vertex[0], 2);\n" +
            "\t VERTEX i;\n" +
            "\t VERTEX j;\n" +
            "\t VERTEX k;\n" +
            "\t VERTEX l;\n" +
            "\t VERTEX m;\n" +
            "\n" +
            "\t CHILD c1 = chair([chair.vertex[0], a, chair.vertex[1], j, i, m, chair.vertex[7], h]);\n" +
            "\t CHILD c2 = chair([chair.vertex[2], c, chair.vertex[3], d, k, j, chair.vertex[1], b]);\n" +
            "\t CHILD c3 = chair([i, j, k, d, chair.vertex[4], e, l, m]);\n" +
            "\t CHILD c4 = chair([chair.vertex[6], g, chair.vertex[7], m, l, e, chair.vertex[5], f]);\n" +
            "\t};\n" +
            "\n" +
            "\n" +
            "\n" +
            "tile( chair , 1 );"

    val lexer = EscherLexer(org.antlr.v4.runtime.ANTLRInputStream(file))
    val tokens = CommonTokenStream(lexer)
    val parser = EscherParser(tokens)

    val fileContext = parser.file()
    val walker = ParseTreeWalker()
    val listener = MyEscherListener()
    walker.walk(listener, fileContext)

    val graph = listener.program.mainGraph
    val triangulation = Triangulation<Unit, Unit, String>()
    val dt = DCELTransform<Unit, Unit, String>()

    //triangulation.triangulateDCEL(graph)

    val combinatorics = dt.toSphericalRepresentation(graph)

    println()

}