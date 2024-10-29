import java.io.*;
import java.util.Random;

public class SimpleSort {
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
                        } else {
                            str.append(c);
                        }
                    }
                    readedCharNum = reader.read(buffer);
                }
                quickSort(array, 0, n - 1);
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
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        Random rand = new Random();
        int randId = rand.nextInt(right - left + 1) + left;
        int q = arr[randId];
        int i = left;
        int j = right;
        while (i <= j){
            while (arr[i] < q) {
                i++;
            }
            while (arr[j] > q) {
                j--;
            }
            if (i <= j) {
                int saveJ = arr[j];
                int saveI = arr[i];
                arr[i++] = saveJ;
                arr[j--] = saveI;
            }
        }
        quickSort(arr, left, j);
        quickSort(arr, j + 1, right);
    }
}
