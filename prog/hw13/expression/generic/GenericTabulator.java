package expression.generic;

import expression.exceptions.expresion_exceptions.UnsupportedModeException;
import expression.generic.expressions.TripleExpression;
import expression.generic.operation_types.BigIntOperations;
import expression.generic.operation_types.DoubleOperations;
import expression.generic.operation_types.IntOperations;
import expression.generic.operation_types.NumericOperations;
import expression.generic.parser.ExpressionParser;
import expression.generic.parser.TripleParser;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class GenericTabulator implements Tabulator {
    private static final Map<String, NumericOperations<?>> OPERATIONS_MAP = Map.of(
            "i", new IntOperations(),
            "d", new DoubleOperations(),
            "bi", new BigIntOperations()
    );
    private static final Set<String> AVAILABLE_MODES = OPERATIONS_MAP.keySet();

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        return makeTable(defineOperationType(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <E extends Number> Object[][][] makeTable(
            NumericOperations<E> operations,
            String expression,
            int x1,
            int x2,
            int y1,
            int y2,
            int z1,
            int z2
    ) {
        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        TripleParser<E> parser = new ExpressionParser<>(operations);
        TripleExpression<E> parsedExpression = parser.parse(expression);
        for (int i = 0; i <= x2 - x1; i++) {
            for (int j = 0; j <= y2 - y1; j++) {
                for (int k = 0; k <= z2 - z1; k++) {
                    try {
                        table[i][j][k] = parsedExpression.evaluate(
                                operations.parse(String.valueOf(i + x1)),
                                operations.parse(String.valueOf(j + y1)),
                                operations.parse(String.valueOf(k + z1))
                        );
                    } catch (Exception e) {
                        table[i][j][k] = null;
                    }
                }
            }
        }
        return table;
    }

    private NumericOperations<? extends Number> defineOperationType(String mode) {
        if (!AVAILABLE_MODES.contains(mode)) {
            throw new UnsupportedModeException(mode, Arrays.toString(AVAILABLE_MODES.toArray(new String[0])));
        }
        return OPERATIONS_MAP.get(mode);
    }
}
