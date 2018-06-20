import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.*;


public class GUI extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    
    private JButton button;
    private JLabel label;

    public GUI(String name) {
        setTitle(name);
        setResizable(false);
        setContentPane(new JLabel(new ImageIcon("background1.jpg")));
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600,500);
        setVisible(true);
    }

    public void addComp() {
        button = new JButton("Click me for text!");
        add(button);


        label = new JLabel("");
        add(label);

        button.addActionListener(this);
        
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            label.setText("hello");
        }
    
}

    public static void main(String[] args) {
        GUI mainGUI = new GUI("The GUI");
        mainGUI.addComp();
    }
} // end of class "GUI"