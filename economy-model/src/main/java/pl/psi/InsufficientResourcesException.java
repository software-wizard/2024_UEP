package pl.psi;

public class InsufficientResourcesException extends Exception {

    private final String message;

    public InsufficientResourcesException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}