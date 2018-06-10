import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.Color;

public class Frame extends Canvas {
    static final long serialVersionUID = 1;
    public static void main(String[] args) {
        JFrame f = new JFrame("Monster Feeder: The Game");
        Canvas canvas = new Frame();
        canvas.setSize(700, 200);
        canvas.setBackground(Color.gray);
        f.setResizable(false);
        f.add(canvas);
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminates program once window is closed.
    }

    public void paint(Graphics g) {
        g.fillOval(100, 100, 70, 70);
        g.setColor(Color.red);
        // g.setColor(Color.black);
        g.fillRect(300, 300, 50, 50);
        
    }
} // end of Frame