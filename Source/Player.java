import java.awt.Image;
import java.util.ArrayList;
import java.awt.CardLayout;

import javax.swing.ImageIcon;


public class Player {

	String name;
	int x,y,w,h,speedOnHorizontalAxis, speedOnVerticalAxis, atkState, health, jumps;
	PlayerStatus currentStatus;
	boolean jump, left, right, isJumping, isTurningRight, isAlive;
	Attack currentAttack;
	ArrayList<Attack> tabAttacks, tabLastAttacksForCombo;
	ArrayList<Combo> tabCombos;
	long lastTimerAttack, castingTimer;
	// Global Cooldown Timer
	long GCDTimer;
	ArrayList<Boolean> attack, attackReleased;
	Image imageBody;
	Image imageArm;
	Attack castingAttack;
	int numberOfLife;
	long waitedTimeForCombo;
	boolean isWaitingForCombo;
	
	/**
	 * Constructeur pour creer un joueur
	 * @param name nom du joueur
	 * @param i Abscisse du joueur
	 * @param j Ordonnee du joueur
	 * @param k Largeur du joueur
	 * @param l Hauteur du joueur
	 * @param attaques liste d'attaques specifiques au joueur
	 */
	public Player(String n, int k, int l, ArrayList<Attack> attacks, ArrayList<Combo> combos) {
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
		tabAttacks = attacks;
		tabCombos = combos;
		tabLastAttacksForCombo = new ArrayList<Attack>();
		tabLastAttacksForCombo.clear();
		atkState = Constant.ATK_STATE_READY;
		jumps = Constant.JUMP_BASE;
		attack = new ArrayList<Boolean>();
		attack.clear();
		attackReleased = new ArrayList<Boolean>();
		attackReleased.clear();
		for (int i = 0; i < attacks.size(); i++) {
			attack.add(false);
			attackReleased.add(false);
		}
		currentStatus = PlayerStatus.NORMAL;
		ImageIcon ii = new ImageIcon("images/character/body.png");
		Image imgBody = ii.getImage();
		imageBody = imgBody.getScaledInstance(80, -1, Image.SCALE_FAST);
		ii = new ImageIcon("images/character/arm.png");
		Image imgArm = ii.getImage();
		imageArm = imgArm.getScaledInstance(25, -1, Image.SCALE_FAST);
		numberOfLife = Constant.LIFE_NUMBER;
		waitedTimeForCombo = -1;
		isWaitingForCombo = false;
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
			currentStatus = PlayerStatus.FALLING ;
			Sound jump = new Sound("sounds/jump.wav") ;
			jump.play_once() ;
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
		if (b && atkState == Constant.ATK_STATE_READY) {
			if (!isWaitingForCombo) {
				attack.set(n, b);
			} else tabLastAttacksForCombo.add(tabAttacks.get(n));
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
			waitedTimeForCombo = main.engineLoop + Constant.TIME_WAIT_COMBO;
			isWaitingForCombo = true;
		}
	}

