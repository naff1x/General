import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

//test

public class Graphical extends JFrame {
    private static final long serialVersionUID = 1L;
    private ImageIcon image1;
    private JLabel label1;

    JButton[] nines = new JButton[10];
    public void Window() { // Constructor. Creates window
        JFrame f = new JFrame("The Window");
        f.setContentPane(new JLabel(new ImageIcon("500x500.jpg")));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setSize(500,500);
        f.setLocationRelativeTo(null);    
        
        f.setLayout(new GridLayout(3,3,7,7));
        for (int i=1; i<10; i++) {
            nines[i] = new JButton("Number: " + i);
            f.add(nines[i]);
        }

        f.setVisible(true);
    }

    public static void main(String[] args) { // Contains all the code and executions 
        Graphical g = new Graphical();
        g.Window();
    }
} // end of Graphical