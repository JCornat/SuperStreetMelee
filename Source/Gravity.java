import java.util.ArrayList;


public class Gravity {


	public int collisionOnLeftSide, collisionOnRightSide, collisionOnTopSide, collisionOnBottomSide;
	public static Player player;
	
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
		if (GameEngine.CURRENT_STATE == State.IN_GAME) {
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
		player.speedOnVerticalAxis = player.speedOnVerticalAxis + Constant.GRAVITY_MAX;
		
		//Vitesse cap de chute
		if (player.speedOnVerticalAxis > Constant.GRAVITY_SPEED_CAP) {
			player.speedOnVerticalAxis = Constant.GRAVITY_SPEED_CAP;
		}
	}
	

	/**
	 * Verification de collision verticale
	 */
	public void calculationVerticalCollision() {
		int y = player.getY() + player.speedOnVerticalAxis / 10;
		if (player.speedOnVerticalAxis > 0) {
			//Le personnage est en train d'aller vers le bas
			collisionOnBottomSide = Collision.collisionCalculation(player.getX(), y, player.getW(),player.getH(), GameEngine.arrayDecor, player);
			if (collisionOnBottomSide > -1) {
				//Contact avec le sol
				player.setY(player.getY() + GameEngine.arrayDecor.get(collisionOnBottomSide).y - player.getY() - player.getH());
				player.speedOnVerticalAxis = 0;
				player.jumps = Constant.JUMP_BASE;
			} else {
				//Le personnage tombe
				player.setY(y);
				collisionOnBottomSide = -1;
			}
		} else if (player.speedOnVerticalAxis < 0) {
			//Le personnage est en train d'aller vers le haut
			collisionOnTopSide = Collision.collisionCalculation(player.getX(), y, player.getW(),player.getH(), GameEngine.arrayDecor, player);
			if (collisionOnTopSide > -1) {
				//Contact avec un decor situe au-dessus
				player.setY(player.getY() + GameEngine.arrayDecor.get(collisionOnTopSide).y + GameEngine.arrayDecor.get(collisionOnTopSide).h - player.getY());
				player.speedOnVerticalAxis = 0;
			} else {
				//Le personnage monte
				player.setY(y);
			}
		}
	}
	

	/**
	 * Verification de collision horizontale
	 */
	public void calculationHorizontalCollision() {
		int x = player.getX() + player.speedOnHorizontalAxis / 10;
		//Application d'une inertie si le joueur ne bouge pas
		if (player.speedOnHorizontalAxis == 0) {
			if (player.right && player.isJumping) {
				player.speedOnHorizontalAxis += Constant.INERTIE/2;
			} else if (player.left && player.isJumping) {
				player.speedOnHorizontalAxis -= Constant.INERTIE/2;
			} else if (player.right) {
				player.speedOnHorizontalAxis += Constant.INERTIE;
			} else if (player.left) {
				player.speedOnHorizontalAxis -= Constant.INERTIE;
			}
		} else if (player.speedOnHorizontalAxis > 0) {
			//Le personnage est en train d'aller vers la droite

			//Si touche droite non enfoncee, inertie mise en place pour freiner
			if (!player.right) {
				player.speedOnHorizontalAxis -= Constant.INERTIE/2;
			} else {
				if (player.currentStatus == PlayerStatus.EJECTED) {
					player.speedOnHorizontalAxis -= Constant.INERTIE/2;
					if (player.speedOnHorizontalAxis <= Constant.MAX_RUN_SPEED) {
						player.currentStatus = PlayerStatus.NORMAL;
					}
				} else {
					player.speedOnHorizontalAxis += Constant.INERTIE;
					if (player.speedOnHorizontalAxis >= Constant.MAX_RUN_SPEED) {
						player.speedOnHorizontalAxis = Constant.MAX_RUN_SPEED;
					}
				}
			}

			x = player.getX() + player.speedOnHorizontalAxis / 10;
			collisionOnRightSide = Collision.collisionCalculation(x, player.getY(), player.getW(),player.getH(), GameEngine.arrayDecor, player);
			if (collisionOnRightSide > -1) {
				//Contact avec le decor sur la droite
				player.speedOnHorizontalAxis = 0;
				player.setX(player.getX() + GameEngine.arrayDecor.get(collisionOnRightSide).x - player.getX() - player.getW());
			} else {
				//Le personnage se deplace sur la droite
				player.setX(x);
			}

		} else if (player.speedOnHorizontalAxis < 0) {
			//Le personnage est en train d'aller vers la gauche

			//Si touche gauche non enfoncee, inertie mise en place pour freiner
			if (!player.left) {
				player.speedOnHorizontalAxis += Constant.INERTIE / 2;
			} else {
				if (player.currentStatus == PlayerStatus.EJECTED) {
					player.speedOnHorizontalAxis += Constant.INERTIE / 2;
					if (player.speedOnHorizontalAxis >= -Constant.MAX_RUN_SPEED) {
						player.currentStatus = PlayerStatus.NORMAL;
					}
				} else {
					player.speedOnHorizontalAxis -= Constant.INERTIE;
					if (player.speedOnHorizontalAxis <= -Constant.MAX_RUN_SPEED) {
						player.speedOnHorizontalAxis = -Constant.MAX_RUN_SPEED;
					}
				}
			}

			x = player.getX() + player.speedOnHorizontalAxis / 10;
			collisionOnLeftSide = Collision.collisionCalculation(x, player.getY(), player.getW(),player.getH(), GameEngine.arrayDecor, player);
			if (collisionOnLeftSide > -1) {
				//Contact avec le décor sur la gauche
				player.speedOnHorizontalAxis = 0;
				player.setX(player.getX() + GameEngine.arrayDecor.get(collisionOnLeftSide).x + GameEngine.arrayDecor.get(collisionOnLeftSide).w - player.getX());
			} else {
				//Le personnage se déplace sur la gauche
				player.setX(x);
			}
		}
	}

}
