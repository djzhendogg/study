package expression;

import java.util.List;

/**
 * One-argument arithmetic expression over integers.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FunctionalInterface
@SuppressWarnings("ClassReferencesSubclass")
public interface Expression extends ToMiniString {
    int evaluate(int x);
}
