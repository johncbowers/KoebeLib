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

    init {
        // Build the GUI:
        this.contentPane.layout = BorderLayout()

        // Build the button bar:

        buttonPanel = JPanel()
        buttonPanel.layout = BorderLayout()

        buttonFlowPanel = JPanel()
        buttonFlowPanel.layout = FlowLayout()

        arcballButton = JButton("arcball")
        pointButton = JButton("point")
        circleButton = JButton("circle")
        selectionButton = JButton("Select")

        buttonFlowPanel.add(arcballButton)
        buttonFlowPanel.add(pointButton)
        buttonFlowPanel.add(circleButton)
        buttonFlowPanel.add(selectionButton)

        buttonPanel.add(buttonFlowPanel, BorderLayout.WEST)
        this.contentPane.add(buttonPanel, BorderLayout.PAGE_START)

        arcballButton.addActionListener {
            sketch.currentTool = ArcballTool(sketch.arcball)
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
    }

}
