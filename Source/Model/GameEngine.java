package Model;

public class GameEngine {

	public static Game game;
	
	public static boolean running = true;
	public static int frames = 0;
	public static int averageFrames = 0;
	public static long engineLoop = 0;
	
	public GameEngine() {
		game = new Game();
		double nsPerTick = 1000000000D/127D;
		long timerFPS = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		
		while(running) {
			
			while(System.nanoTime() <= lastTime + nsPerTick) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			frames++;
			engineLoop++;
			game.render();
			game.update();
			
			lastTime = System.nanoTime();
			
			if (System.currentTimeMillis()-timerFPS >= 1000) {
				timerFPS = System.currentTimeMillis();
				averageFrames = frames;
				frames = 0;
				game.updateTimer();
			}
		}
	}
}
