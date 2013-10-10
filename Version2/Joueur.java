import java.awt.Rectangle;
import java.util.Observable;


public class Joueur extends Observable {

	int x,y,w,h,vitesse;
	boolean jump, left, right, crouch;
	
	public Joueur(int i, int j, int k, int l) {
		x = i;
		y = j;
		w = k;
		h = l;
		jump = false; 
		left = false;
		right = false;
		crouch = false;
		vitesse = 2;
	}

	public void notrun() {
		vitesse = 2;
	}
	
	public void run() {
		vitesse = 4;
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
	
	public void setVitesse(int i) {
		vitesse = i;
	}

	
	
	public boolean getJump() {
		return jump;
	}
	
	public boolean getLeft() {
		return left;
	}
	
	public boolean getRight() {
		return right;
	}
	
	public boolean getCrouch() {
		return crouch;
	}
	
	public void setJump(boolean b) {
		jump = b;
		setChanged();
		notifyObservers();
	}
	
	public void setLeft(boolean b) {
		left = b;
		setChanged();
		notifyObservers();
	}
	
	public void setRight(boolean b) {
		right = b;
		setChanged();
		notifyObservers();
	}
	
	public void setCrouch(boolean b) {
		crouch = b;
		setChanged();
		notifyObservers();
	}
	
	
}
