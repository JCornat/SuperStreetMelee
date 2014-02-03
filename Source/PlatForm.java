import java.awt.Image;

import javax.swing.ImageIcon;



public class PlatForm extends Decor {
	
	static Image image;
	
	/**
	 * Creation d'une plate-forme sous laquelle le joueur pourra passer au travers de bas en haut
	 * @param a : origine en X de la plate-forme
	 * @param b : origine en Y de la plate-forme
	 * @param c : largeur de la plate-forme
	 * @param d : hauteur de la plate-forme
	 */
	public PlatForm(int a, int b, int c, int d) {
		super(a, b, c, d);
		ImageIcon ii = new ImageIcon("images/level/platForm.png");
		Image img = ii.getImage();
		image = img.getScaledInstance(200, -1, Image.SCALE_FAST);
	}

}
