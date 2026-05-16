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
  var onMenuClick : Boolean = false
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
    playerMachine.drawMudry(g)
    if(currentMode != "play"){
      playerMachine.sleep()
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

    onMenuClick = modesMachine.onMenuClick(x,y)
    currentMode = modesMachine.currentMode()

    if (button == Input.Buttons.LEFT) {
      println("current mode = " + currentMode)
      currentMode match{
        case "free" => {
          if (!onMenuClick) {
            freeMachine.onClick("LEFT",x,y)
          } else {
            playerMachine.setPos(900, 900)
          }
        }
        case "lines" => {
          if (!onMenuClick) {
            lineMachine.onClick("LEFT",x,y)
          } else {
            playerMachine.setPos(900, 900)
          }
        }
        case "play" => {
          playerMachine.setPos(900, 900)
          playerMachine.awake()
        }
      }

      println("Left button clicked")
    } else {
      /*currentMode match{
        case "free" =>
          modesMachine.modeSwitcher("lines")
        case "lines" =>
          modesMachine.modeSwitcher("free")
        case "play" =>
          modesMachine.modeSwitcher("play")
      }*/
      println("Right button clicked")

    }


  }

  override def onDrag(x: Int, y: Int): Unit = {
    //println("I'm draaaged")
    if (!onMenuClick){
      currentMode match{
        case "free" => freeMachine.onDrag(x,y)
        case "lines" => lineMachine.onDrag(x,y)
        case "play" => {}
      }
    }

  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    if (!onMenuClick) {
      super.onRelease(x, y, button)

      if (button == Input.Buttons.LEFT) {
        currentMode match {
          case "free" => freeMachine.onRelease("LEFT", x, y)
          case "lines" => lineMachine.onRelease("LEFT", x, y)
          case "play" => {}
        }
        println("Left button released")
      } else {
        println("Right button released")
      }
    }
  }
}


