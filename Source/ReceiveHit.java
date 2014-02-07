
public class ReceiveHit {
	
	static double coef;
	static double puissanceX;
	static double puissanceY;


	/**
	 * Methode permettant d'enlever des points de vie au joueur
	 * @param hit entier qui se soustrait a la vie du joueur
	 */
	public static void receiveHit(Player p, int hit, int powerX, int powerY) {
		if (p.atkState == 3) {
			p.currentAttack = null;
			p.atkState = Constant.ATK_STATE_READY;
			p.lastTimerAttack = -1;
		}
		p.health += hit;

		coef = 1 + p.health/5;
		puissanceX = (powerX * coef)/10;
		puissanceY = (powerY * coef)/10;
		
		p.eject((int)puissanceX, (int)puissanceY);
	}
	
	/**
	 * Methode de calcul de puissance minimal du coup, utilise pour donner une force minimale
	 */
	public static void calculation() {
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
}
