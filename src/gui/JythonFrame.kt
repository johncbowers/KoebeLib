package gui

import javax.swing.*
import java.awt.*
import sketches.SphericalSketch
import java.io.OutputStream

import org.python.core.*
import org.python.util.PythonInterpreter

import java.io.*
import geometry.algorithms.*

import org.fife.ui.rtextarea.*
import org.fife.ui.rsyntaxtextarea.*

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
    val clearButton : JButton

    val splitPane : JSplitPane
    val editorScrollPane : JScrollPane
    val consoleScrollPane : JScrollPane
    val editor : RSyntaxTextArea
    val console : JTextArea

    val pi : PythonInterpreter
    val _ich = IncrementalConvexHullAlgorithms()

    var lastDirectory : String? = null
    var lastFile : String? = null

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
        clearButton = JButton("Clear Console")

        buttonFlowPanel.add(openButton)
        buttonFlowPanel.add(saveButton)
        buttonFlowPanel.add(clearButton)

        buttonPanel.add(buttonFlowPanel, BorderLayout.WEST)
        buttonPanel.add(execButton, BorderLayout.EAST)
        this.contentPane.add(buttonPanel, BorderLayout.SOUTH)

        // Build the editors:

        //editor = JTextArea("#\n#Type your python code\n#\n\n#This adds three points and a disk:\ncons.clear()\n\np1 = cons.makePointS2( 1.0, 0.3, 1.0)\np2 = cons.makePointS2(-0.1,-0.2, 1.0) \np3 = cons.makePointS2( 0.1,-0.2, 1.0) \n\ndisk1 = cons.makeDiskS2(p1, p2, p3)")
        editor = RSyntaxTextArea(20, 60)
        editor.syntaxEditingStyle = SyntaxConstants.SYNTAX_STYLE_PYTHON
        editor.setCodeFoldingEnabled(true)
        editorScrollPane = JScrollPane(editor)

        console = JTextArea("Jython Console Output\n\n")
        console.setEditable(false)
        consoleScrollPane = JScrollPane(console)

        splitPane = JSplitPane(JSplitPane.VERTICAL_SPLIT, editorScrollPane, consoleScrollPane)
        splitPane.dividerLocation = 450
        this.contentPane.add(splitPane, BorderLayout.CENTER)

        pi = PythonInterpreter()
        pi.setOut(PyOutputStream(this))
        pi.setErr(PyOutputStream(this))

        clearButton.addActionListener { _ -> console.text = "" }

        execButton.addActionListener {
            _ -> synchronized(sketch.constructionLock, { pi.exec(editor.text) })
        }

        openButton.addActionListener {
            evt ->
            val fd = FileDialog(this, "Open a python script", FileDialog.LOAD)

            lastDirectory?.let { fd.directory = it }
            fd.file = lastFile?.toString() ?: "*.py"

            fd.setVisible(true)

            if (fd.directory != null && fd.file != null) {

                lastDirectory = fd.directory
                lastFile = fd.file

                val file = File(fd.directory, fd.file)

                try {
                    val inReader = FileReader(file)
                    try {
                        editor.text = inReader.readText()
                    } catch (e: IOException) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Error opening file: ${e.message}",
                                "Error opening file.",
                                JOptionPane.ERROR_MESSAGE
                        )
                    } finally {
                        inReader.close()
                    }
                } catch (e: FileNotFoundException) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error opening file: ${e.message}",
                            "Error opening file.",
                            JOptionPane.ERROR_MESSAGE
                    )
                }
            }
        }

        saveButton.addActionListener {
            evt ->
            val fd = FileDialog(this, "Save python script", FileDialog.SAVE)

            lastDirectory?.let { fd.directory = it }
            fd.file = lastFile?.toString() ?: "*.py"

            fd.setVisible(true)


            if (fd.directory != null && fd.file != null) {

                lastDirectory = fd.directory
                lastFile = fd.file

                val file = File(fd.directory, fd.file)

                try {
                    val outWriter = FileWriter(file)
                    try {
                        outWriter.write(editor.text)
                    } catch (e: IOException) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Error saving file: ${e.message}",
                                "Error saving file.",
                                JOptionPane.ERROR_MESSAGE
                        )
                    } finally {
                        outWriter.close()
                    }
                } catch (e: IOException) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error saving file: ${e.message}",
                            "Error saving file.",
                            JOptionPane.ERROR_MESSAGE
                    )
                }
            }
        }
    }

    fun setup() {
        // Set up the jython interpreter:
        pi.set("view", sketch.viewSettings)
        pi.set("cons", sketch.construction)
        pi.set("objs", sketch.objects)
        pi.set("styles", sketch.objectStyles)
        pi.set("sketch", sketch)
    }
}

class PyOutputStream(val jyFrame: JythonFrame) : OutputStream() {
    internal var buffer : String = ""

    override fun write(b: Int) {
        buffer += b.toChar().toString()
        if (b.toChar() == '\n') flush()
    }

    override fun flush() {
        jyFrame.console.text += buffer
        buffer = ""
    }
}