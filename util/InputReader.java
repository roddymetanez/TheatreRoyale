package util;

import java.util.Scanner;

public class InputReader {
    private Scanner scanner;
    
    public InputReader(){
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Print a message to the console and return the next integer value entered by the user
     * 
     * @param message to print to console
     * @return integer user inputs
     */
    public int getNextInt(String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    /**
     * Print a message to the console and return the next string value entered by the user
     * 
     * @param message to print to console
     * @return String user inputs
     */
    public String getNextText(String message) {
        System.out.print(message);
        return scanner.next();
    }

}
