import java.awt.Rectangle;
import java.util.Observable;


public class Joueur extends Observable {

	int x,y,w,h;
	public Joueur(int i, int j, int k, int l) {

		x = i;
		y = j;
		w = k;
		h = l;
		
		
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int i) {
		x = i;
		setChanged();
		notifyObservers();
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int i) {
		y = i;
		setChanged();
		notifyObservers();
	}
	
	public int getW() {
		return w;
	}
	
	public void setW(int i) {
		w = i;
		setChanged();
		notifyObservers();
	}
	
	public int getH() {
		return h;
	}
	
	public void setH(int i) {
		h = i;
		setChanged();
		notifyObservers();
	}

	public void left() {
		int x = this.getX() - 1 ;
		
		this.setX(x);
	}
	
	public void right() {
		int x = this.getX() + 1 ;
		
		this.setX(x);
	}
	

	
}
