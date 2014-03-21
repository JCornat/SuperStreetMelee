package Model;

import java.awt.CardLayout;


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
	
	public void decreaseNumberOfLife() {
		int newNumber = numberOfLife - 1 ;
		if (newNumber < 1) {
			Game.CURRENT_STATE = State.IN_MENU ;
			((CardLayout) Menu.cards.getLayout()).show(Menu.cards, "mainmenu");
		} else {
			numberOfLife = newNumber ;
		}
	}
}
