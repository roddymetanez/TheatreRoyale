package controller;

/**
 * Engine drives the system to read SQL queries from a file, process these
 * queries and output results where there are any.
 */
public class Engine {

//	public static void main(String[] args) {
//		Parser parser = new Parser();
//		DBConnector db = new DBConnector();
//		// connect to the database
//		db.connect();
//		// open the SQL file and get the first query
//		parser.open("../TheatreRoyale/sql/theatre_system.sql");
//		String query = parser.getQuery();
//		// get the results of each query and print results where they exist
//		while (query != null) {
//			ResultSet results = db.runQuery(query);
//			if (results != null) {
//				db.printResult(results);
//			}
//			query = parser.getQuery();
//		}
//		// close the file interface and the database
//		parser.close();
//		db.close();
//	}
}
