
public class CollisionAttack {
	
	
	/**
	 * Methode pour determiner si une attaque touche un adversaire
	 * @param player : Joueur actuel
	 * @return vrai si touché, faux, si non touché
	 */
	public static boolean collisionAttack(Player player) {
		// Calcul de la position de l'attaque
		int tabXYWH[] = player.currentAttack.getAttackPosition(player);
		boolean collisionJoueur = false;
		boolean hasHit = false;
		// Collision avec des joueurs ?
		for (Player otherPlayer : GameEngine.listPlayers) {
			if (otherPlayer != player) {
				collisionJoueur = Collision.collision(tabXYWH[0], tabXYWH[1], tabXYWH[2], tabXYWH[3], otherPlayer.getX(), otherPlayer.getY(), otherPlayer.getW(), otherPlayer.getH());
				if (collisionJoueur) {
					if(player.isTurningRight) {
						ReceiveHit.receiveHit(otherPlayer,player.currentAttack.getDamage(),player.currentAttack.getPowerX(),player.currentAttack.getPowerY());
					} else {
						ReceiveHit.receiveHit(otherPlayer,player.currentAttack.getDamage(),-player.currentAttack.getPowerX(),player.currentAttack.getPowerY());
						
					}
					hasHit = true;
				}
			}
		}
		return hasHit;
	}
}
