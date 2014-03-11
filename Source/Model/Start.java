package Model;

public class Start {

	public static GameEngine game;
	
	public static void main(String[] args) {
		if(args.length != 0) {
			Constant.DEBUGGING = true;
			System.out.println("Mode debugging activé");
		}
		game = new GameEngine();
	}
}
