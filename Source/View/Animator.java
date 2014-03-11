package View;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Classe d'animation qui permet de lire en boucle les frames du sprite correspondant au corps du personnage
 */
public class Animator {
	
	boolean running = false;
	ArrayList<BufferedImage> frames;
	BufferedImage sprite;
	public long previousTime, speed;
	public int frameAtPause, currentFrame;
	
	/**
	 * La liste des frames composant le sprite
	 * @param frames : BufferedImage
	 */
	public Animator(ArrayList<BufferedImage> frames) {
		this.frames = frames;
	}
	
	/**
	 * Temps entre chaque frame
	 * @param speed : en nombre de boucle de moteur de jeu
	 */
	public void setSpeed(long speed) {
		this.speed = speed;
	}
	
	/**
	 * Mise à jour de la frame
	 */
	public void update() {
		long time = System.currentTimeMillis();
		if (running) {
			if (time - previousTime >= speed) {
				currentFrame++;
				try {
				if(currentFrame == frames.size()-1) {
					currentFrame=0;
				}
				sprite = frames.get(currentFrame);
				previousTime = time;
				} catch (Exception e) {
					System.out.println(currentFrame+frames.toString()+"\n##"+e);
					System.exit(-2);
					
				}
			}
		}
	}
	
	/**
	 * Permet de commencer la lecture d'un sprite
	 */
	public void start() {
		running = true;
		previousTime = 0;
		frameAtPause = 0;
		currentFrame = 0;
	}
	
	/**
	 * Permet d'arrêter la lecture d'un sprite
	 */
	public void stop() {
		running = false;
		previousTime = 0;
		frameAtPause = 0;
		currentFrame = 0;
		sprite = frames.get(8);
	}
	
	/**
	 * Permet de mettre en pause la lecture d'un sprite
	 */
	public void pause() {
		frameAtPause = currentFrame;
		running = false;
	}
	
	/**
	 * Permet de reprendre la lecture d'un sprite à là où elle était arrétée
	 */
	public void resume() {
		running = true;
		//currentFrame = 0;
	}

}
