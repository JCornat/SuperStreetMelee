import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GameEngine {

	static ArrayList<Player> listPlayers;
	static Player player;
	static Menu frame;
	ArrayList<Attack> arrayOfAttacksForOneCharacter;
	static int tailleDecor;
	static ArrayList<Decor> arrayDecor;
	static int gameDuration;
	static State CURRENT_STATE = State.IN_MENU ;
	Gravity gravity;
	
	/**
	 * Creation de la fenetre de jeu
	 */
	public GameEngine() {
		gravity = new Gravity();
		Levels a = new Levels();
		
		//Chargement du niveau 1
		arrayDecor = a.levels.get(1);
		
		// Creation des attaques
		arrayOfAttacksForOneCharacter = new ArrayList<Attack>();
		arrayOfAttacksForOneCharacter.clear();
		arrayOfAttacksForOneCharacter.add(new Attack("Small", 5, 5, 5, 0, 10, 10,20,20));
		arrayOfAttacksForOneCharacter.add(new Attack("Big", 16, 10, 15, 60, 80, 150,100,100));
		
		// Initilisation de la duree de la partie en sec
		gameDuration = 120;
		
		
		listPlayers = new ArrayList<Player>();
		listPlayers.clear();
		listPlayers.add(new Player("Joueur 1", 80, 80, arrayOfAttacksForOneCharacter));
		listPlayers.add(new Player("Joueur 2", 80, 80, arrayOfAttacksForOneCharacter));
		
		
		//Appel et ajout du pattern d'affichage	
		GraphicalView graphicalView = new GraphicalView(arrayDecor, arrayOfAttacksForOneCharacter, listPlayers);
		frame = new Menu("SuperStreetMelee", graphicalView) ;
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setFocusable(true) ;
		
		//Ajout des controles
		new GraphicalController(frame, listPlayers);
	}
	
	
	/**
	 * Methode pour reinitialiser le jeu actuel, incluant les joueurs et le timer.
	 */
	public static void resetGame() {
		for (int i = 0; i < listPlayers.size(); i++) {
			listPlayers.get(i).setX(i*300+310);
			listPlayers.get(i).setY(110);
			listPlayers.get(i).resetLife();
			listPlayers.get(i).speedOnHorizontalAxis = 0;
			listPlayers.get(i).speedOnVerticalAxis = 0;
			listPlayers.get(i).left = false;
			listPlayers.get(i).right = false;
			listPlayers.get(i).jump = false;
			listPlayers.get(i).isJumping = false;
    	}
		gameDuration = 120;
	}
	
	/**
	 * Mise a jour des proprietes du monde sur les joueurs
	 */
	public void update() {
		// Lois du monde
		if (CURRENT_STATE == State.IN_GAME) {
			for (Player j : listPlayers) {
				if ((j.getX() > Menu.WIDTH) || (j.getX() < -100) || (j.getY() > Menu.HEIGHT) || (j.getY() < -150)) {
					j.decreaseNumberOfLife() ;
					j.resetLife() ;
					j.setX(Menu.WIDTH / 2 - j.w);
					j.setY(110);
					j.speedOnHorizontalAxis = 0 ;
					j.speedOnVerticalAxis = 0 ;
				}
				gravity.gravity(j);
				//On verifie si les joueurs ont lance des attaques
				VerificationAttack.verificationAttack(j);
				//On met a jour les temps lies aux attaques des joueurs (temps de recharge, temps d'affichage, etc)
				j.updateTimeAttack();
			}
			//Future intelligence artificielle
		}
	}
	
	/**
	 * Mise a jour du compteur de la partie
	 */
	public void updateTimer() {
		if (CURRENT_STATE == State.IN_GAME) {
			// Timer
			if (gameDuration > 0)
				gameDuration--;
			else {
				// Temps ecoule, partie terminee
				GameEngine.CURRENT_STATE = State.IN_MENU ;
				((CardLayout) Menu.cards.getLayout()).show(Menu.cards, "mainmenu");
			}
		}
	}

	/**
	 * 
	 */
	public void render() {
		frame.repaint();
	}
	
	
	

	
}
