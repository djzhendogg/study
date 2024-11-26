package game;

import game.board.Board;
import game.players.Player;
import game.utils.Result;

public class Game {
    private final boolean log;
    private final Player player1;
    private final Player player2;

    public Game(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board) {
        while (true) {
            final int result1 = move(board, player1, 1);
            if (result1 != -1) {
                return result1;
            }
            final int result2 = move(board, player2, 2);
            if (result2 != -1) {
                return result2;
            }
        }
    }

    private int move(final Board board, final Player player, final int no) {
        final Move move = player.move(board.getPosition());
        if (move == null) {
            System.out.println("Player " + no + " lose");
            return 3 - no;
        }
        final Result result = board.makeMove(move);
        if (log) {
            System.out.println("Player " + no + " move: " + move);
        }
        if (result == Result.WIN) {
            System.out.println("Player " + no + " won");
            return no;
        } else if (result == Result.LOSE) {
            System.out.println("Player " + no + " lose");
            return 3 - no;
        } else if (result == Result.DRAW) {
            System.out.println("Draw");
            return 0;
        } else {
            return -1;
        }
    }
}
