import java.awt.Image;

import javax.swing.ImageIcon;


public class Ground extends Decor {

	static Image image;
	
	public Ground(int a, int b, int c, int d) {
		super(a, b, c, d);
		ImageIcon ii = new ImageIcon("images/level/ground.png");
		Image img = ii.getImage();
		image = img.getScaledInstance(50, -1, Image.SCALE_FAST);
	}

}
