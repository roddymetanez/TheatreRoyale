package src.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
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
        scanner = new Scanner(new File(connParams));}
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
        } catch (SQLException e) {
            System.out.println("Connection failed." + dburl + ", " + user + ", " + pass );
            e.printStackTrace();
            return;
        }

        if (conn != null) {
            System.out.println("Connection established.");
        } else {
            System.out.println("Connection null still.");
        }
    }

    /** 
     * runQuery will prepare an SQL statement taken from a file to run
     */

    public ResultSet runQuery(String sql) {
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
                    results.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first
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
                        String name = rsmd.getColumnName(i);
                        String data = rs.getString(i);
                        firstColData.add(i-1, data);
                        
                        if (headerWidth > data.length())
                            colWidth = headerWidth;
                        else
                            colWidth = data.length();
                        if (name.length() > colWidth)
                            colWidth = name.length();
                        colWidths.add(i-1, colWidth);
                        System.out.print(name);
                        // to make the columns the right width
                        for (int j = 0; j<=(colWidth - name.length()); j++)
                        System.out.print(" ");
                    }
                    System.out.println();
                    for (int i = 1; i <= colCount; i++) {
                        String data = firstColData.get(i-1);
                        System.out.print(data);
                        for (int j = 0; j<=(colWidths.get(i-1) - data.length()); j++)
                        System.out.print(" ");                        
                    }
                    System.out.println();
                    heading = false;
                } else {
                    for (int i = 1; i <= colCount; i++)
                        {colWidth = colWidths.get(i-1);  
                        //System.out.println("column width" + colWidth);
                        System.out.print(rs.getString(i));
                        //to make the columns line up
                        for (int j = 0; j<=(colWidth - rs.getString(i).length()); j++)
                        { System.out.print(" "); }
                    }      
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println("Connection not closed.");
            e.printStackTrace();
        }
    }


}
