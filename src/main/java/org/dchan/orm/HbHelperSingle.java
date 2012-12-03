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

	public static void init(String url, String user, String password,
			String schema) {

	}

	public static HbHelperSingle getInstance() {
		if (pool == null) {
			pool = new HbHelperSingle();
		}
		return pool;
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
		// startSecondLevelCache();
		factory = configuration.buildSessionFactory();
	}

}
