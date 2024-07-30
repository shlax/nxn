package org.nxn.model.skeleton

class ParsedAngle(val from:String, val to:String, val value:Float){
  if(!(from == "X" || from == "Y" || from == "Z")) throw new IllegalArgumentException("from is "+from)
  if(!(to == "X" || to == "Y" || to == "Z")) throw new IllegalArgumentException("to is " + to)
  
}
