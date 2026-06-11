import com.badlogic.gdx.math.Vector2

// Geometry calculations for the eraser: point-to-segment distance and hit testing

class Calculator {
  private val tolerance = 8 //Mouse tolerance, nbr of pixels around the mouse
  private val vDir = new Vector2() //Vecteur Directeur
  private val vXP = new Vector2() // Vecteur entre le point X et le point P (P = Mouse location)

  //The logic is the same as a Segment, but to make it consistant, we added this fonction that equals isPointInSegment
  def isPointInLine(sgmt: Line, point: Vector2): Boolean = {
    isPointInSegment(sgmt, point)
  }

  //The mouse is in the segment : if the distance between it is in tolerance and if it is on the said segment
  def isPointInSegment(sgmt: Line, point: Vector2): Boolean = {
    if (distanceToSegment(sgmt, point) <= tolerance) {
      if (isPointInBoundaries(sgmt, point)) {
        return true
      }
    }
    false
  }

  //Returns true if the point is part of a said segment
  private def isPointInBoundaries(sgmt: Line, point: Vector2): Boolean = {
    val minX = math.min(sgmt.p1x, sgmt.p2x) - tolerance
    val maxX = math.max(sgmt.p1x, sgmt.p2x) + tolerance
    val minY = math.min(sgmt.p1y, sgmt.p2y) - tolerance
    val maxY = math.max(sgmt.p1y, sgmt.p2y) + tolerance

    if (minX <= point.x && point.x <= maxX) {
      if (minY <= point.y && point.y <= maxY) {
        return true
      }
    }
    false
  }

  //Returns the distance between the mouse and the vector
  private def distanceToSegment(sgmt: Line, point: Vector2): Double = {
    vDir.set((sgmt.p2x - sgmt.p1x), (sgmt.p2y - sgmt.p1y))
    vXP.set((point.x - sgmt.p1x), (point.y - sgmt.p1y))
    val vectorProduct = vDir.x * vXP.y - vDir.y * vXP.x
    var vDirNorm = math.sqrt(vDir.x * vDir.x + vDir.y * vDir.y)
    if (vDirNorm == 0) vDirNorm = Double.MinPositiveValue //Removing the issue of dividing by zero
    val distance = (math.abs(vectorProduct) / vDirNorm)
    distance
  }
}
