package model.adt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import exceptions.UndefinedFileException;
import model.values.StringValue;

public class FileTable implements IFileTable {
    private final Map<StringValue, BufferedReader> map;

    public FileTable() {
        map = new HashMap<>();
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
