package ua.duikt.holoshchuk.lr5;

public class RecordParseException extends Exception {

    public RecordParseException(String message) {
        super(message);
    }

    public RecordParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
