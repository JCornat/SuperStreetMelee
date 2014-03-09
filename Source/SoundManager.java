import java.util.HashMap;


public class SoundManager {

	
	public static HashMap<String, Sound> sounds ;
	
	
	public SoundManager(){
		
		sounds = new HashMap<String, Sound>() ;
		sounds.put("background", 	new Sound("sounds/background.wav")) ;
		sounds.put("intro", 		new Sound("sounds/intro.wav")) ;
		sounds.put("bighit", 		new Sound("sounds/bighit.wav")) ;
		sounds.put("smallhit", 		new Sound("sounds/smallhit.wav")) ;
		sounds.put("jump", 			new Sound("sounds/jump.wav")) ;
		sounds.put("special1", 		new Sound("sounds/special1.wav")) ;
		sounds.put("special2", 		new Sound("sounds/special2.wav")) ;
		//sounds.put("special3", 	new Sound("sounds/special3.wav")) ;
	}

}
