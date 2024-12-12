package expression.exceptions.expresion_exceptions;

public class NegativeGeomVariable extends ExpressionEvaluateException {
    public NegativeGeomVariable(int found, String expression) {
        super(
                "Negative value of a variable for a geometric function: \"" +
                found +
                "\" in " +
                expression
        );
    }
}
