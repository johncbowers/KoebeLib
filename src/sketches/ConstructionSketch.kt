package sketches

import com.processinghacks.arcball.Arcball
import geometry.construction.Construction
import geometry.construction.BaseNode
import geometry.construction.INode
import geometry.ds.dcel.DCEL
import geometry.primitives.Euclidean2.DiskE2
import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.Euclidean2.VectorE2
import geometry.primitives.Euclidean3.DirectionE3
import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.Euclidean3.VectorE3
import geometry.primitives.OrientedProjective2.CircleArcOP2
import geometry.primitives.OrientedProjective2.DiskOP2
import geometry.primitives.OrientedProjective2.LineOP2
import geometry.primitives.OrientedProjective2.PointOP2
import geometry.primitives.OrientedProjective3.PointOP3
import geometry.primitives.Spherical2.*
import geometry.primitives.inner_product
import gui.JythonFrame
import gui.ConstructionGUI
import processing.core.PApplet
import processing.event.MouseEvent

import processing.core.PConstants
import processing.core.PGraphics.R
import processing.core.PMatrix3D
import processing.opengl.PGraphicsOpenGL
import java.awt.Button
import java.awt.Point
import java.io.PrintWriter

import javax.swing.*

/**
 * Created by browermb on 7/27/2017.
 */


open class MouseTool {
    var mouseDown = false
    var startX = -1
    var startY = -1
    var dragged = false

    open fun mousePressed(mouseX: Int, mouseY: Int) {
        mouseDown = true
        dragged = false
        startX = mouseX
        startY = mouseY
    }

    open fun mouseReleased(mouseX: Int, mouseY: Int) {
        mouseDown = false
        if (!dragged) {
            mouseClicked(mouseX, mouseY)
        }
        dragged = false
    }

    open fun mouseDragged(mouseX: Int, mouseY: Int) {

    }

    open fun mouseClicked(mouseX: Int, mouseY: Int) {
    }

    open fun mouseMoved(mouseX: Int, mouseY: Int) {
        if (mouseDown) {
            val dx = mouseX - startX
            val dy = mouseY - startY

            if ((dx * dx) + (dy * dy) >= 4) {
                dragged = true
            }

            if (dragged) {
                this.mouseDragged(mouseX, mouseY)
            }
        }
    }
}

class ArcballTool(val arcball: Arcball?) : MouseTool() {

    override fun mousePressed(mouseX: Int, mouseY: Int) {
        super.mousePressed(mouseX, mouseY)
        arcball?.mousePressed()
    }
    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)
        arcball?.mousePressed()
    }
    override fun mouseDragged(mouseX: Int, mouseY: Int) {
        arcball?.mouseDragged()
    }
    override fun mouseClicked(mouseX: Int, mouseY: Int) {

    }
    override fun mouseMoved(mouseX: Int, mouseY: Int) {
        super.mouseMoved(mouseX, mouseY)
    }
}

