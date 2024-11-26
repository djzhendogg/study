package game;

import java.util.Arrays;

public class Rhombus {
    public static int[][] createRhombus(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be a positive integer");
        }

        int size = 2 * n - 1;
        int[][] rhombus = new int[size][size];

        // Fill the rhombus with 1s
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(i - (n - 1)) + Math.abs(j - (n - 1)) < n) {
                    rhombus[i][j] = 1;
                } else {
                    rhombus[i][j] = 0;
                }
            }
        }
        return rhombus;
    }


    public static void printRhombus(int[][] rhombus) {
        for (int[] row : rhombus) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        int n = 3;
        int[][] rhombus = createRhombus(n);
        printRhombus(rhombus);


        System.out.println("\nRhombus with n=5:");
        n = 5;
        rhombus = createRhombus(n);
        printRhombus(rhombus);
    }
}

