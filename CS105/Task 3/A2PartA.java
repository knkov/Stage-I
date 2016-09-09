/*
 * Purpose: application for the ColourMe game
 * 
 * Author: Adriana Ferraro
 * Date: S2 2012
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class A2PartA {
    public static final int JFRAME_AREA_WIDTH = A2Constants.JFRAME_AREA_WIDTH;
    public static final int JFRAME_AREA_HEIGHT = A2Constants.JFRAME_AREA_HEIGHT;;
    
    public static void main(String[] args) {
        A2JFrame drench = new A2JFrame("A2 Part A by kkov003", 3, 3, JFRAME_AREA_WIDTH, JFRAME_AREA_HEIGHT);
    }
}

class A2JFrame extends JFrame {
    
    
    public A2JFrame(String title, int x, int y, int width, int height) {
        // Set the title, top left location, and close operation for the frame
        setTitle(title);
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create an instance of the JPanel class, and set this to define the
        // content of the window
        JPanel frameContent = new A2JPanel();
        Container visibleArea = getContentPane();
        visibleArea.add(frameContent);
        
        // Set the size of the content pane of the window, resize and validate the
        // window to suit, obtain keyboard focus, and then make the window visible
        frameContent.setPreferredSize(new Dimension(width, height));
        pack();
        frameContent.requestFocusInWindow();
        setVisible(true);
    }
}