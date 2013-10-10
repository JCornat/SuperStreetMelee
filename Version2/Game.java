import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Game {

	static Joueur j;
	static JFrame frame;
	static JFrame fStats;
	static Collision c;
	static ArrayList<Decor> tabDecore;
	static int tailleDecor;
	
	//Creation de la fenetre de jeu
	public Game() {
		frame = new JFrame();
		
		//Creation du joueur
		j = new Joueur(50,330,60,100);
		
		//Creation du sol
		tabDecore = new ArrayList<Decor>();
		tabDecore.clear();
		tabDecore.add(new Decor(30,500,500,10));
		
		
		tabDecore.add(new Decor(500,500,10,60));
		
		//Appel et ajout du pattern d'affichage
		
		VueGraphique vg = new VueGraphique(j,tabDecore);
		
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
		tailleDecor = tabDecore.size();
		System.out.println(tailleDecor);
		c = new Collision();
	}
				
	
	public void update() {
		
		//– Lois du monde
		//– Collisions
		/*if (c.Collision(j.x, j.y, j.w, j.h, sol.x, sol.y, sol.w, sol.h)) {
			System.out.println("Collision");
		}*/
		
		//– Contrôleurs du joueur
		checkKeys();
		//– Intelligence artificielle
		
	}
	
	public void render() {
		frame.repaint();
		fStats.repaint();
	}
	
	
	public void checkKeys() {
		if(j.jump) {
			jump();
		}
		if (j.left) {
			left();
		}
		if (j.right) {
			right();
		}
		if (j.crouch) {
			crouch();
		}
		
		
	}
	
///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
	
	
	//C'est ici que je gère les collisions
	
	
///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
	
	public void left() {
		boolean collide = false;
		int x = j.getX() - j.vitesse ;
		for (int i = 0; i<tailleDecor; i++) {
			if (c.Collision(j.x, j.y, j.w, j.h, tabDecore.get(i).x, tabDecore.get(i).y, tabDecore.get(i).w, tabDecore.get(i).h)) {
				collide = true;
			}
		}
		if (!collide) {
			j.setX(x);
		}
	}
	
	public void right() {
		boolean collide = false;
		int x = j.getX() + j.vitesse ;
		for (int i = 0; i<tailleDecor; i++) {
			if (c.Collision(j.x, j.y, j.w, j.h, tabDecore.get(i).x, tabDecore.get(i).y, tabDecore.get(i).w, tabDecore.get(i).h)) {
				collide = true;
			}
		}
		if (!collide) {
			j.setX(x);
		}
	}
	
	public void jump() {
		boolean collide = false;
		int y = j.getY() - j.vitesse ;
		for (int i = 0; i<tailleDecor; i++) {
			if (c.Collision(j.x, j.y, j.w, j.h, tabDecore.get(i).x, tabDecore.get(i).y, tabDecore.get(i).w, tabDecore.get(i).h)) {
				collide = true;
			}
		}
		if (!collide) {
			j.setY(y);
		}
	}
	
	public void crouch() {
		boolean collide = false;
		int y = j.getY() + j.vitesse ;
		for (int i = 0; i<tailleDecor; i++) {
			if (c.Collision(j.x, j.y, j.w, j.h, tabDecore.get(i).x, tabDecore.get(i).y, tabDecore.get(i).w, tabDecore.get(i).h)) {
				collide = true;
			}
		}
		if (!collide) {
			j.setY(y);
		}
	}
	
}
