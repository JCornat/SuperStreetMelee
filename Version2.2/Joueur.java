import java.awt.Rectangle;
import java.util.Observable;

public class Joueur extends Observable {

	int x, y, w, h, vitesseH, health;
	boolean jump, left, right, crouch, isJumping, canJump, isAlive;
	final int MAX_HEALTH = 100;

	public Joueur(int i, int j, int k, int l) {
		this.x = i;
		this.y = j;
		this.w = k;
		this.h = l;
		this.jump = false;
		this.left = false;
		this.right = false;
		this.crouch = false;
		this.vitesseH = 2;
		this.canJump = false;
		this.health = MAX_HEALTH;
		this.isAlive = true;
	}

	public void notrun() {
		this.vitesseH = 2;
	}

	public void run() {
		this.vitesseH = 4;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int i) {
		this.x = i;
		setChanged();
		notifyObservers();
	}

	public int getY() {
		return this.y;
	}

	public void setY(int i) {
		this.y = i;
		setChanged();
		notifyObservers();
	}

	public int getW() {
		return this.w;
	}

	public void setW(int i) {
		this.w = i;
		setChanged();
		notifyObservers();
	}

	public int getH() {
		return this.h;
	}

	public void setH(int i) {
		this.h = i;
		setChanged();
		notifyObservers();
	}

	public void setVitesseH(int i) {
		this.vitesseH = i;
	}

	public boolean getJump() {
		return this.jump;
	}

	public boolean getLeft() {
		return this.left;
	}

	public boolean getRight() {
		return this.right;
	}

	public boolean getCrouch() {
		return this.crouch;
	}

	public void setJump(boolean b) {
		this.jump = b;
		setChanged();
		notifyObservers();
	}

	public void setLeft(boolean b) {
		this.left = b;
		setChanged();
		notifyObservers();
	}

	public void setRight(boolean b) {
		this.right = b;
		setChanged();
		notifyObservers();
	}

	public void setCrouch(boolean b) {
		this.crouch = b;
		setChanged();
		notifyObservers();
	}

	public void receiveHit(int hit) {
		int temp = this.health - hit;
		if (temp <= 0) {
			this.isAlive = false;
			this.health = 0 ;
		}else {
			this.health -= hit ;
		}
	}

	public void receiveHeal(int heal) {
		int temp = this.health + heal;
		if (this.isAlive) {
			if (temp > MAX_HEALTH) {
				this.health = MAX_HEALTH;
			}else {
				this.health += heal;
			}
		}
	}

	public void resetLife() {
		this.health = MAX_HEALTH;
		this.isAlive = true;
	}

	public void getResurected(int heal) {
		this.isAlive = true;
		this.health = heal;
	}

}
