import java.awt.Point;

import javax.swing.JFrame;


public class main extends JFrame {

	static Game g;
	
	//Vitesse du jeu (6 pour vitesse normale)
	final static long REFRESH_TIME = 6;
	
	public static void main(String[] args) {
		
		boolean jeuFonctionne = true;
		g = new Game();
		
		while(jeuFonctionne) {
			//Pour obtenir un framerate constant
			long avant = System.currentTimeMillis();
			g.update();
			g.render();
			long apres = System.currentTimeMillis();
			long sleep = REFRESH_TIME - (apres - avant);
			if (sleep < 0) {
				sleep = REFRESH_TIME;
			}
			try {
				Thread.sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
