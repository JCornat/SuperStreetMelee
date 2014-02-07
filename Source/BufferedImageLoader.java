import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class BufferedImageLoader {
	
	public BufferedImage loadImage(String path) throws IOException{
		//F url = this.getClass().getResource(path);
		BufferedImage bufferedImage = ImageIO.read(new File(path));
		return bufferedImage;
	}

}
