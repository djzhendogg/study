package expression.parser;

public class Test {
    public static void main(String[] args) {
        //        String before = "x/ -   -   - 1";
        String before = "-(0)";
        ExpressionParser parser = new ExpressionParser();
        VariableExpression result = parser.parse(before);
        System.out.println(result);
        System.out.println(result.toMiniString());
        System.out.println(result.evaluate(1, 2, 3));
    }
}
