import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class ControleurGraphique {

	
	Joueur j; 
	
	public ControleurGraphique(JFrame f, Joueur p) {
		super();
		j = p;
		f.addKeyListener(new ClavierListener());
	}
	
	class ClavierListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_Z: 
            	j.setJump(true); 
            	break;
            case KeyEvent.VK_Q: 
            	j.setLeft(true);  
            	break;
            case KeyEvent.VK_S: 
            	j.setCrouch(true); 
            	break;
            case KeyEvent.VK_D: 
            	j.setRight(true); 
            	break;
            case KeyEvent.VK_SHIFT: 
            	j.run(); 
            	break;
            
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_Z: 
	            	j.setJump(false); 
	            	break;
	            case KeyEvent.VK_Q: 
	            	j.setLeft(false);  
	            	break;
	            case KeyEvent.VK_S: 
	            	j.setCrouch(false); 
	            	break;
	            case KeyEvent.VK_D: 
	            	j.setRight(false); 
	            	break;
	            case KeyEvent.VK_SHIFT: 
	            	j.notrun(); 
	            	break;
	            
			}
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		
		
		
		
		
	}
	
}
