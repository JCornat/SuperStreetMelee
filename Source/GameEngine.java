import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


import javax.swing.JFrame;
import javax.swing.Timer;

public class GameEngine {

	static ArrayList<Player> listPlayers;
	static Player player;
	static Menu frame;
	ArrayList<Attack> arrayOfAttacksForOneCharacter;
	ArrayList<Combo> arrayOfComboForOneCharacter;
	static int tailleDecor;
	static ArrayList<Decor> arrayDecor;
	static int gameDuration;
	static State CURRENT_STATE = State.IN_MENU ;
	Gravity gravity;
	static Sound backgroundsound ;
	static ArrayList<Xbox360Controller> xbox_controllers ;
	
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
		arrayOfComboForOneCharacter = new ArrayList<Combo>();
		arrayOfComboForOneCharacter.clear();

		Attack small = new Attack("Small", 55, 5, 5, 0, 0, 0, 20, 20);
		Attack medium = new Attack("Medium", 73, 7, 10, 40, 75, 100, 60, 60);
		Attack big = new Attack("Big", 80, 10, 15, 60, 80, 150, 100, 100);

		// Combo 1
		Attack specialAttack1 = new Attack("Special1", 100, 20, 20, 25, 60, 0, 100, 100);
		Combo combo1 = new Combo(big, small, specialAttack1);

		// Combo 2
		Attack specialAttack2 = new Attack("Special2", 100, 20, 25, 27, 60, 0, 100, 100);
		Combo combo2 = new Combo(medium, small, specialAttack2);

		// Combo 3
		Attack specialAttack3 = new Attack("Special3", 100, 20, 50, 30, 60, 0, 100, 100);
		Combo combo3 = new Combo(medium, small, big, specialAttack3);

		// Ajouts dans la liste des attaques
		arrayOfAttacksForOneCharacter.add(small);
		arrayOfAttacksForOneCharacter.add(medium);
		arrayOfAttacksForOneCharacter.add(big);
		arrayOfAttacksForOneCharacter.add(specialAttack1);
		arrayOfAttacksForOneCharacter.add(specialAttack2);
		arrayOfAttacksForOneCharacter.add(specialAttack3);

		// Ajouts dans la liste des combos
		arrayOfComboForOneCharacter.add(combo1);
		arrayOfComboForOneCharacter.add(combo2);
		arrayOfComboForOneCharacter.add(combo3);
		
		// Initilisation de la duree de la partie en sec
		gameDuration = Constant.GAME_DURATION;
		
		
		listPlayers = new ArrayList<Player>();
		listPlayers.clear();
		listPlayers.add(new Player("Joueur 1", 80, 80, arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter));
		listPlayers.add(new Player("Joueur 2", 80, 80, arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter));
		
		
		//Appel et ajout du pattern d'affichage	
		GraphicalView graphicalView = new GraphicalView(arrayDecor, arrayOfAttacksForOneCharacter, listPlayers);
		frame = new Menu("SuperStreetMelee", graphicalView) ;
//		frame.setUndecorated(true);  
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setFocusable(true) ;
//		frame.setExtendedState(Frame.MAXIMIZED_BOTH);  
//		Toolkit tk = Toolkit.getDefaultToolkit();  
//		int xSize = ((int) tk.getScreenSize().getWidth());  
//		int ySize = ((int) tk.getScreenSize().getHeight()); 
//		frame.setSize(xSize,ySize);  

		//Ajout des controles
		
		xbox_controllers = Xbox360Controller.initControllers(listPlayers) ;
		new GraphicalController(frame, listPlayers);
		
		
		backgroundsound = new Sound("Sounds/background.wav") ;
		backgroundsound.decrease_volume() ;
		
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
		gameDuration = Constant.GAME_DURATION;
	}
	
	/**
	 * Mise a jour des proprietes du monde sur les joueurs
	 */
	public void update() {
		// Lois du monde
		if (CURRENT_STATE == State.IN_GAME) {
			for (Player player : listPlayers) {
				if ((player.getX() > Menu.WIDTH) || (player.getX() < -100) || (player.getY() > Menu.HEIGHT) || (player.getY() < -150)) {
					player.decreaseNumberOfLife() ;
					player.resetLife() ;
					player.setX(Menu.WIDTH / 2 - player.w);
					player.setY(110);
					player.speedOnHorizontalAxis = 0 ;
					player.speedOnVerticalAxis = 0 ;
				}
				gravity.gravity(player);
				//On verifie si les joueurs ont lance des attaques
				player.verificationAttack();
				//On met a jour les temps lies aux attaques des joueurs (temps de recharge, temps d'affichage, etc)
				player.updateTimeAttack();
				player.checkCombo();
			}
			
				
			//Future intelligence artificielle
			
			//Si on est dans le jeu on joue la musique
			backgroundsound.resume() ;
			
			
		}else {
			//si on est pas dans le jeu on met la musique en pause
			backgroundsound.pause() ;
		}

		for(Xbox360Controller controller : this.xbox_controllers){
			controller.checkButtons() ;
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
