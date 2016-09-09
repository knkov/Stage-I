/* Name:Ksenia Kovaleva
 * UPI: kkov003
 * Date: S2 2012
 * Purpose: JPanel for the A1 game of Solitaire
 */

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class A1JPanel extends JPanel implements MouseListener, KeyListener {
  public static final int CARDS_IN_EACH_SUIT = A1Constants.CARDS_IN_EACH_SUIT;
  private static final int TOTAL_NUMBER_OF_CARDS = A1Constants.TOTAL_NUMBER_OF_CARDS;
  
  private static final int NUMBER_OF_ROWS = A1Constants.NUMBER_OF_ROWS;
  private static final int NUMBER_OF_COLS = A1Constants.NUMBER_OF_COLS;
  
  private static final Color BACKGROUND_COLOUR = new Color(233, 0, 211); 
  private static final int CARD_DISPLAY_LEFT = A1Constants.CARD_DISPLAY_LEFT;
  
  private ArrayList<Card> cardStack;
  private Card[][] cards;
  
  private Card userCard;
  //card which is face down
  //and covers the card stack
  private Card aFaceDownCard;
  
  private int cardWidth;
  private int cardHeight;
  
  private int numberRemoved; 
  
  private boolean noMoreTableCards;
  private boolean noMoreAvailableMoves;
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
// Stage 9  (10 Marks) variables used for scoring the game 
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
  private int userScore;
  private int pointsToAdd;
  
  
  public A1JPanel() {
    setBackground(BACKGROUND_COLOUR);
    loadAllCardImagesAndSetUpCardDimensions();
    
    addKeyListener(this);
    addMouseListener(this);
    
    reset();
  }
  
  private void reset() {
    int randomPosition;
    cardStack = createTheFullPack();
    cards = getRandomTableCards(cardStack);
    
    setUpVisibleRowOfCards(cards);
    
    randomPosition = (int) (Math.random() * cardStack.size());
    userCard = cardStack.remove(randomPosition);
    setUpCardPosition(userCard, cardWidth * 8, cardHeight * 5, true);
    
    aFaceDownCard = new Card(0, 0);
    setUpCardPosition(aFaceDownCard, cardWidth * 6, cardHeight * 5, false);
    
    numberRemoved = 0; 
    userScore = 0;
    pointsToAdd = 1;
    
    noMoreTableCards = false;
    noMoreAvailableMoves = false;
    
  }
  
  private void setUpCardPosition(Card card, int x, int y, boolean isFaceUp) {
    card.setCardArea(x, y, cardWidth, cardHeight);
    card.setIsFaceUp(isFaceUp);  
  }
//--------------------------------------------------------------------- 
// Handle KeyEvents
// Stage 5  (6 Marks), DONE
// Stage 10 (2 Marks) DONE
//and 
// part of Stage 11 (8 Marks) DONE
//--------------------------------------------------------------------- 
  public void keyPressed(KeyEvent e) {
    if (e.getKeyChar()=='n' || e.getKeyChar() == 'N'){
      reset(); // new game
    }else if(e.getKeyChar()=='s' || e.getKeyChar() == 'S'){
      writeToFile(); //save game
    }else if( e.getKeyChar() == 'l' || e.getKeyChar() == 'L'){
      loadFromFile(); //load last saved game
    }
    repaint();
  }
  
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
//--------------------------------------------------------------------- 
// Handle MouseEvents
//--------------------------------------------------------------------- 
  public void mousePressed(MouseEvent e) {  
    Card selectedCard;
    int selectedCardRow;
    int selectedCardCol;
    int randomPosition;
    
    
    
    
    if (noMoreTableCards || noMoreAvailableMoves) {
      return;
    }
    
    Point pressPt = e.getPoint();
    Point rowColOfSelectedCard = getRowColOfSelectedCard(pressPt);
    
    
    if (rowColOfSelectedCard != null) {
      selectedCardRow = rowColOfSelectedCard.x;
      selectedCardCol = rowColOfSelectedCard.y;
      selectedCard = cards[selectedCardRow][selectedCardCol];
      
      if (selectedCard.getIsFaceUp() && haveValueDifferenceOfOne(userCard, selectedCard)) {
        userCard.setValue(selectedCard.getValue());
        userCard.setSuit(selectedCard.getSuit());
        
        selectedCard.setHasBeenRemoved(true);
        numberRemoved++;
        
        userScore = userScore + pointsToAdd;
        pointsToAdd++;
        
        
        
        
      
      
      
      cards[selectedCardRow][selectedCardCol] = null;
      revealNeighbouringCards(selectedCard.getCardArea(), selectedCardRow);
    } 
    }
    
    
    
    
    if (cardStack.size() > 0 && packCardHasBeenPressed(pressPt)) {
      randomPosition = (int) (Math.random() * cardStack.size());
      userCard = cardStack.remove(randomPosition); 
      
      setUpCardPosition(userCard, cardWidth * 8, cardHeight * 5, true);
      userScore -= 5;
      pointsToAdd = 1;
    
      
      
      if (noMoreTableCards()==true) {
        noMoreTableCards = true;
      } else if (userIsStillAbleToWin(userCard) == false) {
        noMoreAvailableMoves = true;
      }
    
    
    
    
    
    //Stage 9 done above (scoring)
    
    
    repaint();
  }
  }
  
  private boolean haveValueDifferenceOfOne(Card userCard, Card selectedCard) {
    int userValue = userCard.getValue();
    int selectedValue = selectedCard.getValue();
    int diff = Math.abs(userValue - selectedValue);
    
    return diff == 1 || diff == 12;
  }
  public void mouseClicked(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {} 
//--------------------------------------------------------------------- 
// Stage 6 (8 Marks) DONE
//--------------------------------------------------------------------- 
  private Point getRowColOfSelectedCard(Point pressPt) {
    
    Card card;
    
    for( int row = 0; row < NUMBER_OF_ROWS; row++){
      for(int col = 0; col < NUMBER_OF_COLS; col++){
        card = cards[row][col];
        
        if(card != null){ // if card is not null then test
          if(card.getIsFaceUp() == true){ // card is face up
            if(card.pressPointIsInsideCard(pressPt)){ // press pt is inside card
              Point cardLocationInArray = new Point (row, col); // create new point
              return cardLocationInArray;
            }
          }
        }
      }
    }
    return null;  
  }
  
  private boolean packCardHasBeenPressed(Point pressPt) {
    if (aFaceDownCard.pressPointIsInsideCard(pressPt)) {
      return true;
    }
    
    return false;  
  }
//--------------------------------------------------------------------- 
// Note that before this method is executed the selected
// card has been removed from the 2D cards array, i.e., the  
// position where the selected card was in the cards array is null.
// Stage 7 (20 Marks) DONE!
//--------------------------------------------------------------------- 
  private void revealNeighbouringCards(Rectangle removedCardArea, int rowOfRemovedCard) {
    int previousRow = rowOfRemovedCard - 1;
    int nextRow = rowOfRemovedCard + 1;
    
    if (rowOfRemovedCard == 1){ // for row 1 check row 0 only
      checkRowOfNeighbours(removedCardArea, previousRow, rowOfRemovedCard);
      
    }else if (rowOfRemovedCard == 2){ // for row 2 check row 1 and row 3
      checkRowOfNeighbours(removedCardArea, previousRow,rowOfRemovedCard);
      checkRowOfNeighbours(removedCardArea,nextRow, rowOfRemovedCard);
      
    }else if (rowOfRemovedCard == 3){ // for row 3 check row 4 only
      checkRowOfNeighbours(removedCardArea, nextRow, rowOfRemovedCard);
    }
    /*
     Stage 7  DONE
     */  
  }
  
  private void checkRowOfNeighbours(Rectangle removedCardArea, int rowCurrentlyChecking, int rowNumberOfRemovedCard) {   
    //removedCardArea,row being checked, row  of removed card
    Card cardBeingChecked; // will be card being checked - (not the card removed)
    
    for (int col = 0; col< NUMBER_OF_COLS; col++){ // goes through columns of row being checked
      cardBeingChecked = cards[rowCurrentlyChecking][col]; // card that is currently being checked
      
      if(cardBeingChecked != null){ // card is not null
        Rectangle cardArea = cardBeingChecked.getCardArea(); // cardArea of card being checked
        if(removedCardArea.intersects(cardArea)){
          if(hasNoIntersectingNeighbourInRow(cardArea,rowNumberOfRemovedCard)== true){
            cardBeingChecked.setIsFaceUp(true);
          }
        } 
      }
    }
  }
  
  /*
   * 
   helper for Stage 7 
   */
  
  
  
  
  
  
  private boolean hasNoIntersectingNeighbourInRow(Rectangle areaOfCardToCheck, int rowNumberOfRemovedCard) {
    Rectangle areaToCheck; // area of card from clicked Row
    Card card; //originally called CardToCheck
    boolean hasNoCardOnTop = false;//card we are interested in has card on top from clicked row - default value
    
    for(int col = 0; col < NUMBER_OF_COLS; col++){
      card = cards[rowNumberOfRemovedCard][col]; // cycles through columns of the clicked Row
      
      if(card!= null){ 
        areaToCheck = card.getCardArea();
        if (areaToCheck.intersects(areaOfCardToCheck)){
          
          return false; //has intersecting neighbours
        }
        
      }else if (card == null){
        hasNoCardOnTop = true;
        
      }
    }
    return hasNoCardOnTop;
  }
  /*
   helper for Stage 7 
   */  
  
  
  
  
  
//--------------------------------------------------------------------- 
// Stage 8 (6 Marks) // 
//--------------------------------------------------------------------- 
  private boolean noMoreTableCards() { 
    
    Card card;
    boolean cardsRemoved = false; 
    
    
    for (int row = 0; row < NUMBER_OF_ROWS; row++){ 
      for(int col = 0; col < NUMBER_OF_COLS; col++){ 
        card = cards[row][col];
        if(card != null){
          
          return false;
          
          
        }else if (card == null){
          cardsRemoved = true;
        }
      }
    }
    
    return cardsRemoved;
  }
  
//-------------------------------------------------------------------
//-------- Draw all the CARD objects --------------------------------
//-------- Do not draw any null cards  ------------------------------
//--------------------------------------------------------------------- 
// Stage 4 (6 Marks) DONE
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawTableCards(g);  
    drawRestOfJPanelDisplay(g);  
  }
  
  private void drawTableCards(Graphics g) {
    //the order in which rows are drawn
    //i.e., row 0, then row 4, then row 1, 
    //etc.,
    int[] orderToBeDrawn = {0, 4, 1, 3, 2};
    // draw method from card class
    for(int i= 0; i< orderToBeDrawn.length; i++){
      int whichRow = orderToBeDrawn[i];
      drawRowOfCards(g, whichRow);
    }
    
    /*
     Stage 4 (6 Marks) DONE
     */
    
  } 
  
  private void drawRowOfCards(Graphics g, int whichRow) {  
    
    for( int col =0; col < NUMBER_OF_COLS; col++){
      if( cards[whichRow][col]!= null){
        cards[whichRow][col].drawCard(g, this);
      }
      
    }
  }
  
  private void drawRestOfJPanelDisplay(Graphics g) {
    userCard.drawCard(g, this);
    int numberLeftInPack = cardStack.size();
    if (numberLeftInPack > 0) {
      aFaceDownCard.drawCard(g, this);
      drawNumberInsideCardArea(g, aFaceDownCard);   
    }
    
    drawGameInformation(g); 
  }
  
  private void drawNumberInsideCardArea(Graphics g, Card aFaceDownCard) {  
    Rectangle cardArea = aFaceDownCard.getCardArea();
    int numberLeftInPack = cardStack.size();
    g.setFont(new Font("Times", Font.BOLD, 48));
    if (numberLeftInPack < 10) {
      g.drawString("" + numberLeftInPack, cardArea.x + cardArea.width / 3, cardArea.y + cardArea.height * 2 / 3);   
    } else {
      g.drawString("" + numberLeftInPack, cardArea.x + cardArea.width / 6, cardArea.y + cardArea.height * 2 / 3);   
    }
  }
  private void drawGameInformation(Graphics g) {
    g.setFont(new Font("Times", Font.BOLD, 48));
    g.setColor(Color.LIGHT_GRAY);
    String scoreMessage = " Score: " + userScore;
    
    if (noMoreTableCards) {
      scoreMessage = "No more table cards! " + scoreMessage;  
    } else if (noMoreAvailableMoves) {
      scoreMessage = "No more available moves! " + scoreMessage; 
    } else {
      scoreMessage = "Cards removed: " + numberRemoved + scoreMessage; 
    }
    g.drawString(scoreMessage, A1Constants.SCORE_POSITION.x, A1Constants.SCORE_POSITION.y);
  }
//-------------------------------------------------------------------
//-------- The parameter is a 2D array of CARD objects --------------
//-------- The method sets up the middle row as being visible -------
//--------------------------------------------------------------------- 
// Stage 3 (4 Marks) 
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
  private void setUpVisibleRowOfCards(Card[][] cards) {
    
    int row = 2;
    for( int col=0; col< NUMBER_OF_COLS; col++){
      cards[row][col].setIsFaceUp(true);
    }
    
    
    
    
    
  }
//-------------------------------------------------------------------
//-------- Create a 2D array of CARD objects and --------------------
//--- the parameter ArrayList will contain the cards which remain ---
//---  in the pack after the table cards are randomly selected ------
//--------------------------------------------------------------------- 
// Stage 2 (8 Marks) 
//--------------------------------------------------------------------- 
//---------------------------------------------------------------------
  private Card[][] getRandomTableCards(ArrayList<Card> cardStack) {
    
    final int[] NON_NULL_CARDS_IN_EACH_ROW = {4, 8, 9, 8, 4};
    Card card;
    int randomArrayListPosition;
    Card[][] displayCards = new Card[NUMBER_OF_ROWS][NUMBER_OF_COLS]; 
    
    
    for(int row = 0; row < NUMBER_OF_ROWS; row++){// processing elements row by row
      for (int col = 0; col < NON_NULL_CARDS_IN_EACH_ROW[row]; col++){
        randomArrayListPosition = (int)(Math.random() * cardStack.size()); // get random index between 0-51
        card = cardStack.get(randomArrayListPosition);
        int value = card.getValue();
        int suit = card.getSuit();
        
        displayCards[row][col] = new Card(value, suit);
        
        setupIndividualCardPosition(displayCards[row][col], row, col);
        
        cardStack.remove(randomArrayListPosition);// removing card from arraylist
        
      }
      
    }
    
    
    return displayCards;
  }
  
  private void setupIndividualCardPosition(Card card, int rowNumber, int colNumber) {
    final int CARD_DISPLAY_TOP = 60;
    final int DISPLAY_GAP = 6;
    
    int leftPositionForRow = getLeftPositionForRow(rowNumber);
    
    int y = CARD_DISPLAY_TOP + (cardHeight * 3 / 4) * rowNumber;
    int x = CARD_DISPLAY_LEFT + leftPositionForRow + (cardWidth + DISPLAY_GAP - 1) * colNumber;
    
    if (rowNumber == 0 || rowNumber == NUMBER_OF_ROWS - 1) {
      x = CARD_DISPLAY_LEFT + leftPositionForRow + (cardWidth + DISPLAY_GAP - 1) * 2 * colNumber;
    }
    
    card.setCardArea(x, y, cardWidth, cardHeight);
  }
  
  private int getLeftPositionForRow(int rowNumber) {
    if (rowNumber == 0 || rowNumber == NUMBER_OF_ROWS - 1) {
      return CARD_DISPLAY_LEFT + cardWidth;
    }
    if (rowNumber == 1 || rowNumber == NUMBER_OF_ROWS - 2) {
      return CARD_DISPLAY_LEFT + cardWidth / 2;
    } 
    
    return CARD_DISPLAY_LEFT; 
  }
//---------------------------------------------------------------------
//-------- Write To File ----------------------------------------------
//--------------------------------------------------------------------- 
// Write the current game information to the SavedGame.txt file
// private Card aFaceDownCard; doesn't need to be stored
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
  private void writeToFile() {
    String fileName = "SavedGame.txt";
    PrintWriter pW = null;
    Card card; 
    try {
      pW = new PrintWriter(fileName);
      pW.println(cardWidth);
      pW.println(cardHeight);
      pW.println(userCard.getCardStatusInformation());
      
      for (int i=0; i < NUMBER_OF_ROWS; i++) {
        for (int j=0; j < NUMBER_OF_COLS; j++) {
          if (cards[i][j] == null) {
            pW.println("null");
          } else {
            pW.println(cards[i][j].getCardStatusInformation());
          }
        }
      }
      
      pW.println(cardStack.size());
      
      for (int i = 0; i < cardStack.size(); i++) {
        card = cardStack.get(i);
        pW.println(card.getCardStatusInformation());
      }
      
      
      pW.println(userScore);
      pW.println(pointsToAdd);
      pW.println(noMoreTableCards);
      pW.println(noMoreAvailableMoves);
      pW.println(numberRemoved);
      
      pW.close();
    } catch(IOException e) {
      System.out.println("Error saving game to " + fileName);
    }
  }
//---------------------------------------------------------------------
//-------- Load From File ---------------------------------------------
//--------------------------------------------------------------------- 
  
// Stage 11  (8 Marks) 
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
  public void loadFromFile() {
    
    String fileName = "SavedGame.txt";
    Scanner scan = null;
    Card card; 
    String cardInfo;
    int cardStackSize;
    
    
    try{
      File savedFile = new File(fileName);
      scan = new Scanner(savedFile);
      
      while(scan.hasNext()){
        
        cardWidth = scan.nextInt();
        cardHeight = scan.nextInt();   
        scan.nextLine();
        cardInfo = scan.nextLine();
        userCard = createACard(cardInfo, cardWidth, cardHeight);
        
        for (int i=0; i < NUMBER_OF_ROWS; i++) {
          for (int j=0; j < NUMBER_OF_COLS; j++) {
            cardInfo = scan.nextLine();
            if(cardInfo.equals("null")){
              card = null;
            }else{
              card = createACard(cardInfo, cardWidth, cardHeight);
            }
            cards[i][j] = card;      
          }
        }
        
        cardStack.clear();
        cardStackSize = Integer.parseInt(scan.nextLine());
        
        for (int i = 0; i < cardStackSize; i++){
          cardInfo = scan.nextLine();
          card = createACard(cardInfo,cardWidth,cardHeight);
          cardStack.add(i,card);
          
          
        }
        userScore = scan.nextInt();
        pointsToAdd = scan.nextInt();
        noMoreTableCards = scan.nextBoolean();
        noMoreAvailableMoves = scan.nextBoolean();
        numberRemoved = scan.nextInt();  
      }
      scan.close();
    }catch( IOException e){
      System.out.println("Error loading game from" + fileName);
    }
    /* 
     Stage 11  (8 Marks)
     */ 
    
  }
  
  private Card createACard(String info, int width, int height) {
    Card card;
    int suit, value, x, y;
    boolean removed, faceUp;
    
    Scanner scanInfo = new Scanner(info);
    value = scanInfo.nextInt();
    suit = scanInfo.nextInt();
    x = scanInfo.nextInt();
    y = scanInfo.nextInt();
    removed = scanInfo.nextBoolean();
    faceUp = scanInfo.nextBoolean();
    
    card = new Card(value, suit);
    
    card.setCardArea(x, y, width, height);
    card.setIsFaceUp(faceUp);
    card.setHasBeenRemoved(removed);
    
    return card;
  }
//-------------------------------------------------------------------
//------ Create an ArrayList of a full pack of CARD objects ---------
//-------------------------------------------------------------------
  private ArrayList<Card> createTheFullPack() {  
    ArrayList<Card> theCards = new ArrayList <Card> (TOTAL_NUMBER_OF_CARDS);
    int suitNum = A1Constants.CLUBS;
    int cardValue = 0;
    
    for (int i = 0; i < TOTAL_NUMBER_OF_CARDS; i++) {
      theCards.add(new Card(cardValue, suitNum));
      
      if( cardValue >= CARDS_IN_EACH_SUIT - 1) {
        suitNum++;
      }
      
      cardValue = (cardValue + 1) % (CARDS_IN_EACH_SUIT);  
    }
    
    return theCards;
  }
//-------------------------------------------------------------------
//-------- Load all the CARD images ---------------------------------
//-- Set up the width and height instance variables  ----------------
//-------------------------------------------------------------------
  private void loadAllCardImagesAndSetUpCardDimensions() {
    CardImageLoadUp.loadAndSetUpAllCardImages(this);
    
    Dimension d = CardImageLoadUp.getDimensionOfSingleCard();
    cardWidth = d.width;
    cardHeight = d.height;    
  }
//--------------------------------------------------------------------- 
// Checks if there are any more moves which can still be made
//--------------------------------------------------------------------- -
  private boolean userIsStillAbleToWin(Card userCard) {
    if (cardStack.size() > 0) {
      return true;
    }
    
    int userCardValue = userCard.getValue();
    int cardValue;
    int diff;
    
    for (int i = 0; i < NUMBER_OF_ROWS; i++) {
      for (int j = 0; j < NUMBER_OF_COLS; j++) {       
        if (cards[i][j] != null) {
          cardValue = cards[i][j].getValue();
          diff = Math.abs(cardValue - userCardValue);
          if (diff == 1 || diff == 12) {
            return true;
          }     
        }      
      }
    }
    
    return false;
  }
}



