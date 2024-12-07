package model.states;

import exceptions.EmptyStackException;
import exceptions.MyException;
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

    private static int id = 0;
    private int programId;

    public ProgramState(IStatement initialProgram) {
        executionStack = new ExecutionStack();
        symbolsTable = new SymbolsTable();
        output = new OutputList();
        fileTable = new FileTable();
        heap = new Heap();
        executionStack.push(initialProgram);

        programId = getId();
    }

    public ProgramState(
            ISymbolsTable symbolsTable,
            IOutputList output,
            IFileTable fileTable,
            IHeap heap,
            IStatement initialProgram) {
        executionStack = new ExecutionStack();
        this.symbolsTable = symbolsTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        executionStack.push(initialProgram);

        programId = getId();
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

    public static synchronized int getId() {
        return ++id;
    }

    public Boolean isCompleted() {
        return executionStack.isEmpty();
    }

    public ProgramState oneStep() throws MyException {
        if (isCompleted()) {
            throw new EmptyStackException("The execution stack is empty");
        }

        return executionStack.pop().execute(this);
    }

    @Override
    public String toString() {
        final int LINE_LENGTH = 80;
        StringBuilder builder = new StringBuilder();

        builder.append("Program ID: " + programId + "\n\n");
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
