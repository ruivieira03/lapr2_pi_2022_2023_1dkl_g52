package pt.ipp.isep.dei.esoft.project.util.error;

public class OperationResult {
    private final boolean success;
    private String errorMessage;

    private OperationResult(boolean success) {
        this.success = success;
    }

    private OperationResult(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean success() {
        return this.success;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public static OperationResult successfull() {
        return new OperationResult(true);
    }

    public static OperationResult failed(String errorMessage) {
        return new OperationResult(false, errorMessage);
    }
}
