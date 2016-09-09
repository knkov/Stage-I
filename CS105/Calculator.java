/*Purpose: A simple calculator which takes a math expression (in infix form)
 * from the cmd line (with no spaces), prints the infix form,
 * converts to and prints the postfix form,
 * and returns the result of the expression as a double.
 * The simple calculator deals with brackets -> '()' and + * - / operators.
 * 
 * Author: Ksenia Kovaleva
 * UPI: kkov003
 * Date: S2 2012
 * 
 */



import java.util.*;
import java.lang.*;
import java.io.*;




public class Calculator { 
  
  public static void main(String[] args) { //main method begins
    
    String mathExpression = args[0];
    System.out.println("A simple calculator by kkov003");
    System.out.println("Evaluating...");
    System.out.println("Infix Expression: "+ mathExpression);
    
    boolean noErrors = noSyntaxErrors(mathExpression); 
    
    if(noErrors){ 
      String postFix = conversionToPostfix(mathExpression);
      System.out.println("Postfix Expression: "+ postFix);
      calculation(postFix); // gives result as a double
    } 
  } // main method ends
  
  //checks for syntax errors returns a boolean
  private static boolean noSyntaxErrors(String expression){  
    final String ALL_VALID = "0123456789+-/*()";
    StackReferenceBased stack = new StackReferenceBased();
    boolean bracketsMatch = true;
    int index = 0; // used in evaluating bracket mismatch
    
    //Checks for invalid input from user
    for(int i=0; i< expression.length(); i++){ 
      char ch = expression.charAt(i);
      if(ALL_VALID.indexOf(ch) == -1){
        System.out.println("Syntax error: Invalid input -> non-digit, spaces, or other characters.");
        return false;
      }
    } 
    
    //Checks start of expression
    char start = expression.charAt(0);
    if((isOperator(start)) || start == ')'){
      System.out.println("Syntax error: Missing operand -> invalid start of expression.");
      return false;
    }
    //Checks the rest of expression for syntax errors
    for(int i=0; i< (expression.length()-1); i++){ 
      
      char ch = expression.charAt(i);//current char
      char nextCh = expression.charAt(i+1);//next char
      
      if((Character.isDigit(ch)) && (nextCh == '(')){
        System.out.println("Syntax error: Missing operator -> digit followed by an open bracket.");
        return false;
      }
      else if(isOperator(ch)){//2 operators
        if(isOperator(nextCh)){
          System.out.println("Syntax error: Missing operand -> two consecutive operators.");
          return false;
        }
        if(nextCh == ')'){ // operator and ')' bracket
          System.out.println("Syntax error: Missing operand -> operator followed by a closed bracket.");
          return false;
        }
        if((ch == '/') && (nextCh == '0')){
          System.out.println("Syntax error: Division by zero.");
          return false;
        } 
      }
      else if(isBracket(ch)){
        if(ch =='(' && (isOperator(nextCh))){ //open bracket & operator
          System.out.println("Syntax error: Missing operand -> open bracket followed by an operator.");
          return false;
        }
        if(ch == '(' && nextCh == ')'){ //empty bracket
          System.out.println("Syntax error: Missing operand -> empty bracket.");
          return false;  
        }
        if( ch == ')'&& nextCh == '('){//brackets without operator between them
          System.out.println("Syntax error: Missing operator -> closed bracket followed by an open bracket.");
          return false;
        }
        if(ch == ')' && (Character.isDigit(nextCh))){
          System.out.println("Syntax error: Missing operator -> closed bracket followed by a digit.");
          return false;
        } 
      }  
    }
    
    //checks end of expression
    char last = expression.charAt(expression.length()-1);
    if ( last == '(' || (isOperator(last))){
      System.out.println("Syntax error: missing operand -> invalid end of expression.");
      return false;
    } 
    
    //checks bracket mistmatch
    while(bracketsMatch && (index < expression.length())){ // start while loop
      char ch = expression.charAt(index);
      
      if(ch=='('){
        stack.push(ch);
      }
      else if(ch == ')'){ 
        if(stack.isEmpty()){ 
          System.out.println("Syntax error: Bracket mistmatch -> no matching open brace.");
          bracketsMatch = false;  
        }
        else{ // stack isn't empty
          stack.pop(); 
        }
      }
      index++;
    }// end while loop
    if(!bracketsMatch){
      return false;
    }else if(!stack.isEmpty()){ // something is still in the stack
      System.out.println("Syntax error: Bracket mismatch -> too many open parentheses.");
      stack.popAll(); // will clear stack for next expression
      return false;
    }
    
    
    return true;  
  } // end of syntax error method
  
  
  private static boolean isOperator(char ch){ // helper method for syntax error checks
    if(ch =='+'|| ch == '-'|| ch =='*'||ch =='/'){
      return true;
    }else{
      return false;
    }
  }
  
