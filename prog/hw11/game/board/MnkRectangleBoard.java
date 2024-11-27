package game.board;

import game.position.Cell;

import java.util.Arrays;

public final class MnkRectangleBoard extends MnkAbstractBoard {
    public MnkRectangleBoard(int mCols, int nRows, int kCellsToWin) {
        super(mCols, nRows, kCellsToWin);
    }

    @Override
    protected int calculateEmptyCells(int mCols, int nRows) {
        return mCols * nRows;
    }

    @Override
    protected Cell[][] createCellsTable(int mCols, int nRows) {
        Cell[][] result = new Cell[nRows][mCols];
        for (Cell[] row : result) {
            Arrays.fill(row, Cell.E);
        }
        return result;
    }
}
