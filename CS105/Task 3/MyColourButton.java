/*
 * Purpose: defines a coloured button.
 * 
 * Author: Adriana Ferraro
 * Date: S2 2012
*/

import java.awt.*;
import javax.swing.*;

public class MyColourButton extends JButton {
    public static final Color[] COLOURS = A2Constants.COLOURS;
    public static final int CELL_SIZE = A2Constants.CELL_SIZE;
    
    private int colourIndex;
    
    public MyColourButton(String label, int colourIndex) {
        super(label);
        this.colourIndex = colourIndex;
    }
    
    public int getColourIndex() {
        return colourIndex;
    }
//-------------------------------------------------------
// Draw the cell
//-------------------------------------------------------
    public void paintComponent(Graphics g) {
        Color fillColor = COLOURS[colourIndex];
        int indent = 0;
        g.setColor(Color.WHITE);
        g.fillOval(indent, indent, getSize().width - indent * 2, getSize().height - indent * 2);
        indent = 2;
        g.setColor(fillColor);
        g.fillOval(indent, indent, getSize().width - indent * 2, getSize().height - indent * 2);
        indent = 6;
        g.setColor(Color.BLACK);
        g.fillOval(indent, indent, getSize().width - indent * 2, getSize().height - indent * 2);
        indent = 7;
        g.setColor(fillColor);
        g.fillOval(indent, indent, getSize().width - indent * 2, getSize().height - indent * 2);
    }
}


