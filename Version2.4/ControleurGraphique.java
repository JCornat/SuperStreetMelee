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
            	tabJoueurs.get(0).setTurned(Joueur.TURNED_LEFT);
            	break;
            /*case KeyEvent.VK_S: 
            	tabJoueurs.get(0).setCrouch(true); 
            	break;*/
            case KeyEvent.VK_D: 
            	tabJoueurs.get(0).setRight(true); 
            	tabJoueurs.get(0).setTurned(Joueur.TURNED_RIGHT);
            	break;
            case KeyEvent.VK_R: 
				tabJoueurs.get(0).setX(250);
				tabJoueurs.get(0).setY(110);
				tabJoueurs.get(0).resetLife();
				tabJoueurs.get(1).setX(500);
				tabJoueurs.get(1).setY(110);
				tabJoueurs.get(1).resetLife();
            	break;
            case KeyEvent.VK_SHIFT: 
            	tabJoueurs.get(0).run(); 
            	break;
            case KeyEvent.VK_E: 
            	tabJoueurs.get(0).attack("Base", tabJoueurs);
            	break;
            case KeyEvent.VK_A: 
            	tabJoueurs.get(0).attack("Grosse", tabJoueurs);
               	break;
/* ******************** PLAYER 2 ******************** */
			case KeyEvent.VK_UP:
				tabJoueurs.get(1).setJump(true);
				break;
			case KeyEvent.VK_LEFT:
				tabJoueurs.get(1).setLeft(true);
            	tabJoueurs.get(1).setTurned(Joueur.TURNED_LEFT);
				break;
			/*case KeyEvent.VK_DOWN:
				tabJoueurs.get(1).setCrouch(true);
				break;*/
			case KeyEvent.VK_RIGHT:
				tabJoueurs.get(1).setRight(true);
            	tabJoueurs.get(1).setTurned(Joueur.TURNED_RIGHT);
				break;
			 case KeyEvent.VK_NUMPAD1: 
	          	tabJoueurs.get(1).attack("Base", tabJoueurs);
	           	break;
	         case KeyEvent.VK_NUMPAD2: 
	           	tabJoueurs.get(1).attack("Grosse", tabJoueurs);
	           	break;
	         default:
	        	 break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
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
/* ******************** PLAYER 2 ******************** */
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
				case KeyEvent.VK_3: 
	            	tabJoueurs.get(1).notrun(); 
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
