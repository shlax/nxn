package org.nxn.controls

import org.nxn.math.{Angle3f, Matrix4f, Vector3f}
import org.nxn.vulkan.GlfwWindow

class OrbitCamera(val window: GlfwWindow, projection: Matrix4f, sensitivity:Float = 0.005f, scrollStep:Float = 0.25f) extends AutoCloseable {

  protected def initMouseInput():MouseInput = {
    new MouseInput(window)
  }

  val mouseInput:MouseInput = initMouseInput()

  var xRotation: Angle3f = new Angle3f()
  var yRotation: Angle3f = new Angle3f()
  var offset:Float = 0f

  val rotationMatrix: Matrix4f = new Matrix4f()
  val viewMatrix: Matrix4f = new Matrix4f()

  protected def update(point: Vector3f, off: Float, rx: Float, ry: Float): this.type = {
    val tmp = Matrix4f.yRot(ry)
    rotationMatrix.xRot(rx).mulTxM(tmp)

    tmp.set(point).mulMxT(rotationMatrix)
    viewMatrix.set(0f, 0f, off).mulTxM(tmp).mulMxT(projection)

    this
  }

  def update(point:Vector3f):Option[Vector3f] = {
    val diff = mouseInput.pull()
    if(diff.isDefined){
      val df = diff.get

      val rx = xRotation.add(df.y * sensitivity)
      val ry = yRotation.add(-df.x * sensitivity)

      val off = offset + df.z * scrollStep
      offset = off

      update(point, off, rx, ry)
    }else{
      update(point, offset, xRotation(), yRotation())
    }
    diff
  }

  def set(point:Vector3f, off:Float, xRot:Float, yRot:Float):this.type = {
    xRotation.set(xRot)
    yRotation.set(yRot)
    offset = off

    update(point, off, xRot, yRot)
  }

  override def close(): Unit = {
    mouseInput.close()
  }
}
