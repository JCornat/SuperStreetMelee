import java.util.ArrayList;


public class Levels {

	ArrayList<Decor> tabDecor;
	
	ArrayList<ArrayList<Decor>> levels = new ArrayList<ArrayList<Decor>>();
	
	public Levels() {

		//Creation du terrain
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
		
		tabDecor.clear();
		tabDecor.add(new Decor(100,500,800,10));
		tabDecor.add(new Decor(100,300,100,10));
		tabDecor.add(new Decor(800,300,100,10));
		//tabDecor.add(new Decor(400,300,10,100));
//		tabDecor.add(new Decor(600,500,200,10));
//		tabDecor.add(new Decor(200,300,400,10));
//		tabDecor.add(new Decor(520,440,10,60));
//		tabDecor.add(new Decor(820,380,60,40));
//		tabDecor.add(new Decor(800,420,60,20));
//		tabDecor.add(new Decor(939,380,60,40));
//		tabDecor.add(new Decor(660,340,80,20));
//		tabDecor.add(new Decor(0,690,1000,10));
//		tabDecor.add(new Decor(0,0,10,700));
//		tabDecor.add(new Decor(990,0,10,700));
		
		levels.add(tabDecor);
		
	
	}
	
	
}
