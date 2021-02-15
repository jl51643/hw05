package hr.fer.oprpp1.hw05.shell;

/**
 * Thrown when there is internal issue with shell program
 */
public class ShellIOException extends RuntimeException {

    /**
     * Throws new Runtime exception when there is internal issue with shell program
     *
     * @param message the detail message
     */
    public ShellIOException(String message) {
        super(message);
    }
}
