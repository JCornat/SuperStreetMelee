import java.util.ArrayList;


public class Collision {

	public Collision() {
		
	}
	

	// Calcul optimise d'une collision avec un decor
	/**
	 * Methode utilisee pour detecter une collision avec un decor
	 * @param x1 : Abscisse du coin inferieur gauche du joueur
	 * @param y1 : Ordonnee du coin inferieur gauche du joueur
	 * @param w1 : Largeur du joueur
	 * @param h1 : Hauteur du joueur
	 * @param x2 : Abscisse du coin inferieur gauche du decor
	 * @param y2 : Ordonnee du coin inferieur gauche du decor
	 * @param w2 : Largeur du decor
	 * @param h2 : Hauteur du decor
	 * @return vrai si une collision avec un decor est detectee
	 */
	public boolean collision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {		
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
	public int collisionCalculation(int x, int y, int w, int h, ArrayList<Decor> tabDecor) {
		for (int i = 0; i<tabDecor.size(); i++) {
			if (collision(x, y, w, h, tabDecor.get(i).x, tabDecor.get(i).y, tabDecor.get(i).w, tabDecor.get(i).h)) {
				return i;
			}
		}
		return -1;
	}
}
