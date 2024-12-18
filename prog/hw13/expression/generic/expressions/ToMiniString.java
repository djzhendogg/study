package expression.generic.expressions;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ToMiniString {
    default String toMiniString() {
        return toString();
    }
}
