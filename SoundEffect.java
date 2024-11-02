import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class SoundEffect {
    private Clip clip;
    private FloatControl volumeControl;

    public SoundEffect(String filePath) {
        try {
            URL audioSrc = getClass().getResource(filePath);
            if (audioSrc == null) {throw new IllegalArgumentException("Sound file not found: " + filePath);}
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);

            clip = AudioSystem.getClip();
            clip.open(audioStream);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            }
        } catch (Exception e) {}
    }

    public void play() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0); 
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop(); 
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float newVolume = min + (max - min) * volume;
            volumeControl.setValue(newVolume);
        }
    }
}
