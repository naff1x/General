import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;
import java.awt.*;

public class MFGrafik {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;
    static String newLine = System.lineSeparator();
    static ArrayList<Monster> monsterVector = new ArrayList<Monster>();
    public static void main(String[] args) throws IOException, InterruptedException{
        MFGrafik mfg = new MFGrafik();
        mfg.init();
        output.println("Welcome to Monster Feeder!" + newLine);
        int antal = 5;

        for (int i=0; i<antal; i++) { // creates and adds Monster-instances to monsterVector
            Monster a = new Monster();
            monsterVector.add(a);
            output.print("yum...");
        }
        output.println(newLine);
        mfg.gameLoop();
    }
    

    public void init() throws InterruptedException{
        window();
        output.print(newLine + "Game starting");
        Thread.sleep(400);
        System.out.print(".");
        Thread.sleep(400);
        System.out.print(".");
        Thread.sleep(400);
        System.out.println("." + newLine);
        Thread.sleep(400);
    } // end of init

    public void window() {
        JFrame f = new JFrame();
        f.setBackground(Color.black);
        f.setSize(600,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
    }

    public void gameLoop() throws IOException {
        int roundCount = 0;
        while (monsterVector.size() > 1) {  
            
            output.println("Number of cookies each monster has:");
            for (int i=0; i<monsterVector.size(); i++) {
                output.print("Monster " + (i+1) + ": " + monsterVector.get(i).cookieCount() + "     ");
            }
            output.print(newLine);
            feed();
            eat();
            output.println(newLine);
            roundCount++;
        }
        output.println("Number of rounds played: " + roundCount);
        output.println("Thanks for playing!");
    } // end of gameLoop    

    public void feed() throws IOException {
        int food = ThreadLocalRandom.current().nextInt(0, 2 + 1);
            if (food == 0) {
                output.print("No cookies to give!");
            } else {
                output.print("Cookies you can give: " + food + ". ");
                output.print("Which monster do you want to feed?: ");
                int svar = Integer.parseInt(input.readLine());
                monsterVector.get(svar-1).increase(food); // svar-1 because monster "1" is actually in slot 0 of the vector  
            }
            output.print(newLine);
    } // end of feed    
    
    public void eat() {
        for (int i=0; i<monsterVector.size(); i++) {
            monsterVector.get(i).decrease();

            if (!monsterVector.get(i).isAlive()) { // if monster dies...
                monsterVector.remove(monsterVector.get(i));
                output.print("aargh!...");
            } else {
                output.print("yum...");
            }
            
        }
    } // end of eat
} // end of MFGrafik's class

class Monster {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;

    private int cookies;

    public Monster() {
        cookies = ThreadLocalRandom.current().nextInt(2, 5 + 1);
    } // end of Monster's method

    public Boolean isAlive() {
        return cookies>=0;
    } // end of isAlive's method

    public void decrease() { // decreases monster's cookie count by random num.
        cookies -= ThreadLocalRandom.current().nextInt(0, 2 + 1);
        
    }

    public int cookieCount() { // checks monster's cookie count
        return cookies;
    }

    public void increase(int food) {
        cookies += food;
    }

} // end of Monster's class