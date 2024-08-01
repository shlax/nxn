package org.nxn.model.skeleton

import org.nxn.model.{Vertex, VertexModel}

object SkinModel{

  def apply(m:VertexModel):SkinModel = {
    new SkinModel(m.vertexes, m.indexesCount)
  }

}

class SkinModel(vertexes: Array[Vertex], override val indexesCount:Int) extends VertexModel(vertexes)
