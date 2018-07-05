import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HighScores extends JFrame {

    private static final long serialVersionUID = 1L;

    public HighScores(String name) {
        setTitle(name);
        setResizable(false);
        setLayout(null);
        setContentPane(new JLabel(new ImageIcon("HighScores.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
        setLocationRelativeTo(null);
        setVisible(true);
    } // end of contructor method "game"
} // end of class "game"