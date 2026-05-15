import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import scala.collection.mutable.ArrayBuffer

class LineDrawMachine {
  val LineArray: ArrayBuffer[Line] = ArrayBuffer.empty
  var startPoint: Vector2 = new Vector2()
  var endPoint: Vector2 = new Vector2()
  var lastEndPoint: Vector2 = new Vector2()
  var isMousePressed: Boolean = false

  def drawLines(g:GdxGraphics) : Unit = {
    if(endPoint.x != 0.0f && endPoint.y != 0.0f) g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y)
    for(line <- LineArray){
      g.setColor(Color.BLACK)
      g.drawLine(line.p1x,line.p1y,line.p2x,line.p2y)
    }
  }

  def onClick(mode : String, x: Int, y: Int) : Unit = {
    mode match {
      case "RIGHT" => {

      }
      case "LEFT" => {
        if(lastEndPoint.x == endPoint.x && lastEndPoint.y == endPoint.y) endPoint.set(x,y)
        startPoint.set(x, y)
        isMousePressed = true
      }
    }
  }

  def onDrag(x:Int,y:Int):Unit = {
    endPoint.set(x,y)
  }

  def onRelease(mode:String, x:Int,y:Int) : Unit = {
    mode match {
      case "RIGHT" => {

      }
      case "LEFT" => {
        isMousePressed = false
        endPoint.set(x,y)
        val l1 = Line(startPoint.x,startPoint.y,endPoint.x,endPoint.y)
        LineArray.addOne(l1)
        lastEndPoint.set(endPoint.x,endPoint.y)
      }
    }
  }

}

