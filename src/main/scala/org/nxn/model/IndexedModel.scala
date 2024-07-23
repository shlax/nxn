package org.nxn.model

class IndexedModel(val vulkanModel: VulkanModel, indexes:Array[Array[Int]]){

  def lookup(i:Int):Array[Int] = {
    indexes(i)
  }

}
