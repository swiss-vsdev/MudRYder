import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

trait Line{
  def p1x: Float
  def p1y: Float
  def p2x: Float
  def p2y: Float
  var color: Color

  def destroy() {}

  def draw(g: GdxGraphics) {}

}

