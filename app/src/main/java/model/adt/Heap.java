package model.adt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import exceptions.NullReferenceException;
import model.values.IValue;

public class Heap implements IHeap {
    private Map<Integer, IValue> map;
    private int currentFreeAddress = 1;

    public Heap() {
        map = new HashMap<>();
    }

    @Override
    public IValue getValueAt(int address) throws NullReferenceException {
        if (!map.containsKey(address)) {
            throw new NullReferenceException(address);
        }
        return map.get(address);
    }

    @Override
    public void setValueAt(int address, IValue newValue) throws NullReferenceException {
        if (!map.containsKey(address)) {
            throw new NullReferenceException(address);
        }
        map.put(address, newValue);
    }

    @Override
    public Set<Integer> getAddresses() {
        return new HashSet<>(map.keySet());
    }

    @Override
    public int allocate(IValue value) {
        while (map.containsKey(currentFreeAddress)) {
            currentFreeAddress++;
        }

        map.put(currentFreeAddress, value);
        return currentFreeAddress++;
    }

    @Override
    public void deallocate(int address) throws NullReferenceException {
        if (!map.containsKey(address)) {
            throw new NullReferenceException(address);
        }

        currentFreeAddress = address;
        map.remove(address);
    }

    @Override
    public Map<Integer, IValue> getAll() {
        return new HashMap<>(map);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Heap:\n");

        if (map.isEmpty()) {
            builder.append("    Empty");
            return builder.toString();
        }

        builder.append(String.join("\n", map.entrySet().stream()
                .map((entry) -> "    " + entry.getKey() + " -> " + entry.getValue())
                .toList()));

        return builder.toString();
    }
}
