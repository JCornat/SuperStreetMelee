import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Menu extends JFrame {

	static JPanel cards;
	public int WIDTH = 1016;
	public int HEIGHT = 738;
	public static ImageIcon imgbackground 			 	= new ImageIcon("ssmmainmenu.png") ;
	public static ImageIcon imgplaybutton 				= new ImageIcon("playbutton.png") ;
	public static ImageIcon imgbuttonplayhover 		= new ImageIcon("playbuttonhover.png") ;
	public static ImageIcon imggamepaused 				= new ImageIcon("ssmgamepaused.png") ;
	public static ImageIcon imgresumebutton 			= new ImageIcon("resumebutton.png") ;
	public static ImageIcon imgresumebuttonhover 		= new ImageIcon("resumebuttonhover.png") ;
	public static ImageIcon imgmainmenubutton		 	= new ImageIcon("mainmenubutton.png") ;
	public static ImageIcon imgmainmenubuttonhover  	= new ImageIcon("mainmenubuttonhover.png") ;
	
	public Menu(String titre, VueGraphique vg) {
		super(titre);

		setLayout(new BorderLayout());

		cards = new JPanel();
		cards.setLayout(new CardLayout());

		//************ Les 2 panels **************
		JPanel mainmenu = new JPanel() ;
		JLabel labelbackground = new JLabel(imgbackground);
		mainmenu.setLayout(null);
		labelbackground.setBounds(0, 0, WIDTH, HEIGHT) ;
		mainmenu.add(labelbackground);
		
		JPanel gamepaused = new JPanel() ;
		JLabel labelgamepaused = new JLabel(imggamepaused);
		gamepaused.setLayout(null);
		labelgamepaused.setBounds(0, 0, WIDTH, HEIGHT) ;
		gamepaused.add(labelgamepaused);
		
		//************* Play button ****************
		final JButton playbutton = new JButton("");
		playbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cards.getLayout()).show(cards, "play");
				Game.resetGame() ;
				Game.CURRENT_STATE = STATE.IN_GAME ;
			}
		});
		
		playbutton.setBounds(WIDTH / 2 - 100, (int) (HEIGHT * 0.5) , 200, 100);
		playbutton.setBorderPainted(false) ;
		playbutton.setBackground(new Color(0 ,0 ,0, 0)) ;
		playbutton.setIcon(imgplaybutton) ;
		
		playbutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
                playbutton.setIcon(imgbuttonplayhover) ;         
            }
			public void mouseExited(java.awt.event.MouseEvent evt) {
                playbutton.setIcon(imgplaybutton) ;
            }
		}) ;
		
		
		//************* Resume button ****************
		final JButton resumebutton = new JButton("");
		resumebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cards.getLayout()).show(cards, "play");
				Game.CURRENT_STATE = STATE.IN_GAME ;
			}
		});
		
		resumebutton.setBounds(WIDTH / 2 - 100, (int) (HEIGHT * 0.5) , 200, 100);
		resumebutton.setBorderPainted(false) ;
		resumebutton.setBackground(new Color(0 ,0 ,0, 0)) ;
		resumebutton.setIcon(imgresumebutton) ;
		
		resumebutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				resumebutton.setIcon(imgresumebuttonhover) ;  
				resumebutton.repaint() ;
            }
			public void mouseExited(java.awt.event.MouseEvent evt) {
				resumebutton.setIcon(imgresumebutton) ;
            }
		}) ;
		
		
		//************* Main menu button ****************
		final JButton mainmenubutton = new JButton("");
		mainmenubutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cards.getLayout()).show(cards, "mainmenu");
				Game.CURRENT_STATE = STATE.IN_MENU ;
			}
		});
		
		mainmenubutton.setBounds(WIDTH / 2 - 100, (int) (HEIGHT * 0.5 + 100) , 200, 100);
		mainmenubutton.setBorderPainted(false) ;
		mainmenubutton.setBackground(new Color(0 ,0 ,0, 0)) ;
		mainmenubutton.setIcon(imgmainmenubutton) ;
		
		mainmenubutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				mainmenubutton.setIcon(imgmainmenubuttonhover) ;         
            }
			public void mouseExited(java.awt.event.MouseEvent evt) {
				mainmenubutton.setIcon(imgmainmenubutton) ;
            }
		});

		//************* On ajoute les cartes ****************
	
		mainmenu.add(playbutton);
		mainmenu.validate() ;
		
		gamepaused.add(resumebutton) ;
		gamepaused.add(mainmenubutton) ;
		gamepaused.validate() ;
		
		
		cards.add("mainmenu", mainmenu);
		cards.add("play", vg);
		cards.add("gamepaused", gamepaused) ;
		
		
		this.add(cards, BorderLayout.CENTER);
		this.validate() ;
	}

}