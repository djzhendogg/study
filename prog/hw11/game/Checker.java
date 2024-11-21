package game;

import java.util.Arrays;

public class Checker {
    public static void main(String[] args) {
        int m = 5;
        int side = 2 * m - 1;
        int center = side / 2;
        int blockedOffset = m - 1;
        int emptyVals = 1;
        Cell[][] cells = new Cell[side][side];

        for (int i = 0; i < side/2; i++){
            int nowCol = 0;
            for (int j = 0; j < blockedOffset; j++) {
                cells[i][j] = Cell.B;
            }
            nowCol += blockedOffset;
            for (int nonBlocked = nowCol; nonBlocked < nowCol + emptyVals; nonBlocked++) {
                cells[i][nonBlocked] = Cell.E;
            }
            nowCol += emptyVals;
            for (int j = nowCol; j < nowCol + blockedOffset; j++) {
                cells[i][j] = Cell.B;
            }
            blockedOffset--;
            emptyVals += 2;
        }
        Arrays.fill(cells[center], Cell.E);
        blockedOffset++;
        emptyVals -= 2;
        for (int i = center + 1; i < side; i++){
            int nowCol = 0;
            for (int j = 0; j < blockedOffset; j++) {
                cells[i][j] = Cell.B;
            }
            nowCol += blockedOffset;
            for (int nonBlocked = nowCol; nonBlocked < nowCol + emptyVals; nonBlocked++) {
                cells[i][nonBlocked] = Cell.E;
            }
            nowCol += emptyVals;
            for (int j = nowCol; j < nowCol + blockedOffset; j++) {
                cells[i][j] = Cell.B;
            }
            blockedOffset++;
            emptyVals -= 2;
        }
    }

}
