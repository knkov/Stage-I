/** Name: Ksenia Kovaleva
  * ID:4716583
  * 
  * COMPSCI 101 example class - storing info for a book
  * For Assignment 2
  */

public class BookItem {
  private String code;  // the book code
  private String title;       // the book title
  private String status;  // the book status
  private String retdate;  // the return date, if borrowed
  
  // the constructor of the BookItem class
  
  public BookItem (String code, String title){
    this.code = code;
    this.title = title;
    this.status = null;
    this.retdate = null;
  }
  
  // accessor and mutator methods
  
  public String getCode(){
    return code;
  }
  
  public void setCode(String code){
    this.code = code;
  }
  
  public String getTitle(){
    return title;
  }
  
  public void setTitle(String title){
    this.title = title;
  }
  
  public String getStatus(){
    return status;
  }
  
  public void setStatus(String status){
    this.status = status;
  }
  
  public String getReturnDate(){
    return retdate;
  }
  
  public void setReturnDate(String retdate){
    this.retdate = retdate;
  }
  
  // the string representation of a BookItem object
  
  public String toString(){
    String item = "";
    item = code + "," + title;
    if (status != null) {
      item += "," + status + "," + retdate;
    }
    return item;
  }
}