import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;


public class VueGraphique extends JPanel implements Observer {

	Joueur j;
	
	public VueGraphique(Joueur p) {
		j = p;
		this.setPreferredSize(new Dimension(1000,700));
		j.addObserver(this);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawLine(0, j.getY(), 1000, j.getY());
		g.drawLine(0, j.getY()+j.getH(), 1000, j.getY()+j.getH());
		g.drawLine(j.getX(), 0, j.getX(), 900);
		g.drawLine(j.getX()+j.getW(), 0, j.getX()+j.getW(), 900);
		g.fillRect(j.getX(), j.getY(), j.getW(), j.getH());
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
	
}
