package vft.window;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import vft.GameChar;
import vft.VFTChar;

class VFTCharInfoPanelRenderer<T extends VFTChar> implements ListCellRenderer<T> {

	@Override
	public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected,
			boolean cellHasFocus) {
		CharInfoPanel panel = new CharInfoPanel(new GameChar(value));
		panel.setSelected(isSelected);
		return panel;
	}
	
}