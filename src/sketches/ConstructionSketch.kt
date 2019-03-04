package sketches

import com.processinghacks.arcball.Arcball
import geometry.construction.BaseNode
import geometry.construction.INode
import geometry.ds.dcel.DCEL
import geometry.primitives.Euclidean2.DiskE2
import geometry.primitives.Euclidean2.PointE2
import geometry.primitives.Euclidean3.PointE3
import geometry.primitives.Euclidean3.VectorE3
import geometry.primitives.OrientedProjective2.CircleArcOP2
import geometry.primitives.OrientedProjective2.DiskOP2
import geometry.primitives.OrientedProjective2.LineOP2
import geometry.primitives.OrientedProjective2.PointOP2
import geometry.primitives.OrientedProjective3.PointOP3
import geometry.primitives.Spherical2.*
import gui.ConstructionGUI
import processing.core.PApplet
import processing.event.MouseEvent

import processing.core.PMatrix3D
import processing.opengl.PGraphicsOpenGL
import java.util.*
import java.util.concurrent.locks.ReentrantLock

import kotlin.collections.HashSet

/**
 * Created by browermb on 7/27/2017.
 */


open class MouseTool(open var sketch: ConstructionSketch) {
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

        return false
    }
}

class ArcballTool(val arcball: Arcball?,  override var sketch: ConstructionSketch) : MouseTool(sketch) {

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

}

class IntersectionTool(override var sketch: ConstructionSketch) : MouseTool(sketch) {
    var selectedDisks = mutableListOf<INode<DiskS2>>()

    override fun mousePressed(mouseX: Int, mouseY: Int) {
        super.mousePressed(mouseX, mouseY)
        var cursor = super.transform(mouseX, mouseY)

        if(cursor != null) {
            for(node in sketch.construction.nodes) {
                var output = node.getOutput()

                if (output is DiskS2) {
                    var a = output.a
                    var b = output.b
                    var c = output.c
                    var d = output.d

                    if(super.isIntersection(mouseX, mouseY, a, b, c, d)) {

                        if(!selectedDisks.contains(node)) {
                            selectedDisks.add(node as INode<DiskS2>)
                            break
                        }
                    }
                }
            }
        }

    }

    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)
        if(selectedDisks.size >= 2) {
            var obj1 = selectedDisks[0]
            var obj2 = selectedDisks[1]
            sketch.construction.makeIntersectionPoint(obj1, obj2)
        }
    }
}
class SelectionTool(override var sketch: ConstructionSketch) : MouseTool(sketch) {

    //TODO: add a feature to select multiple things
    override fun mousePressed(mouseX: Int, mouseY: Int) {
        var cursor = super.transform(mouseX, mouseY)

        if(cursor!= null) {
            var minDist = .1
            for(node in sketch.construction.nodes) {
                var output = node.getOutput()

                if(output is PointS2) {
                    val dist = Math.sqrt(Math.pow(output.x - cursor.x, 2.0) + Math.pow(output.y - cursor.y, 2.0)
                            + Math.pow(output.z - cursor.z, 2.0))

                    if (dist < minDist) {
                        //TODO: change the cursor so the point is visible when being dragged
                        minDist = dist
                        node.style = Style(Color.noColor, Color(128.0.toFloat(), 0.0.toFloat(), 128.0.toFloat()))
                        break
                    }

                }

                if (output is DiskS2) {
                    var a = output.a
                    var b = output.b
                    var c = output.c
                    var d = output.d

                    if(super.isIntersection(mouseX, mouseY, a, b, c, d)) {
                        val center = output.centerE3
                        val radius = output.radiusE3
                        val diskDist = Math.sqrt(Math.pow(center.x - cursor.x, 2.0) + Math.pow(center.y - cursor.y, 2.0)
                                + Math.pow(center.z - cursor.z, 2.0)) - radius

                        if (diskDist < minDist) {
                            minDist = diskDist
                            node.style = Style(Color(255.0.toFloat(), 255.0.toFloat(), 0.0.toFloat()), Color.noColor)
                            break
                        }

                    }
                }
            }
        }
    }
}

open class PointEditorTool(override var sketch: ConstructionSketch) : MouseTool(sketch) {

    var selectedNode : INode<*>? = null

