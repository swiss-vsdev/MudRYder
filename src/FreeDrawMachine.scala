import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import typesLibrary.Free

import scala.collection.mutable.ArrayBuffer

class FreeDrawMachine {
  val calc : Calculator = new Calculator
  var FreeArray: ArrayBuffer[Free] = ArrayBuffer.empty
  var startPoint: Vector2 = new Vector2()
  var endPoint: Vector2 = new Vector2()
  var lastEndPoint: Vector2 = new Vector2()
  var isMousePressed: Boolean = false
  var FreeCnt : Int = -1
  var SegCnt : Int = 0
  var firstRun : Boolean = true

  ArrayEmptyFix()

  private def ArrayEmptyFix() : Unit = {
      val l1 = Line(-10000,-10000,-10000,-10000)
      FreeArray.addOne(new ArrayBuffer[Line]().addOne(l1))
  }

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

  def clean(x: Int, y: Int) : Unit = {
    ArrayEmptyFix()
    val toRemove : ArrayBuffer[Line] = ArrayBuffer.empty
    val pixelSquare : ArrayBuffer[Vector2] = ArrayBuffer.empty
    val tolerence : Int = 12

    for(i <- 0 to tolerence){
      for(j <- 0 to tolerence){
        pixelSquare.addOne(new Vector2((x-(tolerence/2))+i,(y-(tolerence/2))+j))
      }
    }

    for(free <- FreeArray){
      for(segment <- free){
        for(coordinate <- pixelSquare){
          if (calc.isPointInSegment(segment,coordinate)){
            if(!toRemove.contains(segment)) toRemove.addOne(segment)
            //println("Clean mee")
          }
        }
      }
    }
    for(free <- FreeArray){
      for (segment <- toRemove){
        if(free.contains(segment)){
          free -= segment
          segment.destroy()
        }
      }
    }
  }

  def mop() : Unit = {
   for(free <- FreeArray) {
     for(segmt <- free){
       segmt.destroy()
     }
   }
    for(free <- FreeArray) {
      free.clear()
    }
    ArrayEmptyFix()
  }

}

