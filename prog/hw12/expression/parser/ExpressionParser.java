package expression.parser;

import expression.*;

import java.util.Set;

public class ExpressionParser extends BaseParser implements TripleParser {
    private final Set<Character> possibleVariables = Set.of('x', 'y', 'z');

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
            return parseUnaryMinus();
        } else {
            return parseAtom();
        }
    }

    private VariableExpression parseUnaryMinus() {
        return new UnaryMinus(parseAtom());
    }

    private VariableExpression parseAtom() {
        skipWhitespace();
        if (Character.isDigit(get())) {
            return parseConst();
        } else if (Character.isLetter(get())) {
            return parseVariable();
        } else {
            throw source.error("Variable or constant expeсted '" + take() + "' found");
        }
    }

    private VariableExpression parseVariable() {
        if (possibleVariables.contains(get())) {
            return new Variable(Character.toString(take()));
        }
        return null;
    }

    private VariableExpression parseConst() {
        StringBuilder sb = new StringBuilder();
        while (Character.isDigit(get())) {
            sb.append(take());
        }
        return new Const(Integer.parseInt(sb.toString()));
    }
}
