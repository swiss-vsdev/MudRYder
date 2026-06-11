import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject
import com.badlogic.gdx.math.Vector2

// Player character: a physics circle with a sledge image, responds to collisions
class MudryMachine(name: String, position: Vector2, radius: Float, density: Float, restitution: Float, friction: Float)
  extends PhysicsCircle(name, position, radius, density, restitution, friction) with DrawableObject {

  var firstRun = true
  var posX: Float = 960
  var posY: Float = 900
  var angle: Float = 0
  var img: BitmapImage = _
  private var rider: BitmapImage = _
  private var imgDown: BitmapImage = _
  private var selectedImage: BitmapImage = _
  private var lastCollision = 0.5f

  // Teleport the player to a world position
  def setPos(x: Int, y: Int): Unit = {
    val muBody = this.getBody
    val positionMeters = new Vector2(x, y).scl(PhysicsConstants.P2M)
    muBody.setTransform(positionMeters, 0)
    this.setBodyLinearVelocity(new Vector2(0, 0))
    posX = x
    posY = y
  }

  // Enable physics on the player
  def awake(): Unit = {
    this.setBodyAwake(true)
    this.enableCollisionListener()
  }

  // Freeze the player (disables physics)
  def sleep(): Unit = {
    this.setBodyAwake(false)
  }

  // Draw the player
  def draw(g: GdxGraphics): Unit = {
    loadImages()

    if (math.abs(this.angle) > 30) {
      selectedImage = imgDown
    } else {
      selectedImage = img
    }

    g.drawTransformedPicture(posX, posY, this.angle, 0.1f, selectedImage)
    g.drawTransformedPicture(posX, posY, this.angle / 2, 0.1f, rider)

  }

  def loadImages(): Unit = {
    if (firstRun) {
      if (img == null) {
        rider = new BitmapImage("./icons/no-sled-mud.png")
        img = new BitmapImage("./icons/sledge.png")
        imgDown = new BitmapImage("./icons/sledge.png")
        selectedImage = img
      }
      firstRun = false
    }
  }

  // Sync stored position with the physics body position
  def update(): Unit = {
    posX = this.getBodyPosition.x
    posY = this.getBodyPosition.y
  }

  // Computes the player's tilt angle on collision
  override def collision(other: AbstractPhysicsObject, energy: Float): Unit = {
    //this.angle = this.angle + 1 //prout <----- (commentaire relique de St-Mui)
    angle = (other.getBodyAngle * 180 / math.Pi).toFloat

    if (angle <= -120) {
      angle += 180
    }
    if (angle >= 120) {
      angle -= 180
    }

    lastCollision = 1.0f
  }
}
