package tilings.language.ds

import allMains.CirclePack
import geometry.algorithms.CirclePackH
import org.antlr.runtime.ANTLRInputStream
import org.antlr.runtime.ANTLRStringStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import sun.security.provider.certpath.Vertex
import tilings.algorithms.DCELTransform
import tilings.algorithms.Triangulation
import tilings.ds.EdgeData
import tilings.ds.FaceData
import tilings.ds.VertexData
import tilings.language.algorithms.TileFactory
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

    /*val file = "TILETYPE chair{8};\n" +
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
            "tile( chair , 1 );"*/

    val file = "TILETYPE sponge {4, 4};\n" +
            "SUBDIVISION sponge\n" +
            "    {\n" +
            "\n" +
            "        VERTEX a, b = split(sponge.vertex[0], sponge.vertex[1], 3);\n" +
            "        VERTEX c, d = split(sponge.vertex[1], sponge.vertex[2], 3);\n" +
            "        VERTEX e, f = split(sponge.vertex[2], sponge.vertex[3], 3);\n" +
            "        VERTEX g, h = split(sponge.vertex[3], sponge.vertex[0], 3);\n" +
            "        VERTEX i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;\n" +
            "        VERTEX aa, bb, cc, dd, ee, ff, gg, hh, ii, jj, kk, ll, mm, nn;\n" +
            "\n" +
            "        CHILD c1 = sponge([sponge.vertex[0], a, sponge.vertex[4], h] [i, l, k, j]);\n" +
            "        CHILD c2 = sponge([a, b, sponge.vertex[7], sponge.vertex[4]] [m, p, o, n]);\n" +
            "        CHILD c3 = sponge([b, sponge.vertex[1], c, sponge.vertex[7]] [q, t, s, r]);\n" +
            "        CHILD c4 = sponge([sponge.vertex[7], c, d, sponge.vertex[6]] [u, x, w, v]);\n" +
            "        CHILD c5 = sponge([sponge.vertex[6], d, sponge.vertex[2], e] [y, bb, aa, z]);\n" +
            "        CHILD c6 = sponge([sponge.vertex[5], sponge.vertex[6], e, f] [cc, ff, ee, dd]);\n" +
            "        CHILD c7 = sponge([g, sponge.vertex[5], f, sponge.vertex[3]] [gg, jj, ii, hh]);\n" +
            "        CHILD c8 = sponge([h, sponge.vertex[4], sponge.vertex[5], g] [kk, nn, mm, ll]);\n" +
            "\n" +
            "    };\n" +
            "\n" +
            "tile(sponge, 1);"

    val lexer = EscherLexer(org.antlr.v4.runtime.ANTLRInputStream(file))
    val tokens = CommonTokenStream(lexer)
    val parser = EscherParser(tokens)

    val fileContext = parser.file()
    val walker = ParseTreeWalker()
    val listener = MyEscherListener()
    walker.walk(listener, fileContext)

    val graph = listener.program.mainGraph
    val tileFact = TileFactory<VertexData, EdgeData, FaceData>()
    val triangulation = Triangulation<Unit, Unit, String>()
    val dt = DCELTransform<Unit, Unit, String>()
    val cp = CirclePackH()

    val unitGraph = tileFact.copyUnitTile(graph)

    triangulation.triangulateDCEL(unitGraph)

    val combinatorics = dt.toSphericalRepresentation(graph)

    println()

}