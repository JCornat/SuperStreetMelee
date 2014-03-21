package Model;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import Sound.SoundManager;
import View.GraphicalView;

public class Menu extends JFrame {

	public static JPanel cards;
	public static int WIDTH = 1016;
	public static int HEIGHT = 738;
	public int playercount;

	public Menu(String title, GraphicalView vg) {
		super(title);

		final ImageIcon imgbackground = new ImageIcon(
				"images/backgrounds/ssmmainmenu.png");
		final ImageIcon imgplaybutton = new ImageIcon(
				"images/buttons/playbutton.png");
		final ImageIcon imgbuttonplayhover = new ImageIcon(
				"images/buttons/playbuttonhover.png");
		final ImageIcon imggamepaused = new ImageIcon(
				"images/backgrounds/ssmgamepaused.png");
		final ImageIcon imgmode = new ImageIcon("images/backgrounds/ssmode.png");
		final ImageIcon imgmap = new ImageIcon("images/backgrounds/ssmap.png");
		final ImageIcon imgplayer = new ImageIcon(
				"images/backgrounds/ssplayer.png");
		final ImageIcon imgresumebutton = new ImageIcon(
				"images/buttons/resumebutton.png");
		final ImageIcon imgresumebuttonhover = new ImageIcon(
				"images/buttons/resumebuttonhover.png");
		final ImageIcon imgmainmenubutton = new ImageIcon(
				"images/buttons/mainmenubutton.png");
		final ImageIcon imgmainmenubuttonhover = new ImageIcon(
				"images/buttons/mainmenubuttonhover.png");
		final ImageIcon imgexitbutton = new ImageIcon(
				"images/buttons/exitbutton.png");
		final ImageIcon imgexitbuttonhover = new ImageIcon(
				"images/buttons/exitbuttonhover.png");
		final ImageIcon imgleftbutton = new ImageIcon(
				"images/buttons/leftbutton.png");
		final ImageIcon imgleftbuttonhover = new ImageIcon(
				"images/buttons/leftbuttonhover.png");
		final ImageIcon imgrightbutton = new ImageIcon(
				"images/buttons/rightbutton.png");
		final ImageIcon imgrightbuttonhover = new ImageIcon(
				"images/buttons/rightbuttonhover.png");
		final ImageIcon img1playerbutton = new ImageIcon(
				"images/buttons/1playerbutton.png");
		final ImageIcon img1playerbuttonhover = new ImageIcon(
				"images/buttons/1playerbuttonhover.png");
		final ImageIcon img2playersbutton = new ImageIcon(
				"images/buttons/2playersbutton.png");
		final ImageIcon img2playersbuttonhover = new ImageIcon(
				"images/buttons/2playersbuttonhover.png");
		final ImageIcon img3playersbutton = new ImageIcon(
				"images/buttons/3playersbutton.png");
		final ImageIcon img3playersbuttonhover = new ImageIcon(
				"images/buttons/3playersbuttonhover.png");
		final ImageIcon img4playersbutton = new ImageIcon(
				"images/buttons/4playersbutton.png");
		final ImageIcon img4playersbuttonhover = new ImageIcon(
				"images/buttons/4playersbuttonhover.png");
		final ImageIcon imgvalidatebutton = new ImageIcon(
				"images/buttons/validatebutton.png");
		final ImageIcon imgvalidatebuttonhover = new ImageIcon(
				"images/buttons/validatebuttonhover.png");

		this.playercount = 0;

		setLayout(new BorderLayout());

		cards = new JPanel();
		cards.setLayout(new CardLayout());

		final JPanel players = new JPanel();
		players.setLayout(new CardLayout());
		players.setPreferredSize(new Dimension(300, 300));
		players.setBounds(WIDTH / 2 - 150, HEIGHT / 2 - 100, 300, 300);

		final JPanel maps = new JPanel();
		maps.setLayout(new CardLayout());
		maps.setPreferredSize(new Dimension(300, 300));
		maps.setBounds(WIDTH / 2 - 150, HEIGHT / 2 - 100, 300, 300);

		// ************ Les panels **************
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

		JPanel mode = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imgmode.getImage(), 0, 0, this);
			}
		};
		mode.setLayout(null);

		JPanel map = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imgmap.getImage(), 0, 0, this);
			}
		};
		map.setLayout(null);

		JPanel player = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imgplayer.getImage(), 0, 0, this);
			}
		};
		player.setLayout(null);

		JPanel player1 = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
		player1.setLayout(null);

		JPanel player2 = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
		player2.setLayout(null);

		JPanel map1 = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.GREEN);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
		map1.setLayout(null);

		JPanel map2 = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.CYAN);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
		map2.setLayout(null);

		// ************* Play button ****************
		final JButton playbutton = new JButton("");
		playbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cards.getLayout()).show(cards, "mode");
				Game.CURRENT_STATE = State.IN_MENU;
				SoundManager.sounds.get("buttonclick").play_once();
				// Resolution des problemes de focus avec Java sur OSX
