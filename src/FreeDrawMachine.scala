import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import typesLibrary.Free

import scala.collection.mutable.ArrayBuffer

class FreeDrawMachine {
  val FreeArray: ArrayBuffer[Free] = ArrayBuffer.empty
  var startPoint: Vector2 = new Vector2()
  var endPoint: Vector2 = new Vector2()
  var lastEndPoint: Vector2 = new Vector2()
  var isMousePressed: Boolean = false
  var FreeCnt : Int = -1
  var SegCnt : Int = 0

  def drawFreeLines(g:GdxGraphics) : Unit = {
    if(endPoint.x != 0.0f && endPoint.y != 0.0f) g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y)
    for(free <- FreeArray){
      for(segment <- free){
        g.setColor(Color.BLACK)
        g.drawLine(segment.p1x,segment.p1y,segment.p2x,segment.p2y)
      }
    }
  }

  def onClick(mode : String, x: Int, y: Int) : Unit = {
    mode match {
      case "RIGHT" => {

      }
      case "LEFT" => {
        val f1 = new Free
        FreeArray.addOne(f1)
        FreeCnt += 1
        if(lastEndPoint.x == endPoint.x && lastEndPoint.y == endPoint.y) endPoint.set(x,y)
        startPoint.set(x, y)
        isMousePressed = true
      }
    }
    }

  def onDrag(x:Int,y:Int):Unit = {
    endPoint.set(x,y)
    SegCnt += 1
    if(SegCnt > 0){
      val seg1 = Line(startPoint.x,startPoint.y,endPoint.x,endPoint.y)
      lastEndPoint.set(endPoint.x,endPoint.y)
      FreeArray(FreeCnt).addOne(seg1)
      SegCnt = 0
      startPoint.set(endPoint.x,endPoint.y)
    }

  }

  def onRelease(mode:String, x:Int,y:Int) : Unit = {
    mode match {
      case "RIGHT" => {

      }
      case "LEFT" => {
        isMousePressed = false

      }
    }
  }

  }

