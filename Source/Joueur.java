import java.util.ArrayList;


public class Joueur {

	// Max Health of the player
	final int MAX_HEALTH = 100;
	
	// Global Cooldown of the player
	public final long GLOBAL_COOLDOWN = 300;
	
	
	// State
	public final int ATK_STATE_READY = 0;
	public final int ATK_STATE_ATTACKING = 1;
	public final int ATK_STATE_IN_COOLDOWN = 2;
	public final int ATK_STATE_CASTING = 3;
	
	public final int NORMAL = 0;
	public final int EJECTED = 1;
	
	public int MAX_RUN_SPEED = 30;
	
	String name;
	int x,y,w,h,vitesseX, vitesseY, atkState, health, jumps, jumpsBase, status;
	boolean jump, left, right, isJumping, turnedRight, isAlive;
	
	Attaque currentAttack;
	ArrayList<Attaque> tabAttaques;
	long lastTimerAttack, castingTimer;
	
	// Global Cooldown Timer
	long GCDTimer;
	ArrayList<Boolean> atk, atkReleased;

	Attaque castingAttack;
	
	/**
	 * Constructeur pour creer un joueur
	 * @param name nom du joueur
	 * @param i Abscisse du joueur
	 * @param j Ordonnee du joueur
	 * @param k Largeur du joueur
	 * @param l Hauteur du joueur
	 * @param attaques liste d'attaques specifiques au joueur
	 */
	public Joueur(String n, int i, int j, int k, int l, ArrayList<Attaque> attaques) {
		name = n;
		x = i; y = j;
		w = k; h = l;
		jump = false;
		left = false; 
		right = false;
		vitesseX = 0;
		vitesseY = 0;
		health = MAX_HEALTH;
		isAlive = true;
		turnedRight = true;
		currentAttack = null;
		lastTimerAttack = -1;
		GCDTimer = -1;
		tabAttaques = attaques;
		atkState = ATK_STATE_READY;
		jumpsBase = 2;
		jumps = jumpsBase;
		atk = new ArrayList<Boolean>();
		atk.clear();
		atk.add(false);
		atk.add(false);
		atkReleased = new ArrayList<Boolean>();
		atkReleased.clear();
		atkReleased.add(false);
		atkReleased.add(false);
		status = NORMAL;
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
		vitesseX = i;
	}
	public void setJump(boolean b) {

		if (b && jumps>0) {
			jumps--;
			vitesseY = -80;
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
		turnedRight = right;
	}
	public boolean getTurned() {
		return turnedRight;
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
			atk.set(n, true);
		} else {
			atk.set(n, false);
		}
		
		if (!b) {
			atkReleased.set(n, false);
		}
	}

	
	/**
	 * Verification et lancement des attaques des joueurs
	 */
	public void verificationAttack() {
		long time = main.engineLoop;
		if (getState() == ATK_STATE_CASTING) {
			if (time >= castingTimer) {
				this.attack(tabAttaques.indexOf(castingAttack));
			}
		}
		
		if (getState() == ATK_STATE_READY) {
			if (atk.get(0) && !atkReleased.get(0)) {
				atkReleased.set(0, true);
				castingAttack = tabAttaques.get(0);
				atkState = ATK_STATE_CASTING;
				castingTimer = time+tabAttaques.get(0).cast;
			}
			if (atk.get(1) && !atkReleased.get(1)) {
				atkReleased.set(1, true);
				//System.out.println(atkReleased.get(1));
				castingAttack = tabAttaques.get(1);
				atkState = ATK_STATE_CASTING;
				castingTimer = time+tabAttaques.get(1).cast;
			}
		}
	}
	
	/**
	 * Methode utilisee lorsque le joueur lance une attaque.
	 * Determine si l'attaque est disponible pour le joueur, s'il peut la lancer,
	 * si oui lance l'attaque, et enleve des points de vie a un joueur si il y a collision avec ce dernier
	 * @param n nom de l'attaque
	 * @param tabJoueurs tous les joueurs du jeu
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
	 * Methode pour determiner si une attaque touche un adversaire
	 * @return vrai si touché, faux, si non touché
	 */
	public boolean collisionAtk() {
		// Calcul de la position de l'attaque
		int tabXYWH[] = currentAttack.getAttackPosition(this);
		Collision c = new Collision();
		boolean collisionJoueur = false;
		boolean hasHit = false;
		// Collision avec des joueurs ?
		for (Joueur j : Game.tabJoueurs) {
			if (j != this) {
				collisionJoueur = c.collision(tabXYWH[0], tabXYWH[1], tabXYWH[2], tabXYWH[3], j.getX(), j.getY(), j.getW(), j.getH());
				if (collisionJoueur) {
					if(this.turnedRight) {
						j.receiveHit(currentAttack.getDamage(),currentAttack.getPowerX(),currentAttack.getPowerY());
					} else {
						j.receiveHit(currentAttack.getDamage(),-currentAttack.getPowerX(),currentAttack.getPowerY());
						
					}
					hasHit = true;
				}
			}
		}
		return hasHit;
	}
	
	/**
	 * Methode utilisee pour connaitre l'attaque que le joueur est en train de lancer
	 * @return l'attaque que le joueur est en train de lancer
	 */
	public Attaque getAttaque() {
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
		long time = main.engineLoop;
		// Update des temps de recharge des attaques
		for (int i = 0; i < tabAttaques.size(); i++) {
			if (tabAttaques.get(i).getEffectiveCooldown() <= time) {
				tabAttaques.get(i).setEffectiveCooldown(0);
			}
		}
		
		switch (atkState) {
		case ATK_STATE_ATTACKING:
			if (collisionAtk() || lastTimerAttack <= time) {
				currentAttack = null;
				atkState = ATK_STATE_IN_COOLDOWN;
				lastTimerAttack = -1;
			}
			break;
		case ATK_STATE_IN_COOLDOWN:
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
	 * Methode permettant d'enlever des points de vie au joueur
	 * @param hit entier qui se soustrait a la vie du joueur
	 */
	public void receiveHit(int hit, int powerX, int powerY) {
		if (this.atkState == 3) {
			currentAttack = null;
			atkState = ATK_STATE_READY;
			lastTimerAttack = -1;
		}
		int temp = this.health - hit;
		if (temp <= 0) {
			this.isAlive = false;
			this.health = 0;
		} else {
			this.health -= hit;
		}
		eject(powerX,powerY);
		
	}

	/**
	 * Methode permettant de soigner le joueur
	 * @param heal entier qui s'ajoute a la vie du joueur
	 */
	public void receiveHeal(int heal) {
		int temp = this.health + heal;
		if (this.isAlive) {
			if (temp > MAX_HEALTH) {
				this.health = MAX_HEALTH;
			} else {
				this.health += heal;
			}
		}
	}

	/**
	 * Methode permettant de maximiser les points de vie du joueur, et de le remettre en vie
	 */
	public void resetLife() {
		health = MAX_HEALTH;
		isAlive = true;
	}

	/**
	 * Appliquer une force a un joueur
	 * @param i Force en X appliquee au joueur
	 * @param j	Force en Y appliquee au joueur
	 */
	public void eject(int i, int j) {
		status = EJECTED;
		vitesseX = i;
		vitesseY = -j;
	}


}
