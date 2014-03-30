package Model;
import java.util.ArrayList;
import Sound.SoundManager;
import View.GraphicalPlayer;


public class Player {

	public PlayerInfo playerInfo;
	public PlayerInfoBoolean playerInfoBoolean;
	public PlayerStatus currentStatus;
	public PlayerPosition playerPosition;
	public PlayerSpeed playerSpeed;
	public CombatMgr playerCombatMgr;
	public GraphicalPlayer graphicalPlayer;
	
	/**
	 * Constructeur pour creer un joueur
	 * @param name nom du joueur
	 * @param i Abscisse du joueur
	 * @param j Ordonnee du joueur
	 * @param k Largeur du joueur
	 * @param l Hauteur du joueur
	 * @param attaques liste d'attaques specifiques au joueur
	 */

	public Player(String n, int k, int l, int skin) {
		playerInfo = new PlayerInfo(n);
		playerInfoBoolean = new PlayerInfoBoolean();
		playerPosition = new PlayerPosition(k,l);
		playerSpeed = new PlayerSpeed();
		playerCombatMgr = new CombatMgr();
		currentStatus = PlayerStatus.NORMAL;
		graphicalPlayer = new GraphicalPlayer(skin);
	}
	
	public void setJump(boolean b) {
		if (b && playerInfo.jumps>0) {
			playerInfo.jumps--;
			playerSpeed.speedOnVerticalAxis = -80;
			playerInfoBoolean.isJumping = true;
			currentStatus = PlayerStatus.FALLING ;
			SoundManager.play_once("jump") ;
		}
		playerInfoBoolean.jump = b;
		if(!b) {
			playerInfoBoolean.isJumping=false;
		}
	}


	
	/* ******************************************************* */
	/* ********************	------------- ******************** */
	/* ******************** COMBAT SYSTEM ******************** */
	/* ********************	------------- ******************** */
	/* ******************************************************* */
	
	/**
	 * Appelle l'attaque n
	 * @param num
	 * @param enable
	 */
	public void setAtk(int num, boolean enable) {
		playerCombatMgr.enableOrDisableAttackForLaunching(this, num, enable);
	}

	/**
	 * Methode pour initialiser la sequence de combo ( = combo possible)
	 */
	public void initCombo() {
		if (!playerInfoBoolean.isWaitingForCombo && playerCombatMgr.waitedTimeForCombo == -1) {
			playerCombatMgr.waitedTimeForCombo = GameEngine.engineLoop + Constant.TIME_WAIT_COMBO;
			playerInfoBoolean.isWaitingForCombo = true;
		}
	}

	/**
	 * Methode lancee a chaque update pour declencher les combos enregistres
	 */
	public void checkCombo() {
		if (isNoMoreWaitingForCombo()) {
			playerInfoBoolean.isWaitingForCombo = false;
			playerCombatMgr.processCombo(this);
		}
	}

	private boolean isNoMoreWaitingForCombo() {
		return playerInfoBoolean.isWaitingForCombo && playerCombatMgr.waitedTimeForCombo <= GameEngine.engineLoop;
	}
	
	/**
	 * Methode permettant de mettre a jour 
	 * le temps de recharge des attaques du joueur,
	 * son temps de recharge global
	 * et l'affichage de sa derniere attaque
	 */
	public void updateTimeAttack() {
		long time = GameEngine.engineLoop;
		// Update des temps de recharge des attaques
		playerCombatMgr.updateCooldownAllAttacks(this, time);
		playerCombatMgr.updateTimeAttackStates(this, time);
	}

	/**
	 * Methode permettant de soigner le joueur
	 * @param heal entier qui s'ajoute a la vie du joueur
	 */
	public void receiveHeal(int heal) {
		int temp = this.playerInfo.health - heal;
		if (playerInfoBoolean.isAlive) {
			if (temp < Constant.MIN_HEALTH) {
				this.playerInfo.health = Constant.MIN_HEALTH;
			} else {
				this.playerInfo.health -= heal;
			}
		}
	}

	/**
	 * Methode permettant de maximiser les points de vie du joueur, et de le remettre en vie
	 */
	public void resetLife() {
		playerInfo.health = Constant.MIN_HEALTH;
		playerInfoBoolean.isAlive = true;
	}
	
	/**
	 * Verification et lancement des attaques des joueurs
	 */
	public void verificationAttack() {
		long time = GameEngine.engineLoop;
		playerCombatMgr.checkAndLaunchAttacks(this, time);
	}

	public void eject(int puissanceX, int puissanceY) {
		currentStatus = PlayerStatus.EJECTED;
		playerSpeed.eject(puissanceX, puissanceY);
	}

}
