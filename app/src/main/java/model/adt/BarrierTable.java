package model.adt;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import model.helpers.Pair;

public class BarrierTable implements IBarrierTable {
    private final ObservableMap<Integer, Pair<Integer, List<Integer>>> table;
    private static int freeLocation = 0;

    public BarrierTable() {
        table = FXCollections.observableHashMap();
    }

    @Override
    public Pair<Integer, List<Integer>> getBarrier(int location) {
        synchronized (this) {
            return table.get(location);
        }
    }

    @Override
    public int createBarrier(Pair<Integer, List<Integer>> barrier) {
        synchronized (this) {
            table.put(freeLocation, barrier);
            return freeLocation++;
        }
    }

    @Override
    public boolean barrierExists(int location) {
        synchronized (this) {
            return table.containsKey(location);
        }
    }

    @Override
    public ObservableMap<Integer, Pair<Integer, List<Integer>>> getAll() {
        synchronized (this) {
            return table;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Barrier Table:\n");

        if (table.isEmpty()) {
            builder.append("    Empty");
            return builder.toString();
        }

        builder.append(String.join("\n", table.entrySet().stream()
                .map((entry) -> "    " + entry.getKey() + " -> " + entry.getValue())
                .toList()));

        return builder.toString();
    }
}
