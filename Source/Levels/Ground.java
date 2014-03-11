package Levels;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Ground extends Decor {

	public static Image image;
	
	/**
	 * Creation d'un sol
	 * @param a : origine en X du sol
	 * @param b : origine en Y du sol
	 * @param c : largeur du sol
	 * @param d : hauteur du sol
	 */
	public Ground(int a, int b, int c, int d) {
		super(a, b, c, d);
		ImageIcon ii = new ImageIcon("images/level/ground.png");
		Image img = ii.getImage();
		image = img.getScaledInstance(50, -1, Image.SCALE_FAST);
	}

}
