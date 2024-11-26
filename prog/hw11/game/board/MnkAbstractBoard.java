package game.board;

import game.*;
import game.position.MnkPosition;
import game.position.Position;
import game.utils.Cell;
import game.utils.Result;

public abstract class MnkAbstractBoard implements Board {
    private final int mCols;
    private final int nRows;
    private final int kCellsToWin;

    private final boolean checkDiagonal;
    private final boolean checkHorizontal;
    private final boolean checkVertical;

    private final MnkPosition position;
    private final Cell[][] cells;

    private int empty;
    private Cell turn;

    public MnkAbstractBoard(int mCols, int nRows, int kCellsToWin) {
        this.turn = Cell.X;
        this.mCols = mCols;
        this.nRows = nRows;
        this.kCellsToWin = kCellsToWin;
        this.checkDiagonal = kCellsToWin <= Math.min(mCols, nRows);
        this.checkHorizontal = kCellsToWin <= mCols;
        this.checkVertical = kCellsToWin <= nRows;
        this.cells = createCellsTable(mCols, nRows);
        this.position = new MnkPosition(mCols, nRows, turn, cells);
        this.empty = calculateEmptyCells(mCols, nRows);
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

    @Override
    public String toString() {
        return position.toString();
    }

    protected int calculateEmptyCells(int mCols, int nRows) {
        return mCols * nRows;
    }

    protected Cell[][] createCellsTable(int mCols, int nRows) {
        return new Cell[nRows][mCols];
    }

    protected boolean checkHorizontalWin(Move move) {
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

    protected boolean checkVerticalWin(Move move) {
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

    protected boolean checkDiagonalWin(Move move) {
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
