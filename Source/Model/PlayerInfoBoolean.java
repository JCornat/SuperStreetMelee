package Model;

public class PlayerInfoBoolean {

	public boolean jump, left, right, isJumping, isTurningRight, isAlive;
	
	public PlayerInfoBoolean() {
		jump = false;
		left = false; 
		right = false;
		isAlive = true;
		isTurningRight = true;
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
	
}
