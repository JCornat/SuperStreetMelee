package Controller;

import java.util.ArrayList;

import Model.Game;
import Model.Menu;
import Model.Player;

public class ControllerAdministrator {
	
	public static ArrayList<Player> listPlayers;
	public static Menu menu;
	public static ArrayList<Xbox360Controller> xbox_controllers ;

	public ControllerAdministrator(Menu me, ArrayList<Player> tabJ) {
		menu = me;
		listPlayers = tabJ;
	}
	
	public static void resetGame() {
		Game.resetGame();
	}
}
