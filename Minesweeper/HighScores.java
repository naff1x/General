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
        readScoreFile();
        rankScores();
    } // end of contructor method "game"

    public void addFonts() {
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("VCR_OSD_MONO_1.001.ttf"))).deriveFont(Font.BOLD, 25);   
        } catch (FileNotFoundException e) {
            e.addSuppressed(e);
        } catch (FontFormatException e) {
            e.addSuppressed(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end of method "addFonts"

    public void readScoreFile() {
        scoreFile = new File("scores.ser");

        try {
            fileIn = new FileInputStream(scoreFile);
            //objectIn = new ObjectInputStream(fileIn);
            scoreArray = new ArrayList<ScoreEntry>();
            objectReturned = true;
            /*
            while (true) {
                ScoreEntry entry = (ScoreEntry)objectIn.readObject();
                scoreArray.add(entry);
            }
            */
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

        /// Loops and if-statements for ranking

            for (int i=0; i<scoreArray.size(); i++) { // Go through every object (score entry) [for every object..]

                for (int d=0; d<diffcultyArray.size(); d++) { // [go through every difficulty added...]

                    if (scoreArray.get(i).getDifficulty() == diffcultyArray.get(d)) { // If the current (i) scoreEntry's difficulty equals the current (d) item in difficultyArray...

                        scoreArray.get(i).setRank(d); // Sets the current (i) scoreEntry's rank to ranking in "difficultyArray" (this will be adjusted later)
                    
                        if (i > 0) { // If this IS NOT the first item in "scoreArray", proceed. [one object has already been given a rank])
                        
                            //output.println("s.A. item:" + i + " difficulty: " + scoreArray.get(i).getDifficulty() + " and s.A. i-1: " + scoreArray.get(i-1).getDifficulty());

                            if (scoreArray.get(i).getDifficulty() == scoreArray.get(i-1).getDifficulty()) { // 
        
                                output.println("Difficulties matched!");

                                if (scoreArray.get(i).getTime() < scoreArray.get(i-1).getTime()) { // If current s.A. item has a lower time
                                    output.println("Current s.A. item has lower time!");
                                    scoreArray.get(i).decreaseRankVar();
                                }

                                if (scoreArray.get(i).getTime() > scoreArray.get(i-1).getTime()) { // If current s.A. item has a higher time
                                    output.println("Current s.A. item has higher time!");
                                    scoreArray.get(i).increaseRankVar();
                                }
                            } 
                        }
                    }
                }

                output.println("s.A. loop done! (" + i + ")");
            }

        for (int i=0; i<scoreArray.size(); i++) {
        output.println("-- ENTRY (" + i + ") RANK: "+scoreArray.get(i).getRank());
        output.println("-- ENTRY (" + i + ") DIFFICULTY: " + scoreArray.get(i).getDifficulty());
        output.println("-- ENTRY (" + i + ") TIME: " + scoreArray.get(i).getTime());
        }

        for (int i=0; i<scoreArray.size(); i++) { // This loop finds the top 3 scores and prints out info.
            
            switch (scoreArray.get(i).getRank()) {
                case 0:
                output.println(scoreArray.get(i).getUsername() + " had the best score with " + scoreArray.get(i).getDifficulty() + "% !");
                addRank1(scoreArray.get(i));
                    break;
                case 1:
                output.println(scoreArray.get(i).getUsername() + " had the 2nd best score with " + scoreArray.get(i).getDifficulty() + "% !");
                addRank2(scoreArray.get(i));
                    break;
                case 2:
                output.println(scoreArray.get(i).getUsername() + " had the 3rd best score with " + scoreArray.get(i).getDifficulty() + "% !");
                addRank3(scoreArray.get(i));
                    break;
            }
        }
    } // end of method "rankScores"

    public void addRank1(ScoreEntry number1) {

        /// Name
            oneName = new JLabel(number1.getUsername(), SwingConstants.CENTER);
            oneName.setFont(pixelFont);
            oneName.setBorder(BorderFactory.createLineBorder(Color.black));
            oneName.setBounds(180, 270, 92, 24);
            super.add(oneName);
        /// Difficulty
            oneDiff = new JLabel(number1.getDifficulty()+"%", SwingConstants.CENTER);
            oneDiff.setFont(pixelFont);
            oneDiff.setBorder(BorderFactory.createLineBorder(Color.black));
            oneDiff.setBounds(320, 270, 92, 24);
            super.add(oneDiff);
        /// Time
            oneTime = new JLabel(number1.getTime()+"s", SwingConstants.CENTER);
            oneTime.setFont(pixelFont);
            oneTime.setBorder(BorderFactory.createLineBorder(Color.black));
            oneTime.setBounds(465, 272, 92, 24);
            super.add(oneTime);

    } // end of method "addRank1"

    public void addRank2(ScoreEntry number2) {

        /// Name
            twoName = new JLabel(number2.getUsername(), SwingConstants.CENTER);
            twoName.setFont(pixelFont);
            twoName.setBorder(BorderFactory.createLineBorder(Color.black));
            twoName.setBounds(180, 360, 92, 24);
            super.add(twoName);
        /// Difficulty
            twoDiff = new JLabel(number2.getDifficulty()+"%", SwingConstants.CENTER);
            twoDiff.setFont(pixelFont);
            twoDiff.setBorder(BorderFactory.createLineBorder(Color.black));
            twoDiff.setBounds(320, 360, 92, 24);
            super.add(twoDiff);
        /// Time
            twoTime = new JLabel(number2.getTime()+"s", SwingConstants.CENTER);
            twoTime.setFont(pixelFont);
            twoTime.setBorder(BorderFactory.createLineBorder(Color.black));
            twoTime.setBounds(465, 360, 92, 24);
            super.add(twoTime);

    } // end of method "addRank2"

    public void addRank3(ScoreEntry number3) {

       /// Name
            threeName = new JLabel(number3.getUsername(), SwingConstants.CENTER);
            threeName.setFont(pixelFont);
            threeName.setBorder(BorderFactory.createLineBorder(Color.black));
            threeName.setBounds(180, 450, 92, 24);
            super.add(threeName);
        /// Difficulty
            threeDiff = new JLabel(number3.getDifficulty()+"%", SwingConstants.CENTER);
            threeDiff.setFont(pixelFont);
            threeDiff.setBorder(BorderFactory.createLineBorder(Color.black));
            threeDiff.setBounds(320, 450, 92, 24);
            super.add(threeDiff);
        /// Time
            threeTime = new JLabel(number3.getTime()+"s", SwingConstants.CENTER);
            threeTime.setFont(pixelFont);
            threeTime.setBorder(BorderFactory.createLineBorder(Color.black));
            threeTime.setBounds(465, 450, 92, 24);
            super.add(threeTime);

    } // end of method "addRank3"
} // end of class "game"