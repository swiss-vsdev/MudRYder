import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics

class MudryMachine() {
  private var saintMudry : Option[Mudry] = None
  var firstRun = true
  var posX : Float = 900
  var posY : Float = 900
  var angle : Int = 0

  private def loadImages() : Unit = {
    if(firstRun){
      saintMudry = Some(Mudry(new BitmapImage("./icons/mudry.png"),900f,900f))
      firstRun = false
    }
  }

  def drawMudry(g:GdxGraphics) : Unit = {
    loadImages()

    saintMudry.foreach { m =>
      m.body.getBodyPosition
      g.drawTransformedPicture(posX, posY, angle, 0.1f, m.getImg)
    }
  }

  def update(): Unit = {
    saintMudry.foreach { m =>
      posX = m.body.getBodyPosition.x
      posY = m.body.getBodyPosition.y
    }
  }
}