class SelectionTool(val sketch : ConstructionSketch) : MouseTool() {
    fun transform(mouseX: Int, mouseY: Int) : PointE3{
        val gl = sketch.g
        if (gl is PGraphicsOpenGL) {
            var Minv = PMatrix3D(gl.projmodelview)
            Minv.invert() //PVM inverted
            var mousex = (2.0f * mouseX) / sketch.width - 1.0f
            var mousey = 1.0f - (2.0f * mouseY) / sketch.height

            var w1 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m33
            var w2 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m32 + Minv.m33

            var new1 = PointE3(
                    ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m03) / w1).toDouble(),
                    ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m13) / w1).toDouble(),
                    ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m23) / w1).toDouble()
            )

            var new2 = PointE3(
                    ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m02 + Minv.m03) / w2).toDouble(),
                    ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m12 + Minv.m13) / w2).toDouble(),
                    ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m22 + Minv.m23) / w2).toDouble()
            )

            var direction = (new2 - new1).normalize()

            var l = PointE3.O - new1

            var tca = l.dot(direction)
            var d = (Math.sqrt(l.normSq() - tca * tca))//(200.0*zoom)
            var thc = Math.sqrt(1 - d * d)
            var t0 = tca - thc
            var t1 = tca + thc
            var inter = PointE3(0.0, 0.0, 0.0)
            if (t0 > t1) {
                inter = new1 + direction * t1
                return inter
            } else {
                inter = new1 + direction * t0
                return inter
            }
        }
        return PointE3()
    }

    fun isIntersection(mouseX : Int, mouseY : Int, a : Double, b : Double, c : Double, d : Double) : Boolean {
        val gl = sketch.g
        if (gl is PGraphicsOpenGL) {
            var Minv = PMatrix3D(gl.projmodelview)
            Minv.invert() //PVM inverted
            var mousex = (2.0f * mouseX) / sketch.width - 1.0f
            var mousey = 1.0f - (2.0f * mouseY) / sketch.height

            var w1 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m33
            var w2 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m32 + Minv.m33

            var new1 = PointE3(
                    ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m03) / w1).toDouble(),
                    ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m13) / w1).toDouble(),
                    ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m23) / w1).toDouble()
            )

            var new2 = PointE3(
                    ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m02 + Minv.m03) / w2).toDouble(),
                    ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m12 + Minv.m13) / w2).toDouble(),
                    ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m22 + Minv.m23) / w2).toDouble()
            )

            var direction = (new2 - new1).normalize()
            var rayOrigin = VectorE3(new1.x, new1.y, new1.z)
            var worldOrigin = VectorE3(0.0, 0.0, 0.0)
            var normalVector = VectorE3(a, b, c)
            var denom = direction.dot(normalVector)
            var t = ((worldOrigin - rayOrigin).dot(normalVector)) / denom
            return t >= 0
        }

        return false;
    }

    //TODO: need to get minimum distance and then set node also add a feature to select multiple things
    override fun mouseClicked(mouseX: Int, mouseY: Int) {
        var cursor = transform(mouseX, mouseY)
        //var selectedNode: Any? = null
        if(cursor!= null) {
            for(node in sketch.construction.nodes) {
                var output = node.getOutput()
                if(output is PointS2) {
                    if (Math.abs(cursor.x.toFloat() - output.x) <= .1
                            && Math.abs(cursor.y.toFloat() - output.y) <= .1
                            && Math.abs(cursor.z.toFloat() - output.z) <= .1) {
                        //TODO: change the cursor so the point is visible when being dragged
                        node.style = Style(Color.noColor, Color(128.0.toFloat(), 0.0.toFloat(), 128.0.toFloat()))
                        break
                    }
                }
                if (output is DiskS2) {
                    var a = output.a
                    var b = output.b
                    var c = output.c
                    var d = output.d
                    if(isIntersection(mouseX, mouseY, a, b, c, d)) {
                        node.style = Style(Color(255.0.toFloat(), 255.0.toFloat(), 0.0.toFloat()), Color.noColor)
                        break
                    }
                }
            }
        }
        //(selectedNode as INode<*>).style = Style()
    }

    override fun mouseMoved(mouseX: Int, mouseY: Int) {
        super.mouseMoved(mouseX, mouseY)
    }

    override fun mouseDragged(mouseX: Int, mouseY: Int) {

    }

    override fun mousePressed(mouseX: Int, mouseY: Int) {
        super.mousePressed(mouseX, mouseY)
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)
    }
}

open class PointEditorTool(val sketch: ConstructionSketch) : MouseTool() {

    var selectedNode : INode<*>? = null;
    var selectedNodes = mutableListOf<INode<*>>()

