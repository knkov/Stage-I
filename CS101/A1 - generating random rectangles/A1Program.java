import java.awt.Rectangle;


/*
 *Ksenia Kovaleva
 * kkov003 
 * Assignment One for Compsci101
 */
public class A1Program {
  
  public static final int PRINT_WELCOME = 1;
  public static final int CREATE_RECTANGLE = 2;
  public static final int DISPLAY_COORDINATES = 3;
  public static final int DISPLAY_DIMENSIONS = 4;
  public static final int CHECK_POINT = 5;
  public static final int CHECK_RECTANGLE = 6;
  public static final int COMPUTE_INTERSECTION = 7;
  public static final int COMPUTE_UNION = 8;
  public static final int EXIT = 0;
  public static final int MAXSIZE = 100;
  
  
  public void start() {
    Rectangle randRectangle = null;
    int selection = -1;
    boolean userHasFinished = false;
    
    
    while (! userHasFinished) {
      printMenu();
      int userSelection = getUserSelection();
      
      
      
      
      
      
      
      System.out.println("--------------------------------------------------------");
      System.out.println("--------------------------------------------------------");
      if (userSelection == PRINT_WELCOME) {
        printWelcome();
        
      } else if (userSelection == CREATE_RECTANGLE) {
        randRectangle = createRectangle();
        
      } else if (userSelection == DISPLAY_COORDINATES) {
        displayCoordinates(randRectangle);
        
      } else if (userSelection == DISPLAY_DIMENSIONS) {
        displayDimensions(randRectangle);
        
      } else if (userSelection == CHECK_POINT) {
        checkPoint(randRectangle);
        
      } else if (userSelection == CHECK_RECTANGLE) {
        checkRectangle(randRectangle);
        
      }  else if (userSelection == COMPUTE_INTERSECTION) {
        computeIntersection(randRectangle);
        
      }  else if (userSelection == COMPUTE_UNION) {
        computeUnion(randRectangle);
        
      } else if (userSelection == EXIT) {
        userHasFinished = true;
      }
      if (!userHasFinished) {
        System.out.println("--------------------------------------------------------");
        System.out.println("--------------------------------------------------------");
      }
    }
  }
  
  
  
  
  
  private void printMenu() {
    System.out.println("Choose Action by typing:");
    System.out.println("0 - Exit");
    System.out.println("1 - Print Welcome");
    System.out.println("2 - Create a new random rectangle");
    System.out.println("3 - Display the coordinates of the random rectangle");
    System.out.println("4 - Display the dimensions of the random rectangle");
    System.out.println("5 - Check whether the random rectangle contains the specified location (x,y)"); 
    System.out.println("6 - Check whether the random rectangle contains the specified rectangle");
    System.out.println("7 - Compute the intersection of the random rectangle with the specified rectangle");
    System.out.println("8 - Compute the union of the random rectangle with the specified rectangle");
    System.out.println();    
  }
  
  private void printWelcome() {
    System.out.println("Welcome! This program is written by kkov003.");    
  }
  
  private Rectangle createRectangle() {
    int x = (int)(Math.random()*(MAXSIZE+1));
    int y = (int)(Math.random()*(MAXSIZE+1));
    int width =(int)(Math.random()*(MAXSIZE)+1);
    int height =(int)(Math.random()*(MAXSIZE)+1);
    Rectangle randRectangle = new Rectangle(x, y,width, height);
    System.out.println("A random rectangle has been created.");
    return randRectangle; 
  }
  
  
  private void displayCoordinates(Rectangle randRectangle) {
    if (randRectangle != null){
      int x = (int)randRectangle.getX();
      int y = (int)randRectangle.getY();
      
      System.out.println("The random rectangle is set at the location ("+x+","+y+")");
      
    } else{
      System.out.println("The random rectangle is null");
    }
    
  }
  
  private void displayDimensions(Rectangle randRectangle) {
    if (randRectangle != null){
      int width =(int)randRectangle.getWidth();
      int height =(int)randRectangle.getHeight();
      
      
      System.out.println("The dimensions of the random rectangle are "+ width+" (W) x "+height+" (H)");
      
    }else{
      System.out.println("The random rectangle is null");
    }
    
  }
  
  private void checkPoint(Rectangle randRectangle) {
    if (randRectangle != null){
      
      System.out.print("Enter x-coordinate: ");
      int userX = Integer.parseInt(Keyboard.readInput());
      System.out.print("Enter y-coordinate: ");
      int userY = Integer.parseInt(Keyboard.readInput());
      if (randRectangle.contains(userX,userY)){
        System.out.println("The random rectangle contains the specified location ("+ userX +"," + userY +")");
      }else{
        System.out.println("The random rectangle does not contain the specified location ("+userX+","+ userY +")");
      }
    }else{
      System.out.println("The random rectangle is null");
    }
    
  }
  
  
  private void checkRectangle(Rectangle randRectangle) {
    if (randRectangle != null){
      
      Rectangle userRectangle = promptForRectangle();
      if (randRectangle.contains(userRectangle)){
        System.out.println("The random rectangle contains the specified rectangle " + userRectangle);
      }else{
        System.out.println("The random rectangle does not contain the specified rectangle " + userRectangle);
      }
    }else{
      System.out.println("The random rectangle is null");
    }
  }
  
  
  private void computeIntersection(Rectangle randRectangle) {
    if (randRectangle !=null){
      Rectangle userRectangle = promptForRectangle();
      Rectangle intersectionRectangle = randRectangle.intersection(userRectangle);
      if (intersectionRectangle.intersects(randRectangle)){
        System.out.println(" The intersection is "+ intersectionRectangle);
      }else {
        System.out.println(" No intersection.");
      }
    }else{
      System.out.println("The random rectangle is null");
    }
  }
  
  
  
  
  
  private void computeUnion(Rectangle randRectangle) {
    if (randRectangle != null){
      Rectangle userRectangle = promptForRectangle();
      Rectangle unionRectangle = randRectangle.union(userRectangle);
      System.out.println(" The union is " + unionRectangle);
    }else{
      System.out.println("The random rectangle is null");
    }
    
    
  }
  
  
  private Rectangle promptForRectangle() {
    System.out.print("Enter x-coordinate: ");
    int x = Integer.parseInt(Keyboard.readInput());
    System.out.print("Enter y-coordinate: ");
    int y = Integer.parseInt(Keyboard.readInput());
    System.out.print("Enter width: ");
    int width = Integer.parseInt(Keyboard.readInput());
    System.out.print("Enter height: ");
    int height = Integer.parseInt(Keyboard.readInput());
    return new Rectangle(x,y,width,height);
  }
  
  
  private int getUserSelection() {
    System.out.print("        Enter selection: ");
    String userSelection = Keyboard.readInput();
    return Integer.parseInt(userSelection);
  }
  
}


