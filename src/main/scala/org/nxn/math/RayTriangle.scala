package org.nxn.math

class RayTriangle {

  private val e1 = new Vector3f(0,0,0)
  private val e2 = new Vector3f(0,0,0)

  private val vp = new Vector3f(0,0,0)
  private val vq = new Vector3f(0,0,0)
  private val vt = new Vector3f(0,0,0)

  /** https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm */
  def intersect(r: Ray3f, t: Triangle3f): Float = {
    e1.sub(t.b, t.a)
    e2.sub(t.c, t.a)

    vp.cross(r.dir, e2)

    val det = e1.dot(vp)
    if (det == 0) Float.NaN
    else {
      val iDet = 1f / det

      vt.sub(r.point, t.a)

      val u = vt.dot(vp) * iDet
      if (u < 0 || u > 1) Float.NaN
      else {
        vq.cross(vt, e1)

        val v = r.dir.dot(vq) * iDet
        if (v < 0 || u + v > 1) Float.NaN
        else e2.dot(vq) * iDet
      }
    }
  }

}
