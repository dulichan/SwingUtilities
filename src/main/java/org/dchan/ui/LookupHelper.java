package org.dchan.ui;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * 
 * @author Dchan(Dulitha Rasanga Wijewantha)
 * 
 *         Lookup Helper This class is used as a Utility by ActionHelper to make
 *         sure that all the components in the Container is available to
 *         ActionHelper. Moreover this class provides Utility methods to obtain
 *         <ul>
 *         <li>Swing Buttons <strong>btn</strong></li>
 *         <li>Swing Labels <strong>lbl</strong></li>
 *         <li>Swing Components</li>
 *         <li>Swing Tables <strong>tbl</strong></li>
 *         <li>Swing Comboboxes <strong>cmb</strong></li>
 *         <li>Swing Panel <strong>pnl</strong></li>
 *         <li>Swing Textfield <strong>txt</strong></li>
 *         <li>Swing Textareas <strong>txa</strong></li>
 *         </ul>
 * 
 * @version $Revision: 1.0 $
 */
public class LookupHelper {
	UIHookHelper hookHelper =new UIHookHelper();
	/**
	 * This map consists the components that have been traversed by the
	 * Component Lookup drone
	 */
	private Map<String, Component> componentTree;

	/**
	 * Constructor takes a Component array which derives from the
	 * Container.getComponents();
	 * 
	 * @param components
	 *            - Primary component List
	 */
	public LookupHelper(Component[] components) {
		componentTree = new HashMap<String, Component>();
		for (int i = 0; i < components.length; i++) {
			Component component = components[i];
			delegate(component);
		}
	}

	/**
	 * This method specifically takes care of traversing through the JPanel's
	 * inner components
	 * 
	 * @param jPanel
	 */
	private void lookupJPanel(JPanel jPanel) {
		Component[] components = jPanel.getComponents();
		for (int i = 0; i < components.length; i++) {
			Component component = components[i];
			delegate(component);
		}
	}

	/**
	 * This method takes care of traversing through the JScrollPane's inner
	 * components
	 * 
	 * @param jScrollPane
	 */
	private void lookupJScrollPane(JScrollPane jScrollPane) {
		Component[] components = jScrollPane.getViewport().getComponents();
		for (int i = 0; i < components.length; i++) {
			Component component = components[i];
			delegate(component);
		}
	}

	/**
	 * This method takes care of traversing through the JTabbedPane inner
	 * components
	 * 
	 * @param jTabbedPane
	 */
	private void lookupJTabbedPane(JTabbedPane jTabbedPane) {
		Component[] components = jTabbedPane.getComponents();
		for (int i = 0; i < components.length; i++) {
			Component component = components[i];
			if (component instanceof JPanel) {
				delegate(component);
			}

		}
	}

	/**
	 * Delegates all component discoveries. This method ensures that inner
	 * components are properly added to the context
	 * 
	 * @param component
	 */
	private void delegate(Component component) {
		if (component instanceof JPanel) {
			lookupJPanel((JPanel) component);
		} else if (component instanceof JScrollPane) {
			lookupJScrollPane((JScrollPane) component);
		} else if (component instanceof JTabbedPane) {
			lookupJTabbedPane((JTabbedPane) component);
		}
		/**
		 * This object applies UI changes to universally 
		 */
		hookHelper.applyHook(component);
		getTree().put(component.getName(), component);
	}

	/**
	 * This method is general wrapper for the context. Getter can be used to
	 * obtain components that have not been delegated with methods
	 * 
	 * @param s
	 *            - Name of the component
	
	 * @return Component */
	public Component get(String s) {
		return componentTree.get(s);
	}

	/**
	 * This method can be used to obtain the whole context. This might be useful
	 * for more collection related operations that are later developed
	 * 
	
	 * @return Map<String,Component>
	 */
	public Map<String, Component> getTree() {
		return componentTree;
	}

}
