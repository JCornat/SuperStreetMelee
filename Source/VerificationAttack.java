
public class VerificationAttack {
	
	
	/**
	 * Verification et lancement des attaques des joueurs
	 */
	public static void verificationAttack(Player p) {
		long time = main.engineLoop;
		if (p.getState() == p.ATK_STATE_CASTING) {
			if (time >= p.castingTimer) {
				p.attack(p.tabAttaques.indexOf(p.castingAttack));
			}
		}
		
		if (p.getState() == p.ATK_STATE_READY) {
			if (p.atk.get(0) && !p.atkReleased.get(0)) {
				p.atkReleased.set(0, true);
				p.castingAttack = p.tabAttaques.get(0);
				p.atkState = p.ATK_STATE_CASTING;
				p.castingTimer = time+p.tabAttaques.get(0).cast;
			}
			if (p.atk.get(1) && !p.atkReleased.get(1)) {
				p.atkReleased.set(1, true);
				//System.out.println(atkReleased.get(1));
				p.castingAttack = p.tabAttaques.get(1);
				p.atkState = p.ATK_STATE_CASTING;
				p.castingTimer = time+p.tabAttaques.get(1).cast;
			}
		}
	}
}
