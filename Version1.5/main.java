import java.awt.Point;

import javax.swing.JFrame;


public class main extends JFrame {

	static Game g;
	
	public static void main(String[] args) {
		
		
		
		boolean jeuFonctionne = true;
		g = new Game();
		
		long duree = 6;
		while(true) {
			
			//Pour obtenir un framerate constant
			long avant = System.currentTimeMillis();
			g.update();
			g.render();
			long apres = System.currentTimeMillis();
			
			try {
				Thread.sleep(duree - (apres - avant));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	

}
