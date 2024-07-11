package org.nxn.vulkan

enum TypeLength(val size:Int) {
  case intLength extends TypeLength(4)
  case floatLength extends TypeLength(4)
}
