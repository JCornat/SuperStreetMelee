package Model;

import java.awt.CardLayout;

public class DecreaseNumberOfLife {
	
	
	public void calculation(PlayerInfo playerInfo) {
		
		
		int newNumber = playerInfo.numberOfLife - 1 ;
		if (newNumber < 1) {
			Game.CURRENT_STATE = State.IN_MENU ;
			((CardLayout) Menu.cards.getLayout()).show(Menu.cards, "mainmenu");
		} else {
			playerInfo.numberOfLife = newNumber ;
		}
	}
}
