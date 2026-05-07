import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

class Game extends DesktopApplication(1920, 1080) {
  val LineArray: ArrayBuffer[Line] = ArrayBuffer.empty
  var startPoint: Vector2 = new Vector2()
  var endPoint: Vector2 = new Vector2()
  var lastEndPoint: Vector2 = new Vector2()
  var isMousePressed: Boolean = false

  override def onInit(): Unit = {
    setTitle("MudRYder")
    new PhysicsScreenBoundaries(getWindowWidth.toFloat, getWindowHeight.toFloat)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.WHITE)
    g.drawFPS(Color.BLACK)
    g.drawSchoolLogo()
    g.setColor(Color.BLACK)
    if(endPoint.x != 0.0f && endPoint.y != 0.0f) g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y)
    for(line <- LineArray){
      g.setColor(Color.BLACK)
      g.drawLine(line.p1x,line.p1y,line.p2x,line.p2y)
    }
  }
  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)

    if (button == Input.Buttons.LEFT) {
      if(lastEndPoint.x == endPoint.x && lastEndPoint.y == endPoint.y) endPoint.set(x,y)
      startPoint.set(x, y)
      Logger.log("Left button clicked")
      isMousePressed = true

    }
    else{
      Logger.log("Right button clicked")
    }


  }

  override def onDrag(x: Int, y: Int): Unit = {
    //println("I'm draaaged")
    endPoint.set(x,y)
  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    super.onRelease(x, y, button)

    if (button == Input.Buttons.LEFT) {
      isMousePressed = false
      endPoint.set(x,y)
      val l1 = Line(startPoint.x,startPoint.y,endPoint.x,endPoint.y)
      LineArray.addOne(l1)
      lastEndPoint.set(endPoint.x,endPoint.y)
      Logger.log("Left button released")
    }
  }
}

object HelloGdx2d {
  def main(args: Array[String]): Unit = {
    new Game().launch()
  }
}
