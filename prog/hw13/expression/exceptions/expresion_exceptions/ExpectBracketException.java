package expression.exceptions.expresion_exceptions;

public class ExpectBracketException extends ExpressionParserException {
    public ExpectBracketException(char found) {
        super("')' expected, '" + found + "' found");
    }
}
