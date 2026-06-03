import com.badlogic.gdx.math.Vector2

class Calculator {
  def isPointInBoundaries(sgmt: Line, point: Vector2): Boolean = {
    if ((math.min(sgmt.p1x, sgmt.p2x)) <= point.x && point.x <= (math.max(sgmt.p1x, sgmt.p2x))) {
      if ((math.min(sgmt.p1y, sgmt.p2y)) <= point.y && point.y <= (math.max(sgmt.p1y, sgmt.p2y))) {
        return true
      }
    }
    false
  }

  def distanceToSegment(sgmt: Line, point: Vector2): Double = {
    val vDir = new Vector2((sgmt.p2x-sgmt.p1x),(sgmt.p2y-sgmt.p1y))
    val vXP = new Vector2((point.x-sgmt.p1x),(point.y-sgmt.p1y))
    val vectorProduct = vDir.x*vXP.y - vDir.y*vXP.x
    val vDirNorm = math.sqrt(math.pow(vDir.x,2) + math.pow(vDir.y,2))
    val distance = (math.abs(vectorProduct) / vDirNorm)
    distance
  }

  def isPointInSegment(sgmt: Line, point: Vector2): Boolean = {
    if (isPointInBoundaries(sgmt, point)) {
      if (distanceToSegment(sgmt, point) <= 5) {
        return true
      }
    }
    false
  }

  def isPointInLine(sgmt: Line, point: Vector2): Boolean = {
    isPointInSegment(sgmt,point)
  }
}