    fun transform(mouseX: Int, mouseY: Int): PointE3 {
        val gl = sketch.g
                if (gl is PGraphicsOpenGL) {
                    var Minv = PMatrix3D(gl.projmodelview)
                    Minv.invert() //PVM inverted
                    var mousex = (2.0f * mouseX) / sketch.width - 1.0f
                    var mousey = 1.0f - (2.0f * mouseY) / sketch.height

                    var w1 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m33
                    var w2 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m32 + Minv.m33

                    var new1 = PointE3(
                            ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m03) / w1).toDouble(),
                            ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m13) / w1).toDouble(),
                            ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m23) / w1).toDouble()
                    )

                    var new2 = PointE3(
                            ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m02 + Minv.m03) / w2).toDouble(),
                            ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m12 + Minv.m13) / w2).toDouble(),
                            ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m22 + Minv.m23) / w2).toDouble()
                    )

                    var direction = (new2 - new1).normalize()

                    var l = PointE3.O - new1

            var tca = l.dot(direction)
            var d = (Math.sqrt(l.normSq() - tca * tca))//(200.0*zoom)
            var thc = Math.sqrt(1 - d * d)
            var t0 = tca - thc
            var t1 = tca + thc
            var inter = PointE3(0.0, 0.0, 0.0)
            if (t0 > t1) {
                inter = new1 + direction * t1
                return inter
            } else {
                inter = new1 + direction * t0
                return inter
            }
        }
        return PointE3()
    }

    override fun mouseMoved(mouseX: Int, mouseY: Int) {
        super.mouseMoved(mouseX, mouseY)
    }

    override fun mousePressed(mouseX: Int, mouseY: Int) {
        super.mousePressed(mouseX, mouseY)
        var cursor = transform(mouseX, mouseY)
        if (cursor != null) {

            for (node in sketch.construction.nodes) {
                if (node != null) {
                    var output = node.getOutput()
                    if (output is PointS2) {
                        //should change this so its the closet found point
                        if (Math.abs(cursor.x.toFloat() - output.x) <= .1
                                && Math.abs(cursor.y.toFloat() - output.y) <= .1
                                && Math.abs(cursor.z.toFloat() - output.z) <= .1) {
                            //TODO: change the cursor so the point is visible when being dragged
                            selectedNode = node
                            break
                        }
                    }

                }
            }
        }
    }


    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)
    }

    override fun mouseDragged(mouseX: Int, mouseY: Int) {
        var cursor = transform(mouseX, mouseY)
        if (cursor != null) {
            var node = selectedNode
            if (node is BaseNode<*>) {
                node.resetBaseNodeWith(PointS2(cursor.x, cursor.y, cursor.z))
            }
        }
    }
    override fun mouseClicked(mouseX: Int, mouseY: Int) {
        var drawNode = true
        var cursor = transform(mouseX, mouseY)
        if (cursor != null) {
            for (node in sketch.construction.nodes) {
                if(node != null) {
                    var output = node.getOutput()
                    if (output is PointS2) {
                        if (Math.abs(cursor.x.toFloat() - output.x) <= .1
                                && Math.abs(cursor.y.toFloat() - output.y) <= .1
                                && Math.abs(cursor.z.toFloat() - output.z) <= .1) {
                            drawNode = false
                            break
                        }
                    }
                }
            }
            if (drawNode) {
                val node = sketch.construction.makePointS2(cursor.x, cursor.y, cursor.z)
            }
        }

    }

    class CircleTool(sketch: ConstructionSketch) : PointEditorTool(sketch) {

        override fun mousePressed(mouseX: Int, mouseY: Int) {
            super.mousePressed(mouseX, mouseY)
            var cursor = transform(mouseX, mouseY)
            if (cursor != null) {

                for (node in sketch.construction.nodes) {
                    if (node != null) {
                        var output = node.getOutput()
                        if (output is PointS2) {
                            //TODO: should change this so its the closet found point
                            if (Math.abs(cursor.x.toFloat() - output.x) <= .1
                                    && Math.abs(cursor.y.toFloat() - output.y) <= .1
                                    && Math.abs(cursor.z.toFloat() - output.z) <= .1) {

                                selectedNode = node
                                node.style = Style(Color.noColor, Color(255.0.toFloat(), 0.0.toFloat(), 0.0.toFloat()))
                                break
                            }
                        }

                    }
                }
            }
            
            selectedNodes.add(selectedNode as INode<*>)
        }

        override fun mouseMoved(mouseX: Int, mouseY: Int) {
            super.mouseMoved(mouseX, mouseY)
        }

        override fun mouseClicked(mouseX: Int, mouseY: Int) {

        }

        override fun mouseReleased(mouseX: Int, mouseY: Int) {
            super.mouseReleased(mouseX, mouseY)
                if (selectedNodes.size >= 3) {
                    var obj1 = selectedNodes[0]
                    var obj2 = selectedNodes[1]
                    var obj3 = selectedNodes[2]

                    @Suppress("UNCHECKED_CAST")
                    var node = sketch.construction.makeDiskS2( obj1 as INode<PointS2>,
                            obj2 as INode<PointS2>,
                            obj3 as INode<PointS2>)

                }
            }

        }
    }

