import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

// A decorative line (no physics, rendered in blue when editing and in black when playing)

case class DecoLine(p1x: Float, p1y: Float, p2x: Float, p2y: Float)
  extends Line {
  var color: Color = Color.BLACK

  override def destroy(): Unit = {
    super.destroy()
  }

  override def draw(g: GdxGraphics): Unit = {
    g.setColor(color)
    g.drawLine(p1x, p1y, p2x, p2y)
  }
}
