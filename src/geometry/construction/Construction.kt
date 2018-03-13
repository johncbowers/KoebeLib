package geometry.construction

/**
 * Created by johnbowers on 5/28/17.
 */

import geometry.primitives.Spherical2.*

/**
 * A directed acyclic graph representing a construction. The nodes with only out-degree are the base objects.
 */

class Construction {

    internal val nodes = mutableListOf<INode<*>>()

    internal fun getSourceNodes() = nodes.filter { it is BaseNode<*> || (it is ConstructionNode<*> && it.incoming.size > 0)  }
    internal fun getSinkNodes() = nodes.filter { it.outgoing.size > 0 }

    fun clear() =  nodes.clear()

    /**
     * @return The actual geometric objects used in this construction. These are sorted topologically based on construction dependencies.
     */
    fun getGeometricObjects(styles: HashMap<Any, Any>? = null) : MutableList<Any> {
        val objList = mutableListOf<Any>()
        nodes.forEach { it.visited = false }
        getSourceNodes().forEach { it.visit(objList, styles) }
        return objList
    }

    // Base constructions
    fun makePointS2(x: Double, y: Double, z:Double, style:Any? = null) = BaseNode<PointS2>(this, PointS2(x, y, z), style)

    // Other constructions
    fun makeDiskS2(p1: INode<PointS2>, p2: INode<PointS2>, p3: INode<PointS2>) =
            ConstructionNode<DiskS2>(this, listOf(p1, p2, p3), ThreePointsToDiskS2(), null)

    fun makeCoaxialFamilyS2(disk1: INode<DiskS2>, disk2: INode<DiskS2>) =
            ConstructionNode<CoaxialFamilyS2>(this, listOf(disk1, disk2), TwoPointsToCoaxialFamilyS2(), null)

    fun makeCPlaneS2(disk1: INode<DiskS2>, disk2: INode<DiskS2>, disk3: INode<DiskS2>) =
            ConstructionNode<CPlaneS2>(this, listOf(disk1, disk2, disk3), ThreeDisksToCPlaneS2(), null)
}


interface INode<OutputType> {

    val construction : Construction
    val outgoing : MutableList<INode<*>>
    var dirty : Boolean
    var visited : Boolean
    var style : Any?

    fun getOutput() : OutputType

    fun visit(objList: MutableList<Any>, styles: HashMap<Any, Any>? = null) {
        try {
            val geomObj = getOutput()
            val style = this.style
            objList.add(geomObj as Any)
            if (styles != null && style != null) {
                styles[geomObj] = style
            }
            visited = true
            outgoing.forEach { if (!it.visited && it.readyToOutputQ()) it.visit(objList, styles) }
        } catch (e: InvalidConstructionParametersException) {
            e.printStackTrace()
        }
    }

    fun readyToOutputQ(): Boolean

    fun dfsSetDirty() {
        dirty = true
        outgoing.forEach { it.dfsSetDirty() }
    }


}

class BaseNode<OutputType>(
        override val construction: Construction,
        var theObject: OutputType,
        override var style : Any? = null
): INode<OutputType> {

    override var visited = false
    override var dirty = false
    override val outgoing = mutableListOf<INode<*>>()

    init {
        construction.nodes.add(this)
    }

    override fun readyToOutputQ() = true

    override fun getOutput(): OutputType {
        return theObject
    }

    fun resetBaseNodeWith(x: Any) {
        theObject = x as OutputType
        this.dfsSetDirty()
    }
}

class ConstructionNode<OutputType>(
        override val construction: Construction,
        val incoming: List<INode<*>>,
        val algorithm: IAlgorithm<OutputType>,
        override var style : Any? = null
): INode<OutputType> {

    override var visited = false
    override var dirty = false
    override val outgoing = mutableListOf<INode<*>>()

    internal var _outObj: OutputType? = null

    init {
        incoming.forEach { it.outgoing.add(this) }
        construction.nodes.add(this)
    }

    override fun readyToOutputQ(): Boolean = incoming.map { it.visited }.reduce { acc, b ->  acc && b }

    override fun getOutput(): OutputType {
        var output = _outObj
        if (output == null || dirty) {
            output = algorithm.run(this)
            _outObj = output
            dirty = false
            return output
        } else {
            return output
        }
    }
}