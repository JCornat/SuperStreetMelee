
public class Constant {

	// Status des attaques
	final static int ATK_STATE_READY = 0;
	final static int ATK_STATE_ATTACKING = 1;
	final static int ATK_STATE_IN_COOLDOWN = 2;
	final static int ATK_STATE_CASTING = 3;

	// Moteur
	final static int GRAVITY_MAX = 2;
	final static int INERTIE = 2;
	final static int GRAVITY_SPEED_CAP = 50;

	// Joueur
	final static int MAX_RUN_SPEED = 30;
	final static int MIN_HEALTH = 0;
	final static int JUMP_BASE = 2;
	final static int LIFE_NUMBER = 5;
	
	// Combos en centisecondes
	final static int TIME_WAIT_COMBO = 30;

	// En secondes
	final static int GAME_DURATION = 120;

	// En centisecondes
	final static long GLOBAL_COOLDOWN = 40;
}
