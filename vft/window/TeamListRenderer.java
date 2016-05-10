package vft.window;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import vft.VFTTeam;

class TeamListRenderer<T extends VFTTeam> extends JPanel implements ListCellRenderer<T> {

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
		JLabel nameLabel = new JLabel(value.getName());
		JLabel sizeLabel = new JLabel("Size: "+value.getChars().size());
		//String text = String.format("%-50s size:%5d", value.getName(), value.getChars().size());
		//setText(text);
		removeAll();
		setLayout(new BorderLayout());
		this.add(nameLabel, BorderLayout.CENTER);
		this.add(sizeLabel, BorderLayout.LINE_END);
		setOpaque(true);

		return this;
	}
}