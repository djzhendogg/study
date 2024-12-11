package expression;

import java.util.List;

/**
 * Three-argument arithmetic expression over integers.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FunctionalInterface
@SuppressWarnings("ClassReferencesSubclass")
public interface TripleExpression extends ToMiniString {
    int evaluate(int x, int y, int z);

}
