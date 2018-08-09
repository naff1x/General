import java.io.*;
    import java.util.*;
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.FlowLayout;
    import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Insets;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseListener;
    import java.awt.Event;
    import java.awt.event.MouseEvent;

import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
    import javax.swing.JButton;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane.*;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.w3c.dom.css.RGBColor;

public class Game extends JFrame {

    // TODO: Resize "bombIcon.png" and make it fit inside the 20x20 cells

    private static final long serialVersionUID = 1L;

    /// Variables for reciving input from constructor method
    private int bombs;
    private int frameWidth;
    private int frameHeight;
    private int squareWidth;
    private int squareHeight;
    /// Fonts
    private Font pixelFont;
    /// Variables for class "Board"
    private Board playground;
    /// Variables for class "Header"
    private Header topBar;

    public Game(String nameFromInput, int widthFromInput, int heightFromInput, int bombsFromInput) {
        bombs = bombsFromInput;
        frameWidth = widthFromInput * 20 + 40;   // Multiplied by 20 to make room for the 20x20 cells that will make up the playing field
        frameHeight = heightFromInput * 20 + 80; // Plus 40 and 60 to add margin between the playing field and the border of the JFrame
        
        // These two integers contain the raw input from the user.
        // In other words, the requested number of squares for width and height.
        squareWidth = widthFromInput;
        squareHeight = heightFromInput;

        setTitle(nameFromInput);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(340, 200));
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setVisible(true);
        
        /// Other methods and classes goes below
        addFonts();
        if (frameWidth > 340) {
            topBar = new Header(frameWidth);
        } else {
            topBar = new Header(340);
        }
        add(topBar);

        playground = new Board(squareWidth, squareHeight);
        add(playground);
    } // end of contructor method "game"

    public void addFonts() {
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("VCR_OSD_MONO_1.001.ttf"))).deriveFont(Font.PLAIN, 20);   
        } catch (FileNotFoundException e) {
            e.addSuppressed(e);
        } catch (FontFormatException e) {
            e.addSuppressed(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end of method "addFonts"
} // end of class "game"

class Header extends JPanel {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;

    private int barCoordinate;

    public Header(int widthOfFrame) {

        barCoordinate = widthOfFrame/2 - 150;
        output.println(barCoordinate);

        // setLayout(null);
        setBackground(Color.blue);
        setBounds(barCoordinate, 5, 300, 30);
        setVisible(true);

    } // end of constructor method "Header"
} // end of class "Header"

class Board extends JPanel { 

    public Board(int xWidth, int yHeight) {

        // TODO: Issue: The absolute positioning of the panel stops working when the user enters a 7 or lower. 

        setLayout(new GridLayout(xWidth, yHeight));
        setBackground(Color.yellow);
        setBounds(20, 40, xWidth * 20, yHeight * 20); // xWidth and yHeight multiplied by 20 to fit in the 20px*20px squares.
        setVisible(true);
    } // end of constructor method "Board"
} // end of class "Board"

/*
class Cell { // TODO: Should implement an ActionListner to push the logic down the hierarchy as far as possible.

} // end of class "Cell" 
*/