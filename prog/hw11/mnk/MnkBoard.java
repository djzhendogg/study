package mnk;

import java.util.Arrays;

public class MnkBoard implements Board {
    private final int mCols;
    private final int nRows;
    private final int kCellsToWin;
    private final MnkPosition position;
    private final Cell[][] cells;
    private final boolean checkDiagonal;
    private final boolean checkHorizontal;
    private final boolean checkVertical;
    private int empty;
    private Cell turn;

    public MnkBoard(int m, int n, int k) {
        this.mCols = m;
        this.nRows = n;
        this.empty = m * n;
        this.kCellsToWin = k;
        this.checkDiagonal = kCellsToWin <= Math.min(m, n);
        this.checkHorizontal = kCellsToWin <= mCols;
        this.checkVertical = kCellsToWin <= nRows;

        this.cells = new Cell[nRows][mCols];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }

        this.turn = Cell.X;
        this.position = new MnkPosition(mCols, nRows, turn, cells);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!position.isValid(move)) {
            return Result.LOSE;
        }

        cells[move.getRow()][move.getColumn()] = move.getValue();
        empty--;

        if (checkHorizontal) {
            if (checkHorizontalWin(move)) {
                return Result.WIN;
            }
        }
        if (checkVertical) {
            if (checkVerticalWin(move)) {
                return Result.WIN;
            }
        }
        if (checkDiagonal) {
            if (checkDiagonalWin(move)) {
                return Result.WIN;
            }
        }

        if (empty == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        position.setCell(turn);
        return Result.UNKNOWN;
    }

    public boolean checkHorizontalWin(Move move) {
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

    public boolean checkVerticalWin(Move move) {
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

    public boolean checkDiagonalWin(Move move) {
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
