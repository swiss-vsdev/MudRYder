import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

class Game extends DesktopApplication(1920, 1080) {
  val lineMachine = new LineDrawMachine
  val modesMachine = new DrawingModesMachine

  override def onInit(): Unit = {
    setTitle("MudRYder")
    new PhysicsScreenBoundaries(getWindowWidth.toFloat, getWindowHeight.toFloat)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.WHITE)
    g.drawFPS(Color.BLACK)
    g.drawSchoolLogo()
    g.setColor(Color.BLACK)
    lineMachine.drawLines(g)
    modesMachine.drawModesMenu(g)
  }
  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)

    if (button == Input.Buttons.LEFT) {
      lineMachine.onClick("LEFT",x,y)
      println("Left button clicked")
    }
    else{
      lineMachine.onClick("RIGHT",x,y)
      println("Right button clicked")
    }


  }

  override def onDrag(x: Int, y: Int): Unit = {
    //println("I'm draaaged")
    lineMachine.onDrag(x,y)
  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    super.onRelease(x, y, button)

    if (button == Input.Buttons.LEFT) {
      lineMachine.onRelease("LEFT",x,y)
      println("Left button released")
    } else {
      println("Right button released")
    }
  }
}


