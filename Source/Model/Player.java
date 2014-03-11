package Model;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.CardLayout;

import javax.swing.ImageIcon;

import Sound.SoundManager;
import View.Animator;
import View.AnimatorOneLoop;
import View.BufferedImageLoader;
import View.GraphicalPlayer;
import View.SpriteArray;
import View.SpriteSheet;


public class Player {

	public PlayerInfo playerInfo;
	public PlayerInfoBoolean playerInfoBoolean;
	public PlayerStatus currentStatus;
	public PlayerPosition playerPosition;
	public PlayerSpeed playerSpeed;
	public GraphicalPlayer graphicalPlayer;
	public Attack currentAttack;
	public ArrayList<Attack> tabAttacks, tabLastAttacksForCombo;
	public ArrayList<Combo> tabCombos;
	public long lastTimerAttack, castingTimer;
	// Global Cooldown Timer
	public long GCDTimer;
	public ArrayList<Boolean> attack, attackReleased;
	public Attack castingAttack;
	public boolean booleanJump;
	public long waitedTimeForCombo;
	public boolean isWaitingForCombo;
	
	/**
	 * Constructeur pour creer un joueur
	 * @param name nom du joueur
	 * @param i Abscisse du joueur
	 * @param j Ordonnee du joueur
	 * @param k Largeur du joueur
	 * @param l Hauteur du joueur
	 * @param attaques liste d'attaques specifiques au joueur
	 */

	public Player(String n, int k, int l, ArrayList<Attack> attacks, ArrayList<Combo> combos, int skin) {
		playerInfo = new PlayerInfo(n);
		playerInfoBoolean = new PlayerInfoBoolean();
		playerPosition = new PlayerPosition(k,l);
		playerSpeed = new PlayerSpeed();
		currentAttack = null;
		lastTimerAttack = -1;
		GCDTimer = -1;
		tabAttacks = attacks;
		tabCombos = combos;
		tabLastAttacksForCombo = new ArrayList<Attack>();
		tabLastAttacksForCombo.clear();
		attack = new ArrayList<Boolean>();
		attack.clear();
		attackReleased = new ArrayList<Boolean>();
		attackReleased.clear();
		for (int i = 0; i < attacks.size(); i++) {
			attack.add(false);
			attackReleased.add(false);
		}
		currentStatus = PlayerStatus.NORMAL;
		graphicalPlayer = new GraphicalPlayer(skin);
		waitedTimeForCombo = -1;
		isWaitingForCombo = false;
	}
	
