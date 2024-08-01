package org.nxn.utils

import org.nxn.math.Matrix4f

enum Axis extends Enum[Axis] {
  case X extends Axis
  case Y extends Axis
  case Z extends Axis

  def rotate(a: Float): Matrix4f = {
    if (X.ordinal() == this.ordinal()) Matrix4f.xRot(a)
    else if (Y.ordinal() == this.ordinal()) Matrix4f.yRot(a)
    else Matrix4f.zRot(a) // Z
  }

  def rotate(m:Matrix4f, a:Float):Matrix4f = {
    if(X.ordinal() == ordinal()) m.xRot(a)
    else if(Y.ordinal() == ordinal()) m.yRot(a)
    else m.zRot(a) // Z
  }

}
