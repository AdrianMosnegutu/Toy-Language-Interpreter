package model.adt;

import java.util.concurrent.atomic.AtomicBoolean;

import exceptions.EmptyStackException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.statements.CompoundStatement;
import model.statements.IStatement;

public class ExecutionStack implements IExecutionStack {
    private final ObservableList<IStatement> stack;

    public ExecutionStack() {
        stack = FXCollections.observableArrayList();
    }

    @Override
    public IStatement pop() throws EmptyStackException {
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.removeLast();
    }

    @Override
    public void push(IStatement item) {
        stack.addLast(item);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public ObservableList<IStatement> getAll() {
        return stack;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Execution Stack:\n");

        if (stack.isEmpty()) {
            builder.append("    Empty\n");
            return builder.toString();
        }

        AtomicBoolean isExecutableStackTop = new AtomicBoolean(true);
        builder.append(String.join("\n", stack.reversed().stream()
                .map((item) -> {
                    if ((item instanceof CompoundStatement) || !isExecutableStackTop.get()) {
                        return "    " + item;
                    }

                    isExecutableStackTop.set(false);
                    return " -> " + item;
                })
                .toList()));

        return builder.toString() + '\n';
    }
}
