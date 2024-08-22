package org.nxn.math

enum Axis extends Enum[Axis] {
  case X extends Axis
  case Y extends Axis
  case Z extends Axis

  def rotate(a: Float): Matrix4f = {
    this match {
      case X => Matrix4f.xRot(a)
      case Y => Matrix4f.yRot(a)
      case Z => Matrix4f.zRot(a)
    }
  }

  def rotate(m: Matrix4f, a: Float): Matrix4f = {
    this match {
      case X => m.xRot(a)
      case Y => m.yRot(a)
      case Z => m.zRot(a)
    }
  }

  def apply(v: Vector3f): Float = {
    this match {
      case X => v.x
      case Y => v.y
      case Z => v.z
    }
  }

  def update(v: Vector3f, w: Float): Vector3f = {
    this match {
      case X => v.x = w
      case Y => v.y = w
      case Z => v.z = w
    }
    v
  }

}
