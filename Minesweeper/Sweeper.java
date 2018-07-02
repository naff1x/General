import java.io.*;
    import java.util.*;
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.FlowLayout;
    import java.awt.Font;
    import java.awt.Insets;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.EventObject;
    import javax.swing.ImageIcon;
    import javax.swing.JButton;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JPanel;
    import javax.swing.SwingConstants;

public class Sweeper extends JFrame {
    /// General variables
    static Sweeper game; // let's program know about game's existence 
    private static final long serialVersionUID = 1L;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;
    /// Landing Page variables
    private JFrame landingFrame;
    private JButton newGameButton;
    //
    
    public static void main(String[] args) {
        game = new Sweeper();
        game.landingPage();
        game.repaint();

    } // end of method "main"
    
    public void landingPage() { // method creates window that asks for size of playing field
        landingFrame = new JFrame("Landing Page");
        landingFrame.setSize(400,500);
        landingFrame.setResizable(false);
        landingFrame.setContentPane(new JLabel(new ImageIcon("Minesweeper.png")));
        landingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        landingFrame.setLocationRelativeTo(null);
        landingFrame.setVisible(true);

        gameButton();
    } // end of method "landingPage"

    public void gameButton() {
        newGameButton = new JButton("hello");
        newGameButton.setBounds(101, 134, 189, 60);
        add(newGameButton); 
    } // end of method "gameButton"
} // end of class "Sweeper"