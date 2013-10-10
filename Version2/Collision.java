
public class Collision {

	public Collision() {
		
	}
	

	//Calcul optimis� d'une collision, on ne touche pas � �a, c'est t�s bien comme �a :)
	//Ca retourne vrai si ya une collision d�tect�e
	public boolean Collision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
		if ( (x1 >= x2 + w2) || (x1 + w1 <= x2) 
			|| (y1 >= y2 + h2) || (y1 + h1 <= y2)) {
			return false;
		}

		return true;
	}
}
