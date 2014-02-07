import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GraphicalController {

	ArrayList<Player> tabJoueurs;
	
	/**
	 * Constructeur pour la detection de touches
	 * @param f : frame sur laquelle appliquer le Listener
	 * @param joueurs : liste des joueurs actuels
	 */
	public GraphicalController(Menu menu, ArrayList<Player> players) {
		this.tabJoueurs = players;
		menu.addKeyListener(new ClavierListener());
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
                tabJoueurs.get(0).initCombo();
                tabJoueurs.get(0).setAtk(2,true);
                break;
            case KeyEvent.VK_F:
                tabJoueurs.get(0).initCombo();
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
            	GameEngine.resetGame() ;
            	break;
	         case KeyEvent.VK_T:
	        	 tabJoueurs.get(0).eject(100,50);
	        	 break;
	         case KeyEvent.VK_ESCAPE:
					if (GameEngine.CURRENT_STATE == State.IN_GAME) {
						GameEngine.CURRENT_STATE = State.PAUSED ;
						((CardLayout) Menu.cards.getLayout()).show(Menu.cards, "gamepaused");
					} else {
						GameEngine.CURRENT_STATE = State.IN_GAME ;
						((CardLayout) Menu.cards.getLayout()).show(Menu.cards, "play");
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
		            tabJoueurs.get(0).setAtk(2,false);
		            break;
		        case KeyEvent.VK_F:
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
