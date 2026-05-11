import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import scala.collection.mutable.ArrayBuffer

class DrawingModesMachine {
  private var cm : String = "lines"
  private var firstRun : Boolean = true

  val modes: ArrayBuffer[DrawingModes] = ArrayBuffer.empty
  loadModes()
  val icons: ArrayBuffer[BitmapImage] = ArrayBuffer.empty

  private def loadModes() : Unit = {
    modes.addOne(DrawingModes("free"))
    modes.addOne(DrawingModes("lines"))
  }

  private def loadIcons() : Unit = {
    if(firstRun){
      for (mode <- modes) {
        val img = new BitmapImage(s"./icons/${mode.name}.png")
        icons.addOne(img)
      }
      firstRun = false
    }
  }

  def currentMode() : String = {
    return cm
  }

  private def modeSwitcher(m:String) : Unit = {
    cm = m
  }

  def drawModesMenu(g : GdxGraphics): Unit = {
    loadIcons()
    val startPointH : Int = g.getScreenHeight - 20
    var startPointW : Int = 30
    for(i <- modes.indices){
      if(currentMode() == modes(i).name){
        g.drawCircle(startPointW,startPointH,18,Color.BLUE)
      }
      g.drawTransformedPicture(startPointW,startPointH,0,0.05f,icons(i))
      startPointW += 50
    }
  }
}
