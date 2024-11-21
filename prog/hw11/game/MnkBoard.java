package game;

import java.util.Arrays;

public class MnkBoard extends AbstractBoard {
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
            if (checkHorizontalWin(move, cells, turn, mCols, kCellsToWin)) {
                return Result.WIN;
            }
        }
        if (checkVertical) {
            if (checkVerticalWin(move, cells, turn, nRows, kCellsToWin)) {
                return Result.WIN;
            }
        }
        if (checkDiagonal) {
            if (checkDiagonalWin(move, cells, turn, mCols, nRows, kCellsToWin)) {
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
}
