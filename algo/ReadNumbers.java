import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadNumbers {
    public static void main(String[] args) {
        final int BUFFER_SIZE = 2048;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                int n = Integer.parseInt(reader.readLine());
                char[] buffer = new char[BUFFER_SIZE];
                int[] array = new int[n];
                int pointer = 0;
                int readedCharNum = reader.read(buffer);
                StringBuilder str = new StringBuilder();
                while (readedCharNum >= 0) {
                    for (char c : buffer) {
                        if (Character.isWhitespace(c) && !str.isEmpty()) {
                            array[pointer++] = Integer.parseInt(str.toString());
                            str.setLength(0);
                        } else if (!(c == 0)) {
                            str.append(c);
                        }
                    }
                    readedCharNum = reader.read(buffer);
                }
                if (!str.isEmpty() && pointer < n) {
                    array[pointer] = Integer.parseInt(str.toString());
                }
                for (int i : array) {
                    System.out.print(i + " ");
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
