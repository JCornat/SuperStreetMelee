package Controller;


import java.awt.CardLayout;
import java.util.ArrayList;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import Model.Constant;
import Model.Game;
import Model.GameEngine;
import Model.Menu;
import Model.Player;
import Model.PlayerStatus;
import Model.State;


public class Xbox360Controller {

	private Controller controller ;
	private int indexPlayer ;
	private boolean hasJumped ;
	private boolean hasPaused ;
	long lastLoopJump ;
	long lastLoopPause ;
	boolean attackReady[];
	static ControllerAdministrator controllerAdministrator;
	
	
	public Xbox360Controller(Controller controller, ArrayList<Player> joueurs, int indexPlayer) {
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
	
	public static ArrayList<Xbox360Controller> initControllers(){
		
		ArrayList<Xbox360Controller> controllers = new ArrayList<Xbox360Controller>() ;
		
		int indexPlayer = controllerAdministrator.tabJoueurs.size() ;
		
		Controller[] allTheControllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		
		for(int i = 0; i < allTheControllers.length; i++){
			
            Controller controller = allTheControllers[i];
      
            if ((controller.getType() == Controller.Type.GAMEPAD) || (controller.getName().matches("(?i).*stick.*"))){
                if (indexPlayer <= controllerAdministrator.tabJoueurs.size()) {
                	
					controllers.add(new Xbox360Controller(controller, controllerAdministrator.tabJoueurs,indexPlayer - 1));
					System.out.println(controller.getName() + " has been added for player " + indexPlayer);
					indexPlayer-- ;
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
			
			if (GameEngine.engineLoop >= lastLoopJump + Constant.TIME_MAX_BETWEEN_JUMPS){
				hasJumped = false ;
			}
			
			if ((controllerAdministrator.tabJoueurs.get(indexPlayer).currentStatus == PlayerStatus.FALLING) && (GameEngine.engineLoop >= lastLoopJump + Constant.TIME_MIN_BETWEEN_JUMPS)) {
				hasJumped = false ;
				lastLoopJump = GameEngine.engineLoop ;
			}
			
			if (GameEngine.engineLoop >= lastLoopPause + Constant.TIME_BETWEEN_PAUSES) {
				hasPaused = false ;
			}

			for (Component c : this.controller.getComponents()) {
				
				
				/*if (c.getIdentifier().getName().equals("rz")) {
					if (c.getPollData() > 0.3) {
						// Charge ici
						controllerAdministrator.tabJoueurs.get(this.indexPlayer).eject(100, 0);
						controllerAdministrator.tabJoueurs.get(this.indexPlayer).currentStatus = PlayerStatus.CHARGING;
					}
				}*/

				if (this.controller.getType() == Controller.Type.GAMEPAD) {
					if (c.getIdentifier().getName().equals("0")) {
						if (c.getPollData() == 1.0f) {
							if (!hasJumped) {
								controllerAdministrator.tabJoueurs.get(this.indexPlayer).setJump(true);
								hasJumped = true;
								lastLoopJump = GameEngine.engineLoop;
							}
						} else {
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).setJump(false);
						}
					}
					if (c.getIdentifier().getName().equals("x")) {

						if (c.getPollData() > 0.4) {
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setRight(true);
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setTurned(true);
						} else if (c.getPollData() < -0.4) {
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setLeft(true);
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setTurned(false);
						} else {
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setRight(false);
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setLeft(false);
						}
					}
					if (c.getIdentifier().getName().equals("pov")) {

						if (c.getPollData() == 1) {
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setLeft(true);
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setTurned(false);
						} else if (c.getPollData() == 0.75) {

						} else if (c.getPollData() == 0.5) {
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setRight(true);
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setTurned(true);
						} else if (c.getPollData() == 0.25) {

						}
					}
					if (c.getIdentifier().getName().equals("1")) {
						if (c.getPollData() == 1.0f) {
							if (attackReady[2]) {
								controllerAdministrator.tabJoueurs.get(this.indexPlayer).initCombo();
								controllerAdministrator.tabJoueurs.get(this.indexPlayer).setAtk(2, true);
								attackReady[2] = false;
							}
						} else {
							attackReady[2] = true;
						}
					}
					if (c.getIdentifier().getName().equals("2")) {
						if (c.getPollData() == 1.0f) {
							if (attackReady[0]) {
								controllerAdministrator.tabJoueurs.get(this.indexPlayer).setAtk(0, true);
								attackReady[0] = false;
							}
						} else {
							attackReady[0] = true;
						}
					}
					if (c.getIdentifier().getName().equals("3")) {
						if (c.getPollData() == 1.0f) {
							if (attackReady[1]) {
								controllerAdministrator.tabJoueurs.get(this.indexPlayer).initCombo();
								controllerAdministrator.tabJoueurs.get(this.indexPlayer).setAtk(1, true);
								attackReady[1] = false;
							}
						} else {
							attackReady[1] = true;
						}
					}
					// ATTENTION : 8 avec les manettes filaires !
					if (c.getIdentifier().getName().equals("7")) {
						if (c.getPollData() == 1.0f) {
							if (!hasPaused) {
								if (Game.CURRENT_STATE == State.IN_GAME) {
									Game.CURRENT_STATE = State.PAUSED;
									((CardLayout) Menu.cards.getLayout()).show(
											Menu.cards, "gamepaused");
								} else {
									Game.CURRENT_STATE = State.IN_GAME;
									((CardLayout) Menu.cards.getLayout()).show(
											Menu.cards, "play");
								}
								hasPaused = true;
								lastLoopPause = GameEngine.engineLoop;
							}
						} else {

						}

					}
				}else if (this.controller.getName().matches("(?i).*stick.*")) {
					if (c.getIdentifier().getName().equals("pov")) {
						
						if (c.getPollData() == 1) {
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setLeft(true);
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setTurned(false);
						} else if (c.getPollData() == 0.75) {

						} else if (c.getPollData() == 0.5) {
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setRight(true);
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setTurned(true);
						} else if (c.getPollData() == 0.25) {

						}else {
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setRight(false);
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).playerInfoBoolean.setLeft(false);
						}
					}
					
					if (c.getIdentifier().getName().equals("0")) {
						if (c.getPollData() == 1.0f) {
							if (!hasJumped) {
								controllerAdministrator.tabJoueurs.get(this.indexPlayer).setJump(true);
								hasJumped = true;
								lastLoopJump = GameEngine.engineLoop;
							}
						} else {
							controllerAdministrator.tabJoueurs.get(this.indexPlayer).setJump(false);
						}
					}
					
					if (c.getIdentifier().getName().equals("1")) {
						if (c.getPollData() == 1.0f) {
							if (!attackReady[2]) {
								controllerAdministrator.tabJoueurs.get(this.indexPlayer).initCombo();
								controllerAdministrator.tabJoueurs.get(this.indexPlayer)
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
								controllerAdministrator.tabJoueurs.get(this.indexPlayer)
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
								controllerAdministrator.tabJoueurs.get(this.indexPlayer).initCombo();
								controllerAdministrator.tabJoueurs.get(this.indexPlayer)
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
								if (Game.CURRENT_STATE == State.IN_GAME) {
									Game.CURRENT_STATE = State.PAUSED;
									((CardLayout) Menu.cards.getLayout()).show(
											Menu.cards, "gamepaused");
								} else {
									Game.CURRENT_STATE = State.IN_GAME;
									((CardLayout) Menu.cards.getLayout()).show(
											Menu.cards, "play");
								}
								hasPaused = true;
								lastLoopPause = GameEngine.engineLoop;
							}
						} else {

						}

					}
				}
				
				
			}

		}
	}
}
