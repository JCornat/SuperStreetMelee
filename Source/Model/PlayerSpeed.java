package Model;

public class PlayerSpeed {
	public int speedOnHorizontalAxis, speedOnVerticalAxis;
	
	public PlayerSpeed() {
		speedOnHorizontalAxis = 0;
		speedOnVerticalAxis = 0;
	}
	
	/**
	 * Applique une force a un joueur pour l'ejecter
	 * @param i Force en X appliquee au joueur
	 * @param j	Force en Y appliquee au joueur
	 */
	public void eject(int i, int j) {
		speedOnHorizontalAxis = i;
		speedOnVerticalAxis = -j;
	}
}
