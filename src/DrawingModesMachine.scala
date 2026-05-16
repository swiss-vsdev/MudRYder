import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import scala.collection.mutable.ArrayBuffer

class DrawingModesMachine {
  private var cm : String = "lines"
  private var firstRun : Boolean = true

  val modes: ArrayBuffer[MenuModes] = ArrayBuffer.empty
  loadModes()
  val icons: ArrayBuffer[BitmapImage] = ArrayBuffer.empty

  private def loadModes() : Unit = {
    modes.addOne(MenuModes("free", 0, 0, 10))
    modes.addOne(MenuModes("lines", 0, 0, 10))
    modes.addOne(MenuModes("play", 0, 0, 10))
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

  def modeSwitcher(m:String) : Unit = {
    cm = m
  }

  def drawModesMenu(g : GdxGraphics): Unit = {
    loadIcons()
    val startPointH : Int = g.getScreenHeight - 20
    var startPointW : Int = 30
    val radius = 18
    for(i <- modes.indices){
      modes(i).x = startPointW
      modes(i).y = startPointH
      modes(i).radius = radius

      if(currentMode() == modes(i).name){
        g.drawCircle(startPointW,startPointH,radius,Color.BLUE)
      }
      g.drawTransformedPicture(startPointW,startPointH,0,0.05f,icons(i))
      startPointW += 50
    }
  }


  def onMenuClick(x : Int, y: Int) : Boolean = {

    //vérifier pythagore vu que boutons ronds
    // Return true si un bouton du menu a été touché (pour prevent les actions quand clic sur le menu)
    for(m <- modes){
      if(math.sqrt(math.pow(x - m.x, 2) + math.pow(y - m.y, 2)) < m.radius){
        // bouton touché -> Changement de mode
        println("bouton pressé")
        modeSwitcher(m.name)
        return true
      }
    }
    false
  }
}
