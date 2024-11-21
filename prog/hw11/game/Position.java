package game;

public interface Position {
    boolean isValid(Move move);

    Cell getCell();
}
