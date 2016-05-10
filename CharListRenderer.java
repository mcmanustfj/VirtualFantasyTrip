package vft.window;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import vft.VFTChar;

class CharListRenderer<T extends VFTChar> extends JLabel implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1098621963643434219L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if(isSelected) {
			setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		VFTChar character = (VFTChar) value;
		setText(character.getName());
		Font f = getFont();
		if(character.isUnique()){
			setFont(new Font(f.getName(), Font.BOLD, f.getSize()));
		}
		else {
			setFont(new Font(f.getName(), Font.PLAIN, f.getSize()));
		}
		setOpaque(true);
		
		return this;
	}
	
}