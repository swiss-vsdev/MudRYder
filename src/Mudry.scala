import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import com.badlogic.gdx.math.Vector2

case class Mudry(img : BitmapImage, startX: Float, startY : Float) {
  val radius = 10f
  val body = new PhysicsCircle("rider", new Vector2(startX, startY), radius, 1f, 0.1f, 0.6f)

  def getImg: BitmapImage = {
     img
  }
}

