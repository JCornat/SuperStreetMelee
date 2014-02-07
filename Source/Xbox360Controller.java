
import java.awt.CardLayout;
import java.util.ArrayList;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


public class Xbox360Controller {

	private Controller controller ;
	private ArrayList<Player> tabJoueurs;
	private int indexPlayer ;
	private boolean hasJumped ;
	private boolean hasPaused ;
	long lastLoopJump ;
	long lastLoopPause ;
	
	
	
	public Xbox360Controller(Controller controller, ArrayList<Player> joueurs, int indexPlayer) {
		
		this.tabJoueurs = joueurs ;
		this.indexPlayer = indexPlayer ;
		this.controller = controller ;
		this.hasJumped = false ;
		this.hasPaused = false ;
		this.lastLoopJump = 0 ;
		this.lastLoopPause = 0 ;
        
	}
	
	public static ArrayList<Xbox360Controller> initControllers(ArrayList<Player> players){
		
		ArrayList<Xbox360Controller> controllers = new ArrayList<Xbox360Controller>() ;
		
		int indexPlayer = 0 ;
		
		Controller[] allTheControllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		
		for(int i = 0; i < allTheControllers.length; i++){
			
            Controller controller = allTheControllers[i];
      
            if (controller.getType() == Controller.Type.GAMEPAD){
                if (indexPlayer <= players.size()) {
                	
					controllers.add(new Xbox360Controller(controller, players,indexPlayer));
					System.out.println(controller.getName() + " has been added for player " + (indexPlayer+1));
					indexPlayer++ ;
				}
            }
		}
		return controllers ;
		
	}
	
	public Controller getController(){
		return this.controller ;
	}
	
	public int getIndexPlayer(){
		return this.indexPlayer ;
	}
	public void checkButtons(){
		
		if (this.controller != null) {
			this.controller.poll();
			
			if (main.engineLoop >= lastLoopJump + 55){
				hasJumped = false ;
			}
			
			if ((tabJoueurs.get(0).currentStatus == PlayerStatus.FALLING) && (main.engineLoop >= lastLoopJump + 25)) {
				hasJumped = false ;
				lastLoopJump = main.engineLoop ;
			}
			
			if (main.engineLoop >= lastLoopPause + 45) {
				hasPaused = false ;
			}
			for (Component c : this.controller.getComponents()) {

				if (c.getName().equals("Button 0")) {
					if (c.getPollData() == 1.0f) {
						if (!hasJumped) {
							tabJoueurs.get(this.indexPlayer).setJump(true);
							hasJumped = true ;
							lastLoopJump = main.engineLoop ;
						}
					} else {
						tabJoueurs.get(this.indexPlayer).setJump(false);
					}
				}

				if (c.getName().equals("X Axis")) {

					if (c.getPollData() > 0.4) {
						tabJoueurs.get(this.indexPlayer).setRight(true);
						tabJoueurs.get(this.indexPlayer).setTurned(true);
					} else if (c.getPollData() < -0.4) {
						tabJoueurs.get(this.indexPlayer).setLeft(true);
						tabJoueurs.get(this.indexPlayer).setTurned(false);
					} else {
						tabJoueurs.get(this.indexPlayer).setRight(false);
						tabJoueurs.get(this.indexPlayer).setLeft(false);
					}
				}

				if (c.getName().equals("Hat Switch")) {

					if (c.getPollData() == 1) {
						tabJoueurs.get(this.indexPlayer).setLeft(true);
						tabJoueurs.get(this.indexPlayer).setTurned(false);
					} else if (c.getPollData() == 0.75) {

					} else if (c.getPollData() == 0.5) {
						tabJoueurs.get(this.indexPlayer).setRight(true);
						tabJoueurs.get(this.indexPlayer).setTurned(true);
					} else if (c.getPollData() == 0.25) {

					}
				}

				if (c.getName().equals("Button 1")) {
					if (c.getPollData() == 1.0f) {
						tabJoueurs.get(this.indexPlayer).setAtk(1, true);
					} else {
						tabJoueurs.get(this.indexPlayer).setAtk(1, false);
					}
				}

				if (c.getName().equals("Button 2")) {
					if (c.getPollData() == 1.0f) {
						tabJoueurs.get(this.indexPlayer).setAtk(0, true);
					} else {
						tabJoueurs.get(this.indexPlayer).setAtk(0, false);
					}
				}
				
				if (c.getName().equals("Button 7")) {
					if (c.getPollData() == 1.0f) {
						if (!hasPaused) {
							if (GameEngine.CURRENT_STATE == State.IN_GAME) {
								GameEngine.CURRENT_STATE = State.PAUSED;
								((CardLayout) Menu.cards.getLayout()).show(
										Menu.cards, "gamepaused");
							} else {
								GameEngine.CURRENT_STATE = State.IN_GAME;
								((CardLayout) Menu.cards.getLayout()).show(
										Menu.cards, "play");
							}
							hasPaused = true ;
							lastLoopPause = main.engineLoop ;
						}
					} else {
						
					}
				
				}
				
				
			}

		}
	}
}
