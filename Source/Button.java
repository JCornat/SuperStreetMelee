import java.awt.Color;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Button extends JButton {
	
	
	public Button(String name, final ImageIcon img, final ImageIcon imghover) {
		super(name) ;
		this.setIcon(img) ;
		this.setBorderPainted(false) ;
		this.setBackground(new Color(0 ,0 ,0, 0)) ;
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
                setIcon(img) ;         
            }
			public void mouseExited(java.awt.event.MouseEvent evt) {
                setIcon(imghover) ;
            }
		}) ;
	}
	
	
}
