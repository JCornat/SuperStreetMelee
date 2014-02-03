
public class VerificationAttack {
	
	
	/**
	 * Verification et lancement des attaques des joueurs
	 */
	public static void verificationAttack(Player player) {
		long time = main.engineLoop;
		if (player.getState() == player.ATK_STATE_CASTING) {
			if (time >= player.castingTimer) {
				player.attack(player.tabAttaques.indexOf(player.castingAttack));
			}
		}
		
		if (player.getState() == player.ATK_STATE_READY) {
			if (player.attack.get(0) && !player.attackReleased.get(0)) {
				player.attackReleased.set(0, true);
				player.castingAttack = player.tabAttaques.get(0);
				player.atkState = player.ATK_STATE_CASTING;
				player.castingTimer = time+player.tabAttaques.get(0).cast;
			}
			if (player.attack.get(1) && !player.attackReleased.get(1)) {
				player.attackReleased.set(1, true);
				//System.out.println(atkReleased.get(1));
				player.castingAttack = player.tabAttaques.get(1);
				player.atkState = player.ATK_STATE_CASTING;
				player.castingTimer = time+player.tabAttaques.get(1).cast;
			}
		}
	}
}
