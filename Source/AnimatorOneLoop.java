import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Classe d'animation qui permet de ne lire qu'une fois les frames d'un sprite
 */
public class AnimatorOneLoop {
	
	
	boolean running = false;
	ArrayList<BufferedImage> frames;
	BufferedImage sprite;
	public long previousTime, speed;
	public int currentFrame;
	
	/**
	 * La liste des frames composant le sprite
	 * @param frames : BufferedImage
	 */
	public AnimatorOneLoop(ArrayList<BufferedImage> frames) {
		this.frames = frames;
	}
	
	/**
	 * Temps entre chaque
	 * @param speed
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
					sprite = frames.get(currentFrame);
				} catch (IndexOutOfBoundsException e) {
					stop();
				}
				previousTime = time;
			}
		}
	}
	
	/**
	 * Permet de commencer la lecture du sprite
	 */
	public void start() {
		running = true;
		previousTime = 0;
		currentFrame = 0;
	}
	
	/**
	 * Permet d'arrêter la lecture d'un sprite
	 */
	public void stop() {
		running = false;
		currentFrame = 0;
		sprite = frames.get(0);
	}
	
	
}
