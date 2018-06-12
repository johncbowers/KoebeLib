package tilings.ds

import geometry.ds.dcel.DCEL
import geometry.primitives.Euclidean2.PointE2

class PentagonalTwistTile (s : ArrayList<PointE2>) : Tile<PointE2, Unit, Unit> (null) {
    val twistSize = 5

    init {
        val inner = graph.Face( data = Unit )

        addVertices(inner, s)

        root = TileNode<PointE2, Unit, Unit, DCEL<PointE2, Unit, Unit>.Face>(graph.faces[0], null, null)
        frontier.add(root!!)
    }

    /**
     * Adds initial vertices from the point list to to the graph
     */
    private fun addVertices (f : DCEL<PointE2, Unit, Unit>.Face, s : ArrayList<PointE2>) {
        val v = ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>()

        for (k in 0..twistSize-1) {
            v.add(graph.Vertex( data = s[k]))
        }

        addEdges(f, v)
    }

    /**
     * Adds initial edges to the graph
     */
    private fun addEdges (f : DCEL<PointE2, Unit, Unit>.Face, v : ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>) {
        val e = ArrayList<DCEL<PointE2, Unit, Unit>.Edge>()

        for (k in 0..twistSize-1) {
            e.add(graph.Edge( data = Unit))
        }

        addHalfEdges(f, v, e)
    }

    /**
     * Adds initial half edges to the graph
     */
    private fun addHalfEdges (f : DCEL<PointE2, Unit, Unit>.Face, v : ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>,
                              e : ArrayList<DCEL<PointE2, Unit, Unit>.Edge>) {

        val innerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
        val outerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()

        for (k in 0..twistSize-1) {


            // inner darts
            if (k > 0) {
                innerDarts.add(graph.Dart(edge = e[k], origin = v[k], face = f, prev = innerDarts[k-1]))
            } else {
                innerDarts.add(graph.Dart(edge = e[k], origin = v[k], face = f))
            }

            // outer darts
            if (k > 0) {
                outerDarts.add(graph.Dart(edge = e[k], origin = v[(k + 1) % twistSize], face = graph.outerFace,
                        next = outerDarts[k - 1] ,twin = innerDarts[k]))
            } else {
                outerDarts.add(graph.Dart(edge = e[k], origin = v[(k + 1) % twistSize], face = graph.outerFace,
                        twin = innerDarts[k]))
            }

        }

        for (k in 0..twistSize-1) {
            if (k == 0) {
                innerDarts[k].makePrev(innerDarts[(k + twistSize - 1) % twistSize])
                outerDarts[k].makeNext(outerDarts[(k + twistSize - 1) % twistSize])
            }

            //inner darts
            innerDarts[k].makeTwin(outerDarts[k])
            innerDarts[k].makeNext(innerDarts[(k + 1) % twistSize])

            //outer darts
            outerDarts[k].makePrev(outerDarts[(k + 1) % twistSize])
        }

        graph.faces[0].aDart = innerDarts[0]

    }

    override fun collar (face : DCEL<PointE2, Unit, Unit>.Face) :
            ArrayList<DCEL<PointE2, Unit, Unit>.Face> {
        val neighbors = ArrayList<DCEL<PointE2, Unit, Unit>.Face>()

        for (dart in face.darts()) {
            if (!neighbors.contains(dart.twin!!.face) && dart.twin!!.face != graph.outerFace) {
                neighbors.add(dart.twin!!.face!!)
            }
        }

        return neighbors
    }

    override fun subdivide () {
        val len = graph.faces.size
        val lonelyDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
        var frontierIdx = 0

        for (k in 0..len-1) {
            frontierIdx = findFace(graph.faces[k])
            subDivideFace(graph.faces[k], lonelyDarts, frontier[frontierIdx])
            frontier.removeAt(frontierIdx)
        }

        pairLonelyDarts(lonelyDarts)

        for (k in 0..len-1) {
            graph.faces.removeAt(0)
        }
    }

    private fun averagePoint (face : DCEL<PointE2, Unit, Unit>.Face) : PointE2 {
        val darts = face.darts()
        var x_avg = 0.0
        var y_avg = 0.0

        for (dart in darts) {
            x_avg += dart.origin!!.data.x
            y_avg += dart.origin!!.data.y

        }

        return PointE2(x_avg / darts.size, y_avg / darts.size)

    }

