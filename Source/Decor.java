import java.util.Observable;


public class Decor extends Observable {
	
	int x,y,w,h;
	
	/**
	 * Constructeur pour cr�er un d�cor
	 * @param a : origine en X du d�cor
	 * @param b : origine en Y du d�cor
	 * @param c : largeur du d�cor
	 * @param d : hauteur du d�cor
	 */
	public Decor(int a,int b,int c,int d) {
		x = a;
		y = b;
		w = c;
		h = d;
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}

}
