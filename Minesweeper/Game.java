import java.io.*;
    import java.util.*;
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.FlowLayout;
    import java.awt.Font;
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

        playground = new Board(widthFromInput, heightFromInput, minesFromInput, topBar.returnScoreLabel());
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

    private static final long serialVersionUID = 1L;
	/// General varibles
    private Dimension componentDimension;
    private int barCoordinate;
    /// Visual components
    private JLabel scoreLabel;
    private JButton menuButton;
    private JButton restartButton;
    private JLabel timeLabel;
    /// Other variables
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
} // end of class "Header"

class Board extends JPanel { 
    private static final long serialVersionUID = 1L;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;
    private int rngY;
    private int rngX;
    private Cell[][] cellMatrix;

	public Board(int width, int height, int mines, JLabel labelFromInput) {

        // TODO: Issue: The absolute positioning of the panel stops working when the user enters a 14 or lower. 

        setLayout(new GridLayout(height, width));
        //setBackground(Color.yellow); // Enable this line to show dimension of the JPanel "playground".
        setBounds(20, 40, width * 20, height * 20); // width and height multiplied by 20 to fit in the 20px*20px squares.

        cellMatrix = new Cell[height+2][width+2]; // Adding 2 rows and 2 cols in order to make buffer around the actual playing field.

        for (int y=0; y < height+2; y++) {      // Adding 2 rows and 2 cols in order to make buffer around the actual playing field.
            for (int x=0; x < width+2; x++) {   // See previous comment :)
                cellMatrix[y][x] = new Cell(y, x, cellMatrix, labelFromInput);
                if (y == 0 | x == 0 | x == width+1 | y == height+1) { // if this cell is located outside of the JPanel, then modify variable.
                    cellMatrix[y][x].setNotOpenable();
                    output.println("<<< Y: " + y + " X: " + x + " has a 0!");
                }
            }
        }

        for (int y=1; y < height+1; y++) {     // integers "y" and "x" set to 1 because the cells at [0][0] are not to be added to the Panel. 
            for (int x=1; x < width+1; x++) {  // Removing the +1(s) here breaks the layout because a cell on both axies is removed.
                add(cellMatrix[y][x]);
                cellMatrix[y][x].increaseNonMines(); // This sets "Cell (class)" variable "nonMines" to the number of cells created. This is corrected in Cell.addMine() however.
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
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;
    static int cellsOpened;
    private JLabel scoreLabel; // Stores the "labelFromInput"
    static int nonMines; // Used for storing number of cells that don't have mines.

    private Font fallbackFont = new Font("Sans-Serif", Font.PLAIN, 8);
    private Boolean isClosed;
    private Boolean hasMine;
    private Boolean isOpenable; // Default set to "true", but for the invisible edge-cells, this should be set to "false".
    private int foundMines; // Used as the returned integer from method "checkNeighbors"
    private int nearMines;

    public Cell(int yPos, int xPos, Cell[][] theMatrix, JLabel labelFromInput) {
        /// Frontend 
        setMinimumSize(new Dimension(20,20));;
        setPreferredSize(new Dimension(20,20));
        setMaximumSize(new Dimension(20,20));
        setIcon(new ImageIcon("UnopenedSquare.png"));
        /// Backend
        scoreLabel = labelFromInput;
        cellsOpened = 0;

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
        }
        output.println(">>> NonMines at: "+nonMines);
    } // end of method "openCell"

    public void addMine() {
        this.hasMine = true;
        nonMines--; // Without running this, "nonMines" will be set to the total number of cells. This corrects it.
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
        } else { // This else-part combined with the PrintStream stops the program from generating an "ArrayIndexOutOfBoundsException".
            output.println("<<< At method 'checkNeighbors', the main if-statement was bypassed and 'else' was used. >>>");
        }
        return foundMines;
    } // end of method "checkNeighbors"

    public void sweeperLoop(int yPos, int xPos, Cell[][] theMatrix) {

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
} // end of class "Cell" 