    override fun mousePressed(mouseX: Int, mouseY: Int) {
        super.mousePressed(mouseX, mouseY)
        var cursor = super.transform(mouseX, mouseY)

        if (cursor != null) {

            for (node in sketch.construction.nodes) {
                var minDist = .1

                if (node != null) {
                    var output = node.getOutput()

                    if (output is PointS2) {
                        val dist = Math.sqrt(Math.pow(output.x - cursor.x, 2.0) + Math.pow(output.y - cursor.y, 2.0)
                                + Math.pow(output.z - cursor.z, 2.0))

                        if (dist < minDist) {
                            //TODO: change the cursor so the point is visible when being dragged
                            minDist = dist
                            selectedNode = node
                            break
                        }
                    }

                }
            }
        }
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
        var cursor = super.transform(mouseX, mouseY)

        if (cursor != null) {
            for (node in sketch.construction.nodes) {
                var minDist = .1

                if(node != null) {
                    var output = node.getOutput()
                    if (output is PointS2) {

                        val dist = Math.sqrt(Math.pow(output.x - cursor.x, 2.0) + Math.pow(output.y - cursor.y, 2.0)
                                + Math.pow(output.z - cursor.z, 2.0))

                        if (dist < minDist) {
                            minDist = dist
                            drawNode = false
                            break
                        }
                    }
                }
            }

            if (drawNode) {
                sketch.construction.makePointS2(cursor.x, cursor.y, cursor.z)
            }
        }

    }

    class CircleTool(override var sketch: ConstructionSketch) : MouseTool(sketch) {
        var selectedNode : INode<*>? = null
        var selectedNodes = mutableListOf<INode<*>>()

        override fun mousePressed(mouseX: Int, mouseY: Int) {
            super.mousePressed(mouseX, mouseY)
            var cursor = super.transform(mouseX, mouseY)

            if (cursor != null) {
                var minDist = .1
                for (node in sketch.construction.nodes) {

                    if (node != null) {
                        var output = node.getOutput()

                        if (output is PointS2) {
                            val dist = Math.sqrt(Math.pow(output.x - cursor.x, 2.0) + Math.pow(output.y - cursor.y, 2.0)
                                    + Math.pow(output.z - cursor.z, 2.0))

                            if (dist < minDist) {
                                minDist = dist
                                selectedNode = node
                                node.style = Style(Color.noColor, Color(255.0.toFloat(), 0.0.toFloat(), 0.0.toFloat()))

                                if (!selectedNodes.contains(selectedNode as INode<*>)) {
                                    selectedNodes.add(selectedNode as INode<*>)
                                    break
                                }
                            }
                        }
                    }
                }
            }
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
                selectedNodes.removeAll{true}

            }
        }

    }
}

open class CoaxialPointTool(override var sketch: ConstructionSketch) : MouseTool(sketch) {

    var selectedNode : INode<*>? = null
    var selectedNodes = mutableListOf<INode<*>>()

    override fun mousePressed(mouseX: Int, mouseY: Int) {
        super.mousePressed(mouseX, mouseY)
        val cursor = transform(mouseX, mouseY)

        if (cursor != null) {
            var minDist = .1
            for (node in sketch.construction.nodes) {

                if (node != null) {
                    val output = node.getOutput()

                    if (output is PointS2) {
                        val dist = Math.sqrt(Math.pow(output.x - cursor.x, 2.0) + Math.pow(output.y - cursor.y, 2.0)
                                + Math.pow(output.z - cursor.z, 2.0))
                        if (dist < minDist) {
                            minDist = dist
                            selectedNode = node
                        }
                    }

                    if (output is DiskS2) {
                        val a = output.a
                        val b = output.b
                        val c = output.c
                        val d = output.d

                        if(isIntersection(mouseX, mouseY, a, b, c, d)) {
                            val center = output.centerE3
                            val radius = output.radiusE3
                            val diskDist = Math.sqrt(Math.pow(center.x - cursor.x, 2.0) + Math.pow(center.y - cursor.y, 2.0)
                                                     + Math.pow(center.z - cursor.z, 2.0)) - radius
                            if (diskDist < minDist) {
                                minDist = diskDist
                                selectedNode = node
                            }
                        }
                    }
                }
            }

            selectedNode?.style = Style(Color(200.0.toFloat(), 0.0f, 0.0f), Color(255.0.toFloat(), 0.0.toFloat(), 0.0.toFloat()))
            if(!selectedNodes.contains(selectedNode)) {
                selectedNodes.add(selectedNode as INode<*>)
            }
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)

        if (selectedNodes.size >= 3) {
            var obj1 = selectedNodes[0]
            var obj2 = selectedNodes[1]
            var obj3 = selectedNodes[2]

            @Suppress("UNCHECKED_CAST")
            sketch.construction.makeCoaxialDisk( obj1 as INode<DiskS2>,
                    obj2 as INode<DiskS2>,
                    obj3 as INode<PointS2>)

        }
    }
}

open class ThreeDiskCPlaneTool(override var sketch: ConstructionSketch) : MouseTool(sketch) {
    var selectedNode : INode<*>? = null
    var selectedNodes = mutableListOf<INode<*>>()

