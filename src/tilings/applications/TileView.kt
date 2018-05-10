package tilings.applications

import geometry.ds.dcel.DCEL
import geometry.primitives.Euclidean2.PointE2
import javafx.scene.shape.Circle
import tilings.algorithms.ChairTile
import javax.swing.*
import java.awt.*
import java.awt.geom.Line2D

import tilings.algorithms.makeChairDCEL
import tilings.algorithms.subDivideChair
import tilings.algorithms.triangulateDCEL
import java.awt.geom.Ellipse2D

class TileView () : JFrame() {
    var graphPanel : GraphPanel
    val buttonPanel : JPanel
    val buttonFlowPanel : JPanel
    val chairButton : JButton
    val twistButton : JButton
    val subdivButton : JButton

    init {
        this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        this.setSize(1000, 700)

        // Build the GUI:
        this.contentPane.layout = BorderLayout()

        graphPanel = GraphPanel(0)
        graphPanel.setSize(400, 400)

        // Build the button bar:

        buttonPanel = JPanel()
        buttonPanel.layout = BorderLayout()

        buttonFlowPanel = JPanel()
        buttonFlowPanel.layout = FlowLayout()

        chairButton = JButton("Chair")
        twistButton = JButton("Pentagonal Twist")
        subdivButton = JButton("Subdivide")

        buttonFlowPanel.add(chairButton)
        buttonFlowPanel.add(twistButton)
        buttonFlowPanel.add(subdivButton)

        buttonPanel.add(buttonFlowPanel, BorderLayout.WEST)
        this.contentPane.add(graphPanel, BorderLayout.CENTER)
        this.contentPane.add(buttonPanel, BorderLayout.EAST)

        chairButton.addActionListener {
            paintChair(graphPanel)
        }

        twistButton.addActionListener {
            paintTwist(graphPanel)
        }

        subdivButton.addActionListener {
            graphPanel.subdivide()
            graphPanel.revalidate()
            graphPanel.repaint()
            revalidate()
        }

    }

    fun paintChair (panel : JPanel) {
        this.contentPane.remove(panel)
        graphPanel = GraphPanel(0)
        this.contentPane.add(graphPanel, BorderLayout.EAST)
        graphPanel.repaint()

    }

    fun paintTwist (panel : JPanel) {
        this.contentPane.remove(panel)
        graphPanel = GraphPanel(1)
        this.contentPane.add(graphPanel, BorderLayout.EAST)
        graphPanel.repaint()

    }
}

class GraphPanel (which : Int) : JPanel() {

    val tileIdx = which
    var points : ArrayList<PointE2>
    var graph : DCEL<PointE2, Unit, Unit>

    init {
        points = ArrayList()
        graph = DCEL()

        this.setSize(600, 600)

        this.isVisible = true

    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        //super.paint(g)
        g?.clearRect(0, 0, this.width, this.height)
        val g2 : Graphics2D = g as Graphics2D

        if (tileIdx == 0) {
            createChair()
        }
        else if (tileIdx == 1) {
            paintTwist(g2)
        }

        paintGraph(g2)
    }

    fun subdivide() {
        println("In subdivide")
        graph = subDivideChair(graph)
        repaint()
        revalidate()
    }

    fun createChair () {
        points = ArrayList<PointE2>()

        points.add(PointE2(x = 100.0, y = 100.0))
        points.add(PointE2(x = 300.0, y = 100.0))
        points.add(PointE2(x = 500.0, y = 100.0))
        points.add(PointE2(x = 500.0, y = 300.0))
        points.add(PointE2(x = 300.0, y = 300.0))
        points.add(PointE2(x = 300.0, y = 500.0))
        points.add(PointE2(x = 100.0, y = 500.0))
        points.add(PointE2(x = 100.0, y = 300.0))

        val chair = ChairTile(points)
        graph = chair.chair
        graph = subDivideChair(graph)
        triangulateDCEL(graph)
        //println("")
        //graph = subDivideChair(graph)
        //graph = makeChairDCEL(points)
    }

    fun paintGraph(g: Graphics2D?) {
        //println("Painting")
        // paint vertices
        g?.paint = Color.BLUE
        for (k in 0..graph.verts.size-1) {
            //println(graph.verts[k].data.x)
            //println(graph.verts[k].data.y)
            g?.draw(Ellipse2D.Double(graph.verts[k].data.x - 5, graph.verts[k].data.y - 5, 10.0, 10.0))
        }

        // paint edges
        //for (k in 0..graph.darts.size-1)
        for (k in 0..graph.darts.size-1) {
            g?.draw(Line2D.Double(graph.darts[k].origin?.data!!.x, graph.darts[k].origin?.data!!.y,
                    graph.darts[k].dest?.data!!.x, graph.darts[k].dest?.data!!.y))
        }

        /*// paint face edge
        g?.paint = Color.RED
        g?.draw(Line2D.Double(graph.faces[0].aDart?.origin?.data!!.x, graph.faces[0].aDart?.origin?.data!!.y,
                graph.faces[0].aDart?.dest?.data!!.x, graph.faces[0].aDart?.dest?.data!!.y))

        // paint midpoint
        val x = (graph.verts[0].data.x + graph.verts[3].data.x) / 2.0
        val y = (graph.verts[0].data.y + graph.verts[3].data.y) / 2.0
        g?.draw(Ellipse2D.Double(x - 5, y - 5, 10.0, 10.0))*/
    }

    fun paintTwist(g: Graphics2D?) {

    }
}

fun main(passedArgs : Array<String>) {
    val gui = TileView()

    gui.isVisible = true
}

