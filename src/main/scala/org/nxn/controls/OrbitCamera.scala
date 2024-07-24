package org.nxn.controls

import org.nxn.math.{Matrix4f, Vector2f, Vector3f}
import org.nxn.vulkan.GlfwWindow

class OrbitCamera(val window: GlfwWindow, offset:Float, projection: Matrix4f) extends AutoCloseable {

  protected def initMouseInput():MouseInput = {
    new MouseInput(window)
  }

  val mouseInput:MouseInput = initMouseInput()

  val viewMatrix: Matrix4f = new Matrix4f()

  protected val offsetMatrix:Matrix4f = new Matrix4f().update(new Vector3f(0f, 0f, -offset))
  protected val diff = new Vector2f()

  def update():Unit = {
    if(mouseInput.pull(diff)){
      
    }
  }

  override def close(): Unit = {
    mouseInput.close()
  }
}
