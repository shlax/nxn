package org.nxn.model.skeleton

import org.nxn.math.Vector3f

class ParsedJoint(val name:String, val vec: Vector3f, val angle:String, val binding: Array[ParsedBinding], val subJoints: Array[ParsedJoint])
