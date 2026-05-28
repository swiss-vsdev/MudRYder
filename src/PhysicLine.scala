import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticLine
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.graphics.Color

case class PhysicLine(p1x: Float, p1y: Float, p2x: Float, p2y: Float)
  extends PhysicsStaticLine ("a line", new Vector2(p1x,p1y), new Vector2(p2x,p2y), 0.1f, 0.5f, 0.4f)  with Line{
  var color : Color = Color.BLACK

  override def collision(other: AbstractPhysicsObject, energy: Float): Unit = {
    //println(s"$name collided ${other.name} with energy $energy")
  }

  override def destroy(): Unit = {
    super[PhysicsStaticLine].destroy()
  }

  override def draw(g : GdxGraphics): Unit = {
    g.setColor(color)
    g.drawLine(p1x,p1y,p2x,p2y)
  }
}