//				cards.transferFocus();
			}
		});

		playbutton.setBounds(WIDTH / 2 - 100, (int) (HEIGHT * 0.5), 200, 100);
		playbutton.setBorderPainted(false);
		playbutton.setBackground(new Color(0, 0, 0, 0));
		playbutton.setIcon(imgplaybutton);

		playbutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				playbutton.setIcon(imgbuttonplayhover);
			}

			public void mouseExited(MouseEvent evt) {
				playbutton.setIcon(imgplaybutton);
			}
		});

		// ************* Resume button ****************
		final JButton resumebutton = new JButton("");
		resumebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cards.getLayout()).show(cards, "play");
				Game.CURRENT_STATE = State.IN_GAME;
				// Resolution des problemes de focus avec Java sur OSX
				SoundManager.sounds.get("buttonclick").play_once();
				cards.transferFocus();
			}
		});

		resumebutton.setBounds(WIDTH / 2 - 100, (int) (HEIGHT * 0.5), 200, 100);
		resumebutton.setBorderPainted(false);
		resumebutton.setBackground(new Color(0, 0, 0, 0));
		resumebutton.setIcon(imgresumebutton);

		resumebutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				resumebutton.setIcon(imgresumebuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				resumebutton.setIcon(imgresumebutton);
			}
		});

		// ************* Left button players ****************
		final JButton leftbuttonplayers = new JButton("");
		leftbuttonplayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) players.getLayout()).previous(players);
				Game.CURRENT_STATE = State.IN_MENU;
				// Resolution des problemes de focus avec Java sur OSX
				SoundManager.sounds.get("buttonclick").play_once();
				players.transferFocus();
			}
		});

		leftbuttonplayers.setBounds(50, HEIGHT / 2 - 5, 100, 100);
		leftbuttonplayers.setBorderPainted(false);
		leftbuttonplayers.setBackground(new Color(0, 0, 0, 0));
		leftbuttonplayers.setIcon(imgleftbutton);

		leftbuttonplayers.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				leftbuttonplayers.setIcon(imgleftbuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				leftbuttonplayers.setIcon(imgleftbutton);
			}
		});
		
