package expression.exceptions.expresion_exceptions;

public class UnsupportedMode extends RuntimeException {
    public UnsupportedMode(String message) {
        super(message);
    }

    public UnsupportedMode(String found, String availableModes) {
        super("Unsupported mode given: %s. Available modes: %s.".formatted(found, availableModes));
    }
}
