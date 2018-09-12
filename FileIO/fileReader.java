import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class fileReader {
    public static void main(String[] args) {
        File testFile = new File("testFile.txt");

        try {
            Scanner fileI = new Scanner(testFile);

            String name = fileI.nextLine();
            int age = fileI.nextInt();
    
            System.out.printf("Name: %s Age: %d\n", name, age);  
            fileI.close(); 
        } catch (FileNotFoundException fnf) {
            System.out.printf("ERROR: %s\n", fnf);
        }

    }
} // end of class "fileReader"