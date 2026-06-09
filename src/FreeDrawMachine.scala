import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import typesLibrary.Free

import java.io.{FileOutputStream, PrintWriter}
import scala.collection.mutable.ArrayBuffer

// THIS CLASS IS RESPONSIBLE FOR DRAWING THE FREE DRAWINGS, EITHER DECO OR PHYSICS
// IT ALSO CAN REMOVE LINES (MOP, ERASER)
// FINALLY IT SAVES THE FREELINES IN THE SAVE

class FreeDrawMachine {
  val calc: Calculator = new Calculator
  var FreeArray: ArrayBuffer[Free] = ArrayBuffer.empty
  var startPoint: Vector2 = new Vector2()
  var endPoint: Vector2 = new Vector2()
  var lastEndPoint: Vector2 = new Vector2()
  var isMousePressed: Boolean = false
  var FreeCnt: Int = -1
  var firstRun: Boolean = true
  var cursorLoc = new Vector2()

  ArrayEmptyFix()

  // IF THE LINE IS CLOSE TO THE VISIBLE AREA OF THE CAMERA, IT DRAWS IT
  def drawFreeLines(g: GdxGraphics, cm: String, dm: String, camX : Int, camY : Int): Unit = {
    if (dm != "decoration") g.setColor(Color.BLACK) else g.setColor(Color.BLUE)
    if (endPoint.x != 0.0f && endPoint.y != 0.0f) g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y)
    for (free <- FreeArray) {
      for (segment <- free) {
        if (segment.isInstanceOf[DecoLine] && cm != "play") {
          segment.color = Color.BLUE
        } else {
          segment.color = Color.BLACK
        }
        if(segment.p1x >= (camX-2000) && segment.p1x <= (camX+2000) ||
          segment.p2x >= (camX-2000) && segment.p2x <= (camX+2000) ||
          segment.p1y >= (camY-1500) && segment.p1y <= (camY+1500) ||
          segment.p2y >= (camY-1500) && segment.p2y <= (camY+1500) ||
          segment.p1x == -10000 && segment.p2y == -10000){
          segment.draw(g)
        }

      }
    }
  }

  def onClick(mode: String, x: Int, y: Int): Unit = {
    mode match {
      case "RIGHT" => {

      }
      case "LEFT" => {
        val f1 = new Free
        FreeArray.addOne(f1)
        FreeCnt += 1
        if (lastEndPoint.x == endPoint.x && lastEndPoint.y == endPoint.y) endPoint.set(x, y)
        startPoint.set(x, y)
        isMousePressed = true
      }
    }
  }

  def onDrag(x: Int, y: Int, dm: String): Unit = {
    endPoint.set(x, y)
      dm match {
        case "physic" => {
          val seg1 = PhysicLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y)
          lastEndPoint.set(endPoint.x, endPoint.y)
          FreeArray(FreeCnt).addOne(seg1)
          startPoint.set(endPoint.x, endPoint.y)
        }
        case "decoration" => {
          val seg1 = DecoLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y)
          lastEndPoint.set(endPoint.x, endPoint.y)
          FreeArray(FreeCnt).addOne(seg1)
          startPoint.set(endPoint.x, endPoint.y)
        }
      }
  }

  def onRelease(mode: String, x: Int, y: Int): Unit = {
    mode match {
      case "RIGHT" => {

      }
      case "LEFT" => {
        isMousePressed = false

      }
    }
  }

  def clean(x: Int, y: Int): Unit = {
    ArrayEmptyFix()
    cursorLoc.set(x, y)
    val toRemove: ArrayBuffer[Line] = ArrayBuffer.empty

    for (free <- FreeArray) {
      for (segment <- free) {
        if (calc.isPointInSegment(segment, cursorLoc)) {
          if (!toRemove.contains(segment))
            toRemove.addOne(segment)
        }
      }
    }
    for (free <- FreeArray) {
      for (segment <- toRemove) {
        if (free.contains(segment)) {
          free -= segment
          segment.destroy()
        }
      }
    }
  }

  def mop(): Unit = {
    for (free <- FreeArray) {
      for (segmt <- free) {
        segmt.destroy()
      }
    }
    for (free <- FreeArray) {
      free.clear()
    }
    ArrayEmptyFix()
  }

  private def ArrayEmptyFix(): Unit = {
    if (FreeArray.isEmpty) {
      val l1 = PhysicLine(-10000, -10000, -10000, -10000)
      FreeArray.addOne(new ArrayBuffer[Line]().addOne(l1))
    }
  }

  def save(filename: String): Unit = {
    val pw = new PrintWriter(
      new FileOutputStream(s"./saves/$filename.csv", true)
    )
    for (free <- FreeArray) {
      for (sgmt <- free) {
        pw.println(sgmt.getClass.getSimpleName + "," +
          sgmt.p1x + "," + sgmt.p1y + "," + sgmt.p2x + "," + sgmt.p2y)
      }
    }
    pw.close()
  }

}

