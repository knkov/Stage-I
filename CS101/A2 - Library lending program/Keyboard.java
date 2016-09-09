/* Name: Ksenia Kovaleva
 * ID:4716583
 * 
 * CompSci 101 - Keyboard Class
 * ============================
 * This class is used for input from the keyboard. 
 * YOU DO NOT NEED TO UNDERSTAND THE DETAILS OF THIS CLASS. 
 * To use this class, put it in the same directory as the source file for your program, and include the statement:
 * String input = Keyboard.readInput(); in your program. 
 * This will assign whatever is typed at the keyboard to the input variable.
 * 
 * This version tries to determine whether input is coming from the keyboard or from
 * a redirected file. If it is coming from a file it echoes the information to the screen.
 * The way it does this is to see how much data is currently available to be read from the file.
 * It assumes the keyboard has no data waiting, until the user types some in.
 */
import java.io.*;

public class Keyboard {
  
  private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  private static boolean redirected;
  
  public Keyboard() {
    try {
      redirected = (System.in).available() != 0;
    } catch (IOException e) {
      System.err.println("An error has occurred in the Keyboard constructor.");
      e.printStackTrace();
      System.exit(-1);
    }
  }
  
  public static String readInput() {
    try {
      String input = in.readLine();
      if (redirected) {
        System.out.println(input);
      }
      return input;
    } catch (IOException e) {
      System.err.println("An error has occurred in the Keyboard.readInput() method.");
      e.printStackTrace();
      System.exit(-1);
    }
    return null;
  }
}