    private fun areOpposite(dart1 : DCEL<PointE2, Unit, Unit>.Dart, dart2 : DCEL<PointE2, Unit, Unit>.Dart) : Boolean {
        val dx1 = dart1.next!!.origin!!.data.x - dart1.origin!!.data.x
        val dx2 = dart2.next!!.origin!!.data.x - dart2.origin!!.data.x

        val dy1 = dart1.next!!.origin!!.data.y - dart1.origin!!.data.y
        val dy2 = dart2.next!!.origin!!.data.y - dart2.origin!!.data.y

        return (dx1 == -dx2 && dy1 == -dy2)
    }

    private fun findFace (face : DCEL<PointE2, Unit, Unit>.Face) : Int{
        for (k in 0..frontier.size-1) {
            if (frontier[k].value == face) {
                return k
            }
        }
        return -1
    }

    private fun pairLonelyDarts (lonelyDarts: ArrayList<DCEL<PointE2, Unit, Unit>.Dart>) {
        val len = lonelyDarts.size
        var stepSize = 0.0


        if (lonelyDarts.size > 0) {
            stepSize = lonelyDarts[0].origin!!.data.distTo(lonelyDarts[1].origin!!.data)
        }

        while (lonelyDarts.size > 0) {
            for (j in 0..lonelyDarts.size-1) {
                if (lonelyDarts[0].face != lonelyDarts[j].face) {
                    //println(lonelyDarts[0].origin!!.data.distTo(lonelyDarts[j].origin!!.data))
                }
                if (areOpposite(lonelyDarts[0], lonelyDarts[j])
                        &&  lonelyDarts[0].origin!!.data.distTo(lonelyDarts[j].next!!.origin!!.data) == 0.0
                        && lonelyDarts[0].face != lonelyDarts[j].face) {
                    lonelyDarts[0].makeTwin(lonelyDarts[j])
                    lonelyDarts.removeAt(j)
                    lonelyDarts.removeAt(0)
                    break
                }
            }
        }

    }

