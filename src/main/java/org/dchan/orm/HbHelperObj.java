package org.dchan.orm;

import org.hibernate.cfg.AnnotationConfiguration;

public class HbHelperObj extends HbHelperAbstract {
	private String url;
	private String password;
	private String schema;
	private String user;
	private boolean isProduction = false;

	public HbHelperObj(String url, String user, String password, String schema) {
		url = this.url;
		password = this.password;
		user = this.user;
		schema = this.schema;
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
