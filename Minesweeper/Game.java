import java.io.*;
    import java.util.*;
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.FlowLayout;
    import java.awt.Font;
import java.awt.FontFormatException;
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

    private static final long serialVersionUID = 1L;

    /// Variables for reciving input from constructor method
    private int bombs;
    private int width;
    private int height;
    /// Fonts
    private Font pixelFont;
    /// 
    
    public Game(String nameFromInput, int widthFromInput, int heightFromInput, int bombsFromInput) {
        bombs = bombsFromInput;
        width = widthFromInput * 20; // Multiplied by 20 to make room for the 20x20 cells that will make up the playing field
        height = heightFromInput * 20;

        setTitle(nameFromInput);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 200));
        setSize(width + 40, height + 50); // Plus 40 and 50 to add margin between the playing field and the border of the JFrame
        setLocationRelativeTo(null);
        setVisible(true);
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

class Cell extends JButton {

	private static final long serialVersionUID = 1L;

} // end of class "Cell" 