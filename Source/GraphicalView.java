import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphicalView extends JPanel {

	ArrayList<Player> arrayPlayers;
	ArrayList<Decor> arrayOfDecorsForTheLevel;
	ArrayList<Attack> arrayOfAttacksAvailableForTheCharacter;
	int positionXOnJumping = 0;
	int positionYOnJumping = 0;
	int i;
	public GraphicalView(ArrayList<Decor> de, ArrayList<Attack> at, ArrayList<Player> jo) {
		
		
		
		this.setPreferredSize(new Dimension(1000,700));
		arrayOfDecorsForTheLevel = de;
		arrayOfAttacksAvailableForTheCharacter = at;
		arrayPlayers = jo;
		
		for (int i = 0; i < arrayPlayers.size(); i++) {
			arrayPlayers.get(i).characterAnimationBody = new Animator(arrayPlayers.get(i).arrayOfSpritesOfTheBody);
			arrayPlayers.get(i).characterAnimationBody.setSpeed(150);
			arrayPlayers.get(i).characterAnimationBody.start();
			
			arrayPlayers.get(i).characterAnimationJump = new AnimatorOneLoop(arrayPlayers.get(i).arrayOfSpritesOfPThePlayerJump);
			arrayPlayers.get(i).characterAnimationJump.setSpeed(30);
			
			arrayPlayers.get(i).characterAnimationBigAttack = new AnimatorOneLoop(arrayPlayers.get(i).arrayOfSpritesOfTheBigPlayerAttack);
			arrayPlayers.get(i).characterAnimationBigAttack.setSpeed(30);
			//arrayPlayers.get(i).characterJumping.start();
		}
	}
	
	/**
	 * Methode d'affichage du contenu sur la frame
	 */
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		//Affichage du sprite du niveau
		
		graphics.drawImage(Levels.image, 0, 0, this);
		Font f = new Font("Arial", Font.PLAIN, 30); // par exemple 
		this.setFont(f); 
		i = 1;
		for (Player player : arrayPlayers) {
			
			//Affichage du nom et des points de degats du joueur
			graphics.setColor(Color.WHITE);
			graphics.drawString(player.getName(), this.getWidth()/5*i+10, this.getHeight()-70);
			graphics.drawString(String.valueOf(player.health)+'%',  this.getWidth()/5*i, this.getHeight()-30);
			graphics.drawString(String.valueOf(player.numberOfLife)+"<3",  this.getWidth()/5*i+80, this.getHeight()-30);
			
			//Recuperation de l'attaque en cours
			Attack currentAttack = player.getAttaque();
			
			//Lancement de l'animation si une attaque est en cours
			if (currentAttack != null)
			{	
				//Si petite attaque, alors charger l'animation petite attaque
				if(currentAttack.name == "Small") {
					if(!player.characterAnimationBigAttack.running) {
						player.characterAnimationBigAttack.running = true;
					}
				//Sinon lancer la grosse
				} else {
					if(!player.characterAnimationBigAttack.running) {
						player.characterAnimationBigAttack.running = true;
					}
				}

				

			//S'il n'attaque pas et que l'annimation du corps est arrêtée, on la relance
			} else {
				if(!player.characterAnimationBody.running) {
					player.characterAnimationBody.resume();
				}
			}
			
			//Si la grosse attaque est en train d'être animée, on arrête l'animation du corps du personnage
			//Et on update celle de l'attaque, puis on l'affiche
			if(player.characterAnimationBigAttack.running) {
				if(player.atkState != Constant.ATK_STATE_IN_COOLDOWN) {
					player.characterAnimationBody.stop();
					player.characterAnimationBigAttack.update();
					if(player.isTurningRight) {
						graphics.drawImage(player.characterAnimationBigAttack.sprite, player.playerPosition.getX(), player.playerPosition.getY(), 113,100, this);
					} else {
						graphics.drawImage(player.characterAnimationBigAttack.sprite, player.playerPosition.getX()+80, player.playerPosition.getY(), -113,100, this);
					}
				} else {
					player.characterAnimationBody.resume();
					if(currentAttack.name == "Small") {
						player.characterAnimationBigAttack.stop();
					//Sinon lancer la grosse
					} else {
						player.characterAnimationBigAttack.stop();
					}
				}
				
			}
			
			
			//On anime le corps du joueur 
			player.characterAnimationBody.update();
			if(player.isTurningRight) {
				graphics.drawImage(player.characterAnimationBody.sprite, player.playerPosition.getX(), player.playerPosition.getY(), 85,80, this);
			} else {
				graphics.drawImage(player.characterAnimationBody.sprite, player.playerPosition.getX()+80, player.playerPosition.getY(), -85, 80, null);
			}

			
			//Affichage de l'etat des coups

			if (player.atkState == Constant.ATK_STATE_ATTACKING) {
				graphics.setColor(Color.RED);
				graphics.drawString("ATTACKING", (player.playerPosition.getX() + (player.playerPosition.getW()/2) - 25), (player.playerPosition.getY() - 38));
			} else if (player.atkState == Constant.ATK_STATE_IN_COOLDOWN) {
				graphics.setColor(Color.BLUE);
				graphics.drawString("COOLDOWN", (player.playerPosition.getX() + (player.playerPosition.getW()/2) - 25), (player.playerPosition.getY() - 38));
			} else if (player.atkState == Constant.ATK_STATE_CASTING) {
				graphics.setColor(Color.GREEN);
				graphics.drawString("CASTING", (player.playerPosition.getX() + (player.playerPosition.getW()/2) - 25), (player.playerPosition.getY() - 38));
			}

			player.characterAnimationJump.update();
			if(player.isJumping) {
				if(!player.booleanJump) {
					//System.out.println(player.getX() + " "+ player.getY());
					player.positionXOnJumping = player.playerPosition.getX()-35;
					player.positionYOnJumping = player.playerPosition.getY()+35;
					player.characterAnimationJump.start();
					player.booleanJump = true;
				}
			} else if(!player.isJumping) {
				player.booleanJump = false;

		/*	if (player.atkState == Constant.ATK_STATE_ATTACKING) {
				graphics.setColor(Color.RED);
				graphics.drawString("ATTACKING", (player.getX() + (player.getW()/2) - 25), (player.getY() - 38));
			} else if (player.atkState == Constant.ATK_STATE_IN_COOLDOWN) {
				graphics.setColor(Color.BLUE);
				graphics.drawString("COOLDOWN", (player.getX() + (player.getW()/2) - 25), (player.getY() - 38));
			} else if (player.atkState == Constant.ATK_STATE_CASTING) {
				graphics.setColor(Color.GREEN);
				graphics.drawString("CASTING", (player.getX() + (player.getW()/2) - 25), (player.getY() - 38));*/

			}
			
			
			if(player.isTurningRight) {
				graphics.drawImage(player.characterAnimationJump.sprite, player.positionXOnJumping, player.positionYOnJumping, 150,50, this);
			} else {
				graphics.drawImage(player.characterAnimationJump.sprite, player.positionXOnJumping+150, player.positionYOnJumping, -150,50, this);
			}
//			if(player.getJump() == true) {
//				player.characterJumping.resume();
//				graphics.drawImage(player.characterJumping.sprite, player.getX()-20, player.getY()+50, 120,50, this);
//			} else if(!player.isJumping) {
//				player.characterJumping.stop();
//			}
			

			//#######################
			//#######DEBUGGING#######
			//Utilise pour savoir la portee des coups, decommenter si on veut aligner le sprite sur l'attaque
//			graphics.setColor(Color.RED);
//			int tabXYWH[] = currentAttack.getAttackPosition(player);
//			graphics.fillRect(tabXYWH[0], tabXYWH[1], tabXYWH[2], tabXYWH[3]);
			//#######################
			
			

			//DEBUGGING
			//Utilise pour afficher les vecteurs vitesses du personnage
			/*
			//Vecteur vitesse Y
			graphics.setColor(Color.BLUE);
			graphics.drawLine((player.getX()+player.getW()/2), (player.getY()+player.getH()/2), (player.getX()+player.getW()/2),(player.getY()+player.getH()/2+player.vitesseY*2));
			
			//Vecteur vitesse X
			graphics.setColor(Color.ORANGE);
			graphics.drawLine((player.getX()+player.getW()/2), (player.getY()+player.getH()/2), (player.getX()+player.getW()/2+player.vitesseX*2),(player.getY()+player.getH()/2));
			
			//Vecteur vitesse X
			graphics.setColor(Color.CYAN);
			graphics.drawLine((player.getX()+player.getW()/2), (player.getY()+player.getH()/2), (player.getX()+player.getW()/2+player.vitesseX*2), (player.getY()+player.getH()/2+player.vitesseY*2));
			*/
			
			//DEBUGGING
			//Trace des traits autour du joueur
//			graphics.setColor(Color.RED);
//			graphics.drawLine(0, player.getY(), 1000, player.getY());
//			graphics.drawLine(0, (player.getY()+player.getH()-1), 1000, (player.getY()+player.getH()-1));
//			graphics.drawLine(player.getX(), 0, player.getX(), 700);
//			graphics.drawLine((player.getX()+player.getW()-1), 0, (player.getX()+player.getW()-1), 700);
//			graphics.drawRect(player.getX(), player.getY(), 10, 10);
			i++;
		}
		

		//Affichage du sol en fonction de son type
		for (int i = 0; i< arrayOfDecorsForTheLevel.size();i++) {
			if(arrayOfDecorsForTheLevel.get(i).getClass()==PlatForm.class) {
				graphics.drawImage(PlatForm.image, arrayOfDecorsForTheLevel.get(i).getX(), arrayOfDecorsForTheLevel.get(i).getY(), this);
			} else {
				graphics.drawImage(Ground.image, arrayOfDecorsForTheLevel.get(i).getX(), arrayOfDecorsForTheLevel.get(i).getY(), this);	
				//DEBUGGING
				//graphics.drawLine(arrayOfDecorsForTheLevel.get(i).getX(), arrayOfDecorsForTheLevel.get(i).getY(), arrayOfDecorsForTheLevel.get(i).getX()+50, arrayOfDecorsForTheLevel.get(i).getY()+50);
			}
		}

		//DEBUGGING
		//Affichage de la position des personnages
		/*for (int i = 0; i < arrayPlayers.size(); i++) {
			graphics.drawString("Joueur "+(i+1)+" : "+String.valueOf(arrayPlayers.get(i).x)+" "+String.valueOf(arrayPlayers.get(i).y)+" "+String.valueOf(arrayPlayers.get(i).vitesseX), 10, (i+1)*15+20);
		}*/
		for (int i = 0; i < arrayPlayers.size(); i++) {
			graphics.drawString(arrayPlayers.get(i).isJumping+" ", 10, (i+1)*15+20);
		}
		
		
		// Affichage du timer
		String zero1 = "";
		String zero2 = "";
		if (Game.gameDuration/60 < 10)
			zero1 = "0";
		if (Game.gameDuration%60 < 10)
			zero2 = "0";
		
		graphics.drawString(zero1+Game.gameDuration/60+" : "+zero2+Game.gameDuration%60, getWidth()/2, getHeight()/6);
		try {

			graphics.drawString(Integer.toString(GameEngine.averageFrames)+" FPS"+" -- GameEngineLoop "+GameEngine.engineLoop, 10, 10);
			if (arrayPlayers.get(0) != null && !arrayPlayers.get(0).tabLastAttacksForCombo.isEmpty()) {
				graphics.drawString("Last Attack 1st player = "+arrayPlayers.get(0).tabLastAttacksForCombo.get(arrayPlayers.get(0).tabLastAttacksForCombo.size()-1).name, 22, 22);
			}
		} catch (IndexOutOfBoundsException e) {}
		
	}

}
