package game.position;

import game.Move;
import game.utils.Cell;

public interface Position {
    boolean isValid(Move move);

    Cell getCell();
}
