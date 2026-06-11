import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import scala.collection.mutable.ArrayBuffer

class MenuModesMachine {
  private val modes: ArrayBuffer[MenuModes] = ArrayBuffer.empty
  private val icons: ArrayBuffer[BitmapImage] = ArrayBuffer.empty
  private var cm: String = "lines"
  private var dm: String = "physic"
  private var mm: String = "music"
  loadModes()
  private var firstRun: Boolean = true

  def loadIcons(): Unit = {
    if (firstRun) {
      for (mode <- modes) {
        val img = new BitmapImage(s"./icons/${mode.name}.png")
        icons.addOne(img)
      }
      firstRun = false
    }
  }

  def drawModesMenu(g: GdxGraphics, width: Int, height: Int): Unit = {
    val startPointH: Int = width - 20
    var startPointW: Int = height + 30
    val radius = 18

    for (i <- modes.indices) {
      if (modes(i).name == "music" || modes(i).name == "musicmute") {
        if (getMusicMode() == modes(i).name) {
          modes(i).x = startPointW
          modes(i).y = startPointH
          modes(i).radius = radius

          if (getMusicMode() == modes(i).name) {
            g.drawCircle(modes(i).x, modes(i).y, modes(i).radius, Color.BLUE)
          }
          g.drawTransformedPicture(startPointW, startPointH, 0, 0.05f, icons(i))
          startPointW += 50
        }
      } else {
        if (modes(i).name == "physic" || modes(i).name == "decoration") {
          if (getDrawMode() == modes(i).name) {
            modes(i).x = startPointW
            modes(i).y = startPointH
            modes(i).radius = radius

            if (getDrawMode() == modes(i).name) {
              g.drawCircle(modes(i).x, modes(i).y, modes(i).radius, Color.BLUE)
            }
            g.drawTransformedPicture(startPointW, startPointH, 0, 0.05f, icons(i))
            startPointW += 50
          }
        } else {
          modes(i).x = startPointW
          modes(i).y = startPointH
          modes(i).radius = radius

          if (currentMode() == modes(i).name) {
            g.drawCircle(modes(i).x, modes(i).y, modes(i).radius, Color.BLUE)
          }
          if (getDrawMode() == modes(i).name) {
            g.drawCircle(modes(i).x, modes(i).y, modes(i).radius, Color.BLUE)
          }
          g.drawTransformedPicture(startPointW, startPointH, 0, 0.05f, icons(i))
          startPointW += 50
        }
      }
    }
  }

  def currentMode(): String = {
    cm
  }

  def getDrawMode(): String = {
    dm
  }

  def getMusicMode(): String = {
    mm
  }

  def onMenuClick(x: Int, y: Int): Boolean = {

    //vérifier pythagore vu que boutons ronds
    // Return true si un bouton du menu a été touché (pour prevent les actions quand clic sur le menu)
    for (m <- modes) {
      if (math.sqrt((x - m.x) * (x - m.x) + (y - m.y) * (y - m.y)) < m.radius) {
        // bouton touché -> Changement de mode
        modeSwitcher(m.name)
        return true
      }
    }
    false
  }

  def modeSwitcher(m: String): Unit = {
    if (m == "physic") {
      if (dm == "physic") {
        dm = "decoration"
      } else {
        dm = "physic"
      }
    } else if (m == "music") {
      if (mm == "music") {
        mm = "musicmute"
      } else {
        mm = "music"
      }
    } else {
      cm = m
    }
  }

  private def loadModes(): Unit = {
    modes.addOne(MenuModes("free", 0, 0, 10))
    modes.addOne(MenuModes("lines", 0, 0, 10))
    modes.addOne(MenuModes("play", 0, 0, 10))
    modes.addOne(MenuModes("eraser", 0, 0, 10))
    modes.addOne(MenuModes("mop", 0, 0, 10))
    modes.addOne(MenuModes("return", 0, 0, 10))
    modes.addOne(MenuModes("save", 0, 0, 10))
    modes.addOne(MenuModes("load", 0, 0, 10))
    modes.addOne(MenuModes("physic", 0, 0, 10))
    modes.addOne(MenuModes("decoration", 0, 0, 10))
    modes.addOne(MenuModes("music", 0, 0, 10))
    modes.addOne(MenuModes("musicmute", 0, 0, 10))
  }
}
