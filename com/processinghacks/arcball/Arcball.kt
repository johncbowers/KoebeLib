package com.processinghacks.arcball

/*

  Adapted into Processing library 5th Feb 2006 Tom Carden
  from "simple Arcball use template" 9.16.03 Simon Greenwold

  Heavily updated and moved to github in March 2012.

  Copyright (c) 2003 Simon Greenwold
  Copyright (c) 2006, 2012 Tom Carden

  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General
  Public License along with this library; if not, write to the
  Free Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA  02111-1307  USA

*/


import processing.core.PApplet
import processing.core.PVector

import java.awt.event.MouseEvent

class Arcball(center: PVector?, radius: Float, internal var parent: PApplet) {
    internal var center: PVector
    internal var radius: Float = 0.toFloat()

    internal var qNow = Quat()
    internal var qDrag: Quat? = null
    internal var dragFactor = 0.99f

    /** defaults to radius of mag(width, height)/2  */
    constructor(parent: PApplet) : this(null, 0f, parent) {
    }

    init {
        var center = center
        var radius = radius

        if (center == null) {
            val w = parent.g.width.toFloat()
            val h = parent.g.height.toFloat()
            if (radius == 0f) {
                radius = PApplet.mag(w, h) / 2.0f
            }
            center = PVector(w / 2.0f, h / 2.0f)
        }

        //parent.registerMethod("mouseEvent", this)
        parent.registerMethod("mouseDragged", this)
        parent.registerMethod("mousePressed", this)
        parent.registerMethod("mouseReleased", this)
        parent.registerMethod("pre", this)

        this.center = center
        this.radius = radius
    }

    fun reset() {
        qNow = Quat()
        qDrag = null
    }

    fun mouseEvent(event: MouseEvent) {

        val id = event.id
        if (id == MouseEvent.MOUSE_DRAGGED) {
            mouseDragged()
        } else if (id == MouseEvent.MOUSE_PRESSED) {
            mousePressed()
        } else if (id == MouseEvent.MOUSE_RELEASED) {
            mouseReleased()
        }
    }

    fun mousePressed() {
        qDrag = null
    }

    fun mouseReleased() {
        updateDrag()
    }

    fun mouseDragged() {
        if (!parent.mousePressed) return
        updateDrag()
        if (qDrag != null) {
            qNow = Quat.mult(qNow, qDrag as Quat)
        }
    }

    private fun updateDrag() {
        val pMouse = PVector(parent.pmouseX.toFloat(), parent.pmouseY.toFloat())
        val mouse = PVector(parent.mouseX.toFloat(), parent.mouseY.toFloat())
        val from = mouseOnSphere(pMouse)
        val to = mouseOnSphere(mouse)
        qDrag = Quat(from.dot(to), from.cross(to))
    }

    fun pre() {
        if (dragFactor > 0.0 && !parent.mousePressed && qDrag != null && qDrag!!.w < 0.999999) {
            qDrag!!.scaleAngle(dragFactor)
            qNow = Quat.mult(qNow, qDrag as Quat)
        }
    }

    private fun mouseOnSphere(mouse: PVector): PVector {
        val v = PVector()
        v.x = (mouse.x - center.x) / radius
        v.y = (mouse.y - center.y) / radius

        val mag = v.x * v.x + v.y * v.y
        if (mag > 1.0f) {
            v.normalize()
        } else {
            v.z = PApplet.sqrt(1.0f - mag)
        }
        return v
    }

    val angle: Float
        get() = qNow.angle

    val axis: PVector
        get() = qNow.axis

    // Quat!

    internal class Quat {

        var w: Float = 0.toFloat()
        var x: Float = 0.toFloat()
        var y: Float = 0.toFloat()
        var z: Float = 0.toFloat()

        constructor() {
            reset()
        }

        constructor(w: Float, v: PVector) {
            this.w = w
            x = v.x
            y = v.y
            z = v.z
        }

        constructor(w: Float, x: Float, y: Float, z: Float) {
            this.w = w
            this.x = x
            this.y = y
            this.z = z
        }

        fun reset() {
            w = 1.0f
            x = 0.0f
            y = 0.0f
            z = 0.0f
        }

        operator fun set(w: Float, x: Float, y: Float, z: Float) {
            this.w = w
            this.x = x
            this.y = y
            this.z = z
        }

        operator fun set(w: Float, v: PVector) {
            this.w = w
            x = v.x
            y = v.y
            z = v.z
        }

        fun set(q: Quat) {
            w = q.w
            x = q.x
            y = q.y
            z = q.z
        }

        // transforming this quat into an angle and an axis vector...
        val value: FloatArray
            get() {

                val res = FloatArray(4)

                var sa = Math.sqrt((1.0f - w * w).toDouble()).toFloat()
                if (sa < PApplet.EPSILON) {
                    sa = 1.0f
                }

                res[0] = Math.acos(w.toDouble()).toFloat() * 2.0f
                res[1] = x / sa
                res[2] = y / sa
                res[3] = z / sa

                return res
            }

        var angle: Float
            get() = PApplet.acos(w) * 2.0f
            set(angle) {
                val axis = axis
                w = PApplet.cos(angle / 2.0f)
                val scale = PApplet.sin(angle / 2.0f)
                x = axis.x * scale
                y = axis.y * scale
                z = axis.z * scale
            }

        val axis: PVector
            get() {
                var sa = PApplet.sqrt(1.0f - w * w).toFloat()
                if (sa < PApplet.EPSILON) {
                    sa = 1.0f
                }
                return PVector(x / sa, y / sa, z / sa)
            }

        // these are a bit sketchy because they've been written without concern for whetherthe quat remains a unit quat :-/

        fun scaleAngle(scale: Float) {
            angle = scale * angle
        }

        companion object {

            fun mult(q1: Quat, q2: Quat): Quat {
                val res = Quat()
                res.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z
                res.x = q1.w * q2.x + q1.x * q2.w + q1.y * q2.z - q1.z * q2.y
                res.y = q1.w * q2.y + q1.y * q2.w + q1.z * q2.x - q1.x * q2.z
                res.z = q1.w * q2.z + q1.z * q2.w + q1.x * q2.y - q1.y * q2.x
                return res
            }
        }

    } // Quat

}
