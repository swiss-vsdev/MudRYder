import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.math.Vector2

class MudryMachine( /*m: Mudry,*/ name: String, position: Vector2, radius: Float, density: Float, restitution: Float, friction: Float)
  extends PhysicsCircle(name, position, radius, density, restitution, friction) with DrawableObject {

  var firstRun = true
  var posX : Float = 960
  var posY : Float = 900
  var angle : Float = 0
  var img : BitmapImage = _
  private var lastCollision = 0.5f

  def setPos(x: Int, y: Int): Unit = {
    val muBody = this.getBody()
    val positionMeters = new Vector2(x, y).scl(PhysicsConstants.P2M)
    muBody.setTransform(positionMeters, 0)
    this.setBodyLinearVelocity(new Vector2(0, 0))
    posX = x
    posY = y
  }

  def awake(): Unit = {
    this.setBodyAwake(true)
    this.enableCollisionListener()
  }

  def sleep(): Unit = {
    this.setBodyAwake(false)
  }

  def loadImages() : Unit = {
    if(firstRun){
      if(img == null)
        img = new BitmapImage("./icons/mudry.png")

      firstRun = false
    }
  }

  def draw(g:GdxGraphics) : Unit = {
    loadImages()

    this.getBodyPosition
    g.drawTransformedPicture(posX, posY, this.angle, 0.1f, img)

    /*if (this.getBodyAngularVelocity < 0 && this.getBodyLinearVelocity.x > 0.1
      && this.getBodyLinearVelocity.x < 4.5){
      g.drawTransformedPicture(posX, posY, this.angle, 0.1f, this.getImgDown)
    } else {
      g.drawTransformedPicture(posX, posY, this.angle, 0.13f, this.getImg)
    }*/
  }

  def update(): Unit = {
    posX = this.getBodyPosition.x
    posY = this.getBodyPosition.y
  }

  /** Called for every collision. */
  override def collision(other: AbstractPhysicsObject, energy: Float): Unit = {
    //this.angle = this.angle + 1 //prout <----- (commentaire relique de St-Mui)
    angle = (other.getBodyAngle * 180 / math.Pi).toFloat
    println(s"$name angle ${this.angle} and other angle = ${other.getBodyAngle}")
    //Logger.log(s"$name collided ${other.getBodyAngle} with energy $energy")

    lastCollision = 1.0f
  }
}
