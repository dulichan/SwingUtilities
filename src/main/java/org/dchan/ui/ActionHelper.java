package org.dchan.ui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.dchan.context.Context;
import org.dchan.context.SessionContextManager;

/**
 * 
 * @author Dchan(Dulitha Rasanga Wijewantha)
 * 
 *         ActionHelper is a class that maps out as a base for the Actions that
 *         are later developed. This method uses template design pattern to full
 *         fill the design requirements. An action's life cycle can be best
 *         described as bellow
 * 
 *         *Instantiated from the View's constructor
 * 
 *         *Routes to the ActionHelper base from call to super
 * 
 *         *Variables will be intialized
 * 
 *         *Context will be created and Swing Components will be discovered
 * 
 *         *Process method will execute
 * 
 *         *Variables of implemented actions's will be intialized
 * 
 *         *Controllers of implemented action's will be intialized
 * 
 * 
 * 
 * @version $Revision: 1.0 $
 */
public abstract class ActionHelper {
	/**
	 * This contentPanel could be any component that extends JComponent and this
	 * includes JPanel, JLayeredPanel
	 */
	private JComponent contentPanel;
	LookupHelper lookupHelper;
	Context context;

	/**
	 * Constructor for ActionHelper.
	 * 
	 * @param contentPanel
	 *            JComponent
	 * @param contextName
	 *            String
	 */
	public ActionHelper(JComponent contentPanel, String contextName) {
		this.contentPanel = contentPanel;
		this.lookupHelper = new LookupHelper(contentPanel.getComponents());
		this.context = SessionContextManager.getInstance().getContext(
				contextName);
		process();
	}

	/**
	 * This method is to be overridden and this consists the actions that will
	 * occur in the view
	 */
	public abstract void executeAction();

	/**
	 * This method is intended to be overridden. Initialization of controllers
	 * would occur in this method
	 */
	public abstract void initializeControllers();

	/**
	 * This method is used to initialize variables for the constructor
	 */
	public abstract void initilaizeVariables();

	/**
	 * This is the template method that is executed once the constructor is
	 * complete
	 */
	public void process() {
		initilaizeVariables();
		initializeControllers();
		executeAction();
	}

	/**
	 * 
	 * @return The Lookup helper to process over the Components
	 */
	public LookupHelper getLookupHelper() {
		return lookupHelper;
	}

	/**
	 * This is a wrapper method over the Lookup engine
	 * 
	 * @param s
	 *            - String
	 * 
	 * @return Component
	 */
	public Component get(String s) {
		return lookupHelper.get(s);
	}

	/**
	 * Below is a set of methods to ease up getting components out of the Tree
	 * 
	 * @return Context
	 */

	public Context getContext() {
		return context;
	}

	/**
	 * Method getCombox.
	 * 
	 * @param s
	 *            String
	 * @return JComboBox
	 */
	public JComboBox getCombox(String s) {
		return (JComboBox) lookupHelper.get(s);
	}

	/**
	 * Method getTable.
	 * 
	 * @param s
	 *            String
	 * @return JTable
	 */
	public JTable getTable(String s) {
		return (JTable) lookupHelper.get(s);
	}

	/**
	 * Method getTextField.
	 * 
	 * @param s
	 *            String
	 * @return JTextField
	 */
	public JTextField getTextField(String s) {
		return (JTextField) lookupHelper.get(s);
	}

	/**
	 * Method getTextArea.
	 * 
	 * @param s
	 *            String
	 * @return JTextArea
	 */
	public JTextArea getTextArea(String s) {
		return (JTextArea) lookupHelper.get(s);
	}

	/**
	 * Method getButton.
	 * 
	 * @param s
	 *            String
	 * @return JButton
	 */
	public JButton getButton(String s) {
		return (JButton) lookupHelper.get(s);
	}

	/**
	 * Method getPanel.
	 * 
	 * @param string
	 *            String
	 * @return JPanel
	 */
	public JPanel getPanel(String string) {
		return (JPanel) lookupHelper.get(string);
	}

	/**
	 * Method getFormattedTextField.
	 * 
	 * @param name
	 *            String
	 * @return JFormattedTextField
	 */
	public JFormattedTextField getFormattedTextField(String name) {
		return (JFormattedTextField) get(name);
	}

	/**
	 * Method getPasswordField.
	 * 
	 * @param name
	 *            String
	 * @return JPasswordField
	 */
	public JPasswordField getPasswordField(String name) {
		return (JPasswordField) get(name);
	}

	/**
	 * Method getTabbedPane.
	 * 
	 * @param name
	 *            String
	 * @return JTabbedPane
	 */
	public JTabbedPane getTabbedPane(String name) {
		return (JTabbedPane) get(name);
	}

	public JRadioButton getRadioButton(String name) {
		return (JRadioButton) get(name);
	}

	public JComponent getContentPanel() {
		return contentPanel;
	}

	public JCheckBox getCheckBox(String name) {
		return (JCheckBox) get(name);
	}
	public JLabel getLabel(String name){
		return (JLabel) get(name);
	}
	public JEditorPane getEditorPane(String name){
		return (JEditorPane) get(name);
	}
}
