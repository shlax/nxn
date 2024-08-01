package org.nxn.model.skeleton

import org.nxn.math.{Angle3f, Matrix4f, Vector3f}
import org.nxn.utils.Axis

class RotatingJoint(val point: Vector3f, vertexes:Array[SkinVertex], subJoints:Array[AbstractJoint]) extends AbstractJoint(vertexes, subJoints){
  val x: Angle3f = new Angle3f()
  val y: Angle3f = new Angle3f()
  val z: Angle3f = new Angle3f()

  def angle(a:Axis): Angle3f = {
    a match {
      case Axis.X => x
      case Axis.Y => y
      case Axis.Z => z
    }
  }

  def apply(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit = {
    val tmp = Matrix4f.zRot(z())
    val rot = Matrix4f.yRot(y()).mulMxT(tmp).mulTxM(tmp.xRot(x()))

    tmp.set(point).mulMxT(rot).mulMxT(modelMatrix)
    rot.mulMxT(normalMatrix)

    for(s <- subJoints) s(tmp, rot)
  }

}
