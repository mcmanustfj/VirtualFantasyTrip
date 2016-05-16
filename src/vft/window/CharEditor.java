package vft.window;

import com.google.gson.*;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.ListCellRenderer;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import vft.Armor;
import vft.MeleeWeapon;
import vft.VFTChar;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JCheckBox;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;

public class CharEditor extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7668714970904692309L;

	private JTextField nameField;

	private int pointsRemaining_initial;
	private int pointsST_initial;
	private int pointsDX_initial;
	private int pointsIQ_initial;
	private int pointsRemaining;
	private int pointsST;
	private int pointsDX;
	private int pointsIQ;

	private JLabel lblPoints;
	private JLabel lblStPoints;
	private JLabel lblDxPoints;
	private JLabel lblIqPoints;

	private JComboBox<MeleeWeapon> comboBox_weapon;

	private JComboBox<Armor> comboBox_armor;
	/**
	 * Create the panel.
	 */
	CharSelector parent;

	private JCheckBox chckbxStatic;
	@SuppressWarnings("rawtypes")
	public CharEditor(boolean isNew, CharSelector charSelector) {
		parent = charSelector;
		setPreferredSize(new Dimension(315, 455));
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		
		if(isNew) {
			pointsRemaining_initial = 8;
			pointsST_initial = 8;
			pointsDX_initial = 8;
			pointsIQ_initial = 8;
			
		}
		pointsRemaining = pointsRemaining_initial;
		pointsST = pointsST_initial;
		pointsDX = pointsDX_initial;
		pointsIQ = pointsIQ_initial;
		
		setTitle("Character Editor");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblCharacterEditor = DefaultComponentFactory.getInstance().createTitle("Character Editor");
		lblCharacterEditor.setFont(new Font("Super Black SF", Font.PLAIN, 17));
		GridBagConstraints gbc_lblCharacterEditor = new GridBagConstraints();
		gbc_lblCharacterEditor.gridwidth = 4;
		gbc_lblCharacterEditor.anchor = GridBagConstraints.SOUTH;
		gbc_lblCharacterEditor.insets = new Insets(5, 5, 5, 0);
		gbc_lblCharacterEditor.gridx = 0;
		gbc_lblCharacterEditor.gridy = 0;
		contentPane.add(lblCharacterEditor, gbc_lblCharacterEditor);
		
		JLabel lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 1;
		contentPane.add(lblName, gbc_lblName);
		
		nameField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		contentPane.add(nameField, gbc_textField);
		nameField.setColumns(10);
		
		JLabel lblRace = new JLabel("Race");
		GridBagConstraints gbc_lblRace = new GridBagConstraints();
		gbc_lblRace.anchor = GridBagConstraints.EAST;
		gbc_lblRace.insets = new Insets(0, 0, 5, 5);
		gbc_lblRace.gridx = 1;
		gbc_lblRace.gridy = 2;
		contentPane.add(lblRace, gbc_lblRace);
		
		JComboBox<?> comboBox_Race = new JComboBox<Object>();
		comboBox_Race.setEnabled(false);
		GridBagConstraints gbc_comboBox_Race = new GridBagConstraints();
		gbc_comboBox_Race.gridwidth = 2;
		gbc_comboBox_Race.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_Race.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_Race.gridx = 2;
		gbc_comboBox_Race.gridy = 2;
		contentPane.add(comboBox_Race, gbc_comboBox_Race);
		
		JLabel lblPointsLeft = new JLabel("Points left:");
		GridBagConstraints gbc_lblPointsLeft = new GridBagConstraints();
		gbc_lblPointsLeft.insets = new Insets(0, 0, 5, 5);
		gbc_lblPointsLeft.gridx = 1;
		gbc_lblPointsLeft.gridy = 3;
		contentPane.add(lblPointsLeft, gbc_lblPointsLeft);
		
		lblPoints = new JLabel(""+pointsRemaining_initial);
		GridBagConstraints gbc_lblPoints = new GridBagConstraints();
		gbc_lblPoints.insets = new Insets(0, 0, 5, 5);
		gbc_lblPoints.gridx = 2;
		gbc_lblPoints.gridy = 3;
		contentPane.add(lblPoints, gbc_lblPoints);
		
		JLabel lblSt = new JLabel("ST:");
		GridBagConstraints gbc_lblSt = new GridBagConstraints();
		gbc_lblSt.insets = new Insets(0, 0, 5, 5);
		gbc_lblSt.gridx = 1;
		gbc_lblSt.gridy = 4;
		contentPane.add(lblSt, gbc_lblSt);
		
		lblStPoints = new JLabel(""+pointsST_initial);
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 4;
		contentPane.add(lblStPoints, gbc_label_1);
		
		JPanel plusminusST = new JPanel();
		GridBagConstraints gbc_plusminusST = new GridBagConstraints();
		gbc_plusminusST.insets = new Insets(0, 0, 5, 5);
		gbc_plusminusST.fill = GridBagConstraints.BOTH;
		gbc_plusminusST.gridx = 3;
		gbc_plusminusST.gridy = 4;
		contentPane.add(plusminusST, gbc_plusminusST);
		plusminusST.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton plusST = new JButton("+");
		plusST.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pointsRemaining>0) {
					pointsST++;
					pointsRemaining--;
				}
				update();
			}
		});
		plusminusST.add(plusST);
		
		JButton minusST = new JButton("-");
		minusST.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pointsRemaining_initial>pointsRemaining && pointsST > pointsST_initial) {
					pointsST--;
					pointsRemaining++;
				}
				update();
			}
		});
		plusminusST.add(minusST);
		
		JLabel lblDx = new JLabel("DX:");
		GridBagConstraints gbc_lblDx = new GridBagConstraints();
		gbc_lblDx.insets = new Insets(0, 0, 5, 5);
		gbc_lblDx.gridx = 1;
		gbc_lblDx.gridy = 5;
		contentPane.add(lblDx, gbc_lblDx);
		
		lblDxPoints = new JLabel(""+pointsDX_initial);
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 2;
		gbc_label_2.gridy = 5;
		contentPane.add(lblDxPoints, gbc_label_2);
		
		JPanel plusminusDX = new JPanel();
		GridBagConstraints gbc_plusminusDX = new GridBagConstraints();
		gbc_plusminusDX.insets = new Insets(0, 0, 5, 5);
		gbc_plusminusDX.fill = GridBagConstraints.BOTH;
		gbc_plusminusDX.gridx = 3;
		gbc_plusminusDX.gridy = 5;
		contentPane.add(plusminusDX, gbc_plusminusDX);
		plusminusDX.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton plusDX = new JButton("+");
		plusDX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pointsRemaining>0) {
					pointsDX++;
					pointsRemaining--;
				}
				update();
			}
		});
		plusminusDX.add(plusDX);
		
		JButton minusDX = new JButton("-");
		minusDX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pointsRemaining_initial>pointsRemaining && pointsDX > pointsDX_initial) {
					pointsDX--;
					pointsRemaining++;
				}
				update();
			}
		});
		plusminusDX.add(minusDX);
		
		JLabel lblIq = new JLabel("IQ:");
		GridBagConstraints gbc_lblIq = new GridBagConstraints();
		gbc_lblIq.insets = new Insets(0, 0, 5, 5);
		gbc_lblIq.gridx = 1;
		gbc_lblIq.gridy = 6;
		contentPane.add(lblIq, gbc_lblIq);
		
		lblIqPoints = new JLabel(""+pointsIQ_initial);
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 6;
		contentPane.add(lblIqPoints, gbc_label_3);
		
		JPanel plusminusIQ = new JPanel();
		GridBagConstraints gbc_plusminusIQ = new GridBagConstraints();
		gbc_plusminusIQ.insets = new Insets(0, 0, 5, 5);
		gbc_plusminusIQ.fill = GridBagConstraints.BOTH;
		gbc_plusminusIQ.gridx = 3;
		gbc_plusminusIQ.gridy = 6;
		contentPane.add(plusminusIQ, gbc_plusminusIQ);
		plusminusIQ.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton plusIQ = new JButton("+");
		plusIQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pointsRemaining>0) {
					pointsIQ++;
					pointsRemaining--;
				}
				update();
			}
		});
		plusminusIQ.add(plusIQ);
		
		JButton minusIQ = new JButton("-");
		minusIQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pointsRemaining_initial>pointsRemaining && pointsIQ > pointsIQ_initial) {
					pointsIQ--;
					pointsRemaining++;
				}
				update();
			}
		});
		plusminusIQ.add(minusIQ);
		
		JLabel lblWeapon = new JLabel("Weapon:");
		GridBagConstraints gbc_lblWeapon = new GridBagConstraints();
		gbc_lblWeapon.anchor = GridBagConstraints.EAST;
		gbc_lblWeapon.insets = new Insets(0, 0, 5, 5);
		gbc_lblWeapon.gridx = 1;
		gbc_lblWeapon.gridy = 8;
		contentPane.add(lblWeapon, gbc_lblWeapon);
		
		comboBox_weapon = new JComboBox<MeleeWeapon>(MeleeWeapon.values());
		//comboBox_weapon.setEnabled(false);
		GridBagConstraints gbc_comboBox_weapon = new GridBagConstraints();
		gbc_comboBox_weapon.gridwidth = 2;
		gbc_comboBox_weapon.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_weapon.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_weapon.gridx = 2;
		gbc_comboBox_weapon.gridy = 8;
		contentPane.add(comboBox_weapon, gbc_comboBox_weapon);
		
		comboBox_weapon.setRenderer(new WeaponRenderer<MeleeWeapon>());
		
		JLabel lblArmor = new JLabel("Armor:");
		GridBagConstraints gbc_lblArmor = new GridBagConstraints();
		gbc_lblArmor.anchor = GridBagConstraints.EAST;
		gbc_lblArmor.insets = new Insets(0, 0, 5, 5);
		gbc_lblArmor.gridx = 1;
		gbc_lblArmor.gridy = 9;
		contentPane.add(lblArmor, gbc_lblArmor);
		
		comboBox_armor = new JComboBox<Armor>(Armor.values());
		//comboBox_armor.setEnabled(false);
		GridBagConstraints gbc_comboBox_armor = new GridBagConstraints();
		gbc_comboBox_armor.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_armor.gridwidth = 2;
		gbc_comboBox_armor.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_armor.gridx = 2;
		gbc_comboBox_armor.gridy = 9;
		contentPane.add(comboBox_armor, gbc_comboBox_armor);
		comboBox_armor.setRenderer(new ArmorRenderer<Armor>());
		
		JLabel lblSpells = new JLabel("Spells:");
		GridBagConstraints gbc_lblSpells = new GridBagConstraints();
		gbc_lblSpells.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpells.gridx = 1;
		gbc_lblSpells.gridy = 10;
		contentPane.add(lblSpells, gbc_lblSpells);
		
		JList<?> spellList = new JList<Object>();
		spellList.setEnabled(false);
		GridBagConstraints gbc_spellList = new GridBagConstraints();
		gbc_spellList.gridwidth = 2;
		gbc_spellList.insets = new Insets(0, 0, 5, 5);
		gbc_spellList.fill = GridBagConstraints.BOTH;
		gbc_spellList.gridx = 2;
		gbc_spellList.gridy = 10;
		contentPane.add(spellList, gbc_spellList);
		
		JButton btnAddSpell = new JButton("Add");
		btnAddSpell.setEnabled(false);
		btnAddSpell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_btnAddSpell = new GridBagConstraints();
		gbc_btnAddSpell.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddSpell.gridx = 2;
		gbc_btnAddSpell.gridy = 11;
		contentPane.add(btnAddSpell, gbc_btnAddSpell);
		
		JButton btnRemoveSpell = new JButton("Remove");
		btnRemoveSpell.setEnabled(false);
		GridBagConstraints gbc_btnRemoveSpell = new GridBagConstraints();
		gbc_btnRemoveSpell.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveSpell.gridx = 3;
		gbc_btnRemoveSpell.gridy = 11;
		contentPane.add(btnRemoveSpell, gbc_btnRemoveSpell);
		
		chckbxStatic = new JCheckBox("Static (NPC)");
		GridBagConstraints gbc_chckbxStaticnpc = new GridBagConstraints();
		gbc_chckbxStaticnpc.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxStaticnpc.gridx = 2;
		gbc_chckbxStaticnpc.gridy = 12;
		contentPane.add(chckbxStatic, gbc_chckbxStaticnpc);
		
		JButton btnSaveCharacter = new JButton("Save Character");
		GridBagConstraints gbc_btnSaveCharacter = new GridBagConstraints();
		gbc_btnSaveCharacter.gridx = 3;
		gbc_btnSaveCharacter.gridy = 12;
		btnSaveCharacter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
			
		});
		contentPane.add(btnSaveCharacter, gbc_btnSaveCharacter);
		
		pack();

	}
	private void update() {
		lblPoints.setText(""+pointsRemaining);
		lblStPoints.setText(""+pointsST);
		lblDxPoints.setText(""+pointsDX);
		lblIqPoints.setText(""+pointsIQ);
	}
	private void save() {
		if(nameField.getText().equals("")) {
			this.dispose();
			return;
		}
		else if(((MeleeWeapon)comboBox_weapon.getSelectedItem()).st > pointsST) {
			return;
		}
		VFTChar character = new VFTChar(nameField.getText(), pointsST, pointsDX, pointsIQ, pointsRemaining, 
				(MeleeWeapon)comboBox_weapon.getSelectedItem(), (Armor)comboBox_armor.getSelectedItem(), !chckbxStatic.isSelected());
		try{
		File dir = new File("chars" + File.separator);
		dir.mkdirs();
		File save = new File(dir, nameField.getText() + ".vftchar");
		if(!save.exists()) save.createNewFile();
		PrintWriter writer = new PrintWriter(save);
		
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		
		
		
		String json = gson.toJson(character);
		writer.print(json);
		writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		this.parent.charList.add(character);
		((DefaultListModel<VFTChar>)parent.list.getModel()).addElement(character);
		
		this.dispose();
	}
	
	@SuppressWarnings("unused")
	private void load(String name) {
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CharEditor frame = new CharEditor(true, null);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	class WeaponRenderer<T extends MeleeWeapon> extends JPanel implements ListCellRenderer<T> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1098621963643434219L;

		public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if(isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			JLabel nameLabel = new JLabel(value.name());
			JLabel attributes = new JLabel();
			String text = String.format("min ST: %s  Damage: %dd%+d", value.st, value.dice, value.mod);
			attributes.setText(text);
			removeAll();
			setLayout(new BorderLayout());
			this.add(nameLabel, BorderLayout.CENTER);
			this.add(attributes, BorderLayout.LINE_END);
			setOpaque(true);

			return this;
		}
	}
	class ArmorRenderer<T extends Armor> extends JPanel implements ListCellRenderer<T> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1098621963643434219L;

		public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if(isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			JLabel nameLabel = new JLabel(value.name());
			JLabel attributes = new JLabel();
			String text = String.format("Hits: %d  Mod: -%d", value.hits, value.mod);
			attributes.setText(text);
			removeAll();
			setLayout(new BorderLayout());
			this.add(nameLabel, BorderLayout.CENTER);
			this.add(attributes, BorderLayout.LINE_END);
			setOpaque(true);

			return this;
		}
	}

}
