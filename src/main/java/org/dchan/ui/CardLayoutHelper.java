package org.dchan.ui;

import java.awt.CardLayout;

import javax.swing.JPanel;

/**
 * 
 * @author Dchan(Dulitha Wijewantha)
 * 
 *         This class is used to switch Cards in a CardLayout
 * 
 * @version $Revision: 1.0 $
 */
public class CardLayoutHelper {

	private JPanel panel;
	private CardLayout layout;

	/**
	 * 
	 * 
	 * @param panel
	 *            JPanel
	 */
	public CardLayoutHelper(JPanel panel) {
		this.panel = panel;
		this.layout = (CardLayout) this.panel.getLayout();
	}

	public CardLayoutHelper(JPanel panel, JPanel... panels) {
		this(panel);
		for (int i = 0; i < panels.length; i++) {
			JPanel jPanel = panels[i];
			panel.add(jPanel.getName(), jPanel);
		}
	}

	/**
	 * 
	 * @param currentPanel
	 *            - The panel that will be switched into the view
	 */
	public void switchPanel(JPanel currentPanel) {
		panel.removeAll();
		panel.add(currentPanel, currentPanel.getName());
		layout.show(panel, currentPanel.getName());
		panel.revalidate();
		panel.repaint();
	}

	public void switchPanel(String name) {
		layout.show(panel, name);
		panel.revalidate();
		panel.repaint();
	}
}
