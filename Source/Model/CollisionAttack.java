package Model;

import Sound.SoundManager;

public class CollisionAttack {
	
	public CollisionAttack() {
		
	}
	
	public boolean Calculation(Player player, Attack currentAttack) {
		// Calcul de la position de l'attaque
		int tabXYWH[] = currentAttack.getAttackPosition(player);
		boolean collisionJoueur = false;
		boolean hasHit = false;
		ReceiveHit receiveHit = new ReceiveHit();
		// Collision avec des joueurs ?
		for (Player otherPlayer : Game.listPlayers) {
			if (otherPlayer != player) {
				collisionJoueur = Collision.collision(tabXYWH[0], tabXYWH[1], tabXYWH[2], tabXYWH[3], otherPlayer.playerPosition.x, otherPlayer.playerPosition.y, otherPlayer.playerPosition.w, otherPlayer.playerPosition.h);
				if (collisionJoueur) {
					if(player.playerInfoBoolean.isTurningRight) {
						receiveHit.Calculation(otherPlayer, player.currentAttack.getDamage(),player.currentAttack.getPowerX(),player.currentAttack.getPowerY());
					} else {
						receiveHit.Calculation(otherPlayer,player.currentAttack.getDamage(),-player.currentAttack.getPowerX(),player.currentAttack.getPowerY());
					}
					
					if (player.currentAttack.getName().equals("Small")) {
						SoundManager.sounds.get("smallhit").play_once() ;
					} else if (player.currentAttack.getName().equals("Big")) {
						SoundManager.sounds.get("bighit").play_once() ;
					}else if (player.currentAttack.getName().equals("Special1")) {
						SoundManager.sounds.get("special1").play_once() ;
					}else if (player.currentAttack.getName().equals("Special2")) {
						SoundManager.sounds.get("special2").play_once() ;
					}else if (player.currentAttack.getName().equals("Special3")) {
						
					}
					
					hasHit = true;
				}
			}
		}
		return hasHit;
	}

}
