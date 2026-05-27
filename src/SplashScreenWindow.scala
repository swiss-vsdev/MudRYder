import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2

import java.awt.Font

class SplashScreenWindow extends RenderingScreen {

  private var imgBitmap: BitmapImage = _
  val mM = new MudryMachine
  var c = 0

  override def onInit(): Unit = {
    imgBitmap = new BitmapImage("icons/mudry.png")
    mM.loadImages()
    mM.saintMudry.foreach { m =>
      m.body = new PhysicsCircle("rider", new Vector2(m.startX, m.startY), m.radius, 1f, 0.65f, 0.6f)
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.WHITE)
    g.setColor(Color.BLACK)
    g.drawStringCentered(((g.getScreenHeight / 2) + c).toFloat, "MudRYder")
    c += 3
    g.setColor(Color.WHITE)

    mM.awake()
    mM.update()
    PhysicsWorld.updatePhysics()

    //mM.drawMudry(g)

    mM.saintMudry match {
      case Some(sm) => {
        println(mM.posX + " " + mM.posY)
        g.drawTransformedPicture(mM.posX, mM.posY, 0, 0.8f, sm.img)
      }
      case None => {}
    }

    g.drawSchoolLogo()
  }

  override def dispose(): Unit = {
    imgBitmap.dispose()
  }

}
