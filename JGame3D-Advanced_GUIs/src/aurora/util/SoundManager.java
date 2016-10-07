package aurora.util;

import java.util.HashMap;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;

public class SoundManager {

	private static HashMap<String, Audio> SOUNDIDS = new HashMap<String, Audio>();

	/* Registers A Texture Into Memory */
	public static void loadLocalSound(String fileName) {

		// Tries to Load the Texture
		Audio sound = null;
		try {

			sound = AudioLoader.getAudio("WAV", SoundManager.class
					.getResourceAsStream("/aurora/resources/sounds/" + fileName
							+ ".wav"));

			// Registers the Sound
			SOUNDIDS.put(fileName, sound);

		} catch (Exception e) {

			System.err.println("Could Not Load Sound for " + fileName);
			System.err.println("/aurora/resources/sounds/" + fileName + ".wav");
		}
	}
	
	public static void startSound(String arg0){
		
		SoundManager.startSound(arg0, false);
	}
	
	public static void startSound(String arg0, boolean loop){
		
		SOUNDIDS.get(arg0).playAsMusic(1.0F, 1.0F, loop);
		SoundStore.get().poll(0);
	}
	
	public static void stopSound(String arg0){
		
		SOUNDIDS.get(arg0).stop();
	}
}
