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
                "tile( chair , " + num + " );"

        /*val file = "TILETYPE pent {5};\n" +
                "SUBDIVISION pent\n" +
                "    {\n" +
                "        VERTEX a, b = split(pent.v[0], pent.v[1], 3);\n" +
                "        VERTEX c, d = split(pent.v[1], pent.v[2], 3);\n" +
                "        VERTEX e, f = split(pent.v[2], pent.v[3], 3);\n" +
                "        VERTEX g, h = split(pent.v[3], pent.v[4], 3);\n" +
                "        VERTEX i, j = split(pent.v[4], pent.v[0], 3);\n" +
                "        VERTEX k;\n" +
                "        \n" +
                "        CHILD c1 = pent(pent.v[0], a, b, k, j);\n" +
                "        CHILD c1 = pent(pent.v[1], c, d, k, b);\n" +
                "        CHILD c1 = pent(pent.v[2], e, f, k, d);\n" +
                "        CHILD c1 = pent(pent.v[3], g, h, k, f);\n" +
                "        CHILD c1 = pent(pent.v[4], i, j, k, h);\n" +
                "        \n" +
                "    };\n" +
                "tile( pent , " + num + " );"*/

        /*val file = "TILETYPE rect {4};\n" +
                "TILETYPE tri {3};\n" +
                "SUBDIVISION rect\n" +
                "    {\n" +
                "        VERTEX a = split(rect.v[0], rect.v[1], 2);\n" +
                "        VERTEX b = split(rect.v[1], rect.v[2], 2);\n" +
                "        VERTEX c = split(rect.v[2], rect.v[3], 2);\n" +
                "        VERTEX d = split(rect.v[3], rect.v[0], 2);\n" +
                "        VERTEX e;\n" +
                "        VERTEX f;\n" +
                "        \n" +
                "        CHILD c1 = rect(rect.v[0], a, e, d);\n" +
                "        CHILD c2 = rect(b, f, a, rect.v[1]);\n" +
                "        CHILD c3 = rect(rect.v[2], c, f, b);\n" +
                "        CHILD c4 = rect(d, e, c, rect.v[3]);\n" +
                "        \n" +
                "        CHILD c5 = tri(e, f, c);\n" +
                "        CHILD c6 = tri(f, e, a);\n" +
                "        \n" +
                "        \n" +
                "        \n" +
                "    };\n" +
                "    \n" +
                "SUBDIVISION tri\n" +
                "    {\n" +
                "        VERTEX a = split(tri.v[0], tri.v[1], 2);\n" +
                "        VERTEX b = split(tri.v[1], tri.v[2], 2);\n" +
                "        VERTEX c = split(tri.v[2], tri.v[0], 2);\n" +
                "        \n" +
                "        CHILD c1 = tri(tri.v[0], a, c);\n" +
                "        CHILD c2 = tri(tri.v[1], b, a);\n" +
                "        CHILD c3 = tri(tri.v[2], c, b);\n" +
                "        CHILD c4 = tri(a, b, c);\n" +
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