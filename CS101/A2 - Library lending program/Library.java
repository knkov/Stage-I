/** Name: Ksenia Kovaleva
  * ID:4716583
  * COMPSCI 101 Library class - storing info and functions of a simple library
  * For Assignment 2
  */

import java.io.*;
import java.util.Calendar;

public class Library {
  private static final int MAX_SIZE = 1000;   // a constant for maximum array size
  private BookItem[] books;                   // stores the inforamtion of books in the library
  private int size;                           // the total numbers of books in the library
  
  // the constructor of the Library class
  public Library (){
    books = new BookItem[MAX_SIZE];
    size = 0;
  }
  
  //  this method borrows a book based on user input code and UPI;
  public void borrowBook(){
    
    System.out.print("Please enter the book code to borrow: ");
    String bookCode = Keyboard.readInput();
    BookItem item =findBook(bookCode);
    if (item != null){ // if the book has been found/ book exists in registry
      String status = item.getStatus(); //get status
      
      
      
      
      if(status != null){ // if item is on loan
        String title = item.getTitle(); // get title
        String returnDate = item.getReturnDate(); //get return date
        System.out.println("The book - "+ title + " is on loan.");
        System.out.println("It will be returned by "+ returnDate+".");
      }else{// status is null so book is available
        String title = item.getTitle(); // get book title 
        System.out.println("The book - "+ title + " is available.");
        System.out.print("Please enter the UPI to borrow: ");
        String userUpi = Keyboard.readInput();
        item.setStatus(userUpi);
        String borrowedBy = item.getStatus();
        
        
        String returnDate = getReturnDate();
        
        item.setReturnDate(returnDate);
        item.getReturnDate();
        
        System.out.println("The book - "+ bookCode + " is borrowed by " + borrowedBy+".");
      }
      
    }else{ 
      System.out.println("The book - "+ bookCode+ " is not in the collection.");
    }
    
    
    
    
  }
  
  // this method returns a book based on a user input code;
  public void returnBook(){
    System.out.print("Please enter the book code to return: ");
    String bookCode = Keyboard.readInput();
    BookItem item =findBook(bookCode);
    if (item != null){// item is in the collection
      String status = item.getStatus();
      String title = item.getTitle();
      if(status != null){ // book is on loan
        System.out.println("Thank you for returning the book - " + title+ ".");
        item.setStatus(null); // set status to null as book returned
      }else{// book is not on loan
        System.out.println("The book - "+ title+ " has not been borrowed.");
      }
    }else{ //item is not in the collection
      System.out.println("The book - "+ bookCode+ " is not in the collection.");
    }
    
  }
  
  // this method searches a book based on a user input title;
  public void searchByTitle(){ 
    System.out.println("Please enter the book title that you would like to search:");
    String searchTerm = Keyboard.readInput();
    BookItem item = null;
    int count = 0;
    
    for ( int i = 0; i< size; i++){ 
      item = books[i];
      
      
      if (item != null && item.getTitle().equals(searchTerm)){// item matches a title
        System.out.println("Record found in the collection:");
        
        count++;
        
        
        String code =item.getCode(); // code
        String title = item.getTitle(); //title
        String status = item.getStatus(); // status
        String returnDate = item.getReturnDate(); // return date
        
        System.out.println("Code: " + code);
        System.out.println("Title: " + title);
        if ( status != null){
          System.out.println("Status: On loan");
          System.out.println("Return Date: "+ returnDate);
        }else{
          System.out.println("Status: Available");
        }
        
        
        
      } 
      
      
      
    } if( count == 0){
      System.out.println("Sorry, this book is not in the collection.");
    }
  }
  
  
  
  
  
  
  
  
  
  
  
  // this method displays all the books in the library
  
  public void displayBooks(){
    
    System.out.println("----------------List of books in the collection---------------");
    System.out.println("--------------------------------------------------------------");
    BookItem item = null;
    for ( int i = 0; i< size; i++){ 
      item = books[i];
      String code =item.getCode(); 
      String title = item.getTitle(); 
      String status = item.getStatus(); 
      String returnDate = item.getReturnDate(); 
      
      System.out.println("Code: " + code);
      System.out.println("Title: " + title);
      if ( status != null){ //book on loan
        System.out.println("Status: On loan");
        System.out.println("Return Date: "+ returnDate);
        System.out.println("************************");
      }else{// book not on loan aka status set to null
        System.out.println("Status: Available");
        System.out.println("************************");
      }
      
    }
  }
  
  // this method displays books borrowed by a given user;
  
  public void displayRecords(){
    
    System.out.print("Please enter the UPI of borrowing: ");
    String userUpi = Keyboard.readInput();
    int count = 0;
    BookItem item;
    
    for (int i = 0; i< size; i++){ 
      item = books[i];
      String status = item.getStatus();
      if(status != null){
        if (item!=null && status.equals(userUpi)){
          System.out.println("Code: "+ item.getCode());//get and print code
          System.out.println("Title: "+item.getTitle());// get and print title
          System.out.println("Return Date: " + item.getReturnDate());//get and print return date
          System.out.println("************************");
          
          count++;
          
          
          
        }
      }
      
    }if (count == 1){
      System.out.println("There is "+ count +" book borrowed by "+userUpi+".");
    }else if(count > 1){
      System.out.println("There are "+ count + " books borrowed by " + userUpi+".");
    }else{
      System.out.println("There is no record of books borrowed by "+userUpi+".");
    }
  }
  
  
  
  
  
  /** -------------------------------------------------
    *  DO NOT CHANGE ANY CODE IN THE FOLLOWING METHOD  *
    * --------------------------------------------------
    */
  // this method reads the book file and stores the books in an array of type BookItem
  public void loadBooks(String filename){  
    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String theLine;
      while ((theLine = br.readLine()) != null) {
        String[] parts = theLine.split(",");
        books[size] = new BookItem(parts[0],parts[1]);
        if (parts.length > 2) {
          books[size].setStatus(parts[2]);
          books[size].setReturnDate(parts[3]);
        }
        size++;
      }
    }
    catch (IOException e) {
      System.err.println("Error: " + e);
    }
  }
  
  /** -------------------------------------------------
    *  DO NOT CHANGE ANY CODE IN THE FOLLOWING METHOD  *
    * --------------------------------------------------
    */
  // this method save all the books back into a text file 
  public void saveBooks(String filename){  
    try{
      PrintWriter pw = new PrintWriter(new FileWriter(filename));
      for (int i=0; i<size; i++){
        pw.println(books[i]);
      }
      pw.close();
    }
    catch (IOException e){
      System.err.println("Error: " + e);
    }
  }
  
  /** -------------------------------------------------
    *  DO NOT CHANGE ANY CODE IN THE FOLLOWING METHOD  *
    * --------------------------------------------------
    */
  // this method finds a book item based on the code
  public BookItem findBook(String code){
    BookItem item;
    for (int i=0; i<size; i++){
      item = books[i];
      if (item!=null && item.getCode().equals(code)) 
        return item;
    }
    return null;
  }
  
  /** -------------------------------------------------
    *  DO NOT CHANGE ANY CODE IN THE FOLLOWING METHOD  *
    * --------------------------------------------------
    */
  // this method computes a return date for a borrowing
  private String getReturnDate(){
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, 14);
    String retdate = cal.get(Calendar.DAY_OF_MONTH) +"/"+ (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.YEAR);
    return retdate;
  }
}


