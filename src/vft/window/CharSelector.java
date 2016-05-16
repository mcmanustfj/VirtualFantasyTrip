package vft.window;

import javax.swing.JPanel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import vft.VFTChar;

import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class CharSelector extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 586032913358376935L;
	/**
	 * Create the panel.
	 */
	List<VFTChar> charList;
	JList<VFTChar> list;

	public CharSelector() {

		charList = new ArrayList<>();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);


		list = new JList<VFTChar>(makeList());
		list.setLayoutOrientation(JList.VERTICAL);
		list.setCellRenderer(new CharListRenderer<VFTChar>());
		//		list.setSelectionBackground(Color.BLUE);
		//		list.setSelectionForeground(Color.WHITE);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.gridheight = 6;
		gbc_list.gridwidth = 9;
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		add(list, gbc_list);



		JButton btnNewCharacter = new JButton("New Character");
		btnNewCharacter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame charEditor = new CharEditor(true,CharSelector.this);
				charEditor.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnNewCharacter = new GridBagConstraints();
		gbc_btnNewCharacter.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewCharacter.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewCharacter.gridx = 9;
		gbc_btnNewCharacter.gridy = 1;
		add(btnNewCharacter, gbc_btnNewCharacter);

		JButton btnEditCharacter = new JButton("Edit Character");
		btnEditCharacter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnEditCharacter.setEnabled(false);
		GridBagConstraints gbc_btnEditCharacter = new GridBagConstraints();
		gbc_btnEditCharacter.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEditCharacter.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditCharacter.gridx = 9;
		gbc_btnEditCharacter.gridy = 2;
		add(btnEditCharacter, gbc_btnEditCharacter);

		JButton btnDeleteCharacter = new JButton("Delete Character");
		btnDeleteCharacter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = list.getSelectedValue().getName();
				File dir = new File("chars" + File.separator);
				File f = new File(dir, name+".vftchar");
				try {
					Files.delete(f.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
				((DefaultListModel<VFTChar>)list.getModel()).removeElement(list.getSelectedValue());


			}

		});
		GridBagConstraints gbc_btnDeleteCharacter = new GridBagConstraints();
		gbc_btnDeleteCharacter.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeleteCharacter.gridx = 9;
		gbc_btnDeleteCharacter.gridy = 3;
		add(btnDeleteCharacter, gbc_btnDeleteCharacter);

	}

	private DefaultListModel<VFTChar> makeList() {
		List<VFTChar> list = new ArrayList<>();
		File folder = new File("chars" + File.separator);
		File[] listOfFiles = folder.listFiles();
		try{
			Gson gson = new GsonBuilder().create();
			for(File f : listOfFiles) {
				if(!f.getName().substring(f.getName().length()-7).equals("vftchar")) continue;
				try {
					byte[] encoded = Files.readAllBytes(f.toPath());
					String json = new String(encoded, "UTF-8");

					list.add(gson.fromJson(json, VFTChar.class));
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
		} catch (NullPointerException e) {

		}
		charList = list;
		DefaultListModel<VFTChar> model = new DefaultListModel<>();
		for(VFTChar c : charList) {
			model.addElement(c);
		}
		return model;

	}
	public void updateList() {
		list.setModel(makeList());
	}

}
