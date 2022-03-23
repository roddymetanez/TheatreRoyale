package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * DBConnector provides an interface to the Database.
 */

public class DBConnector {
	// conn is the database connection
	private Connection conn;
	// connParams is the name of the file containing information required to make
	// the connection
	private static final String connParams = "connParams.txt";
	private static final Charset CREATE = null;
	private static final OpenOption APPEND = null;
	private FileRoster fileRoster;
	private CsvReader csvReader;

	public DBConnector() {
		conn = null;

	}

	/**
	 * connect will connect to the database using the address, username and password
	 * specified in the connParams file
	 */
	public void connect() {
		Scanner scanner;
		try {
			scanner = new Scanner(new File(connParams));
		}
		catch (FileNotFoundException e) {
			System.out.println("No such File");
			return;
		}
		scanner.useDelimiter(", ");
		String dburl = scanner.next();
		String user = scanner.next();
		String pass = scanner.next();
		try {
			conn = DriverManager.getConnection(dburl, user, pass);
		}
		catch (SQLException e) {
			System.out.println("Connection failed." + dburl + ", " + user + ", " + pass);
			e.printStackTrace();
			return;
		}

		if (conn != null) {
			System.out.println("Connection established.");
		}
		else {
			System.out.println("Connection null still.");
		}
	}

	/**
	 * runQuery will prepare an SQL statement taken from a file to run
	 */
	public ResultSet runQuery(String sql) {
		ResultSet tmpResults = runQuery(sql, false);
		return tmpResults;
	}

	public ResultSet runQuery(String sql, Boolean silent) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, // allows us to move forward and back in
																				// the ResultSet
					ResultSet.CONCUR_UPDATABLE);
			pst.execute();
			ResultSet results = pst.getResultSet();
			if (results != null) {
				int rowcount = 0;
				if (results.last()) {
					rowcount = results.getRow();
					results.beforeFirst(); // not rs.first() because the rs.next() later will move on, missing the first
											// element
				}
				if (!silent) {
					System.out.println(sql + "\n Success.  Result set has " + rowcount + " rows");
				}
			}
			else {
				System.out.println(sql);
			}
			return results;
		}
		catch (SQLException e) {
			System.out.println(sql + "\n failed to run.");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * printResults will print a formatted table of the query statement results
	 */
	public void printResult(ResultSet rs) {
		try {
			// MetaData contains all the information needed for formatting
			ResultSetMetaData rsmd = rs.getMetaData();
			int colWidth = 0;
			int colCount = rsmd.getColumnCount();
			// format and print column headings
			ArrayList<Integer> colWidths = new ArrayList<Integer>();
			ArrayList<String> firstColData = new ArrayList<String>();

			Boolean heading = true;
			// while there is another row, format and print that row
			while (rs.next()) {
				if (heading) {
					rsmd = rs.getMetaData();
					for (Integer i = 1; i <= colCount; i++) {
						Integer headerWidth = rsmd.getColumnDisplaySize(i);
						String name = rsmd.getColumnLabel(i);
						String data = rs.getString(i);
						firstColData.add(i - 1, data);

						if (headerWidth > data.length())
							colWidth = headerWidth;
						else colWidth = data.length();
						if (name.length() > colWidth) colWidth = name.length();
						colWidths.add(i - 1, colWidth);
						// to make the columns the right width
						System.out.print(String.format("%-" + colWidth + "." + colWidth + "s", name));
						System.out.print(" ");
					}
					System.out.println();
					for (int i = 1; i <= colCount; i++) {
						String data = firstColData.get(i - 1);
						colWidth = colWidths.get(i - 1);
						System.out.print(String.format("%-" + colWidth + "." + colWidth + "s", data));
						System.out.print(" ");
					}
					System.out.println();
					heading = false;
				}
				else {
					for (int i = 1; i <= colCount; i++) {
						// to make the columns line up
						colWidth = colWidths.get(i - 1);
						System.out.print(String.format("%-" + colWidth + "." + colWidth + "s", rs.getString(i)));
						System.out.print(" ");
					}
					System.out.println();
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			conn.close();
			System.out.println("Connection closed.");
		}
		catch (SQLException e) {
			System.out.println("Connection not closed.");
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		try {
			return conn.getCatalog();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return "Invalid Connection.";
		}
	}

	public Connection getConn() {
		return conn;
	}

	/**
	 * Takes a filepath to a folder to generate a series of links to files and then
	 * use the csv reader to process the csv files into ArrayLists of strings. This
	 * method is self contained so that on completion the temporary arrays can be
	 * deleted
	 * 
	 * @param filePath to local folders of csv files
	 * @param
	 * @throws IOException
	 */
	public void loadSampleTablesIntoDb(File filePath, boolean dropTable) throws IOException {
		String writeInstr = "";
		csvReader = new CsvReader();
		fileRoster = new FileRoster();
		ArrayList<File> tableFiles = new ArrayList<File>();
		ArrayList<String> tableInstr = new ArrayList<String>();// Prep list for same
		if (dropTable) {
			tableFiles = fileRoster.getFilesForFolder(filePath); // get files in the folder
		}
		else {
			tableFiles.add(filePath); // single file, no need for drop table
		}
		for (File table : tableFiles) {// Make the Arrays of strings for queries
			// Table Branches
			try {
				// get the tables in their formed arrays
				tableInstr = csvReader.readFile(table, dropTable);
				// Pipe the tables to the Db, as one MySql command at a time
				for (String instr : tableInstr) {
					// System.out.println(instr);
					runQuery(instr, true);
				}
			}
			catch (Exception malformedCsvFile_CheckCsv) {
				malformedCsvFile_CheckCsv.printStackTrace();
			}
		}
	}
}
