package game;

import java.util.Arrays;

public class MnkRhombusBoard extends AbstractBoard {
    private final int side;
    private final int kCellsToWin;
    private final MnkPosition position;
    private final Cell[][] cells;
    private final boolean checkDiagonal;
    private final boolean checkHorizontal;
    private final boolean checkVertical;
    private int empty;
    private Cell turn;

    public MnkRhombusBoard(int m, int k) {
        this.side = m * 2 - 1;
        this.empty = m * m + (m - 1) * (m - 1);
        this.kCellsToWin = k;
        this.checkDiagonal = kCellsToWin <= side;
        this.checkHorizontal = kCellsToWin <= side;
        this.checkVertical = kCellsToWin <= m;
        this.cells = createTable(side, m);

        this.turn = Cell.X;
        this.position = new MnkPosition(side, side, turn, cells);
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
            if (checkHorizontalWin(move, cells, turn, side, kCellsToWin)) {
                return Result.WIN;
            }
        }
        if (checkVertical) {
            if (checkVerticalWin(move, cells, turn, side, kCellsToWin)) {
                return Result.WIN;
            }
        }
        if (checkDiagonal) {
            if (checkDiagonalWin(move, cells, turn, side, side, kCellsToWin)) {
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


    public Cell[][] createTable(int side, int m) {
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
        return cells;
    }
}
