package org.dchan.context;

import java.util.HashMap;
import java.util.Map;

/**
 * This class will be the Super class for all Contexts so that we can better
 * manage contexts.
 * 
 * @author Dchan (Dulitha Rasanga Wijewantha)
 * 
 * @version $Revision: 1.0 $
 */
public abstract class ContextManager {
	// This map holds the Contexts that are managed by the SessionContextManager
	Map<String, Context> contextList = new HashMap<String, Context>();

	/**
	 * This method needs to be overridden in order to be used by a subclass
	 * 
	 * @param name
	 *            = Name of the Context that will be known to the application
	
	 * @return A Context Object */
	protected abstract Context createContext(String name);

	/**
	 * This method returns a Context Object with the given name if the Context
	 * is not present it'll create a Context and it will be returned
	 * 
	 * @param name = Name of the Context that will be known to the application
	
	 * @return A Context Object */
	public Context getContext(String name) {
		// Gets the Context object from the ContextList
		Context context = contextList.get(name);
		// If the Context object is not present an instantiation proceeded
		if (context == null) {
			context = createContext(name);
			contextList.put(name, context);
		}
		return context;
	}

}
