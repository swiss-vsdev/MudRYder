import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticLine
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

trait Line{
  def p1x: Float
  def p1y: Float
  def p2x: Float
  def p2y: Float
  var color: Color

  def destroy() {}

  def draw(g: GdxGraphics) {}

}

