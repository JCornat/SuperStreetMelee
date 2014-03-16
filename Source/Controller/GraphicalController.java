package Controller;
import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import Model.Game;
import Model.Menu;
import Model.Player;
import Model.State;

public class GraphicalController {

	boolean zeroCommand;
	boolean oneCommand;
	ControllerAdministrator controllerAdministrator;

	/**
	 * Constructeur pour la detection de touches
	 * 
	 * @param f : frame sur laquelle appliquer le Listener
	 * @param joueurs : liste des joueurs actuels
	 */

	public GraphicalController() {
		controllerAdministrator.menu.addKeyListener(new ClavierListener());
		this.zeroCommand = true;
		this.oneCommand = true;

		for (Xbox360Controller x : controllerAdministrator.xbox_controllers) {
			if (x.getIndexPlayer() == 0) {
				zeroCommand = false;
			} else if (x.getIndexPlayer() == 1) {
				oneCommand = false;
			}
		}
	}

	/**
	 * Mises a jour de variables en fonction d'appui de touches
	 */
	class ClavierListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if (zeroCommand) {
				switch (e.getKeyCode()) {
				/* ******************** PLAYER 1 ******************** */
				case KeyEvent.VK_Z:
					controllerAdministrator.tabJoueurs.get(0).setJump(true);
					break;
				case KeyEvent.VK_Q:
					controllerAdministrator.tabJoueurs.get(0).playerInfoBoolean.setLeft(true);
					controllerAdministrator.tabJoueurs.get(0).playerInfoBoolean.setTurned(false);
					break;
				case KeyEvent.VK_D:
					controllerAdministrator.tabJoueurs.get(0).playerInfoBoolean.setRight(true);
					controllerAdministrator.tabJoueurs.get(0).playerInfoBoolean.setTurned(true);
					break;
				case KeyEvent.VK_E:
					controllerAdministrator.tabJoueurs.get(0).setAtk(0, true);
					break;
				case KeyEvent.VK_A:
					controllerAdministrator.tabJoueurs.get(0).initCombo();
					controllerAdministrator.tabJoueurs.get(0).setAtk(2, true);
					break;
				case KeyEvent.VK_F:
					controllerAdministrator.tabJoueurs.get(0).initCombo();
					controllerAdministrator.tabJoueurs.get(0).setAtk(1, true);
					break;
				}
			}

			/* ******************** PLAYER 2 ******************** */
			if (oneCommand) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					controllerAdministrator.tabJoueurs.get(1).setJump(true);
					break;
				case KeyEvent.VK_LEFT:
					controllerAdministrator.tabJoueurs.get(1).playerInfoBoolean.setLeft(true);
					controllerAdministrator.tabJoueurs.get(1).playerInfoBoolean.setTurned(false);
					break;
				case KeyEvent.VK_RIGHT:
					controllerAdministrator.tabJoueurs.get(1).playerInfoBoolean.setRight(true);
					controllerAdministrator.tabJoueurs.get(1).playerInfoBoolean.setTurned(true);
					break;
				case KeyEvent.VK_NUMPAD1:
					controllerAdministrator.tabJoueurs.get(1).setAtk(0, true);
					break;
				case KeyEvent.VK_NUMPAD2:
					controllerAdministrator.tabJoueurs.get(1).setAtk(1, true);
					break;
				}
			}

			/* ******************** COMMANDE ******************** */

			switch (e.getKeyCode()) {
			case KeyEvent.VK_R:
				controllerAdministrator.resetGame();
				break;
			case KeyEvent.VK_T:
				controllerAdministrator.tabJoueurs.get(0).eject(100, 0);
				break;
			case KeyEvent.VK_ESCAPE:
				if (Game.CURRENT_STATE == State.IN_GAME) {
					Game.CURRENT_STATE = State.PAUSED;
					((CardLayout) Menu.cards.getLayout()).show(Menu.cards,"gamepaused");

				} else {
					Game.CURRENT_STATE = State.IN_GAME;
					((CardLayout) Menu.cards.getLayout()).show(Menu.cards,"play");
				}
				break;
			default:
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (zeroCommand) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_Z:
					controllerAdministrator.tabJoueurs.get(0).setJump(false);
					break;
				case KeyEvent.VK_Q:
					controllerAdministrator.tabJoueurs.get(0).playerInfoBoolean.setLeft(false);
					break;
				case KeyEvent.VK_D:
					controllerAdministrator.tabJoueurs.get(0).playerInfoBoolean.setRight(false);
					break;
				case KeyEvent.VK_E:
					controllerAdministrator.tabJoueurs.get(0).setAtk(0, false);
					break;
				case KeyEvent.VK_A:
					controllerAdministrator.tabJoueurs.get(0).setAtk(2, false);
					break;
				case KeyEvent.VK_F:
					controllerAdministrator.tabJoueurs.get(0).setAtk(1, false);
					break;
				}
			}

			/* ******************** PLAYER 2 ******************** */
			if (oneCommand) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					controllerAdministrator.tabJoueurs.get(1).setJump(false);
					break;
				case KeyEvent.VK_LEFT:
					controllerAdministrator.tabJoueurs.get(1).playerInfoBoolean.setLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					controllerAdministrator.tabJoueurs.get(1).playerInfoBoolean.setRight(false);
					break;
				case KeyEvent.VK_NUMPAD1:
					controllerAdministrator.tabJoueurs.get(1).setAtk(0, false);
					break;
				case KeyEvent.VK_NUMPAD2:
					controllerAdministrator.tabJoueurs.get(1).setAtk(1, false);
					break;
				default:
					break;
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

}
