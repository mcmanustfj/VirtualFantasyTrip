package vft.window;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import vft.VFTChar;
import vft.VFTTeam;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TeamSelector extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7098049187615918601L;
	private JTextField txtTeamName;
	private JList<VFTTeam> teamList;
	private JList<VFTChar> teamCharList;
	private JList<VFTChar> availableCharList;

	private ArrayList<VFTTeam> teams;
	private ArrayList<VFTChar> chars;
	private JList<VFTChar> charInfoPanels;

	/**
	 * Create the panel.
	 */
	public TeamSelector() {

		teams = new ArrayList<>();
		chars = new ArrayList<>();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblTeams = new JLabel("Teams");
		GridBagConstraints gbc_lblTeams = new GridBagConstraints();
		gbc_lblTeams.insets = new Insets(0, 0, 5, 5);
		gbc_lblTeams.gridx = 1;
		gbc_lblTeams.gridy = 0;
		add(lblTeams, gbc_lblTeams);

		teamList = new JList<VFTTeam>();
		GridBagConstraints gbc_teamList = new GridBagConstraints();
		gbc_teamList.gridheight = 2;
		gbc_teamList.insets = new Insets(0, 0, 5, 5);
		gbc_teamList.fill = GridBagConstraints.BOTH;
		gbc_teamList.gridx = 1;
		gbc_teamList.gridy = 1;
		add(teamList, gbc_teamList);

		DefaultListModel<VFTTeam> teamListModel = new DefaultListModel<VFTTeam>();

		File folder = new File("teams" + File.separator);

		try {
			File[] listOfFiles = folder.listFiles();


			Gson gson = new GsonBuilder().create();
			for(File f : listOfFiles) {
				if(!f.getName().substring(f.getName().length()-7).equals("vftteam")) continue;
				try {
					byte[] encoded = Files.readAllBytes(f.toPath());
					String json = new String(encoded, "UTF-8");

					VFTTeam team =gson.fromJson(json, VFTTeam.class);
					teamListModel.addElement(team);
					teams.add(team);
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
		teamList.setModel(teamListModel);
		teamList.setCellRenderer(new TeamListRenderer<VFTTeam>());
		teamList.setSelectedIndex(0);
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JButton btnNewTeam = new JButton("New Team");
		btnNewTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtTeamName.getText().equals("new team name") || txtTeamName.getText().equals(""))
					return;
				for(VFTTeam t : teams) {
					if (txtTeamName.getText().equals(t.getName())) return;
				}
				VFTTeam newTeam = new VFTTeam(txtTeamName.getText());
				try{
					File dir = new File("teams" + File.separator);
					dir.mkdirs();
					File save = new File(dir, txtTeamName.getText() + ".vftteam");
					if(!save.exists()) save.createNewFile();
					PrintWriter writer = new PrintWriter(save);

					GsonBuilder builder = new GsonBuilder();
					builder.setPrettyPrinting().serializeNulls();
					Gson gson = builder.create();

					String json = gson.toJson(newTeam);
					writer.print(json);
					writer.close();
				} catch(IOException ex) {
					ex.printStackTrace();
					return;
				}
				teamListModel.addElement(newTeam);
				teams.add(newTeam);

			}
		});
		GridBagConstraints gbc_btnNewTeam = new GridBagConstraints();
		gbc_btnNewTeam.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewTeam.gridx = 2;
		gbc_btnNewTeam.gridy = 1;
		add(btnNewTeam, gbc_btnNewTeam);

		JButton btnDeleteTeam = new JButton("Delete Team");
		btnDeleteTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = teamList.getSelectedValue().getName();
				File dir = new File("teams" + File.separator);
				File f = new File(dir, name+".vftteam");
				teams.remove(teamList.getSelectedValue());
				try {
					Files.delete(f.toPath());
				} catch (IOException ex1) {
					ex1.printStackTrace();
				}
				teamListModel.removeElement(teamList.getSelectedValue());
				teamList.setSelectedIndex(0);
			}
		});

		txtTeamName = new JTextField();
		txtTeamName.setText("new team name");
		GridBagConstraints gbc_txtTeamName = new GridBagConstraints();
		gbc_txtTeamName.anchor = GridBagConstraints.NORTH;
		gbc_txtTeamName.insets = new Insets(0, 0, 5, 5);
		gbc_txtTeamName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTeamName.gridx = 3;
		gbc_txtTeamName.gridy = 1;
		add(txtTeamName, gbc_txtTeamName);
		txtTeamName.setColumns(10);
		txtTeamName.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				txtTeamName.selectAll();
			}

			@Override
			public void focusLost(FocusEvent arg0) {}

		});

		GridBagConstraints gbc_btnDeleteTeam = new GridBagConstraints();
		gbc_btnDeleteTeam.anchor = GridBagConstraints.NORTH;
		gbc_btnDeleteTeam.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeleteTeam.gridx = 2;
		gbc_btnDeleteTeam.gridy = 2;
		add(btnDeleteTeam, gbc_btnDeleteTeam);


		JLabel lblAvailableCharacters = new JLabel("Available Characters");
		GridBagConstraints gbc_lblAvailableCharacters = new GridBagConstraints();
		gbc_lblAvailableCharacters.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvailableCharacters.gridx = 1;
		gbc_lblAvailableCharacters.gridy = 4;
		add(lblAvailableCharacters, gbc_lblAvailableCharacters);

		JLabel lblCharactersOnTeam = new JLabel("Characters on Team");
		GridBagConstraints gbc_lblCharactersOnTeam = new GridBagConstraints();
		gbc_lblCharactersOnTeam.insets = new Insets(0, 0, 5, 5);
		gbc_lblCharactersOnTeam.gridx = 3;
		gbc_lblCharactersOnTeam.gridy = 4;
		add(lblCharactersOnTeam, gbc_lblCharactersOnTeam);


		availableCharList = new JList<VFTChar>();
		GridBagConstraints gbc_availableCharList = new GridBagConstraints();
		gbc_availableCharList.gridheight = 2;
		gbc_availableCharList.insets = new Insets(0, 0, 5, 5);
		gbc_availableCharList.fill = GridBagConstraints.BOTH;
		gbc_availableCharList.gridx = 1;
		gbc_availableCharList.gridy = 5;
		add(availableCharList, gbc_availableCharList);

		availableCharList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		availableCharList.setCellRenderer(new CharListRenderer<VFTChar>());






		teamCharList = new JList<VFTChar>();
		GridBagConstraints gbc_teamCharList = new GridBagConstraints();
		gbc_teamCharList.insets = new Insets(0, 0, 5, 5);
		gbc_teamCharList.gridheight = 2;
		gbc_teamCharList.fill = GridBagConstraints.BOTH;
		gbc_teamCharList.gridx = 3;
		gbc_teamCharList.gridy = 5;
		add(teamCharList, gbc_teamCharList);

		teamCharList.setCellRenderer(new CharListRenderer<VFTChar>());
		teamCharList.setModel(new DefaultListModel<VFTChar>());
		teamCharList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		teamList.addListSelectionListener(new ListSelectionListener() {


			@Override
			public void valueChanged(ListSelectionEvent e) {

				DefaultListModel<VFTChar> teamCharListModel = new DefaultListModel<VFTChar>();

				if(e.getValueIsAdjusting()) return;
				VFTTeam team = teamList.getSelectedValue();
				try{
					if(team.getChars().isEmpty()){
						teamCharListModel.removeAllElements();

					} else{
						for(VFTChar c : team.getChars().toArray(new VFTChar[1])) teamCharListModel.addElement(c);
					}	
				} catch(NullPointerException ex) {
					System.err.println("Either something was just deleted or there's something very wrong");

				}
				teamCharList.setModel(teamCharListModel);

			}



		});
		JButton addCharButton = new JButton(">");
		addCharButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VFTTeam team = teamList.getSelectedValue();
				if(availableCharList.getSelectedValue() == null) return;
				VFTChar character = availableCharList.getSelectedValue();
				team.addChar(character);
				((DefaultListModel<VFTChar>)teamCharList.getModel()).addElement(character);
				updateCharList();
				saveTeam(team);
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 2;
		gbc_button.gridy = 5;
		add(addCharButton, gbc_button);

		JButton delCharButton = new JButton("<");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 2;
		gbc_button_1.gridy = 6;
		add(delCharButton, gbc_button_1);
		delCharButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VFTTeam team = teamList.getSelectedValue();
				if(team.getChars().isEmpty()) return;
				VFTChar character = teamCharList.getSelectedValue();
				team.delChar(character);
				((DefaultListModel<VFTChar>)teamCharList.getModel()).removeElement(character);
				updateCharList();
				saveTeam(team);
			}
		});
		
		charInfoPanels = new JList<>();
		charInfoPanels.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					VFTTeam team = teamList.getSelectedValue();
					if(charInfoPanels.getSelectedValue() == null) return;
					if(!isAvailable(charInfoPanels.getSelectedValue())) return;
					VFTChar character = charInfoPanels.getSelectedValue();
					team.addChar(character);
					((DefaultListModel<VFTChar>)teamCharList.getModel()).addElement(character);
					updateCharList();
					saveTeam(team);
				}
			}
		});
		charInfoPanels.setFocusTraversalKeysEnabled(false);
		GridBagConstraints gbc_charInfoPanels = new GridBagConstraints();
		gbc_charInfoPanels.gridwidth = 3;
		gbc_charInfoPanels.gridheight = 2;
		gbc_charInfoPanels.insets = new Insets(0, 0, 5, 5);
		gbc_charInfoPanels.fill = GridBagConstraints.BOTH;
		gbc_charInfoPanels.gridx = 1;
		gbc_charInfoPanels.gridy = 7;
		//add(charInfoPanels, gbc_charInfoPanels);
		charInfoPanels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		charInfoPanels.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		charInfoPanels.setVisibleRowCount(1);
		charInfoPanels.setCellRenderer(new VFTCharInfoPanelRenderer<VFTChar>());
		
		JScrollPane infoScrollPane = new JScrollPane(charInfoPanels, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		infoScrollPane.setPreferredSize(new Dimension(2, 130));
		add(infoScrollPane, gbc_charInfoPanels);
		
	}
	boolean isAvailable(VFTChar c) {
		if(!c.isUnique()) return true;
		for(VFTTeam team : teams) {
			if (team.contains(c)) 
				return false;
		}
		return true;
	}
	public void updateCharList() {
		chars = new ArrayList<>();

		File folder1 = new File("chars"+File.separator);
		File[] listOfFiles = folder1.listFiles();

		Gson gson = new GsonBuilder().create();
		for(File f : listOfFiles) {
			if(!f.getName().substring(f.getName().length()-7).equals("vftchar")) continue;
			try {
				byte[] encoded = Files.readAllBytes(f.toPath());
				String json = new String(encoded, "UTF-8");

				chars.add(gson.fromJson(json, VFTChar.class));
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			DefaultListModel<VFTChar> teamCharListModel = new DefaultListModel<VFTChar>();
			VFTTeam team = teamList.getSelectedValue();
			try{
				if(team.getChars().isEmpty()){
					System.out.println("Empty");
					teamCharListModel.removeAllElements();

				} else{
					System.out.println("Not Empty");
					for(VFTChar c : team.getChars().toArray(new VFTChar[1])) teamCharListModel.addElement(c);
				}	
			} catch(NullPointerException ex) {
				System.err.println("Either something was just deleted or there's something very wrong");

			}
			teamCharList.setModel(teamCharListModel);

		}
		DefaultListModel<VFTChar> availableCharListModel = new DefaultListModel<VFTChar>();
		DefaultListModel<VFTChar> charListModel = new DefaultListModel<VFTChar>();
		for(VFTChar character : chars) {
			charListModel.addElement(character);
			if(isAvailable(character))
				availableCharListModel.addElement(character);
		}
		availableCharList.setModel(availableCharListModel);
		charInfoPanels.setModel(charListModel);
	}
	public void holdonasecond(int i) throws InterruptedException {
		Thread.sleep(i);

	}
	public void saveTeam(VFTTeam team) {
		try{
			File dir = new File("teams" + File.separator);
			dir.mkdirs();
			File save = new File(dir, team.getName() + ".vftteam");
			if(!save.exists()) save.createNewFile();
			PrintWriter writer = new PrintWriter(save);

			GsonBuilder builder = new GsonBuilder();
			builder.setPrettyPrinting().serializeNulls();
			Gson gson = builder.create();

			String json = gson.toJson(team);
			writer.print(json);
			writer.close();
		} catch(IOException ex) {
			ex.printStackTrace();
			return;
		}
	}

}

