package controller;

import java.sql.ResultSet;

import util.DBConnector;
import util.Parser;

/**
 * Engine drives the system to read SQL queries from a file, process these queries
 * and output results where there are any.
 */
public class DataAccess {
    private DBConnector db;
    private final String GET_SHOWS = "getShows";
    private final String GET_SHOW_BY_NAME = "getShowByName";
    private final String GET_SHOW_BY_DATE = "getShowByDate";

    public DataAccess() {
        db = new DBConnector();
        // connect to the database
        db.connect();        
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
}
