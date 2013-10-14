import java.util.Observable;


public class Decor extends Observable {
	
	int x,y,w,h;
	
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
