package expression.exceptions.expresion_exceptions;

public class UnknownVariableException extends ExpressionParserException {
    public UnknownVariableException(String found) {
        super("\"" + found + "\" does not match the valid format of the variable: the variable must end with x or z or y");
    }
}
