/*
 * Purpose: The Cell class for
 * ColourMe game
 * 
 * Author: Adriana Ferraro
 * Date: S2 2012
*/

import java.awt.*;

public class Cell {
    public static final Color[] COLOURS = A2Constants.COLOURS;
    public static final int CELL_SIZE = A2Constants.CELL_SIZE;
    
    private Rectangle area;
    private int colourIndex;
    private boolean hasBeenVisited;
    
    public Cell(int left, int top) {
        area = new Rectangle(left, top, CELL_SIZE, CELL_SIZE);     
        colourIndex = (int) (Math.random() * COLOURS.length);
        hasBeenVisited = false;
    }
    
    public Cell(int left, int top, int colourIndex) {
        area = new Rectangle(left, top, CELL_SIZE, CELL_SIZE);     
        this.colourIndex = colourIndex;
        hasBeenVisited = false;
    }
//-------------------------------------------------------
// Accessor methods
//-------------------------------------------------------      
    public Rectangle getArea() {
        return area;
    }
    
    public void setHasBeenVisited(boolean visited) {
        hasBeenVisited = visited;
    }
    
    public boolean getHasBeenVisited() {  
        return hasBeenVisited;
    }
    
    public void setColourIndex(int index) {
        colourIndex = index;
    }
    
    public int getColourIndex() {  
        return colourIndex;
    }
//-------------------------------------------------------
// Draw the cell
//-------------------------------------------------------
    public void draw(Graphics g) {
        Color fillColor = COLOURS[colourIndex];
        g.setColor(fillColor);  
        g.fillRect(area.x, area.y, area.width, area.height);
        g.setColor(Color.BLACK);
        g.drawRect(area.x, area.y, area.width, area.height);
    }
}

