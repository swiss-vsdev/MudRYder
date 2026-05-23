import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticLine
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

case class DecoLine(p1x: Float, p1y: Float, p2x: Float, p2y: Float)
  extends Line{
  var color : Color = Color.BLACK;

  override def destroy() ={
    super.destroy()
  }

  override def draw(g : GdxGraphics): Unit = {
    g.setColor(color)
    g.drawLine(p1x,p1y,p2x,p2y)
  }
}
