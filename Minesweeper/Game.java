/// Imported classes
    import java.io.*;
        import java.util.*;
        import java.awt.Color;
        import java.awt.Dimension;
        import java.awt.FlowLayout;
        import java.awt.Font;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.io.PrintWriter;
import java.text.DecimalFormat;
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
    import javax.swing.Timer;
    import javax.swing.UIManager;

    import org.w3c.dom.css.RGBColor;

public class Game {

    private static final long serialVersionUID = 1L;
    private Game game;
    private JFrame gameFrame;
    /// Variables for reciving input from constructor method
        private int mines;
        private int frameWidth;
        private int frameHeight;
    /// Fonts
        private Font pixelFont;
    /// Variables for class "Board"
        private Board playground;
    /// Variables for class "Header"
        private Header topBar;

    public Game(String nameFromInput, int widthFromInput, int heightFromInput, int minesFromInput) {
        mines = minesFromInput;
        frameWidth = widthFromInput * 20 + 40;   // Multiplied by 20 to make room for the 20x20 cells that will make up the playing field
        frameHeight = heightFromInput * 20 + 80; // Plus 40 and 80 to add margin between the playing field and the border of the JFrame

        gameFrame = new JFrame();

        gameFrame.setTitle(nameFromInput);
        gameFrame.setResizable(false);
        gameFrame.setLayout(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setMinimumSize(new Dimension(340, 200));
        gameFrame.setSize(frameWidth, frameHeight);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        System.out.println("frameWidth: " + frameWidth + " frameHeight: " + frameHeight);
        
        /// Other methods and classes goes below
        addFonts();

        if (frameWidth > 340) {
            topBar = new Header(frameWidth, gameFrame, widthFromInput, heightFromInput, minesFromInput);
        } else {
            topBar = new Header(340, gameFrame, widthFromInput, heightFromInput, minesFromInput);
        }
        gameFrame.add(topBar);

        playground = new Board(widthFromInput, heightFromInput, minesFromInput, topBar.returnScoreLabel(), topBar.returnTimeLabel(), topBar.returnRefreshTimer());
        gameFrame.add(playground);
        System.out.println("Playground width: " + playground.getWidth() + " height: " + playground.getHeight() );
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

	/// General varibles
        private Dimension componentDimension;
        private int barCoordinate;
    /// Visual components
        private JLabel scoreLabel;
        private JButton menuButton;
        private JButton restartButton;
        private JLabel timeLabel;
    /// Other variables
        private static final long serialVersionUID = 1L;
        static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        static PrintStream output = System.out;
        private MainMenu menu;
        private JFrame frameHolder;
        private Game gameHolder;
    /// For clock
        private Timer refreshTimer; // Used for refreshing the counter every 1 second
        private int timeKeeper;
    /// Fonts
        private Font pixelFont;
    /// Colors
        private Color darkerGray = new Color(45, 45, 45);
        private Color darkerWhite = new Color(240,240,240);
        private Color regularGray = new Color(190, 190, 190);

    public Header(int widthOfFrame, JFrame mainFrame, int widthForNewGame, int heightForNewGame, int minesForNewGame) {
        frameHolder = mainFrame;
        barCoordinate = widthOfFrame/2 - 150;
        //output.println("Header starts at:" + barCoordinate);

        //setBackground(Color.red);
        setBounds(barCoordinate, 7, 300, 30);
        setLayout(new FlowLayout(FlowLayout.CENTER,0, -6));
        addFonts();
        addComponents(widthForNewGame, heightForNewGame, minesForNewGame);
        repaint();
        setVisible(true);

    } // end of constructor method "Header"

    public void addComponents(int widthForNewGame, int heightForNewGame, int minesForNewGame) {
        
        componentDimension = new Dimension(64, 30);

        addScoreLabel();
        addMenuButton(); 
        addRestartButton(widthForNewGame, heightForNewGame, minesForNewGame);
        addTimeLabel();
    } // end of method "addComponents"

    public void addScoreLabel() {
        scoreLabel = new JLabel("0", SwingConstants.CENTER);
        scoreLabel.setFont(pixelFont);
        scoreLabel.setForeground(darkerWhite);
        scoreLabel.setMinimumSize(componentDimension);
        scoreLabel.setPreferredSize(componentDimension);
        scoreLabel.setMaximumSize(componentDimension);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(darkerGray);
        add(scoreLabel);
    } // end of method "addScoreLabel"

    public void addMenuButton() {
        menuButton = new JButton(new ImageIcon("ToMenu.png"));
        menuButton.setContentAreaFilled(true);
        menuButton.setBorderPainted(true);
        menuButton.setFocusPainted(false);

        menuButton.setMinimumSize(new Dimension(70, 35));;
        menuButton.setPreferredSize(new Dimension(70, 35));
        menuButton.setMaximumSize(new Dimension(70, 35));
        menuButton.setOpaque(false);

        menuButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                output.println("Menu button pressed!");
                refreshTimer.stop();
                frameHolder.dispose();
                menu = new MainMenu("Main Menu");
            }
        });

