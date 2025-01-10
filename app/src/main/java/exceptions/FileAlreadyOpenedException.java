package exceptions;

import model.values.StringValue;

public class FileAlreadyOpenedException extends MyException {
    public FileAlreadyOpenedException() {
        super("File is already opened");
    }

    public FileAlreadyOpenedException(String message) {
        super(message);
    }

    public FileAlreadyOpenedException(StringValue filaName) {
        super(String.format("File '%s' is already open", filaName.getValue()));
    }
}
