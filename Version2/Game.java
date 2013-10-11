import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Game {

	static Joueur j;
	static JFrame frame;
	static Collision c;
	static ArrayList<Decor> tabDecor;
	static int tailleDecor;
	public int collisionLeft, collisionRight, collisionTop, collisionBottom;
	
	//Creation de la fenetre de jeu
	public Game() {
		frame = new JFrame();
		
		//Creation du joueur
		j = new Joueur(50,330,60,100);
		
		//Creation du terrain
		tabDecor = new ArrayList<Decor>();
		tabDecor.clear();
		tabDecor.add(new Decor(30,500,500,10));
		tabDecor.add(new Decor(600,500,200,10));
		tabDecor.add(new Decor(200,300,400,10));
		tabDecor.add(new Decor(520,440,10,60));
		
		//Appel et ajout du pattern d'affichage	
		VueGraphique vg = new VueGraphique(j,tabDecor);
		frame.add(vg);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Point p = frame.getLocation();
		
		//Ajout des controles
		ControleurGraphique cg = new ControleurGraphique(frame, j);
		
		c = new Collision();
		
		collisionLeft = -1;
		collisionRight = -1; 
		collisionTop = -1; 
		collisionBottom = -1;
		
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

	public void left() {
		int x = j.getX() - j.vitesse ;
		collisionLeft = c.Collision(x,j.getY(),j.getW(),j.getH(),tabDecor);
		if (collisionLeft > -1) {
			j.setX(j.getX()+tabDecor.get(collisionLeft).x+tabDecor.get(collisionLeft).w-j.getX());
		} else {
			j.setX(x);
		}
	}
	
	public void right() {
		int x = j.getX() + j.vitesse ;
		collisionRight = c.Collision(x,j.getY(),j.getW(),j.getH(),tabDecor);
		if (collisionRight > -1) {
			j.setX(j.getX()+tabDecor.get(collisionRight).x-j.getX()-j.getW());
		} else {
			j.setX(x);
		}
	}
	
	public void jump() {
		int y = j.getY() - j.vitesse ;
		collisionTop = c.Collision(j.getX(),y,j.getW(),j.getH(),tabDecor);
		if (collisionTop > -1) {
			j.setY(j.getY()+tabDecor.get(collisionTop).y+tabDecor.get(collisionTop).h-j.getY());
		} else {
			j.setY(y);
		}
	}
	
	public void crouch() {
		int y = j.getY() + j.vitesse ;
		collisionBottom = c.Collision(j.getX(),y,j.getW(),j.getH(),tabDecor);
		if (collisionBottom > -1) {
			j.setY(j.getY()+tabDecor.get(collisionBottom).y-j.getY()-j.getH());
		} else {
			j.setY(y);
		}
	}
	
}
