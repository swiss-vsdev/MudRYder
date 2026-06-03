import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import scala.collection.mutable.ArrayBuffer

class DrawingModesMachine {
  private var cm : String = "lines"
  private var dm : String = "physic"
  private var firstRun : Boolean = true

  val modes: ArrayBuffer[MenuModes] = ArrayBuffer.empty
  loadModes()
  val icons: ArrayBuffer[BitmapImage] = ArrayBuffer.empty

  private def loadModes() : Unit = {
    modes.addOne(MenuModes("free", 0, 0, 10))
    modes.addOne(MenuModes("lines", 0, 0, 10))
    modes.addOne(MenuModes("play", 0, 0, 10))
    modes.addOne(MenuModes("eraser", 0, 0, 10))
    modes.addOne(MenuModes("mop",0,0,10))
    modes.addOne(MenuModes("return",0,0,10))
    modes.addOne(MenuModes("save",0,0,10))
    modes.addOne(MenuModes("load",0,0,10))
    modes.addOne(MenuModes("physic", 0, 0, 10))
    modes.addOne(MenuModes("decoration", 0, 0, 10))
  }

  def loadIcons() : Unit = {
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

  def getDrawMode() : String = {
    return dm
  }

  def modeSwitcher(m:String) : Unit = {
    if (m == "physic" ){
      if (dm == "physic"){
        dm = "decoration"
      } else {
        dm = "physic"
      }
    } else {
      cm = m
    }

  }

  def drawModesMenu(g : GdxGraphics, width : Int, height : Int): Unit = {
    val startPointH : Int = width - 20
    var startPointW : Int = height + 30
    val radius = 18

    for(i <- modes.indices){

      if(modes(i).name == "physic" || modes(i).name == "decoration"){
        if (getDrawMode() == modes(i).name) {
          modes(i).x = startPointW
          modes(i).y = startPointH
          modes(i).radius = radius

          if(getDrawMode() == modes(i).name){
            g.drawCircle(modes(i).x,modes(i).y,modes(i).radius,Color.BLUE)
          }
          g.drawTransformedPicture(startPointW,startPointH,0,0.05f,icons(i))
          startPointW += 50
        }
      } else {
        modes(i).x = startPointW
        modes(i).y = startPointH
        modes(i).radius = radius

        if(currentMode() == modes(i).name){
          g.drawCircle(modes(i).x,modes(i).y,modes(i).radius,Color.BLUE)
        }
        if(getDrawMode() == modes(i).name){
          g.drawCircle(modes(i).x,modes(i).y,modes(i).radius,Color.BLUE)
        }
        g.drawTransformedPicture(startPointW,startPointH,0,0.05f,icons(i))
        startPointW += 50
      }
    }
  }


  def onMenuClick(x : Int, y: Int) : Boolean = {

    //vérifier pythagore vu que boutons ronds
    // Return true si un bouton du menu a été touché (pour prevent les actions quand clic sur le menu)
    for(m <- modes){
      if(math.sqrt((x - m.x)*(x - m.x) + (y - m.y)*(y - m.y)) < m.radius){
        // bouton touché -> Changement de mode
        modeSwitcher(m.name)
        return true
      }
    }
    false
  }
}