open class CoaxialPointTool(val sketch: ConstructionSketch) : MouseTool() {

    var selectedNode : INode<*>? = null;
    var selectedNodes = mutableListOf<INode<*>>()

    fun transform(mouseX: Int, mouseY: Int): PointE3 {
        val gl = sketch.g
        if (gl is PGraphicsOpenGL) {
            var Minv = PMatrix3D(gl.projmodelview)
            Minv.invert() //PVM inverted
            var mousex = (2.0f * mouseX) / sketch.width - 1.0f
            var mousey = 1.0f - (2.0f * mouseY) / sketch.height

            var w1 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m33
            var w2 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m32 + Minv.m33

            var new1 = PointE3(
                    ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m03) / w1).toDouble(),
                    ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m13) / w1).toDouble(),
                    ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m23) / w1).toDouble()
            )

            var new2 = PointE3(
                    ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m02 + Minv.m03) / w2).toDouble(),
                    ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m12 + Minv.m13) / w2).toDouble(),
                    ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m22 + Minv.m23) / w2).toDouble()
            )

            var direction = (new2 - new1).normalize()

            var l = PointE3.O - new1

            var tca = l.dot(direction)
            var d = (Math.sqrt(l.normSq() - tca * tca))//(200.0*zoom)
            var thc = Math.sqrt(1 - d * d)
            var t0 = tca - thc
            var t1 = tca + thc
            var inter = PointE3(0.0, 0.0, 0.0)
            if (t0 > t1) {
                inter = new1 + direction * t1
                return inter
            } else {
                inter = new1 + direction * t0
                return inter
            }
        }
        return PointE3()
    }

    fun isIntersection(mouseX : Int, mouseY : Int, a : Double, b : Double, c : Double, d : Double) : Boolean {
        val gl = sketch.g
        if (gl is PGraphicsOpenGL) {
            var Minv = PMatrix3D(gl.projmodelview)
            Minv.invert() //PVM inverted
            var mousex = (2.0f * mouseX) / sketch.width - 1.0f
            var mousey = 1.0f - (2.0f * mouseY) / sketch.height

            var w1 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m33
            var w2 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m32 + Minv.m33

            var new1 = PointE3(
                    ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m03) / w1).toDouble(),
                    ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m13) / w1).toDouble(),
                    ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m23) / w1).toDouble()
            )

            var new2 = PointE3(
                    ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m02 + Minv.m03) / w2).toDouble(),
                    ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m12 + Minv.m13) / w2).toDouble(),
                    ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m22 + Minv.m23) / w2).toDouble()
            )

            var direction = (new2 - new1).normalize()
            var rayOrigin = VectorE3(new1.x, new1.y, new1.z)
            var worldOrigin = VectorE3(0.0, 0.0, 0.0)
            var normalVector = VectorE3(a, b, c)
            var denom = direction.dot(normalVector)
            var t = ((worldOrigin - rayOrigin).dot(normalVector)) / denom
            return t >= 0
        }

        return false;
    }

    override fun mouseMoved(mouseX: Int, mouseY: Int) {
        super.mouseMoved(mouseX, mouseY)
    }

    override fun mouseDragged(mouseX: Int, mouseY: Int) {

    }

    override fun mousePressed(mouseX: Int, mouseY: Int) {
        super.mousePressed(mouseX, mouseY)
        var cursor = transform(mouseX, mouseY)
        if (cursor != null) {

            for (node in sketch.construction.nodes) {
                if (node != null) {
                    var output = node.getOutput()
                    if (output is PointS2 && !selectedNodes.isEmpty()) {
                        //TODO: should change this so its the closet found point
                        if (Math.abs(cursor.x.toFloat() - output.x) <= .1
                                && Math.abs(cursor.y.toFloat() - output.y) <= .1
                                && Math.abs(cursor.z.toFloat() - output.z) <= .1) {

                            selectedNode = node
                            node.style = Style(Color(255.0.toFloat(), 0.0f, 0.0f), Color(255.0.toFloat(), 0.0.toFloat(), 0.0.toFloat()))
                            break
                        }
                    }
                    if (output is DiskS2) {
                        var a = output.a
                        var b = output.b
                        var c = output.c
                        var d = output.d
                        if(isIntersection(mouseX, mouseY, a, b, c, d)) {selectedNode = node
                            selectedNode = node
                            node.style = Style(Color(200.0.toFloat(), 0.0f, 0.0f), Color(255.0.toFloat(), 0.0.toFloat(), 0.0.toFloat()))
                        }
                    }

                }
            }
        }

        selectedNodes.add(selectedNode as INode<*>)
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)
        if (selectedNodes.size >= 3) {
            var obj1 = selectedNodes[0]
            var obj2 = selectedNodes[1]
            var obj3 = selectedNodes[2]

            @Suppress("UNCHECKED_CAST")
            var node = sketch.construction.makeDiskS2( obj1 as INode<PointS2>,
                    obj2 as INode<PointS2>,
                    obj3 as INode<PointS2>)

        }
    }
}

