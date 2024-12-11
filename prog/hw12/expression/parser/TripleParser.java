package expression.parser;

@FunctionalInterface
public interface TripleParser {
    TripleExpression parse(String expression);
}
