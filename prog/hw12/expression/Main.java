package expression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int x = in.nextInt();
//        VariableExpression exp = new Add(
//                new Subtract(
//                        new Multiply(
//                                new Variable("x"),
//                                new Variable("x")
//                        ),
//                        new Multiply(
//                                new Const(2),
//                                new Variable("x")
//                        )
//                ),
//                new Const(1)
//        );
//        int result = exp.evaluate(x);
//        System.out.println(result);

        int x = 5;
        VariableExpression exp = new Cbrt(new Const(-123));
        int result = exp.evaluate(x);
        System.out.println(exp);
        System.out.println(exp.toMiniString());
        System.out.println(result);
    }
}
