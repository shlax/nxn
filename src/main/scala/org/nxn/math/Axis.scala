package org.nxn.math

enum Axis(rot: Float => Matrix4f, rotMat: (Matrix4f, Float) => Matrix4f,
          get: Vector3f => Float, set: (Vector3f, Float) => Unit) extends Enum[Axis] {

  case X extends Axis(a => Matrix4f.xRot(a), (m,a) => m.xRot(a), v => v.x, (v,w) => v.x = w)
  case Y extends Axis(a => Matrix4f.yRot(a), (m,a) => m.yRot(a), v => v.y, (v,w) => v.y = w)
  case Z extends Axis(a => Matrix4f.zRot(a), (m,a) => m.zRot(a), v => v.z, (v,w) => v.z = w)

  inline def rotate(a: Float): Matrix4f = rot(a)
  inline def rotate(m: Matrix4f, a: Float): Matrix4f = rotMat(m, a)

  inline def apply(v: Vector3f): Float = get(v)

  inline def update(vec: Vector3f, w: Float): Vector3f = {
    set(vec, w)
    vec
  }

}
