package tilings.applications

import geometry.ds.dcel.DCEL
import geometry.primitives.Euclidean2.PointE2
import tilings.ds.Tile
import tilings.ds.ChairTile
import javax.swing.*
import java.awt.*
import java.awt.geom.Line2D

import tilings.algorithms.makeChairDCEL
import tilings.algorithms.subDivideChair
import tilings.algorithms.triangulateDCEL
import tilings.ds.PentagonalTwistTile
import java.awt.geom.Ellipse2D

class TileView () : JFrame() {
    var graphPanel : GraphPanel
    val buttonPanel : JPanel
    val buttonFlowPanel : JPanel
    val chairButton : JButton
    val twistButton : JButton
    val subdivButton : JButton
    val triButton : JButton
    val collarButton : JButton
    val superButton : JButton

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
        buttonFlowPanel.layout = BoxLayout(buttonFlowPanel, BoxLayout.Y_AXIS)
        buttonFlowPanel.add(Box.createRigidArea(Dimension(0, 4)))

        chairButton = JButton("Chair")
        twistButton = JButton("Pentagonal Twist")
        subdivButton = JButton("Subdivide")
        triButton = JButton("Triangulate")
        collarButton = JButton ("Collar")
        superButton = JButton("Super Tile")

        buttonFlowPanel.add(chairButton)
        buttonFlowPanel.add(twistButton)
        buttonFlowPanel.add(subdivButton)
        buttonFlowPanel.add(triButton)
        buttonFlowPanel.add(collarButton)
        buttonFlowPanel.add(superButton)

        buttonPanel.add(buttonFlowPanel, BorderLayout.WEST)
        this.contentPane.add(graphPanel, BorderLayout.CENTER)
        this.contentPane.add(buttonPanel, BorderLayout.EAST)


        // Action listeners for all of the buttons.
        // Each button calls a specific method in the graph panel
        chairButton.addActionListener {
            paintChair(graphPanel)
            contentPane.repaint()
        }

        twistButton.addActionListener {
            paintTwist(graphPanel)
            contentPane.repaint()
        }

        subdivButton.addActionListener {
            graphPanel.subdivide()
            contentPane.repaint()
        }

        triButton.addActionListener {
            graphPanel.triangulate()
            contentPane.repaint()
        }

        collarButton.addActionListener {
            graphPanel.collar()
            contentPane.repaint()
        }

        superButton.addActionListener {
            graphPanel.superTile()
            contentPane.repaint()
        }

    }

    /**
     * Adds a new graph panel to the frame that contains a chair tile
     */
    fun paintChair (panel : JPanel) {
        this.contentPane.remove(panel)
        graphPanel = GraphPanel(0)
        this.contentPane.add(graphPanel, BorderLayout.EAST)
        graphPanel.repaint()

    }

    /**
     * Adds a new graph panel to the frame that contains a pentagonal twist tile
     */
    fun paintTwist (panel : JPanel) {
        this.contentPane.remove(panel)
        graphPanel = GraphPanel(1)
        this.contentPane.add(graphPanel, BorderLayout.EAST)
        graphPanel.repaint()

    }
}

/**
 * Jpanel class that contains the tile instance and performs operations on the tile
 */
class GraphPanel (which : Int) : JPanel() {

    val tileIdx = which     //identifies the tile type
    var collarIdx = -1      //counter for which collar to select
    var superTileIdx = -1   //counter for which supertile to select
    var currFaces = ArrayList<DCEL<PointE2, Unit, Unit>.Face>()
    var points : ArrayList<PointE2>
    var tile : Tile<PointE2, Unit, Unit>

    init {
        points = ArrayList()

        this.setSize(600, 600)

        this.isVisible = true

        // Creating a Chair Tile
        if (tileIdx == 0) {
            points = ArrayList<PointE2>()

            points.add(PointE2(x = 100.0, y = 100.0))
            points.add(PointE2(x = 300.0, y = 100.0))
            points.add(PointE2(x = 500.0, y = 100.0))
            points.add(PointE2(x = 500.0, y = 300.0))
            points.add(PointE2(x = 300.0, y = 300.0))
            points.add(PointE2(x = 300.0, y = 500.0))
            points.add(PointE2(x = 100.0, y = 500.0))
            points.add(PointE2(x = 100.0, y = 300.0))

            this.tile = ChairTile(points)
            //createChair()
        }
        else {
            points = ArrayList<PointE2>()

            points.add(PointE2(x = 200.0, y = 100.0))
            points.add(PointE2(x = 400.0, y = 100.0))
            points.add(PointE2(x = 500.0, y = 400.0))
            points.add(PointE2(x = 300.0, y = 550.0))
            points.add(PointE2(x = 100.0, y = 400.0))

            this.tile = PentagonalTwistTile(points)
            //createChair()
        }

    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        //super.paint(g)
        g?.clearRect(0, 0, this.width, this.height)
        val g2 : Graphics2D = g as Graphics2D

        paintGraph(g2)
    }

