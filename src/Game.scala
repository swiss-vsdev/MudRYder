import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.{GdxGraphics, ScreenManager}
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.{Color, OrthographicCamera}
import com.badlogic.gdx.math.Vector2

import java.util.Calendar

class Game extends DesktopApplication(1920, 1080) {
  val lineMachine = new LineDrawMachine
  val modesMachine = new DrawingModesMachine
  val freeMachine = new FreeDrawMachine
  var playerMachine : MudryMachine = _
  val uSure = new AreYouSureWindow
  val saveWin = new SavingWindow
  val walkman = new MusicPlayer
  val s = new ScreenManager
  val s1 = new ScreenManager
  val startTime = System.currentTimeMillis()
  val l = PhysicLine(0, 100, 1920, 100)
  val l2 = PhysicLine(0, 1000, 1920, 100)
  var onMenuClick: Boolean = false
  var iAmClicked: Boolean = false
  var currentMode = ""
  var lastMouseClick = 1
  var cam = new OrthographicCamera
  var camX: Int = 1920 / 2
  var camY: Int = 540
  var RDragX: Int = 0
  var RDragY: Int = 0
  var lastMode = ""
  var currentX: Int = 0
  var currentY: Int = 0
  var stableXOfset: Int = 0
  var stableYOfset: Int = 0
  var wasMopping: Boolean = false
  var drawMode: String = "physic"
  var firstRun = true
  var isSaving = false
  var savefile = ""
  var saveTime : Long = _

  override def onInit(): Unit = {
    playerMachine = new MudryMachine("rider", new Vector2(960, 900), 20f, 0f, 0f, 0.001f)
    camX = playerMachine.posX.toInt
    camY = playerMachine.posY.toInt
    setTitle("MudRYder")
    modesMachine.loadIcons()
    s.registerScreen(classOf[SplashScreenWindow])
    s1.registerScreen(classOf[LoadWindow])
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    walkman.play(currentMode)
    if (System.currentTimeMillis() - startTime < 3000) {
      s.render(g)
    } else {
      if (firstRun) {
        l.destroy()
        l2.destroy()
        playerMachine.setPos(camX, camY)
      }
      if (currentMode == "load") {
        camX = 1920 / 2
        camY = 540
        cam.position.set(camX, camY, 0)
        cam.update()
        s1.render(g)
      } else {
        firstRun = false
        g.clear(Color.WHITE)
        g.setColor(Color.BLACK)

      cam = g.getCamera
      playerMachine.update()
      playerMachine.draw(g)

      if (currentMode != "play") {
        playerMachine.sleep()
        playerMachine.angle = 0
      }

      lineMachine.drawLines(g, modesMachine.currentMode(), modesMachine.getDrawMode())
      freeMachine.drawFreeLines(g, modesMachine.currentMode(), modesMachine.getDrawMode())

        lastMode = currentMode
        currentMode = modesMachine.currentMode()
        PhysicsWorld.updatePhysics()

        g.resetCamera()
        modesMachine.drawModesMenu(g, g.getScreenHeight, 0)
        if (currentMode == "mop") {
          uSure.drawWindow(g)
        }
        if (isSaving && currentMode != "mop" &&
          (System.currentTimeMillis() - saveTime) < 5000){
          saveWin.drawWindow(g,savefile)
        }

      if (currentMode == "play") {
        cam.position.set(playerMachine.posX, playerMachine.posY, 0)
      } else {
        cam.position.set(camX, camY, 0)
      }
      cam.update()
      if (currentMode == "play"){
        g.drawString(5, 50, s"X : ${playerMachine.posX.toInt}")
        g.drawString(5, 35, s"Y : ${playerMachine.posY.toInt}")
      } else {
        g.drawString(5, 50, s"X : ${camX}")
        g.drawString(5, 35, s"Y : ${camY}")
      }
      g.drawFPS(Color.BLACK)
      g.drawSchoolLogo()
    }
  }
}

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    currentX = x
    currentY = y

    //var menuObjects : Array[Array[Int]] = Array(Array(20,40))
    lastMouseClick = button //On enregistre si click droit ou gache pour prevent le drag sur clic droit

    super.onClick(x, y, button)

