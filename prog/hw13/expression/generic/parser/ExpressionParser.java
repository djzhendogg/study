package expression.generic.parser;

import expression.generic.expressions.*;
import expression.generic.operation_types.ArithmeticOperations;

public class ExpressionParser<T extends Number> extends BaseParser implements TripleParser<T> {
    private final ArithmeticOperations<T> operations;

    public ExpressionParser(ArithmeticOperations<T> operations) {
        this.operations = operations;
    }

    @Override
    public TripleExpression<T> parse(String expression) {
        source = new StringSource(expression);
        take();
        return parseAdditiveExpression();
    }

    private TripleExpression<T> parseAdditiveExpression() {
        TripleExpression<T> primaryLeft = parseMultiplicativeExpression();

        while (true) {
            skipWhitespace();
            if (take('+')) {
                primaryLeft = new Add<>(primaryLeft, parseMultiplicativeExpression(), operations);
            } else if (take('-')) {
                primaryLeft = new Subtract<>(primaryLeft, parseMultiplicativeExpression(), operations);
            } else {
                return primaryLeft;
            }
        }
    }

    private TripleExpression<T> parseMultiplicativeExpression() {
        TripleExpression<T> primaryLeft = parsePrimary();

        while (true) {
            skipWhitespace();
            if (take('*')) {
                primaryLeft = new Multiply<>(primaryLeft, parsePrimary(), operations);
            } else if (take('/')) {
                primaryLeft = new Divide<>(primaryLeft, parsePrimary(), operations);
            } else {
                return primaryLeft;
            }
        }
    }

    private TripleExpression<T> parsePrimary() {
        skipWhitespace();
        if (take('(')) {
            TripleExpression<T> res = parseAdditiveExpression();
            skipWhitespace();
            expect(')');
            return res;
        } else if (take('-')) {
            if (Character.isDigit(get())) {
                return parseConst(true);
            } else {
                return parseUnaryMinus();
            }
        } else {
            return parseAtom();
        }
    }

    private TripleExpression<T> parseUnaryMinus() {
        return new Negative<>(parsePrimary(), operations);
    }

    private TripleExpression<T> parseAtom() {
        skipWhitespace();
        if (Character.isDigit(get())) {
            return parseConst(false);
        } else if (Character.isLetter(get())) {
            return parseVariable();
        } else {
            throw source.error("Variable or constant value expected, '" + take() + "' found");
        }
    }

    private TripleExpression<T> parseVariable() {
        StringBuilder sb = new StringBuilder();
        while (Character.isLetterOrDigit(get())) {
            sb.append(take());
        }
        return new Variable<>(sb.toString());
    }

    private TripleExpression<T> parseConst(boolean isNegative) {
        StringBuilder sb = new StringBuilder();
        if (isNegative) {
            sb.append('-');
        }
        while (Character.isDigit(get())) {
            sb.append(take());
        }
        return new Const<>(operations.parse(sb.toString()));
    }
}
