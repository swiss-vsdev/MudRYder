import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticLine
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.math.Vector2

case class Line (p1x: Float, p1y: Float, p2x: Float, p2y: Float)
  extends PhysicsStaticLine ("a line", new Vector2(p1x,p1y), new Vector2(p2x,p2y)) {

  override def collision(other: AbstractPhysicsObject, energy: Float): Unit = {
    Logger.log(s"$name collided ${other.name} with energy $energy")
  }
}
