import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class Player {

	
	
	// State
	int ATK_STATE_READY = Constant.ATK_STATE_READY;
	int ATK_STATE_ATTACKING = Constant.ATK_STATE_ATTACKING;
	int ATK_STATE_IN_COOLDOWN = Constant.ATK_STATE_IN_COOLDOWN;
	int ATK_STATE_CASTING = Constant.ATK_STATE_CASTING;
   
	
	String name;
	int x,y,w,h,speedOnHorizontalAxis, speedOnVerticalAxis, atkState, health, jumps, jumpsBase;

	PlayerStatus currentStatus;
	boolean jump, left, right, isJumping, isTurningRight, isAlive;
	
	Attack currentAttack;
	ArrayList<Attack> tabAttaques;
	long lastTimerAttack, castingTimer;
	
	// Global Cooldown Timer
	long GCDTimer;
	ArrayList<Boolean> attack, attackReleased;

	Image imageBody;
	

	Image imageArm;

	Attack castingAttack;
	
	/**
	 * Constructeur pour creer un joueur
	 * @param name nom du joueur
	 * @param i Abscisse du joueur
	 * @param j Ordonnee du joueur
	 * @param k Largeur du joueur
	 * @param l Hauteur du joueur
	 * @param attaques liste d'attaques specifiques au joueur
	 */
	public Player(String n, int k, int l, ArrayList<Attack> attaques) {
		name = n;
		x = 0; 
		y = 0;
		w = k;
		h = l;
		jump = false;
		left = false; 
		right = false;
		speedOnHorizontalAxis = 0;
		speedOnVerticalAxis = 0;
		health = Constant.MIN_HEALTH;
		isAlive = true;
		isTurningRight = true;
		currentAttack = null;
		lastTimerAttack = -1;
		GCDTimer = -1;
		tabAttaques = attaques;
		atkState = ATK_STATE_READY;
		jumpsBase = 2;
		jumps = jumpsBase;
		attack = new ArrayList<Boolean>();
		attack.clear();
		attack.add(false);
		attack.add(false);
		attackReleased = new ArrayList<Boolean>();
		attackReleased.clear();
		attackReleased.add(false);
		attackReleased.add(false);
		currentStatus = PlayerStatus.NORMAL;
		ImageIcon ii = new ImageIcon("images/character/body.png");
		Image imgBody = ii.getImage();
		imageBody = imgBody.getScaledInstance(80, -1, Image.SCALE_FAST);
		ii = new ImageIcon("images/character/arm.png");
		Image imgArm = ii.getImage();
		imageArm = imgArm.getScaledInstance(25, -1, Image.SCALE_FAST);
	}
	
	public String getName() {
		return name;
	}
	public int getX() {
		return x;
	}
	public void setX(int i) {
		x = i;
	}
	public int getY() {
		return y;
	}
	public void setY(int i) {
		y = i;
	}
	public int getW() {
		return w;
	}
	public void setW(int i) {
		w = i;
	}
	public int getH() {
		return h;
	}
	public void setH(int i) {
		h = i;
	}
	public void setVitesseH(int i) {
		speedOnHorizontalAxis = i;
	}
	public void setJump(boolean b) {

		if (b && jumps>0) {
			jumps--;
			speedOnVerticalAxis = -80;
			isJumping = true;
		}
		jump = b;
	}
	public boolean getJump() {
		return jump;
	}
	public void setLeft(boolean b) {
		left = b;
	}
	public boolean getLeft() {
		return left;
	}
	public void setRight(boolean b) {
		right = b;
	}
	public boolean getRight() {
		return right;
	}
	
	/**
	 * Methode utilisee quand le joueur se tourne d'un cote
	 * @param right cote duquel le joueur est tourne : droite->vrai, gauche->faux
	 */
	public void setTurned(boolean right) {
		isTurningRight = right;
	}
	public boolean getTurned() {
		return isTurningRight;
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
		if (b && atkState == ATK_STATE_READY) {
			attack.set(n, true);
		} else {
			attack.set(n, false);
		}
		
		if (!b) {
			attackReleased.set(n, false);
		}
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
		currentAttack = tabAttaques.get(n);
		// Traitement de l'attaque
		long time = main.engineLoop;
		// Duee de l'affichage de l'attaque
		lastTimerAttack = time + tabAttaques.get(n).getTime();
		// Temps de recharge de l'attaque
		tabAttaques.get(n).setEffectiveCooldown(time + tabAttaques.get(n).getInfoCooldown());
		// Global Cooldown
		GCDTimer = time + tabAttaques.get(n).infoCooldown;
		// L'attaque est en train d'etre produite
		atkState = ATK_STATE_ATTACKING;
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
	 * @return le temps system en millisecondes où le joueur a lance sa derniere attaque
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
		long time = main.engineLoop;
		// Update des temps de recharge des attaques
		for (int i = 0; i < tabAttaques.size(); i++) {
			if (tabAttaques.get(i).getEffectiveCooldown() <= time) {
				tabAttaques.get(i).setEffectiveCooldown(0);
			}
		}
		
		switch (atkState) {
		case Constant.ATK_STATE_ATTACKING:
			if (CollisionAttack.collisionAttack(this) || lastTimerAttack <= time) {
				currentAttack = null;
				atkState = ATK_STATE_IN_COOLDOWN;
				lastTimerAttack = -1;
			}
			break;
		case Constant.ATK_STATE_IN_COOLDOWN:
			if (GCDTimer != -1 && time >= GCDTimer)	{
				atkState = ATK_STATE_READY;
				GCDTimer = -1;
			}
			break;
		default: 
			break;
		}		
	}
	
	public void setState(int state) {
		this.atkState = state;
	}
	
	/**
	 * Methode permettant de connaitre l'etat du joueur dans son combat
	 * @return STATE_ATTACKING : le joueur est en train de lancer une attaque<br>
	 * STATE_IN_COOLDOWN : le joueur a son temps de recharge global actif<br>
	 * STATE_READY : le joueur est pret a lancer des attaques
	 */
	public int getState() {
		return atkState;
	}
	
	

	/**
	 * Methode permettant de soigner le joueur
	 * @param heal entier qui s'ajoute a la vie du joueur
	 */
	public void receiveHeal(int heal) {
		int temp = this.health - heal;
		if (this.isAlive) {
			if (temp < Constant.MIN_HEALTH) {
				this.health = Constant.MIN_HEALTH;
			} else {
				this.health -= heal;
			}
		}
	}

	/**
	 * Methode permettant de maximiser les points de vie du joueur, et de le remettre en vie
	 */
	public void resetLife() {
		health = Constant.MIN_HEALTH;
		isAlive = true;
	}

	/**
	 * Appliquer une force a un joueur
	 * @param i Force en X appliquee au joueur
	 * @param j	Force en Y appliquee au joueur
	 */
	public void eject(int i, int j) {
		currentStatus = PlayerStatus.EJECTED;
		speedOnHorizontalAxis = i;
		speedOnVerticalAxis = -j;
	}


}
