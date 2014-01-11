import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class ControleurGraphique {

	ArrayList<Joueur> tabJoueurs;
	
	public ControleurGraphique(JFrame f, ArrayList<Joueur> joueurs) {
		this.tabJoueurs = joueurs;
		f.addKeyListener(new ClavierListener());
	}
	
	class ClavierListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
/* ******************** PLAYER 1 ******************** */
			case KeyEvent.VK_Z: 
				tabJoueurs.get(0).setJump(true); 
            	break;
            case KeyEvent.VK_Q: 
            	tabJoueurs.get(0).setLeft(true);
            	tabJoueurs.get(0).setTurned(false);
            	break;
            case KeyEvent.VK_D: 
            	tabJoueurs.get(0).setRight(true); 
            	tabJoueurs.get(0).setTurned(true);
            	break;
            case KeyEvent.VK_E:
            	tabJoueurs.get(0).setAtk(0,true);
            	break;
            case KeyEvent.VK_A: 
            	tabJoueurs.get(0).setAtk(1,true);
               	break;
               	
/* ******************** PLAYER 2 ******************** */
			case KeyEvent.VK_UP:
				tabJoueurs.get(1).setJump(true);
				break;
			case KeyEvent.VK_LEFT:
				tabJoueurs.get(1).setLeft(true);
            	tabJoueurs.get(1).setTurned(false);
				break;
			case KeyEvent.VK_RIGHT:
				tabJoueurs.get(1).setRight(true);
            	tabJoueurs.get(1).setTurned(true);
				break;
			 case KeyEvent.VK_NUMPAD1: 
	          	tabJoueurs.get(1).setAtk(0,true);
	           	break;
	         case KeyEvent.VK_NUMPAD2: 
            	tabJoueurs.get(1).setAtk(1,true);
	           	break;
	           	
	           	
/* ******************** COMMANDE ******************** */	           	
	         case KeyEvent.VK_R: 
            	Game.resetGame() ;
            	break;
	         case KeyEvent.VK_T:
	        	 tabJoueurs.get(0).eject(100,50);
	        	 break;
	         case KeyEvent.VK_ESCAPE:
					if (Game.CURRENT_STATE == STATE.IN_GAME) {
						Game.CURRENT_STATE = STATE.PAUSED ;
						((CardLayout) Menu.cards.getLayout()).show(Menu.cards, "gamepaused");
					} else {
						Game.CURRENT_STATE = STATE.IN_GAME ;
					}
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
	            case KeyEvent.VK_D: 
	            	tabJoueurs.get(0).setRight(false); 
	            	break;
/* ******************** PLAYER 2 ******************** */
	            case KeyEvent.VK_UP:
					tabJoueurs.get(1).setJump(false);
					break;
				case KeyEvent.VK_LEFT:
					tabJoueurs.get(1).setLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					tabJoueurs.get(1).setRight(false);
					break;
				case KeyEvent.VK_E:
	            	tabJoueurs.get(0).setAtk(0,false);
	            	break;
		        case KeyEvent.VK_A: 
	            	tabJoueurs.get(0).setAtk(1,false);
	               	break;
		        case KeyEvent.VK_NUMPAD1: 
		          	tabJoueurs.get(1).setAtk(0,false);
		           	break;
		         case KeyEvent.VK_NUMPAD2: 
	            	tabJoueurs.get(1).setAtk(1,false);
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
