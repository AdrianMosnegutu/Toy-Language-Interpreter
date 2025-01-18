package model.adt;

import java.io.BufferedReader;
import java.io.IOException;

import exceptions.UndefinedFileException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import model.values.StringValue;

public class FileTable implements IFileTable {
    private final ObservableMap<StringValue, BufferedReader> map;

    public FileTable() {
        map = FXCollections.observableHashMap(); 
    }

    @Override
    public BufferedReader getFile(StringValue fileName) throws UndefinedFileException {
        if (!map.containsKey(fileName)) {
            throw new UndefinedFileException(fileName.getValue());
        }
        return map.get(fileName);
    }

    @Override
    public void setFile(StringValue fileName, BufferedReader file) {
        map.put(fileName, file);
    }

    @Override
    public boolean isOpen(StringValue fileName) {
        return map.containsKey(fileName);
    }

    @Override
    public void closeFile(StringValue fileName) throws UndefinedFileException {
        if (!isOpen(fileName)) {
            throw new UndefinedFileException(fileName.getValue());
        }

        try {
            map.get(fileName).close();
        } catch (IOException e) {
            throw new UndefinedFileException(fileName.getValue());
        }

        map.remove(fileName);
    }

    @Override
    public ObservableMap<StringValue, BufferedReader> getAll() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("File Table:\n");

        if (map.isEmpty()) {
            builder.append("    Empty\n");
            return builder.toString();
        }

        builder.append(String.join("\n", map.keySet().stream()
                .map((key) -> "    " + key)
                .toList()));

        return builder.toString() + '\n';
    }
}
