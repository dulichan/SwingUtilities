package org.dchan.ui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class UIHookHelper {

	public void applyHook(Component component) {
		if (component instanceof JTable) {
			JTable table = (JTable) component;
		} else if (component instanceof JScrollPane) {
			JScrollPane scrollPane = (JScrollPane) component;
		} else if (component instanceof JButton) {
			JButton button = (JButton) component;
		}
	}

}
