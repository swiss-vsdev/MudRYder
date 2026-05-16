import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

import scala.collection.mutable.ArrayBuffer

class Game extends DesktopApplication(1920, 1080){
  val lineMachine = new LineDrawMachine
  val modesMachine = new DrawingModesMachine
  val freeMachine = new FreeDrawMachine
  val playerMachine = new MudryMachine
  var currentMode = ""

  override def onInit(): Unit = {
    setTitle("MudRYder")
    new PhysicsScreenBoundaries(getWindowWidth.toFloat, getWindowHeight.toFloat)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.WHITE)
    g.drawFPS(Color.BLACK)
    g.drawSchoolLogo()
    g.setColor(Color.BLACK)
    //g.drawTransformedPicture(450,450,0,0.05f,img)
    playerMachine.update()
    if(currentMode == "play"){
      playerMachine.drawMudry(g)
    }
    lineMachine.drawLines(g)
    freeMachine.drawFreeLines(g)
    modesMachine.drawModesMenu(g)
    currentMode = modesMachine.currentMode()
    PhysicsWorld.updatePhysics()
  }

  /**/

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    //var menuObjects : Array[Array[Int]] = Array(Array(20,40))
    super.onClick(x, y, button)

    modesMachine.onMenuClick(x,y)

    //if (x )

    if (button == Input.Buttons.LEFT) {
      currentMode match{
        case "free" => freeMachine.onClick("LEFT",x,y)
        case "lines" => lineMachine.onClick("LEFT",x,y)
        case "play" => {
          playerMachine.setPos(900, 900)
        }
      }

      println("Left button clicked")
    } else {
      currentMode match{
        case "free" =>
          modesMachine.modeSwitcher("lines")
        case "lines" =>
          modesMachine.modeSwitcher("free")
        case "play" =>
          modesMachine.modeSwitcher("play")
      }
      println("Right button clicked")

    }


  }

  override def onDrag(x: Int, y: Int): Unit = {
    //println("I'm draaaged")
    currentMode match{
      case "free" => freeMachine.onDrag(x,y)
      case "lines" => lineMachine.onDrag(x,y)
      case "play" => lineMachine.onDrag(x,y)
    }
  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    super.onRelease(x, y, button)

    if (button == Input.Buttons.LEFT) {
      currentMode match{
        case "free" => freeMachine.onRelease("LEFT",x,y)
        case "lines" => lineMachine.onRelease("LEFT",x,y)
        case "play" => lineMachine.onRelease("LEFT",x,y)
      }
      println("Left button released")
    } else {
      println("Right button released")
    }
  }
}


