/*
 * Purpose: creates 
 * a Block of Cell objects for
 * the ColourMe game
 * 
 * Author: Adriana Ferraro
 * Date: S2 2012
 * Completed by: Ksenia Kovaleva
 * Date: S2 2012
 * 
 */



import java.awt.*;

public class BlockOfCells {  
  public static final int NUMBER_OF_ROWS = A2Constants.NUMBER_OF_ROWS;
  public static final int NUMBER_OF_COLS = A2Constants.NUMBER_OF_COLS;
  public static final int CELL_SIZE = A2Constants.CELL_SIZE;
  public static final Rectangle GAME_AREA = A2Constants.GAME_AREA;
  
  private Cell[][] cellBlock;
  
  public BlockOfCells() {   
    cellBlock = new Cell[NUMBER_OF_ROWS][NUMBER_OF_COLS]; 
    createBlockOfCells(cellBlock);
    
  }
  
  private void createBlockOfCells(Cell[][] cellBlock) {
    int x = GAME_AREA.x;
    int y = GAME_AREA.y;
    
    for(int i = 0; i < cellBlock.length; i++) {
      x = GAME_AREA.x;
      for(int j = 0; j < cellBlock.length; j++) {
        cellBlock[i][j] = new Cell(x, y);
        x = x + CELL_SIZE;
      }
      
      y = y + CELL_SIZE;   
    }  
  }
//-------------------------------------------------------
// Methods to do with the colour index of each cell (Stage 2)
//-------------------------------------------------------  
  public int getCellColourIndex(int row, int col) {
    int colourIndex = cellBlock[row][col].getColourIndex();
    return  colourIndex;
  }
  
  public void setCellColourIndex(int row, int col, int colourIndex) {
    cellBlock[row][col].setColourIndex(colourIndex);
    
  } // (Stage 3) Resets hasBeenVisited for all cells
  
  private void resetCellHasBeenVisited() {  
    for( int row = 0; row < NUMBER_OF_ROWS; row++){
      for( int col = 0; col < NUMBER_OF_COLS; col++){
        cellBlock[row][col].setHasBeenVisited(false);
      }
    }
    
  }
//-------------------------------------------------------
// returns the number of cells connected 
// to the top left cell (position 0, 0) (Stage 5)
//-------------------------------------------------------   
  public int getNumberOfConnectedCells() {  
    int colourIndex = getCellColourIndex(0, 0);
    resetCellHasBeenVisited();
    return getNumberOfCellsInUserBlock(0, 0, colourIndex); 
  }
  
  private int getNumberOfCellsInUserBlock(int row, int col, int colourIndex) { 
    
    int cellCount = 0; // counter for num of cells in user block
    
    
    
    if( row < 0 || row > 13 || col < 0 || col > 13){//out of bounds
      
      return 0; 
    } 
    
    if(cellBlock[row][col].getHasBeenVisited() == false){ //  cell has not been visited
      cellBlock[row][col].setHasBeenVisited(true); // now it has!
      
      if (getCellColourIndex(row,col) != colourIndex){ // colour of current cell doesn't match start cell
        
        return 0; // spread isn't increased because colours are different
        
      }else if (getCellColourIndex(row,col) == colourIndex){
        
        cellCount++; // includes the top left cell - spread is at least 1 at start of game
        
        
        cellCount += getNumberOfCellsInUserBlock(row+1, col, colourIndex); 
        cellCount += getNumberOfCellsInUserBlock(row-1,col, colourIndex); 
        cellCount += getNumberOfCellsInUserBlock(row,col+1, colourIndex);
        cellCount += getNumberOfCellsInUserBlock(row,col -1, colourIndex);
        
        
        
      }
    }
    return cellCount; //returns number of cells in user block
  }
  
  
  
  
  
  
  
//-------------------------------------------------------
// updates the colour of all cells connected 
// to the top left cell (position 0, 0), i.e.,
// have the same colour index. (Stage 4)
//-------------------------------------------------------   
  public void updateConnectedCells(int updateColourIndex) { //paramer = new colour
    int colourToChangeIndex = getCellColourIndex(0, 0);  // original colour of Start Cell (cell 0,0)
    resetCellHasBeenVisited(); //nothing has been visited yet
    updateUserAreaColours(0, 0, updateColourIndex, colourToChangeIndex); //cell (0,0) newColour, oldColour
    
  }
  
  private void updateUserAreaColours(int row, int col, int updateColourIndex, int colourToChangeIndex) { 
    
    if( row < 0 || row > 13 || col < 0 || col > 13){
      return; // arrayIndexOutOfBounds - rows & cols only btw 0-13; 
    }if(cellBlock[row][col].getHasBeenVisited() == false){ // cell has NOT been visited before and processed
      
      
      if(getCellColourIndex(row,col) != colourToChangeIndex){ // current cell colour is not the same as top left cell colour
        cellBlock[row][col].setHasBeenVisited(true);                               
        return; // stop recursion/ do nothing
      }else{
        
        
        cellBlock[row][col].setColourIndex(updateColourIndex);
        cellBlock[row][col].setHasBeenVisited(true);
        
        updateUserAreaColours(row +1, col,updateColourIndex, colourToChangeIndex);
        updateUserAreaColours(row -1, col, updateColourIndex, colourToChangeIndex);
        updateUserAreaColours(row,col -1, updateColourIndex, colourToChangeIndex);
        updateUserAreaColours(row,col +1, updateColourIndex, colourToChangeIndex);
        
        
        
      }
    }
  }
  
//-------------------------------------------------------
// returns a String with all the colour indexes
// concatenated, row by row (Stage 6)
//------------------------------------------------------- 
  public String colourIndexesToString() {
    String info = "";
    
    for( int row = 0; row < NUMBER_OF_ROWS; row++){
      for( int col = 0; col < NUMBER_OF_COLS; col++){
        int colourIndex = getCellColourIndex(row,col);
        info += colourIndex+" ";
        
        
      }
      
    } 
    return info;
  }
//-------------------------------------------------------
// Draw the 2D array of coloured cells (Stage 1)
//-------------------------------------------------------
  public void drawCells(Graphics g) { 
    
    for( int row = 0; row < NUMBER_OF_ROWS; row++){
      for(int col = 0; col < NUMBER_OF_COLS; col++){
        cellBlock[row][col].draw(g);
        
      }
    }
    
  }
}
