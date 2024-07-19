package org.nxn.math

import java.nio.FloatBuffer

class Vector3f(var x : Float, var y : Float, var z : Float)  extends ToBuffer{

  def add(a:Vector3f, b:Vector3f):this.type = {
    x = a.x + b.x
    y = a.y + b.y
    z = a.z + b.z

    this
  }

  def add(b:Vector3f):this.type = {
    x += b.x
    y += b.y
    z += b.z

    this
  }

  def sub(b:Vector3f): this.type = {
    x -= b.x
    y -= b.y
    z -= b.z

    this
  }

  def sub(a:Vector3f, b:Vector3f) : this.type = {
    x = a.x - b.x
    y = a.y - b.y
    z = a.z - b.z

    this
  }

  def mul(m:Float): this.type = {
    x = x * m
    y = y * m
    z = z * m

    this
  }

  def div(m:Float):this.type = {
    x = x / m
    y = y / m
    z = z / m

    this
  }

  def lengthSquared(): Float = {
    x * x + y * y + z * z
  }

  def length(): Float = {
    Math.sqrt(lengthSquared()).toFloat
  }

  def normalize(): this.type = {
    normalize(this)
  }

  def normalize(v:Vector3f): this.type = {
    val l = v.length()

    x = v.x / l
    y = v.y / l
    z = v.z / l

    this
  }

  def cross(v: Vector3f) : this.type = {
    cross(this, v)
  }

  def cross(v1: Vector3f, v2: Vector3f) : this.type = {
    val tx = v1.y * v2.z - v1.z * v2.y
    val ty = v1.z * v2.x - v1.x * v2.z
    val tz = v1.x * v2.y - v1.y * v2.x

    x = tx; y = ty; z = tz

    this
  }

  def dot(a: Vector3f): Float = {
    a.x * this.x + a.y * this.y + a.z * this.z
  }

  override def write(b: FloatBuffer): Unit = {
    b.put(x).put(y).put(z)
  }

  override def toString: String = {
    "{" + x + ", " + y + ", " + z + "}"
  }

}
