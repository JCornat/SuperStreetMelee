package Levels;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class Levels {

	public ArrayList<Decor> tabDecor;
	public ArrayList<Decor> levels = new ArrayList<Decor>();
	public static Image image;
	
	/**
	 * Creation des differents niveaux
	 */
	public Levels(int level) {
		
		if(level == 0) {
			tabDecor = new ArrayList<Decor>();
			//LEVEL 1
			tabDecor.clear();
			for(int i=2;i<18;i++) {
				tabDecor.add(new Ground(i*50,500,50,50));
			}
			tabDecor.add(new PlatForm(100,300,200,15));
			tabDecor.add(new PlatForm(700,300,200,15));
			levels = tabDecor;
			
		} else if (level == 1) {
			tabDecor = new ArrayList<Decor>();
			tabDecor.clear();
			for(int i=2;i<7;i++) {
				tabDecor.add(new Ground(i*50,500,50,50));
			}
			for(int i=9;i<11;i++) {
				tabDecor.add(new Ground(i*50,300,50,50));
			}
			for(int i=13;i<18;i++) {
				tabDecor.add(new Ground(i*50,500,50,50));
			}
			for(int i=5;i<8;i++) {
				tabDecor.add(new Ground(50,i*50,50,50));
			}
			for(int i=5;i<8;i++) {
				tabDecor.add(new Ground(900,i*50,50,50));
			}
			levels = tabDecor;
		}

		ImageIcon ii = new ImageIcon("images/level/level.jpg");
		Image img = ii.getImage();
		image = img.getScaledInstance(1000, -1, Image.SCALE_FAST);
	}
}
