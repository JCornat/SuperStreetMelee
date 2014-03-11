package Sound;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	Clip clip ;
	AudioInputStream inputStream ;
	boolean isPlaying ;
	File file ;
	
	public Sound(final String file_name){
		try {
			file = new File(file_name) ;
			clip = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(file);
			clip.open(inputStream);
			isPlaying = false ;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " : " + file_name);
		}
        
	}
	
    public void play_once(){          
        
        try {
        	clip.setFramePosition(0) ;
			clip.start();
		} catch (Exception e) {
			e.getMessage();
		}
    }
    
    
    public void loop(){          
       
    	try {
    		clip.setFramePosition(0) ;
            clip.loop(Clip.LOOP_CONTINUOUSLY) ;
            isPlaying = true ;
 		} catch (Exception e) {
 			e.getMessage() ;
 		}
    	
    }
         
    
    public void resume(){          
        
    	if (!isPlaying) {
    		try {
    			clip.loop(Clip.LOOP_CONTINUOUSLY) ;
        		isPlaying = true ;
    		} catch (Exception e) {
     			e.getMessage() ;
     		}
		}	
 		
    	
    }
    
    public void pause(){          
        
    	if (isPlaying) {
    		try {
    			clip.stop() ;
        		isPlaying = false ;
    		} catch (Exception e) {
     			e.getMessage() ;
     		}
		}
    	
    }
    
    public void decrease_volume(){
    	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    	gainControl.setValue(-4.0f);
    }
    
    public void increase_volume(){
    	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    	gainControl.setValue(+10.0f);
    }
}