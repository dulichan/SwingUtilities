package org.dchan.components;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

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
 * 
 *         Example usage :-
 * 
 *         <pre>
 * tblItems.setParameters(new String[] { &quot;SKUSKU&quot;, &quot;SKUDES&quot;, &quot;SKUSPR&quot; });
 * tblItems.setConnection(Runner.getConnection());
 * tblItems.setQuery(&quot;select  SKUSKU,SKUDES,SKUSPR  from SKUMAST&quot;);
 * </pre>
 */
@SuppressWarnings("serial")
public class ViewTable extends JTable {
	private Connection connection;
	private String query;
	private String[] parameters;
	static DecimalFormat format = new DecimalFormat("0.00");

	Map<String, Class> map = new HashMap<String, Class>();

	public void put(String field, Class clas) {
		map.put(field, clas);
	}

	public static void setFormat(DecimalFormat format) {
		ViewTable.format = format;
	}

	/**
	 * Use this constructor to later pass in the parameters later
	 */
	public ViewTable() {

	}

	/**
	 * If we can provide the connection when the {@link ViewTable} object is
	 * been created use this method
	 * 
	 * @param connection
	 * @param query
	 * @param parameters
	 */
	public ViewTable(Connection connection, String query, String parameters[]) {
		this.connection = connection;
		this.query = query;
		this.parameters = parameters;
		captureData();
	}

	/**
	 * Covering method
	 */
	public void captureData() {
		captureData(query, parameters);
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
					try {
						Object object = resultSet.getObject(field);
						if (object instanceof String) {
							row[i] = object.toString();
						} else if (object instanceof Double) {
							row[i] = format.format(((Double) object));
						} else if (object instanceof Integer) {
							row[i] = ((Integer) object).toString();
						} else if (object instanceof BigDecimal) {
							if (map.get(field) != null
									&& map.get(field).equals(Integer.class)) {
								int is = (int) ((BigDecimal) object)
										.doubleValue();
								row[i] = Integer.toString(is);
							} else {
								row[i] = format.format(((BigDecimal) object));
							}
						}
					} catch (Exception e) {

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
	 * Print out the column names of the query
	 * 
	 * @throws SQLException
	 */
	public void printColumnNames() throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			System.out.println("Column name is = " + metaData.getColumnName(i));
		}
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

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Setup the connection after object creation
	 * 
	 * @param connection
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
