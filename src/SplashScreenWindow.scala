import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.math.Vector2


class SplashScreenWindow extends RenderingScreen {

  private var imgBitmap: BitmapImage = _

  private val param : FreeTypeFontParameter = new FreeTypeFontParameter
  param.size = 50
  param.color = Color.BLACK
  private val customFont : BitmapFont =
    new FreeTypeFontGenerator(Gdx.files.local("font/Acme-Regular.ttf")).generateFont(param)

  var mM : MudryMachine = _
  var c = 0

  override def onInit(): Unit = {
    imgBitmap = new BitmapImage("icons/mudry2.png")
    mM = new MudryMachine("rider2", new Vector2(960, 900), 10, 1f, 0.65f, 0.6f)
    mM.loadImages()
    /*mM.saintMudry.foreach { m =>
      m.body = new BumpyBall(m, "rider", new Vector2(m.startX, m.startY), m.radius, 1f, 0.65f, 0.6f)
    }*/
    mM.setPos(100,1200)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.WHITE)
    g.setColor(Color.BLACK)
    g.drawStringCentered(((g.getScreenHeight / 2) + c).toFloat, "MudRYder",customFont)
    c += 2
    g.setColor(Color.WHITE)

    mM.awake()
    mM.update()
    PhysicsWorld.updatePhysics()

    //mM.drawMudry(g)

    //mM.saintMudry match {
      //case Some(sm) => {
        //println(mM.posX + " " + mM.posY)
        g.drawTransformedPicture(mM.posX, mM.posY, 0, 0.7f, imgBitmap)
      //}
      //case None => {}
    //}
    g.drawSchoolLogo()
  }

  override def dispose(): Unit = {
    imgBitmap.dispose()
  }

}
