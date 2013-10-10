import java.awt.Dimension;
import java.awt.TextArea;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class VueStats extends JPanel implements Observer {

	Joueur j;
	TextArea jtf;
	
	public VueStats(Joueur p) {
		j = p;
		jtf = new TextArea();
		jtf.setEditable(false);
		jtf.setPreferredSize(new Dimension(150,690));
		j.addObserver(this);
		this.add(jtf);
		jtf.setText("X : " + j.getX()
				+"\nY : "+j.getY()
				+"\nW : "+j.getW()
				+"\nH : "+j.getH()
				+"\nLeft : "+j.getLeft()
				+"\nRight : "+j.getRight()
				+"\nJump : "+j.getJump()
				+"\nCrouch : "+j.getCrouch()
				+"\nVitesse : "+j.vitesse
			);
	}
	
	public void update(Observable o, Object arg) {
		
		jtf.setText("X : " + j.getX()
				+"\nY : "+j.getY()
				+"\nW : "+j.getW()
				+"\nH : "+j.getH()
				+"\nLeft : "+j.getLeft()
				+"\nRight : "+j.getRight()
				+"\nJump : "+j.getJump()
				+"\nCrouch : "+j.getCrouch()
				+"\nVitesse : "+j.vitesse
			);
		
		
	}
	
}
