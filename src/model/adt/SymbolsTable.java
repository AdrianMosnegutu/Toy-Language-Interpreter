package model.adt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import exceptions.UndefinedVariableException;
import model.values.IValue;

public class SymbolsTable implements ISymbolsTable {
    private final Map<String, IValue> map;

    public SymbolsTable() {
        map = new HashMap<>();
    }

    @Override
    public IValue getVariableValue(String variableName) throws UndefinedVariableException {
        if (!map.containsKey(variableName)) {
            throw new UndefinedVariableException(variableName);
        }
        return map.get(variableName);
    }

    @Override
    public void setVariableValue(String variableName, IValue value) {
        map.put(variableName, value);
    }

    @Override
    public Set<String> getVariableNames() {
        return new HashSet<>(map.keySet());
    }

    @Override
    public Map<String, IValue> getContent() {
        return new HashMap<>(map);
    }

    @Override
    public boolean isVariableDefined(String variableName) {
        return map.containsKey(variableName);
    }

    @Override
    public void deleteVariable(String variableName) throws UndefinedVariableException {
        if (!map.containsKey(variableName)) {
            throw new UndefinedVariableException(variableName);
        }
        map.remove(variableName);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Symbols Table:\n");

        if (map.isEmpty()) {
            builder.append("    Empty\n");
            return builder.toString();
        }

        builder.append(String.join("\n", map.entrySet().stream()
                .map((entry) -> "    " + entry.getKey() + " -> " + entry.getValue())
                .toList()));

        return builder.toString() + '\n';
    }
}