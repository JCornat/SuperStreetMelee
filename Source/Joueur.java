import java.util.ArrayList;


public class Joueur {

	// Max Health of the player
	final int MAX_HEALTH = 100;
	
	// Global Cooldown of the player
	public final static long GLOBAL_COOLDOWN = 500;
	
	// Side
	public final static boolean TURNED_RIGHT = true;
	public final static boolean TURNED_LEFT = false;
	
	// State
	public final static int STATE_READY = 0;
	public final static int STATE_ATTACKING = 1;
	public final static int STATE_IN_COOLDOWN = 2;
	
	
	public static int RUN_SPEED = 30;
	
	String name;
	int x,y,w,h,vitesseX, vitesseY, state, health, jumps, jumpsBase;
	boolean jump, left, right, isJumping, turnedRight, isAlive;
	
	Attaque currentAttack;
	ArrayList<Attaque> tabAttaques;
	long lastTimerAttack;
	
	// Global Cooldown Timer
	long GCDTimer;
	
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
		state = STATE_READY;
		jumpsBase = 2;
		jumps = jumpsBase;
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
	 * Methode utilisee lorsque le joueur lance une attaque.
	 * Determine si l'attaque est disponible pour le joueur, s'il peut la lancer,
	 * si oui lance l'attaque, et enleve des points de vie a un joueur si il y a collision avec ce dernier
	 * @param n nom de l'attaque
	 * @param tabJoueurs tous les joueurs du jeu
	 */
	public void attack(String n, ArrayList<Joueur> tabJoueurs) {
		if (getState() == STATE_READY)
			for (int i = 0; i < tabAttaques.size(); i++)
				if (tabAttaques.get(i).getName() == n) {
					if (tabAttaques.get(i).getEffectiveCooldown() == 0) {
						currentAttack = tabAttaques.get(i);
						// Traitement de l'attaque
						if (currentAttack != null)
						{
							// Calcul de la position de l'attaque
							int tabXYWH[] = currentAttack.getAttackPosition(this);
							Collision c = new Collision();
							boolean collisionJoueur = false;
							// Collision avec des joueurs ?
							for (Joueur j : tabJoueurs) {
								collisionJoueur = c.collision(tabXYWH[0], tabXYWH[1], tabXYWH[2], tabXYWH[3], j.getX(), j.getY(), j.getW(), j.getH());
								if (collisionJoueur)
									j.receiveHit(currentAttack.getPower());
							}
						}
						long time = System.currentTimeMillis();
						// Duee de l'affichage de l'attaque
						lastTimerAttack = time + tabAttaques.get(i).getTime();
						// Temps de recharge de l'attaque
						tabAttaques.get(i).setEffectiveCooldown(time + tabAttaques.get(i).getInfoCooldown());
						// Global Cooldown
						GCDTimer = time + GLOBAL_COOLDOWN;
						// L'attaque est en train d'etre produite
						state = STATE_ATTACKING;
						break;
					} else currentAttack = null;
				}
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
		if (GCDTimer > -1)
			return true;
		return false;
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
		long time = System.currentTimeMillis();
		// Update des temps de recharge des attaques
		for (int i = 0; i < tabAttaques.size(); i++)
			if (tabAttaques.get(i).getEffectiveCooldown() <= time)
				tabAttaques.get(i).setEffectiveCooldown(0);
		switch (state)
		{
		case STATE_ATTACKING:
			if (lastTimerAttack <= time)
			{
				currentAttack = null;
				state = STATE_IN_COOLDOWN;
				lastTimerAttack = -1;
			}
			break;
		case STATE_IN_COOLDOWN:
			if (GCDTimer != -1 && time >= GCDTimer)
			{
				state = STATE_READY;
				GCDTimer = -1;
			}
			break;
		default: 
			break;
		}		
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	/**
	 * Methode permettant de connaitre l'etat du joueur dans son combat
	 * @return STATE_ATTACKING : le joueur est en train de lancer une attaque,
	 * STATE_IN_COOLDOWN : le joueur a son temps de recharge global actif,
	 * STATE_READY : le joueur est pret a lancer des attaques
	 */
	public int getState() {
		return state;
	}
	
	public void receiveHit(int hit) {
		int temp = this.health - hit;
		if (temp <= 0) {
			this.isAlive = false;
			this.health = 0 ;
		} else {
			this.health -= hit ;
		}
	}

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

	public void resetLife() {
		health = MAX_HEALTH;
		isAlive = true;
	}

	public void getResurected(int heal) {
		isAlive = true;
		health = heal;
	}
	
}