        add(menuButton);
    } // end of method "addMenuButton"

    public void addRestartButton(int widthForNewGame, int heightForNewGame, int minesForNewGame) {
        restartButton = new JButton(new ImageIcon("Restart.png"));
        restartButton.setContentAreaFilled(true);
        restartButton.setBorderPainted(true);
        restartButton.setFocusPainted(false);

        restartButton.setMinimumSize(new Dimension(70, 35));;
        restartButton.setPreferredSize(new Dimension(70, 35));
        restartButton.setMaximumSize(new Dimension(70, 35));
        restartButton.setOpaque(false);

        restartButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                output.println("Restart button pressed!");
                refreshTimer.stop();
                frameHolder.dispose();
                gameHolder = new Game("Minesweeper", widthForNewGame, heightForNewGame, minesForNewGame);
            }
        });

        add(restartButton);
    } // end of method "addRestartButton"

    public void addTimeLabel() {

        timeKeeper = 0;

        timeLabel = new JLabel("0", SwingConstants.CENTER);
        timeLabel.setFont(pixelFont);
        timeLabel.setForeground(darkerWhite);
        timeLabel.setMinimumSize(componentDimension);
        timeLabel.setPreferredSize(componentDimension);
        timeLabel.setMaximumSize(componentDimension);
        timeLabel.setOpaque(true);
        timeLabel.setBackground(darkerGray);

        refreshTimer = new Timer(1000, new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                timeKeeper++;
                //output.println("<<< Time at: " + timeKeeper);
                timeLabel.setText(""+timeKeeper);
            }
        });
        refreshTimer.setRepeats(true);
        refreshTimer.start();

        add(timeLabel);
    } // end of method "addTimeLabel"

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

    public JLabel returnScoreLabel() {
        return scoreLabel;
    } // end of method "returnScoreLabel"

    public JLabel returnTimeLabel() {
        return timeLabel;
    } // end of method "returnTimeLabel"

    public Timer returnRefreshTimer() {
        return refreshTimer;
    } // end of method "returnRefreshTimer"
} // end of class "Header"

class Board extends JPanel { 
    private static final long serialVersionUID = 1L;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;
    private int rngY;
    private int rngX;
    private Cell[][] cellMatrix;

	public Board(int width, int height, int mines, JLabel scoreLabelFromInput, JLabel timeLabelFromInput, Timer timerFromInput) {

        // TODO: Issue: The absolute positioning of the panel stops working when the user enters a 14 or lower. 

        setLayout(new GridLayout(height, width));
        //setBackground(Color.yellow); // Enable this line to show dimension of the JPanel "playground".
        setBounds(20, 40, width * 20, height * 20); // width and height multiplied by 20 to fit in the 20px*20px squares.

        cellMatrix = new Cell[height+2][width+2]; // Adding 2 rows and 2 cols in order to make buffer around the actual playing field.

        for (int y=0; y < height+2; y++) {      // Adding 2 rows and 2 cols in order to make buffer around the actual playing field.
            for (int x=0; x < width+2; x++) {   // See previous comment :)
                cellMatrix[y][x] = new Cell(y, x, cellMatrix, scoreLabelFromInput, timeLabelFromInput, timerFromInput);
                cellMatrix[y][x].resetNonMines();
                if (y == 0 | x == 0 | x == width+1 | y == height+1) { // if this cell is located outside of the JPanel, then modify variable.
                    cellMatrix[y][x].setNotOpenable();
                    output.println("<<< Y: " + y + " X: " + x + " has a 0!");
                }
            }
        }

        for (int y=1; y < height+1; y++) {     // integers "y" and "x" set to 1 because the cells at [0][0] are not to be added to the Panel. 
            for (int x=1; x < width+1; x++) {  // Removing the +1(s) here breaks the layout because a cell on both axies is removed.
                add(cellMatrix[y][x]);
                cellMatrix[y][x].increaseNonMines(); // This sets "Cell (class)" variable "nonMines" to the number of cells created. This is corrected in Cell.addMine().
                output.println("y: " + y + " height: " + height + " x: " + x + " width: " + width);
            }
        }

        if (mines > height*width) { // If the user entered more mines than field can fit, set to max amount of mines possible. 
            mines = height*width;
        }

        for (int i=0; i < mines; i++) {
            rngY = (int)(Math.random()* height +1); // Should return integer from 1 to the value of "height" (inclusive) 
            rngX = (int)(Math.random()* width +1);  // Here I add a 1 to skip any cells at [0][?] and [?][0] in the "cellMatrix"
            output.println("rngY: " + rngY);
            output.println("rngX: " + rngX);

            if (cellMatrix[rngY][rngX].hasMineCheck()) {
                output.println("Mine-spawning process 'i' at: " + i);
                i--;
                output.println("Mine-spawning process 'i' reduced to: " + i);
            } else {
                output.println("Mine-spawning process 'i' wasn't reduced, and is at: " + i);
                cellMatrix[rngY][rngX].addMine();
                output.println("Mine " + i + " added!");
            }
        }
        repaint();
        setVisible(true);
    } // end of constructor method "Board"
} // end of class "Board"

