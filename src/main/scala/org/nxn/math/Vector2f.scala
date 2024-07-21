package org.nxn.math

import org.nxn.vulkan.memory.ToBuffer

import java.nio.FloatBuffer

class Vector2f(var x : Float, var y : Float) extends ToBuffer{

  def add(a: Vector2f, b: Vector2f): this.type = {
    x = a.x + b.x
    y = a.y + b.y

    this
  }

  def add(b: Vector2f): this.type = {
    x += b.x
    y += b.y

    this
  }

  def sub(b: Vector2f): this.type = {
    x -= b.x
    y -= b.y

    this
  }

  def sub(a: Vector2f, b: Vector2f): this.type = {
    x = a.x - b.x
    y = a.y - b.y

    this
  }

  def mul(m: Float): this.type = {
    x = x * m
    y = y * m

    this
  }

  def div(m: Float): this.type = {
    x = x / m
    y = y / m

    this
  }

  def lengthSquared(): Float = {
    x * x + y * y
  }

  def length(): Float = {
    Math.sqrt(lengthSquared()).toFloat
  }

  def normalize(): this.type = {
    normalize(this)
  }

  def normalize(v: Vector2f): this.type = {
    val l = v.length()

    x = v.x / l
    y = v.y / l

    this
  }

  def dot(a: Vector2f): Float = {
    a.x * this.x + a.y * this.y
  }

  override def write(b:FloatBuffer):FloatBuffer = {
    b.put(x).put(y)
  }
  
  override def toString: String = {
    "{" + x + ", " + y + "}"
  }

}