    /**
     * Subdivides the tile and resets the collar and supertile idxs
     */
    fun subdivide () {
        println("In subdivide")
        tile.subdivide()
        collarIdx = -1
        superTileIdx = -1
        println("")
    }

    /**
     * Loops through each tile selecting the super tile and storing it in currFaces
     */
    fun superTile () {
        superTileIdx = (superTileIdx + 1) % tile.graph.faces.size
        //currFaces = (tile as ChairTile).superTile(tile.graph.faces[superTileIdx], 3)
        currFaces = tile.superTile(tile.graph.faces[superTileIdx])

        collarIdx = -1
    }

    /**
     * Triangulates all of the faces of the tile
     */
    fun triangulate () {
        println("In triangulate")
        triangulateDCEL(tile.graph)
        collarIdx = -1
        superTileIdx = -1
    }

    /**
     * Lopps through each tile selecting the collar and storing it in currFaces
     */
    fun collar () {
        superTileIdx = -1
        collarIdx = (collarIdx + 1) % tile.graph.faces.size
        currFaces = tile.collar(tile.graph.faces[collarIdx])
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

        this.tile = ChairTile(points)

    }

    /**
     * Paints the panel and the current graph of the tile
     */
    fun paintGraph(g: Graphics2D?) {

        // paint vertices
        /*g?.paint = Color.BLUE
        for (k in 0..tile.graph.verts.size-1) {
            g?.draw(Ellipse2D.Double(tile.graph.verts[k].data.x - 5,
                    tile.graph.verts[k].data.y - 5, 10.0, 10.0))
        }*/

        // paint edges
        //for (k in 0..graph.darts.size-1)
        for (k in 0..tile.graph.darts.size-1) {
            g?.draw(Line2D.Double(tile.graph.darts[k].origin?.data!!.x, tile.graph.darts[k].origin?.data!!.y,
                    tile.graph.darts[k].dest?.data!!.x, tile.graph.darts[k].dest?.data!!.y))
        }


        // Paint Edges Of Collar Red and Edges of current face green
        if (collarIdx != -1)
        {

            g?.paint = Color.RED
            for (k in 0..currFaces.size-1) {
                for (j in 0..currFaces[k].darts().size-1) {
                    g?.draw(Line2D.Double(currFaces[k].darts()[j].origin?.data!!.x, currFaces[k].darts()[j].origin?.data!!.y,
                            currFaces[k].darts()[j].dest?.data!!.x, currFaces[k].darts()[j].dest?.data!!.y))
                }
            }
            g?.paint = Color.GREEN
            for (k in 0..tile.graph.faces[collarIdx].darts().size-1) {
                g?.draw(Line2D.Double(tile.graph.faces[collarIdx].darts()[k].origin?.data!!.x, tile.graph.faces[collarIdx].darts()[k].origin?.data!!.y,
                        tile.graph.faces[collarIdx].darts()[k].dest?.data!!.x, tile.graph.faces[collarIdx].darts()[k].dest?.data!!.y))
            }
        }

        // Paints supertile edges red and edges of current face green
        if (superTileIdx != -1)
        {

            g?.paint = Color.RED
            for (k in 0..currFaces.size-1) {
                for (j in 0..currFaces[k].darts().size-1) {
                    g?.draw(Line2D.Double(currFaces[k].darts()[j].origin?.data!!.x, currFaces[k].darts()[j].origin?.data!!.y,
                            currFaces[k].darts()[j].dest?.data!!.x, currFaces[k].darts()[j].dest?.data!!.y))
                }
            }
            g?.paint = Color.GREEN
            for (k in 0..tile.graph.faces[superTileIdx].darts().size-1) {
                g?.draw(Line2D.Double(tile.graph.faces[superTileIdx].darts()[k].origin?.data!!.x, tile.graph.faces[superTileIdx].darts()[k].origin?.data!!.y,
                        tile.graph.faces[superTileIdx].darts()[k].dest?.data!!.x, tile.graph.faces[superTileIdx].darts()[k].dest?.data!!.y))
            }
        }

        // paint face edge
        /*g?.paint = Color.RED
        g?.draw(Line2D.Double(tile.graph.faces[0].aDart?.origin?.data!!.x, tile.graph.faces[0].aDart?.origin?.data!!.y,
                tile.graph.faces[0].aDart?.dest?.data!!.x, tile.graph.faces[0].aDart?.dest?.data!!.y))*/

    }

    fun paintTwist(g: Graphics2D?) {

    }
}

fun main(passedArgs : Array<String>) {
    val gui = TileView()

    gui.isVisible = true
}

