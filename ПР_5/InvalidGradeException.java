package ua.duikt.holoshchuk.pr5;

public class InvalidGradeException extends Exception {

    public InvalidGradeException(String message) {
        super(message);
    }

    public InvalidGradeException(String message, Throwable cause) {
        super(message, cause);
    }
}
