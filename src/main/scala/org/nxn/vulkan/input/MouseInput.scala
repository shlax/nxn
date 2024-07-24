package org.nxn.vulkan.input

import org.lwjgl.glfw.{GLFW, GLFWCursorPosCallbackI, GLFWMouseButtonCallbackI}
import org.lwjgl.system.{MemoryStack, MemoryUtil}
import org.nxn.math.Vector2f
import org.nxn.utils.using.|
import org.nxn.vulkan.GlfwWindow

class MouseInput(val window: GlfwWindow) extends AutoCloseable{
  val cursor: Long = GLFW.glfwCreateStandardCursor(GLFW.GLFW_CROSSHAIR_CURSOR)
  GLFW.glfwSetCursor(window.glfwWindowHandle, cursor)

  private var rotate = false

  private var xOff:Double = 0
  private var yOff:Double = 0

  private var xWin:Double = 0
  private var yWin:Double = 0

  def pull(offset: Vector2f):Boolean = {
    offset.x = xOff.toFloat
    xOff = 0

    offset.y = yOff.toFloat
    yOff = 0

    offset.x != 0f || offset.y != 0f
  }

  private def closeCallback(c:AutoCloseable):Unit = {
    if(c != null) c.close()
  }

  closeCallback(GLFW.glfwSetCursorPosCallback(window.glfwWindowHandle, (win: Long, xpos: Double, ypos: Double) => {
    if (rotate) {
      xOff += xpos - xWin
      yOff += ypos - yWin
      xWin = xpos
      yWin = ypos
    }
  }))

  closeCallback(GLFW.glfwSetMouseButtonCallback(window.glfwWindowHandle, (win: Long, button: Int, action: Int, mods: Int) => {
    if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
      if (action == GLFW.GLFW_PRESS) {
        MemoryStack.stackPush() | { stack =>
          val x = stack.callocDouble(1)
          val y = stack.callocDouble(1)
          GLFW.glfwGetCursorPos(win, x, y)
          xWin = x.get(0)
          yWin = y.get(0)
        }
        GLFW.glfwSetInputMode(win, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)
        rotate = true
      }else if (action == GLFW.GLFW_RELEASE){
        GLFW.glfwSetInputMode(win, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL)
        rotate = false
      }
    }
  }))

  override def close(): Unit = {
    closeCallback(GLFW.glfwSetCursorPosCallback(window.glfwWindowHandle, null))
    closeCallback(GLFW.glfwSetMouseButtonCallback(window.glfwWindowHandle, null))

    GLFW.glfwSetCursor(window.glfwWindowHandle, MemoryUtil.NULL)
    GLFW.glfwDestroyCursor(cursor)
  }

}
