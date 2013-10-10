import java.util.ArrayList;


public class Collision {

	public Collision() {
		
	}
	

	//Calcul optimise d'une collision, on ne touche pas a ça, c'est tres bien comme ça je pense :)
	//Ca retourne vrai si ya une collision détectee
	public boolean Collision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
		if ( (x1 >= x2 + w2) || (x1 + w1 <= x2) 
			|| (y1 >= y2 + h2) || (y1 + h1 <= y2)) {
			return false;
		}

		return true;
	}


	public boolean Collision(int x, int y,int w,int h, ArrayList<Decor> tabDecor) {
		for (int i = 0; i<tabDecor.size(); i++) {
			if (Collision(x, y, w, h, tabDecor.get(i).x, tabDecor.get(i).y, tabDecor.get(i).w, tabDecor.get(i).h)) {
				return true;
			}
		}
		return false;
	}
}
