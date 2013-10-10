import java.util.ArrayList;


public class Collision {

	public Collision() {
		
	}
	

	//Calcul optimise d'une collision
	/**
	 * Retourne vrai si une collision est detectee
	 * @param x1 : Abscisse du coin inferieur gauche du rectangle A
	 * @param y1 : Ordonnee du coin inferieur gauche du rectangle A
	 * @param w1 : Largeur du rectangle A
	 * @param h1 : Hauteur du rectangle A
	 * @param x2 : Abscisse du coin inferieur gauche du rectangle B
	 * @param y2 : Ordonnee du coin inferieur gauche du rectangle B
	 * @param w2 : Largeur du rectangle B
	 * @param h2 : Hauteur du rectangle B
	 * @return
	 */
	public boolean Collision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
		if ( (x1 >= x2 + w2) || (x1 + w1 <= x2) 
			|| (y1 >= y2 + h2) || (y1 + h1 <= y2)) {
			return false;
		}
		return true;
	}

	/**
	 * Calcul de collision entre la position future du joueur et le decor
	 * @param x : Abscisse du coin inferieur gauche du Joueur
	 * @param y : Ordonnee du coin inferieur gauche du Joueur
	 * @param w : Largeur du Joueur
	 * @param h : Hauteur du Joueur
	 * @param tabDecor : ArrayList des differents elements du decor.
	 * @return
	 */
	public int Collision(int x, int y, int w, int h, ArrayList<Decor> tabDecor) {
		for (int i = 0; i<tabDecor.size(); i++) {
			if (Collision(x, y, w, h, tabDecor.get(i).x, tabDecor.get(i).y, tabDecor.get(i).w, tabDecor.get(i).h)) {
				return i;
			}
		}
		return -1;
	}
}
