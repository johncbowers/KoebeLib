package tilings.language.algorithms

import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import tilings.language.ds.MyEscherListener
import tilings.language.grammar.EscherLexer
import tilings.language.grammar.EscherParser

class LanguageDriver (num : Int) {

    val listener : MyEscherListener

    init {
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
                "tile( chair , " + num + " );"

        /*val file = "TILETYPE pent {5};\n" +
                "SUBDIVISION pent\n" +
                "    {\n" +
                "        VERTEX a, b = split(pent.vertex[0], pent.vertex[1], 3);\n" +
                "        VERTEX c, d = split(pent.vertex[1], pent.vertex[2], 3);\n" +
                "        VERTEX e, f = split(pent.vertex[2], pent.vertex[3], 3);\n" +
                "        VERTEX g, h = split(pent.vertex[3], pent.vertex[4], 3);\n" +
                "        VERTEX i, j = split(pent.vertex[4], pent.vertex[0], 3);\n" +
                "        VERTEX k;\n" +
                "        \n" +
                "        CHILD c1 = pent([pent.vertex[0], a, b, k, j]);\n" +
                "        CHILD c1 = pent([pent.vertex[1], c, d, k, b]);\n" +
                "        CHILD c1 = pent([pent.vertex[2], e, f, k, d]);\n" +
                "        CHILD c1 = pent([pent.vertex[3], g, h, k, f]);\n" +
                "        CHILD c1 = pent([pent.vertex[4], i, j, k, h]);\n" +
                "        \n" +
                "    };\n" +
                "tile( pent , " + num + " );"*/

        /*val file = "TILETYPE rect {4};\n" +
                "TILETYPE tri {3};\n" +
                "SUBDIVISION rect\n" +
                "    {\n" +
                "        VERTEX a = split(rect.vertex[0], rect.vertex[1], 2);\n" +
                "        VERTEX b = split(rect.vertex[1], rect.vertex[2], 2);\n" +
                "        VERTEX c = split(rect.vertex[2], rect.vertex[3], 2);\n" +
                "        VERTEX d = split(rect.vertex[3], rect.vertex[0], 2);\n" +
                "        VERTEX e;\n" +
                "        VERTEX f;\n" +
                "        \n" +
                "        CHILD c1 = rect([rect.vertex[0], a, e, d]);\n" +
                "        CHILD c2 = rect([b, f, a, rect.vertex[1]]);\n" +
                "        CHILD c3 = rect([rect.vertex[2], c, f, b]);\n" +
                "        CHILD c4 = rect([d, e, c, rect.vertex[3]]);\n" +
                "        \n" +
                "        CHILD c5 = tri([e, f, c]);\n" +
                "        CHILD c6 = tri([f, e, a]);\n" +
                "        \n" +
                "        \n" +
                "        \n" +
                "    };\n" +
                "    \n" +
                "SUBDIVISION tri\n" +
                "    {\n" +
                "        VERTEX a = split(tri.vertex[0], tri.vertex[1], 2);\n" +
                "        VERTEX b = split(tri.vertex[1], tri.vertex[2], 2);\n" +
                "        VERTEX c = split(tri.vertex[2], tri.vertex[0], 2);\n" +
                "        \n" +
                "        CHILD c1 = tri([tri.vertex[0], a, c]);\n" +
                "        CHILD c2 = tri([tri.vertex[1], b, a]);\n" +
                "        CHILD c3 = tri([tri.vertex[2], c, b]);\n" +
                "        CHILD c4 = tri([a, b, c]);\n" +
                "        \n" +
                "    };\n" +
                "    \n" +
                "tile( rect , " + num + " );"*/

        val lexer = EscherLexer(ANTLRInputStream(file))
        val tokens = CommonTokenStream(lexer)
        val parser = EscherParser(tokens)

        val fileContext = parser.file()
        val walker = ParseTreeWalker()
        listener = MyEscherListener()
        walker.walk(listener, fileContext)
    }

}