import java.io.*;
import java.util.*;
    import java.util.concurrent.ThreadLocalRandom;
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

public class MFGrafik extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JButton[] buttons = new JButton[4];
    private JLabel[] cookieCounter = new JLabel[4];
    private JLabel scoreLabel;
    private JButton restartButton;
    private Font standardFont = new Font("Sans-Serif", Font.PLAIN, 16);
    private JLabel textBox;
    private int waitTime = 500;
    private Monster monster1;
    private Monster monster2;
    private Monster monster3;
    private Monster monster4;
    private ArrayList<Monster> monsterVector = new ArrayList<Monster>();
    private int food;
    private int roundCount = -1;
    private int svar;
    private ImageIcon fainted;
    private int numberAlive = 4;
    static MFGrafik game;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;
    private String newLine = System.lineSeparator();
    private JButton okButton;
    private int threshold = 0;

    public MFGrafik(String name){
        setTitle(name);
        setResizable(false);
        setContentPane(new JLabel(new ImageIcon("background1.jpg")));
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,500);
        setLocationRelativeTo(null);
        setVisible(true);
    } // end of constructor "MFGrafik"

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
            buttons[i].addActionListener(this);
            add(buttons[i]); 
        }
    } // end of method "addPikachu"

    public void addScore() { // Adds score button to frame
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(standardFont);
        //scoreLabel.setContentAreaFilled(true);
        //scoreLabel.setBorderPainted(false);
        //scoreLabel.setFocusPainted(false);
        scoreLabel.setBounds(10, 411, 120, 50);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(217, 229, 242));

        //scoreLabel.addActionListener(this);

        add(scoreLabel);
    } // end of method "addScore"

    public void addRestart() { // Adds restart button to frame
        restartButton = new JButton("Restart");
        restartButton.setFont(standardFont);
        restartButton.setContentAreaFilled(true);
        restartButton.setBorderPainted(false);
        restartButton.setFocusPainted(false);
        restartButton.setBounds(464, 411, 120, 50);
        
        restartButton.addActionListener(this);
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

    public void addTextBox() { // Adds middle text box to frame
        textBox = new JLabel("You can give: " + "2 cookies", SwingConstants.CENTER);
        textBox.setFont(standardFont);
        textBox.setOpaque(true);
        textBox.setBackground(new Color(217, 229, 242));

        textBox.setBounds(163, 213, 275, 65);

        add(textBox);
    } // end of method "addTextBox"

    public void actionPerformed(ActionEvent e) { // handles events
        if (e.getSource() == restartButton) { // resets variables etc.
                monsterVector.clear();
                monster1 = new Monster();
                monster2 = new Monster();
                monster3 = new Monster();
                monster4 = new Monster();
                monsterVector.add(monster1);
                monsterVector.add(monster2);
                monsterVector.add(monster3);
                monsterVector.add(monster4);

                for (int i=0; i<4; i++) {
                    buttons[i].setIcon(new ImageIcon("pikachu.png"));
                    buttons[i].addActionListener(this);
                    buttons[i].setVisible(true);
                }

                for (int i = 0; i < 4; i++) {
                    cookieCounter[i].setVisible(true);
                }

                roundCount = 0;
                scoreLabel.setText("Score: " + roundCount);
                scoreLabel.setVisible(true);

                threshold = 0;

                output.println("Game restarting...");
                textBox.setText("Game restarting...");
                numberAlive = 4;
                gameLoop();

            
        } if (e.getSource() == buttons[0]) {
            svar = 1;
            eat();
        } if (e.getSource() == buttons[1]) {
            svar = 2;
            eat();
        } if (e.getSource() == buttons[2]) {
            svar = 3;
            eat();
        } if (e.getSource() == buttons[3]) {
            svar = 4;
            eat();
        } if (e.getSource() == okButton) {
            eat();
        }
    } // end of method "actionPerformed"

    public void addOK() {
        okButton = new JButton("Oh, null!");
        okButton.setFont(standardFont);
        okButton.addActionListener(this);
        okButton.setBounds(248, 284, 100, 43);
        add(okButton);
    } // end of method "okButton"

    public void initalization() { // creates monster1-4 and adds them to monsterVector
        monster1 = new Monster();
        monster2 = new Monster();
        monster3 = new Monster();
        monster4 = new Monster();
        monsterVector.add(monster1);
        monsterVector.add(monster2);
        monsterVector.add(monster3);
        monsterVector.add(monster4);
        for (int i=0; i<4; i++) {
            output.print("yum...");
        }
        output.print(newLine + "Game starting...");
        System.out.println("." + newLine);
        textBox.setText("Game starting...");
        output.println(newLine);
    } // end of method "initalization"

    public void gameLoop() {
        if (numberAlive > 1) {
            output.println("Number of cookies each monster has:"); // for console
            for (int i=0; i<monsterVector.size(); i++) {
                if (monsterVector.get(i).isAlive()) {
                    output.print("Monster " + (i+1) + ": " + monsterVector.get(i).cookieCount() + "     ");
                }
            }

            for (int i=0; i<monsterVector.size(); i++) { // for graphical components (cookieCounters)
                if (monsterVector.get(i).isAlive()) {
                    cookieCounter[i].setText("" + monsterVector.get(i).cookieCount());
                }
            }
            output.print(newLine);
            feed();
        } else {
            scoreLabel.setVisible(false);
            output.println("Number of rounds played: " + roundCount);
            textBox.setText("Final score: " + roundCount);
            output.println("Thanks for playing!");
            textBox.setText("<html>Final score: " + roundCount + "<br/> Thanks for playing!</html>");
            for (int i=0; i<4; i++) {
                buttons[i].removeActionListener(this);
            }
        }
    } // end of gameLoop    

    public void feed(){
        food = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            if (food == 0) {
                output.print("No cookies to give!");
                textBox.setText("No cookies to give!");
                if (threshold == 0) { // if it's the first time
                    addOK();
                } else { // if it's not the first time 
                    okButton.setVisible(true);
                }
                threshold++;
                // eat(); moved to actionListener for component "okButton"
            } else {
                output.print("Cookies you can give: " + food + ". ");
                output.print("Which monster do you want to feed?: ");
                textBox.setText("<html>Cookies you can give: " + food + "<br/> Which Pokemon do you want to feed?</html>");  
            }
            output.print(newLine);
    } // end of feed    
    
    public void eat() {
        if (okButton.isVisible()) {
            okButton.setVisible(false);
        }
        if (svar>=1) { // makes sure that an answer has actually been given
            monsterVector.get(svar-1).increase(food); // "svar-1" because monster "1" is actually in slot 0 of the vector
        }
        for (int i=0; i<monsterVector.size(); i++) {
            monsterVector.get(i).decrease();

            if (!monsterVector.get(i).isAlive()) { // if monster dies (has UNDER 0 cookies)
                if (!cookieCounter[i].isVisible()) { // if corresponding button doesn't have "fainted" icon set (hasn't already been delcared)
                } else {
                    //monsterVector.remove(monsterVector.get(i));
                    numberAlive--;
                    fainted = new ImageIcon("fainted_pikachu2.png");
                    buttons[i].setIcon(fainted);
                    buttons[i].removeActionListener(this);
                    cookieCounter[i].setVisible(false);
                    //output.println("NUMBER ALIVE:" + numberAlive);

                    output.print("aargh!..."); 
                }
            } else {
                output.print("yum...");
            }
            
        }
        output.println(newLine);
        roundCount++;
        scoreLabel.setText("Score: " + roundCount);
        gameLoop();
    } // end of eat

    public static void main(String[] args) throws IOException, InterruptedException{
        game = new MFGrafik("Pokemon Feeder");
        game.addTextBox();
        game.setLocationRelativeTo(null);
        game.repaint();
        game.initalization();
        game.addPikachu();
        game.addCookieCounters();
        game.addScore();
        game.addRestart();
        game.repaint();
        game.gameLoop();
    }

} // end of MonsterFeeder's class

class Monster {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;

    private int cookies;

    public Monster() {
        cookies = ThreadLocalRandom.current().nextInt(2, 5 + 1);
    } // end of Monster's method

    public Boolean isAlive() {
        return cookies>=0;
    } // end of isAlive's method

    public void decrease() { // decreases monster's cookie count by random num.
        cookies -= ThreadLocalRandom.current().nextInt(0, 2 + 1);
        
    }

    public int cookieCount() { // checks monster's cookie count
        return cookies;
    }

    public void increase(int food) {
        cookies += food;
    }

} // end of Monster's class