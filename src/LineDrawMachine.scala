import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import scala.collection.mutable.ArrayBuffer

class LineDrawMachine {
  var LineArray: ArrayBuffer[Line] = ArrayBuffer.empty
  val calc : Calculator = new Calculator
  var startPoint: Vector2 = new Vector2()
  var endPoint: Vector2 = new Vector2()
  var lastEndPoint: Vector2 = new Vector2()
  var isMousePressed: Boolean = false
  var firstRun : Boolean = true

  ArrayEmptyFix()

  private def ArrayEmptyFix() : Unit = {
    if(LineArray.isEmpty) {
      val l1 = PhysicLine(-10000,-10000,-10000,-10000)
      LineArray.addOne(l1)
    }
  }

  def drawLines(g:GdxGraphics, dm : String) : Unit = {
    g.setColor(Color.BLACK)
    if(endPoint.x != 0.0f && endPoint.y != 0.0f) g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y)
    for(line <- LineArray){
      if(line.isInstanceOf[DecoLine] && dm != "play"){
        line.color = Color.BLUE
      } else {
        line.color = Color.BLACK
      }
      line.draw(g)
      //g.setColor(Color.BLACK)
      //g.drawLine(line.p1x,line.p1y,line.p2x,line.p2y)
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

  def onRelease(mode:String, x:Int,y:Int, dm : String) : Unit = {
    mode match {
      case "RIGHT" => {

      }
      case "LEFT" => {
        isMousePressed = false
        endPoint.set(x,y)

        dm match {
          case "physic" => {
            val l1 = PhysicLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y)
            LineArray.addOne(l1)
            lastEndPoint.set(endPoint.x,endPoint.y)
          }
          case "decoration" => {
            val l1 = DecoLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y)
            LineArray.addOne(l1)
            lastEndPoint.set(endPoint.x,endPoint.y)
          }
        }
      }
    }
  }

  def clean(x: Int, y: Int) : Unit = {
    ArrayEmptyFix()
    val toRemove : ArrayBuffer[Line] = ArrayBuffer.empty
    val pixelSquare : ArrayBuffer[Vector2] = ArrayBuffer.empty
    val tolerence : Int = 15

    for(i <- 0 to tolerence){
      for(j <- 0 to tolerence){
        pixelSquare.addOne(new Vector2((x-(tolerence/2))+i,(y-(tolerence/2))+j))
      }
    }
    //println("Clean Time " + LineArray.length)

    for(line <- LineArray){
      for(coordinate <- pixelSquare){
        if (calc.isPointInLine(line,coordinate)){
          if(!toRemove.contains(line))toRemove.addOne(line)
          //println("Clean mee")
        }
      }
    }
    for (line <- toRemove){
      LineArray -= line
      line.destroy()
    }
      endPoint.set(0f, 0f)
      startPoint.set(0f, 0f)
      ArrayEmptyFix()

  }

  def mop() : Unit = {
    for(line <- LineArray){
      line.destroy()
    }
    LineArray.clear()
    ArrayEmptyFix()
  }

}