open class ConstructionSketch : SphericalSketch() {

    var currentTool : MouseTool? = null;

    class ConstructionSketchViewSettings() {
        var showBoundingBox = true
        var showCircleCentersAndNormals = true
        var showSphere = true
        var showDualPoint = true
        var showEuclideanDisks = false
    }

    internal val constructFrame = ConstructionGUI(this);
    /**
     * Sets up the sketches.main graphics settings for the PApplet window
     */
    override fun settings() {
        size(600, 600, P3D)
        smooth(4)
    }

    /**
     * Sets up the drawing canvas.
     */
    override fun setup() {
        super.setup()
        constructFrame.setSize(200, 100)
        constructFrame.setVisible(true)
        constructFrame.pack()


    }

    //for mouseClick()
    var clicked = false;
    var sphereCursor : PointS2? = null;

    /**
     * The sketches.main drawing code, called in an infinite loop to continuously redraw the screen.
     */
    override fun draw() {
        update()

        background(255)

        beginCamera()
        camera()
        if (applyToCamera && arcball != null) {
            translate((width / 2).toFloat(), (height / 2).toFloat())
            val axis = (arcball as Arcball).axis
            rotate((arcball as Arcball).angle, axis.x, axis.y, -axis.z)
            translate((-width / 2).toFloat(), (-height / 2).toFloat())
        }
        endCamera()

        translate(width / 2.0f, height / 2.0f, 0.0f)
        scale(1.0f, -1.0f, 1.0f);
        scale(200.0f * zoom)
        strokeWeight(0.005f / zoom)

        pushStyle()

        noStroke()
        lights()

        if (viewSettings.showSphere) sphere(1.0f)

        // Draw the points
        synchronized(constructionLock, {
            val consStyles = HashMap<Any, Any>()
            listOf<MutableList<Any>>(
                    construction.getGeometricObjects(consStyles),
                    objects
            ).forEach {
                list ->
                list.forEach {

                    val style = if (consStyles[it] is Style) consStyles[it] as Style else objectStyles[it]

                    when (it) {
                        is PointS2 -> {
                            if (style != null) style.set(this)
                            else {
                                noStroke()
                                fill(100.0f, 125.0f, 255.0f)
                            }
                            drawPointE3(it.directionE3.endPoint)
                        }
                        is PointE2 -> {
                            if (style != null) style.set(this)
                            else {
                                noStroke()
                                fill(100.0f, 125.0f, 255.0f)
                            }
                            drawPointE2(it)
                        }
                        is DiskOP2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0.0f, 0.0f, 0.0f)
                                noFill()
                            }
                            drawDiskOP2(it)
                        }
                        is LineOP2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0.0f, 0.0f, 0.0f)
                                noFill()
                            }
                            drawLineOP2(it)
                        }
                        is PointE3 -> {
                            if (style != null) style.set(this)
                            else {
                                noStroke()
                                fill(100.0f, 125.0f, 255.0f)
                            }
                            drawPointE3(it)
                        }
                        is PointOP2 -> {
                            if (style != null) style.set(this)
                            else {
                                noStroke()
                                fill(100.0f, 125.0f, 255.0f)
                            }
                            drawPointOP2(it)
                        }

