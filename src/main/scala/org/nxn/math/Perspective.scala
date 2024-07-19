package org.nxn.math

import org.nxn.utils.Dimension

object Perspective{

  def apply(fovy: Float, zNear:Float, zFar:Float, dim:Dimension) : Matrix4f = {
    val aspect = dim.width.toFloat / dim.height.toFloat
    apply(fovy, aspect, zNear, zFar)
  }

  def apply(fovy: Float, aspect:Float, zNear:Float, zFar:Float) : Matrix4f = {
    val tan = Math.tan(fovy * Math.PI / 360d).toFloat // fovy / 2

    val m00 = 1f / (tan * aspect)
    val m11 = 1f / tan
    val m22 = zFar / (zFar - zNear)
    val m23 = - (zFar * zNear) / (zFar - zNear)

    new Matrix4f(m00, 0f,  0f,  0f,
                 0f , m11, 0f,  0f,
                 0f , 0f,  m22, m23,
                 0f , 0f,  1f,  0f)
  }

}
