package mnk;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = setGameParams(scanner, "m", 1);
        int n = setGameParams(scanner, "n", 1);
        int k = setGameParams(scanner, "k", Math.max(m, n));
        System.out.printf("GAME START %nField is %d X %d%n+----------------------------+%n", m, n);

        final Game game = new Game(false, new SequentialPlayer(m, n), new HumanPlayer());
        int result;
        do {
            result = game.play(new MnkBoard(m, n, k));
        } while (result != 0);
    }

    public static int setGameParams(Scanner sc, String param, int option) {
        System.out.printf("Enter %s value:%n", param);
        int res;
        while (true) {
            try {
                int val = sc.nextInt();
                if (val <= 1) {
                    System.out.println("Wrong value. Value must be > 1. Repeat enter:");
                    sc.nextLine();
                    continue;
                }
                if (Objects.equals(param, "k") && val > option) {
                    System.out.printf("Wrong value. Value must be <= %d. Repeat enter:%n", option);
                    sc.nextLine();
                    continue;
                }
                res = val;
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Wrong value. Value must be number. Repeat enter:");
                sc.nextLine();
            }
        }
        return res;
    }
}
