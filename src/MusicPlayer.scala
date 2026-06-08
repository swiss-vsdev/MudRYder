import java.io.File
import javax.sound.sampled.AudioSystem

class MusicPlayer() {
  private val clip = AudioSystem.getClip()
  private var lastmode: String = ""
  private var lastMusicmode: String = ""

  def play(currentMode: String, musicMode: String): Unit = {
    if (musicMode == "musicmute" && clip.isActive) {
      lastMusicmode = "musicmute"
      if(clip.isOpen){
        clip.stop()
        clip.close()
      }
    }

    if (currentMode != lastmode && musicMode != "musicmute" ||
      lastMusicmode != musicMode && musicMode != "musicmute" ||
      !clip.isActive && musicMode != "musicmute") {
      currentMode match {
        case "play" => {
          if (!clip.isActive || lastmode != "play") {
            if (lastmode != currentMode) {
              if(clip.isOpen){
                clip.stop()
                clip.close()
              }
              lastmode = currentMode
            }

            val musicfile = new File(s"./music/play.wav")
            val audio = AudioSystem.getAudioInputStream(musicfile)
            clip.open(audio)
            clip.start()
          }
        }
        case ("free" | "lines" | "eraser" | "mop") => {
          if (lastmode != "free" && lastmode != "lines" && lastmode != "mop" && lastmode != "eraser") {
            if(clip.isOpen){
              clip.stop()
              clip.close()
            }
          }
          if (!clip.isActive &&
            (lastmode != "free" || lastmode != "lines" || lastmode != "mop" || lastmode != "eraser")) {
            if (lastmode != currentMode) {
              if(clip.isOpen){
                clip.stop()
                clip.close()
              }
              lastmode = currentMode
            }

            val musicfile = new File(s"./music/edit.wav")
            val audio = AudioSystem.getAudioInputStream(musicfile)
            clip.open(audio)
            clip.loop(-1)
            clip.start()
          }
        }
        case _ => {
          lastmode = currentMode
          if(clip.isOpen){
            clip.stop()
            clip.close()
          }
        }
      }
    }
    lastmode = currentMode
  }
}
