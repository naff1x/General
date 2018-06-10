import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Graphical extends JFrame {
    private static final long serialVersionUID = 1L;

    JButton[] nines = new JButton[10];
    public void Window() { // Constructor. Creates window
        JFrame f = new JFrame("The Window");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setSize(500,500);
        f.setLocationRelativeTo(null);
        f.setLayout(new GridLayout(4,4,5,5));
        
        for (int i=1; i<10; i++) {
            nines[i] = new JButton("Number: " + i);
            f.add(nines[i]);
        }
        /*
        JButton button1 = new JButton("Hello");
        f.add(button1);

        JPanel p = new JPanel();
        JButton button2 = new JButton("this is a panel");
        p.add(button2);
        f.add(p);
        */
    }
/*
    public void panelAdder() {
        JPanel p = new JPanel();

    }
*/
    public static void main(String[] args) { // Contains all the code and executions 
        Graphical g = new Graphical();
        g.Window();
    }
} // end of Graphical