                        is PointOP3 -> {
                            if (style != null) style.set(this)
                            else {
                                noStroke()
                                fill(100.0f, 125.0f, 255.0f)
                            }
                            if (!it.isIdeal()) drawPointE3(it.toPointE3())
                        }
                        is DiskS2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0)
                            }
                            drawCircleS2(it)
                        }
                        is DiskE2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0)
                            }
                            drawCircleE2(it)
                        }
                        is CircleArcOP2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0.0f, 0.0f, 255.0f)
                            }
                            drawCircleArcOP2(it)
                            if (style == null) {
                                noStroke()
                                fill(255.0f, 0.0f, 125.0f)
                                drawPointOP2(it.source)
                                drawPointOP2(it.target)
                            }
                        }
                        is CircleArcS2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0.0f, 0.0f, 255.0f)
                            }
                            drawCircleArcS2(it)
                            if (style == null) {
                                noStroke()
                                fill(255.0f, 0.0f, 125.0f)
                                drawPointE3(it.source.directionE3.endPoint)
                                drawPointE3(it.target.directionE3.endPoint)
                            }
                        }
                        is CPlaneS2 -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(255.0f, 0.0f, 0.0f)
                            }
                            drawCircleS2(it.dualDiskS2)
                        }
                        is DCEL<*, *, *> -> {
                            if (style != null) style.set(this)
                            else {
                                stroke(0)
                                fill(255.0f, 255.0f, 255.0f)
                            }
                            drawDCEL(it)
                        }
                        else -> {}
                    }
                }
            }
        })



        if (viewSettings.showBoundingBox) {
            noFill()
            stroke(0)
            box(2.0f)
        }

        popStyle()
        val gl = this.g
        if (gl is PGraphicsOpenGL) {
            cursor(CROSS)
            var Minv = PMatrix3D(gl.projmodelview)
            //println(copyOfMatrix)
            Minv.invert() //PVM inverted
            //System.out.println("wdith " + width)
            var mousex = (2.0f * mouseX) / width - 1.0f
            var mousey = 1.0f - (2.0f * mouseY) / height

            var w1 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m33
            var w2 = Minv.m30 * mousex + Minv.m31 * mousey + Minv.m32 + Minv.m33

            var new1 = PointE3(
                    ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m03) / w1).toDouble(),
                    ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m13) / w1).toDouble(),
                    ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m23) / w1).toDouble()
            )

            var new2 = PointE3(
                    ((Minv.m00 * mousex + Minv.m01 * mousey + Minv.m02 + Minv.m03) / w2).toDouble(),
                    ((Minv.m10 * mousex + Minv.m11 * mousey + Minv.m12 + Minv.m13) / w2).toDouble(),
                    ((Minv.m20 * mousex + Minv.m21 * mousey + Minv.m22 + Minv.m23) / w2).toDouble()
            )

            var direction = (new2 - new1).normalize()

            var l = PointE3.O - new1

            var tca = l.dot(direction)
            var d = (Math.sqrt(l.normSq() - tca * tca))//(200.0*zoom)
            var thc = Math.sqrt(1 - d * d)
            var t0 = tca - thc
            var t1 = tca + thc
            var inter = PointE3(0.0, 0.0, 0.0)
            if (t0 > t1) {
                inter = new1 + direction * t1
//                                PointE3(newX1 + direction.x*t1, newY1 + t1*direction.y, newZ1 + t1*direction.z)
            } else {
                inter = new1 + direction * t0
//                        inter = PointE3(newX1 + direction.x*t0, newY1 + t0*direction.y, newZ1 + t0*direction.z)
            }
            pushMatrix() //
            translate(inter.x.toFloat(), inter.y.toFloat(), inter.z.toFloat()) //translates the origin
            sphere(0.05f)
            sphereCursor = PointS2(inter.x, inter.y, inter.z)
            popMatrix()
            var delta = .5
            listOf<MutableList<Any>>(construction.getGeometricObjects(),objects).forEach{
                list -> list.forEach {
                when(it) {
                    is PointS2 -> {
                        //println("it.x: " + it.x.toDouble() + " mousex: $mousex it.y: " + it.y.toDouble() + " mousey: $mousey")
                        if (Math.abs(it.x - mousex.toDouble()) <= delta && Math.abs(it.y - mousey.toDouble()) <= delta && clicked) {
                            var newIt = mouseDragged(it, inter)
                            list.remove(it)
                            list.add(newIt)
                        }
                    }
                }
            }
            }
        }
    }




    /*** Mouse Handling Code ***/

    fun mouseDragged(it : Any, inter : PointE3) : Any {
        currentTool?.mouseDragged(mouseX, mouseY)
        when(it) {
            is PointS2 -> {
                var newPoint = PointS2(inter.x, inter.y, inter.z)
                return newPoint
            }
        }
        return it
    }

    override fun mouseMoved() {
        currentTool?.mouseMoved(mouseX, mouseY)
    }

    override fun mouseClicked() {
        currentTool?.mouseClicked(mouseX, mouseY)
    }

    override fun mousePressed() {
        currentTool?.mousePressed(mouseX, mouseY)
    }

    override fun mouseReleased() {
        currentTool?.mouseReleased(mouseX, mouseY)
    }

    override fun mouseDragged() {
        currentTool?.mouseDragged(mouseX, mouseY)
    }

    override fun mouseWheel(event: MouseEvent?) {
        if (event != null) {
            //val delta = if (event.count > 0) 1.01f else if (event.count < 0) 1.0f/1.01f else 1.0f
            val delta = if (event.count > 0)
                Math.pow(1.0005, event.count.toDouble()).toFloat()
            else if (event.count < 0)
                Math.pow(1.0005, event.count.toDouble()).toFloat()
            else 1.0f
            zoom *= delta
        }
    }

}

/**
 * Start the sketch.
 */
fun main(passedArgs : Array<String>) {
    val appletArgs = arrayOf("sketches.ConstructionSketch")
    if (passedArgs != null) {
        PApplet.main(PApplet.concat(appletArgs, passedArgs))
    } else {
        PApplet.main(appletArgs)
    }
}