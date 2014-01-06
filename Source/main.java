public class main {

	public static Game g;
	
	public static boolean running = true;
	public static int frames = 0;
	public static int averageFrames = 0;
	//Mise en place de la variable engineLoop quand le diagramme des classes
	public static long engineLoop = 0;
	
	public static void main(String[] args) {
		
		g = new Game();
		double nsPerTick = 1000000000D/127D;
		long timerFPS = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		
		while(running) {
			
			while(System.nanoTime() <= lastTime + nsPerTick) {
				//Libération du CPU
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			frames++;
			engineLoop++;
			g.render();
			g.update();	
			
			lastTime = System.nanoTime();
			
			if (System.currentTimeMillis()-timerFPS >= 1000) {
				timerFPS = System.currentTimeMillis();
				averageFrames = frames;
				frames = 0;
				System.out.println(engineLoop);
			}
		}
	}
}
