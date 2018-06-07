package tilings.language.ds

import org.antlr.runtime.ANTLRInputStream
import org.antlr.runtime.ANTLRStringStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import tilings.language.grammar.EscherBaseListener
import tilings.language.grammar.EscherLexer
import tilings.language.grammar.EscherParser
import java.io.InputStream

class MyEscherListener () : EscherBaseListener() {

    val program : EscherProgram
    var currSubdivision : String

    init {
        program = EscherProgram()
        currSubdivision = ""
    }

    override fun enterTileDefinition(ctx: EscherParser.TileDefinitionContext?) {
        super.enterTileDefinition(ctx)

        //println (ctx!!.text)
        if (!program.makeProtoTile(ctx!!.ID().text, ctx.NUMBER().text.toInt())) {
            println("Error: TileType " + ctx.ID().text + " already exists.")
            return
        }

        println()
    }

    override fun enterSubdivisionDefinition(ctx: EscherParser.SubdivisionDefinitionContext?) {
        super.enterSubdivisionDefinition(ctx)

        if (!program.protoTiles.containsKey(ctx!!.ID().text)) {
            println("Error: Cannot create subdivision rule. Tiletype " + ctx.ID().text + " does not exist.")
            return
        }

        currSubdivision = ctx.ID().text
        program.makeSubdivision(ctx.ID().text)

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
            println("Error: Cannot create child. TileType " + ctx!!.ID(0).text + " does not exist")
            return
        }

        val vertices = ArrayList<String>()
        for (k in 1..ctx!!.ID().size-1) {
            vertices.add(ctx!!.ID(k).text)
        }

        program.addChild(currSubdivision, ctx!!.ID(0).text, vertices)

        println()
    }

}

fun main(args: Array<String>) {

    val file = "TILETYPE chair{8};\n" +
            "SUBDIVISION chair \n" +
            "\t{\n" +
            "\t VERTEX a = split(chair.v[0], chair.v[1], 2);\n" +
            "\t VERTEX b = split(chair.v[1], chair.v[2], 2);\n" +
            "\t VERTEX c = split(chair.v[2], chair.v[3], 2);\n" +
            "\t VERTEX d = split(chair.v[3], chair.v[4], 2);\n" +
            "\t VERTEX e = split(chair.v[4], chair.v[5], 2);\n" +
            "\t VERTEX f = split(chair.v[5], chair.v[6], 2);\n" +
            "\t VERTEX g = split(chair.v[6], chair.v[7], 2);\n" +
            "\t VERTEX h = split(chair.v[7], chair.v[0], 2);\n" +
            "\t VERTEX i;\n" +
            "\t VERTEX j;\n" +
            "\t VERTEX k;\n" +
            "\t VERTEX l;\n" +
            "\t VERTEX m;\n" +
            "\n" +
            "\t CHILD c1 = chair(chair.v[0], a, chair.v[1], j, i, m, chair.v[7], h);\n" +
            "\t CHILD c2 = chair(chair.v[2], c, chair.v[3], d, k, j, chair.v[1], b);\n" +
            "\t CHILD c3 = chair(i, j, k, d, chair.v[4], e, l, m);\n" +
            "\t CHILD c4 = chair(chair.v[6], g, chair.v[7], m, l, e, chair.v[5], f);\n" +
            "\t};\n" +
            "\n" +
            "\n" +
            "\n" +
            "tile( chair , 3 );"

    val lexer = EscherLexer(org.antlr.v4.runtime.ANTLRInputStream(file))
    val tokens = CommonTokenStream(lexer)
    val parser = EscherParser(tokens)

    val fileContext = parser.file()
    val walker = ParseTreeWalker()
    val listener = MyEscherListener()
    walker.walk(listener, fileContext)

    println()
}