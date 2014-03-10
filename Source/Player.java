import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.CardLayout;

import javax.swing.ImageIcon;


public class Player {

	String name;
	int atkState, health, jumps;
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
	boolean booleanJump;
	int positionXOnJumping;
	int positionYOnJumping;
	Animator characterAnimationBody;
	AnimatorOneLoop characterAnimationBigAttack;
	AnimatorOneLoop characterAnimationJump;
	ArrayList<BufferedImage> arrayOfSpritesOfTheBody;
	ArrayList<BufferedImage> arrayOfSpritesOfPThePlayerJump;
	ArrayList<BufferedImage> arrayOfSpritesOfTheBigPlayerAttack;
	int numberOfLife;
	long waitedTimeForCombo;
	boolean isWaitingForCombo;
	PlayerPosition playerPosition;
	PlayerSpeed playerSpeed;
	
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

		name = n;
		playerPosition = new PlayerPosition(k,l);
		playerSpeed = new PlayerSpeed();
		
		jump = false;
		left = false; 
		right = false;
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

		numberOfLife = 5;
		
		
		BufferedImageLoader bufferedImageToLoad = new BufferedImageLoader();
		BufferedImage bufferedImageContainingSprites = null;
		try {
			if(skin==1) {
				bufferedImageContainingSprites = bufferedImageToLoad.loadImage("images/character/characterSprite.png");
			} else if(skin==2) {
				bufferedImageContainingSprites = bufferedImageToLoad.loadImage("images/character/characterSprite2.png");
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		SpriteSheet spriteSheet = new SpriteSheet(bufferedImageContainingSprites);
		arrayOfSpritesOfTheBody = new ArrayList<BufferedImage>();
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(0, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(215, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(430, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(645, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(860, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(1075, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(1290, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(1505, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(1720, 0, 215, 200));
		
		
		try {
			bufferedImageContainingSprites = bufferedImageToLoad.loadImage("images/character/jumpingSprite2.png");
		} catch (IOException e) {
			System.err.println(e);
		}
		spriteSheet = new SpriteSheet(bufferedImageContainingSprites);
		arrayOfSpritesOfPThePlayerJump = new ArrayList<BufferedImage>();
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(0, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(71, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(142, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(213, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(284, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(355, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(426, 0, 71, 61));

		
		try {
			bufferedImageContainingSprites = bufferedImageToLoad.loadImage("images/character/character1Attack.png");
		} catch (IOException e) {
			System.err.println(e);
		}
		spriteSheet = new SpriteSheet(bufferedImageContainingSprites);
		arrayOfSpritesOfTheBigPlayerAttack = new ArrayList<BufferedImage>();
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(0, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(286, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(572, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(858, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(1144, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(1430, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(1717, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(2002, 0, 286, 250));
		
		
		numberOfLife = Constant.LIFE_NUMBER;
		waitedTimeForCombo = -1;
		isWaitingForCombo = false;
	}
	
	public String getName() {
		return name;
	}
	
	public void setVitesseH(int i) {
		playerSpeed.speedOnHorizontalAxis = i;
	}
	
	public void setJump(boolean b) {
		if (b && jumps>0) {
			jumps--;
			playerSpeed.speedOnVerticalAxis = -80;
			isJumping = true;
			currentStatus = PlayerStatus.FALLING ;
			SoundManager.sounds.get("jump").play_once() ;
		}
		jump = b;
		if(!b) {
			isJumping=false;
		}
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
		if (b && atkState == Constant.ATK_STATE_READY && tabAttacks.get(n).getEffectiveCooldown() <= GameEngine.engineLoop) {
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
		long time = GameEngine.engineLoop;
		// Update des temps de recharge des attaques
		for (int i = 0; i < tabAttacks.size(); i++) {
			if (tabAttacks.get(i).getEffectiveCooldown() <= time) {
				tabAttacks.get(i).setEffectiveCooldown(0);
				// L'attaque peut de nouveau �tre lancŽe
				setAtk(i, false);
			}
		}
		
		switch (atkState) {
		case Constant.ATK_STATE_ATTACKING:
			if (this.collisionAttack()) {
				atkState = Constant.ATK_HAS_HIT;
			} else if (lastTimerAttack <= time) {
				atkState = Constant.ATK_STATE_IN_COOLDOWN;
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
				for (int i = 0; i < tabAttacks.size(); i++)
					setAtk(i, false);
				// On vide le tableau de sauvegarde des attaques
				tabLastAttacksForCombo.clear();
				currentAttack = null;
				// Remise a zero des combos
				for (int j = 0; j < tabCombos.size(); j++)
					setAtk(tabAttacks.indexOf(tabCombos.get(j).specialAttack), false);
				currentStatus = PlayerStatus.NORMAL;
				atkState = Constant.ATK_STATE_READY;
				GCDTimer = -1;
			}
			break;
		case Constant.ATK_HAS_HIT:
			if (lastTimerAttack <= time) {
				atkState = Constant.ATK_STATE_IN_COOLDOWN;
				lastTimerAttack = -1;
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
	 * Applique une force a un joueur pour l'ejecter
	 * @param i Force en X appliquee au joueur
	 * @param j	Force en Y appliquee au joueur
	 */
	public void eject(int i, int j) {
		currentStatus = PlayerStatus.EJECTED;
		playerSpeed.speedOnHorizontalAxis = i;
		playerSpeed.speedOnVerticalAxis = -j;
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
		long time = GameEngine.engineLoop;
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
	 * @return vrai si touché, faux, si non touché
	 */
	public boolean collisionAttack() {
		// Calcul de la position de l'attaque
		int tabXYWH[] = this.currentAttack.getAttackPosition(this);
		boolean collisionJoueur = false;
		boolean hasHit = false;
		// Collision avec des joueurs ?
		for (Player otherPlayer : Game.listPlayers) {
			if (otherPlayer != this) {
				collisionJoueur = Collision.collision(tabXYWH[0], tabXYWH[1], tabXYWH[2], tabXYWH[3], otherPlayer.playerPosition.getX(), otherPlayer.playerPosition.getY(), otherPlayer.playerPosition.getW(), otherPlayer.playerPosition.getH());
				if (collisionJoueur) {
					if(this.isTurningRight) {
						otherPlayer.receiveHit(this.currentAttack.getDamage(),this.currentAttack.getPowerX(),this.currentAttack.getPowerY());
					} else {
						otherPlayer.receiveHit(this.currentAttack.getDamage(),-this.currentAttack.getPowerX(),this.currentAttack.getPowerY());
					}
					
					if (this.currentAttack.getName().equals("Small")) {
						SoundManager.sounds.get("smallhit").play_once() ;
					} else if (this.currentAttack.getName().equals("Big")) {
						SoundManager.sounds.get("bighit").play_once() ;
					}else if (this.currentAttack.getName().equals("Special1")) {
						SoundManager.sounds.get("special1").play_once() ;
					}else if (this.currentAttack.getName().equals("Special2")) {
						SoundManager.sounds.get("special2").play_once() ;
					}else if (this.currentAttack.getName().equals("Special3")) {
						
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
			Game.CURRENT_STATE = State.IN_MENU ;
			((CardLayout) Menu.cards.getLayout()).show(Menu.cards, "mainmenu");
		} else {
			this.numberOfLife = newNumber ;
		}
	}



}
