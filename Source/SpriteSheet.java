import java.awt.image.BufferedImage;


public class SpriteSheet {
	
	public BufferedImage spriteSheet;
	
	public SpriteSheet(BufferedImage bufferedImage) {
		this.spriteSheet = bufferedImage;
	}
	
	public BufferedImage substractImage(int x, int y, int width, int height) {
		BufferedImage sprite = spriteSheet.getSubimage(x, y, width, height);
		return sprite;
	}
}
