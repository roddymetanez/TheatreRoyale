package util;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


/**
 *  Parser provides an intereface to  file
 */
public class Parser
{
    private Scanner scanner;
    /**
     * Constructor for objects of class Parser
     */
    public Parser()
    {
    }

    /**
     * open will open the specified file if it exists
     * @param queryFile String - the name of the file to be scanned
    */
    public void open(String queryFile) {
        try {
        scanner = new Scanner(new File(queryFile));}
        catch (FileNotFoundException e) { 
            System.out.println("No such File");
            return;
        }
        // setting the delimiter between strings to ';'
        scanner.useDelimiter(";");
    }
    
    /**
     * getQuery will get the next SQL query from the open file 
     * or return null if the end has been reached
     * @return String - the next SQL statement from the file 
     */
    public String getQuery() {
        String query = null;
        if (scanner.hasNext()) {
            // skipping white space between queries
            query = scanner.next().stripTrailing();
            if (query.length() == 0)
                query = null;
        }
        return query;
    }
    
    /**
     * close will close the file
     */
    public void close() {
        scanner.close();
    }
}
