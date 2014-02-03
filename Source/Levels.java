import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class Levels {

	ArrayList<Decor> tabDecor;
	
	ArrayList<ArrayList<Decor>> levels = new ArrayList<ArrayList<Decor>>();

	static Image image;
	
	/**
	 * Creation des differents niveaux
	 */
	public Levels() {

		//LEVEL 0
		tabDecor = new ArrayList<Decor>();
		tabDecor.clear();
		tabDecor.add(new Decor(30,500,500,10));
		tabDecor.add(new Decor(600,500,200,10));
		tabDecor.add(new Decor(200,300,400,10));
		tabDecor.add(new Decor(520,440,10,60));
		tabDecor.add(new Decor(820,380,60,40));
		tabDecor.add(new Decor(800,420,60,20));
		tabDecor.add(new Decor(939,380,60,40));
		tabDecor.add(new Decor(660,340,80,20));
		tabDecor.add(new Decor(0,690,1000,10));
		tabDecor.add(new Decor(0,0,10,700));
		tabDecor.add(new Decor(990,0,10,700));
		levels.add(tabDecor);
		
		
		//LEVEL 1
		tabDecor.clear();
		for(int i=2;i<18;i++) {
			tabDecor.add(new Ground(i*50,500,50,50));
		}
		for(int i=2;i<18;i++) {
			tabDecor.add(new Ground(i*50,547,50,50));
		}
		tabDecor.add(new PlatForm(100,300,200,15));
		tabDecor.add(new PlatForm(700,300,200,15));
		ImageIcon ii = new ImageIcon("images/level/level.jpg");
		Image img = ii.getImage();
		image = img.getScaledInstance(1000, -1, Image.SCALE_FAST);
		levels.add(tabDecor);
	}
	
	
}
