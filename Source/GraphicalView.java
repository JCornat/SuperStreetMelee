import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphicalView extends JPanel {

	ArrayList<Player> arrayPlayers;
	ArrayList<Decor> arrayOfDecorsForTheLevel;
	ArrayList<Attack> arrayOfAttacksAvailableForTheCharacter;
	int positionXOnJumping = 0;
	int positionYOnJumping = 0;
	
	public GraphicalView(ArrayList<Decor> de, ArrayList<Attack> at, ArrayList<Player> jo) {
		this.setPreferredSize(new Dimension(1000,700));
		arrayOfDecorsForTheLevel = de;
		arrayOfAttacksAvailableForTheCharacter = at;
		arrayPlayers = jo;
		
		for (int i = 0; i < arrayPlayers.size(); i++) {
			arrayPlayers.get(i).character = new Animator(arrayPlayers.get(i).sprites);
			arrayPlayers.get(i).character.setSpeed(150);
			arrayPlayers.get(i).character.start();
			
			
			arrayPlayers.get(i).characterJumping = new AnimatorFixedObject(arrayPlayers.get(i).spritesOfJump);
			arrayPlayers.get(i).characterJumping.setSpeed(30);
			
			arrayPlayers.get(i).characterAttack = new AnimatorFixedObject(arrayPlayers.get(i).spritesOfAttack);
			arrayPlayers.get(i).characterAttack.setSpeed(30);
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
		
		
		for (Player player : arrayPlayers) {
			//Affichage du nom et des points de degats du joueur
			graphics.setColor(Color.DARK_GRAY);
			graphics.drawString(player.getName(), (player.getX() + (player.getW()/2) - 25), (player.getY() - 25));
			graphics.drawString(String.valueOf(player.health), (player.getX() + (player.getW()/2) - 10), (player.getY() - 10));
			graphics.drawString(player.getName() + " : " + player.numberOfLife, 250 + (this.arrayPlayers.indexOf(player))*350 , 50) ;
			
			//Recuperation de l'attaque en cours
			Attack currentAttack = player.getAttaque();
			
			//Affichage de l'attaque s'il y a
			if (currentAttack != null)
			{	
				//player.character.pause();
				//Si le joueur est tourné à droite
				if(player.isTurningRight) {
					//En fonction de l'attaque actuelle
					if(currentAttack.name == "Small") {
						//Nous affichons un bras qui bouge par rapport à sa position normale

						player.characterAttack.start();
					} else {
						graphics.drawImage(player.imageArm, player.getX()+90, player.getY()+35, this);
					}
				} else {
					//Sinon s'il est tourne a gauche...
					if(currentAttack.name == "Small") {
						graphics.drawImage(player.imageArm, player.getX()-10, player.getY()+35, this);
					} else {
						graphics.drawImage(player.imageArm, player.getX()-35, player.getY()+35, this);
					}
				}

			
						
				
				//DEBUGGING
				//Utilise pour savoir la portee des coups, decommenter si on veut aligner le sprite sur l'attaque
				/*graphics.setColor(Color.RED);
				int tabXYWH[] = currentAttack.getAttackPosition(player);
				graphics.fillRect(tabXYWH[0], tabXYWH[1], tabXYWH[2], tabXYWH[3]);*/
				
				//S'il n'attaque pas :
			} else {
				if(!player.character.running) {
					player.character.resume();
				}
				//On affiche le bras dans sa position de base
//				if(player.isTurningRight) {
//					graphics.drawImage(player.imageArm, player.getX()+60, player.getY()+35, this);
//				} else {
//					graphics.drawImage(player.imageArm, player.getX()-5, player.getY()+35, this);
//				}
			}
			
			if(player.characterAttack.running) {
				player.character.stop();
				player.characterAttack.update(System.currentTimeMillis());
				graphics.drawImage(player.characterAttack.sprite, player.getX(), player.getY(), 113,100, this);
				
			} else {
				//player.character.start();
			}
			
			
			player.character.update(System.currentTimeMillis());
			if(player.isTurningRight) {
				graphics.drawImage(player.character.sprite, player.getX(), player.getY(), 85,80, this);
			} else {
				graphics.drawImage(player.character.sprite, player.getX()+80, player.getY(), -85, 80, null);
			}


			
			//Affichage de l'etat des coups

//			if (player.atkState == 1) {
//				graphics.setColor(Color.RED);
//				graphics.drawString("ATTACKING", (player.getX() + (player.getW()/2) - 25), (player.getY() - 38));
//			} else if (player.atkState == 2) {
//				graphics.setColor(Color.BLUE);
//				graphics.drawString("COOLDOWN", (player.getX() + (player.getW()/2) - 25), (player.getY() - 38));
//			} else if (player.atkState == 3) {
//				graphics.setColor(Color.GREEN);
//				graphics.drawString("CASTING", (player.getX() + (player.getW()/2) - 25), (player.getY() - 38));
//			}
//			

			player.characterJumping.update(System.currentTimeMillis());
			if(player.isJumping) {
				if(!player.booleanJump) {
					player.positionXOnJumping = player.getX()-35;
					player.positionYOnJumping = player.getY()+35;
					player.characterJumping.start();
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
				graphics.drawImage(player.characterJumping.sprite, player.positionXOnJumping, player.positionYOnJumping, 150,50, this);
			} else {
				graphics.drawImage(player.characterJumping.sprite, player.positionXOnJumping+150, player.positionYOnJumping, -150,50, this);
			}
//			if(player.getJump() == true) {
//				player.characterJumping.resume();
//				graphics.drawImage(player.characterJumping.sprite, player.getX()-20, player.getY()+50, 120,50, this);
//			} else if(!player.isJumping) {
//				player.characterJumping.stop();
//			}
			

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
		if (GameEngine.gameDuration/60 < 10)
			zero1 = "0";
		if (GameEngine.gameDuration%60 < 10)
			zero2 = "0";
		graphics.drawString(zero1+GameEngine.gameDuration/60+" : "+zero2+GameEngine.gameDuration%60, getWidth()/2, getHeight()/6);
		try {

			graphics.drawString(Integer.toString(main.averageFrames)+" FPS"+" -- Gameengineloop "+main.engineLoop, 10, 10);
			if (arrayPlayers.get(0) != null && !arrayPlayers.get(0).tabLastAttacksForCombo.isEmpty()) {
				graphics.drawString("Last Attack 1st player = "+arrayPlayers.get(0).tabLastAttacksForCombo.get(arrayPlayers.get(0).tabLastAttacksForCombo.size()-1).name, 22, 22);
			}
		} catch (IndexOutOfBoundsException e) {}
		
	}

}
