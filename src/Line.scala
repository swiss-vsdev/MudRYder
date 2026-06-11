import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

// Common interface for all line types: physics lines and decorative lines
trait Line {
  var color: Color

  def p1x: Float

  def p1y: Float

  def p2x: Float

  def p2y: Float

  def destroy() {}

  def draw(g: GdxGraphics) {}

}

