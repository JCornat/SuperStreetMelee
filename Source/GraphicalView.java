import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphicalView extends JPanel {

	ArrayList<Player> arrayPlayers;
	ArrayList<Decor> arrayOfDecorsForTheLevel;
	ArrayList<Attack> arrayOfAttacksAvailableForTheCharacter;
	int spriteAnim = 0;
	
	public GraphicalView(ArrayList<Decor> de, ArrayList<Attack> at, ArrayList<Player> jo) {
		this.setPreferredSize(new Dimension(1000,700));
		arrayOfDecorsForTheLevel = de;
		arrayOfAttacksAvailableForTheCharacter = at;
		arrayPlayers = jo;
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
			
			//Recuperation de l'attaque en cours
			Attack currentAttack = player.getAttaque();
			
			//Affichage de l'attaque s'il y a
			if (currentAttack != null)
			{	
				//Si le joueur est tourné à droite
				if(player.isTurningRight) {
					//En fonction de l'attaque actuelle
					if(currentAttack.name == "Small") {
						//Nous affichons un bras qui bouge par rapport à sa position normale
						graphics.drawImage(player.imageArm, player.getX()+65, player.getY()+35, this);
					} else {
						graphics.drawImage(player.imageArm, player.getX()+90, player.getY()+35, this);
					}
				} else {
					//Sinon s'il est tourné à gauche...
					if(currentAttack.name == "Small") {
						graphics.drawImage(player.imageArm, player.getX()-10, player.getY()+35, this);
					} else {
						graphics.drawImage(player.imageArm, player.getX()-35, player.getY()+35, this);
					}
				}

				//DEBUGGING
				//Utilise pour savoir la portee des coups, decommenter si on veut aligner le sprite sur l'attaque
				/*g.setColor(Color.RED);
				int tabXYWH[] = att.getAttackPosition(j);
				g.fillRect(tabXYWH[0], tabXYWH[1], tabXYWH[2], tabXYWH[3]);*/
				
				//S'il n'attaque pas :
			} else {
				//On affiche le bras dans sa position de base
				if(player.isTurningRight) {
					graphics.drawImage(player.imageArm, player.getX()+60, player.getY()+35, this);
				} else {
					graphics.drawImage(player.imageArm, player.getX()-5, player.getY()+35, this);
				}
			}
			
			//On affiche le corps et l'autre bras, en fonction de son orientation toujours.
			if(player.isTurningRight) {
				graphics.drawImage(player.imageBody, player.getX(), player.getY(), this);
				graphics.drawImage(player.imageArm, player.getX()+23, player.getY()+35, this);
			} else {
				graphics.drawImage(player.imageBody, player.getX()+80, player.getY(), -80, 80, null);
				graphics.drawImage(player.imageArm, player.getX()+35, player.getY()+35, this);
			}
			
			
			//Affichage de l'etat des coups
			if (player.atkState == 1) {
				graphics.setColor(Color.RED);
				graphics.drawString("ATTACKING", (player.getX() + (player.getW()/2) - 25), (player.getY() - 38));
			} else if (player.atkState == 2) {
				graphics.setColor(Color.BLUE);
				graphics.drawString("COOLDOWN", (player.getX() + (player.getW()/2) - 25), (player.getY() - 38));
			} else if (player.atkState == 3) {
				graphics.setColor(Color.GREEN);
				graphics.drawString("CASTING", (player.getX() + (player.getW()/2) - 25), (player.getY() - 38));
			}
			

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
			/*g.setColor(Color.RED);
			g.drawLine(0, j.getY(), 1000, j.getY());
			g.drawLine(0, (j.getY()+j.getH()-1), 1000, (j.getY()+j.getH()-1));
			g.drawLine(j.getX(), 0, j.getX(), 700);
			g.drawLine((j.getX()+j.getW()-1), 0, (j.getX()+j.getW()-1), 700);
			g.drawRect(j.getX(), j.getY(), 10, 10);*/
		}
		

		//Affichage du sol en fonction de son type
		for (int i = 0; i< arrayOfDecorsForTheLevel.size();i++) {
			if(arrayOfDecorsForTheLevel.get(i).getClass()==PlatForm.class) {
				graphics.drawImage(PlatForm.image, arrayOfDecorsForTheLevel.get(i).getX(), arrayOfDecorsForTheLevel.get(i).getY(), this);
			} else {
				graphics.drawImage(Ground.image, arrayOfDecorsForTheLevel.get(i).getX(), arrayOfDecorsForTheLevel.get(i).getY(), this);	
			}
		}

		//DEBUGGING
		//Affichage de la position des personnages
		/*for (int i = 0; i < arrayPlayers.size(); i++) {
			graphics.drawString("Joueur "+(i+1)+" : "+String.valueOf(arrayPlayers.get(i).x)+" "+String.valueOf(arrayPlayers.get(i).y)+" "+String.valueOf(arrayPlayers.get(i).vitesseX), 10, (i+1)*15+20);
		}*/
		
		
		// Affichage du timer
		String zero1 = "";
		String zero2 = "";
		if (GameEngine.gameDuration/60 < 10)
			zero1 = "0";
		if (GameEngine.gameDuration%60 < 10)
			zero2 = "0";
		graphics.drawString(zero1+GameEngine.gameDuration/60+" : "+zero2+GameEngine.gameDuration%60, getWidth()/2, getHeight()/6);
		try {
			graphics.drawString(Integer.toString(main.averageFrames)+" FPS", 10, 10);
		} catch (IndexOutOfBoundsException e) {}
		
	}

}
