package vft.window;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import java.awt.Component;
import java.awt.Dimension;

import vft.GameChar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class VFTWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1959981740676746279L;
	private JPanel cardPanel, menuPane;
	CardLayout cl;
	CardLayout cl_game;
	private JPanel gamePanel;
	private JPanel charPanel;
	private JPanel gameCardPanel;
	private GameInit initPanel;
	private JPanel gameWindow;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VFTWindow frame = new VFTWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VFTWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 800));
		cardPanel = new JPanel();
		cardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		cl = new CardLayout();
		cardPanel.setLayout(cl);
		setContentPane(cardPanel);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		menuPane = new JPanel();
		cardPanel.add(menuPane, "menuPane");
		menuPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitle = new JLabel("Virtual Fantasy Trip");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitle.setFont(new Font("Super Black SF", Font.PLAIN, 27));
		menuPane.add(lblTitle, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		menuPane.add(buttonPanel, BorderLayout.CENTER);
		GridBagLayout gbl_buttonPanel = new GridBagLayout();
		gbl_buttonPanel.columnWidths = new int[] {200};
		gbl_buttonPanel.rowHeights = new int[] {50, 50, 50, 50};
		gbl_buttonPanel.columnWeights = new double[]{0.0};
		gbl_buttonPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		buttonPanel.setLayout(gbl_buttonPanel);
		
		JButton playButton = new JButton("Play");
		playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_playButton = new GridBagConstraints();
		gbc_playButton.insets = new Insets(0, 0, 5, 5);
		gbc_playButton.gridx = 0;
		gbc_playButton.gridy = 0;
		buttonPanel.add(playButton, gbc_playButton);
		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(cardPanel, "gamePanel");
				initPanel.renewTeams();
			}
			
		});
		
		JButton charactersButton = new JButton("Characters");
		charactersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_charactersButton = new GridBagConstraints();
		gbc_charactersButton.insets = new Insets(0, 0, 5, 5);
		gbc_charactersButton.gridx = 0;
		gbc_charactersButton.gridy = 1;
		buttonPanel.add(charactersButton, gbc_charactersButton);
		charactersButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(cardPanel, "charPanel");
			}
			
		});
		
		JButton teamsButton = new JButton("Teams");
		teamsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_teamsButton = new GridBagConstraints();
		gbc_teamsButton.insets = new Insets(0, 0, 5, 5);
		gbc_teamsButton.gridx = 0;
		gbc_teamsButton.gridy = 2;
		buttonPanel.add(teamsButton, gbc_teamsButton);
		teamsButton.setEnabled(true);
		
		
		JButton directionsButton = new JButton("How to Play");
		directionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_directionsButton = new GridBagConstraints();
		gbc_directionsButton.insets = new Insets(0, 0, 5, 5);
		gbc_directionsButton.gridx = 0;
		gbc_directionsButton.gridy = 3;
		buttonPanel.add(directionsButton, gbc_directionsButton);
		directionsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame directionsFrame = new JFrame("Directions");
				JScrollPane scrollPane_1 = new JScrollPane();
				
				
				JTextPane instructionsPane = new JTextPane();
				scrollPane_1.setViewportView(instructionsPane);
				try {
					instructionsPane.read(new BufferedReader(new FileReader(new File("instructions.txt"))), new PlainDocument());
				} catch (IOException e1) {
					// 
					e1.printStackTrace();
				}
				
				directionsFrame.setLayout(new BorderLayout());
				directionsFrame.add(scrollPane_1, BorderLayout.CENTER);
				directionsFrame.setPreferredSize(new Dimension(800, 600));
				directionsFrame.pack();

				directionsFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				directionsFrame.setVisible(true);
			}
			
		});
		
		gamePanel = new JPanel();
		cardPanel.add(gamePanel, "gamePanel");
		GridBagLayout gbl_gamePanel = new GridBagLayout();
		gbl_gamePanel.columnWidths = new int[]{38, 348, 0};
		gbl_gamePanel.rowHeights = new int[]{31, 631, 0, 0};
		gbl_gamePanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_gamePanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gamePanel.setLayout(gbl_gamePanel);
		
		JButton btnGameMenu = new JButton("Menu");
		GridBagConstraints gbc_btnGameMenu = new GridBagConstraints();
		gbc_btnGameMenu.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGameMenu.anchor = GridBagConstraints.SOUTH;
		gbc_btnGameMenu.insets = new Insets(0, 0, 5, 5);
		gbc_btnGameMenu.gridx = 0;
		gbc_btnGameMenu.gridy = 1;
		gamePanel.add(btnGameMenu, gbc_btnGameMenu);
		
		gameCardPanel = new JPanel();
		GridBagConstraints gbc_gameCardPanel = new GridBagConstraints();
		gbc_gameCardPanel.gridheight = 2;
		gbc_gameCardPanel.insets = new Insets(0, 0, 5, 0);
		gbc_gameCardPanel.fill = GridBagConstraints.BOTH;
		gbc_gameCardPanel.gridx = 1;
		gbc_gameCardPanel.gridy = 1;
		gamePanel.add(gameCardPanel, gbc_gameCardPanel);
		cl_game = new CardLayout();
		gameCardPanel.setLayout(cl_game);
		
		initPanel = new GameInit(this);
		gameCardPanel.add(initPanel, "gameInit");
		
		gameWindow = new JPanel();
		gameCardPanel.add(gameWindow, "gameWindow");
		gameWindow.setLayout(new BorderLayout(0, 0));
		
		
		
		JList<GameChar> infoPanelList = new JList<GameChar>();
		infoPanelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		infoPanelList.setSelectedIndex(0);
		infoPanelList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		infoPanelList.setVisibleRowCount(1);
		
		GamePanel game = new GamePanel();
		gameWindow.add(game);
		JScrollPane scrollPane = new JScrollPane(infoPanelList);
		gameWindow.add(scrollPane, BorderLayout.SOUTH);
		
		initPanel.setGamePanel(game);
		initPanel.setInfoPanelList(infoPanelList);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_game.show(gameCardPanel, "gameInit");
				initPanel.renewTeams();
			}
		});
		GridBagConstraints gbc_btnNewGame = new GridBagConstraints();
		gbc_btnNewGame.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewGame.gridx = 0;
		gbc_btnNewGame.gridy = 2;
		gamePanel.add(btnNewGame, gbc_btnNewGame);
		
		btnGameMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.first(cardPanel);
			}
			
		});
		
		charPanel = new JPanel();
		cardPanel.add(charPanel, "charPanel");
		GridBagLayout gbl_charPanel = new GridBagLayout();
		gbl_charPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_charPanel.rowHeights = new int[]{0, 0, 0};
		gbl_charPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_charPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		charPanel.setLayout(gbl_charPanel);
		
		JLabel lblCharacterCreation = new JLabel("Character Selection");
		lblCharacterCreation.setFont(new Font("Super Black SF", Font.PLAIN, 20));
		GridBagConstraints gbc_lblCharacterCreation = new GridBagConstraints();
		gbc_lblCharacterCreation.gridwidth = 9;
		gbc_lblCharacterCreation.insets = new Insets(0, 0, 5, 0);
		gbc_lblCharacterCreation.gridx = 0;
		gbc_lblCharacterCreation.gridy = 0;
		charPanel.add(lblCharacterCreation, gbc_lblCharacterCreation);
		
		JButton btnCharMenu = new JButton("Menu");
		GridBagConstraints gbc_btnCharMenu = new GridBagConstraints();
		gbc_btnCharMenu.anchor = GridBagConstraints.SOUTH;
		gbc_btnCharMenu.insets = new Insets(0, 0, 0, 5);
		gbc_btnCharMenu.gridx = 0;
		gbc_btnCharMenu.gridy = 1;
		charPanel.add(btnCharMenu, gbc_btnCharMenu);
		btnCharMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.first(cardPanel);
			}
		});
		
		CharSelector charSelectPanel = new CharSelector();
		GridBagConstraints gbc_charSelectPanel = new GridBagConstraints();
		gbc_charSelectPanel.gridwidth = 8;
		gbc_charSelectPanel.fill = GridBagConstraints.BOTH;
		gbc_charSelectPanel.gridx = 1;
		gbc_charSelectPanel.gridy = 1;
		charPanel.add(charSelectPanel, gbc_charSelectPanel);
		//charSelectPanel.setLayout(new CardLayout(0, 0));

		
		JPanel teamPanel = new JPanel();
		cardPanel.add(teamPanel, "teamPanel");
		teamPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTeamSelection = new JLabel("Team Selection");
		lblTeamSelection.setFont(new Font("Super Black SF", Font.PLAIN, 20));
		lblTeamSelection.setHorizontalAlignment(SwingConstants.CENTER);
		teamPanel.add(lblTeamSelection, BorderLayout.NORTH);
		
		JPanel teamSelectionWindowPanel = new JPanel();
		teamPanel.add(teamSelectionWindowPanel, BorderLayout.CENTER);
		GridBagLayout gbl_teamSelectionWindowPanel = new GridBagLayout();
		gbl_teamSelectionWindowPanel.columnWidths = new int[]{0, 0, 0};
		gbl_teamSelectionWindowPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_teamSelectionWindowPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_teamSelectionWindowPanel.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		teamSelectionWindowPanel.setLayout(gbl_teamSelectionWindowPanel);
		
		TeamSelector teamSelectorPanel = new TeamSelector();
		GridBagConstraints gbc_teamSelectorPanel = new GridBagConstraints();
		gbc_teamSelectorPanel.gridheight = 3;
		gbc_teamSelectorPanel.insets = new Insets(0, 0, 5, 0);
		gbc_teamSelectorPanel.fill = GridBagConstraints.BOTH;
		gbc_teamSelectorPanel.gridx = 1;
		gbc_teamSelectorPanel.gridy = 0;
		teamSelectionWindowPanel.add(teamSelectorPanel, gbc_teamSelectorPanel);
		
		JButton gameButton_teamselect = new JButton("Play Game!");
		GridBagConstraints gbc_btnPlayGame = new GridBagConstraints();
		gbc_btnPlayGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayGame.gridx = 0;
		gbc_btnPlayGame.gridy = 1;
		teamSelectionWindowPanel.add(gameButton_teamselect, gbc_btnPlayGame);
		gameButton_teamselect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(cardPanel, "gamePanel");
				initPanel.renewTeams();
			}
			
		});

		
		JButton btnMenu = new JButton("Menu");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.first(cardPanel);
			}
		});
		GridBagConstraints gbc_btnMenu = new GridBagConstraints();
		gbc_btnMenu.insets = new Insets(0, 0, 0, 5);
		gbc_btnMenu.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMenu.gridx = 0;
		gbc_btnMenu.gridy = 2;
		teamSelectionWindowPanel.add(btnMenu, gbc_btnMenu);
		
		JPanel directionsPanel = new JPanel();
		cardPanel.add(directionsPanel, "directionsPanel");
		GridBagLayout gbl_directionsPanel = new GridBagLayout();
		gbl_directionsPanel.columnWidths = new int[]{0, 974, 0};
		gbl_directionsPanel.rowHeights = new int[]{752, 0, 0};
		gbl_directionsPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_directionsPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		directionsPanel.setLayout(gbl_directionsPanel);

		
		JButton menuButton_Directions = new JButton("Menu");
		menuButton_Directions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.first(cardPanel);
			}
		});
		GridBagConstraints gbc_menuButton_Directions = new GridBagConstraints();
		gbc_menuButton_Directions.anchor = GridBagConstraints.SOUTH;
		gbc_menuButton_Directions.insets = new Insets(0, 0, 5, 5);
		gbc_menuButton_Directions.gridx = 0;
		gbc_menuButton_Directions.gridy = 0;
		directionsPanel.add(menuButton_Directions, gbc_menuButton_Directions);
	
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 0;
		directionsPanel.add(scrollPane_1, gbc_scrollPane_1);
		
		JTextPane instructionsPane = new JTextPane();
		scrollPane_1.setViewportView(instructionsPane);
		try {
			instructionsPane.read(new BufferedReader(new FileReader(new File("instructions.txt"))), new PlainDocument());
		} catch (IOException e1) {
			// 
			e1.printStackTrace();
		}
		
		teamsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(cardPanel, "teamPanel");
				teamSelectorPanel.updateCharList();
			}
		});
		pack();
	}
	public JPanel getGamePanel() {
		return gamePanel;
	}

	public JPanel getCharPanel() {
		return charPanel;
	}
	public void setCL_Game(String s) {
		cl_game.show(gameCardPanel, s);
	}
}
