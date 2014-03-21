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
	public static State CURRENT_STATE = State.IN_MENU;
	public Gravity gravity;
	public static ControllerAdministrator controllerAdministrator;
	public static int PlayerNumber;
	public static int[] SkinsOfPlayers;
	public static int LevelIndex;	
	public static GraphicalView graphicalView;

	/**
	 * Creation de la fenetre de jeu
	 */
	public Game() {
		gravity = new Gravity();
		PlayerNumber = 0;
		SkinsOfPlayers = new int[4];

		// Initilisation de la duree de la partie en sec
		gameDuration = Constant.GAME_DURATION;

		listPlayers = new ArrayList<Player>();
		listPlayers.clear();
		listPlayers.add(new Player("Joueur 1", 80, 80, 1));
		listPlayers.add(new Player("Joueur 2", 80, 80, 2));
		
		//Appel et ajout du pattern d'affichage	
		GraphicalView graphicalView = new GraphicalView();
		frame = new Menu("SuperStreetMelee", graphicalView) ;

		// Appel et ajout du pattern d'affichage
		this.graphicalView = new GraphicalView();
		frame = new Menu("SuperStreetMelee", graphicalView);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setFocusable(true);

		// Ajout du son
		new SoundManager();

		SoundManager.sounds.get("background").decrease_volume();
		SoundManager.sounds.get("intro").loop();

	}

	/**
	 * Methode pour reinitialiser le jeu actuel, incluant les joueurs et le
	 * timer.
	 */
	public static void resetGame() {
		for (int i = 0; i < listPlayers.size(); i++) {
			listPlayers.get(i).resetLife();
			listPlayers.get(i).playerSpeed.speedOnHorizontalAxis = 0;
			listPlayers.get(i).playerSpeed.speedOnVerticalAxis = 0;
			listPlayers.get(i).playerInfoBoolean.left = false;
			listPlayers.get(i).playerInfoBoolean.right = false;
			listPlayers.get(i).playerInfoBoolean.jump = false;
			listPlayers.get(i).playerInfoBoolean.isJumping = false;
			listPlayers.get(i).playerInfo.numberOfLife = Constant.LIFE_NUMBER;
		}
		placePlayers() ;
		gameDuration = Constant.GAME_DURATION;
	}

	private static void placePlayers() {
		switch (PlayerNumber) {
		case 1:
			listPlayers.get(0).playerPosition.setX(Menu.WIDTH / 2 - 35) ;
			listPlayers.get(0).playerPosition.setY(110) ;
			break;
		case 2:
			listPlayers.get(0).playerPosition.setX(Menu.WIDTH / 3 - 150) ;
			listPlayers.get(0).playerPosition.setY(110) ;
			listPlayers.get(1).playerPosition.setX(Menu.WIDTH - 275) ;
			listPlayers.get(1).playerPosition.setY(110) ;
			break;
		case 3:
			listPlayers.get(0).playerPosition.setX(Menu.WIDTH / 3 - 150 ) ;
			listPlayers.get(0).playerPosition.setY(110) ;
			listPlayers.get(1).playerPosition.setX(Menu.WIDTH / 2 - 35) ;
			listPlayers.get(1).playerPosition.setY(110) ;
			listPlayers.get(2).playerPosition.setX(Menu.WIDTH / 2 + 250) ;
			listPlayers.get(2).playerPosition.setY(110) ;
			break;
		case 4:
			listPlayers.get(0).playerPosition.setX(Menu.WIDTH / 4 - 75) ;
			listPlayers.get(0).playerPosition.setY(110) ;
			listPlayers.get(1).playerPosition.setX(Menu.WIDTH / 3 + 40) ;
			listPlayers.get(1).playerPosition.setY(110) ;
			listPlayers.get(2).playerPosition.setX(Menu.WIDTH / 2 + 30) ;
			listPlayers.get(2).playerPosition.setY(110) ;
			listPlayers.get(3).playerPosition.setX(Menu.WIDTH - 275) ;
			listPlayers.get(3).playerPosition.setY(110) ;
			break;
		default:
			break;
		}	
	}

	public static void InitPlayersAndMap() {
		switch (PlayerNumber) {
		case 1:
			listPlayers.add(new Player("Joueur 1", 80, 80, SkinsOfPlayers[0]));
			break;
		case 2:
			listPlayers.add(new Player("Joueur 1", 80, 80, SkinsOfPlayers[0]));
			listPlayers.add(new Player("Joueur 2", 80, 80, SkinsOfPlayers[1]));
			break;
		case 3:
			listPlayers.add(new Player("Joueur 1", 80, 80, SkinsOfPlayers[0]));
			listPlayers.add(new Player("Joueur 2", 80, 80, SkinsOfPlayers[1]));
			listPlayers.add(new Player("Joueur 3", 80, 80, SkinsOfPlayers[2]));
			break;
		case 4:
			listPlayers.add(new Player("Joueur 1", 80, 80, SkinsOfPlayers[0]));
			listPlayers.add(new Player("Joueur 2", 80, 80, SkinsOfPlayers[1]));
			listPlayers.add(new Player("Joueur 3", 80, 80, SkinsOfPlayers[2]));
			listPlayers.add(new Player("Joueur 4", 80, 80, SkinsOfPlayers[3]));
			break;
		default:
			break;
		}

		Levels a = new Levels();
		arrayDecor = a.levels.get(LevelIndex);
		
		controllerAdministrator = new ControllerAdministrator(frame,
				listPlayers);
		controllerAdministrator.xbox_controllers = Xbox360Controller
				.initControllers();
		new GraphicalController();

		graphicalView.initGView(arrayDecor, listPlayers);
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
				// On verifie si les joueurs ont lance des attaques
				player.verificationAttack();
				// On met a jour les temps lies aux attaques des joueurs (temps
				// de recharge, temps d'affichage, etc)
				player.updateTimeAttack();
				player.checkCombo();
			}
			// Si on est dans le jeu on joue la musique
			SoundManager.sounds.get("background").resume();
		} else {
			// si on est pas dans le jeu on met la musique en pause
			SoundManager.sounds.get("background").pause();
		}

		if (GraphicalView.init) {
			for (Xbox360Controller controller : controllerAdministrator.xbox_controllers) {
				controller.checkButtons();
			}
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
				Game.CURRENT_STATE = State.IN_MENU;
				((CardLayout) Menu.cards.getLayout()).show(Menu.cards,
						"mainmenu");
			}
		}
	}

	public void render() {
		frame.repaint();
	}

}
