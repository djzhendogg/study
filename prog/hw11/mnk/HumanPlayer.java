package mnk;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position) {
        out.println("Board");
        out.println(position);
        out.println(position.getCell() + "'s move");
        out.println("Enter row and column");
        Move move;
        while (true) {
            try {
                int row = in.nextInt() - 1;
                int col = in.nextInt() - 1;
                move = new Move(row, col, position.getCell());
                if (position.isValid(move)) {
                    break;
                } else {
                    System.out.println("Move " + move + " is invalid. Repeat enter.");
                    in.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Row and column must be integers. Repeat enter.");
                in.nextLine();
            }
        }
        return move;
    }
}