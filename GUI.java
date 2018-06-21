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

import java.io.*;



public class GUI extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;

    static PrintStream output = System.out;
    
    private JButton button;
    private JLabel label;
    private JButton[] buttons = new JButton[4];
    private JLabel[] cookieCounter = new JLabel[4];
    private JButton scoreButton;
    private int score = 0;
    private JButton restartButton;
    private Font standardFont = new Font("Sans-Serif", Font.PLAIN, 20);

    public GUI(String name) {
        setTitle(name);
        setResizable(false);
        setContentPane(new JLabel(new ImageIcon("background1.jpg")));
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,500);
        setVisible(true);
        
    } // end of constructor method "GUI"
    
    public void addPikachu() { // Adds pikachu-buttons to frame

        for (int i=0; i<4; i++) {
            buttons[i] = new JButton(new ImageIcon("pikachu.png"));
            buttons[i].setContentAreaFilled(true);
            buttons[i].setBorderPainted(false);
            buttons[i].setFocusPainted(false);
        }

        buttons[0].setBounds(10, 10, 120, 100);
        buttons[1].setBounds(163, 10, 120, 100);
        buttons[2].setBounds(316, 10, 120, 100);
        buttons[3].setBounds(464, 10, 120, 100);

        for (int i=0; i<4; i++) {
            add(buttons[i]); 
        }
    } // end of method "addPikachu"

    public void addComp() {

        Dimension buttonSize = new Dimension(120, 100);

        button = new JButton(new ImageIcon("pikachu.png"));
        //button = new JButton("Click me for text!");
        button.setBounds(50, 50, 120, 100);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(buttonSize);
        add(button);

        label = new JLabel("");
        label.setFont(new Font("Sans-Serif", Font.BOLD, 30));
        label.setBounds(300, 10, 120, 100);
        add(label);

        button.addActionListener(this);
    } // end of method "addComp"

    public void addScore() { // Adds score button to frame
        scoreButton = new JButton("Score: " + score);
        scoreButton.setFont(standardFont);
        scoreButton.setContentAreaFilled(true);
        scoreButton.setBorderPainted(false);
        scoreButton.setFocusPainted(false);
        scoreButton.setBounds(10, 411, 120, 50);

        scoreButton.addActionListener(this);

        add(scoreButton);
    } // end of method "addScore"

    public void addRestart() { // Adds restart button to frame
        restartButton = new JButton("Restart");
        restartButton.setFont(standardFont);
        restartButton.setContentAreaFilled(true);
        restartButton.setBorderPainted(false);
        restartButton.setFocusPainted(false);
        restartButton.setBounds(464, 411, 120, 50);
        add(restartButton);
    } // end of method "addRestart"

    public void addCookieCounters() { // Adds boxes for displaying number of cookies each entity has
        for (int i=0; i<4; i++) {
            cookieCounter[i] = new JLabel("?", SwingConstants.CENTER);
            cookieCounter[i].setFont(standardFont);
            cookieCounter[i].setOpaque(true);
            cookieCounter[i].setBackground(new Color(217, 229, 242));
        }
        
        cookieCounter[0].setBounds(49, 120, 44, 44);
        cookieCounter[1].setBounds(203, 120, 44, 44);
        cookieCounter[2].setBounds(358, 120, 44, 44);
        cookieCounter[3].setBounds(506, 120, 44, 44);

        for (int i=0; i<4; i++) {
            add(cookieCounter[i]);
        }
    } // end of method "addCookieCounters"

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            label.setText("hello");
        } else if (e.getSource() == scoreButton) {
            score++;
            scoreButton.setText("Score: " + score);
        }
} // end of method "actionPerformed"

    public static void main(String[] args) throws InterruptedException{
        GUI mainGUI = new GUI("The GUI");
        // mainGUI.addComp();
        mainGUI.addPikachu();
        mainGUI.addCookieCounters();
        mainGUI.addScore();
        mainGUI.addRestart();
        mainGUI.setLocationRelativeTo(null);
    } // end of method "main"
} // end of class "GUI"