import com.badlogic.gdx.math.Vector2

class Calculator {
  def areColinear(v1 : Vector2, v2 : Vector2) : Boolean = {
    val op = ((v1.x * v2.y) - (v1.y * v2.x))
    //println(op)
    if (op == 0) true else false
  }

  def vectorProduct2D(v1 : Vector2, v2 : Vector2) : Double = {
    val op = ((v1.x * v2.y) - (v1.y * v2.x))
    op
  }

  def magnitude(v: Vector2): Double = {
    val sum : Double = (math.pow(v.x, 2)) + (math.pow(v.y, 2))
    math.sqrt(sum)
  }

  def getVectorFromLine(l :Line) : Vector2 = {
    val x : Float = l.p2x - l.p1x
    val y : Float = l.p2y - l.p1y
    new Vector2(x,y)
  }

  def getVectorLinePoint(l: Line, p: Vector2): Vector2 = {
    val x: Float = p.x - l.p1x
    val y: Float = p.y - l.p1y
    new Vector2(x, y)
  }

  def isPointInSegment(sgmt: Line, point : Vector2) : Boolean = {
    val sgmtVector = getVectorFromLine(sgmt)
    val linePointVector = getVectorLinePoint(sgmt,point)
    val sgmtMagnitude = magnitude(sgmtVector)
    val pointMagnitude = magnitude(linePointVector)

    if (areColinear(sgmtVector,linePointVector)){
      if(pointMagnitude < sgmtMagnitude || pointMagnitude == sgmtMagnitude){
         true
      } else {
         false
      }
    } else {
       false
    }
  }

  def isPointInLine(sgmt: Line, point : Vector2) : Boolean = {
    val sgmtVector = getVectorFromLine(sgmt)
    val linePointVector = getVectorLinePoint(sgmt,point)
    val vectorProd = vectorProduct2D(sgmtVector,linePointVector)

    //println(vectorProd)
    if (vectorProd >= -200 && vectorProd <= 200){
      true
    } else {
      false
    }
  }
}
