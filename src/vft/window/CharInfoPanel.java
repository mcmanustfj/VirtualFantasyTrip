package vft.window;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import vft.GameChar;

import java.awt.BasicStroke;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class CharInfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -793771527977474262L;

	/**
	 * Create the panel.
	 */
	private boolean isSelected;
	private GameChar character;

	private JLabel lblHP;

	private JLabel lblAttributes;

	private JLabel lblArmor;

	private JLabel lblWeapon;

	private JLabel lblName;

	private String attributes;

	private JPanel healthbar;
	public CharInfoPanel(GameChar c) {
		setBackgroundColor(new Color(211, 211, 211));
		character = c;
		setPreferredSize(new Dimension(134, 134));
		
		lblName = new JLabel(character.getChar().getName());
		lblName.setFont(new Font("Wicker SF", Font.PLAIN, 16));
		
		healthbar = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.red);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.green);
				g.fillRect(0, 0, this.getWidth() * character.getCurrHP() / character.getChar().getSt(), this.getHeight());
			}
		};
		
		lblHP = new JLabel(character.getCurrHP() + "/" + character.getChar().getSt());
		lblHP.setVerticalAlignment(SwingConstants.TOP);
		
		attributes = String.format("ST%2d DX%2d IQ%2d MP%2d/%2d", character.getChar().getSt(), character.getChar().getDx(),
				character.getChar().getIq(), character.getMoves(), character.getChar().getMoves());
		lblAttributes = new JLabel(attributes);
		
		lblWeapon = new JLabel(character.getChar().getWeapon().name());
		
		lblArmor = new JLabel(character.getChar().getArmor().name());
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(healthbar, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblHP))
						.addComponent(lblName, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
						.addComponent(lblAttributes)
						.addComponent(lblWeapon)
						.addComponent(lblArmor))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(healthbar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHP))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAttributes)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblWeapon)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblArmor)
					.addContainerGap(56, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(isSelected) {
			g.setColor(Color.blue);
		}
		else {
			g.setColor(Color.black);
		}
		Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));
			g2d.drawLine(0, 0, 0, this.getHeight()-1);
			g2d.drawLine(0, 0, this.getWidth()-1, 0);
			g2d.drawLine(0, this.getHeight()-1, this.getWidth()-1, this.getHeight()-1);
			g2d.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight()-1);
		
		
	}
	public void setSelected(boolean selected) {
		isSelected = selected;
	}
	
	public void setBackgroundColor(Color c) {
		setBackground(c);
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public void setCharacter(GameChar c) {
		character = c;
		lblName.setText(character.getChar().getName());
		
//		healthbar = new JPanel() {
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//			public void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				g.setColor(Color.red);
//				g.fillRect(0, 0, this.getWidth(), this.getHeight());
//				g.setColor(Color.green);
//				g.fillRect(0, 0, this.getWidth() * character.getCurrHP() / character.getChar().getSt(), this.getHeight());
//			}
//		};
		
		lblHP.setText(character.getCurrHP() + "/" + character.getChar().getSt());
		
		attributes = String.format("ST%2d DX%2d IQ%2d MP%2d/%2d", character.getChar().getSt(), character.getChar().getDx(),
				character.getChar().getIq(), character.getMoves(), character.getChar().getMoves());
		lblAttributes.setText(attributes);
		
		lblWeapon.setText(character.getChar().getWeapon().name());
		
		lblArmor.setText(character.getChar().getArmor().name());
		System.out.println("updating CharInfoPanel " + character.getChar().getName());
		repaint();
	}
}
