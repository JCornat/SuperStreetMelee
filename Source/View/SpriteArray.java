package View;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


public class SpriteArray {

	ArrayList<BufferedImage> arrayOfSpritesOfTheBody;
	ArrayList<BufferedImage> arrayOfSpritesOfPThePlayerJump;
	ArrayList<BufferedImage> arrayOfSpritesOfTheBigPlayerAttack;
	SpriteSheet spriteSheet;
	BufferedImage bufferedImageContainingSprites;
	BufferedImageLoader bufferedImageToLoad;
	
	public SpriteArray(int skin) {
		
		bufferedImageToLoad = new BufferedImageLoader();
		bufferedImageContainingSprites = null;
		try {
			if(skin==1) {
				bufferedImageContainingSprites = bufferedImageToLoad.loadImage("images/character/characterSprite.png");
			} else if(skin==2) {
				bufferedImageContainingSprites = bufferedImageToLoad.loadImage("images/character/characterSprite2.png");
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		spriteSheet = new SpriteSheet(bufferedImageContainingSprites);
		arrayOfSpritesOfTheBody = new ArrayList<BufferedImage>();
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(0, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(215, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(430, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(645, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(860, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(1075, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(1290, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(1505, 0, 215, 200));
		arrayOfSpritesOfTheBody.add(spriteSheet.substractImage(1720, 0, 215, 200));
		
		
		try {
			bufferedImageContainingSprites = bufferedImageToLoad.loadImage("images/character/jumpingSprite2.png");
		} catch (IOException e) {
			System.err.println(e);
		}
		spriteSheet = new SpriteSheet(bufferedImageContainingSprites);
		arrayOfSpritesOfPThePlayerJump = new ArrayList<BufferedImage>();
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(0, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(71, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(142, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(213, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(284, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(355, 0, 71, 61));
		arrayOfSpritesOfPThePlayerJump.add(spriteSheet.substractImage(426, 0, 71, 61));

		
		try {
			bufferedImageContainingSprites = bufferedImageToLoad.loadImage("images/character/character1Attack.png");
		} catch (IOException e) {
			System.err.println(e);
		}
		spriteSheet = new SpriteSheet(bufferedImageContainingSprites);
		arrayOfSpritesOfTheBigPlayerAttack = new ArrayList<BufferedImage>();
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(0, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(286, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(572, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(858, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(1144, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(1430, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(1717, 0, 286, 250));
		arrayOfSpritesOfTheBigPlayerAttack.add(spriteSheet.substractImage(2002, 0, 286, 250));
		
		
		
	}

}
