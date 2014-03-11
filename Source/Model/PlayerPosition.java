package Model;

public class PlayerPosition {

	public int x,y,w,h;
	
	public PlayerPosition(int k, int l) {
		x = 0;
		y = 0;
		w = k;
		h = l;
	}
	
	public void setX(int i) {
		x = i;
	}
	
	public void setY(int i) {
		y = i;
	}
	
	public void setW(int i) {
		w = i;
	}
	
	public void setH(int i) {
		h = i;
	}

}
