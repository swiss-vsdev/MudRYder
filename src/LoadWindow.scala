import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ListBuffer


class LoadWindow extends RenderingScreen {
  var files: Array[FileHandle] = _
  var filesList: ListBuffer[String] = ListBuffer.empty

  var cancelButtonCo: Vector2 = new Vector2(0, 0)
  var loadButtonCo: Vector2 = new Vector2(0, 0)
  var answer: String = ""
  var selectedSaveIndice: Int = -1

  override def onInit(): Unit = {
    files = Gdx.files.local("saves").list()
    files.foreach { f =>
      filesList.addOne(f.name())
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.WHITE)
    g.setColor(Color.BLACK)
    g.drawStringCentered(800, "Files available : ")
    var c = 40

    for (filename <- filesList) {
      g.drawStringCentered(800 - c, filename)

      if (selectedSaveIndice != -1) {
        if (filename == filesList(selectedSaveIndice)) {
          g.setColor(Color.BLUE)
          g.drawRectangle(960, (795 - c), 160, 20, 0)
          g.setColor(Color.BLACK)
        }
      }

      c += 30
    }

    if (cancelButtonCo.x == 0 && cancelButtonCo.y == 0) {
      cancelButtonCo = new Vector2((g.getScreenWidth / 2 - 80), (750 - c))
    }
    if (loadButtonCo.x == 0 && loadButtonCo.y == 0) {
      loadButtonCo = new Vector2((g.getScreenWidth / 2 + 80), (750 - c))
    }


    g.drawRectangle(cancelButtonCo.x, cancelButtonCo.y, 100, 30, 0)
    g.drawString((g.getScreenWidth / 2 - 105), 755 - c, "Cancel")
    g.drawRectangle(loadButtonCo.x, loadButtonCo.y, 100, 30, 0)
    g.drawString((g.getScreenWidth / 2 + 65), 755 - c, "Load")
  }

  def onClick(x: Int, y: Int): Unit = {
    if (x >= cancelButtonCo.x - 50 && x <= cancelButtonCo.x + 50 &&
      y >= cancelButtonCo.y - 15 && y <= cancelButtonCo.y + 15) {
      answer = "cancel" // Cancel
    } else if (x >= loadButtonCo.x - 50 && x <= loadButtonCo.x + 50 &&
      y >= loadButtonCo.y - 15 && y <= loadButtonCo.y + 15) {
      answer = "load" // Load
    } else {
      answer = ""
    }

    for (c <- 0 until (filesList.length * 30) by 30) {
      if (x >= 880 && x <= 1040 &&
        y >= 745 - c && y <= 765 - c) {
        selectedSaveIndice = c / 30
      }
    }

  }

  def getSelection(): String = {
    filesList(selectedSaveIndice)
  }


  def getAnwser(): String = {
    answer
  }

}
