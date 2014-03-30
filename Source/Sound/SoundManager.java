package Sound;

import java.util.HashMap;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager {

	private static HashMap<String, Sound> sounds;

	public SoundManager() {

		sounds = new HashMap<String, Sound>();
		sounds.put("background", new Sound("sounds/background.wav"));
		sounds.put("buttonclick", new Sound("sounds/buttonSound.wav"));
		sounds.put("intro", new Sound("sounds/intro.wav"));
		sounds.put("bighit", new Sound("sounds/bighit.wav"));
		sounds.put("smallhit", new Sound("sounds/smallhit.wav"));
		sounds.put("jump", new Sound("sounds/jump.wav"));
		sounds.put("special1", new Sound("sounds/special1.wav"));
		sounds.put("special2", new Sound("sounds/special2.wav"));
		// sounds.put("special3", new Sound("sounds/special3.wav")) ;
	}

	public static void play_once(String name) {
		sounds.get(name).play_once();
	}

	public static void loop(String name) {
		sounds.get(name).loop();
	}

	public static void resume(String name) {
		sounds.get(name).resume();
	}

	public static void pause(String name) {
		sounds.get(name).pause();
	}

	public static void decrease_volume(String name) {
		sounds.get(name).decrease_volume();
	}

	public static void increase_volume(String name) {
		sounds.get(name).increase_volume();
	}

}
