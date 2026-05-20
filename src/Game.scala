import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.{Color, OrthographicCamera}
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
  var lastMouseClick = 1
  var cam = new OrthographicCamera
  var camX : Int = 1920 / 2
  var camY : Int = 540
  var RDragX : Int = 0
  var RDragY : Int = 0

  override def onInit(): Unit = {
    setTitle("MudRYder")

    //new PhysicsScreenBoundaries(10000f, 10000f)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.WHITE)
    g.drawFPS(Color.BLACK)
    g.drawSchoolLogo()
    g.setColor(Color.BLACK)
    cam = g.getCamera
    //g.drawTransformedPicture(450,450,0,0.05f,img)
    playerMachine.update()
    playerMachine.drawMudry(g)

    if(currentMode != "play"){
      cam.position.set(camX, camY, 0)
      playerMachine.sleep()
    } else {
      cam.position.set(playerMachine.posX, playerMachine.posY, 0)
    }

    cam.update()
    lineMachine.drawLines(g)
    freeMachine.drawFreeLines(g)
    currentMode = modesMachine.currentMode()
    PhysicsWorld.updatePhysics()


    g.resetCamera()
    modesMachine.drawModesMenu(g)

    if(currentMode == "play"){
      cam.position.set(playerMachine.posX, playerMachine.posY, 0)
    } else {
      cam.position.set(camX, camY, 0)
    }
    cam.update()
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    println("x = " + x)

    //var menuObjects : Array[Array[Int]] = Array(Array(20,40))
    lastMouseClick = button //On enregistre si click droit ou gache pour prevent le drag sur clic droit

    super.onClick(x, y, button)
    if (button == Input.Buttons.LEFT) {
      onMenuClick = modesMachine.onMenuClick(x,y)
      currentMode = modesMachine.currentMode()
      println("current mode = " + currentMode)

      //Maintenant que le monde bouge il faut calculer différement l'emplacement de la souris
      // x et y du clic sont relatif à la fenêtre et non au monde, le pixel haut gauche sera toujours à (0,0)
      // indépendament du mouvement dans le monde derrière
      // il faut donc calculer par nous même cette position

      val inWorldClicX : Int = x + (camX - 960)
      val inWorldClicY : Int = y + (camY - 540)

      currentMode match{
        case "free" => {
          if (!onMenuClick) {
            freeMachine.onClick("LEFT",inWorldClicX,inWorldClicY)
          } else {
            playerMachine.setPos(960, 900)
          }
        }
        case "lines" => {
          if (!onMenuClick) {
            lineMachine.onClick("LEFT",inWorldClicX,inWorldClicY)
          } else {
            playerMachine.setPos(960, 900)
          }
        }
        case "play" => {
          playerMachine.setPos(960, 900)
          playerMachine.awake()
        }
      }

      println("Left button clicked")
    } else {
      RDragX = x
      RDragY = y
      println("Right button clicked")

    }


  }

  override def onDrag(x: Int, y: Int): Unit = {
    //println("I'm draaaged")

    val inWorldClicX : Int = x + (camX - 960)
    val inWorldClicY : Int = y + (camY - 540)

    if (!onMenuClick && lastMouseClick == Input.Buttons.LEFT){
      currentMode match{
        case "free" => freeMachine.onDrag(inWorldClicX,inWorldClicY)
        case "lines" => lineMachine.onDrag(inWorldClicX,inWorldClicY)
        case "play" => {}
      }
    } else if (lastMouseClick == Input.Buttons.RIGHT){ // si drag sur clic droit
      println("I'm right draaaged")
      camX = camX - ( x - RDragX)
      camY = camY - ( y - RDragY)
      RDragX = x
      RDragY = y
      cam.position.set(camX, camY, 0)
      cam.update()
    }

  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    super.onRelease(x, y, button)

    val inWorldClicX : Int = x + (camX - 960)
    val inWorldClicY : Int = y + (camY - 540)

    if (!onMenuClick) {

      if (button == Input.Buttons.LEFT) {
        currentMode match {
          case "free" => freeMachine.onRelease("LEFT", inWorldClicX, inWorldClicY)
          case "lines" => lineMachine.onRelease("LEFT", inWorldClicX, inWorldClicY)
          case "play" => {}
        }
        println("Left button released")
      } else {
        println("Right button released")
      }
    }
  }
}


