import ch.hevs.gdx2d.components.graphics.Polygon
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

// Temporary notification overlay shown after saving, displaying the filename
class SavingWindow {

  val firstWindowPoint: Vector2 = new Vector2(280, 960)
  val windowHeight: Int = 55
  val windowWidth: Int = 165

  val windowCo: Array[Vector2] = Array(
    firstWindowPoint,
    new Vector2(firstWindowPoint.x, firstWindowPoint.y + windowHeight),
    new Vector2(firstWindowPoint.x + windowWidth, firstWindowPoint.y + windowHeight),
    new Vector2(firstWindowPoint.x + windowWidth, firstWindowPoint.y)
  )

  def drawWindow(g: GdxGraphics, fn: String): Unit = {
    val oldColor = g.sbGetColor()
    g.setColor(Color.BLACK)
    g.drawFilledPolygon(new Polygon(windowCo), Color.WHITE)
    g.drawPolygon(new Polygon(windowCo))
    g.drawString(firstWindowPoint.x + 33, firstWindowPoint.y + windowHeight - 10, "Saving File...")
    g.drawString(firstWindowPoint.x + 10, firstWindowPoint.y + windowHeight - 33, fn)
    g.setColor(oldColor)
  }

}
