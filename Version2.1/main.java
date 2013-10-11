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
			long sleep = duree - (apres - avant);
			long diff = apres - avant;
			if (diff > duree)
				diff = duree;
			
			try {
				Thread.sleep(duree - diff);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	

}
