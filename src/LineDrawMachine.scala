import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import java.io.{FileOutputStream, PrintWriter}
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class LineDrawMachine {
  var LineArray: ArrayBuffer[Line] = ArrayBuffer.empty
  val calc : Calculator = new Calculator
  var startPoint: Vector2 = new Vector2()
  var endPoint: Vector2 = new Vector2()
  var lastEndPoint: Vector2 = new Vector2()
  var isMousePressed: Boolean = false
  var firstRun : Boolean = true
  var cursorLoc = new Vector2()

  ArrayEmptyFix()

  private def ArrayEmptyFix() : Unit = {
    if(LineArray.isEmpty) {
      val l1 = PhysicLine(-10000,-10000,-10000,-10000)
      LineArray.addOne(l1)
    }
  }

  def drawLines(g:GdxGraphics, cm : String, dm : String) : Unit = {
    if(dm != "decoration") g.setColor(Color.BLACK) else g.setColor(Color.BLUE)
    if(endPoint.x != 0.0f && endPoint.y != 0.0f) g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y)
    for(line <- LineArray){
      if(line.isInstanceOf[DecoLine] && cm != "play"){
        line.color = Color.BLUE
      } else {
        line.color = Color.BLACK
      }
      line.draw(g)
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
    cursorLoc.set(x,y)
    val toRemove : ArrayBuffer[Line] = ArrayBuffer.empty

    for(line <- LineArray){
        if (calc.isPointInLine(line,cursorLoc)){
          if(!toRemove.contains(line))
            toRemove.addOne(line)
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

  def save(filename:String) : Unit = {
    val pw = new PrintWriter(
      new FileOutputStream(s"./saves/$filename.csv",true)
    )
    for(line <- LineArray){
      pw.println(line.getClass.getSimpleName + "," +
        line.p1x + "," + line.p1y + "," + line.p2x + "," + line.p2y)
    }
    pw.close()
  }

  def load(fp : String) : Unit = {
    val src = Source.fromFile(s"./saves/$fp")
    val lines = src.getLines().toArray
    for(line <- lines){
      val a = line.split(",")
      if(line.contains("PhysicLine")){
        LineArray.addOne(PhysicLine(a(1).toFloat,a(2).toFloat,a(3).toFloat,a(4).toFloat))
      }
      if(line.contains("DecoLine")){
        LineArray.addOne(DecoLine(a(1).toFloat,a(2).toFloat,a(3).toFloat,a(4).toFloat))
      }
    }
  }
}



