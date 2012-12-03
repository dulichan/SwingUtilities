package org.dchan.context;

import java.util.HashMap;
import java.util.Map;

/**
 * A Context is there to store data necessary for a period of time and to be
 * flushed away when they are not necessary
 * 
 * @author Dchan (Dulitha Rasanga Wijewantha)
 * 
 */
public abstract class Context {
	// This context holds all data
	Map<String, Object> dataStack = new HashMap<String, Object>();

	/**
	 * Getter for stack objects
	 * 
	 * @param s
	 *            - The key
	 * @return Object
	 */
	public Object get(String s) {
		return dataStack.get(s);
	}

	/**
	 * Setter for the stack object
	 * 
	 * @param name
	 *            - the Key
	 * @param s
	 *            - the Object
	 */
	public void set(String name, Object s) {
		dataStack.put(name, s);
	}

	public void nullify(String name) {
		dataStack.put(name, null);
	}
}
