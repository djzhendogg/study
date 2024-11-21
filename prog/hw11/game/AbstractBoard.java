package game;

public abstract class AbstractBoard implements Board {

    public static boolean checkHorizontalWin(
            Move move, Cell[][] cells, Cell turn, int mCols, int kCellsToWin
    ) {
        int row = move.getRow();
        int col = move.getColumn();
        int inRow = 1;
        int offset = 1;

        while (0 <= col - offset && cells[row][col - offset] == turn) {
            inRow++;
            offset++;
        }

        offset = 1;
        while (mCols > col + offset && cells[row][col + offset] == turn) {
            inRow++;
            offset++;
        }

        return inRow >= kCellsToWin;
    }
    public static boolean checkVerticalWin(
            Move move, Cell[][] cells, Cell turn, int nRows, int kCellsToWin
    ) {
        int row = move.getRow();
        int col = move.getColumn();
        int inColumn = 1;
        int offset = 1;

        while (0 <= row - offset && cells[row - offset][col] == turn) {
            inColumn++;
            offset++;
        }

        offset = 1;
        while (nRows > row + offset && cells[row + offset][col] == turn) {
            inColumn++;
            offset++;
        }

        return inColumn >= kCellsToWin;
    }

    public boolean checkDiagonalWin(
            Move move, Cell[][] cells, Cell turn,int mCols, int nRows, int kCellsToWin
    ) {
        int row = move.getRow();
        int col = move.getColumn();
        int inDiag1 = 1;
        int inDiag2 = 1;
        int offset = 1;

        while (
                0 <= row - offset &&
                        0 <= col - offset &&
                        cells[row - offset][col - offset] == turn
        ) {
            inDiag1++;
            offset++;
        }

        offset = 1;
        while (
                nRows > row + offset &&
                        mCols > col + offset &&
                        cells[row + offset][col + offset] == turn
        ) {
            inDiag1++;
            offset++;
        }

        if (inDiag1 >= kCellsToWin) {
            return true;
        }

        offset = 1;
        while (
                0 <= row - offset &&
                        mCols > col + offset &&
                        cells[row - offset][col + offset] == turn
        ) {
            inDiag2++;
            offset++;
        }

        offset = 1;
        while (
                nRows > row + offset &&
                        0 <= col - offset &&
                        cells[row + offset][col - offset] == turn
        ) {
            inDiag2++;
            offset++;
        }

        return inDiag2 >= kCellsToWin;
    }
}
