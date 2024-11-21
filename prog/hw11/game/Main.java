package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        final Game game = new Game(true, new RandomPlayer(), new HumanPlayer());
        int result;
        do {
            result = game.play(new TicTacToeBoard());
            System.out.println("Game result: " + result);
        } while (result != 0);
    }
}