class Cell extends JButton {
    private static final long serialVersionUID = 1L;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;
    static int cellsOpened;
    private JLabel scoreLabel; // Stores the "scoreLabelFromInput"
    private JLabel timeLabel; // Stores the "timeLabelFromInput"
    private Timer refreshTimer; // Stores the "timerFromInput"
    static int nonMines; // Used for storing number of cells that don't have mines.

    private Font fallbackFont = new Font("Sans-Serif", Font.PLAIN, 8);
    private Boolean isClosed;
    private Boolean hasMine;
    private Boolean isOpenable; // Default set to "true", but for the invisible edge-cells, this should be set to "false".
    private int foundMines; // Used as the returned integer from method "checkNeighbors"
    private int nearMines;

    /// Variables used for JOptionPane 
        private JTextField nameField;
        private int victoryPane;
        private String username; // Stores whatever comes from variable "nameField"
        private UIManager optionPaneManager;
        private Object[] message; // Variable "nameField" is put in here further down.
        private Color regularGray = new Color(190, 190, 190);
        private ImageIcon largeBomb;
        private int paneButton;
        private JLabel timeField;
        private JLabel openedCellsField;
        private JLabel scoreField;
        private int duration;
        static int mines = 0;
        private double difficulty;
        private int finalDifficulty;
    /// Variables used for file I/O
        private ScoreEntry entryToScore;
        static File scoreFile;
        static FileOutputStream fileO;
        static ObjectOutputStream objectOutStream;
        //static Scanner fileI = new Scanner(scoreFile);
    ///
    public Cell(int yPos, int xPos, Cell[][] theMatrix, JLabel scoreLabelFromInput, JLabel timeLabelFromInput, Timer timerFromInput) {
        /// Frontend 
        setMinimumSize(new Dimension(20,20));;
        setPreferredSize(new Dimension(20,20));
        setMaximumSize(new Dimension(20,20));
        setIcon(new ImageIcon("UnopenedSquare.png"));
        /// Backend
        scoreLabel = scoreLabelFromInput;
        timeLabel = timeLabelFromInput;
        refreshTimer = timerFromInput;
        cellsOpened = 0;
        mines = 0;

        this.isOpenable = true;
        this.isClosed = true;
        this.hasMine = false;
        this.nearMines = 0;
        addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isClosed) {
                    if (hasMine) {
                        setBorder(BorderFactory.createLineBorder(Color.gray, 1));
                        setIcon(new ImageIcon("MineIcon.png"));
                        output.println("You lose");
                        refreshTimer.stop();
                        for (int y = 1; y < theMatrix.length-1; y++) { // Reveals entire field   
                            for (int x = 1; x < theMatrix[0].length-1; x++) {
                                revealCell(y, x, theMatrix); 
                            }
                        }
                    } else {
                        theMatrix[yPos][xPos].nearMines = checkNeighbors(yPos, xPos, theMatrix);
                        openCell(yPos, xPos, theMatrix, theMatrix[yPos][xPos].nearMines);
                        if (theMatrix[yPos][xPos].nearMines == 0) { // Only if the cell doesn't border any bombs will "sweeperLoop" be run.
                            sweeperLoop(yPos, xPos, theMatrix);
                        }
                    }
                }
            }
        });
    } // end of constructor method "Cell"

    public void openCell(int yPos, int xPos, Cell[][] theMatrix, int surroundingMines) {
        if (theMatrix[yPos][xPos].hasMine) {
            theMatrix[yPos][xPos].setText(null);
            output.println("A mine was about to be erased!");
        } else if (yPos == 0 | xPos == 0) {
            theMatrix[yPos][xPos].setEnabled(false);
            theMatrix[yPos][xPos].isOpenable = false;
        } else {
            theMatrix[yPos][xPos].setIcon(null);
            theMatrix[yPos][xPos].isClosed = false;
            theMatrix[yPos][xPos].setText(surroundingMines+"");
            theMatrix[yPos][xPos].setBorder(BorderFactory.createLineBorder(Color.gray, 1));
            cellsOpened++;
            output.println("<<< Number of cells opened: " + cellsOpened);
            output.println("<<< Number of cells left: " + (nonMines-cellsOpened));
            scoreLabel.setText(""+ (nonMines-cellsOpened));
            
            if (cellsOpened == nonMines) { // If all non-mine-cells have been cleared...
                // VICTORY!
                toVictory();
            }
        }
    } // end of method "openCell"

    public void revealCell(int yPos, int xPos, Cell[][] theMatrix) { // Used when player has LOST the game. Not to be confused with method "openCell"!
        if (theMatrix[yPos][xPos].hasMine) {
            theMatrix[yPos][xPos].setBorder(BorderFactory.createLineBorder(Color.gray, 1));
            theMatrix[yPos][xPos].setIcon(new ImageIcon("MineIcon.png"));
        } else {
            theMatrix[yPos][xPos].setIcon(null);
            theMatrix[yPos][xPos].isClosed = false;
            theMatrix[yPos][xPos].setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        }
    } // end of method "revealCell"

    public void addMine() {
        this.hasMine = true;
        mines++; // This lets the program count how many mines are added without having to pass a variable down from parent class.
        nonMines--; // Without running this, "nonMines" will be set to the total number of cells. This corrects it.
        setMineCounter(); // Sets the displayed number of (non) cells left to the actual number and not the total number of cells. This CAN'T be put under increaseNonMines()!
    } // end of method "addMine"

    public Boolean hasMineCheck() {
        if (this.hasMine) {
            return true;
        } else {
            return false;
        }
    } // end of method "hasMineCheck"

    public int checkNeighbors(int yPos, int xPos, Cell[][] theMatrix) {

        foundMines = 0;

        if (theMatrix[yPos][xPos].isOpenable) {
            /// Old code
                /*
                /// Upper row

                if (theMatrix[yPos-1][xPos-1].hasMine) {
                    foundMines++;
                    output.println("There is a mine nearby!");
                }

                if (theMatrix[yPos-1][xPos].hasMine) {
                    foundMines++;
                    output.println("There is a mine nearby!");
                }

                if (theMatrix[yPos-1][xPos+1].hasMine) {
                    foundMines++;
                    output.println("There is a mine nearby!");
                }

                /// Middle row

                if (theMatrix[yPos][xPos-1].hasMine) {
                    foundMines++;
                    output.println("There is a mine nearby!");
                }

                if (theMatrix[yPos][xPos+1].hasMine) {
                    foundMines++;
                    output.println("There is a mine nearby!");
                }

                /// Lower row 
                
                if (theMatrix[yPos+1][xPos-1].hasMine) {
                    foundMines++;
                    output.println("There is a mine nearby!");
                }

                if (theMatrix[yPos+1][xPos].hasMine) {
                    foundMines++;
                    output.println("There is a mine nearby!");
                }

                if (theMatrix[yPos+1][xPos+1].hasMine) {
                    foundMines++;
                    output.println("There is a mine nearby!");
                }
                */
            /// New code
                for (int x=-1; x<2; x++) {
                    for (int y=-1; y<2; y++) {
                        if ((x==0) & (y==0)) {
                            //skip 
                        } else {
                            if (theMatrix[yPos+y][xPos+x].hasMine) {
                                foundMines++;
                                output.println("There is a mine nearby!");
                            }
                        }
                    }
                }
        } else { // This else-part combined with the PrintStream stops the program from generating an "ArrayIndexOutOfBoundsException".
            output.println("<<< At method 'checkNeighbors', the main if-statement was bypassed and 'else' was used. >>>");
        }
        return foundMines;
    } // end of method "checkNeighbors"

    public void sweeperLoop(int yPos, int xPos, Cell[][] theMatrix) {
        /// Old loop
            /*
            /// Upper row (3)
            if (theMatrix[yPos-1][xPos-1].isClosed & !theMatrix[yPos-1][xPos-1].hasMine & theMatrix[yPos-1][xPos-1].isOpenable) {
                sweeperHelper(yPos-1, xPos-1, theMatrix);
                if (theMatrix[yPos-1][xPos-1].nearMines == 0) {
                    sweeperLoop(yPos-1, xPos-1, theMatrix);
                }
            }
            if (theMatrix[yPos-1][xPos].isClosed & !theMatrix[yPos-1][xPos].hasMine & theMatrix[yPos-1][xPos].isOpenable) {
                sweeperHelper(yPos-1, xPos, theMatrix);
                if (theMatrix[yPos-1][xPos].nearMines == 0) {
                    sweeperLoop(yPos-1, xPos, theMatrix);
                }
            }
            if (theMatrix[yPos-1][xPos+1].isClosed & !theMatrix[yPos-1][xPos+1].hasMine & theMatrix[yPos-1][xPos+1].isOpenable) {
                sweeperHelper(yPos-1, xPos+1, theMatrix);
                if (theMatrix[yPos-1][xPos+1].nearMines == 0) {
                    sweeperLoop(yPos-1, xPos+1, theMatrix);
                }
            }
            /// Middle row (2)
            if (theMatrix[yPos][xPos-1].isClosed & !theMatrix[yPos][xPos-1].hasMine & theMatrix[yPos][xPos-1].isOpenable) {
                sweeperHelper(yPos, xPos-1, theMatrix);
                if (theMatrix[yPos][xPos-1].nearMines == 0) {
                    sweeperLoop(yPos, xPos-1, theMatrix);
                }
            }
            if (theMatrix[yPos][xPos+1].isClosed & !theMatrix[yPos][xPos+1].hasMine & theMatrix[yPos][xPos+1].isOpenable) {
                sweeperHelper(yPos, xPos+1, theMatrix);
                if (theMatrix[yPos][xPos+1].nearMines == 0) {
                    sweeperLoop(yPos, xPos+1, theMatrix);
                }
            }
            /// Lower row (3)
            if (theMatrix[yPos+1][xPos-1].isClosed & !theMatrix[yPos+1][xPos-1].hasMine & theMatrix[yPos+1][xPos-1].isOpenable) {
                sweeperHelper(yPos+1, xPos-1, theMatrix);
                if (theMatrix[yPos+1][xPos-1].nearMines == 0) {
                    sweeperLoop(yPos+1, xPos-1, theMatrix);
                }
            }
            if (theMatrix[yPos+1][xPos].isClosed & !theMatrix[yPos+1][xPos].hasMine & theMatrix[yPos+1][xPos].isOpenable) {
                sweeperHelper(yPos+1, xPos, theMatrix);
                if (theMatrix[yPos+1][xPos].nearMines == 0) {
                    sweeperLoop(yPos+1, xPos, theMatrix);
                }
            }
            if (theMatrix[yPos+1][xPos+1].isClosed & !theMatrix[yPos+1][xPos+1].hasMine & theMatrix[yPos+1][xPos+1].isOpenable) {
                sweeperHelper(yPos+1, xPos+1, theMatrix);
                if (theMatrix[yPos+1][xPos+1].nearMines == 0) {
                    sweeperLoop(yPos+1, xPos+1, theMatrix);
                }
            }
            */
        /// New loop
            for (int x=-1; x<2; x++) {
                for (int y=-1; y<2; y++) {
                    if ((x==0) & (y==0)) {
                        //skip 
                    } else {
                        if (theMatrix[yPos+y][xPos+x].isClosed & !theMatrix[yPos+y][xPos+x].hasMine & theMatrix[yPos+y][xPos+x].isOpenable) {
                            sweeperHelper(yPos+y, xPos+x, theMatrix);
                            if (theMatrix[yPos+y][xPos+x].nearMines == 0) {
                                sweeperLoop(yPos+y, xPos+x, theMatrix);
                            }
                        }
                    }
                }
            }
    } // end of method "sweeperLoop"

    public void sweeperHelper(int yPos, int xPos, Cell[][] theMatrix) { // Used for the method "sweeperLoop" in order to reduce clutter.
        theMatrix[yPos][xPos].nearMines = checkNeighbors(yPos, xPos, theMatrix);
        openCell(yPos, xPos, theMatrix, theMatrix[yPos][xPos].nearMines);
    } // end of method "sweeperHelper"

    public void setNotOpenable() { // Used for setting invisible edge-cells' "isOpenable" variable to "false"-
        this.isOpenable = false;
    } // end of method "setNotOpenable"

    public void increaseNonMines() {
        nonMines++;
    } // end of method "increaseNonMines"

    public void setMineCounter() { // Used for setting the displayed amount of non-mines at the beginning of the game.
        scoreLabel.setText(""+nonMines);
    } // end of method "setMineCounter"

    public void resetNonMines() { // Resets variable "nonMines" to 0. Restarting without running this will result in an incorrect value.
        nonMines = 0;
    } // end of method "resetNonMines"

    public void toVictory() { // Method that is run after winning the game.
        refreshTimer.stop();
        output.println("<==> Congratulations, you've won!");
        output.println("<==> It took: " + timeLabel.getText() + " seconds!");
        output.println("<==> You cleared: " + cellsOpened + " squares!");

        duration = Integer.parseInt(timeLabel.getText());

        output.println("Mines: " + mines);

        difficulty = (float)mines/(cellsOpened+mines) * 100;
        output.println(difficulty);
        difficulty = Math.round(difficulty * 100) / 100;

        finalDifficulty = (int)difficulty;
        output.println("INTED: " + finalDifficulty);
        output.println("CALC: " + difficulty + " Time: " + duration + " /// " + (cellsOpened+mines));


        if (cellsOpened+mines >= 81) { // If the minimum requirement of 81+ squares is met...
            nameField = new JTextField();
            timeField = new JLabel("Your time: " + timeLabel.getText() + "s");
            openedCellsField = new JLabel("Cells opened: " + cellsOpened);
            scoreField = new JLabel("Difficulty: " + finalDifficulty + "");

            message = new Object[] {
                "", timeField,
                "", openedCellsField,
                "", scoreField,
                "Enter username:", 
                "(6 characters max)",
                nameField
            };

            largeBomb = new ImageIcon("bombIcon.png");

            optionPaneManager = new UIManager();
            optionPaneManager.put("OptionPane.background", regularGray);
            optionPaneManager.put("Panel.background", regularGray);

            victoryPane = JOptionPane.showConfirmDialog(null, message, "Congratulations!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, largeBomb);

            if (victoryPane == JOptionPane.OK_OPTION) {
                username = nameField.getText();

                entryToScore = new ScoreEntry(username, finalDifficulty, duration); // Creates an instance of the class "ScoreEntry" which contains the given vars.
                
                scoreFile = new File("scores.ser"); // Creates a file called "scores.txt" if no such file already exists
                
                try {
                    fileO = new FileOutputStream(scoreFile, true); // The FileOutoutStream is used to read/write to a file ("scoreFile" in this case) in bytes.
                    objectOutStream = new ObjectOutputStream(fileO); // ObjectOutputStream serializes/deserializes the object. (Writes the object to the file in this case).
        
                    objectOutStream.writeObject(entryToScore);
                    
                    objectOutStream.close();
                    fileO.close();   
                } catch (FileNotFoundException fnfe) {
                    output.println("!! -A file is missing!");
                } catch (IOException ioe) {
                    output.println("!! - An IOException was generated!");
                }
            } else if (victoryPane == JOptionPane.CLOSED_OPTION) {
                while (victoryPane != JOptionPane.OK_OPTION) {
                    victoryPane = JOptionPane.showConfirmDialog(null, message, "Congratulations!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, largeBomb);
                }
            }
        }
    } // end of method "toVictory"
} // end of class "Cell" 

class ScoreEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private int difficulty;
    private int time;
    private int rank = 0;

    public ScoreEntry(String usernameInput, int difficultyInput, int timeInput) {
        this.username = usernameInput;
        this.difficulty = difficultyInput;
        this.time = timeInput;
    } // end of constructor method "ScoreEntry"

    public String getUsername() {
        return this.username;
    }
    public int getTime() {
        return this.time;
    }
    public int getDifficulty() {
        return this.difficulty;
    }
    public void decreaseRankVar() {
        this.rank -= 1;
    }
    public void increaseRankVar() {
        this.rank += 1;
    }
    public int getRank() {
        return this.rank;
    }
    public void setRank(int spot) {
        this.rank = spot;
    }
} // end of class "ScoreEntry"