	public void setJump(boolean b) {
		if (b && playerInfo.jumps>0) {
			playerInfo.jumps--;
			playerSpeed.speedOnVerticalAxis = -80;
			playerInfoBoolean.isJumping = true;
			currentStatus = PlayerStatus.FALLING ;
			SoundManager.sounds.get("jump").play_once() ;
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
	 * @param b
	 */
	public void setAtk(int n, boolean b) {
		if (b && playerInfo.atkState == Constant.ATK_STATE_READY && tabAttacks.get(n).getEffectiveCooldown() <= GameEngine.engineLoop) {
			if (!isWaitingForCombo) {
				attack.set(n, b);
			} else if (isWaitingForCombo && tabAttacks.get(n).isBind) {
				tabLastAttacksForCombo.add(tabAttacks.get(n));
			}
		} else {
			attack.set(n, false);
		}
		if (!b) {
			attackReleased.set(n, b);
		}
	}

	/**
	 * Methode pour initialiser la sequence de combo ( = combo possible)
	 */
	public void initCombo() {
		if (!isWaitingForCombo && waitedTimeForCombo == -1) {
			waitedTimeForCombo = GameEngine.engineLoop + Constant.TIME_WAIT_COMBO;
			isWaitingForCombo = true;
		}
	}

	/**
	 * Methode lancee a chaque update pour declencher les combos enregistres
	 */
	public void checkCombo() {
		if (enAttenteCombo()) {
			// Attente terminee
			waitedTimeForCombo = -1;
			isWaitingForCombo = false;
			// Declenchement des combos, lancement des attaques speciales correspondantes
			int nbBoutonsDejaAppuyes = tabLastAttacksForCombo.size();
			switch (nbBoutonsDejaAppuyes) {
				case 1:
					setAtk(tabAttacks.indexOf(tabLastAttacksForCombo.get(0)), true);
					break;
				// Combos ˆ 2 touches et plus
				case 2:
				case 3:
				{
					for (Combo c : tabCombos) {
						boolean check = true;
						for (int i = 0; i < c.nbOfBind; i++) {
							if (c.nbOfBind == nbBoutonsDejaAppuyes) {
								if (c.binds[i] != tabLastAttacksForCombo.get(i))
									check = false;
							} else check = false;
						}
						if (check) {
							setAtk(tabAttacks.indexOf(c.specialAttack), true);
							break;
						}
					}
					setAtk(tabAttacks.indexOf(tabLastAttacksForCombo.get(0)), true);
					break;
				}
				default:
					if (nbBoutonsDejaAppuyes > 3)
						setAtk(tabAttacks.indexOf(tabLastAttacksForCombo.get(0)), true);
					break;
			}
		}
	}

	private boolean enAttenteCombo() {
		return isWaitingForCombo && waitedTimeForCombo <= GameEngine.engineLoop;
	}

	
	/**
	 * Methode utilisee lorsque le joueur lance une attaque.
	 * Determine si l'attaque est disponible pour le joueur, s'il peut la lancer,
	 * si oui lance l'attaque, et enleve des points de vie a un joueur si il y a collision avec ce dernier
	 * @param n nom de l'attaque
	 * @param listPlayers tous les joueurs du jeu
	 */
	public void attack(int n) {
		castingAttack = null;
		currentAttack = tabAttacks.get(n);
		// Traitement de l'attaque
		long time = GameEngine.engineLoop;
		// Duee de l'affichage de l'attaque
		lastTimerAttack = time + tabAttacks.get(n).getTime();
		// Temps de recharge de l'attaque
		tabAttacks.get(n).setEffectiveCooldown(time + tabAttacks.get(n).getInfoCooldown());
		// Global Cooldown
		GCDTimer = time + Constant.GLOBAL_COOLDOWN;
		// L'attaque est en train d'etre produite
		playerInfo.atkState = Constant.ATK_STATE_ATTACKING;
	}
	
	
	/**
	 * Methode utilisee pour connaitre l'attaque que le joueur est en train de lancer
	 * @return l'attaque que le joueur est en train de lancer
	 */
	public Attack getAttaque() {
		return currentAttack;
	}
	
	/**
	 * Methode utilisee pour savoir si le joueur a le temps de recharge global (ne peut pas lancer d'attaque)
	 * @return vrai si il a le temps de recharge global
	 */
	public boolean hasGlobalCooldown() {
		if (GCDTimer == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Methode utilisee pour connaitre le moment de la derniere attaque du joueur
	 * @return le temps system en millisecondes oÃ¹ le joueur a lance sa derniere attaque
	 */
	public long getLastTimerAttack() {
		return lastTimerAttack;
	}
	
	/**
	 * Methode permettant de mettre a jour 
	 * le temps de recharge des attaques du joueur,
	 * son temps de recharge global
	 * et l'affichage de sa derniere attaque
	 */
	public void updateTimeAttack() {
		CollisionAttack collisionAttack = new CollisionAttack();
		long time = GameEngine.engineLoop;
		// Update des temps de recharge des attaques
		for (int i = 0; i < tabAttacks.size(); i++) {
			if (tabAttacks.get(i).getEffectiveCooldown() <= time) {
				tabAttacks.get(i).setEffectiveCooldown(0);
				// L'attaque peut de nouveau �tre lancŽe
				setAtk(i, false);
			}
		}
		
		switch (playerInfo.atkState) {
		case Constant.ATK_STATE_ATTACKING:
			if (collisionAttack.Calculation(this, currentAttack)) {
				playerInfo.atkState = Constant.ATK_HAS_HIT;
			} else if (lastTimerAttack <= time) {
				playerInfo.atkState = Constant.ATK_STATE_IN_COOLDOWN;
				lastTimerAttack = -1;
			}
			break;
		case Constant.ATK_STATE_IN_COOLDOWN:
			// Si le player n'est plus en cooldown global
			/* Le cooldown des attaques, propre au lancement de chaque attaque,
			 et gere dans la methode setAtack() */
			if (GCDTimer != -1 && time >= GCDTimer)	{
				// Desactivation de l'attaque speciale et des attaques necessaire a son lancement
				if (currentAttack.isSpecialAttack) {
					for (Attack a : tabLastAttacksForCombo)
						setAtk(tabAttacks.indexOf(a), false);
				}
				// Desactivation des attaques basiques
				for (int i = 0; i < tabAttacks.size(); i++) {
					setAtk(i, false);
				}
				// On vide le tableau de sauvegarde des attaques
				tabLastAttacksForCombo.clear();
				currentAttack = null;
				// Remise a zero des combos
				for (int j = 0; j < tabCombos.size(); j++) {
					setAtk(tabAttacks.indexOf(tabCombos.get(j).specialAttack), false);
				}
				currentStatus = PlayerStatus.NORMAL;
				playerInfo.atkState = Constant.ATK_STATE_READY;
				GCDTimer = -1;
			}
			break;
		case Constant.ATK_HAS_HIT:
			if (lastTimerAttack <= time) {
				playerInfo.atkState = Constant.ATK_STATE_IN_COOLDOWN;
				lastTimerAttack = -1;
			}
			break;
		default: 
			break;
		}		
	}
	
	public void setState(int state) {
		playerInfo.atkState = state;
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
		if (this.playerInfo.atkState == Constant.ATK_STATE_CASTING) {
			if (time >= this.castingTimer) {
				// Lancement de l'attaque
				this.attack(this.tabAttacks.indexOf(this.castingAttack));
			}
		}
		
		if (this.playerInfo.atkState == Constant.ATK_STATE_READY) {
			for (int i = 0; i < attack.size(); i++) {
				if (this.attack.get(i) && !this.attackReleased.get(i)) {
					this.attackReleased.set(i, true);
					this.castingAttack = this.tabAttacks.get(i);
					playerInfo.atkState = Constant.ATK_STATE_CASTING;
					this.castingTimer = time+this.tabAttacks.get(i).cast;
				}
			}
		}
	}

	public void eject(int puissanceX, int puissanceY) {
		currentStatus = PlayerStatus.EJECTED;
		playerSpeed.eject(puissanceX, puissanceY);
	}

}
