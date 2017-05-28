package gui

import javax.swing.*
import java.awt.*
import sketches.SphericalSketch

import org.python.core.*
import org.python.util.PythonInterpreter

/**
 * Created by johnbowers on 5/27/17.
 */


class JythonFrame(val sketch : SphericalSketch) : JFrame() {

    // Button bar across the bottom
    val buttonPanel : JPanel
    val buttonFlowPanel : JPanel
    val openButton : JButton
    val saveButton: JButton
    val execButton : JButton

    val splitPane : JSplitPane
    val editorScrollPane : JScrollPane
    val consoleScrollPane : JScrollPane
    val editor : JTextArea
    val console : JTextArea

    val pi : PythonInterpreter

    init {
        // Build the GUI:

        this.contentPane.layout = BorderLayout()

        // Build the button bar:

        buttonPanel = JPanel()
        buttonPanel.layout = BorderLayout()

        buttonFlowPanel = JPanel()
        buttonFlowPanel.layout = FlowLayout()

        openButton = JButton("Open")
        saveButton = JButton("Save")
        execButton = JButton("Execute")

        buttonFlowPanel.add(openButton)
        buttonFlowPanel.add(saveButton)

        buttonPanel.add(buttonFlowPanel, BorderLayout.WEST)
        buttonPanel.add(execButton, BorderLayout.EAST)
        this.contentPane.add(buttonPanel, BorderLayout.SOUTH)

        // Build the editors:

        editor = JTextArea("#\n#Type your python code\n#\n")
        editorScrollPane = JScrollPane(editor)

        console = JTextArea("Jython Console Output")
        consoleScrollPane = JScrollPane(console)

        splitPane = JSplitPane(JSplitPane.VERTICAL_SPLIT, editorScrollPane, consoleScrollPane)
        this.contentPane.add(splitPane, BorderLayout.CENTER)

        pi = PythonInterpreter()
    }

    fun setup() {
        // Set up the jython interpreter:
        pi.set("points", sketch.points)
        pi.set("disks", sketch.disks)
        pi.set("orthos", sketch.orthos)

        execButton.addActionListener { evt -> pi.exec(editor.text) }
    }
}