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
import java.io.*;


public class GUI extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    
    private JButton button;
    private JLabel label;
    private JPanel panel;
    private JButton[] buttons = new JButton[4];

    public GUI(String name) {
        setTitle(name);
        setResizable(false);
        setContentPane(new JLabel(new ImageIcon("background1.jpg")));
        //setLayout(new FlowLayout());
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,500);
        setVisible(true);
    }

    public void addComp() {

        Dimension buttonSize = new Dimension(120, 100);

        button = new JButton(new ImageIcon("pikachu.png"));
        //button = new JButton("Click me for text!");
        button.setBounds(50, 50, 120, 100);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(buttonSize);
        add(button);


        label = new JLabel("");
        label.setFont(new Font("Sans-Serif", Font.BOLD, 30));
        label.setBounds(300, 10, 120, 100);
        add(label);

        button.addActionListener(this);
    }

    public void pikachu() { // Adds pikachu-buttons to frame

        for (int i=0; i<4; i++) {
            buttons[i] = new JButton(new ImageIcon("pikachu.png"));
            buttons[i].setContentAreaFilled(true);
            buttons[i].setBorderPainted(false);
            buttons[i].setFocusPainted(false);
        }

        buttons[0].setBounds(10, 10, 120, 100);
        buttons[1].setBounds(161, 10, 120, 100);
        buttons[2].setBounds(315, 10, 120, 100);
        buttons[3].setBounds(464, 10, 120, 100);

        for (int i=0; i<4; i++) {
            add(buttons[i]); 
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            label.setText("hello");
        }
    
}

    public static void main(String[] args) throws InterruptedException{
        GUI mainGUI = new GUI("The GUI");
        // mainGUI.addComp();
        mainGUI.pikachu();
        mainGUI.setLocationRelativeTo(null);
    }
} // end of class "GUI"