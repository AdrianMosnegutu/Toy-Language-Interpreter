package exceptions;

public class UndefinedFileException extends MyException {
    public UndefinedFileException() {
        super("File is not open");
    }

    public UndefinedFileException(String fileName) {
        super("File '" + fileName + "' is not open");
    }
}