    override fun mousePressed(mouseX: Int, mouseY: Int) {
        super.mousePressed(mouseX, mouseY)
        val cursor = super.transform(mouseX, mouseY)

        if (cursor != null) {
            var minDist = .1
            for (node in sketch.construction.nodes) {

                if (node != null) {
                    val output = node.getOutput()
                    if (output is DiskS2) {
                        val a = output.a
                        val b = output.b
                        val c = output.c
                        val d = output.d

                        if(super.isIntersection(mouseX, mouseY, a, b, c, d)) {
                            val center = output.centerE3
                            val radius = output.radiusE3
                            val diskDist = Math.sqrt(Math.pow(center.x - cursor.x, 2.0) + Math.pow(center.y - cursor.y, 2.0)
                                    + Math.pow(center.z - cursor.z, 2.0)) - radius
                            if (diskDist < minDist) {
                                minDist = diskDist
                                selectedNode = node
                            }
                        }
                    }
                }
            }

            selectedNode?.style = Style(Color(200.0.toFloat(), 0.0f, 0.0f), Color(255.0.toFloat(), 0.0.toFloat(), 0.0.toFloat()))
            if(!selectedNodes.contains(selectedNode)) {
                selectedNodes.add(selectedNode as INode<*>)
            }
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)

        if (selectedNodes.size >= 3) {
            var obj1 = selectedNodes[0]
            var obj2 = selectedNodes[1]
            var obj3 = selectedNodes[2]

            @Suppress("UNCHECKED_CAST")
            sketch.construction.makeCPlaneS2( obj1 as INode<DiskS2>,
                    obj2 as INode<DiskS2>,
                    obj3 as INode<DiskS2>)

        }
    }
}
open class ThreePlanesDiskTool(override var sketch: ConstructionSketch) : MouseTool(sketch) {
    var selectedNode : INode<*>? = null
    var selectedNodes = mutableListOf<INode<*>>()

    override fun mousePressed(mouseX: Int, mouseY: Int) {
        super.mousePressed(mouseX, mouseY)
        val cursor = transform(mouseX, mouseY)

        if (cursor != null) {
            var minDist = .1
            for (node in sketch.construction.nodes) {

                if (node != null) {
                    val output = node.getOutput()
                    if (output is CPlaneS2) {
                        val a = output.a
                        val b = output.b
                        val c = output.c
                        val d = output.d
                        if(isIntersection(mouseX, mouseY, a, b, c, d)) {
                            //TODO: figure out how to check the distance between the cursor and a plane
                            //val planeDist = Math.sqrt(Math.pow(center.x - cursor.x, 2.0) + Math.pow(center.y - cursor.y, 2.0)
                                    //+ Math.pow(center.z - cursor.z, 2.0)) - radius
                           // if (planeDist < minDist) {
                             //   minDist = planeDist
                               // selectedNode = node
                            //}
                        }
                    }
                }
            }

            selectedNode?.style = Style(Color(200.0.toFloat(), 0.0f, 0.0f), Color(255.0.toFloat(), 0.0.toFloat(), 0.0.toFloat()))
            if(!selectedNodes.contains(selectedNode)) {
                selectedNodes.add(selectedNode as INode<*>)
            }
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)
        if (selectedNodes.size >= 3) {
            var obj1 = selectedNodes[0]
            var obj2 = selectedNodes[1]
            var obj3 = selectedNodes[2]

            @Suppress("UNCHECKED_CAST")
            sketch.construction.makeDiskFromPlanes( obj1 as INode<CPlaneS2>,
                obj2 as INode<CPlaneS2>,
                obj3 as INode<CPlaneS2>)

        }
    }
}

open class HyperbolicPointTool(override var sketch: ConstructionSketch) : MouseTool(sketch) {
    var selectedNode : INode<*>? = null
    var selectedNodes = mutableListOf<INode<*>>()

