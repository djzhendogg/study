import java.io.IOException;
import java.util.Scanner;

public class Checker {
    public static void main(String[] args) throws IOException {
        MyScanner stringScanner = new MyScanner("  -0001 ");
        while (stringScanner.hasNextInt()) {
            System.out.println(stringScanner.nextInt());
        }
        stringScanner.close();
    }
}
