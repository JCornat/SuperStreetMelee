package Model;

public class ReceiveHit {
	
	public void Calculation(Player player, int hit, int powerX, int powerY) {
		/**
		 * Methode permettant d'enlever des points de vie au joueur
		 * @param hit entier qui se soustrait a la vie du joueur
		 */

		if (player.playerInfo.atkState == 3) {
			player.currentAttack = null;
			player.playerInfo.atkState = Constant.ATK_STATE_READY;
			player.lastTimerAttack = -1;
		}
		player.playerInfo.health += hit;
		double coef = 1 + player.playerInfo.health/5;
		double puissanceX = (powerX * coef)/10;
		double puissanceY = (powerY * coef)/10;
		puissanceX = this.minimumHitCalculationX(puissanceX);
		puissanceY = this.minimumHitCalculationY(puissanceY);
		player.eject((int)puissanceX, (int)puissanceY);

	}
	
	
	/**
	 * Methode de calcul de puissance minimal du coup, utilise pour donner une force minimale
	 */
	public double minimumHitCalculationX(double puissanceX) {
		if (puissanceX > 30) {
			
		} else if (puissanceX < -30){
			
		} else if (puissanceX > 0){
			puissanceX = 30;
		} else if (puissanceX < 0){ 
			puissanceX = -30;
		}
		return puissanceX;
	}
	
	public double minimumHitCalculationY(double puissanceY) {
		if (puissanceY > 30) {
			
		} else if (puissanceY < -30){
			
		} else if (puissanceY > 0){
			puissanceY = 30;
		} else if (puissanceY < 0){ 
			puissanceY = -30;
		}
		return puissanceY;
	}

	

}
