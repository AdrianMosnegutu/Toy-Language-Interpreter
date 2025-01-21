package model.adt;

import java.util.List;

import javafx.collections.ObservableMap;
import model.helpers.Pair;

public interface IBarrierTable {
    Pair<Integer, List<Integer>> getBarrier(int location);

    int createBarrier(Pair<Integer, List<Integer>> barrier);

    boolean barrierExists(int location);

    ObservableMap<Integer, Pair<Integer, List<Integer>>> getAll();
}
