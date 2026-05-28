import java.io.File
import javax.sound.sampled.AudioSystem

class MusicPlayer () {
  private val clip = AudioSystem.getClip()

  def play(currentMode : String) : Unit = {
    currentMode match {
      case "play" => {
        val musicfile = new File(s"./music/$currentMode.wav")
        val audio = AudioSystem.getAudioInputStream(musicfile)
        clip.open(audio)
        clip.start()
      }
      case _ => {
        clip.stop()
        clip.close()
      }
    }
  }
}
