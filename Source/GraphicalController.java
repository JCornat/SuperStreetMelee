import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GraphicalController {

	ArrayList<Player> tabJoueurs;
	boolean zeroCommand;
	boolean oneCommand;

	/**
	 * Constructeur pour la detection de touches
	 * 
	 * @param f
	 *            : frame sur laquelle appliquer le Listener
	 * @param joueurs
	 *            : liste des joueurs actuels
	 */
	public GraphicalController(JFrame f, ArrayList<Player> joueurs) {
		this.tabJoueurs = joueurs;
		f.addKeyListener(new ClavierListener());
		this.zeroCommand = true;
		this.oneCommand = true;

		for (Xbox360Controller x : GameEngine.xbox_controllers) {
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
			switch (e.getKeyCode()) {
			/* ******************** PLAYER 1 ******************** */
			case KeyEvent.VK_Z:
				if (zeroCommand) {
					tabJoueurs.get(0).setJump(true);
				}

				break;
			case KeyEvent.VK_Q:

				if (zeroCommand) {
					tabJoueurs.get(0).setLeft(true);
					tabJoueurs.get(0).setTurned(false);
				}

				break;
			case KeyEvent.VK_D:

				if (zeroCommand) {
					tabJoueurs.get(0).setRight(true);
					tabJoueurs.get(0).setTurned(true);
				}

				break;
			case KeyEvent.VK_E:

				if (zeroCommand) {
					tabJoueurs.get(0).setAtk(0, true);
				}

				break;
			case KeyEvent.VK_A:

				if (zeroCommand) {
					tabJoueurs.get(0).setAtk(1, true);
				}

				break;

			/* ******************** PLAYER 2 ******************** */
			case KeyEvent.VK_UP:

				if (oneCommand) {
					tabJoueurs.get(1).setJump(true);
				}

				break;
			case KeyEvent.VK_LEFT:

				if (oneCommand) {
					tabJoueurs.get(1).setLeft(true);
					tabJoueurs.get(1).setTurned(false);
				}

				break;
			case KeyEvent.VK_RIGHT:

				if (oneCommand) {
					tabJoueurs.get(1).setRight(true);
					tabJoueurs.get(1).setTurned(true);
				}

				break;
			case KeyEvent.VK_NUMPAD1:

				if (oneCommand) {
					tabJoueurs.get(1).setAtk(0, true);
				}

				break;
			case KeyEvent.VK_NUMPAD2:

				if (oneCommand) {
					tabJoueurs.get(1).setAtk(1, true);
				}

				break;

			/* ******************** COMMANDE ******************** */
			case KeyEvent.VK_R:
				GameEngine.resetGame();
				break;
			case KeyEvent.VK_T:
				tabJoueurs.get(0).eject(100, 50);
				break;
			case KeyEvent.VK_ESCAPE:
				if (GameEngine.CURRENT_STATE == State.IN_GAME) {
					GameEngine.CURRENT_STATE = State.PAUSED;
					((CardLayout) Menu.cards.getLayout()).show(Menu.cards,
							"gamepaused");

				} else {
					GameEngine.CURRENT_STATE = State.IN_GAME;
					((CardLayout) Menu.cards.getLayout()).show(Menu.cards,
							"play");
				}
				break;
			default:
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_Z:

				if (zeroCommand) {
					tabJoueurs.get(0).setJump(false);
				}

				break;
			case KeyEvent.VK_Q:

				if (zeroCommand) {
					tabJoueurs.get(0).setLeft(false);
				}

				break;
			case KeyEvent.VK_D:

				if (zeroCommand) {
					tabJoueurs.get(0).setRight(false);
				}

				break;
			case KeyEvent.VK_E:

				if (zeroCommand) {
					tabJoueurs.get(0).setAtk(0, false);
				}

				break;
			case KeyEvent.VK_A:

				if (zeroCommand) {
					tabJoueurs.get(0).setAtk(1, false);
				}

				break;
			/* ******************** PLAYER 2 ******************** */
			case KeyEvent.VK_UP:

				if (oneCommand) {
					tabJoueurs.get(1).setJump(false);
				}

				break;
			case KeyEvent.VK_LEFT:

				if (oneCommand) {
					tabJoueurs.get(1).setLeft(false);
				}

				break;
			case KeyEvent.VK_RIGHT:

				if (oneCommand) {
					tabJoueurs.get(1).setRight(false);
				}

				break;
			case KeyEvent.VK_NUMPAD1:

				if (oneCommand) {
					tabJoueurs.get(1).setAtk(0, false);
				}

				break;
			case KeyEvent.VK_NUMPAD2:

				if (oneCommand) {
					tabJoueurs.get(1).setAtk(1, false);
				}

				break;
			default:
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}
	}

}
