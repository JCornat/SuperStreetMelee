package View;

public class GraphicalPlayer {
	
	public SpriteArray spriteArray;
	public Animator characterAnimationBody;
	public AnimatorOneLoop characterAnimationBigAttack;
	public AnimatorOneLoop characterAnimationJump;
	
	public GraphicalPlayer(int skin) {
		spriteArray = new SpriteArray(skin);
		characterAnimationBody = new Animator(spriteArray.arrayOfSpritesOfTheBody);
		characterAnimationBody.setSpeed(150);
		characterAnimationBody.start();
		
		characterAnimationJump = new AnimatorOneLoop(spriteArray.arrayOfSpritesOfPThePlayerJump);
		characterAnimationJump.setSpeed(30);
		
		characterAnimationBigAttack = new AnimatorOneLoop(spriteArray.arrayOfSpritesOfTheBigPlayerAttack);
		characterAnimationBigAttack.setSpeed(30);
	}
}
