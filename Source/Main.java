import Model.Constant;
import Model.Start;


public class Main {
	
	public static void main(String[] args) {
		if(args.length != 0) {
			Constant.DEBUGGING = true;
			System.out.println("Mode debugging actif");
		}
		Start start = new Start();
	}
}
