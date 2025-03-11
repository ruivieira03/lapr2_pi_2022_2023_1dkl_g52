package pt.ipp.isep.dei.esoft.project.util.error;

import java.util.Optional;


public class ErrorOptional<T> {
    private final Optional<T> optional;
    private String errorMessage;

    private ErrorOptional() {
        optional = Optional.empty();
    }

    private ErrorOptional(T value) {
        optional = Optional.ofNullable(value);
    }

    public static <T> ErrorOptional<T> empty(String errorMessage) {
        ErrorOptional<T> errorOptional = new ErrorOptional<>();
        errorOptional.errorMessage = errorMessage;
        return errorOptional;
    }

    public static <T> ErrorOptional<T> of(T value) {
        ErrorOptional<T> errorOptional = new ErrorOptional<>(value);
        if (!errorOptional.optional.isPresent()) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        return errorOptional;
    }

    public boolean hasValue() {
        return optional.isPresent();
    }

    public T get() {
        return optional.orElse(null);
    }

    public boolean hasError() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}


