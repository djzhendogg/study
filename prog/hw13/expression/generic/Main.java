package expression.generic;

import expression.exceptions.expresion_exceptions.CommandLineArgumentsException;
import expression.exceptions.expresion_exceptions.UnsupportedMode;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new CommandLineArgumentsException("The required arguments are missing. " +
                    "Command line argument pattern: -[mode] [expression].");
        }
        if (!args[0].startsWith("-")) {
            throw new UnsupportedMode("The type in which the calculations will be performed must begin with '-'.");
        }
        try {
            GenericTabulator tabulator = new GenericTabulator();
            Object[][][] table = tabulator.tabulate(
                    args[0].substring(1),
                    args[1],
                    -2,
                    2,
                    -2,
                    2,
                    -2,
                    2
            );
            printTable(table);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printTable(Object[][][] table) {
        System.out.print("[");
        for (Object[][] forX : table) {
            System.out.print("[");
            for (Object[] forY : forX) {
                System.out.print("[");
                for (Object forZ : forY) {
                    System.out.print(forZ + ", ");
                }
                System.out.print("],");
                System.out.println();
            }
            System.out.print("]");
        }
        System.out.print("]");
    }
}
