package org.dchan.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 */
public class JFrameHelper extends WindowHelper {
	JFrame frame;
	JDialog dialog;
	boolean isFullscreen = false;

	/**
	 * Constructor for JFrameHelper.
	 * 
	 * @param f
	 *            JFrame
	 * @param d
	 *            JDialog
	 * @param panel
	 *            JPanel
	 */
	public JFrameHelper(JFrame f, JDialog d, JPanel panel) {
		frame = f;
		dialog = d;
		this.setPanel(panel);
	}

	/**
	 * Additional Constructor for JFrameHelper that can define
	 * 
	 * @param f
	 *            JFrame
	 * @param d
	 *            JDialog
	 * @param panel
	 *            JPanel
	 */
	public JFrameHelper(JFrame f, JDialog d, JPanel panel, boolean isFullscreen) {
		frame = f;
		dialog = d;
		this.setPanel(panel);
		this.isFullscreen = isFullscreen;
	}

	/**
	 * Method switchPanel.
	 * 
	 * @param newPanel
	 *            JPanel
	 */
	@Override
	public void switchPanel(JPanel newPanel) {
		super.switchPanel(newPanel);
		frame.setTitle(newPanel.getName());
		if (!isFullscreen) {
			frame.pack();
		}
	}

	/**
	 * By calling this method the MainDialogbox will be hidden
	 * 
	 * @param newPanel
	 *            - This is the panel that will be displayed in the MainFrame
	 */
	public void hideDialog(JPanel newPanel) {
		dialog.setVisible(false);
		switchPanel(newPanel);
	}

	/**
	 * This method hides the dailog only without manipulating the MainFrame
	 */
	public void hideDialog() {
		dialog.setVisible(false);
	}
}
