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

    private JButton button;
    private JLabel label;
    private JButton[] buttons = new JButton[4];
    private JLabel[] cookieCounter = new JLabel[4];
    private JButton scoreButton;
    private int score = 0;
    private JButton restartButton;
    private Font standardFont = new Font("Sans-Serif", Font.PLAIN, 16);
    private JLabel textBox;
    private int waitTime = 500;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;
    private String newLine = System.lineSeparator();
    private ArrayList<Monster> monsterVector = new ArrayList<Monster>();

    public MFGrafik(String name) throws InterruptedException{
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
            add(buttons[i]); 
        }
    } // end of method "addPikachu"

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

    public void addTextBox() { // Adds middle text box to frame
        textBox = new JLabel("You can give: " + "2 cookies", SwingConstants.CENTER);
        textBox.setFont(standardFont);
        textBox.setOpaque(true);
        textBox.setBackground(new Color(217, 229, 242));

        textBox.setBounds(163, 213, 275, 65);

        add(textBox);
    } // end of method "addTextBox"

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            label.setText("hello");
        } else if (e.getSource() == scoreButton) {
            score++;
            scoreButton.setText("Score: " + score);
        }
    } // end of method "actionPerformed"

    public void initalization() throws InterruptedException{
        output.println("Welcome to Monster Feeder!" + newLine);
        textBox.setText("Welcome to Pokemon Feeder!");
        Thread.sleep(2000);
        Monster aMonster = new Monster();
        Monster bMonster = new Monster();
        Monster cMonster = new Monster();
        Monster dMonster = new Monster();
        monsterVector.add(aMonster);
        monsterVector.add(bMonster);
        monsterVector.add(cMonster);
        monsterVector.add(dMonster);
        for (int i=0; i<4; i++) {
            output.print("yum...");
        }

        output.print(newLine + "Game starting");
        textBox.setText("Game starting");
        Thread.sleep(waitTime);
        System.out.print(".");
        textBox.setText("Game starting.");
        Thread.sleep(waitTime);
        System.out.print(".");
        textBox.setText("Game starting..");
        Thread.sleep(waitTime);
        System.out.println("." + newLine);
        textBox.setText("Game starting...");
        Thread.sleep(waitTime);
        output.println(newLine);
    } // end of method "initalization"

    public void gameLoop() throws IOException {
        int roundCount = 0;
        while (monsterVector.size() > 1) {  
            
            output.println("Number of cookies each monster has:");
            for (int i=0; i<monsterVector.size(); i++) {
                output.print("Monster " + (i+1) + ": " + monsterVector.get(i).cookieCount() + "     ");
            }
            output.print(newLine);
            feed();
            eat();
            output.println(newLine);
            roundCount++;
        }
        output.println("Number of rounds played: " + roundCount);
        output.println("Thanks for playing!");
    } // end of gameLoop    

    public void feed() throws IOException {
        int food = ThreadLocalRandom.current().nextInt(0, 2 + 1);
            if (food == 0) {
                output.print("No cookies to give!");
            } else {
                output.print("Cookies you can give: " + food + ". ");
                output.print("Which monster do you want to feed?: ");
                int svar = Integer.parseInt(input.readLine());
                monsterVector.get(svar-1).increase(food); // svar-1 because monster "1" is actually in slot 0 of the vector  
            }
            output.print(newLine);
    } // end of feed    
    
    public void eat() {
        for (int i=0; i<monsterVector.size(); i++) {
            monsterVector.get(i).decrease();

            if (!monsterVector.get(i).isAlive()) { // if monster dies...
                monsterVector.remove(monsterVector.get(i));
                output.print("aargh!...");
            } else {
                output.print("yum...");
            }
            
        }
    } // end of eat

    public static void main(String[] args) throws IOException, InterruptedException{
        MFGrafik game = new MFGrafik("Pokemon Feeder");
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