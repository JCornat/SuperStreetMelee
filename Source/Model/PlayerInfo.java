package Model;


public class PlayerInfo {

	public String name;
	public int numberOfLife;
	public int positionXOnJumping;
	public int positionYOnJumping;
	public int health;
	public int atkState;
	public int jumps;
	
	public PlayerInfo(String n) {
		name = n;
		numberOfLife = Constant.LIFE_NUMBER;
		health = Constant.MIN_HEALTH;
		atkState = Constant.ATK_STATE_READY;
		jumps = Constant.JUMP_BASE;
	}
	
}
