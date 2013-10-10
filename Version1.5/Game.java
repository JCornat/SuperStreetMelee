import java.awt.Point;

import javax.swing.JFrame;


public class Game {

	static Joueur j;
	static JFrame frame;
	static JFrame fStats;
	
	//Creation de la fenetre de jeu
	public Game() {
		frame = new JFrame();
		
		//Creation du joueur
		j = new Joueur(50,330,60,100);
		
		//Appel et ajout du pattern d'affichage
		VueGraphique vg = new VueGraphique(j);
		frame.add(vg);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Point p = frame.getLocation();
		
		ControleurGraphique cg = new ControleurGraphique(frame, j);
		
		//Creation de la fenetre de stats du joeur
		fStats = new JFrame();
		VueStats vs = new VueStats(j);
		fStats.add(vs);
		fStats.pack();
		fStats.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fStats.setVisible(true);
		p.x = p.x - fStats.getWidth();
		fStats.setLocation(p);
		
		frame.requestFocus();	
	}
				
	
	public void update() {
		
		//– Lois du monde
		//– Collisions
		//– Contrôleurs du joueur
		j.checkKeys();
		//– Intelligence artificielle
		
	}
	
	public void render() {
		frame.repaint();
		fStats.repaint();
	}
}
