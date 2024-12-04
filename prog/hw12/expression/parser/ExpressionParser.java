package expression.parser;

import expression.*;

import java.util.Set;

public class ExpressionParser extends BaseParser implements TripleParser {
//    private final Set<Character> possibleVariables = Set.of('x', 'y', 'z');

    @Override
    public VariableExpression parse(String expression) {
        source = new StringSource(expression);
        take();
        return parseExpression();
    }

    private VariableExpression parseExpression() {
        VariableExpression primaryLeft = parseOperand();

        while (true) {
            skipWhitespace();
            if (take('+')) {
                primaryLeft = new Add(primaryLeft, parseOperand());
            } else if (take('-')) {
                primaryLeft = new Subtract(primaryLeft, parseOperand());
            } else {
                return primaryLeft;
            }
        }
    }

    private VariableExpression parseOperand() {
        VariableExpression primaryLeft = parsePrimary();

        while (true) {
            skipWhitespace();
            if (take('*')) {
                primaryLeft = new Multiply(primaryLeft, parsePrimary());
            } else if (take('/')) {
                primaryLeft = new Divide(primaryLeft, parsePrimary());
            } else {
                return primaryLeft;
            }
        }
    }

    private VariableExpression parsePrimary() {
        skipWhitespace();
        if (take('(')) {
            VariableExpression res = parseExpression();
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

    private VariableExpression parseUnaryMinus() {
        return new UnaryMinus(parsePrimary());
    }

    private VariableExpression parseAtom() {
        skipWhitespace();
        if (Character.isDigit(get())) {
            return parseConst(false);
        } else if (Character.isLetter(get())) {
            return parseVariable();
        } else {
            throw source.error("Variable or constant expected '" + take() + "' found");
        }
    }

    private VariableExpression parseVariable() {
        StringBuilder sb = new StringBuilder();
        while (Character.isLetterOrDigit(get())) {
            sb.append(take());
        }
        return new Variable(sb.toString());
    }

    private VariableExpression parseConst(boolean isNegative) {
        StringBuilder sb = new StringBuilder();
        if (isNegative) {
            sb.append('-');
        }
        while (Character.isDigit(get())) {
            sb.append(take());
        }
        return new Const(Integer.parseInt(sb.toString()));
    }
}
