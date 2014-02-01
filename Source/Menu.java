import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

	public static JPanel cards;
	public int WIDTH = 1016;
	public int HEIGHT = 738;
	public static ImageIcon imgbackground = new ImageIcon(
			"images/backgrounds/ssmmainmenu.png");
	public static ImageIcon imgplaybutton = new ImageIcon(
			"images/buttons/playbutton.png");
	public static ImageIcon imgbuttonplayhover = new ImageIcon(
			"images/buttons/playbuttonhover.png");
	public static ImageIcon imggamepaused = new ImageIcon(
			"images/backgrounds/ssmgamepaused.png");
	public static ImageIcon imgresumebutton = new ImageIcon(
			"images/buttons/resumebutton.png");
	public static ImageIcon imgresumebuttonhover = new ImageIcon(
			"images/buttons/resumebuttonhover.png");
	public static ImageIcon imgmainmenubutton = new ImageIcon(
			"images/buttons/mainmenubutton.png");
	public static ImageIcon imgmainmenubuttonhover = new ImageIcon(
			"images/buttons/mainmenubuttonhover.png");
	public static ImageIcon imgexitbutton = new ImageIcon(
			"images/buttons/exitbutton.png");
	public static ImageIcon imgexitbuttonhover = new ImageIcon(
			"images/buttons/exitbuttonhover.png");

	public Menu(String title, GraphicalView vg) {
		super(title);

		setLayout(new BorderLayout());

		cards = new JPanel();
		cards.setLayout(new CardLayout());

		// ************ Les 2 panels **************
		JPanel mainmenu = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imgbackground.getImage(), 0, 0, this);
			}
		};
		mainmenu.setLayout(null);

		JPanel gamepaused = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imggamepaused.getImage(), 0, 0, this);
			}
		};
		gamepaused.setLayout(null);

		// ************* Play button ****************
		final JButton playbutton = new JButton("");
		playbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cards.getLayout()).show(cards, "play");
				GameEngine.resetGame();
				GameEngine.CURRENT_STATE = State.IN_GAME;
			}
		});

		playbutton.setBounds(WIDTH / 2 - 100, (int) (HEIGHT * 0.5), 200, 100);
		playbutton.setBorderPainted(false);
		playbutton.setBackground(new Color(0, 0, 0, 0));
		playbutton.setIcon(imgplaybutton);

		playbutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				playbutton.setIcon(imgbuttonplayhover);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				playbutton.setIcon(imgplaybutton);
			}
		});

		// ************* Resume button ****************
		final JButton resumebutton = new JButton("");
		resumebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cards.getLayout()).show(cards, "play");
				GameEngine.CURRENT_STATE = State.IN_GAME;
			}
		});

		resumebutton.setBounds(WIDTH / 2 - 100, (int) (HEIGHT * 0.5), 200, 100);
		resumebutton.setBorderPainted(false);
		resumebutton.setBackground(new Color(0, 0, 0, 0));
		resumebutton.setIcon(imgresumebutton);

		resumebutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				resumebutton.setIcon(imgresumebuttonhover);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				resumebutton.setIcon(imgresumebutton);
			}
		});

		// ************* Main menu button ****************
		final JButton mainmenubutton = new JButton("");
		mainmenubutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cards.getLayout()).show(cards, "mainmenu");
				GameEngine.CURRENT_STATE = State.IN_MENU;
			}
		});

		mainmenubutton.setBounds(WIDTH / 2 - 100, (int) (HEIGHT * 0.5 + 100),
				200, 100);
		mainmenubutton.setBorderPainted(false);
		mainmenubutton.setBackground(new Color(0, 0, 0, 0));
		mainmenubutton.setIcon(imgmainmenubutton);

		mainmenubutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				mainmenubutton.setIcon(imgmainmenubuttonhover);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				mainmenubutton.setIcon(imgmainmenubutton);
			}
		});

		// ************* Exit button ****************
		final JButton exitbutton = new JButton("");
		exitbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		exitbutton.setBounds(WIDTH / 2 - 92, (int) (HEIGHT * 0.5 + 150),
				200, 100);
		exitbutton.setBorderPainted(false);
		exitbutton.setBackground(new Color(0, 0, 0, 0));
		exitbutton.setIcon(imgexitbutton);

		exitbutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				exitbutton.setIcon(imgexitbuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				exitbutton.setIcon(imgexitbutton);
			}
		});

		// ************* On ajoute les cartes et les boutons aux panels correspondants ****************

		mainmenu.add(playbutton);
		mainmenu.add(exitbutton);
		mainmenu.validate();

		gamepaused.add(resumebutton);
		gamepaused.add(mainmenubutton);
		gamepaused.validate();

		cards.add("mainmenu", mainmenu);
		cards.add("play", vg);
		cards.add("gamepaused", gamepaused);

		this.add(cards, BorderLayout.CENTER);
		this.validate();
	}

}