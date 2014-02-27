

public class Gravity {


	public int collisionOnLeftSide, collisionOnRightSide, collisionOnTopSide, collisionOnBottomSide;
	public static Player player;
	public static PlayerSpeed playerSpeed;
	public static PlayerPosition playerPosition;
	
	
	public Gravity() {
		collisionOnLeftSide = -1;
		collisionOnRightSide = -1; 
		collisionOnTopSide = -1; 
		collisionOnBottomSide = -1;
	}
	

	/**
	 * Methode generale de la gravite, faisant appel a d'autres methodes
	 * @param p : Joueur sur lequel appliquer la classe Gravite
	 */
	public void gravity(Player p) {
		player = p;
		playerSpeed = p.playerSpeed;
		playerPosition = p.playerPosition;
		if (Game.CURRENT_STATE == State.IN_GAME) {
			gravityApply();
			calculationVerticalCollision();
			calculationHorizontalCollision();
		}
	}
	

	/**
	 * Application de la gravite au joueur
	 */
	private void gravityApply() {
		//Application de la gravite à la vitesseY du joueur
		playerSpeed.speedOnVerticalAxis = playerSpeed.speedOnVerticalAxis + Constant.GRAVITY_MAX;
		
		//Vitesse cap de chute
		if (playerSpeed.speedOnVerticalAxis > Constant.GRAVITY_SPEED_CAP) {
			playerSpeed.speedOnVerticalAxis = Constant.GRAVITY_SPEED_CAP;
		}
	}
	

	/**
	 * Verification de collision verticale
	 */
	public void calculationVerticalCollision() {
		int y = playerPosition.getY() + playerSpeed.speedOnVerticalAxis / 10;
		if (playerSpeed.speedOnVerticalAxis > 0) {
			//Le personnage est en train d'aller vers le bas
			collisionOnBottomSide = Collision.collisionCalculation(playerPosition.getX(), y, playerPosition.getW(),playerPosition.getH(), Game.arrayDecor, player);
			if (collisionOnBottomSide > -1) {
				//Contact avec le sol
				playerPosition.setY(playerPosition.getY() + Game.arrayDecor.get(collisionOnBottomSide).y - playerPosition.getY() - playerPosition.getH());
				playerSpeed.speedOnVerticalAxis = 0;
				player.jumps = Constant.JUMP_BASE;
				player.currentStatus = PlayerStatus.NORMAL ;
			} else {
				//Le personnage tombe
				playerPosition.setY(y);
				collisionOnBottomSide = -1;
			}
		} else if (playerSpeed.speedOnVerticalAxis < 0) {
			//Le personnage est en train d'aller vers le haut
			collisionOnTopSide = Collision.collisionCalculation(playerPosition.getX(), y, playerPosition.getW(),playerPosition.getH(), Game.arrayDecor, player);
			if (collisionOnTopSide > -1) {
				//Contact avec un decor situe au-dessus
				playerPosition.setY(playerPosition.getY() + Game.arrayDecor.get(collisionOnTopSide).y + Game.arrayDecor.get(collisionOnTopSide).h - playerPosition.getY());
				playerSpeed.speedOnVerticalAxis = 0;
			} else {
				//Le personnage monte
				playerPosition.setY(y);
			}
		}
	}
	

	/**
	 * Verification de collision horizontale
	 */
	public void calculationHorizontalCollision() {
		int x = playerPosition.getX() + playerSpeed.speedOnHorizontalAxis / 10;
		//Application d'une inertie si le joueur ne bouge pas
		if (playerSpeed.speedOnHorizontalAxis == 0) {
			if (player.right && player.isJumping) {
				playerSpeed.speedOnHorizontalAxis += Constant.INERTIE/2;
			} else if (player.left && player.isJumping) {
				playerSpeed.speedOnHorizontalAxis -= Constant.INERTIE/2;
			} else if (player.right) {
				playerSpeed.speedOnHorizontalAxis += Constant.INERTIE;
			} else if (player.left) {
				playerSpeed.speedOnHorizontalAxis -= Constant.INERTIE;
			}
		} else if (playerSpeed.speedOnHorizontalAxis > 0) {
			//Le personnage est en train d'aller vers la droite

			//Si touche droite non enfoncee, inertie mise en place pour freiner
			if (!player.right) {
				playerSpeed.speedOnHorizontalAxis -= Constant.INERTIE/2;
			} else {
				if (player.currentStatus == PlayerStatus.EJECTED) {
					playerSpeed.speedOnHorizontalAxis -= Constant.INERTIE/2;
					if (playerSpeed.speedOnHorizontalAxis <= Constant.MAX_RUN_SPEED) {
						player.currentStatus = PlayerStatus.NORMAL;
					}
				} else {
					playerSpeed.speedOnHorizontalAxis += Constant.INERTIE;
					if (playerSpeed.speedOnHorizontalAxis >= Constant.MAX_RUN_SPEED) {
						playerSpeed.speedOnHorizontalAxis = Constant.MAX_RUN_SPEED;
					}
				}
			}

			x = playerPosition.getX() + playerSpeed.speedOnHorizontalAxis / 10;
			collisionOnRightSide = Collision.collisionCalculation(x, playerPosition.getY(), playerPosition.getW(),playerPosition.getH(), Game.arrayDecor, player);
			if (collisionOnRightSide > -1) {
				//Contact avec le decor sur la droite
				playerSpeed.speedOnHorizontalAxis = 0;
				playerPosition.setX(playerPosition.getX() + Game.arrayDecor.get(collisionOnRightSide).x - playerPosition.getX() - playerPosition.getW());
			} else {
				//Le personnage se deplace sur la droite
				playerPosition.setX(x);
			}

		} else if (playerSpeed.speedOnHorizontalAxis < 0) {
			//Le personnage est en train d'aller vers la gauche

			//Si touche gauche non enfoncee, inertie mise en place pour freiner
			if (!player.left) {
				playerSpeed.speedOnHorizontalAxis += Constant.INERTIE / 2;
			} else {
				if (player.currentStatus == PlayerStatus.EJECTED) {
					playerSpeed.speedOnHorizontalAxis += Constant.INERTIE / 2;
					if (playerSpeed.speedOnHorizontalAxis >= -Constant.MAX_RUN_SPEED) {
						player.currentStatus = PlayerStatus.NORMAL;
					}
				} else {
					playerSpeed.speedOnHorizontalAxis -= Constant.INERTIE;
					if (playerSpeed.speedOnHorizontalAxis <= -Constant.MAX_RUN_SPEED) {
						playerSpeed.speedOnHorizontalAxis = -Constant.MAX_RUN_SPEED;
					}
				}
			}

			x = playerPosition.getX() + playerSpeed.speedOnHorizontalAxis / 10;
			collisionOnLeftSide = Collision.collisionCalculation(x, playerPosition.getY(), playerPosition.getW(),playerPosition.getH(), Game.arrayDecor, player);
			if (collisionOnLeftSide > -1) {
				//Contact avec le décor sur la gauche
				playerSpeed.speedOnHorizontalAxis = 0;
				playerPosition.setX(playerPosition.getX() + Game.arrayDecor.get(collisionOnLeftSide).x + Game.arrayDecor.get(collisionOnLeftSide).w - playerPosition.getX());
			} else {
				//Le personnage se déplace sur la gauche
				playerPosition.setX(x);
			}
		}
	}

}
