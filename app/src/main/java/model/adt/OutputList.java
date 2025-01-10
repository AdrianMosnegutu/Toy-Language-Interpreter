package model.adt;

import java.util.ArrayList;
import java.util.List;

import model.values.IValue;

public class OutputList implements IOutputList {
    private final List<IValue> list;

    public OutputList() {
        list = new ArrayList<>();
    }

    @Override
    public void addValue(IValue item) {
        list.add(item);
    }

    @Override
    public List<IValue> getContent() {
        return new ArrayList<>(list);
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
