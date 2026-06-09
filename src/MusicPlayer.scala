import java.io.File
import javax.sound.sampled.AudioSystem

// THIS CLASS IS RESPONSIBLE FOR MUSIC PART OF THE GAME

class MusicPlayer() {
  private val clip = AudioSystem.getClip()
  private var lastmode: String = ""
  private var lastMusicmode: String = ""

  def play(currentMode: String, musicMode: String): Unit = {
    if (musicMode == "musicmute" && clip.isActive) { // Si la musique est coupée en jeu, fermer le clip
      lastMusicmode = "musicmute"
      if(clip.isOpen){
        clip.stop()
        clip.close()
      }
    }

    if (currentMode != lastmode && musicMode != "musicmute" ||
      lastMusicmode != musicMode && musicMode != "musicmute" ||
      !clip.isActive && musicMode != "musicmute") {
      currentMode match { // Else, we play the music based on the game mode
        case "play" => {
          if (!clip.isActive || lastmode != "play") { //if the mode play is selected, play the music "play.wav"
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
        case ("free" | "lines" | "eraser" | "mop") => { //if an edition mode is selected, play the music "edit.wav"
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
        case _ => { //else stop everything
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
