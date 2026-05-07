import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

class Game extends DesktopApplication(1920, 1080) {

  override def onInit(): Unit = {
    setTitle("MudRYder")

  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.WHITE)
    g.drawStringCentered(getWindowHeight / 2.0f, "Hello I'm a game")
    g.drawFPS(Color.BLACK)
    g.drawSchoolLogo()
  }
}

object HelloGdx2d {
  def main(args: Array[String]): Unit = {
    new Game().launch()
  }
}
