package org.dchan.orm;

import org.hibernate.cfg.AnnotationConfiguration;

public class HbHelperSingle extends HbHelperAbstract {

	private static HbHelperSingle pool;
	private static String url;
	private static String password;
	private static String schema;
	private static String user;
	private static boolean isProduction = false;
	boolean isInit = false;

	/**
	 * Init variable setup to create the Singleton {@link HbHelperSingle}
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @param schema
	 */
	public static void init(String url, String user, String password,
			String schema) {
		HbHelperSingle.url = url;
		HbHelperSingle.password = password;
		HbHelperSingle.user = user;
		HbHelperSingle.schema = schema;
	}

	/**
	 * A method to set production level
	 * 
	 * @param flag
	 */
	public static void setProduction(boolean flag) {
		HbHelperSingle.isProduction = flag;
	}

	public static HbHelperSingle getInstance() {
		if (pool == null) {
			pool = new HbHelperSingle();
		}
		return pool;
	}

	// private constructor for singleton
	private HbHelperSingle() {
	}

	@Override
	public void startFactory() {
		AnnotationConfiguration configuration = new AnnotationConfiguration()
				.configure();
		configuration.setProperty("hibernate.connection.url", url);
		configuration.setProperty("hibernate.connection.username", user);
		configuration.setProperty("hibernate.connection.password", password);
		configuration.setProperty("hibernate.default_schema", schema);
		if (isProduction) {
			configuration.setProperty("hibernate.show_sql", "true");
		}
		factory = configuration.buildSessionFactory();
	}

}
