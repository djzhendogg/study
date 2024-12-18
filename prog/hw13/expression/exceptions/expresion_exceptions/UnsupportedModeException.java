package expression.exceptions.expresion_exceptions;

public class UnsupportedModeException extends RuntimeException {
    public UnsupportedModeException(String message) {
        super(message);
    }

    public UnsupportedModeException(String found, String availableModes) {
        super("Unsupported mode given: %s. Available modes: %s.".formatted(found, availableModes));
    }
}
