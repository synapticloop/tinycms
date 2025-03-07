package com.synapticloop.tinycms.sqlite.h2zero.util;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//        (java-create-database-checker-helper.templar)

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Collections;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
public class DatabaseCheckerHelper {
		private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCheckerHelper.class);


	private final Set<String> allTables = new HashSet<String>();
	private final Map<String, Set<String>> allTableFields = new HashMap<String, Set<String>>();

	private static final String[] ALL_TABLE_NAMES = { "collection", "field_type", "field", "data_collection", "data_field" };
	private static final String[] ALL_TABLE_FIELD_NAMES = { "collection.id_collection", "collection.nm_collection", 
			"field_type.id_field_type", "field_type.nm_field_type", "field_type.fl_field_should_quote", 
			"field.id_field", "field.id_field_type", "field.id_collection", "field.nm_field", 
			"data_collection.id_data_collection", "data_collection.id_collection", "data_collection.nm_collection", "data_collection.fl_is_published", 
			"data_field.id_data_field", "data_field.id_field"
			 };

	private void initialiseDataStructures() {
		Collections.addAll(allTables, ALL_TABLE_NAMES);

		for (int i = 0; i < ALL_TABLE_FIELD_NAMES.length; i++) {
			String allTableFieldName = ALL_TABLE_FIELD_NAMES[i];
			String[] split = allTableFieldName.split("\\.");
			String table = split[0];
			String field = split[1];
			if(allTableFields.containsKey(table)) {
				allTableFields.get(table).add(field);
			} else {
				Set<String> hashSet = new HashSet<String>();
				hashSet.add(field);
				allTableFields.put(table, hashSet);
			}
		}
	}

	private boolean areTablesCorrect() {
		boolean isDatabaseCorrect = true;
		Connection connection = null;
		ResultSet tablesResultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			tablesResultSet = databaseMetaData.getTables("tinycms", null, "%", new String[] { "TABLE" });
			while(tablesResultSet.next()) {
				String tableName = tablesResultSet.getString("TABLE_NAME");
				if(allTables.contains(tableName)) {
					LOGGER.info("Table '" + tableName + "' exists in the h2zero file and in the database.");
				} else {
					LOGGER.error("Table '" + tableName + "' exists in the database but not in the h2zero file.");
					LOGGER.error("SQL COMMAND -> DROP TABLE " + tableName + ";");
				}
				allTables.remove(tableName);
			}

			// now go through the tableNames and see what is left...
			Iterator<String> allTablesIterator = allTables.iterator();
			while (allTablesIterator.hasNext()) {
				String tableName = allTablesIterator.next();
				LOGGER.error("Table '" + tableName + "' exists in the h2zero file, but not in the database.");
				LOGGER.error("SQL COMMAND -> CREATE TABLE " + tableName + ";");
				// now we need to remove this from the all table fields lookup as it won't be found and we will get spurious
				// errors
				allTableFields.remove(tableName);
				isDatabaseCorrect = false;
			}

		} catch (SQLException sqlex) {
			// do nothing
		} finally {
			ConnectionManager.closeAll(tablesResultSet, null, connection);
		}

		return(isDatabaseCorrect);
	}

	private boolean areTableFieldsCorrect() {
		boolean isDatabaseCorrect = true;
		// now it is time to check all of the fields for all of the tables
		Connection connection = null;
		ResultSet columns = null;
		try {
			connection = ConnectionManager.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			columns = databaseMetaData.getColumns("macdaddy", null, "%", "%");
			while(columns.next()) {
				String tableName = columns.getString("TABLE_NAME");
				String columnName = columns.getString("COLUMN_NAME");

				// this will be picked up by the previous check
				if(allTableFields.containsKey(tableName)) {
					if(allTableFields.get(tableName).contains(columnName)) {
						LOGGER.info("table.field '" + tableName + "." + columnName + "' exists in the h2zero file and in the database.");
					} else {
						LOGGER.error("table.field '" + tableName + "." + columnName + "' exists in the h2zero file, but not in the database.");
						LOGGER.error("SQL COMMAND -> ALTER TABLE " + tableName + " ADD COLUMN " + columnName + ";");
						isDatabaseCorrect = false;
					}
					allTableFields.get(tableName).remove(columnName);
				} else {
					LOGGER.warn("Refusing to check '" + tableName + "." + columnName + "' as the table does not exist in the database.");
				}
			}

			// now go through everything that is left
			Iterator<String> allTableFieldsIterator = allTableFields.keySet().iterator();
			while (allTableFieldsIterator.hasNext()) {
				String tableName = allTableFieldsIterator.next();
				Set<String> hashSet = allTableFields.get(tableName);
				Iterator<String> iterator = hashSet.iterator();
				while (iterator.hasNext()) {
					String columnName = iterator.next();
					LOGGER.error("SQL COMMAND -> ALTER TABLE " + tableName + " DROP COLUMN " + columnName + ";");
					isDatabaseCorrect = false;

				}
			}
		} catch (SQLException sqlex) {
			// do nothing
		} finally {
			ConnectionManager.closeAll(columns, null, connection);
		}

		return(isDatabaseCorrect);
	}

	public boolean isDatabaseCorrect() {
		initialiseDataStructures();
		return(areTablesCorrect() && areTableFieldsCorrect());
	}

}
