import ch.hevs.gdx2d.components.graphics.Polygon
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

class AreYouSureWindow {
  private var answer : String = ""

  val firstWindowPoint: Vector2 = new Vector2(200, 960)
  val windowHeight: Int = 60
  val windowWidth: Int = 130

  val windowCo: Array[Vector2] = Array(
    firstWindowPoint,
    new Vector2(firstWindowPoint.x, firstWindowPoint.y + windowHeight),
    new Vector2(firstWindowPoint.x + windowWidth, firstWindowPoint.y + windowHeight),
    new Vector2(firstWindowPoint.x + windowWidth, firstWindowPoint.y)
  )

  val buttonMargin: Int = 10
  val buttonHeight: Int = 20
  val buttonWidth: Int  = 50

  // Yes — coin bas-gauche dans la fenêtre
  val firstButtonPoint: Vector2 = new Vector2(firstWindowPoint.x + buttonMargin, firstWindowPoint.y + buttonMargin)
  val yesButton: Array[Vector2] = Array(
    firstButtonPoint,
    new Vector2(firstButtonPoint.x, firstButtonPoint.y + buttonHeight),
    new Vector2(firstButtonPoint.x + buttonWidth, firstButtonPoint.y + buttonHeight),
    new Vector2(firstButtonPoint.x + buttonWidth, firstButtonPoint.y)
  )

  // No — à droite du Yes
  val noButtonPoint: Vector2 = new Vector2(firstButtonPoint.x + buttonWidth + buttonMargin, firstButtonPoint.y)
  val noButton: Array[Vector2] = Array(
    noButtonPoint,
    new Vector2(noButtonPoint.x, noButtonPoint.y + buttonHeight),
    new Vector2(noButtonPoint.x + buttonWidth, noButtonPoint.y + buttonHeight),
    new Vector2(noButtonPoint.x + buttonWidth, noButtonPoint.y)
  )

  def drawWindow(g: GdxGraphics): Unit = {
    val oldColor = g.sbGetColor()
    g.setColor(Color.BLACK)
    g.drawFilledPolygon(new Polygon(windowCo),Color.WHITE)
    g.drawPolygon(new Polygon(windowCo))
    g.drawString(firstWindowPoint.x + 13, firstWindowPoint.y + windowHeight - buttonMargin, "Are you sure ?")
    g.drawPolygon(new Polygon(yesButton))
    g.drawString(firstButtonPoint.x + 12, firstButtonPoint.y + buttonHeight - 5, "Yes")
    g.drawPolygon(new Polygon(noButton))
    g.drawString(noButtonPoint.x + 15, noButtonPoint.y + buttonHeight - 5, "No")
    g.setColor(oldColor)
  }

  def onClick(x: Int, y: Int): Unit = {
    if (x >= firstButtonPoint.x && x <= firstButtonPoint.x + buttonWidth &&
      y >= firstButtonPoint.y && y <= firstButtonPoint.y + buttonHeight) {
      answer = "yes" // Yes
    } else if (x >= noButtonPoint.x && x <= noButtonPoint.x + buttonWidth &&
      y >= noButtonPoint.y && y <= noButtonPoint.y + buttonHeight) {
      answer = "no" // No
    } else {
      answer = ""
    }
  }

  def getAnwser() : String = {
    return answer
  }
}