    override fun mousePressed(mouseX: Int, mouseY: Int) {
        super.mousePressed(mouseX, mouseY)
        val cursor = super.transform(mouseX, mouseY)

        if (cursor != null) {
            var minDist = .1
            for (node in sketch.construction.nodes) {

                if (node != null) {
                    val output = node.getOutput()

                    if (output is DiskS2) {
                        val a = output.a
                        val b = output.b
                        val c = output.c
                        val d = output.d

                        if(super.isIntersection(mouseX, mouseY, a, b, c, d)) {
                            val center = output.centerE3
                            val radius = output.radiusE3
                            val diskDist = Math.sqrt(Math.pow(center.x - cursor.x, 2.0) + Math.pow(center.y - cursor.y, 2.0)
                                    + Math.pow(center.z - cursor.z, 2.0)) - radius
                            if (diskDist < minDist) {
                                minDist = diskDist
                                selectedNode = node
                            }
                        }
                    }

                    if (output is PointS2) {
                        val dist = Math.sqrt(Math.pow(output.x - cursor.x, 2.0) + Math.pow(output.y - cursor.y, 2.0)
                                + Math.pow(output.z - cursor.z, 2.0))
                        if (dist < minDist) {
                            minDist = dist
                            selectedNode = node
                        }
                    }
                }
            }

            selectedNode?.style = Style(Color(200.0.toFloat(), 0.0f, 0.0f), Color(255.0.toFloat(), 0.0.toFloat(), 0.0.toFloat()))
            if(!selectedNodes.contains(selectedNode)) {
                selectedNodes.add(selectedNode as INode<*>)
            }
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)

        if (selectedNodes.size >= 3) {
            var obj1 = selectedNodes[0]
            var obj2 = selectedNodes[1]
            var obj3 = selectedNodes[2]

            @Suppress("UNCHECKED_CAST")
            sketch.construction.makeHyperbolicDisk( obj1 as INode<DiskS2>,
                obj2 as INode<DiskS2>,
                obj3 as INode<PointS2>)

        }
    }
}

open class DeleteTool(override var sketch: ConstructionSketch) : MouseTool(sketch) {
    var selectedNode: INode<*>? = null
    var selectedNodes = mutableListOf<INode<*>?>()

    override fun mousePressed(mouseX: Int, mouseY: Int) {
        var cursor = transform(mouseX, mouseY)

        if (cursor != null) {
            var minDist = .1
            for (node in sketch.construction.nodes) {
                var output = node.getOutput()

                if (output is PointS2) {
                    val dist = Math.sqrt(
                        Math.pow(output.x - cursor.x, 2.0) + Math.pow(output.y - cursor.y, 2.0)
                                + Math.pow(output.z - cursor.z, 2.0)
                    )
                    if (dist < minDist) {
                        minDist = dist
                        selectedNode = node
                    }
                }

                if (output is DiskS2) {
                    val a = output.a
                    val b = output.b
                    val c = output.c
                    val d = output.d
                    if (isIntersection(mouseX, mouseY, a, b, c, d)) {
                        val center = output.centerE3
                        val radius = output.radiusE3
                        val diskDist = Math.sqrt(
                            Math.pow(center.x - cursor.x, 2.0) + Math.pow(center.y - cursor.y, 2.0)
                                    + Math.pow(center.z - cursor.z, 2.0)
                        ) - radius
                        if (diskDist < minDist) {
                            minDist = diskDist
                            selectedNode = node
                        }
                    }
                }
            }
        }

        selectedNodes.add(selectedNode)

    }

    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)
        bfs()
        delete()
    }

    fun bfs() {
        var frontier = ArrayDeque<INode<*>?>()
        var closed = HashSet<INode<*>?>()

        frontier.add(sketch.construction.nodes.get(0))
        closed.add(sketch.construction.nodes.get(0))

        while (!frontier.isEmpty()) {
            var curr = frontier.removeFirst()
            closed.add(curr)

            if (selectedNodes.contains(curr)) {
                curr?.delete = true
                for (node in curr!!.outgoing) {
                    if(!frontier.contains(node) && !closed.contains(node)) {
                        node.delete = true
                        frontier.addLast(node)
                    }
                }
            } else {

                if(curr!!.outgoing.size != 0) {
                    for (node in curr!!.outgoing) {
                        if(!frontier.contains(node) && !closed.contains(node)) {
                            frontier.addLast(node)
                        }
                    }
                } else {
                    for (node in sketch.construction.getSourceNodes()) {
                        if(!frontier.contains(node) && !closed.contains(node)) {
                            frontier.addLast(node)
                        }
                    }
                }

            }
        }

    }

    fun delete() {

        val lock = ReentrantLock()
        val iter = sketch.construction.nodes.iterator()

        synchronized(lock,{
            while (iter.hasNext()) {
                var curr = iter.next()

                if (curr.delete) {
                    iter.remove()
                }

            }
        })
    }
}


open class ConstructionSketch : SphericalSketch() {

    var currentTool : MouseTool? = null

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

                    if(it is List<*>) {
                        it.forEach {
                            obj -> drawGeometricObjects(obj, style)
                        }
                    } else {
                        drawGeometricObjects(it, style)
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
            var inter = PointE3()

            if (t0 > t1) {
                inter = new1 + direction * t1
            } else {
                inter = new1 + direction * t0
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

    fun drawGeometricObjects(it : Any?, style: Style?) {
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