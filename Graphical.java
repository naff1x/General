import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Graphical extends JFrame {
    private static final long serialVersionUID = 1L;
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

        Dimension button = new Dimension(120, 100);

        JPanel monsterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 7, 7));
        monsterPanel.setOpaque(false);

        for (int i=0; i<4; i++) {
            Jb = new JButton(new ImageIcon("pikachu.png"));
            Jb.setContentAreaFilled(true);
            Jb.setBorderPainted(false);
            Jb.setFocusPainted(false);
            Jb.setPreferredSize(button);
            monsterPanel.add(Jb);
        }
        f.add(monsterPanel);

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


/*
class PikachuButton {
    private JButton Jb;
    
    public void PikachuButton() {
        Jb = new JButton(new ImageIcon("pikachu.png"));
        Jb.setContentAreaFilled(true);
        Jb.setBorderPainted(false);
        Jb.setFocusPainted(false);
        Jb.setPreferredSize(button);
    }
}
*/