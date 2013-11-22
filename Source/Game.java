import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Game {

	static ArrayList<Joueur> tabJoueurs;
	static Joueur j;
	static JFrame frame;
	static Collision c;
	static ArrayList<Decor> tabDecor;
	static ArrayList<Attaque> tabAttaques;
	static int tailleDecor;
	public int collisionLeft, collisionRight, collisionTop, collisionBottom;
	final static int GRAVITY_MAX = 2;
	final static int INERTIE = 2;
	
	//Creation de la fenetre de jeu
	public Game() {
		
		//Creation du terrain
		tabDecor = new ArrayList<Decor>();
		tabDecor.clear();
		tabDecor.add(new Decor(30,500,500,10));
		tabDecor.add(new Decor(600,500,200,10));
		tabDecor.add(new Decor(200,300,400,10));
		tabDecor.add(new Decor(520,440,10,60));
		tabDecor.add(new Decor(820,380,60,40));
		tabDecor.add(new Decor(800,420,60,20));
		tabDecor.add(new Decor(939,380,60,40));
		tabDecor.add(new Decor(660,340,80,20));
		tabDecor.add(new Decor(0,690,1000,10));
		tabDecor.add(new Decor(0,0,10,700));
		tabDecor.add(new Decor(990,0,10,700));
		

		// Creation des attaques
		tabAttaques = new ArrayList<Attaque>();
		tabAttaques.clear();
		tabAttaques.add(new Attaque("Base", 5, 5, 5, 100, 300));
		tabAttaques.add(new Attaque("Grosse", 20, 10, 15, 300, 1300));
		
		// Creation des joueurs
		tabJoueurs = new ArrayList<Joueur>();
		tabJoueurs.clear();
		tabJoueurs.add(new Joueur("Joueur 1", 250, 10, 60, 100, tabAttaques));
		tabJoueurs.add(new Joueur("Joueur 2", 500, 10, 60, 100, tabAttaques));
		
		
		//Appel et ajout du pattern d'affichage	
		VueGraphique vg = new VueGraphique(j, tabDecor, tabAttaques, tabJoueurs);
		frame = new JFrame();
		frame.add(vg);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//Ajout des controles
		new ControleurGraphique(frame, tabJoueurs);
		
		c = new Collision();
		
		collisionLeft = -1;
		collisionRight = -1; 
		collisionTop = -1; 
		collisionBottom = -1;
	}
				
	
	public void update() {
		
		// Lois du monde
		
		for (Joueur j : tabJoueurs) {
			gravity(j);
			// on met a jour les temps lies aux attaques des joueurs (temps de recharge, temps d'affichage, etc)
			j.updateTimeAttack();
		}
		// Intelligence artificielle
		
	}
	
	private void gravity(Joueur j) {
		j.vitesseY = j.vitesseY + GRAVITY_MAX;
		if (j.vitesseY > 50) {
			j.vitesseY = 50;
		}
		int y = j.getY()  + j.vitesseY/10;
		int x = j.getX() + j.vitesseX/10;
		
		if (j.vitesseY > 0) {
			
			//Le personnage est en train d'aller vers le bas
			collisionBottom = c.collisionCalculation(j.getX(), y, j.getW(), j.getH(), tabDecor);
			if (collisionBottom > -1) {
				//Contact avec le sol
				j.setY(j.getY() + tabDecor.get(collisionBottom).y - j.getY() - j.getH());
				j.vitesseY=0;
				j.jumps = j.jumpsBase;
			} else {
				//Le personnage tombe
				j.setY(y);
				collisionBottom=-1;
			}
		} else if (j.vitesseY < 0) {
			//Le personnage est en train d'aller vers le haut
			collisionTop = c.collisionCalculation(j.getX(), y, j.getW(), j.getH(), tabDecor);
			if (collisionTop > -1) {
				//Contact avec un decor situe au-dessus
				j.setY(j.getY() + tabDecor.get(collisionTop).y+ tabDecor.get(collisionTop).h - j.getY());
				j.vitesseY=0;
			} else {
				//Le personnage monte
				j.setY(y);
				
			}
			
		}
		
		if (j.vitesseX == 0){
			if (j.right && j.isJumping) {
				j.vitesseX += INERTIE/2;
			} else if (j.left && j.isJumping) {
				j.vitesseX -= INERTIE/2;
			} else if (j.right) {
				j.vitesseX += INERTIE;
			} else if (j.left ) {
				j.vitesseX -= INERTIE;
			} 
		} else if (j.vitesseX > 0) {
			//Le personnage est en train d'aller vers la droite
			
			//Si touche droite non enfoncee, inertie mise en place pour freiner
			if (!j.right) {
				j.vitesseX -= INERTIE/2;
			} else {
				j.vitesseX += INERTIE;
				if (j.vitesseX >= j.RUN_SPEED) {
					j.vitesseX = j.RUN_SPEED;
				}
			}
			x = j.getX() + j.vitesseX/10;
			collisionRight = c.collisionCalculation(x, j.getY(), j.getW(), j.getH(), tabDecor);
			if (collisionRight > -1) {
				//Contact avec le decor sur la droite
				j.vitesseX = 0;
				j.setX(j.getX() + tabDecor.get(collisionRight).x - j.getX() - j.getW());
			} else {
				//Le personnage se deplace sur la droite
				j.setX(x);
			}
			 
			
			
		} else if (j.vitesseX < 0) {
			//Le personnage est en train d'aller vers la gauche
			
			//Si touche gauche non enfoncee, inertie mise en place pour freiner
			if (!j.left) {
				j.vitesseX += INERTIE/2;
			} else {
				j.vitesseX -= INERTIE;
				if (j.vitesseX <= -j.RUN_SPEED) {
					j.vitesseX = -j.RUN_SPEED;
				}
			}
			x = j.getX() + j.vitesseX/10;
			collisionLeft = c.collisionCalculation(x, j.getY(), j.getW(), j.getH(), tabDecor);
			if (collisionLeft > -1) {
				//Contact avec le décor sur la gauche
				j.vitesseX = 0;
				j.setX(j.getX() + tabDecor.get(collisionLeft).x + tabDecor.get(collisionLeft).w - j.getX());
			} else {
				//Le personnage se déplace sur la gauche
				j.setX(x);
			}
		} 

	}

	public void render() {
		frame.repaint();
	}
	
	
	

	
}
