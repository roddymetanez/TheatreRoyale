package controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import util.DBConnector;

/**
 * Engine drives the system to read SQL queries from a file, process these
 * queries and output results where there are any.
 */
public class DataAccess {
	private DBConnector db;
	private final String GET_SHOWS = "getShows";
	private final String GET_SHOW_BY_NAME = "getShowByName";
	private final String GET_SHOW_BY_DATE = "getShowByDate";
	File filePath;

	public DataAccess() throws IOException {
		db = new DBConnector();
		filePath = new File("../TheatreRoyale/csv/");
		/// TheatreRoyale/src/csv
		// csvcsv/TheatreRoyale/csv
		// connect to the database
		db.connect();
		// get the existing data tables into the db
		db.loadSampleTablesIntoDb(filePath, true);
	}

	public DBConnector getDb() {
		return db;
	}

	public ResultSet getShows() {
		return db.runQuery("call " + GET_SHOWS + "()");
	}

	public ResultSet getShowByName(String name) {
		return db.runQuery("call " + GET_SHOW_BY_NAME + "('" + name + "')");
	}

	public ResultSet getShowByDate(String date) {
		return db.runQuery("call " + GET_SHOW_BY_DATE + "('" + date + "')");
	}

	public void close() {
		db.close();
	}

	private String getLocalPath() {

		String basePath = new File("").getAbsolutePath();
		System.out.println(basePath);

		String path = new File("/csv/").getAbsolutePath();
		System.out.println(path);

		String filePath = new File("").getAbsolutePath();
		filePath.concat("path to the property file");

		return null;
	}
}
