package org.nxn.model

import org.nxn.vulkan.memory.ToIntBuffer
import java.nio.IntBuffer

class IndexedModel(vertexes: Array[Vertex], val indexes:Array[IndexedTriangle]) extends VertexModel(vertexes), ToIntBuffer {
  override val indexesCount: Int = indexes.length * 3

  override def toIntBuffer(b: IntBuffer): IntBuffer = {
    for (i <- indexes) i.toIntBuffer(b)
    b
  }

  def indexesStride: Int = {
    indexes.head.size
  }

  def indexesSize: Int = {
    indexes.length * indexesStride
  }

}
