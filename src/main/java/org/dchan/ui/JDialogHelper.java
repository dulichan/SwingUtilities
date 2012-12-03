package org.dchan.ui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 */
public class JDialogHelper extends WindowHelper {
	JDialog dialog;

	/**
	 * Constructor for JDialogHelper.
	 * 
	 * @param d
	 *            JDialog
	 */
	public JDialogHelper(JDialog d) {
		dialog = d;
		setPanel((JPanel) dialog.getContentPane());
	}

	/**
	 * This method is used to switch the Panel of a Dialog box.
	 * 
	 * @param newPanel
	 *            - The Panel that we need to be visible in the Dialog box
	 */
	public void switchPanel(final JPanel newPanel) {
		super.switchPanel(newPanel);
		dialog.setTitle(newPanel.getName());
		dialog.repaint();
		dialog.pack();
		dialog.setVisible(true);
	}
}