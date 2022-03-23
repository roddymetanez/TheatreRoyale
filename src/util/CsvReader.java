package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class CsvReader {
	private FileReader flRdr;
	private String csvFileName;
	private BufferedReader buffRdr;
	private ArrayList<String> prepdTable;
	private static final String delimiter = ",";

	/**
	 * takes a csvfile formatted as the below instructions and prepares MySQL table
	 * creating scripts as arraylist strings. readFile
	 */
	public CsvReader() {
		// Empty constructor
	}

	/**
	 * Takes a prepared connection and a csv file and if formatted returns a
	 * formatted ArrayList of strings for use as a series of ; delimited sql queries
	 * 
	 * .csv file must be formatted, in rows, as follows; R1. Headers R2. MySQL types
	 * Rn... Values
	 * 
	 * Nulls are currently not allowable at any point, apologies in advance
	 * 
	 * @param filepath as file
	 * @boolean choice to drop table or not. If false then new information is added
	 *          to the table
	 * @throws Exception
	 */
	public ArrayList<String> readFile(File table, boolean dropTable) throws Exception {

		int lineCount = 0;
		String newLine = "";
		flRdr = new FileReader(table);
		buffRdr = new BufferedReader(flRdr);
		prepdTable = new ArrayList<String>();
		ArrayList<String[]> tmpTable = new ArrayList<String[]>();

		csvFileName = tableNameFromFileName(table);

		while ((newLine = buffRdr.readLine()) != null) {
			String[] tmpLine = newLine.split(delimiter);
			tmpTable.add(tmpLine);
			lineCount++;
		}

		if (dropTable) {
			// DropTable
			prepdTable.add("DROP TABLE IF EXISTS " + csvFileName + "; ");
			// Create Table
			prepdTable.add("CREATE TABLE " + csvFileName + " ("
					+ (createTableHeaderString(tmpTable.get(0), tmpTable.get(1))) + "");
		}
		// If here so that if the csv is malformed we stop
		if (lineCount < 2) {
			throw new Exception("Check csv for errors / insufficient data");
		}
		else {
			// create a string ready for insertion
			String followingLine = "";
			// create a series of insert into tables, using the first line headers
			for (int i = 2; i < lineCount; i++) {
				followingLine = " ";
				followingLine = "INSERT INTO " + csvFileName + " (";
				String[] tmpLine = tmpTable.get(0);

				int k = 0;
				// header counter
				for (k = 0; k < (tmpLine.length - 1); k++) {
					followingLine = followingLine + tmpLine[k] + ", ";
				}
				followingLine = followingLine + tmpLine[k] + ") ";
				followingLine = followingLine + " VALUES " + "(";
				tmpLine = tmpTable.get(i);

				int h = 0;
				// values counter
				for (h = 0; h < (tmpLine.length - 1); h++) {
					followingLine = followingLine + tmpLine[h] + ", ";
				}
				// last line in the query, using counter
				followingLine = followingLine + tmpLine[h] + "); ";
				prepdTable.add(followingLine);
			}
		}
		return prepdTable;
	} // end method

	/**
	 * Creates a string of headers in an MYSQL format
	 * 
	 * @param headerStr, the header line of the .csv
	 * @param specStr,   the MySQL types
	 * @return
	 * @throws Exception with message.
	 */
	private String createTableHeaderString(String[] headerStr, String[] specStr) throws Exception {
		String tmpHeader;
		String tmpHeaderSpec;
		String tmpTableInstr = "";

		if (headerStr.length == specStr.length) {
			int count = headerStr.length;

			for (int i = 0; i < count; i++) {
				tmpHeader = headerStr[i].toString(); // Creates a list of the headers info
				tmpHeaderSpec = specStr[i].toString();
				// read from both of them in a sequence, using the correct format
				if (i < count - 1) {
					tmpTableInstr = tmpTableInstr + tmpHeader + " " + tmpHeaderSpec + ", ";
				}
				// to create a insert into dialogue
				else {
					tmpTableInstr = tmpTableInstr + tmpHeader + " " + tmpHeaderSpec + "); ";
				}
			}
			return tmpTableInstr;
		}
		else {
			throw new Exception("Check csv for mismatch between header and spec");
		}
	}

	/**
	 * Gets the filename from the selected file, for insertion into the header
	 * 
	 * @param fileName
	 * @return
	 */
	private String tableNameFromFileName(File fileName) throws FileNotFoundException {
		String csvFileName = fileName.getName().toString();
		int dotPos = csvFileName.lastIndexOf(".");
		if (dotPos != -1) {
			return csvFileName.substring(0, dotPos);
		}
		else {
			return csvFileName;
		}
	}
}
