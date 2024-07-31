package org.nxn.model.skeleton

class InterpolatedJoint(val parent:RotatingJoint, val interpolations:Array[InterpolatedAngle],
                          vertexes:Array[SkinVertex], subJoints:Array[AbstractJoint]) extends AbstractJoint(vertexes, subJoints){

}
