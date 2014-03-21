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
	public static Player player;
	public static Menu frame;
	public static ArrayList<Attack> arrayOfAttacksForOneCharacter;
	public static ArrayList<Combo> arrayOfComboForOneCharacter;
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

		// Creation des attaques
		arrayOfAttacksForOneCharacter = new ArrayList<Attack>();
		arrayOfAttacksForOneCharacter.clear();
		arrayOfComboForOneCharacter = new ArrayList<Combo>();
		arrayOfComboForOneCharacter.clear();

		// Creation des attaques	
		Attack small = new Attack("Small", 55, 5, 5, 0, 10, 0, 20, 20, false);
		Attack medium = new Attack("Medium", 73, 7, 10, 0, 75, 100, 60, 60,
				false);
		Attack big = new Attack("Big", 80, 10, 15, 30, 40, 150, 100, 100, false);
		// A venir
		// Attack charge = new Attack("Charge", 80, 10, 5, 0, 40, 150, 100, 100,
		// false);

		// Combo 1
		Attack specialAttack1 = new Attack("Special1", 100, 20, 20, 25, 60, 0,
				100, 100, true);
		Combo combo1 = new Combo(big, small, specialAttack1);

		// Combo 2
		Attack specialAttack2 = new Attack("Special2", 100, 20, 25, 27, 60, 0,
				100, 100, true);
		Combo combo2 = new Combo(medium, small, specialAttack2);

		// Combo 3
		Attack specialAttack3 = new Attack("Special3", 100, 20, 35, 30, 60, 0,
				100, 100, true);
		Combo combo3 = new Combo(medium, small, big, specialAttack3);

		// Ajouts dans la liste des attaques
		// /!\ Penser a update la constante ATTACK_NUMBER
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
			listPlayers.add(new Player("Joueur 1", 80, 80,
					arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter,
					SkinsOfPlayers[0]));
			break;
		case 2:
			listPlayers.add(new Player("Joueur 1", 80, 80,
					arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter,
					SkinsOfPlayers[0]));
			listPlayers.add(new Player("Joueur 2", 80, 80,
					arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter,
					SkinsOfPlayers[1]));
			break;
		case 3:
			listPlayers.add(new Player("Joueur 1", 80, 80,
					arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter,
					SkinsOfPlayers[0]));
			listPlayers.add(new Player("Joueur 2", 80, 80,
					arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter,
					SkinsOfPlayers[1]));
			listPlayers.add(new Player("Joueur 3", 80, 80,
					arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter,
					SkinsOfPlayers[2]));
			break;
		case 4:
			listPlayers.add(new Player("Joueur 1", 80, 80,
					arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter,
					SkinsOfPlayers[0]));
			listPlayers.add(new Player("Joueur 2", 80, 80,
					arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter,
					SkinsOfPlayers[1]));
			listPlayers.add(new Player("Joueur 3", 80, 80,
					arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter,
					SkinsOfPlayers[2]));
			listPlayers.add(new Player("Joueur 4", 80, 80,
					arrayOfAttacksForOneCharacter, arrayOfComboForOneCharacter,
					SkinsOfPlayers[3]));
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

		graphicalView.initGView(arrayDecor, arrayOfAttacksForOneCharacter,
				listPlayers);
		
	}

	/**
	 * Mise a jour des proprietes du monde sur les joueurs
	 */
	public void update() {
		DecreaseNumberOfLife decreaseNumberOfLife = new DecreaseNumberOfLife();
		// Lois du monde
		if (CURRENT_STATE == State.IN_GAME) {
			for (Player player : listPlayers) {
				// Limites du terrain
				// Pour la suite, essayer de prendre des paramètres venant de
				// Levels, pour rende la chose plus dynamique et simple a gerer
				if ((player.playerPosition.x > Menu.WIDTH + 500)
						|| (player.playerPosition.x < -500)
						|| (player.playerPosition.y > Menu.HEIGHT + 500)
						|| (player.playerPosition.y < -500)) {
					decreaseNumberOfLife.calculation(player.playerInfo);
					player.resetLife();
					player.playerPosition.setX(Menu.WIDTH / 2
							- player.playerPosition.w);
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
