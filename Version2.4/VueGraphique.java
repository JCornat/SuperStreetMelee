import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;


public class VueGraphique extends JPanel {

	ArrayList<Joueur> tabJoueurs;
	ArrayList<Decor> tabDecors;
	ArrayList<Attaque> tabAttaques;
	
	public VueGraphique(Joueur p, ArrayList<Decor> de, ArrayList<Attaque> at, ArrayList<Joueur> jo) {
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
			g.drawLine(j.getX(), 0, j.getX(), 900);
			g.drawLine(j.getX()+j.getW()-1, 0, j.getX()+j.getW()-1, 900);
		}
		//Affichage du sol
		g.setColor(Color.gray);
		for (int i = 0; i< tabDecors.size();i++) {
			g.fillRect(tabDecors.get(i).getX(), tabDecors.get(i).getY(), tabDecors.get(i).getW(), tabDecors.get(i).getH());
		}
		
		
	}
}
