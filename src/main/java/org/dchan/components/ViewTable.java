package org.dchan.components;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * 
 * @author Dchan (Dulitha R. Wijewantha -dulithaz@gmail.com)
 * 
 *         ViewTable will be a utility class that can be used to view a SQL
 *         table. Cache regions are not used since this is used for changing
 *         tables. But the fault of the table lies in complex queries that
 *         require multiple queries to access the data to be displayed.
 */
@SuppressWarnings("serial")
public class ViewTable extends JTable {
	private Connection connection;
	private String query;
	private final String[] parameters;
	static DecimalFormat format = new DecimalFormat("0.00");

	public ViewTable(Connection connection, String query, String parameters[]) {
		this.connection = connection;
		this.query = query;
		this.parameters = parameters;
		captureData();
	}

	/**
	 * Cursor the table with the given query and enter the values to an array.
	 * After that insert the array to the {@link TableModel}
	 * 
	 * @param query
	 *            - Select query customized
	 * @param parameters
	 *            - order in which parameters are shown in table
	 */
	private void captureData(String query, String parameters[]) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			DefaultTableModel model = (DefaultTableModel) getModel();

			while (resultSet.next()) {
				String[] row = new String[parameters.length];
				for (int i = 0; i < parameters.length; i++) {
					String field = parameters[i];
					Object object = resultSet.getObject(field);
					if (object instanceof String) {
						row[i] = object.toString();
					} else if (object instanceof Double) {
						row[i] = format.format(((Double) object));
					} else if (object instanceof Integer) {
						row[i] = ((Integer) object).toString();
					}
				}
				model.addRow(row);
			}
			// Closing the Statement object
			resultSet.getStatement().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Covering method
	 */
	private void captureData() {
		captureData(query, parameters);
	}

	/**
	 * Query the table according to customization
	 * 
	 * @param query
	 * @param parameters
	 */
	public void query(String query, String parameters[]) {
		captureData(query, parameters);
	}

	/**
	 * Restore with total data to the system
	 */
	public void reset() {
		captureData();
	}

	
}
