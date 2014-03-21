package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import Levels.Decor;
import Levels.Ground;
import Levels.Levels;
import Levels.PlatForm;
import Model.Attack;
import Model.CombatMgr;
import Model.Constant;
import Model.Game;
import Model.GameEngine;
import Model.Player;

@SuppressWarnings("serial")
public class GraphicalView extends JPanel {

	ArrayList<Player> arrayPlayers;
	ArrayList<Decor> arrayOfDecorsForTheLevel;
	int positionXOnJumping = 0;
	int positionYOnJumping = 0;
	int i;
	public static boolean init ;
	
	public GraphicalView() {
		this.setPreferredSize(new Dimension(1000,700));
		this.init = false ;
		arrayPlayers = new ArrayList<Player>();
		arrayPlayers.clear();
		arrayOfDecorsForTheLevel = new ArrayList<Decor>();
		arrayOfDecorsForTheLevel.clear();
	}
	
	
	public void initGView(ArrayList<Decor> de, ArrayList<Player> jo){
		this.init = true ;
		arrayOfDecorsForTheLevel = de;
		arrayPlayers = jo;
	}
	
	/**
	 * Methode d'affichage du contenu sur la frame
	 */
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		//Affichage du sprite du niveau
		
		if (this.init) {
			graphics.drawImage(Levels.image, 0, 0, this);
			Font f = new Font("Arial", Font.PLAIN, 30); // par exemple 
			this.setFont(f);
			i = 1;
			for (Player player : arrayPlayers) {

				//Affichage du nom et des points de degats du joueur
				graphics.setColor(Color.WHITE);
				graphics.drawString(player.playerInfo.name, this.getWidth() / 5
						* i + 10, this.getHeight() - 70);
				graphics.drawString(
						String.valueOf(player.playerInfo.health) + '%',
						this.getWidth() / 5 * i, this.getHeight() - 30);
				graphics.drawString(
						String.valueOf(player.playerInfo.numberOfLife) + "<3",
						this.getWidth() / 5 * i + 80, this.getHeight() - 30);

				//Recuperation de l'attaque en cours
				Attack currentAttack = player.playerCombatMgr.currentAttack;

				//Lancement de l'animation si une attaque est en cours
				if (currentAttack != null) {
					if (Constant.DEBUGGING) {

					}

					//Si petite attaque, alors charger l'animation petite attaque
					if (currentAttack.name == "Small") {
						if (!player.graphicalPlayer.characterAnimationBigAttack.running) {
							player.graphicalPlayer.characterAnimationBigAttack.running = true;
						}
						//Sinon lancer la grosse
					} else {
						if (!player.graphicalPlayer.characterAnimationBigAttack.running) {
							player.graphicalPlayer.characterAnimationBigAttack.running = true;
						}
					}

					//S'il n'attaque pas et que l'annimation du corps est arr�t�e, on la relance
				} else {
					if (!player.graphicalPlayer.characterAnimationBody.running) {
						player.graphicalPlayer.characterAnimationBody.resume();
					}
				}

				//Si la grosse attaque est en train d'�tre anim�e, on arr�te l'animation du corps du personnage
				//Et on update celle de l'attaque, puis on l'affiche
				if (player.graphicalPlayer.characterAnimationBigAttack.running) {
					if (player.playerInfo.atkState != Constant.ATK_STATE_IN_COOLDOWN) {
						player.graphicalPlayer.characterAnimationBody.stop();
						player.graphicalPlayer.characterAnimationBigAttack
								.update();
						if (player.playerInfoBoolean.isTurningRight) {
							graphics.drawImage(
									player.graphicalPlayer.characterAnimationBigAttack.sprite,
									player.playerPosition.x,
									player.playerPosition.y, 113, 100, this);
						} else {
							graphics.drawImage(
									player.graphicalPlayer.characterAnimationBigAttack.sprite,
									player.playerPosition.x + 80,
									player.playerPosition.y, -113, 100, this);
						}
					} else {
						player.graphicalPlayer.characterAnimationBody.resume();
						if (currentAttack.name == "Small") {
							player.graphicalPlayer.characterAnimationBigAttack
									.stop();
							//Sinon lancer la grosse
						} else {
							player.graphicalPlayer.characterAnimationBigAttack
									.stop();
						}
					}

				}

				//On anime le corps du joueur 
				player.graphicalPlayer.characterAnimationBody.update();
				if (player.playerInfoBoolean.isTurningRight) {
					graphics.drawImage(
							player.graphicalPlayer.characterAnimationBody.sprite,
							player.playerPosition.x, player.playerPosition.y,
							85, 80, this);
				} else {
					graphics.drawImage(
							player.graphicalPlayer.characterAnimationBody.sprite,
							player.playerPosition.x + 80,
							player.playerPosition.y, -85, 80, null);
				}

				//Affichage de l'etat des coups

				player.graphicalPlayer.characterAnimationJump.update();
				if (player.playerInfoBoolean.isJumping) {
					if (!player.playerInfoBoolean.jump) {
						player.playerInfo.positionXOnJumping = player.playerPosition.x - 35;
						player.playerInfo.positionYOnJumping = player.playerPosition.y + 35;
						player.graphicalPlayer.characterAnimationJump.start();
						player.playerInfoBoolean.jump = true;
					}
				} else if (!player.playerInfoBoolean.isJumping) {
					player.playerInfoBoolean.jump = false;
				}

				if (player.playerInfoBoolean.isTurningRight) {
					graphics.drawImage(
							player.graphicalPlayer.characterAnimationJump.sprite,
							player.playerInfo.positionXOnJumping,
							player.playerInfo.positionYOnJumping, 150, 50, this);
				} else {
					graphics.drawImage(
							player.graphicalPlayer.characterAnimationJump.sprite,
							player.playerInfo.positionXOnJumping + 150,
							player.playerInfo.positionYOnJumping, -150, 50,
							this);
				}

				if (Constant.DEBUGGING) {
					graphics.setColor(Color.BLUE);
					graphics.drawLine(
							(player.playerPosition.x + player.playerPosition.w / 2),
							(player.playerPosition.y + player.playerPosition.h / 2),
							(player.playerPosition.x + player.playerPosition.w / 2),
							(player.playerPosition.y + player.playerPosition.h
									/ 2 + player.playerSpeed.speedOnVerticalAxis * 2));

					//Vecteur vitesse X
					graphics.setColor(Color.ORANGE);
					graphics.drawLine(
							(player.playerPosition.x + player.playerPosition.w / 2),
							(player.playerPosition.y + player.playerPosition.h / 2),
							(player.playerPosition.x + player.playerPosition.w
									/ 2 + player.playerSpeed.speedOnHorizontalAxis * 2),
							(player.playerPosition.y + player.playerPosition.h / 2));

					//Vecteur vitesse X
					graphics.setColor(Color.CYAN);
					graphics.drawLine(
							(player.playerPosition.x + player.playerPosition.w / 2),
							(player.playerPosition.y + player.playerPosition.h / 2),
							(player.playerPosition.x + player.playerPosition.w
									/ 2 + player.playerSpeed.speedOnHorizontalAxis * 2),
							(player.playerPosition.y + player.playerPosition.h
									/ 2 + player.playerSpeed.speedOnVerticalAxis * 2));

					if (player.playerInfo.atkState == Constant.ATK_STATE_ATTACKING) {
						graphics.setColor(Color.RED);
						graphics.drawString("ATTACKING",
								(player.playerPosition.x
										+ (player.playerPosition.w / 2) - 25),
								(player.playerPosition.y - 38));
					} else if (player.playerInfo.atkState == Constant.ATK_STATE_IN_COOLDOWN) {
						graphics.setColor(Color.BLUE);
						graphics.drawString("COOLDOWN",
								(player.playerPosition.x
										+ (player.playerPosition.w / 2) - 25),
								(player.playerPosition.y - 38));
					} else if (player.playerInfo.atkState == Constant.ATK_STATE_CASTING) {
						graphics.setColor(Color.GREEN);
						graphics.drawString("CASTING", (player.playerPosition.x
								+ (player.playerPosition.w / 2) - 25),
								(player.playerPosition.y - 38));
					}

					//Trace des traits autour du joueur
					graphics.setColor(Color.RED);
					graphics.drawLine(0, player.playerPosition.y, 1000,
							player.playerPosition.y);
					graphics.drawLine(
							0,
							(player.playerPosition.y + player.playerPosition.h - 1),
							1000,
							(player.playerPosition.y + player.playerPosition.h - 1));
					graphics.drawLine(player.playerPosition.x, 0,
							player.playerPosition.x, 700);
					graphics.drawLine(
							(player.playerPosition.x + player.playerPosition.w - 1),
							0,
							(player.playerPosition.x + player.playerPosition.w - 1),
							700);
					graphics.drawRect(player.playerPosition.x,
							player.playerPosition.y, 10, 10);
				}

				i++;
			}
			//Affichage du sol en fonction de son type
			for (int i = 0; i < arrayOfDecorsForTheLevel.size(); i++) {
				if (arrayOfDecorsForTheLevel.get(i).getClass() == PlatForm.class) {
					graphics.drawImage(PlatForm.image, arrayOfDecorsForTheLevel
							.get(i).getX(), arrayOfDecorsForTheLevel.get(i)
							.getY(), this);
				} else {
					graphics.drawImage(Ground.image, arrayOfDecorsForTheLevel
							.get(i).getX(), arrayOfDecorsForTheLevel.get(i)
							.getY(), this);
					//DEBUGGING
					if (Constant.DEBUGGING) {
						graphics.drawLine(arrayOfDecorsForTheLevel.get(i)
								.getX(),
								arrayOfDecorsForTheLevel.get(i).getY(),
								arrayOfDecorsForTheLevel.get(i).getX() + 50,
								arrayOfDecorsForTheLevel.get(i).getY() + 50);
					}
				}
			}
			// Affichage du timer
			String zero1 = "";
			String zero2 = "";
			if (Game.gameDuration / 60 < 10)
				zero1 = "0";
			if (Game.gameDuration % 60 < 10)
				zero2 = "0";
			graphics.drawString(zero1 + Game.gameDuration / 60 + " : " + zero2
					+ Game.gameDuration % 60, getWidth() / 2 - 30,
					getHeight() / 6 + 30);
			//DEBUGGING
			if (Constant.DEBUGGING) {
				//Affichage de la position des personnages
				for (int i = 0; i < arrayPlayers.size(); i++) {
					graphics.drawString(
							arrayPlayers.get(i).playerInfoBoolean.isJumping
									+ " ", 10, (i + 1) * 15 + 20);
					graphics.drawString(
							"Joueur "
									+ (i + 1)
									+ " : "
									+ String.valueOf(arrayPlayers.get(i).playerPosition.x)
									+ " "
									+ String.valueOf(arrayPlayers.get(i).playerPosition.y)
									+ " "
									+ String.valueOf(arrayPlayers.get(i).playerSpeed.speedOnHorizontalAxis),
							10, (i + 1) * 15 + 20);
				}
				try {

					graphics.drawString(
							Integer.toString(GameEngine.averageFrames) + " FPS"
									+ " -- GameEngineLoop "
									+ GameEngine.engineLoop, 10, 40);
					Player player = arrayPlayers.get(0);
					if (player != null) {
						CombatMgr playerCombatMgr = player.playerCombatMgr;
						if (playerCombatMgr != null
								&& !playerCombatMgr.listLastAttacksForCombo
										.isEmpty()) {
							ArrayList<Attack> tabLastAttacksForCombo = playerCombatMgr.listLastAttacksForCombo;
							graphics.drawString(
									"Last Attack 1st player = "
											+ tabLastAttacksForCombo
													.get(tabLastAttacksForCombo
															.size() - 1).name,
									22, 80);
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}
	}

}