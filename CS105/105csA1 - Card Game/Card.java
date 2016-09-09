/*
 * Name:Ksenia Kovaleva
 * UPI:kkov003
 * Date: S2 2012
 * Purpose: Card class used in the A1 game of solitaire
 */

/*
 8 Marks
 */

import java.awt.*; 
import javax.swing.*;


public class Card {    
  private int suit;
  private int value;
  private Rectangle cardArea;
  private boolean hasBeenRemoved;
  private boolean isFaceUp;
  
  public Card(int value, int suit) { 
    
    this.value = value;
    this.suit = suit;
    
    hasBeenRemoved = false; 
    isFaceUp = false; //initialised as false
    cardArea = new Rectangle(0,0,0,0);
    
    
    
  }  
//-------------------------------------------------------------------
//-------- Accessor and mutator methods -----------------------------
//-------------------------------------------------------------------  
  public int getSuit() {    
    return suit; //returns suit  
  }
  
  public int getValue() {  
    return value; //returns card value   
  }
  
  public void setSuit(int suit) {  
    this.suit = suit; // sets a new suit
    
    
  }
  
  public void setValue(int value) {  
    this.value = value; // sets new card value
    
  }
  
  public boolean getHasBeenRemoved() {  
    return hasBeenRemoved; //checks if a card has been removed  
  }
  
  public void setHasBeenRemoved(boolean removed) {  
    hasBeenRemoved = removed;
    
  }
  
  public boolean getIsFaceUp() {  
    return isFaceUp; // returns if it is face up    
  }
  
  public void setIsFaceUp(boolean faceUp) {  
    isFaceUp = faceUp; //changes whether card is face up
    
  }
  
  public void setCardArea(int x, int y, int w, int h) {  
    
    cardArea.x = x;
    cardArea.y = y;
    cardArea.width = w;
    cardArea.height = h;
    
    
    //cardArea.setBounds(x,y,w,h); 
// sets new rectangle parameters for cardArea
    
  }
  
  public Rectangle getCardArea() {  
    return cardArea; //returns cardArea parameters    
  }
//-------------------------------------------------------------------
//-------- Returns true if the parameter Point object ---------------
//-------- is inside the Card area. --------------------------------
//-------------------------------------------------------------------  
  public boolean pressPointIsInsideCard(Point pressPt) {
    
    if (hasBeenRemoved == true){
      return false; // if card is removed then point pressed isn't inside card
    }if (cardArea.contains(pressPt)){ // card contains press pt then it is inside card ELSE IF OR IF?
      return true;
    }else{ // otherwise/ press pt isn't inside card then false
      return false;
    }
    
    
    
  }
//-------------------------------------------------------------------
//-------- Get String describing the card suit and value ------------
//-------------------------------------------------------------------
  public String getCardStatusInformation() { 
    String cardInfo = ""+ value +" "+ suit +" "+cardArea.x+" "+ cardArea.y+ " "+hasBeenRemoved+" "+isFaceUp;
    
    return cardInfo; // returns string with value, suit, x, y,  card removed, face up
  }
//-------------------------------------------------------------------
//-------- Draw the Card object. ------------------------------------
//-------------------------------------------------------------------  
  public void drawCard(Graphics g, JComponent theJPanelInstance) {  
    Image cardPic;
    int fileNumber;
    
    if (hasBeenRemoved) {
      return;
    } 
    
    if (isFaceUp) {
      fileNumber = suit * A1JPanel.CARDS_IN_EACH_SUIT + value;
      cardPic = CardImageLoadUp.getSingleCardImage(fileNumber);   
    } else {
      cardPic = CardImageLoadUp.getFaceDownCardImage();
    }
    
    
    g.drawImage(cardPic, cardArea.x, cardArea.y, theJPanelInstance);    
  }  
//-------------------------------------------------------------------
//-------- Get String describing the card suit and value ------------
//-------------------------------------------------------------------
  public String toString() { 
    final String[] SUITS = {"CLUBS", "DIAMONDS", "HEARTS", "SPADES"};
    if (value == 0) {
      return "A" + " " + SUITS[suit];
    } else if (value == 12) {
      return "K" + " " + SUITS[suit];
    } else if (value == 11) {
      return "Q" + " " + SUITS[suit];
    } else if (value == 10) {
      return "J" + " " + SUITS[suit];
    }
    
    return (value + 1)  + " " + SUITS[suit];
  } 
} 
