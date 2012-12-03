package org.dchan.ui;

import java.awt.CardLayout;

import java.awt.Panel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**
 */
public abstract class WindowHelper {
	 CardLayout layout;
	 JPanel panel;
	/**
	 * @deprecated due to the fact that setPanel can Inject the layout with the use of the main panel
	 * @param la
	 */
	public  void setCardLayout(CardLayout la){
		layout=la;
	}
	/**
	 * This Method injects the Panel and the CardLayout is obtained via it.
	 * @param pa - The Main Panel that has the CardLayout in itself.
	 */
	public  void setPanel(JPanel pa){
		panel=pa;
		layout=(CardLayout) pa.getLayout();
	}
	/**
	 * 
	 * @param newPanel - The Panel that will be swapped with the Panel in the view Object. The View object is the object that contains the CardLayout
	
	 * @see Panel should be first injected manually by us to use WindowHelper.  */
	public  void switchPanel(final JPanel newPanel){
		panel.removeAll();
		panel.add(newPanel, newPanel.getName());
		layout.show(panel, newPanel.getName());
		panel.revalidate();  
		panel.repaint();
	}
	
	
}
