import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

class MudryMachine() {
  private var saintMudry : Option[Mudry] = None
  var firstRun = true
  var posX : Float = 900
  var posY : Float = 900
  var angle : Int = 0

  def setPos(x: Int, y: Int): Unit = {
    saintMudry.foreach { m =>
      val muBody = m.body.getBody()
      val positionMeters = new Vector2(x, y).scl(PhysicsConstants.P2M)
      muBody.setTransform(positionMeters, 0)
      //muBody.setTransform(x.toFloat, y.toFloat, 0)
      m.body.setBodyLinearVelocity(new Vector2(0, 0))
      m.body.setBodyAwake(true)
    }
    posX = x
    posY = y
  }

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
