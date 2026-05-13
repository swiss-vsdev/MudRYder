import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics

class MudryMachine() {
  private var saintMudry : Option[Mudry] = None
  var firstRun = true
  var posX : Float = 15
  var posY : Float = 15
  var angle : Int = 0

  private def loadImages() : Unit = {
    if(firstRun){
      saintMudry = Some(Mudry(new BitmapImage("./icons/mudry.png"),100f,100f))
      firstRun = false
    }
  }

  def drawMudry(g:GdxGraphics) : Unit = {
    loadImages()

    saintMudry.foreach { m =>
      m.body.getBodyPosition
      g.drawTransformedPicture(m.startX, m.startY, angle, 0.1f, m.getImg)
    }
  }

  def update(): Unit = {
    saintMudry.foreach { m =>
      posX = m.body.getBodyPosition.x
      posY = m.body.getBodyPosition.y
    }
  }
}
