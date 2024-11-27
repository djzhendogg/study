package game.settings;

import game.board.Board;
import game.board.MnkRectangleBoard;
import game.board.MnkRhombusBoard;
import game.players.HumanPlayer;
import game.players.Player;
import game.players.RandomPlayer;
import game.players.SequentialPlayer;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Settings {
    private final PrintStream out;
    private final Scanner in;
//    TODO: вообще они final - пообарачивать это все в классы
    private BoardMode boardMode;
    private Board board;
    private PlayerMode playerMode;
    private Player player1;
    private Player player2;

    private int m;
    private int n;
    private int k;

    public Settings(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;

        setBoardMode();
        setGameParameters();
        setGameMode();
        setGamePlayers();
        resetBoard();
    }

    public Settings() {
        this(System.out, new Scanner(System.in));
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    private void setBoardMode() {
        out.println("Choose A Board:\n1. classic.\n2. rhombus.");
        while (true) {
            try {
                int val = in.nextInt();
                if (val == 1) {
                    this.boardMode = BoardMode.CLASSIC;
                } else if (val == 2) {
                    this.boardMode = BoardMode.RHOMBUS;
                } else {
                    out.println("I don't understand. Repeat enter:\n1. classic.\n2. rhombus.");
                    in.nextLine();
                    continue;
                }
                break;
            } catch (InputMismatchException ex) {
                out.println("Wrong value. Value must be integer. Repeat enter:");
                in.nextLine();
            }
        }
    }

    private void setGameMode() {
        out.println("Game Settings:\n1. single play.\n2. coop play.\n3. robot play.");
        while (true) {
            try {
                int val = in.nextInt();
                if (val == 1) {
                    this.playerMode = PlayerMode.SINGLE;
                } else if (val == 2) {
                    this.playerMode = PlayerMode.COOP;
                } else if (val == 3) {
                    this.playerMode = PlayerMode.ROBOT;
                } else {
                    out.println("I don't understand. Repeat enter:\n1. classic.\n2. rhombus.");
                    in.nextLine();
                    continue;
                }
                break;
            } catch (InputMismatchException ex) {
                out.println("Wrong value. Value must be integer. Repeat enter:");
                in.nextLine();
            }
        }
    }

    private void setGamePlayers() {
        if (playerMode == PlayerMode.SINGLE) {
            this.player1 = new HumanPlayer();
            this.player2 = setPlayer();
        } else if (playerMode == PlayerMode.COOP) {
            this.player1 = new HumanPlayer();
            this.player2 = new HumanPlayer();
        } else {
            this.player1 = setPlayer();
            this.player2 = setPlayer();
        }
    }

    private void setGameParameters() {
        if (boardMode == BoardMode.CLASSIC) {
            setM();
            setN();
            setK(Math.max(m, n));
        } else {
            setM();
            m = m * 2 - 1;
            this.n = m;
            setK(m);
        }
    }

    public void resetBoard() {
        if (boardMode == BoardMode.CLASSIC) {
            board = new MnkRectangleBoard(m, n, k);
        } else {
            board = new MnkRhombusBoard(m, n, k);
        }
    }

    private Player setPlayer() {
        out.println("Choose Opponent:\n1. random player.\n2. sequential player.");
        Player res;
        while (true) {
            try {
                int val = in.nextInt();
                if (val == 1) {
                    res = new RandomPlayer(m, n);
                } else if (val == 2) {
                    res = new SequentialPlayer(m, n);
                } else {
                    out.println("I don't understand. Repeat enter:\n1. classic.\n2. rhombus.");
                    in.nextLine();
                    continue;
                }
                break;
            } catch (InputMismatchException ex) {
                out.println("Wrong value. Value must be integer. Repeat enter:");
                in.nextLine();
            }
        }
        return res;
    }

    private void setM() {
        this.m = setParam("m", 0);
    }

    private void setN() {
        this.n = setParam("n", 0);;
    }

    private void setK(int maxVal) {
        this.k = setParam("k", maxVal);;
    }

    private int setParam(String param, int mustBeLessThen) {
        out.printf("Enter %s value:%n", param);
        int res;
        while (true) {
            try {
                res = in.nextInt();
                if (res <= 1) {
                    out.println("Wrong value. Value must be > 1. Repeat enter:");
                    in.nextLine();
                    continue;
                }
                if (Objects.equals(param, "k") && res > mustBeLessThen) {
                    out.printf("Wrong value. Value must be <= %d. Repeat enter:%n", mustBeLessThen);
                    in.nextLine();
                    continue;
                }
                break;
            } catch (InputMismatchException ex) {
                out.println("Wrong value. Value must be number. Repeat enter:");
                in.nextLine();
            }
        }
        return res;
    }
}
