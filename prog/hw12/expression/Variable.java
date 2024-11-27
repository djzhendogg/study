package expression;

import java.util.Objects;

public class Variable implements Expression {
    private final String value;

    public Variable(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Variable variable = (Variable) obj;
        return value.equals(variable.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