    if (button == Input.Buttons.LEFT) {
      onMenuClick = modesMachine.onMenuClick(x + stableXOfset, y - stableYOfset)
      currentMode = modesMachine.currentMode()
      //println("current mode = " + currentMode)

      //Maintenant que le monde bouge il faut calculer différement l'emplacement de la souris
      // x et y du clic sont relatif à la fenêtre et non au monde, le pixel haut gauche sera toujours à (0,0)
      // indépendament du mouvement dans le monde derrière
      // il faut donc calculer par nous même cette position

      val inWorldClicX: Int = x + (camX - 960)
      val inWorldClicY: Int = y + (camY - 540)

      currentMode match {
        case "free" => {
          if (!onMenuClick) {
            freeMachine.onClick("LEFT", inWorldClicX, inWorldClicY)
          } else {
            playerMachine.setPos(960, 900)
          }
        }
        case "lines" => {
          if (!onMenuClick) {
            lineMachine.onClick("LEFT", inWorldClicX, inWorldClicY)
          } else {
            playerMachine.setPos(960, 900)
          }
        }
        case "play" => {
          playerMachine.setPos(960, 900)
          playerMachine.awake()
        }
        case "eraser" => {
          playerMachine.setPos(960, 900)
          iAmClicked = true
          freeMachine.clean(inWorldClicX, inWorldClicY)
          lineMachine.clean(inWorldClicX, inWorldClicY)
        }
        case "mop" => {
          uSure.onClick(x, y)
          wasMopping = true
          if (uSure.getAnwser() == "yes") {
            freeMachine.mop()
            lineMachine.mop()
            modesMachine.modeSwitcher("lines")
            lineMachine.onClick("LEFT", -100000, -100000)
            lineMachine.endPoint.set(0f, 0f)
            lineMachine.startPoint.set(0f, 0f)
          } else if (uSure.getAnwser() == "no") {
            modesMachine.modeSwitcher("lines")
            lineMachine.onClick("LEFT", -100000, -100000)
            lineMachine.endPoint.set(0f, 0f)
            lineMachine.startPoint.set(0f, 0f)
          }
        }
        case "return" => {
          camX = playerMachine.posX.toInt
          camY = playerMachine.posY.toInt
          modesMachine.modeSwitcher("lines")
        }
        case "save" => {
          isSaving = true
          val c: Calendar = Calendar.getInstance()
          val fn: String = "save_" +
            s"${c.get(Calendar.DAY_OF_YEAR)}" +
            s"${c.get(Calendar.HOUR_OF_DAY)}" +
            s"${c.get(Calendar.MINUTE)}" +
            s"${c.get(Calendar.SECOND)}"

          lineMachine.save(fn)
          freeMachine.save(fn)
          savefile = fn + ".csv"
          saveTime = System.currentTimeMillis()
          println(s"Game saved : $fn.csv")
          modesMachine.modeSwitcher("lines")
        }
        case "load" => {
          s1.getActiveScreen match {
            case lw: LoadWindow => {
              lw.onClick(x, y)
              //println(lw.getAnwser())
              if(lw.getAnwser() == "load"){
                lineMachine.mop()
                freeMachine.mop()
                lineMachine.load(lw.getSelection())
                modesMachine.modeSwitcher("lines")
                lineMachine.onClick("LEFT", -100000, -100000)
                lineMachine.endPoint.set(0f, 0f)
                lineMachine.startPoint.set(0f, 0f)
              }
              if(lw.getAnwser() == "cancel"){
                modesMachine.modeSwitcher("lines")
                lineMachine.onClick("LEFT", -100000, -100000)
                lineMachine.endPoint.set(0f, 0f)
                lineMachine.startPoint.set(0f, 0f)
              }
            }
            case _ =>
          }
        }
        case _ => {
          playerMachine.setPos(960, 900)
        }
      }

      //println("Left button clicked")
    } else {
      RDragX = x
      RDragY = y
      if (currentMode == "play") {
        modesMachine.modeSwitcher("lines")
        playerMachine.setPos(960, 900)
        camX = playerMachine.posX.toInt
        camY = playerMachine.posY.toInt
      }
      //println("Right button clicked")
    }


  }

  override def onDrag(x: Int, y: Int): Unit = {
    //println("I'm draaaged")
    currentX = x
    currentY = y
    val physicMode: String = modesMachine.getDrawMode()

    val inWorldClicX: Int = x + (camX - 960)
    val inWorldClicY: Int = y + (camY - 540)

    if (!onMenuClick && lastMouseClick == Input.Buttons.LEFT) {
      currentMode match {
        case "free" => freeMachine.onDrag(inWorldClicX, inWorldClicY, physicMode)
        case "lines" => if (!wasMopping) lineMachine.onDrag(inWorldClicX, inWorldClicY)
        case "play" => {}
        case "eraser" => {
          freeMachine.clean(inWorldClicX, inWorldClicY)
          lineMachine.clean(inWorldClicX, inWorldClicY)
          //println("Draaaaaag")
        }
        case _ => {}
      }
    } else if (lastMouseClick == Input.Buttons.RIGHT && currentMode != "load") { // si drag sur clic droit
      //println("I'm right draaaged")
      camX = camX - (x - RDragX)
      camY = camY - (y - RDragY)
      RDragX = x
      RDragY = y
      cam.position.set(camX, camY, 0)
      cam.update()
    }

  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    currentX = x
    currentY = y
    val physicMode: String = modesMachine.getDrawMode()

    super.onRelease(x, y, button)

    val inWorldClicX: Int = x + (camX - 960)
    val inWorldClicY: Int = y + (camY - 540)

    if (!onMenuClick) {
      super.onRelease(x, y, button)
      lineMachine.firstRun = false
      freeMachine.firstRun = false

      if (button == Input.Buttons.LEFT) {
        currentMode match {
          case "free" => freeMachine.onRelease("LEFT", inWorldClicX, inWorldClicY)
          case "lines" => if (!wasMopping) {
            lineMachine.onRelease("LEFT", inWorldClicX, inWorldClicY, physicMode)
          } else {
            wasMopping = false
          }
          case "play" => {}
          case "eraser" => iAmClicked = false
          case _ => {}
        }
        //println("Left button released")
      } else {
        //println("Right button released")
      }
    }
  }
}


