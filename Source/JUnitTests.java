import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("static-access")
public class JUnitTests {


	static GameEngine g;
	
	@Before
	public void setUp() throws Exception {
		 g = new GameEngine();
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
		g.listPlayers.get(0).vitesseY = 52;
		g.listPlayers.get(0).vitesseX = 52;
		g.listPlayers.get(0).right=true;
		g.update();
		assertTrue(g.listPlayers.get(0).vitesseY==50);
		assertTrue(g.listPlayers.get(0).vitesseX==30);
		
		g.listPlayers.get(0).vitesseY = -52;
		g.listPlayers.get(0).vitesseX = -52;


		g.listPlayers.get(0).left=true;
		g.update();
		assertTrue(g.listPlayers.get(0).vitesseY==-50);
		assertTrue(g.listPlayers.get(0).vitesseX==-30);
	}
	
	
	@Test
	public void AttaqueTest() {
		int nbAttaquesBefore = g.listPlayers.get(0).tabAttaques.size();
		g.listPlayers.get(0).tabAttaques.add(new Attack("Test", 10, 20, 30, 35, 40, 50, 60 ,70));
		int nbAttaquesAfter = g.listPlayers.get(0).tabAttaques.size();
		assertTrue(nbAttaquesAfter == nbAttaquesBefore+1);
		
		assertTrue(g.listPlayers.get(0).tabAttaques.get(2).getName() == "Test");
		assertTrue(g.listPlayers.get(0).tabAttaques.get(2).getWidth() == 10);
		assertTrue(g.listPlayers.get(0).tabAttaques.get(2).getHeight() == 20);
		assertTrue(g.listPlayers.get(0).tabAttaques.get(2).getDamage() == 30);
		assertTrue(g.listPlayers.get(0).tabAttaques.get(2).getTime() == 40);
		assertTrue(g.listPlayers.get(0).tabAttaques.get(2).getInfoCooldown() == 50);
		assertTrue(g.listPlayers.get(0).tabAttaques.get(2).getEffectiveCooldown() == 0);
		
		g.listPlayers.get(0).tabAttaques.get(2).setEffectiveCooldown(60);
		assertTrue(g.listPlayers.get(0).tabAttaques.get(2).getEffectiveCooldown() == 60);
		
		g.listPlayers.get(0).tabAttaques.get(2).getAttackPosition(g.listPlayers.get(0));
		g.listPlayers.get(0).setTurned(false);
		g.listPlayers.get(0).tabAttaques.get(2).getAttackPosition(g.listPlayers.get(0));
	}
	
	

}
