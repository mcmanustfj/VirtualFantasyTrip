package vft.window;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import vft.GameChar;

class GameCharInfoPanelRenderer<T extends GameChar> implements ListCellRenderer<T> {

	@Override
	public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected,
			boolean cellHasFocus) {
		CharInfoPanel panel = new CharInfoPanel(value);
		panel.setSelected(isSelected);
		return panel;
	}
	
}