	/**
	 * Methode lancee a chaque update pour declencher les combos enregistres
	 */
	public void checkCombo() {
		if (isWaitingForCombo && waitedTimeForCombo <= main.engineLoop) {
			// Attente terminee
			waitedTimeForCombo = -1;
			isWaitingForCombo = false;
			// Declenchement des combos, lancement des attaques speciales correspondantes
			switch (tabLastAttacksForCombo.size()) {
				case 1:
					setAtk(tabAttacks.indexOf(tabLastAttacksForCombo.get(0)), true);
					break;
				case 2:
				case 3:
				{
					for (Combo c : tabCombos) {
						boolean check = true;
						for (int i = 0; i < c.nbOfBind; i++) {
							if (c.nbOfBind == tabLastAttacksForCombo.size()) {
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
					if (tabLastAttacksForCombo.size() > 3)
						setAtk(tabAttacks.indexOf(tabLastAttacksForCombo.get(0)), true);
					break;
			}
			tabLastAttacksForCombo.clear();
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
		currentAttack = tabAttacks.get(n);
		// Traitement de l'attaque
		long time = main.engineLoop;
		// Duee de l'affichage de l'attaque
		lastTimerAttack = time + tabAttacks.get(n).getTime();
		// Temps de recharge de l'attaque
		tabAttacks.get(n).setEffectiveCooldown(time + tabAttacks.get(n).getInfoCooldown());
		// Global Cooldown
		GCDTimer = time + Constant.GLOBAL_COOLDOWN;
		// L'attaque est en train d'etre produite
		atkState = Constant.ATK_STATE_ATTACKING;
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
	 * @return le temps system en millisecondes o√π le joueur a lance sa derniere attaque
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
		for (int i = 0; i < tabAttacks.size(); i++) {
			if (tabAttacks.get(i).getEffectiveCooldown() <= time) {
				tabAttacks.get(i).setEffectiveCooldown(0);
			}
		}
		
		switch (atkState) {
		case Constant.ATK_STATE_ATTACKING:
			if (this.collisionAttack() || lastTimerAttack <= time) {
				atkState = Constant.ATK_STATE_IN_COOLDOWN;
				lastTimerAttack = -1;
			}
			break;
		case Constant.ATK_STATE_IN_COOLDOWN:
			if (GCDTimer != -1 && time >= GCDTimer && time >= currentAttack.getEffectiveCooldown())	{
				currentAttack = null;
				atkState = Constant.ATK_STATE_READY;
				// remise a zero des combos
				for (int i = 0; i < tabCombos.size(); i++)
					setAtk(tabAttacks.indexOf(tabCombos.get(i).specialAttack), false);
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
	 * @return STATE_ATTACKING : le joueur est en train de lancer une attaque
	 * STATE_IN_COOLDOWN : le joueur a son temps de recharge global actif
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
	
	
	/**
	 * Methode permettant d'enlever des points de vie au joueur
	 * @param hit entier qui se soustrait a la vie du joueur
	 */
	public void receiveHit(int hit, int powerX, int powerY) {
		if (this.atkState == 3) {
			this.currentAttack = null;
			this.atkState = Constant.ATK_STATE_READY;
			this.lastTimerAttack = -1;
		}
		this.health += hit;
		double coef = 1 + this.health/5;
		double puissanceX = (powerX * coef)/10;
		double puissanceY = (powerY * coef)/10;
		calculation(puissanceX,puissanceY);
		this.eject((int)puissanceX, (int)puissanceY);
	}
	
	/**
	 * Methode de calcul de puissance minimal du coup, utilise pour donner une force minimale
	 */
	public static void calculation(double puissanceX, double puissanceY) {
		if (puissanceX > 10) {
		} else if (puissanceX < -10){
		} else if (puissanceX > 0){
			puissanceX = 10;
		} else if (puissanceX < 0){ 
			puissanceX = -10;
		}
		if (puissanceY > 10) {
		} else if (puissanceY < -10){
		} else if (puissanceY > 0){
			puissanceY = 10;
		} else if (puissanceY < 0){ 
			puissanceY = -10;
		}
	}
	

	/**
	 * Verification et lancement des attaques des joueurs
	 */
	public void verificationAttack() {
		long time = main.engineLoop;
		if (this.getState() == Constant.ATK_STATE_CASTING) {
			if (time >= this.castingTimer)
				// Lancement de l'attaque
				this.attack(this.tabAttacks.indexOf(this.castingAttack));
		}
		
		if (this.getState() == Constant.ATK_STATE_READY) {
			for (int i = 0; i < attack.size(); i++) {
				if (this.attack.get(i) && !this.attackReleased.get(i)) {
					this.attackReleased.set(i, true);
					this.castingAttack = this.tabAttacks.get(i);
					this.atkState = Constant.ATK_STATE_CASTING;
					this.castingTimer = time+this.tabAttacks.get(i).cast;
				}
			}
		}
	}
	
	
	/**
	 * Methode pour determiner si une attaque touche un adversaire
	 * @param player : Joueur actuel
	 * @return vrai si touchÈ, faux, si non touchÈ
	 */
	public boolean collisionAttack() {
		// Calcul de la position de l'attaque
		int tabXYWH[] = this.currentAttack.getAttackPosition(this);
		boolean collisionJoueur = false;
		boolean hasHit = false;
		// Collision avec des joueurs ?
		for (Player otherPlayer : GameEngine.listPlayers) {
			if (otherPlayer != this) {
				collisionJoueur = Collision.collision(tabXYWH[0], tabXYWH[1], tabXYWH[2], tabXYWH[3], otherPlayer.getX(), otherPlayer.getY(), otherPlayer.getW(), otherPlayer.getH());
				if (collisionJoueur) {
					if(this.isTurningRight) {
						otherPlayer.receiveHit(this.currentAttack.getDamage(),this.currentAttack.getPowerX(),this.currentAttack.getPowerY());
					} else {
						otherPlayer.receiveHit(this.currentAttack.getDamage(),-this.currentAttack.getPowerX(),this.currentAttack.getPowerY());
					}
					
					if (this.currentAttack.getName().equals("Small")) {
						Sound hit = new Sound("Sounds/smallhit.wav") ;
						hit.play_once() ;
					} else if (this.currentAttack.getName().equals("Big")) {
						Sound hit = new Sound("Sounds/bighit.wav") ;
						hit.play_once() ;
					}
					
					hasHit = true;
				}
			}
		}
		return hasHit;
	}

	public void decreaseNumberOfLife(){
		int newNumber = this.numberOfLife - 1 ;
		if (newNumber < 1) {
			GameEngine.CURRENT_STATE = State.IN_MENU ;
			((CardLayout) Menu.cards.getLayout()).show(Menu.cards, "mainmenu");
		} else {
			this.numberOfLife = newNumber ;
		}
	}



}
