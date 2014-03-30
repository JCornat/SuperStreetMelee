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
					CombatMgr playerCombatMgr = player.playerCombatMgr;
					if(player.playerInfoBoolean.isTurningRight) {
						receiveHit.Calculation(otherPlayer, playerCombatMgr.currentAttack.getDamage(),playerCombatMgr.currentAttack.getPowerX(),playerCombatMgr.currentAttack.getPowerY());
					} else {
						receiveHit.Calculation(otherPlayer,playerCombatMgr.currentAttack.getDamage(),-playerCombatMgr.currentAttack.getPowerX(),playerCombatMgr.currentAttack.getPowerY());
					}
					
					if (playerCombatMgr.currentAttack.getName().equals("Small")) {
						SoundManager.play_once("smallhit") ;
					} else if (playerCombatMgr.currentAttack.getName().equals("Big")) {
						SoundManager.play_once("bighit") ;
					}else if (playerCombatMgr.currentAttack.getName().equals("Special1")) {
						SoundManager.play_once("special1") ;
					}else if (playerCombatMgr.currentAttack.getName().equals("Special2")) {
						SoundManager.play_once("special2") ;
					}else if (playerCombatMgr.currentAttack.getName().equals("Special3")) {
						
					}
					
					hasHit = true;
				}
			}
		}
		return hasHit;
	}

}
