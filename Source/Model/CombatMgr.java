package Model;

import java.util.ArrayList;

public class CombatMgr {
	
	public Attack currentAttack;
	public ArrayList<Attack> listAttacks, listLastAttacksForCombo;
	public ArrayList<Combo> listCombos;
	public long lastTimerAttack, castingTimer;
	// Global Cooldown Timer
	public long GCDTimer;
	public ArrayList<Boolean> attacks, attacksReleased;
	public Attack castingAttack;
	public long waitedTimeForCombo;
	
	public CombatMgr() {
		currentAttack = null;
		lastTimerAttack = -1;
		GCDTimer = -1;
		waitedTimeForCombo = -1;
		listAttacks = new ArrayList<Attack>();
		listAttacks.clear();
		listCombos = new ArrayList<Combo>();
		listCombos.clear();
		// Creation des attaques	
		Attack small = new Attack("Small", 55, 5, 5, 0, 10, 0, 20, 20, false);
		Attack medium = new Attack("Medium", 73, 7, 10, 0, 75, 100, 60, 60, false);
		Attack big = new Attack("Big", 80, 10, 15, 30, 40, 150, 100, 100, false);
		// A venir
		// Attack charge = new Attack("Charge", 80, 10, 5, 0, 40, 150, 100, 100,
		// false);

		// Combo 1
		Attack specialAttack1 = new Attack("Special1", 100, 20, 20, 25, 60, 0, 100, 100, true);
		Combo combo1 = new Combo(big, small, specialAttack1);

		// Combo 2
		Attack specialAttack2 = new Attack("Special2", 100, 20, 25, 27, 60, 0, 100, 100, true);
		Combo combo2 = new Combo(medium, small, specialAttack2);

		// Combo 3
		Attack specialAttack3 = new Attack("Special3", 100, 20, 35, 30, 60, 0, 100, 100, true);
		Combo combo3 = new Combo(medium, small, big, specialAttack3);

		// Ajouts dans la liste des attaques
		// /!\ Penser a update la constante ATTACK_NUMBER
		listAttacks.add(small);
		listAttacks.add(medium);
		listAttacks.add(big);
		listAttacks.add(specialAttack1);
		listAttacks.add(specialAttack2);
		listAttacks.add(specialAttack3);

		// Ajouts dans la liste des combos
		listCombos.add(combo1);
		listCombos.add(combo2);
		listCombos.add(combo3);
		
		listLastAttacksForCombo 	= new ArrayList<Attack>(); 	listLastAttacksForCombo.clear();
		attacks 				= new ArrayList<Boolean>(); attacks.clear();
		attacksReleased 		= new ArrayList<Boolean>(); attacksReleased.clear();
		for (int i = 0; i < listAttacks.size(); i++) {
			attacks.add(false);
			attacksReleased.add(false);
		}
	}
	
	public void checkAndLaunchAttacks(Player player, long time) {
		PlayerInfo playerInfo = player.playerInfo;
		
		if (playerInfo.atkState == Constant.ATK_STATE_CASTING) {
			if (time >= this.castingTimer) {
				// Lancement de l'attaque
				processAttack(listAttacks.indexOf(castingAttack));
				// L'attaque est en train d'etre produite
				playerInfo.atkState = Constant.ATK_STATE_ATTACKING;
			}
		}
		
		if (playerInfo.atkState == Constant.ATK_STATE_READY) {
			for (int i = 0; i < attacks.size(); i++) {
				if (attacks.get(i) && !attacksReleased.get(i)) {
					attacksReleased.set(i, true);
					castingAttack = listAttacks.get(i);
					playerInfo.atkState = Constant.ATK_STATE_CASTING;
					castingTimer = time + listAttacks.get(i).cast;
				}
			}
		}
	}
	
	public void enableOrDisableAttackForLaunching(Player player, int num, boolean enable) {
		PlayerInfo playerInfo = player.playerInfo;
		boolean isWaitingForCombo = player.playerInfoBoolean.isWaitingForCombo;
		if (enable && playerInfo.atkState == Constant.ATK_STATE_READY && listAttacks.get(num).getEffectiveCooldown() <= GameEngine.engineLoop) {
			if (!isWaitingForCombo)
				attacks.set(num, enable);
			else if (isWaitingForCombo && listAttacks.get(num).isBind)
				listLastAttacksForCombo.add(listAttacks.get(num));
		} else
			attacks.set(num, false);
			
		if (!enable)
			attacksReleased.set(num, enable);
	}
	
	public void processCombo(Player player) {
		// Attente terminee
		waitedTimeForCombo = -1;
		// Declenchement des combos, lancement des attaques speciales correspondantes
		int numberOfButtonPressed = listLastAttacksForCombo.size();
		switch (numberOfButtonPressed) {
			case 1:
				enableOrDisableAttackForLaunching(player, listAttacks.indexOf(listLastAttacksForCombo.get(0)), true);
				break;
			// Combos a 2 touches et plus
			case 2:
			case 3: {
				for (Combo c : listCombos) {
					boolean check = true;
					for (int i = 0; i < c.nbOfBind; i++) {
						if (c.nbOfBind == numberOfButtonPressed) {
							if (c.binds[i] != listLastAttacksForCombo.get(i))
								check = false;
						} else
							check = false;
					}
					if (check) {
						enableOrDisableAttackForLaunching(player, listAttacks.indexOf(c.specialAttack), true);
						break;
					}
				}
				enableOrDisableAttackForLaunching(player, listAttacks.indexOf(listLastAttacksForCombo.get(0)), true);
				break;
			}
			default:
				if (numberOfButtonPressed > 3)
					enableOrDisableAttackForLaunching(player, listAttacks.indexOf(listLastAttacksForCombo.get(0)), true);
				break;
		}
	}
	
	public void processAttack(int num) {
		castingAttack = null;
		currentAttack = listAttacks.get(num);
		// Traitement de l'attaque
		long time = GameEngine.engineLoop;
		// Duee de l'affichage de l'attaque
		lastTimerAttack = time + listAttacks.get(num).getTime();
		// Temps de recharge de l'attaque
		long cooldownAttack = listAttacks.get(num).getInfoCooldown();
		listAttacks.get(num).setEffectiveCooldown(time + cooldownAttack);
		// Global Cooldown
		GCDTimer = time + Constant.GLOBAL_COOLDOWN;
	}
	
	public void updateCooldownAllAttacks(Player player, long time) {
		for (int i = 0; i < listAttacks.size(); i++) {
			Attack attack = listAttacks.get(i); 
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
						for (Attack a : listLastAttacksForCombo)
							enableOrDisableAttackForLaunching(player, listAttacks.indexOf(a), false);
					}
					
					// Desactivation des attaques basiques
					for (int i = 0; i < listAttacks.size(); i++)
						enableOrDisableAttackForLaunching(player, i, false);
	
					// On vide le tableau de sauvegarde des attaques
					listLastAttacksForCombo.clear();
					currentAttack = null;
					
					// Remise a zero des combos
					for (int j = 0; j < listCombos.size(); j++) {
						Attack specialAttackOfCombo = listCombos.get(j).specialAttack;
						enableOrDisableAttackForLaunching(player, listAttacks.indexOf(specialAttackOfCombo), false);
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
