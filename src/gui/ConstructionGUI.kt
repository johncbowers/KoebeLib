package gui

import javax.swing.*
import java.awt.*
import java.io.OutputStream

import org.python.core.*
import org.python.util.PythonInterpreter

import java.io.*
import geometry.algorithms.*

import org.fife.ui.rtextarea.*
import org.fife.ui.rsyntaxtextarea.*
import sketches.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import javax.swing.ImageIcon
import javax.imageio.ImageIO
import javax.swing.plaf.basic.BasicTreeUI


/**
 * Created by browermb on 2/12/2018.
 */

class ConstructionGUI(val sketch : ConstructionSketch) : JFrame() {
    val buttonPanel : JPanel
    val buttonFlowPanel : JPanel
    val arcballButton : JButton
    val pointButton : JButton
    val circleButton : JButton
    val selectionButton : JButton
    val intersectButton : JButton
    val disksPointCoaxialDisk: JButton
    val threeDiskCPlane : JButton
    val disksPointHyperbolicDisk : JButton
    val planesToDisk: JButton
    val delete : JButton
    val planeButton : JButton

    init {
        // Build the GUI:
        this.contentPane.layout = BorderLayout()

        // Build the button bar:

        buttonPanel = JPanel()
        buttonPanel.layout = BorderLayout()

        buttonFlowPanel = JPanel()
        buttonFlowPanel.layout = FlowLayout()

        arcballButton = JButton()
        pointButton = JButton()
        circleButton = JButton()
        selectionButton = JButton()
        intersectButton = JButton()
        disksPointCoaxialDisk = JButton("CoaxialFamilyDisk")
        threeDiskCPlane = JButton("3DiskCPlane")
        disksPointHyperbolicDisk = JButton("HyperbolicDisk")
        planesToDisk = JButton("DiskFromPlanes")
        delete = JButton("Delete")
        planeButton = JButton("CPlane")

        try {
            val pointImg = ImageIO.read(javaClass.getResource("point.png"))
            pointButton.icon = ImageIcon(pointImg)

            val arcballImg = ImageIO.read(javaClass.getResource("arcball.png"))
            arcballButton.icon = ImageIcon(arcballImg)

            val circleImg = ImageIO.read(javaClass.getResource("circle.png"))
            circleButton.icon = ImageIcon(circleImg)

            val intersectImg = ImageIO.read(javaClass.getResource("intersect.png"))
            intersectButton.icon = ImageIcon(intersectImg)

            val selectImg = ImageIO.read(javaClass.getResource("cursor.png"))
            selectionButton.icon = ImageIcon(selectImg)


        } catch (ex: Exception) {
            println(ex)
        }



        buttonFlowPanel.add(arcballButton)
        buttonFlowPanel.add(pointButton)
        buttonFlowPanel.add(circleButton)
        buttonFlowPanel.add(selectionButton)
        buttonFlowPanel.add(intersectButton)
        buttonFlowPanel.add(disksPointCoaxialDisk)
        buttonFlowPanel.add(disksPointHyperbolicDisk)
        buttonFlowPanel.add(planesToDisk)
        buttonFlowPanel.add(delete)
        buttonFlowPanel.add(planeButton)

        buttonPanel.add(buttonFlowPanel, BorderLayout.WEST)
        this.contentPane.add(buttonPanel, BorderLayout.PAGE_START)

        class MyAdapter :  ComponentAdapter() {
            override fun componentResized(e : ComponentEvent) {
                //TODO: add code here to make the shit redraw correctly
            }
        }

        buttonPanel.addComponentListener(MyAdapter())


        arcballButton.addActionListener {
            sketch.currentTool = ArcballTool(sketch.arcball, sketch)
        }

        pointButton.addActionListener {
            sketch.currentTool = PointEditorTool(sketch)
        }

        circleButton.addActionListener {
            sketch.currentTool = PointEditorTool.CircleTool(sketch)
        }

        selectionButton.addActionListener {
            sketch.currentTool = SelectionTool(sketch)
        }

        intersectButton.addActionListener {
            sketch.currentTool = IntersectionTool(sketch)
        }

        disksPointCoaxialDisk.addActionListener {
            sketch.currentTool = CoaxialPointTool(sketch)
        }

        threeDiskCPlane.addActionListener {
            sketch.currentTool = ThreeDiskCPlaneTool(sketch);
        }

        disksPointHyperbolicDisk.addActionListener {
            sketch.currentTool = HyperbolicPointTool(sketch)
        }

        planesToDisk.addActionListener {
            sketch.currentTool = ThreePlanesDiskTool(sketch)
        }

        delete.addActionListener {
            sketch.currentTool = DeleteTool(sketch)
        }

        planeButton.addActionListener {
            sketch.currentTool = PointEditorTool.PlaneTool(sketch)
        }
    }

}
