package org.nxn.model

class IndexedModel(val vulkanModel: VulkanModel, indexes:Array[Array[Int]]){

  def apply(i:Int):Array[VulkanVertex] = {
    indexes(i).map( i => vulkanModel.vertexes(i) )
  }

}
