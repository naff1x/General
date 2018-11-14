import java.io.*;
import java.util.*;
    import java.util.Comparator;
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

public class HighScores extends JFrame {

    private static final long serialVersionUID = 1L;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;

    /// Fonts
        private Font pixelFont;
        private Font smallerPF;
    /// File I/O
        private ArrayList<ScoreEntry> scoreArray;
        private File scoreFile;
        static FileInputStream fileIn;
        static ObjectInputStream objectIn;
        private int id;
        private ScoreEntry entry = null;
        private Boolean objectReturned;
    /// Other
        private ArrayList<Integer> diffcultyArray;
        private ArrayList<Integer> timeArray;
        private MainMenu menu;
    /// Visuals
        /// JLabels for names
            private JLabel oneName;
            private JLabel twoName;
            private JLabel threeName;
        
        /// JLabels for difficulties
            private JLabel oneDiff;
            private JLabel twoDiff;
            private JLabel threeDiff;

        /// JLabels for times
            private JLabel oneTime;
            private JLabel twoTime;
            private JLabel threeTime;
    
        /// Other
            private JLabel noScores; // Shown if there are no scores to show
            private JButton menuButton;
    ///
    public HighScores(String name) {
        setTitle(name);
        setResizable(false);
        setLayout(null);
        setContentPane(new JLabel(new ImageIcon("HighScoresTemplate.png")));
        //setContentPane(new JLabel(new ImageIcon("HighScoresTEMP.png"))); // Used for fixing with layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
        setLocationRelativeTo(null);
        setVisible(true);

        addFonts();
        addMenuButton();

        try {
            scoreFile = new File("scores.ser");
            readScoreFile();
            rankScores();
        } catch (NullPointerException npe) {
            output.println("There any no scores to display!");
            noScores = new JLabel("<html><body>There are no<br>scores to display!</body></html>", SwingConstants.CENTER);
            noScores.setFont(smallerPF);
            noScores.setBounds(200, 200, 200, 200);
            add(noScores);
        }
        repaint();
    } // end of contructor method "game"

