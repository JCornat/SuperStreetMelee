import java.awt.Point;

import javax.swing.JFrame;


public class main extends JFrame {

	static Game g;
	final static long DUREE = 6 ;
	
	public static void main(String[] args) {
		
		
		
		boolean jeuFonctionne = true;
		g = new Game();
		
		while(true) {
			
			//Pour obtenir un framerate constant
			long avant = System.currentTimeMillis();
			g.update();
			g.render();
			long apres = System.currentTimeMillis();
			long sleep = DUREE - (apres - avant);
			long diff = apres - avant;
			if (diff > DUREE)
				diff = DUREE;
			
			try {
				Thread.sleep(DUREE - diff);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	

}