    private fun subDivideFace (face : DCEL<PointE2, Unit, Unit>.Face,
                                    lonelyDarts : ArrayList<DCEL<PointE2, Unit, Unit>.Dart>,
                                    parentFace : TreeNode<DCEL<PointE2, Unit, Unit>.Face>) {
        val numFaces = 5
        val newFaces = ArrayList<DCEL<PointE2, Unit, Unit>.Face>()

        val newVerts = ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>()
        val newDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
        val outerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()

        for (k in 0..numFaces-1) {
            newFaces.add(graph.Face(data = Unit))
            parentFace.children.add(TreeNode(newFaces[k], parentFace, null))
        }

        // Update the frontier
        frontier.addAll(parentFace.children)

        // Exterior Darts and Vertices
        val darts = face.darts()
        val len = darts.size
        var midPoint : PointE2
        for (k in 0..len-1) {
            midPoint = PointE2((darts[k].origin!!.data.x + darts[(k+1) % len].origin!!.data.x) / 2.0,
                    (darts[k].origin!!.data.y + darts[(k+1) % len].origin!!.data.y) / 2.0)

            newVerts.add(graph.Vertex(data = PointE2((darts[k].origin!!.data.x + midPoint.x) / 2.0,
                    (darts[k].origin!!.data.y + midPoint.y) / 2.0)))

            newVerts.add(graph.Vertex(data = PointE2((midPoint.x + darts[(k+1) % len].origin!!.data.x) / 2.0,
                    (midPoint.y + darts[(k+1) % len].origin!!.data.y) / 2.0)))

            newDarts.add(graph.Dart(origin = darts[k].origin))
            newDarts.add(graph.Dart(origin = newVerts[newVerts.size-2]))
            newDarts.add(graph.Dart(origin = newVerts[newVerts.size-1]))

            if (darts[k].twin != null && darts[k].twin!!.face == graph.outerFace) {
                outerDarts.add(graph.Dart(origin = newVerts[newVerts.size-2], twin = newDarts[newDarts.size-3],
                        face = graph.outerFace))
                outerDarts.add(graph.Dart(origin = newVerts[newVerts.size-1], twin = newDarts[newDarts.size-2],
                        face = graph.outerFace))
                outerDarts.add(graph.Dart(origin = darts[k].dest, prev = outerDarts[outerDarts.size-1],
                        twin = newDarts[newDarts.size-1], face = graph.outerFace))
                graph.darts.remove(darts[k].twin)
            }
            else {
                lonelyDarts.add(newDarts[newDarts.size-3])
                lonelyDarts.add(newDarts[newDarts.size-2])
                lonelyDarts.add(newDarts[newDarts.size-1])
            }

            graph.darts.remove(darts[k])

        }

        // Face 0
        newDarts[0].face = newFaces[0]
        newDarts[1].face = newFaces[0]
        newDarts[14].face = newFaces[0]

        newDarts[0].makeNext(newDarts[1])
        newDarts[14].makeNext(newDarts[0])

        // Face 1
        newDarts[2].face = newFaces[1]
        newDarts[3].face = newFaces[1]
        newDarts[4].face = newFaces[1]

        newDarts[2].makeNext(newDarts[3])
        newDarts[3].makeNext(newDarts[4])

        // Face 2
        newDarts[5].face = newFaces[2]
        newDarts[6].face = newFaces[2]
        newDarts[7].face = newFaces[2]

        newDarts[5].makeNext(newDarts[6])
        newDarts[6].makeNext(newDarts[7])


        // Face 3
        newDarts[8].face = newFaces[3]
        newDarts[9].face = newFaces[3]
        newDarts[10].face = newFaces[3]

        newDarts[8].makeNext(newDarts[9])
        newDarts[9].makeNext(newDarts[10])

        // Face 4
        newDarts[11].face = newFaces[4]
        newDarts[12].face = newFaces[4]
        newDarts[13].face = newFaces[4]

        newDarts[11].makeNext(newDarts[12])
        newDarts[12].makeNext(newDarts[13])

        //Interior Vertices and Darts
        val innerVerts = ArrayList<DCEL<PointE2, Unit, Unit>.Vertex>()

        innerVerts.add(graph.Vertex(data = averagePoint(face)))

        val innerDarts = ArrayList<DCEL<PointE2, Unit, Unit>.Dart>()
        innerDarts.add(graph.Dart(origin = newVerts[1], face = newFaces[0]))
        innerDarts.add(graph.Dart(origin = innerVerts[0], twin = innerDarts[innerDarts.size-1], face = newFaces[1]))

        innerDarts.add(graph.Dart(origin = newVerts[3], face = newFaces[1]))
        innerDarts.add(graph.Dart(origin = innerVerts[0], twin = innerDarts[innerDarts.size-1], face = newFaces[2]))

        innerDarts.add(graph.Dart(origin = newVerts[5], face = newFaces[2]))
        innerDarts.add(graph.Dart(origin = innerVerts[0], twin = innerDarts[innerDarts.size-1], face = newFaces[3]))

        innerDarts.add(graph.Dart(origin = newVerts[7], face = newFaces[3]))
        innerDarts.add(graph.Dart(origin = innerVerts[0], twin = innerDarts[innerDarts.size-1], face = newFaces[4]))

        innerDarts.add(graph.Dart(origin = newVerts[9], face = newFaces[4]))
        innerDarts.add(graph.Dart(origin = innerVerts[0], twin = innerDarts[innerDarts.size-1], face = newFaces[0]))

        newFaces[0].aDart = newDarts[0]
        newFaces[1].aDart = newDarts[3]
        newFaces[2].aDart = newDarts[6]
        newFaces[3].aDart = newDarts[9]
        newFaces[4].aDart = newDarts[12]


        innerDarts[0].makeNext(innerDarts[9])
        innerDarts[1].makeNext(newDarts[2])
        innerDarts[2].makeNext(innerDarts[1])
        innerDarts[3].makeNext(newDarts[5])
        innerDarts[4].makeNext(innerDarts[3])
        innerDarts[5].makeNext(newDarts[8])
        innerDarts[6].makeNext(innerDarts[5])
        innerDarts[7].makeNext(newDarts[11])
        innerDarts[8].makeNext(innerDarts[7])
        innerDarts[9].makeNext(newDarts[14])

        innerDarts[0].makePrev(newDarts[1])
        innerDarts[2].makePrev(newDarts[4])
        innerDarts[4].makePrev(newDarts[7])
        innerDarts[6].makePrev(newDarts[10])
        innerDarts[8].makePrev(newDarts[13])


    }
}