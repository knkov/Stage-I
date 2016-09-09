/** Name: Ksenia Kovaleva
  * ID:4716583
  * 
  * A simple library program that can assist
  * the borrowing/returning/searching of books.
  **/

public class AssignmentTwo {
  
  // this method manages the entire library program
  public void start() {
    final String UPI = "kkov003";        // a constant for student UPI
    
    Library lib = new Library();
    lib.loadBooks("books.txt");
    
    System.out.println("==============================================================");
    System.out.println("---------This is a simple library program by " + UPI + ".---------");
    
    
    
    int userChoice = -1;
    boolean userHasFinished = false;
    
    while (!userHasFinished){
      displayMainMenu();
      userChoice = getChoice(1,4);
      
      
      if (userChoice == 1){
        lending(lib); 
        
        
      }else if (userChoice ==2){
        lib.displayBooks();
        
      }else if(userChoice == 3){
        lib.displayRecords();
        
      }else if(userChoice == 4){
        userHasFinished = true;
      }
      
    }
    
    
    
    lib.saveBooks("books2.txt");
    System.out.println("--------------------------------------------------------------");
    System.out.println("----------------Thank you for reading with us!----------------");
    System.out.println("=============================================================="); 
    
  }
  
  //-----------------------------------------------
  // MANAGING THE SECOND LEVEL MENU
  //-----------------------------------------------
  private void lending(Library lib) {
    int userChoice = -1;
    
    while (userChoice!= 4){
      displaySubMenu(); 
      userChoice = getChoice(1,4);
      
      if (userChoice == 1){
        lib.searchByTitle();
      }else if (userChoice == 2){
        lib.borrowBook();
      }else if (userChoice == 3){
        lib.returnBook();
      }
    }
    
    
    
    
    
    
    
    
    
    
  }
  
  //-----------------------------------------------
  // DISPLAYING THE TOP LEVEL MENU
  //-----------------------------------------------
  private void displayMainMenu() {
    System.out.println("--------------------------------------------------------------");
    System.out.println("1. Lending Services");
    System.out.println("2. Display All Books");
    System.out.println("3. Show Borrowing Records");
    System.out.println("4. Exit System");
    System.out.println("--------------------------------------------------------------");
    
    
  }
  
  //-----------------------------------------------
  // DISPLAYING THE SUB-LEVEL MENU
  //-----------------------------------------------
  private void displaySubMenu() {
    System.out.println("--------------------------------------------------------------");
    System.out.println("1.Search a book.");
    System.out.println("2.Borrow a book.");
    System.out.println("3.Return a book.");
    System.out.println("4.Back to main menu.");
    System.out.println("--------------------------------------------------------------");
    
    
    
  }
  
  //-----------------------------------------------
  // GETTING THE USER CHOICE
  //-----------------------------------------------
  private int getChoice(int lower, int upper) { // l and u
    System.out.print("Please enter your choice: ");
    int input = Integer.parseInt(Keyboard.readInput());
    
    
    
    while (input > upper || input < lower){
      input = getChoice(1,4);
      
    }
    
    
    return input;
  }
  
}