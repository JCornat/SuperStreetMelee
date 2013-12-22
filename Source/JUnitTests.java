import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("static-access")
public class JUnitTests {


	static Game g;
	
	@Before
	public void setUp() throws Exception {
		 g = new Game();
	}
	
	@Test
	public void MainTest() {
		assertTrue(main.running == true);
		assertTrue(main.frames == 0);
		assertTrue(main.averageFrames == 0);
	}
	
	
	@Test
	public void GameTest() {
		g.update();
		g.tabJoueurs.get(0).vitesseY = 52;
		g.tabJoueurs.get(0).vitesseX = 52;
		g.tabJoueurs.get(0).right=true;
		g.update();
		assertTrue(g.tabJoueurs.get(0).vitesseY==50);
		assertTrue(g.tabJoueurs.get(0).vitesseX==30);
		
		g.tabJoueurs.get(0).vitesseY = -52;
		g.tabJoueurs.get(0).vitesseX = -52;


		g.tabJoueurs.get(0).left=true;
		g.update();
		assertTrue(g.tabJoueurs.get(0).vitesseY==-50);
		assertTrue(g.tabJoueurs.get(0).vitesseX==-30);
	}
	
	
	@Test
	public void AttaqueTest() {
		int nbAttaquesBefore = g.tabJoueurs.get(0).tabAttaques.size();
		g.tabJoueurs.get(0).tabAttaques.add(new Attaque("Test", 10, 20, 30, 35, 40, 50));
		int nbAttaquesAfter = g.tabJoueurs.get(0).tabAttaques.size();
		assertTrue(nbAttaquesAfter == nbAttaquesBefore+1);
		
		assertTrue(g.tabJoueurs.get(0).tabAttaques.get(2).getName() == "Test");
		assertTrue(g.tabJoueurs.get(0).tabAttaques.get(2).getWidth() == 10);
		assertTrue(g.tabJoueurs.get(0).tabAttaques.get(2).getHeight() == 20);
		assertTrue(g.tabJoueurs.get(0).tabAttaques.get(2).getPower() == 30);
		assertTrue(g.tabJoueurs.get(0).tabAttaques.get(2).getTime() == 40);
		assertTrue(g.tabJoueurs.get(0).tabAttaques.get(2).getInfoCooldown() == 50);
		assertTrue(g.tabJoueurs.get(0).tabAttaques.get(2).getEffectiveCooldown() == 0);
		
		g.tabJoueurs.get(0).tabAttaques.get(2).setEffectiveCooldown(60);
		assertTrue(g.tabJoueurs.get(0).tabAttaques.get(2).getEffectiveCooldown() == 60);
		
		g.tabJoueurs.get(0).tabAttaques.get(2).getAttackPosition(g.tabJoueurs.get(0));
		g.tabJoueurs.get(0).setTurned(false);
		g.tabJoueurs.get(0).tabAttaques.get(2).getAttackPosition(g.tabJoueurs.get(0));
	}
	
	

}
