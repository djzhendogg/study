import java.util.Arrays;
import java.util.Scanner;

public class ReverseOdd {
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] listLine = new int[1][];
        int pointerLine = 0;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            int[] listInt = new int[1];
            int pointerInt = 0;
            Scanner stringScanner = new Scanner(line);
            while (stringScanner.hasNextInt()) {
                if (pointerInt >= listInt.length) {
                    listInt = Arrays.copyOf(listInt, listInt.length * 2);
                }
				int number = stringScanner.nextInt();
				if (number % 2 != 0) {
					listInt[pointerInt++] = number;
				}
            }
            listInt = Arrays.copyOf(listInt, pointerInt);
            if (pointerLine >= listLine.length) {
                listLine = Arrays.copyOf(listLine, listLine.length * 2);
            }
            listLine[pointerLine++] = listInt;
        }
        listLine = Arrays.copyOf(listLine, pointerLine);
        for (int i = listLine.length - 1; i >= 0; i--) {
            if (listLine[i].length > 0) {
                for (int j = listLine[i].length - 1; j >= 0; j--) {
                    System.out.print(listLine[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
