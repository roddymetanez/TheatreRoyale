/**
 * 
 */
package FrontEnd;

/**
 * @author clayton
 *
 */
import java.util.Scanner; // Import the Scanner class

public final class InputScanner {

	private Scanner scanIn;

	public InputScanner() {
		// Empty Scanner
	}

	public String scanText() {

		Scanner scanIn = new Scanner(System.in); // Scanner closed during shutdown
		// scanIn.nextLine();
		String tmpString = scanIn.nextLine();
		return tmpString;
	}

	public void closeScanner() {
		Scanner scanIn = new Scanner(System.in); // (RE)Create a Scanner object
		// and then close it
		scanIn.close();
	}

}
