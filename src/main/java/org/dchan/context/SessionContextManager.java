package org.dchan.context;

import java.util.HashMap;
import java.util.Map;

/**
 * This class will hold references to all session contexts of the application.
 * This class is responsible for creation, management and disposal of Contexts
 * according to the needs of the application
 *  
 * @author Dchan (Dulitha Rasanga Wijewantha)
 * 
 * @version $Revision: 1.0 $
 */
public class SessionContextManager extends ContextManager {
	
	private static SessionContextManager contextManager;

	/**
	 * Method getInstance.
	 * @return SessionContextManager
	 */
	public static SessionContextManager getInstance() {
		if (contextManager == null) {
			contextManager = new SessionContextManager();
		}
		return contextManager;
	}
	
	/**
	 * Method createContext.
	 * @param name String
	 * @return Context
	 */
	protected Context createContext(String name){
		return new SessionContext();
	}
}
