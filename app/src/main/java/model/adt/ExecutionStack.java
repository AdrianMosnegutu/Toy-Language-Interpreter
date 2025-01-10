package model.adt;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

import exceptions.EmptyStackException;
import model.statements.CompoundStatement;
import model.statements.IStatement;

public class ExecutionStack implements IExecutionStack {
    private final Stack<IStatement> stack;

    public ExecutionStack() {
        stack = new Stack<>();
    }

    @Override
    public IStatement pop() throws EmptyStackException {
        if (stack.empty()) {
            throw new EmptyStackException();
        }
        return stack.pop();
    }

    @Override
    public void push(IStatement item) {
        stack.push(item);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
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
