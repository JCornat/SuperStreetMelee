package Model;

public class Constant {

	// Status des attaques
	public final static int ATK_STATE_READY = 0;
	public final static int ATK_STATE_ATTACKING = 1;
	public final static int ATK_STATE_IN_COOLDOWN = 2;
	public final static int ATK_STATE_CASTING = 3;
	public final static int ATK_HAS_HIT = 4;

	// Moteur
	public final static int GRAVITY_MAX = 2;
	public final static int INERTIE = 2;
	public final static int GRAVITY_SPEED_CAP = 50;

	// Joueur
	public final static int MAX_RUN_SPEED = 30;
	public final static int MIN_HEALTH = 0;
	public final static int JUMP_BASE = 2;
	public final static int LIFE_NUMBER = 5;
	public final static int ATTACK_NUMBER = 6;

	// Manette
	public final static int TIME_MAX_BETWEEN_JUMPS = 55;
	public final static int TIME_MIN_BETWEEN_JUMPS = 25;
	public final static int TIME_BETWEEN_PAUSES = 45;
	
	// Combos en centisecondes
	public final static int TIME_WAIT_COMBO = 50;

	// En secondes
	public final static int GAME_DURATION = 240;

	// En centisecondes
	public final static long GLOBAL_COOLDOWN = 20;
	
	// DEBUGGING MODE
	public static boolean DEBUGGING = false;
}
