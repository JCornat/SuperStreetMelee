package Model;

import java.util.ArrayList;

public class CombatMgr {
	
	public Attack currentAttack;
	public ArrayList<Attack> tabAttacks, tabLastAttacksForCombo;
	public ArrayList<Combo> tabCombos;
	public long lastTimerAttack, castingTimer;
	// Global Cooldown Timer
	public long GCDTimer;
	public ArrayList<Boolean> attacks, attacksReleased;
	public Attack castingAttack;
	public long waitedTimeForCombo;
	
	public CombatMgr(ArrayList<Attack> attacksList, ArrayList<Combo> combosList) {
		currentAttack = null;
		lastTimerAttack = -1;
		GCDTimer = -1;
		waitedTimeForCombo = -1;
		tabAttacks = attacksList;
		tabCombos = combosList;
		tabLastAttacksForCombo 	= new ArrayList<Attack>(); 	tabLastAttacksForCombo.clear();
		attacks 				= new ArrayList<Boolean>(); attacks.clear();
		attacksReleased 		= new ArrayList<Boolean>(); attacksReleased.clear();
		for (int i = 0; i < attacksList.size(); i++) {
			attacks.add(false);
			attacksReleased.add(false);
		}
	}
	
	public void checkAndLaunchAttacks(Player player, long time) {
		PlayerInfo playerInfo = player.playerInfo;
		
		if (playerInfo.atkState == Constant.ATK_STATE_CASTING) {
			if (time >= this.castingTimer) {
				// Lancement de l'attaque
				processAttack(tabAttacks.indexOf(castingAttack));
				// L'attaque est en train d'etre produite
				playerInfo.atkState = Constant.ATK_STATE_ATTACKING;
			}
		}
		
		if (playerInfo.atkState == Constant.ATK_STATE_READY) {
			for (int i = 0; i < attacks.size(); i++) {
				if (attacks.get(i) && !attacksReleased.get(i)) {
					attacksReleased.set(i, true);
					castingAttack = tabAttacks.get(i);
					playerInfo.atkState = Constant.ATK_STATE_CASTING;
					castingTimer = time + tabAttacks.get(i).cast;
				}
			}
		}
	}
	
	public void enableOrDisableAttackForLaunching(Player player, int num, boolean enable) {
		PlayerInfo playerInfo = player.playerInfo;
		boolean isWaitingForCombo = player.playerInfoBoolean.isWaitingForCombo;
		if (enable && playerInfo.atkState == Constant.ATK_STATE_READY && tabAttacks.get(num).getEffectiveCooldown() <= GameEngine.engineLoop) {
			if (!isWaitingForCombo)
				attacks.set(num, enable);
			else if (isWaitingForCombo && tabAttacks.get(num).isBind)
				tabLastAttacksForCombo.add(tabAttacks.get(num));
		} else
			attacks.set(num, false);
			
		if (!enable)
			attacksReleased.set(num, enable);
	}
	
	public void processCombo(Player player) {
		// Attente terminee
		waitedTimeForCombo = -1;
		// Declenchement des combos, lancement des attaques speciales correspondantes
		int numberOfButtonPressed = tabLastAttacksForCombo.size();
		switch (numberOfButtonPressed) {
			case 1:
				enableOrDisableAttackForLaunching(player, tabAttacks.indexOf(tabLastAttacksForCombo.get(0)), true);
				break;
			// Combos a 2 touches et plus
			case 2:
			case 3: {
				for (Combo c : tabCombos) {
					boolean check = true;
					for (int i = 0; i < c.nbOfBind; i++) {
						if (c.nbOfBind == numberOfButtonPressed) {
							if (c.binds[i] != tabLastAttacksForCombo.get(i))
								check = false;
						} else
							check = false;
					}
					if (check) {
						enableOrDisableAttackForLaunching(player, tabAttacks.indexOf(c.specialAttack), true);
						break;
					}
				}
				enableOrDisableAttackForLaunching(player, tabAttacks.indexOf(tabLastAttacksForCombo.get(0)), true);
				break;
			}
			default:
				if (numberOfButtonPressed > 3)
					enableOrDisableAttackForLaunching(player, tabAttacks.indexOf(tabLastAttacksForCombo.get(0)), true);
				break;
		}
	}
	
	public void processAttack(int num) {
		castingAttack = null;
		currentAttack = tabAttacks.get(num);
		// Traitement de l'attaque
		long time = GameEngine.engineLoop;
		// Duee de l'affichage de l'attaque
		lastTimerAttack = time + tabAttacks.get(num).getTime();
		// Temps de recharge de l'attaque
		long cooldownAttack = tabAttacks.get(num).getInfoCooldown();
		tabAttacks.get(num).setEffectiveCooldown(time + cooldownAttack);
		// Global Cooldown
		GCDTimer = time + Constant.GLOBAL_COOLDOWN;
	}
	
	public void updateCooldownAllAttacks(Player player, long time) {
		for (int i = 0; i < tabAttacks.size(); i++) {
			Attack attack = tabAttacks.get(i); 
			if (attack.getEffectiveCooldown() <= time) {
				attack.setEffectiveCooldown(0);
				// L'attaque peut de nouveau etre lancee
				enableOrDisableAttackForLaunching(player, i, false);
			}
		}
	}
	
	public void updateTimeAttackStates(Player player, long time) {
		CollisionAttack collisionAttack = new CollisionAttack();
		PlayerInfo playerInfo = player.playerInfo;
		
		switch (playerInfo.atkState) {
			case Constant.ATK_STATE_ATTACKING:
				if (collisionAttack.Calculation(player, currentAttack)) {
					playerInfo.atkState = Constant.ATK_HAS_HIT;
				} else if (lastTimerAttack <= time) {
					playerInfo.atkState = Constant.ATK_STATE_IN_COOLDOWN;
					lastTimerAttack = -1;
				}
				break;
			case Constant.ATK_STATE_IN_COOLDOWN:
				// Si le player n'est plus en cooldown global
				/* Le cooldown des attaques, propre au lancement de chaque attaque,
				 et gere dans la methode setAtack() */
				if (GCDTimer != -1 && time >= GCDTimer)	{
					// Desactivation de l'attaque speciale et des attaques necessaire a son lancement
					if (currentAttack.isSpecialAttack) {
						for (Attack a : tabLastAttacksForCombo)
							enableOrDisableAttackForLaunching(player, tabAttacks.indexOf(a), false);
					}
					
					// Desactivation des attaques basiques
					for (int i = 0; i < tabAttacks.size(); i++)
						enableOrDisableAttackForLaunching(player, i, false);
	
					// On vide le tableau de sauvegarde des attaques
					tabLastAttacksForCombo.clear();
					currentAttack = null;
					
					// Remise a zero des combos
					for (int j = 0; j < tabCombos.size(); j++) {
						Attack specialAttackOfCombo = tabCombos.get(j).specialAttack;
						enableOrDisableAttackForLaunching(player, tabAttacks.indexOf(specialAttackOfCombo), false);
					}
					
					player.currentStatus = PlayerStatus.NORMAL;
					playerInfo.atkState = Constant.ATK_STATE_READY;
					GCDTimer = -1;
				}
				break;
			case Constant.ATK_HAS_HIT:
				if (lastTimerAttack <= time) {
					playerInfo.atkState = Constant.ATK_STATE_IN_COOLDOWN;
					lastTimerAttack = -1;
				}
				break;
			default: 
				break;
			}		
	}

}
