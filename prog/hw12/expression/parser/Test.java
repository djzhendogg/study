package expression.parser;

import expression.VariableExpression;

public class Test {
    public static void main(String[] args) {
        //        String before = "x/ -   -   - 1";
        String before = "x * (x - 2)*x + 1";
        ExpressionParser parser = new ExpressionParser();
        VariableExpression result = parser.parse(before);
        System.out.println(result);
        System.out.println(result.toMiniString());
    }
}
