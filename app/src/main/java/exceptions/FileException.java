package exceptions;

import model.values.StringValue;

public class FileException extends MyException {
    public FileException() {
        super("Error while operating on a file");
    }

    public FileException(String message) {
        super(message);
    }

    public FileException(StringValue filename) {
        super(String.format("Error while operating on file '%s'", filename.getValue()));
    }
}
