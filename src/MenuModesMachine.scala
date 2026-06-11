import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import scala.collection.mutable.ArrayBuffer

// Manages the mode menu: icon loading, mode switching
class MenuModesMachine {
  private val modes: ArrayBuffer[MenuModes] = ArrayBuffer.empty
  private val icons: ArrayBuffer[BitmapImage] = ArrayBuffer.empty
  private var cm: String = "lines"
  private var dm: String = "physic"
  private var mm: String = "music"
  loadModes()
  private var firstRun: Boolean = true

  // Load icon images for each menu mode (called once)
  def loadIcons(): Unit = {
    if (firstRun) {
      for (mode <- modes) {
        val img = new BitmapImage(s"./icons/${mode.name}.png")
        icons.addOne(img)
      }
      firstRun = false
    }
  }

  // Draw all mode buttons on the top-right of the screen
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

  // Check if the user clicked a menu button
  // Returns true if a button was hit
  def onMenuClick(x: Int, y: Int): Boolean = {
    for (m <- modes) {
      if (math.sqrt((x - m.x) * (x - m.x) + (y - m.y) * (y - m.y)) < m.radius) {
        // Button hit -> switch mode
        modeSwitcher(m.name)
        return true
      }
    }
    false
  }

  // Handle mode toggles (physic/decoration, music/mute) and direct mode selection
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

  // All Menus / Toggles added
  private def loadModes(): Unit = {
    modes.addOne(MenuModes("free"))
    modes.addOne(MenuModes("lines"))
    modes.addOne(MenuModes("play"))
    modes.addOne(MenuModes("eraser"))
    modes.addOne(MenuModes("mop"))
    modes.addOne(MenuModes("return"))
    modes.addOne(MenuModes("save"))
    modes.addOne(MenuModes("load"))
    modes.addOne(MenuModes("physic"))
    modes.addOne(MenuModes("decoration"))
    modes.addOne(MenuModes("music"))
    modes.addOne(MenuModes("musicmute"))
  }
}
