/*
 * Purpose: defines the JPanel
 * for the ColourMe game
 * 
 * Author: Adriana Ferraro
 * Date: S2 2012
 */

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class A2JPanel extends JPanel implements ActionListener, MouseListener, KeyListener {
  public static final int MAX_NUMBER_OF_MOVES_ALLOWED = A2Constants.MAX_NUMBER_OF_MOVES_ALLOWED;
  public static final int NUMBER_OF_ROWS = A2Constants.NUMBER_OF_ROWS;
  public static final int NUMBER_OF_COLS = A2Constants.NUMBER_OF_COLS;
  
  public static final String GAME_BEGINNING_FILENAME = "restartGame.txt";
  public static final String GAME_CURRENT_POSITION_FILENAME = "currentGameState.txt";
  
  public static final Color[] COLOURS = A2Constants.COLOURS;
  public static final Color BACKGROUND_COLOUR = new Color(250, 16, 255);
  
  public static final Font MEDIUM_SIZE_FONT = A2Constants.MEDIUM_FONT;
  public static final Point SCORE_POSITION = A2Constants.SCORE_POSITION;
  
  private int numberConnected;
  private int turnsRemaining; 
  private boolean gameHasEnded;
  private boolean userHasWon;
  private BlockOfCells cells;
  private JButton[] colourB;
  
  public A2JPanel() {
    setBackground(BACKGROUND_COLOUR);
    
    Panel colourP = getButtonArrayPanel();
    add(colourP); 
    
    addKeyListener(this);
    addMouseListener(this);
    
    reset();
  }
//-------------------------------------------------------
// reset everything ready for a new game
//-------------------------------------------------------  
  private void reset() {
    cells = new BlockOfCells();
    numberConnected = cells.getNumberOfConnectedCells();
    turnsRemaining = MAX_NUMBER_OF_MOVES_ALLOWED;
    gameHasEnded = false;
    userHasWon = false;
    storeGame(GAME_BEGINNING_FILENAME);
  }
//-------------------------------------------------------
// currentGameState.txt
// restartGame.txt
//-------------------------------------------------------  
  private void storeGame(String fileName) {
    PrintWriter pW = null;
    try{
      pW = new PrintWriter(new FileWriter(fileName));
      pW.print(gameHasEnded+" ");
      pW.print(userHasWon+" ");
      pW.print(turnsRemaining+" ");
      pW.println(numberConnected);
      pW.println(cells.colourIndexesToString());
      
      
      pW.close();
    }catch (IOException e){}
    
  }
  
  private void loadGame(String fileName) {
    Scanner scan = null;
    try {
      scan = new Scanner(new File(fileName));
      gameHasEnded = scan.nextBoolean();
      userHasWon = scan.nextBoolean();
      turnsRemaining = scan.nextInt();
      numberConnected = scan.nextInt();
      
      for(int i = 0; i < NUMBER_OF_ROWS; i++) {
        for(int j = 0; j < NUMBER_OF_COLS; j++) {
          cells.setCellColourIndex(i, j, scan.nextInt());
        }  
      }
      
      scan.close();
    } catch (IOException e) {}
  }
//-------------------------------------------------------
// Handle KeyEvents
// n - new game, 
// s - save the game at its current position, 
// l - load the game which was last saved, 
// r - reload the game from the beginning
//-------------------------------------------------------
  public void keyPressed(KeyEvent e) {
    char userC = e.getKeyChar();
    
    if (userC == 'n' || userC == 'N') {
      reset();   
    } else if (userC == 's' || userC == 'S') {
      System.out.println("Storing game state to: " + GAME_CURRENT_POSITION_FILENAME);
      storeGame(GAME_CURRENT_POSITION_FILENAME);
    } else if (userC == 'l' || userC == 'L') {
      System.out.println("Loading game state from: " + GAME_CURRENT_POSITION_FILENAME);
      loadGame(GAME_CURRENT_POSITION_FILENAME);
    } else if (userC == 'r' || userC == 'R') {
      System.out.println("Loading game from the start: " + GAME_BEGINNING_FILENAME);
      loadGame(GAME_BEGINNING_FILENAME);
    }
    
    repaint();
  }
  
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
//-------------------------------------------------------
// Handle MouseEvents
//------------------------------------------------------- 
  public void mousePressed(MouseEvent e) {}
  
  public void mouseClicked(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
//-------------------------------------------------------
// Handle ActionEvents
//------------------------------------------------------- 
  public void actionPerformed(ActionEvent e) {
    if (gameHasEnded) {
      requestFocusInWindow();
      return;
    }
    
    for (int i = 0; i < colourB.length; i++) {
      if (e.getSource() == colourB[i]) {    
        cells.updateConnectedCells(i);
        numberConnected = cells.getNumberOfConnectedCells();
        turnsRemaining--;
        gameHasEnded = gameHasEnded();
        requestFocusInWindow();
        repaint();
        return;
      }
    }  
  }
  
  private boolean gameHasEnded() {
    if (turnsRemaining == 0 && numberConnected < NUMBER_OF_ROWS * NUMBER_OF_COLS) {
      userHasWon = false;
      gameHasEnded = true;
      return true;      
    } else if (turnsRemaining >= 0 && numberConnected == NUMBER_OF_ROWS * NUMBER_OF_COLS) {
      userHasWon = true;
      gameHasEnded = true;
      return true;
    } 
    
    return false;
  }
//-------------------------------------------------------
// Draw everything
//-------------------------------------------------------
  public void paintComponent(Graphics g){
    super.paintComponent(g);  
    drawCellBlock(g);
    drawInformation(g);
    
    if (gameHasEnded) {
      drawGameEndInformation(g);
    }
  }
  
  private void drawCellBlock(Graphics g) {  
    cells.drawCells(g);
  }
  
  private void drawGameEndInformation(Graphics g) {  
    g.setFont(MEDIUM_SIZE_FONT);
    g.setColor(Color.WHITE);
    if (userHasWon) {   
      g.drawString("Congratulations.", SCORE_POSITION.x, SCORE_POSITION.y + 80);
      g.drawString("    You win!", SCORE_POSITION.x, SCORE_POSITION.y + 110);
    } else {
      g.drawString("You lose!", SCORE_POSITION.x, SCORE_POSITION.y + 80);
    }
  }
  
  private void drawInformation(Graphics g) {  
    g.setFont(MEDIUM_SIZE_FONT);
    g.drawString("Remaining turns: " + turnsRemaining, SCORE_POSITION.x, SCORE_POSITION.y - 40);
    g.drawString("Spread: " + numberConnected, SCORE_POSITION.x, SCORE_POSITION.y + 40);
  }
//-------------------------------------------------------
// Get array of button components
//-------------------------------------------------------
  private Panel getButtonArrayPanel() {
    Panel coloursPanel = new Panel();
    coloursPanel.setBackground(Color.BLACK);
    
    colourB = new JButton[COLOURS.length];
    
    for (int i = 0; i < colourB.length; i++) {
      colourB[i] = new MyColourButton("  ", i);
      
      colourB[i].addActionListener(this);
      coloursPanel.add(colourB[i]);
    }
    
    return coloursPanel;
  } 
}

