
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
	boolean attackReady[];
	
	
	public Xbox360Controller(Controller controller, ArrayList<Player> joueurs, int indexPlayer) {
		
		this.tabJoueurs = joueurs ;
		this.indexPlayer = indexPlayer ;
		this.controller = controller ;
		this.hasJumped = false ;
		this.hasPaused = false ;
		this.lastLoopJump = 0 ;
		this.lastLoopPause = 0 ;
		this.attackReady = new boolean[Constant.ATTACK_NUMBER];
		for (int i = 0; i < Constant.ATTACK_NUMBER; i++)
			attackReady[i] = false;
	}
	
	public static ArrayList<Xbox360Controller> initControllers(ArrayList<Player> players){
		
		ArrayList<Xbox360Controller> controllers = new ArrayList<Xbox360Controller>() ;
		
		int indexPlayer = 0 ;
		
		Controller[] allTheControllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		
		for(int i = 0; i < allTheControllers.length; i++){
			
            Controller controller = allTheControllers[i];
      
            if ((controller.getType() == Controller.Type.GAMEPAD) || (controller.getName().matches("(?i).*stick.*"))){
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
			
			if (main.engineLoop >= lastLoopJump + Constant.TIME_MAX_BETWEEN_JUMPS){
				hasJumped = false ;
			}
			
			if ((tabJoueurs.get(0).currentStatus == PlayerStatus.FALLING) && (main.engineLoop >= lastLoopJump + Constant.TIME_MIN_BETWEEN_JUMPS)) {
				hasJumped = false ;
				lastLoopJump = main.engineLoop ;
			}
			
			if (main.engineLoop >= lastLoopPause + Constant.TIME_BETWEEN_PAUSES) {
				hasPaused = false ;
			}

			for (Component c : this.controller.getComponents()) {
				
				
				/*if (c.getIdentifier().getName().equals("rz")) {
					if (c.getPollData() > 0.3) {
						// Charge ici
					}
				}*/

				if (this.controller.getType() == Controller.Type.GAMEPAD) {
					if (c.getIdentifier().getName().equals("0")) {
						if (c.getPollData() == 1.0f) {
							if (!hasJumped) {
								tabJoueurs.get(this.indexPlayer).setJump(true);
								hasJumped = true;
								lastLoopJump = main.engineLoop;
							}
						} else {
							tabJoueurs.get(this.indexPlayer).setJump(false);
						}
					}
					if (c.getIdentifier().getName().equals("x")) {

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
					if (c.getIdentifier().getName().equals("pov")) {

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
					if (c.getIdentifier().getName().equals("1")) {
						if (c.getPollData() == 1.0f) {
							if (!attackReady[2]) {
								tabJoueurs.get(this.indexPlayer).initCombo();
								tabJoueurs.get(this.indexPlayer)
										.setAtk(2, true);
								attackReady[2] = true;
							}
						} else {
							attackReady[2] = false;
						}
					}
					if (c.getIdentifier().getName().equals("2")) {
						if (c.getPollData() == 1.0f) {
							if (!attackReady[0]) {
								tabJoueurs.get(this.indexPlayer)
										.setAtk(0, true);
								attackReady[0] = true;
							}
						} else {
							attackReady[0] = false;
						}
					}
					if (c.getIdentifier().getName().equals("3")) {
						if (c.getPollData() == 1.0f) {
							if (!attackReady[1]) {
								tabJoueurs.get(this.indexPlayer).initCombo();
								tabJoueurs.get(this.indexPlayer)
										.setAtk(1, true);
								attackReady[1] = true;
							}
						} else {
							attackReady[1] = false;
						}
					}
					if (c.getIdentifier().getName().equals("7")) {
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
								hasPaused = true;
								lastLoopPause = main.engineLoop;
							}
						} else {

						}

					}
				}else if (this.controller.getName().matches("(?i).*stick.*")) {
					if (c.getIdentifier().getName().equals("pov")) {
						
						if (c.getPollData() == 1) {
							tabJoueurs.get(this.indexPlayer).setLeft(true);
							tabJoueurs.get(this.indexPlayer).setTurned(false);
						} else if (c.getPollData() == 0.75) {

						} else if (c.getPollData() == 0.5) {
							tabJoueurs.get(this.indexPlayer).setRight(true);
							tabJoueurs.get(this.indexPlayer).setTurned(true);
						} else if (c.getPollData() == 0.25) {

						}else {
							tabJoueurs.get(this.indexPlayer).setRight(false);
							tabJoueurs.get(this.indexPlayer).setLeft(false);
						}
					}
					
					if (c.getIdentifier().getName().equals("0")) {
						if (c.getPollData() == 1.0f) {
							if (!hasJumped) {
								tabJoueurs.get(this.indexPlayer).setJump(true);
								hasJumped = true;
								lastLoopJump = main.engineLoop;
							}
						} else {
							tabJoueurs.get(this.indexPlayer).setJump(false);
						}
					}
					
					if (c.getIdentifier().getName().equals("1")) {
						if (c.getPollData() == 1.0f) {
							if (!attackReady[2]) {
								tabJoueurs.get(this.indexPlayer).initCombo();
								tabJoueurs.get(this.indexPlayer)
										.setAtk(2, true);
								attackReady[2] = true;
							}
						} else {
							attackReady[2] = false;
						}
					}
					if (c.getIdentifier().getName().equals("2")) {
						if (c.getPollData() == 1.0f) {
							if (!attackReady[0]) {
								tabJoueurs.get(this.indexPlayer)
										.setAtk(0, true);
								attackReady[0] = true;
							}
						} else {
							attackReady[0] = false;
						}
					}
					if (c.getIdentifier().getName().equals("3")) {
						if (c.getPollData() == 1.0f) {
							if (!attackReady[1]) {
								tabJoueurs.get(this.indexPlayer).initCombo();
								tabJoueurs.get(this.indexPlayer)
										.setAtk(1, true);
								attackReady[1] = true;
							}
						} else {
							attackReady[1] = false;
						}
					}
					
					if (c.getIdentifier().getName().equals("7")) {
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
								hasPaused = true;
								lastLoopPause = main.engineLoop;
							}
						} else {

						}

					}
				}
				
				
			}

		}
	}
}
