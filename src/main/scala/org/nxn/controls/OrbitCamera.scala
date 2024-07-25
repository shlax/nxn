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

  val rotationMatrix: Matrix4f = new Matrix4f()
  val viewMatrix: Matrix4f = new Matrix4f(new Vector3f(0, 0, offset)).mulWith(projection)

  def update(point:Vector3f):Unit = {
    for(diff <- mouseInput.pull()){
      val rx = xRotation.add(diff.y * sensitivity)
      val ry = yRotation.add(diff.x * -1 * sensitivity)

      val tmp = new Matrix4f()
      rotationMatrix.rotX(rx).mulThis(tmp.rotY(ry))

      tmp.set(point).mulWith(rotationMatrix)
      viewMatrix.set(new Vector3f(0, 0, offset)).mulThis(tmp).mulWith(projection)
    }
  }

  override def close(): Unit = {
    mouseInput.close()
  }
}
