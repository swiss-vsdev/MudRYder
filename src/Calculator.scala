import com.badlogic.gdx.math.Vector2

class Calculator {
  val tolerance = 8
  var vDir = new Vector2()
  var vXP = new Vector2()

  def isPointInBoundaries(sgmt: Line, point: Vector2): Boolean = {
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

  def distanceToSegment(sgmt: Line, point: Vector2): Double = {
    vDir.set((sgmt.p2x-sgmt.p1x),(sgmt.p2y-sgmt.p1y))
    vXP.set((point.x-sgmt.p1x),(point.y-sgmt.p1y))
    val vectorProduct = vDir.x*vXP.y - vDir.y*vXP.x
    val vDirNorm = math.sqrt(vDir.x * vDir.x + vDir.y * vDir.y)
    val distance = (math.abs(vectorProduct) / vDirNorm)
    distance
  }

  def isPointInSegment(sgmt: Line, point: Vector2): Boolean = {
    if (distanceToSegment(sgmt, point) <= tolerance) {
      if (isPointInBoundaries(sgmt, point)) {
        return true
      }
    }
    false
  }

  def isPointInLine(sgmt: Line, point: Vector2): Boolean = {
    isPointInSegment(sgmt,point)
  }
}