  private static boolean isBracket(char ch){ // helper method for syntax error checks
    if( ch == '(' || ch == ')'){
      return true;
    }else{
      return false;
    }
  }
  
  //-------------------------
  //This method converts a given infix expression to a postfix expression
  
  //--------------------------
  
  private static String conversionToPostfix(String expression){ // converts infix to postfix
    
    String postfixExp = ""; //stores postfix 
    String multiDigit = "";// used for ID-ing and temporarily storing multidigit numbers
    StackReferenceBased stack = new StackReferenceBased();
    
    
    for (int i = 0;i < expression.length();i++){ // start for loop 
      char ch = expression.charAt(i);
    
      if(Character.isDigit(ch)){// char IS a number 
        multiDigit += ch; 
        if(i==(expression.length()-1)){ // if char is the LAST number in infix then append it to postfixExp
          postfixExp += multiDigit+" ";
        }
      }else{ // char is NOT number ( a bracket or an operator)
        
        if(!multiDigit.isEmpty()){ 
          postfixExp += multiDigit+" ";
          multiDigit = "";
        }// end multidigit if
        
        if(ch == '('){ // character is an open brackek
          stack.push(ch); 
        }else if( ch == ')'){ // character is closed bracket
          while(!(stack.peek()).equals('(')){ // while top of stack is not an open bracket
            postfixExp = postfixExp + stack.pop()+" "; 
          } //end while loop
          Object openBracket = stack.pop();// removes open bracket from stack
          
        }else if(stack.isEmpty()){ // have an operator and an empty stack 
          stack.push(ch);  
          
        }else if(!stack.isEmpty()){// operator and stack has operators or brackets
          
          while(!stack.isEmpty() && !(stack.peek()).equals('(') && (precedence(ch) <= precedence(stack.peek()))){
            postfixExp = postfixExp + stack.pop()+" ";
          }
          stack.push(ch);
        }
      }// end if char is/not digit
    } // end of for loop for reading the String expression
    while(!stack.isEmpty()){
      postfixExp = postfixExp + stack.pop()+" ";  
    }
    return postfixExp;
  } // end of conversion method
  
  
  
  private static int precedence(Object operator){//helper method for assesssing precedence of operators
    if(operator.equals('*')){ // multiplication operator
      return 1;
    }else if(operator.equals('/')){ //division operator
      return 1;
    }else if(operator.equals('-')){ //subtraction operator
      return 0;
    }else{ //addition operator
      return 0;
    }
  }
  
  
  
  private static void calculation(String postFix){
    StackReferenceBased stack = new StackReferenceBased();
    Scanner scan = new Scanner(postFix);
   
    while(scan.hasNext()){
      
      String token = scan.next();
      char op = token.charAt(0);
      if(op =='*' || op == '/' || op == '+' || op == '-'){
        
        String args2 = (stack.pop()).toString();
        double num2 = Double.parseDouble(args2); 
        String args1 = (stack.pop()).toString();
        double num1 = Double.parseDouble(args1);
        
        
        double result = compute(op, num1, num2);
        stack.push(result);
      }else{
        stack.push(token);
      }
    }
    scan.close();
    System.out.println("Result: " + stack.peek());
    stack.popAll(); // clears the stack of result- ready for next expression
  }
  
  
  
  
  private static double compute(char op, double num1, double num2){ //helper method for calculation
    
    double answer = 0;
    if (op == '*'){
      answer = num1*num2;
    }else if( op == '/'){
      answer = num1/num2;
    }else if( op == '-'){
      answer = num1-num2;
    }else if( op == '+'){
      answer = num1+num2;
    }
    return answer;    
    
  } 
} 