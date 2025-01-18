package model.adt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.values.IValue;

public class OutputList implements IOutputList {
    private final ObservableList<IValue> list;

    public OutputList() {
        list = FXCollections.observableArrayList();
    }

    @Override
    public void addValue(IValue item) {
        list.add(item);
    }

    @Override
    public ObservableList<IValue> getAll() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Output:\n");

        if (list.isEmpty()) {
            builder.append("    Empty\n");
            return builder.toString();
        }

        builder.append(String.join("\n", list.stream()
                .map((item) -> "    " + item)
                .toList()));

        return builder.toString() + '\n';
    }
}
