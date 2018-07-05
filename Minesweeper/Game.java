import javax.swing.JFrame;

public class Game extends JFrame {

    private static final long serialVersionUID = 1L;

    public Game(String name) {
        setTitle(name);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
        setLocationRelativeTo(null);
        setVisible(true);
    } // end of contructor method "game"
} // end of class "game"