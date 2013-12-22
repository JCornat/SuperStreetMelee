import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class VueGraphique extends JPanel {

	ArrayList<Joueur> tabJoueurs;
	ArrayList<Decor> tabDecors;
	ArrayList<Attaque> tabAttaques;
	
	public VueGraphique(ArrayList<Decor> de, ArrayList<Attaque> at, ArrayList<Joueur> jo) {
		this.setPreferredSize(new Dimension(1000,700));
		tabDecors = de;
		tabAttaques = at;
		tabJoueurs = jo;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	
		for (Joueur j : tabJoueurs) {
			//Affichage du joueur
			g.setColor(Color.DARK_GRAY);
			g.fillRect(j.getX(), j.getY(), j.getW(), j.getH());
			g.drawString(j.getName(), (j.getX() + (j.getW()/2) - 25), j.getY() - 25);
			g.drawString(String.valueOf(j.health), (j.getX() + (j.getW()/2) - 10), j.getY() - 10);
			
			
			
			Attaque att = j.getAttaque();
			// Affichage de l'attaque
			if (att != null)
			{
				g.setColor(Color.RED);
				int tabXYWH[] = att.getAttackPosition(j);
				g.fillRect(tabXYWH[0], tabXYWH[1], tabXYWH[2], tabXYWH[3]);
				
			} 
			
			if (j.state == 1) {
				g.setColor(Color.RED);
				g.drawString("ATTACKING", (j.getX() + (j.getW()/2) - 25), j.getY() - 38);
			} else if (j.state == 2) {
				g.setColor(Color.BLUE);
				g.drawString("COOLDOWN", (j.getX() + (j.getW()/2) - 25), j.getY() - 38);
			} else if (j.state == 3) {
				g.setColor(Color.GREEN);
				g.drawString("CASTING", (j.getX() + (j.getW()/2) - 25), j.getY() - 38);
			}
			
			//Vecteur vitesse Y
			g.setColor(Color.BLUE);
			g.drawLine(j.getX()+j.getW()/2, j.getY()+j.getH()/2, j.getX()+j.getW()/2,j.getY()+j.getH()/2+j.vitesseY*2);
			
			//Vecteur vitesse X
			g.setColor(Color.ORANGE);
			g.drawLine(j.getX()+j.getW()/2, j.getY()+j.getH()/2, j.getX()+j.getW()/2+j.vitesseX*2,j.getY()+j.getH()/2);
			
			//Vecteur vitesse X
			g.setColor(Color.CYAN);
			g.drawLine(j.getX()+j.getW()/2, j.getY()+j.getH()/2, j.getX()+j.getW()/2+j.vitesseX*2,j.getY()+j.getH()/2+j.vitesseY*2);
			
			//Trace des traits autour du joueur, pour le debugging
			g.setColor(Color.RED);
			g.drawLine(0, j.getY(), 1000, j.getY());
			g.drawLine(0, j.getY()+j.getH()-1, 1000, j.getY()+j.getH()-1);
			g.drawLine(j.getX(), 0, j.getX(), 700);
			g.drawLine(j.getX()+j.getW()-1, 0, j.getX()+j.getW()-1, 700);
		}
		
		//Affichage du sol
		g.setColor(Color.gray);
		for (int i = 0; i< tabDecors.size();i++) {
			g.fillRect(tabDecors.get(i).getX(), tabDecors.get(i).getY(), tabDecors.get(i).getW(), tabDecors.get(i).getH());
		}
		
//		for (int i = 0; i < tabJoueurs.size(); i++) {
////			g.drawString(String.valueOf(tabJoueurs.get(i).tabAttaques.get(0).effectiveCooldown), 30, (i+1)*15+20);
////			g.drawString(String.valueOf(tabJoueurs.get(i).tabAttaques.get(1).effectiveCooldown), 30, (i+1)*15+50);
//			g.drawString(String.valueOf(tabJoueurs.get(i).atk.get(0)), 10, (i+1)*15+20);
//			g.drawString(String.valueOf(tabJoueurs.get(i).atk.get(1)), 10, (i+1)*15+50);
//		}
		
		
		try {
			g.drawString(Integer.toString(main.averageFrames)+" FPS", 10, 10);
		} catch (IndexOutOfBoundsException e) {}
		
	}

}
