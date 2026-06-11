import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

// THIS CASE CLASS IS STORING ALL THE DATA ABOUT A SAID DECOLINE

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
