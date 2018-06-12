import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Graphical extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel Jl;
    private ImageIcon img;
    private JButton Jb;
    JButton[] nines = new JButton[10];
    public void Window() {
        JFrame f = new JFrame("Pokemon Feeder");
        f.setContentPane(new JLabel(new ImageIcon("background1.jpg")));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600,500);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setLayout(new FlowLayout());
        /*
        img = new ImageIcon(getClass().getResource("pikachu.png"));
        Jl = new JLabel(img);
        f.add(Jl);
        */
        Jb = new JButton(new ImageIcon("pikachu.png"));
        f.add(Jb);

        /*
        File imageCheck = new File("pikachu.png");
        if (imageCheck.exists()) {
            System.out.println("Image file found!");
        } else {
            System.out.println("Image file not found!");
        }
        */

        /* 
        f.setLayout(new GridLayout(2,6,7,7));
        for (int i=1; i<10; i++) {
            nines[i] = new JButton("Number: " + i);
            f.add(nines[i]);
        */

        f.setVisible(true);
    }

    public static void main(String[] args) { // Contains all the code and executions 
        Graphical g = new Graphical();
        g.Window();
    }
} // end of Graphical