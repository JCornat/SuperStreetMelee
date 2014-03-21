package Model;
import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import Levels.Decor;
import Levels.Levels;
import Sound.SoundManager;
import View.GraphicalView;
import Controller.ControllerAdministrator;
import Controller.GraphicalController;
import Controller.Xbox360Controller;

public class Game {

	public static ArrayList<Player> listPlayers;
	public static Menu frame;
	public static ArrayList<Decor> arrayDecor;
	public static int gameDuration;
	public static State CURRENT_STATE = State.IN_MENU ;
	public Gravity gravity;
	public ControllerAdministrator controllerAdministrator;
	
	/**
	 * Creation de la fenetre de jeu
	 */
	public Game() {
		gravity = new Gravity();
		Levels a = new Levels();
		
		//Chargement du niveau 1
		arrayDecor = a.levels.get(0);

		// Initilisation de la duree de la partie en sec
		gameDuration = Constant.GAME_DURATION;
		
		listPlayers = new ArrayList<Player>();
		listPlayers.clear();
		listPlayers.add(new Player("Joueur 1", 80, 80,1));
		listPlayers.add(new Player("Joueur 2", 80, 80,2));
		
		//Appel et ajout du pattern d'affichage	
		GraphicalView graphicalView = new GraphicalView(arrayDecor, listPlayers);
		frame = new Menu("SuperStreetMelee", graphicalView) ;
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setFocusable(true) ;

		// Ajout du son
		new SoundManager() ;
		
		SoundManager.sounds.get("background").decrease_volume() ;
		SoundManager.sounds.get("intro").loop() ;
		
		// Ajout des controles
		controllerAdministrator = new ControllerAdministrator(frame, listPlayers);
		controllerAdministrator.xbox_controllers = Xbox360Controller.initControllers() ;
		
		new GraphicalController();
	}
	
	
	/**
	 * Methode pour reinitialiser le jeu actuel, incluant les joueurs et le timer.
	 */
	public static void resetGame() {
		for (int i = 0; i < listPlayers.size(); i++) {
			listPlayers.get(i).playerPosition.setX(150+10*i);
			listPlayers.get(i).playerPosition.setY(110);
			listPlayers.get(i).resetLife();
			listPlayers.get(i).playerSpeed.speedOnHorizontalAxis = 0;
			listPlayers.get(i).playerSpeed.speedOnVerticalAxis = 0;
			listPlayers.get(i).playerInfoBoolean.left = false;
			listPlayers.get(i).playerInfoBoolean.right = false;
			listPlayers.get(i).playerInfoBoolean.jump = false;
			listPlayers.get(i).playerInfoBoolean.isJumping = false;
			listPlayers.get(i).playerInfo.numberOfLife = Constant.LIFE_NUMBER ;
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
				//Limites du terrain
				//Pour la suite, essayer de prendre des parametres venant de Levels, pour rende la chose plus dynamique et simple a gerer
				if ((player.playerPosition.x > Menu.WIDTH+500) || (player.playerPosition.x < -500) || (player.playerPosition.y > Menu.HEIGHT+500) || (player.playerPosition.y < -500)) {
					player.playerInfo.decreaseNumberOfLife();
					player.resetLife() ;
					player.playerPosition.setX(Menu.WIDTH / 2 - player.playerPosition.w);
					player.playerPosition.setY(110);
					player.playerSpeed.speedOnHorizontalAxis = 0;
					player.playerSpeed.speedOnVerticalAxis = 0;
				}
				gravity.gravity(player);
				//On verifie si les joueurs ont lance des attaques
				player.verificationAttack();
				//On met a jour les temps lies aux attaques des joueurs (temps de recharge, temps d'affichage, etc)
				player.updateTimeAttack();
				player.checkCombo();
			}
			//Si on est dans le jeu on joue la musique
			SoundManager.sounds.get("background").resume() ;
		} else {
			//si on est pas dans le jeu on met la musique en pause
			SoundManager.sounds.get("background").pause() ;
		}

		for(Xbox360Controller controller : controllerAdministrator.xbox_controllers){
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
				Game.CURRENT_STATE = State.IN_MENU ;
				((CardLayout) Menu.cards.getLayout()).show(Menu.cards, "mainmenu");
			}
		}
	}

	public void render() {
		frame.repaint();
	}
	
}
