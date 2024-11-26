package game.board;

import game.utils.Cell;

import java.util.Arrays;

public class MnkRectangleBoard extends MnkAbstractBoard {
    public MnkRectangleBoard(int mCols, int nRows, int kCellsToWin) {
        super(mCols, nRows, kCellsToWin);
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
