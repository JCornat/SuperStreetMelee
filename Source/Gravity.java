import java.util.ArrayList;


public class Gravity {


	public int collisionLeft, collisionRight, collisionTop, collisionBottom;
	public static Player player;
	
	public Gravity() {
		collisionLeft = -1;
		collisionRight = -1; 
		collisionTop = -1; 
		collisionBottom = -1;
	}
	
	public void gravity(Player p) {
		player = p;
		if (GameEngine.CURRENT_STATE == State.IN_GAME) {
			gravityApply();
			
			calculationVerticalCollision();

			calculationHorizontalCollision();
		} 
	}
	
	//Application de la gravité au joueur
	private void gravityApply() {
		//Application de la gravité à la vitesseY du joueur
		player.vitesseY = player.vitesseY + Constant.GRAVITY_MAX;
		
		//Vitesse cap de chute
		if (player.vitesseY > Constant.GRAVITY_SPEED_CAP) {
			player.vitesseY = Constant.GRAVITY_SPEED_CAP;
		}
	}
	

	//Verification de collision verticale
	public void calculationVerticalCollision() {
		int y = player.getY() + player.vitesseY / 10;
		if (player.vitesseY > 0) {
			//Le personnage est en train d'aller vers le bas
			collisionBottom = Collision.collisionCalculation(player.getX(), y, player.getW(),player.getH(), GameEngine.tabDecor);
			if (collisionBottom > -1) {
				//Contact avec le sol
				player.setY(player.getY() + GameEngine.tabDecor.get(collisionBottom).y - player.getY() - player.getH());
				player.vitesseY = 0;
				player.jumps = player.jumpsBase;
			} else {
				//Le personnage tombe
				player.setY(y);
				collisionBottom = -1;
			}
		} else if (player.vitesseY < 0) {
			//Le personnage est en train d'aller vers le haut
			collisionTop = Collision.collisionCalculation(player.getX(), y, player.getW(),player.getH(), GameEngine.tabDecor);
			if (collisionTop > -1) {
				//Contact avec un decor situe au-dessus
				player.setY(player.getY() + GameEngine.tabDecor.get(collisionTop).y + GameEngine.tabDecor.get(collisionTop).h - player.getY());
				player.vitesseY = 0;
			} else {
				//Le personnage monte
				player.setY(y);
			}
		}
	}
	

	//Verification de collision horizontale
	public void calculationHorizontalCollision() {
		int x = player.getX() + player.vitesseX / 10;
		//Application d'une inertie si le joueur ne bouge pas
		if (player.vitesseX == 0) {
			if (player.right && player.isJumping) {
				player.vitesseX += Constant.INERTIE / 2;
			} else if (player.left && player.isJumping) {
				player.vitesseX -= Constant.INERTIE / 2;
			} else if (player.right) {
				player.vitesseX += Constant.INERTIE;
			} else if (player.left) {
				player.vitesseX -= Constant.INERTIE;
			}
		} else if (player.vitesseX > 0) {
			//Le personnage est en train d'aller vers la droite

			//Si touche droite non enfoncee, inertie mise en place pour freiner
			if (!player.right) {
				player.vitesseX -= Constant.INERTIE / 2;
			} else {
				if (player.status == PlayerStatus.EJECTED) {
					player.vitesseX -= Constant.INERTIE / 2;
					if (player.vitesseX <= Constant.MAX_RUN_SPEED) {
						player.status = PlayerStatus.NORMAL;
					}
				} else {
					player.vitesseX += Constant.INERTIE;
					if (player.vitesseX >= Constant.MAX_RUN_SPEED) {
						player.vitesseX = Constant.MAX_RUN_SPEED;
					}
				}
			}

			x = player.getX() + player.vitesseX / 10;
			collisionRight = Collision.collisionCalculation(x, player.getY(), player.getW(),player.getH(), GameEngine.tabDecor);
			if (collisionRight > -1) {
				//Contact avec le decor sur la droite
				player.vitesseX = 0;
				player.setX(player.getX() + GameEngine.tabDecor.get(collisionRight).x - player.getX() - player.getW());
			} else {
				//Le personnage se deplace sur la droite
				player.setX(x);
			}

		} else if (player.vitesseX < 0) {
			//Le personnage est en train d'aller vers la gauche

			//Si touche gauche non enfoncee, inertie mise en place pour freiner
			if (!player.left) {
				player.vitesseX += Constant.INERTIE / 2;
			} else {
				if (player.status == PlayerStatus.EJECTED) {
					player.vitesseX += Constant.INERTIE / 2;
					if (player.vitesseX >= -Constant.MAX_RUN_SPEED) {
						player.status = PlayerStatus.NORMAL;
					}
				} else {
					player.vitesseX -= Constant.INERTIE;
					if (player.vitesseX <= -Constant.MAX_RUN_SPEED) {
						player.vitesseX = -Constant.MAX_RUN_SPEED;
					}
				}
			}

			x = player.getX() + player.vitesseX / 10;
			collisionLeft = Collision.collisionCalculation(x, player.getY(), player.getW(),player.getH(), GameEngine.tabDecor);
			if (collisionLeft > -1) {
				//Contact avec le décor sur la gauche
				player.vitesseX = 0;
				player.setX(player.getX() + GameEngine.tabDecor.get(collisionLeft).x + GameEngine.tabDecor.get(collisionLeft).w - player.getX());
			} else {
				//Le personnage se déplace sur la gauche
				player.setX(x);
			}
		}
	}

}