//		Action leftbuttonplayersaction = new AbstractAction(){
//			public void actionPerformed(ActionEvent arg0) {	
//				((CardLayout) players.getLayout()).previous(players);
//				Game.CURRENT_STATE = State.IN_MENU;
//				// Resolution des problemes de focus avec Java sur OSX
//				SoundManager.sounds.get("buttonclick").play_once();
//				players.transferFocus();
//			}	
//		} ;
//		
//		
//		leftbuttonplayers.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed") ;
//		leftbuttonplayers.getActionMap().put("pressed", leftbuttonplayersaction) ;
		
		// ************* Left button maps ****************
		final JButton leftbuttonmaps = new JButton("");
		leftbuttonmaps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) maps.getLayout()).previous(maps);
				Game.CURRENT_STATE = State.IN_MENU;
				
				// Resolution des problemes de focus avec Java sur OSX
				SoundManager.sounds.get("buttonclick").play_once();
				maps.transferFocus();
			}
		});

		leftbuttonmaps.setBounds(50, HEIGHT / 2 - 5, 100, 100);
		leftbuttonmaps.setBorderPainted(false);
		leftbuttonmaps.setBackground(new Color(0, 0, 0, 0));
		leftbuttonmaps.setIcon(imgleftbutton);

		leftbuttonmaps.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				leftbuttonmaps.setIcon(imgleftbuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				leftbuttonmaps.setIcon(imgleftbutton);
			}
		});

		// ************* right button players ****************
		final JButton rightbuttonplayers = new JButton("");
		rightbuttonplayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) players.getLayout()).next(players);
				Game.CURRENT_STATE = State.IN_MENU;
				// Resolution des problemes de focus avec Java sur OSX
				SoundManager.sounds.get("buttonclick").play_once();
				players.transferFocus();
			}
		});

		rightbuttonplayers.setBounds(WIDTH - 150, HEIGHT / 2, 100, 100);
		rightbuttonplayers.setBorderPainted(false);
		rightbuttonplayers.setBackground(new Color(0, 0, 0, 0));
		rightbuttonplayers.setIcon(imgrightbutton);

		rightbuttonplayers.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				rightbuttonplayers.setIcon(imgrightbuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				rightbuttonplayers.setIcon(imgrightbutton);
			}
		});

		// ************* right button maps ****************
		final JButton rightbuttonmaps = new JButton("");
		rightbuttonmaps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) maps.getLayout()).next(maps);
				Game.CURRENT_STATE = State.IN_MENU;
				// Resolution des problemes de focus avec Java sur OSX
				SoundManager.sounds.get("buttonclick").play_once();
				maps.transferFocus();
			}
		});

		rightbuttonmaps.setBounds(WIDTH - 150, HEIGHT / 2, 100, 100);
		rightbuttonmaps.setBorderPainted(false);
		rightbuttonmaps.setBackground(new Color(0, 0, 0, 0));
		rightbuttonmaps.setIcon(imgrightbutton);

		rightbuttonmaps.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				rightbuttonmaps.setIcon(imgrightbuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				rightbuttonmaps.setIcon(imgrightbutton);
			}
		});

		// ************* validate button mode ****************
		final JButton validatebuttonmode = new JButton("");
		validatebuttonmode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Game.PlayerNumber != 0) {
					Game.CURRENT_STATE = State.IN_MENU;
					SoundManager.sounds.get("buttonclick").play_once();
					((CardLayout) cards.getLayout()).show(cards, "player");
					Game.SkinsOfPlayers[0] = 1;
					cards.transferFocus();
				}
			}
		});

		validatebuttonmode.setBounds(WIDTH - 125, HEIGHT - 150, 75, 75);
		validatebuttonmode.setBorderPainted(false);
		validatebuttonmode.setBackground(new Color(0, 0, 0, 0));
		validatebuttonmode.setIcon(imgvalidatebutton);

		validatebuttonmode.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				validatebuttonmode.setIcon(imgvalidatebuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				validatebuttonmode.setIcon(imgvalidatebutton);
			}
		});

		// ************* validate button player ****************
		final JButton validatebuttonplayer = new JButton("");
		validatebuttonplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.CURRENT_STATE = State.IN_MENU;
				SoundManager.sounds.get("buttonclick").play_once();
				for (Component component : players.getComponents()) {
					if (component.isVisible()) {
						switch (component.getName()) {
						case "player1":
							Game.SkinsOfPlayers[playercount] = 1;
							break;
						case "player2":
							Game.SkinsOfPlayers[playercount] = 2;
							break;
						default:
							break;
						}
					}
				}
				playercount++;
				if (playercount == Game.PlayerNumber) {
					((CardLayout) cards.getLayout()).show(cards, "map");
					Game.LevelIndex = 0;
					cards.transferFocus();
				}
			}
		});

		validatebuttonplayer.setBounds(WIDTH - 125, HEIGHT - 150, 75, 75);
		validatebuttonplayer.setBorderPainted(false);
		validatebuttonplayer.setBackground(new Color(0, 0, 0, 0));
		validatebuttonplayer.setIcon(imgvalidatebutton);

		validatebuttonplayer.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				validatebuttonplayer.setIcon(imgvalidatebuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				validatebuttonplayer.setIcon(imgvalidatebutton);
			}
		});

		// ************* validate button maps ****************
		final JButton validatebuttonmap = new JButton("");
		validatebuttonmap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Component component : maps.getComponents()) {
					if (component.isVisible()) {
						switch (component.getName()) {
						case "map1":
							Game.LevelIndex = 0;
							break;
						case "map2":
							Game.LevelIndex = 1;
							break;
						default:
							break;
						}
					}
				}
				Game.InitPlayersAndMap();
				Game.resetGame();
				Game.CURRENT_STATE = State.IN_GAME;
				SoundManager.sounds.get("buttonclick").play_once();
				SoundManager.sounds.get("intro").pause();
				((CardLayout) cards.getLayout()).show(cards, "play");
				cards.transferFocus();
			}
		});

		validatebuttonmap.setBounds(WIDTH - 125, HEIGHT - 150, 75, 75);
		validatebuttonmap.setBorderPainted(false);
		validatebuttonmap.setBackground(new Color(0, 0, 0, 0));
		validatebuttonmap.setIcon(imgvalidatebutton);

		validatebuttonmap.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				validatebuttonmap.setIcon(imgvalidatebuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				validatebuttonmap.setIcon(imgvalidatebutton);
			}
		});

		// ************* 1player button ****************
		final JButton oneplayerbutton = new JButton("");
		oneplayerbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.PlayerNumber = 1;
				Game.CURRENT_STATE = State.IN_MENU;
				SoundManager.sounds.get("buttonclick").play_once();
			}
		});

		oneplayerbutton.setBounds(WIDTH / 4 - 75, HEIGHT / 2 - 80, 275, 100);
		oneplayerbutton.setBorderPainted(false);
		oneplayerbutton.setBackground(new Color(0, 0, 0, 0));
		oneplayerbutton.setIcon(img1playerbutton);

		oneplayerbutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				oneplayerbutton.setIcon(img1playerbuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				oneplayerbutton.setIcon(img1playerbutton);
			}
		});

		// ************* 2player button ****************
		final JButton twoplayerbutton = new JButton("");
		twoplayerbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.PlayerNumber = 2;
				Game.CURRENT_STATE = State.IN_MENU;
				SoundManager.sounds.get("buttonclick").play_once();
			}
		});

		twoplayerbutton.setBounds(WIDTH / 2 + 50, HEIGHT / 2 - 80, 275, 100);
		twoplayerbutton.setBorderPainted(false);
		twoplayerbutton.setBackground(new Color(0, 0, 0, 0));
		twoplayerbutton.setIcon(img2playersbutton);

		twoplayerbutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				twoplayerbutton.setIcon(img2playersbuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				twoplayerbutton.setIcon(img2playersbutton);
			}
		});

		// ************* 3player button ****************
		final JButton threeplayerbutton = new JButton("");
		threeplayerbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.PlayerNumber = 3;
				Game.CURRENT_STATE = State.IN_MENU;
				SoundManager.sounds.get("buttonclick").play_once();
			}
		});

		threeplayerbutton.setBounds(WIDTH / 4 - 75, HEIGHT / 2 + 80, 275, 100);
		threeplayerbutton.setBorderPainted(false);
		threeplayerbutton.setBackground(new Color(0, 0, 0, 0));
		threeplayerbutton.setIcon(img3playersbutton);

		threeplayerbutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				threeplayerbutton.setIcon(img3playersbuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				threeplayerbutton.setIcon(img3playersbutton);
			}
		});

		// ************* 4player button ****************
		final JButton fourplayerbutton = new JButton("");
		fourplayerbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.PlayerNumber = 4;
				Game.CURRENT_STATE = State.IN_MENU;
				SoundManager.sounds.get("buttonclick").play_once();
			}
		});

		fourplayerbutton.setBounds(WIDTH / 2 + 50, HEIGHT / 2 + 80, 275, 100);
		fourplayerbutton.setBorderPainted(false);
		fourplayerbutton.setBackground(new Color(0, 0, 0, 0));
		fourplayerbutton.setIcon(img4playersbutton);

		fourplayerbutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				fourplayerbutton.setIcon(img4playersbuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				fourplayerbutton.setIcon(img4playersbutton);
			}
		});

		// ************* Main menu button ****************
		final JButton mainmenubutton = new JButton("");
		mainmenubutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playercount = 0 ;
				Game.listPlayers.clear() ;
				((CardLayout) cards.getLayout()).show(cards, "mainmenu");
				Game.CURRENT_STATE = State.IN_MENU;
				SoundManager.sounds.get("buttonclick").play_once();
				cards.transferFocus() ;
			}
		});

		mainmenubutton.setBounds(WIDTH / 2 - 100, (int) (HEIGHT * 0.5 + 100),
				200, 100);
		mainmenubutton.setBorderPainted(false);
		mainmenubutton.setBackground(new Color(0, 0, 0, 0));
		mainmenubutton.setIcon(imgmainmenubutton);

		mainmenubutton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				mainmenubutton.setIcon(imgmainmenubuttonhover);
			}

			public void mouseExited(MouseEvent evt) {
				mainmenubutton.setIcon(imgmainmenubutton);
			}
		});

		// ************* Exit button ****************
		final JButton exitbutton = new JButton("");
		exitbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundManager.sounds.get("buttonclick").play_once();
				System.exit(0);
			}
		});

		exitbutton.setBounds(WIDTH / 2 - 92, (int) (HEIGHT * 0.5 + 150), 200,
				100);
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

		// ************* On ajoute les cartes et les boutons aux panels
		// correspondants ****************

		player1.setName("player1");
		player2.setName("player2");
		players.add("player1", player1);
		players.add("player2", player2);
		player.add(players);
		player.add(leftbuttonplayers);
		player.add(rightbuttonplayers);
		player.add(validatebuttonplayer);

		map1.setName("map1");
		map2.setName("map2");
		maps.add("map1", map1);
		maps.add("map2", map2);
		map.add(maps);
		map.add(leftbuttonmaps);
		map.add(rightbuttonmaps);
		map.add(validatebuttonmap);

		mode.add(oneplayerbutton);
		mode.add(twoplayerbutton);
		mode.add(threeplayerbutton);
		mode.add(fourplayerbutton);
		mode.add(validatebuttonmode);

		mainmenu.add(playbutton);
		mainmenu.add(exitbutton);
		mainmenu.validate();

		gamepaused.add(resumebutton);
		gamepaused.add(mainmenubutton);
		gamepaused.validate();

		cards.add("mainmenu", mainmenu);
		cards.add("play", vg);
		cards.add("gamepaused", gamepaused);
		cards.add("player", player);
		cards.add("mode", mode);
		cards.add("map", map);

		this.add(cards, BorderLayout.CENTER);
		this.validate();
	}

}