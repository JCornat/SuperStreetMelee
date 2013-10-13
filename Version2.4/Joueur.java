import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Observable;


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
	
	String name;
	int x,y,w,h,vitesseH, state, health;
	boolean jump, left, right, crouch, isJumping, canJump, turnedRight, isAlive;
	
	Attaque currentAttack;
	ArrayList<Attaque> tabAttaques;
	long lastTimerAttack;
	
	// Global Cooldown Timer
	long GCDTimer;
	
	/**
	 * Constructeur pour créer un joueur
	 * @param name nom du joueur
	 * @param i Abscisse du joueur
	 * @param j Ordonnée du joueur
	 * @param k Largeur du joueur
	 * @param l Hauteur du joueur
	 * @param attaques liste d'attaques spécifiques au joueur
	 */
	public Joueur(String name, int i, int j, int k, int l, ArrayList<Attaque> attaques) {
		this.name = name;
		x = i; y = j;
		w = k; h = l;
		jump = false; crouch = false;
		left = false; right = false;
		vitesseH = 2;
		canJump = false;
		health = MAX_HEALTH;
		isAlive = true;
		turnedRight = true;
		currentAttack = null;
		lastTimerAttack = -1;
		GCDTimer = -1;
		this.tabAttaques = attaques;
		state = STATE_READY;
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
		vitesseH = i;
	}
	public void setJump(boolean b) {
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
	public void setCrouch(boolean b) {
		crouch = b;
	}
	public boolean getCrouch() {
		return crouch;
	}
	public void notrun() {
		vitesseH = 2;
	}
	public void run() {
		vitesseH = 4;
	}
	
	/**
	 * Méthode utilisée quand le joueur se tourne d'un coté
	 * @param right côté duquel le joueur est tourné : droite->vrai, gauche->faux
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
	 * Méthode utilisée lorsque le joueur lance une attaque.
	 * Détermine si l'attaque est disponible pour le joueur, s'il peut la lancer,
	 * si oui lance l'attaque, et enlève des points de vie à un joueur si il y a collision avec ce dernier
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
								collisionJoueur = c.collisionJoueur(tabXYWH[0], tabXYWH[1], tabXYWH[2], tabXYWH[3], j.getX(), j.getY(), j.getW(), j.getH());
								if (collisionJoueur)
									j.receiveHit(currentAttack.getPower());
							}
						}
						long time = System.currentTimeMillis();
						// Durée de l'affichage de l'attaque
						lastTimerAttack = time + tabAttaques.get(i).getTime();
						// Temps de recharge de l'attaque
						tabAttaques.get(i).setEffectiveCooldown(time + tabAttaques.get(i).getInfoCooldown());
						// Global Cooldown
						GCDTimer = time + GLOBAL_COOLDOWN;
						// L'attaque est en train d'être produite
						state = STATE_ATTACKING;
						break;
					} else currentAttack = null;
				}
	}
	
	/**
	 * Méthode utilisée pour connaître l'attaque que le joueur est en train de lancer
	 * @return l'attaque que le joueur est en train de lancer
	 */
	public Attaque getAttaque() {
		return currentAttack;
	}
	
	/**
	 * Méthode utilisée pour savoir si le joueur a l'attaque passée en paramètre en temps de recharge
	 * @param a attaque 
	 * @return vrai si le joueur a l'attaque en cours de recharge autrement faux
	 */
	public boolean hasCooldown(Attaque a) {
		for (int i = 0; i < tabAttaques.size(); i++)
			if (tabAttaques.get(i).getName() == a.getName())
				if (tabAttaques.get(i).getEffectiveCooldown() > 0)
					return true;
		return false;	
	}
	
	/**
	 * Méthode utilisée pour savoir si le joueur a le temps de recharge global (ne peut pas lancer d'attaque)
	 * @return vrai si il a le temps de recharge global
	 */
	public boolean hasGlobalCooldown() {
		if (GCDTimer > -1)
			return true;
		return false;
	}
	
	/**
	 * Méthode utilisée pour connaître le moment de la dernière attaque du joueur
	 * @return le temps system en millisecondes où le joueur à lancé sa dernière attaque
	 */
	public long getLastTimerAttack() {
		return lastTimerAttack;
	}
	
	/**
	 * Méthode permettant de mettre à jour 
	 * le temps de recharge des attaques du joueur,
	 * son temps de recharge global
	 * et l'affichage de sa dernière attaque
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
	 * Méthode permettant de connaître l'état du joueur dans son combat
	 * @return STATE_ATTACKING : le joueur est en train de lancer une attaque,
	 * STATE_IN_COOLDOWN : le joueur a son temps de recharge global actif,
	 * STATE_READY : le joueur est prêt à lancer des attaques
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
