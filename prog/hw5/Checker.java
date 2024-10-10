import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;

public class Checker {
    public static void main(String[] args) throws IOException {
        try {
            throw new UnsupportedEncodingException();
        } catch (UnsupportedEncodingException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }
}
