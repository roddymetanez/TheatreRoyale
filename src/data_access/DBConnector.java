package src.data_access;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.Scanner;
import java.nio.file.Files;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * DBConnector provides an interface to the Database.
 */

public class DBConnector {
    // conn is the database connection
    private Connection conn;
    // connParams is the name of the file containing information required to make 
    // the connection
    private static final String connParams = "connParams.txt";

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
            // instantiate a scanner to scan the defined file
            scanner = new Scanner(new File(connParams));}
        catch (FileNotFoundException e) { 
            System.out.println("No such File");
            return;
        }
        // reading the fields from the file separated by ';'
        scanner.useDelimiter(", ");
        String dburl = scanner.next();
        String user = scanner.next();
        String pass = scanner.next();
        try {
            conn = DriverManager.getConnection(dburl, user, pass);
        } catch (SQLException e) {
            // inidicating that connection has failed for specified url, username and password
            System.out.println("Connection failed." + dburl + ", " + user + ", " + pass );
            e.printStackTrace();
            return;
        }

        if (conn != null) {
            //indicating that connection has been made
            System.out.println("Connection established.");
        } else {
            // indicating that connection attempt has timed out
            System.out.println("Connection null still.");
        }
    }

    public ResultSet runCall(CallableStatement cst) {
        try {
            ResultSet rs = cst.executeQuery();
            ResultSet results = cst.getResultSet();
            if (results != null) {
                int rowcount = 0;
                if (results.last()) {
                    rowcount = results.getRow();
                    results.beforeFirst(); // not rs.first() because the rs.next() later will move on, missing the first
                                            // element
                }
                System.out.println(cst.toString() + "\n Success.  Result set has " + rowcount + " rows");
            } else {
                System.out.println(cst.toString() + "\n Success.  No results returned");
            }
            return results;
            
        } catch (SQLException e) {
            System.out.println(cst.toString() + "\n failed to run.");
            e.printStackTrace();
            return null;
        }
    }
    

    public ResultSet callNString(String query, ArrayList<String> params) {
        Connection conn = getConn();
        try {
            CallableStatement cst = conn.prepareCall(
                query,
                ResultSet.TYPE_SCROLL_SENSITIVE, // allows us to move forward and back in the ResultSet
                ResultSet.CONCUR_UPDATABLE);
            int paramNum = 1;
            for (String param: params){
                cst.setString(paramNum++, param);
            }
            return runCall(cst);
        } catch (SQLException e) {
            System.out.println(query + "\n failed to run.");
            e.printStackTrace();
            return null;
        }
    }
    
    
    /** 
     * runQuery will prepare an SQL statement taken from a file to run
     */

    public ResultSet runQuery(String sql) {
        // pst will hold the SQL query
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_SENSITIVE, // allows us to move forward and back in the ResultSet
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
                System.out.println(sql + "\n Success.  Result set has " + rowcount + " rows");
            } else {
                System.out.println(sql + "\n Success.  No results returned");
            }
            return results;
        } catch (SQLException e) {
            System.out.println(sql + "\n failed to run.");
            e.printStackTrace();
            return null;
        }
    }

    
    /**
     * printResults will print a formatted table of the query statement results
     * @param rs ResultSet the result of the query which has been made
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
            
            Boolean heading =true;
            // while there is another row, format and print that row
            while (rs.next()) {
                if (heading) {
                    rsmd = rs.getMetaData();
                    for (Integer i = 1; i <= colCount; i++) {
                        Integer headerWidth = rsmd.getColumnDisplaySize(i);
                        String name = rsmd.getColumnLabel(i);
                        String data = rs.getString(i);
                      // Conflicted line adapted as below
                        if (data == null) data =""; // If null, display empty string

                        firstColData.add(i-1, data);
                        
                        if (headerWidth > data.length())
                            colWidth = headerWidth;
                        else
                            colWidth = data.length();
                        if (name.length() > colWidth)
                            colWidth = name.length();
                        colWidths.add(i-1, colWidth);
                        // to make the columns the right width
                        System.out.print(String.format("%-" + colWidth + "." + colWidth + "s", name));
                        System.out.print(" ");
                    }
                    System.out.println();
                    for (int i = 1; i <= colCount; i++) {
                        String data = firstColData.get(i-1);
// Duplicated line commented as below
                        if (data == null) data =""; // If null, display empty string
   //                     if (data == null) data =""; // If null, display empty string
                        colWidth = colWidths.get(i-1);
                        System.out.print(String.format("%-" + colWidth + "." + colWidth + "s", data));
                        System.out.print(" ");                        
                    }
                    System.out.println();
                    heading = false;
                } else {
                    for (int i = 1; i <= colCount; i++) {
                        //to make the columns line up
                        colWidth = colWidths.get(i-1);  
                        System.out.print(String.format("%-" + colWidth + "." + colWidth + "s", rs.getString(i)));
                        System.out.print(" ");
                    }      
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close will close the connection to the current database
     */
    public void close() {
        try {
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println("Connection not closed.");
            e.printStackTrace();
        }
    }

    /**
     * Prints out the name of the database connectd to
     */
    public String toString(){
        try {
            return conn.getCatalog();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Invalid Connection.";
        }
    }
    
    /**
     * getConn returns the 'handle' of the connected database
     * @return Connection - the 'handle' of the connected database
     */
    public Connection getConn() {
        return conn;
    }
}
