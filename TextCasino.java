import java.io.*;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

public class App {
  static int waitInt = 800;
  static double balance = 0;
  static BufferedReader indata = new BufferedReader (new InputStreamReader(System.in));

  public static String newline = System.getProperty("line.separator");
  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.print("Ready to play? (Y/N): ");
   String svar = indata.readLine();
   if (svar.equals("Y")) {
     System.out.println("Well fuck me, here we go...");
    
   Thread.sleep(400);
   System.out.print("Loading");
   Thread.sleep(400);
   System.out.print(".");
   Thread.sleep(400);
   System.out.print(".");
   Thread.sleep(400);
   System.out.println(".");
   Thread.sleep(400);
   welcomeTo();
   destination();
  
   } else {
     System.out.println("Probably for the better, you seem a bit short anyway.");
   }

 }
  public static void welcomeTo() throws IOException, InterruptedException {
   System.out.println(newline);
   System.out.println("/// Welcome to the Linaxe Casino! ///");
   System.out.println(newline);
   Thread.sleep(waitInt);
   System.out.println("Here are your 500 tokens! [+500 tokens]");
   balance = 500;
   Thread.sleep(waitInt);
  
 }
  public static void destination() throws IOException, InterruptedException {
   System.out.println(newline);
   System.out.println("/// Welcome to the lobby! ///");
   System.out.println("    Balance: " + balance);
   System.out.println(newline);
   System.out.println("Where to? (type destination)");
   System.out.println("[ Multiplier ] [ Coinflip ] [ Quit ]");
   Thread.sleep(waitInt);
   String destination = indata.readLine();
   int destInt = 0;
  
   if (destination.equals("Multiplier")) {
     destInt = 1;
   } else if (destination.equals("Coinflip")) {
     destInt = 2;
   } else if (destination.equals("Quit")) {
     Thread.sleep(waitInt);
     System.out.println("Thanks for playing!");
     Thread.sleep(waitInt);
     System.out.print("Shutting down");
     Thread.sleep(400);
     System.out.print(".");
     Thread.sleep(400);
     System.out.print(".");
     Thread.sleep(400);
     System.out.println(".");
     Thread.sleep(400);
    
     System.exit(0);
   }
  
   if ((destInt != 1) && (destInt != 2)) {
     Thread.sleep(waitInt);
     System.out.println("Invalid destination, try again!");
     destination();
   }
  
   if (destInt == 1) {
     multiplierLobby();
   }
  
   if (destInt == 2) {
     coinflipLobby();
   }
 } // End of destination method
  public static void multiplierLobby() throws IOException, InterruptedException {
   System.out.println(newline);
   System.out.println("/// Welcome to the multiplier table! ///");
   System.out.println("    Balance: " + balance);
   System.out.println(newline);
   Thread.sleep(waitInt);
  
   System.out.println("How it works: Enter amount of tokens to play with, then the machine will make up a random multiplier between 0 and 2. The machine will then multiply your tokens with said multiplier.");
   System.out.print("Press enter to continue...");
   indata.readLine();
   multiplierGame();
 }
  public static void multiplierGame() throws IOException, InterruptedException {
   System.out.print("Enter tokens: ");
   double tokens = Double.parseDouble(indata.readLine());
   if (tokens > balance) {
     Thread.sleep(waitInt);
     System.out.println("Invalid balance, try again!");
     Thread.sleep(waitInt);
     coinflipGame();
  
 }
 double multiplier = Math.random() * 2.1;
 double roundedMultiplier = Math.round(multiplier * 100.0) / 100.0;
  Thread.sleep(waitInt);
 System.out.print("Randomizing and multiplying");
 Thread.sleep(400);
 System.out.print(".");
 Thread.sleep(400);
 System.out.print(".");
 Thread.sleep(400);
 System.out.println(".");
 Thread.sleep(400);
  double newTokenValue = tokens * roundedMultiplier;
  double roundednewTokenValue = Math.round(newTokenValue * 100) / 100.0;
 balance -= tokens;
 balance += roundednewTokenValue;
 balance =  Math.round(balance * 100) / 100.0;

 System.out.println("Tokens entered: " + tokens + " // " + "Multiplier: " + roundedMultiplier + " // " + "Returned: " + roundednewTokenValue + " // " + "New balance: " + balance);
 Thread.sleep(waitInt);
 System.out.print("Again? (Y/N): ");
 String svar = indata.readLine();
  if (svar.equals("N")) {
   Thread.sleep(waitInt);
   System.out.println("Thanks for playing!");
   Thread.sleep(waitInt);
   System.out.println("Going back to the lobby...");
   Thread.sleep(waitInt);
   destination();
 }
  if (svar.equals("Y")) {
   multiplierGame();
 }
}
 public static void coinflipLobby() throws IOException, InterruptedException {
   System.out.println(newline);
   System.out.println("/// Welcome to the coinflip table! ///");
   System.out.println("    Balance: " + balance);
   System.out.println(newline);
   Thread.sleep(waitInt);
  
   System.out.println("How it works: Enter amount of tokens to play with and which side of the coin you want to bet on. The machine will flip a coin and if your side wins you double your tokens, otherwise, you lose.");
   System.out.print("Press enter to continue...");
   indata.readLine();
   coinflipGame();
 }
   public static void coinflipGame() throws IOException, InterruptedException {
  
   boolean heads;
   boolean tails;
   int sideUp = 0;
   String chosenSide = null;
  
   System.out.print("Enter tokens: ");
   double tokens = Double.parseDouble(indata.readLine());
  
   if (tokens > balance) {
     Thread.sleep(waitInt);
     System.out.println("Invalid balance, try again!");
     Thread.sleep(waitInt);
     coinflipGame();
   }
  
   System.out.print("Heads (1) or tails (2) ?: ");
   double side = Double.parseDouble(indata.readLine());
  
   if ((side != 1) && (side != 2)) {
     System.out.println("Invalid input, try again!");
     coinflipGame();
   } else if (side == 1) {
     chosenSide = "Heads";
   } else if (side == 2) {
     chosenSide = "Tails";
   }
  
   double coin = Math.random();
   System.out.println("Coin at: " + coin);
   Thread.sleep(waitInt);
   System.out.print("Flipping coin");
   Thread.sleep(400);
   System.out.print(".");
   Thread.sleep(400);
   System.out.print(".");
   Thread.sleep(400);
   System.out.println(".");
   Thread.sleep(400);
  
   if (coin > 0.49999) {
     sideUp = 1;
   } else {
     sideUp = 2;
   }
  
   String flippedCoin = null;
   if (sideUp == 1) {
     flippedCoin = "Heads";
   } else if (sideUp == 2) {
     flippedCoin = "Tails";
   }
  
   if (flippedCoin.equals(chosenSide)) { // Player wins
     balance += tokens;
     balance =  Math.round(balance * 100) / 100.0;
     System.out.println("Tokens entered: " + tokens + " // " + "Chosen side: " + chosenSide + " // " + "Flipped side: " + flippedCoin + " // " + "New balance: " + balance);
   } else if (flippedCoin != chosenSide) { // Player loses
     balance -= tokens;
     balance =  Math.round(balance * 100) / 100.0;
     System.out.println("Tokens entered: " + tokens + " // " + "Chosen side: " + chosenSide + " // " + "Flipped side: " + flippedCoin + " // " + "New balance: " + balance);
   }
   Thread.sleep(waitInt);
   System.out.print("Again? (Y/N): ");
   String svar = indata.readLine();
  
   if (svar.equals("N")) {
     Thread.sleep(waitInt);
     System.out.println("Thanks for playing!");
     Thread.sleep(waitInt);
     System.out.println("Going back to the lobby...");
     Thread.sleep(waitInt);
     destination();
   }
  
   if (svar.equals("Y")) {
     coinflipGame();
   }
 }
} // End of App class
