import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class ControleurGraphique {

	ArrayList<Joueur> tabJoueurs;

	public ControleurGraphique(JFrame f, ArrayList<Joueur> joueurs) {
		super();
		this.tabJoueurs = joueurs;
		f.addKeyListener(new ClavierListener());
	}

	class ClavierListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_Z:
				tabJoueurs.get(0).setJump(true);
				break;
			case KeyEvent.VK_Q:
				tabJoueurs.get(0).setLeft(true);
				break;
			case KeyEvent.VK_S:
				tabJoueurs.get(0).setCrouch(true);
				break;
			case KeyEvent.VK_D:
				tabJoueurs.get(0).setRight(true);
				break;
			case KeyEvent.VK_R:
				tabJoueurs.get(0).setX(250);
				tabJoueurs.get(0).setY(110);
				tabJoueurs.get(1).setX(500);
				tabJoueurs.get(1).setY(110);
				break;
			case KeyEvent.VK_SHIFT:
				tabJoueurs.get(0).run();
				break;
		////////////////// PLAYER 2 ///////////////////////
			case KeyEvent.VK_UP:
				tabJoueurs.get(1).setJump(true);
				break;
			case KeyEvent.VK_LEFT:
				tabJoueurs.get(1).setLeft(true);
				break;
			case KeyEvent.VK_DOWN:
				tabJoueurs.get(1).setCrouch(true);
				break;
			case KeyEvent.VK_RIGHT:
				tabJoueurs.get(1).setRight(true);
				break;
		
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_Z:
				tabJoueurs.get(0).setJump(false);
				break;
			case KeyEvent.VK_Q:
				tabJoueurs.get(0).setLeft(false);
				break;
			case KeyEvent.VK_S:
				tabJoueurs.get(0).setCrouch(false);
				break;
			case KeyEvent.VK_D:
				tabJoueurs.get(0).setRight(false);
				break;
			case KeyEvent.VK_SHIFT:
				tabJoueurs.get(0).notrun();
				break;
		////////////////// PLAYER 2 ///////////////////////
		case KeyEvent.VK_UP:
				tabJoueurs.get(1).setJump(false);
				break;
			case KeyEvent.VK_LEFT:
				tabJoueurs.get(1).setLeft(false);
				break;
			case KeyEvent.VK_DOWN:
				tabJoueurs.get(1).setCrouch(false);
				break;
			case KeyEvent.VK_RIGHT:
				tabJoueurs.get(1).setRight(false);
				break;
		
			}

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

	}

}
