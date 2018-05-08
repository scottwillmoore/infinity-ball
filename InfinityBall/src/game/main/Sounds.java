package game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
	
	/* Music */
	public Music music;
	
	/*Ball Hit */
	public Sound hit;
	
	public void init() {

		music = Gdx.audio.newMusic(Gdx.files.internal("music/Szymon Matuszewski - Art.mp3"));
		music.setVolume(Settings.VOLUME);
		music.setLooping(true);
		music.play();
		
		hit = Gdx.audio.newSound(Gdx.files.internal("sfx/hit.wav"));
	}
	
	public void setVolume(float volume) {
		
		music.setVolume(volume);
	}
	
	public void playRandomSound(Sound sound, float minPitch, float maxPitch) {
		
		float pitch = (float) (minPitch + (Math.random() * (minPitch - maxPitch) + 1));
		sound.play(Settings.VOLUME, (float) pitch, 0);
	}
	
	public void dispose() {
		
		music.dispose();
		hit.dispose();
	}
}
