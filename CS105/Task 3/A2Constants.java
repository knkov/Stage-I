/*
 * Purpose: defines constants for the ColourMe game.
 * 
 * Author: Adriana Ferraro
 * Date: S2 2012
*/

import java.awt.*;

public class A2Constants { 
    public static final int CELL_SIZE = 20; 
    
//-------------------------------------------------------
// width, height of the JFrame area and gaps
//-------------------------------------------------------
    public static final int GAP_LEFT_RIGHT = CELL_SIZE;
    public static final int GAP_ABOVE = 100;
    public static final int GAP_BELOW = 100;
    
    public static final int NUMBER_OF_ROWS = 14;
    public static final int NUMBER_OF_COLS =  14;
    
    public static final int GAME_AREA_HEIGHT = NUMBER_OF_ROWS * CELL_SIZE;
    public static final int GAME_AREA_WIDTH = NUMBER_OF_COLS * CELL_SIZE;
    
    //a rectangle which defines the area below the blocks area
    public static final Rectangle GAME_AREA = new Rectangle(GAP_LEFT_RIGHT, GAP_ABOVE, GAME_AREA_WIDTH, GAME_AREA_HEIGHT);
    
    public static final int JFRAME_AREA_WIDTH = GAME_AREA_WIDTH * 5 / 3 + GAP_LEFT_RIGHT * 2;
    public static final int JFRAME_AREA_HEIGHT = GAME_AREA_HEIGHT + GAP_ABOVE + GAP_BELOW;
    
//------------------------------------------------------
// Colours which can be used for the cells
//------------------------------------------------------- 
    public static final Color[] COLOURS = {new Color(224,32,32), new Color(145,32,224), new Color(32,32,224), new Color(41,223,225), new Color(176,228,53),  new Color(255,138,0)};
//new Color(255,252,29),new Color(27,234,56), 
//-------------------------------------------------------
// x, y position of the score
//-------------------------------------------------------
    public static final Point SCORE_POSITION = new Point(GAME_AREA.x + GAME_AREA.width + 10, GAME_AREA.y + GAME_AREA.height / 2);
    
//-------------------------------------------------------
// Constant whioh defines an excellent score
//------------------------------------------------------- 
    public static final int MAX_NUMBER_OF_MOVES_ALLOWED = 25;
    public static final int EXCELLENT_SCORE = MAX_NUMBER_OF_MOVES_ALLOWED - 5; 
//-------------------------------------------------------
// Fonts and sizes
//-------------------------------------------------------
    public static final int SMALL_FONT_SIZE = 16;
    public static final int MEDIUM_FONT_SIZE = 20;
    public static final int LARGE_FONT_SIZE = 28;
    public static final int HUGE_FONT_SIZE = 60;
    
    public static final Font SMALL_FONT = new Font("GENEVA", Font.CENTER_BASELINE, SMALL_FONT_SIZE);
    public static final Font MEDIUM_FONT = new Font("TIMES", Font.CENTER_BASELINE, MEDIUM_FONT_SIZE);;
    public static final Font LARGE_FONT = new Font("GENEVA", Font.CENTER_BASELINE, LARGE_FONT_SIZE); 
    public static final Font HUGE_FONT = new Font("SansSerif", Font.BOLD, HUGE_FONT_SIZE);
}
