package vft.window;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import vft.GameChar;
import vft.VFTTeam;
import vft.map.Layout;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JComboBox;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class GameInit extends JPanel {

	
	private GamePanel gamePanel;
	private JList<GameChar> infoPanelList;
	private VFTWindow window;
	/**
	 * Create the panel.
	 */
	public GameInit(VFTWindow window) {
		this.window = window;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblNewGame = new JLabel("New Game");
		lblNewGame.setFont(new Font("Super Black SF", Font.PLAIN, 15));
		add(lblNewGame);
		
		JPanel panel = new JPanel();
		add(panel);
		
		JLabel lblMap = new JLabel("Map: ");
		lblMap.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JComboBox<Layout> comboBoxMap = new JComboBox();
		comboBoxMap.setEnabled(false);
		
		JLabel lblTeam1 = new JLabel("Team 1:");
		lblTeam1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JComboBox<VFTTeam> comboBoxTeam1 = new JComboBox<>();
		
		
		
		JLabel lblTeam2 = new JLabel("Team 2:");
		lblTeam2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JComboBox<VFTTeam> comboBoxTeam2 = new JComboBox<>();
		comboBoxTeam2.setPreferredSize(new Dimension(100, 20));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(103)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblTeam2)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBoxTeam2, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblTeam1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBoxTeam1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblMap)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxMap, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(93, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBoxMap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMap))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTeam1)
						.addComponent(comboBoxTeam1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTeam2)
						.addComponent(comboBoxTeam2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(164, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		DefaultComboBoxModel<VFTTeam> team1ListModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<VFTTeam> team2ListModel = new DefaultComboBoxModel<>();
		
		comboBoxTeam1.setModel(team1ListModel);
		comboBoxTeam2.setModel(team2ListModel);
		
		comboBoxTeam1.setRenderer(new TeamListRenderer<VFTTeam>());
		comboBoxTeam2.setRenderer(new TeamListRenderer<VFTTeam>());
		File folder = new File("teams/");

		try {
			File[] listOfFiles = folder.listFiles();


			Gson gson = new GsonBuilder().create();
			for(File f : listOfFiles) {
				if(!f.getName().substring(f.getName().length()-7).equals("vftteam")) continue;
				try {
					byte[] encoded = Files.readAllBytes(f.toPath());
					String json = new String(encoded, "UTF-8");

					VFTTeam team = gson.fromJson(json, VFTTeam.class);
					team1ListModel.addElement(team);
					team2ListModel.addElement(team);
					//teams.add(team);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (JsonIOException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch(NullPointerException e) {

			e.printStackTrace();
			System.out.println("No teams");
		}
		
		
		
		JButton btnNewButton = new JButton("Start Game");
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePanel.setTeams(new VFTTeam[]{(VFTTeam) comboBoxTeam1.getSelectedItem(), (VFTTeam) comboBoxTeam2.getSelectedItem()});
				gamePanel.setMap(Layout.genPgram(3));
				gamePanel.setCharacters();
				gamePanel.setInfoPanelList(infoPanelList);
				window.setCL_Game("gameWindow");

				gamePanel.startGame();
				
			}
		});
		btnNewButton.setEnabled(false);
		add(btnNewButton);

		ItemListener teamsIL = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				btnNewButton.setEnabled(!(comboBoxTeam1.getSelectedItem().equals(comboBoxTeam2.getSelectedItem())));
				
			}
			
		};
		comboBoxTeam2.addItemListener(teamsIL);
		comboBoxTeam1.addItemListener(teamsIL);
		comboBoxTeam2.setSelectedIndex(1);
	}
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	public void setInfoPanelList(JList<GameChar> list) {
		this.infoPanelList = list;
	}
	

}
