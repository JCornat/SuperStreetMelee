package View;

public class GraphicalPlayer {
	
	public SpriteArray spriteArray;
	public Animator characterAnimationBody;
	public AnimatorOneLoop characterAnimationBigAttack;
	public AnimatorOneLoop characterAnimationJump;
	
	public GraphicalPlayer(int skin) {
		spriteArray = new SpriteArray(skin);
	}
}
