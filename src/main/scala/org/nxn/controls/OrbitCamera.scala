package org.nxn.controls

import org.nxn.math.{Angle3f, Matrix4f, Vector3f}
import org.nxn.vulkan.GlfwWindow

class OrbitCamera(val window: GlfwWindow, offset:Float, projection: Matrix4f, sensitivity:Float = 0.005f) extends AutoCloseable {

  protected def initMouseInput():MouseInput = {
    new MouseInput(window)
  }

  val mouseInput:MouseInput = initMouseInput()

  var xRotation: Angle3f = new Angle3f()
  var yRotation: Angle3f = new Angle3f()

  def update(point:Vector3f):Unit = {
    val diff = mouseInput.pull()
    if(diff.isDefined){
      val df = diff.get
      val rx = xRotation.add(df.y * sensitivity)
      val ry = yRotation.add(-df.x * sensitivity)

      update(point, rx, ry)
    }else{
      update(point, xRotation(), yRotation())
    }
  }

  val rotationMatrix: Matrix4f = new Matrix4f()
  val viewMatrix: Matrix4f = new Matrix4f(0f, 0f, offset).mulWith(projection)

  protected def update(point:Vector3f, rx:Float, ry:Float):Unit = {
    val tmp = Matrix4f.yRot(ry)
    rotationMatrix.xRot(rx).mulThis(tmp)

    tmp.set(point).mulWith(rotationMatrix)
    viewMatrix.set(0f, 0f, offset).mulThis(tmp).mulWith(projection)
  }

  override def close(): Unit = {
    mouseInput.close()
  }
}
