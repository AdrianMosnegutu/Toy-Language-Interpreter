package model.states;

import model.adt.ExecutionStack;
import model.adt.FileTable;
import model.adt.Heap;
import model.adt.IExecutionStack;
import model.adt.IFileTable;
import model.adt.IHeap;
import model.adt.IOutputList;
import model.adt.ISymbolsTable;
import model.adt.OutputList;
import model.adt.SymbolsTable;
import model.statements.IStatement;

public class ProgramState {
    private final IExecutionStack executionStack;
    private final ISymbolsTable symbolsTable;
    private final IOutputList output;
    private final IFileTable fileTable;
    private final IHeap heap;

    public ProgramState() {
        executionStack = new ExecutionStack();
        symbolsTable = new SymbolsTable();
        output = new OutputList();
        fileTable = new FileTable();
        heap = new Heap();
    }

    public ProgramState(IStatement originalProgram) {
        this();
        executionStack.push(originalProgram);
    }

    public IExecutionStack getExecutionStack() {
        return executionStack;
    }

    public ISymbolsTable getSymbolsTable() {
        return symbolsTable;
    }

    public IOutputList getOutput() {
        return output;
    }

    public IFileTable getFileTable() {
        return fileTable;
    }

    public IHeap getHeap() {
        return heap;
    }

    @Override
    public String toString() {
        final int LINE_LENGTH = 80;
        StringBuilder builder = new StringBuilder();

        builder.append(executionStack);
        builder.append(symbolsTable);
        builder.append(output);
        builder.append(fileTable);
        builder.append(heap);

        builder.append("\n\n");
        for (int i = 0; i < LINE_LENGTH; i++) {
            builder.append("=");
        }
        builder.append("\n\n");

        return builder.toString();
    }
}
