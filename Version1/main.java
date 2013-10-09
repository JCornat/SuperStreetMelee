import javax.swing.JFrame;
import javax.swing.JPanel;


public class main extends JFrame {

	public static void main(String[] args) {
		
		//Creation de la fenetre de jeu
		JFrame frame = new JFrame();
		
		//Creation du joueur
		Joueur p1 = new Joueur(50,330,60,100);
		
		//Appel et ajout du pattern d'affichage
		VueGraphique vg = new VueGraphique(p1);
		frame.add(vg);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//Le personnage va vers la droite
		while(true) {
			p1.right();
			
			pause();
		}
		
	}
	
	public static void pause() {
		try {
			Thread.sleep(5);
		} catch (Exception e) {
			System.err.println("Sleep error");
		}
	}

}