    public void addFonts() {
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("VCR_OSD_MONO_1.001.ttf"))).deriveFont(Font.BOLD, 25);  
            smallerPF = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("VCR_OSD_MONO_1.001.ttf"))).deriveFont(Font.BOLD, 17);
        } catch (FileNotFoundException e) {
            e.addSuppressed(e);
        } catch (FontFormatException e) {
            e.addSuppressed(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end of method "addFonts"

    public void addMenuButton() {

        output.println("Hello from 'addMenuButton'");
        
        menuButton = new JButton(new ImageIcon("ToMenu.png"));
        menuButton.setContentAreaFilled(true);
        menuButton.setBorderPainted(true);
        menuButton.setFocusPainted(false);

        menuButton.setBounds(265, 0, 70, 35);
        menuButton.setOpaque(false);

        menuButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                output.println("Menu button pressed!");
                dispose();
                menu = new MainMenu("Main Menu");
            }
        });
        
        add(menuButton);
    } // end of method "addMenuButton"

    public void readScoreFile() {
        scoreFile = new File("scores.ser");

        try {
            fileIn = new FileInputStream(scoreFile);
            scoreArray = new ArrayList<ScoreEntry>();
            objectReturned = true;
            
            while (objectReturned) {
                objectIn = new ObjectInputStream(fileIn);
                ScoreEntry entry = (ScoreEntry)objectIn.readObject();
                if (entry != null) {
                    scoreArray.add(entry);
                } else {
                    objectReturned = false;
                }
            }
            objectIn.close();
            fileIn.close();
        } catch (EOFException ex) {
        } catch (IOException ioe) {
            ioe.printStackTrace();
            output.println("!! - IOException generated in 'HighScores.java'!");
        } catch (ClassNotFoundException cnfe) {
            output.println("!! - A class is missing in 'HighScores.java'!");
        }
        
        for (int i=0; i<scoreArray.size(); i++) {
            output.println("Object ID: " +i);
            output.println("Entry: " + scoreArray.get(i) + " (username: " + scoreArray.get(i).getUsername() + ")" );
        }
        
    } // end of method "readScoreFile"

    public void rankScores() {
        /// Old ranking algorithm
            /*
            /// Create arrays for sorting values of difficulty and time
            diffcultyArray = new ArrayList<Integer>();
            timeArray = new ArrayList<Integer>();

            for (int i=0; i<scoreArray.size(); i++) { 
                diffcultyArray.add(scoreArray.get(i).getDifficulty());
                timeArray.add(scoreArray.get(i).getTime());
            }

            diffcultyArray.sort(Comparator.reverseOrder());
            //Collections.sort(diffcultyArray);
            output.println("-- DIFFICULTY: " + diffcultyArray);

            timeArray.sort(Comparator.naturalOrder());
            output.println("-- TIME: " + timeArray);

            for (int i=0; i<scoreArray.size(); i++) { // Go through every object (scoreEntry)

                for (int d=0; d<diffcultyArray.size(); d++) { // Go through every difficulty added...

                    if (scoreArray.get(i).getDifficulty() == diffcultyArray.get(d)) { // If the current (i) scoreEntry's difficulty equals the current (d) item in difficultyArray...
                        scoreArray.get(i).setRank(d); // Set the current (i) scoreEntry's rank to ranking in "difficultyArray"
                        output.println("s.A. item: " + i + " / RANK: " + d);
                        diffcultyArray.set(d, -1); // This stops objects from being given the same rank
                        //output.println(diffcultyArray);
                        break;
                    }
                }
            }

            for (int i=0; i<scoreArray.size(); i++) {
                for (int b=0; b<scoreArray.size(); b++) {
                    if (i != b) {  
                        if (scoreArray.get(i).getDifficulty() == scoreArray.get(b).getDifficulty()) { // 

                            output.println("Difficulties matched!");
        
                            if (scoreArray.get(i).getTime() < scoreArray.get(b).getTime() & scoreArray.get(i).getRank() > scoreArray.get(b).getRank()) { // If current s.A. item has a lower time
                                output.println("Current s.A. item has lower time!");
                                if (scoreArray.get(i).getRank() != 0) {
                                    
                                    int c = scoreArray.get(b).getRank();
                                    output.println(scoreArray.get(b).getUsername() + "'s rank before change: " + scoreArray.get(b).getRank());
                                    scoreArray.get(b).setRank(scoreArray.get(i).getRank());
                                    output.println(scoreArray.get(b).getUsername() + "'s rank after change: " + scoreArray.get(b).getRank());
                                    output.println(scoreArray.get(i).getUsername() + "'s rank before change: " + scoreArray.get(i).getRank());
                                    scoreArray.get(i).setRank(c);
                                    output.println(scoreArray.get(i).getUsername() + "'s rank after change: " + scoreArray.get(i).getRank());
                                    
                                } else {
                                    scoreArray.get(b).increaseRankVar();
                                    output.println(scoreArray.get(b).getUsername() + "'s rank +1");
                                }
                            }  
                            
                            if (scoreArray.get(i).getTime() == scoreArray.get(b).getTime()) { // If the two also have the same time
                                output.println("Two s.A. items had the same time!");
                            }
                        } 
                    }
                }
            output.println("s.A. loop done! (" + i + ")");
            }

            for (int a=0; a<scoreArray.size(); a++) {
                for (int b=0; b<scoreArray.size(); b++) {
                    if (a != b) {
                        if (scoreArray.get(a).getRank() == scoreArray.get(b).getRank()) {
                            if (scoreArray.get(a).getDifficulty() > scoreArray.get(b).getDifficulty()) {
                                scoreArray.get(b).increaseRankVar();
                            } else if (scoreArray.get(a).getDifficulty() == scoreArray.get(b).getDifficulty()) {
                                if (scoreArray.get(a).getTime() < scoreArray.get(b).getTime() | scoreArray.get(a).getTime() == scoreArray.get(b).getTime()) {
                                    scoreArray.get(b).increaseRankVar();
                                } else {
                                    scoreArray.get(a).increaseRankVar();
                                }
                            }
                        }
                    }
                }
            }

            for (int i=0; i<scoreArray.size(); i++) {
                output.println("-- ENTRY (" + i + ") NAME: "+scoreArray.get(i).getUsername());
                output.println("-- ENTRY (" + i + ") RANK: "+scoreArray.get(i).getRank());
                output.println("-- ENTRY (" + i + ") DIFFICULTY: " + scoreArray.get(i).getDifficulty());
                output.println("-- ENTRY (" + i + ") TIME: " + scoreArray.get(i).getTime());
            }

            for (int i=0; i<scoreArray.size(); i++) { // This loop finds the top 3 scores and prints out info.
                
                switch (scoreArray.get(i).getRank()) {
                    case 0:
                    output.println(scoreArray.get(i).getUsername() + " had the best score with " + scoreArray.get(i).getDifficulty() + "% and " + scoreArray.get(i).getTime() + "s !");
                    addRank1(scoreArray.get(i));
                        break;
                    case 1:
                    output.println(scoreArray.get(i).getUsername() + " had the 2nd best score with " + scoreArray.get(i).getDifficulty() + "% and " + scoreArray.get(i).getTime() + "s !");
                    addRank2(scoreArray.get(i));
                        break;
                    case 2:
                    output.println(scoreArray.get(i).getUsername() + " had the 3rd best score with " + scoreArray.get(i).getDifficulty() + "% and " + scoreArray.get(i).getTime() + "s !");
                    addRank3(scoreArray.get(i));
                        break;
                }
            }
            */
        /// New ranking algorithm
            output.println("--- Before sorting! ---");
            for (int i=0; i<scoreArray.size(); i++) {
                output.println("-- ENTRY (" + i + ") NAME: "+scoreArray.get(i).getUsername());
                //output.println("-- ENTRY (" + i + ") RANK: "+scoreArray.get(i).getRank());
                output.println("-- ENTRY (" + i + ") DIFFICULTY: " + scoreArray.get(i).getDifficulty());
                output.println("-- ENTRY (" + i + ") TIME: " + scoreArray.get(i).getTime());
            }
            scoreArray.sort(new Comparator<ScoreEntry>(){
                public int compare(ScoreEntry a, ScoreEntry b) {
                    int compared = Integer.toString(a.getDifficulty()).compareTo(Integer.toString(b.getDifficulty()));
                    output.println("COMPARED = " + compared);
                    if (compared < 0) {
                        return 1;
                    } else if (compared > 0) {
                        return -1;
                    }
                    if (compared == 0) {
                        compared = Integer.toString(a.getTime()).compareTo(Integer.toString(b.getTime()));
                    }
                    return compared;
                }
            });
            output.println("--- After sorting ---");
            for (int i=0; i<scoreArray.size(); i++) {
                output.println("-- ENTRY (" + i + ") NAME: "+scoreArray.get(i).getUsername());
                //output.println("-- ENTRY (" + i + ") RANK: "+scoreArray.get(i).getRank());
                output.println("-- ENTRY (" + i + ") DIFFICULTY: " + scoreArray.get(i).getDifficulty());
                output.println("-- ENTRY (" + i + ") TIME: " + scoreArray.get(i).getTime());
            }

            addRank1(scoreArray.get(0));
            addRank2(scoreArray.get(1));
            addRank3(scoreArray.get(2));
    } // end of method "rankScores"

    public void addRank1(ScoreEntry number1) {

        /// Name
            oneName = new JLabel(number1.getUsername(), SwingConstants.CENTER);
            oneName.setFont(pixelFont);
            //oneName.setBorder(BorderFactory.createLineBorder(Color.black));
            oneName.setBounds(180, 270, 105, 24);
            super.add(oneName);
        /// Difficulty
            oneDiff = new JLabel(number1.getDifficulty()+"%", SwingConstants.CENTER);
            oneDiff.setFont(pixelFont);
            //oneDiff.setBorder(BorderFactory.createLineBorder(Color.black));
            oneDiff.setBounds(320, 270, 105, 24);
            super.add(oneDiff);
        /// Time
            oneTime = new JLabel(number1.getTime()+"s", SwingConstants.CENTER);
            oneTime.setFont(pixelFont);
            //oneTime.setBorder(BorderFactory.createLineBorder(Color.black));
            oneTime.setBounds(465, 272, 105, 24);
            super.add(oneTime);

    } // end of method "addRank1"

    public void addRank2(ScoreEntry number2) {

        /// Name
            twoName = new JLabel(number2.getUsername(), SwingConstants.CENTER);
            twoName.setFont(pixelFont);
            //twoName.setBorder(BorderFactory.createLineBorder(Color.black));
            twoName.setBounds(180, 360, 105, 24);
            super.add(twoName);
        /// Difficulty
            twoDiff = new JLabel(number2.getDifficulty()+"%", SwingConstants.CENTER);
            twoDiff.setFont(pixelFont);
            //twoDiff.setBorder(BorderFactory.createLineBorder(Color.black));
            twoDiff.setBounds(320, 360, 105, 24);
            super.add(twoDiff);
        /// Time
            twoTime = new JLabel(number2.getTime()+"s", SwingConstants.CENTER);
            twoTime.setFont(pixelFont);
            //twoTime.setBorder(BorderFactory.createLineBorder(Color.black));
            twoTime.setBounds(465, 360, 105, 24);
            super.add(twoTime);

    } // end of method "addRank2"

    public void addRank3(ScoreEntry number3) {

       /// Name
            threeName = new JLabel(number3.getUsername(), SwingConstants.CENTER);
            threeName.setFont(pixelFont);
            //threeName.setBorder(BorderFactory.createLineBorder(Color.black));
            threeName.setBounds(180, 450, 105, 24);
            super.add(threeName);
        /// Difficulty
            threeDiff = new JLabel(number3.getDifficulty()+"%", SwingConstants.CENTER);
            threeDiff.setFont(pixelFont);
            //threeDiff.setBorder(BorderFactory.createLineBorder(Color.black));
            threeDiff.setBounds(320, 450, 105, 24);
            super.add(threeDiff);
        /// Time
            threeTime = new JLabel(number3.getTime()+"s", SwingConstants.CENTER);
            threeTime.setFont(pixelFont);
            //threeTime.setBorder(BorderFactory.createLineBorder(Color.black));
            threeTime.setBounds(465, 450, 105, 24);
            super.add(threeTime);

    } // end of method "addRank3"
} // end of class "game"