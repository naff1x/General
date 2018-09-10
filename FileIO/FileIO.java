import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class FileIO {
    public static void main(String[] args) {
        File testFile = new File("testFile.txt");
        
        // Writing name and age to a file
        try {
            PrintWriter fileO = new PrintWriter(testFile);
            fileO.println("Linus");
            fileO.println("16");
            fileO.close();   
        } catch (IOException ioe) {
            System.out.printf("ERROR: %s\n", ioe);
            //System.out.println("ERROR: %s\n", ioe);
        }
        
        try {
            Scanner fileI = new Scanner(testFile);

            String name = fileI.nextLine();
            int age = fileI.nextInt();
    
            System.out.printf("Name: %s Age: %d\n", name, age);   
        } catch (FileNotFoundException fnf) {
            System.out.printf("ERROR: %s\n", fnf);
        }
    }
} // end of class "FileIO"