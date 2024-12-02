package controller;

import java.util.Set;
import java.util.function.Consumer;

import exceptions.MyException;
import exceptions.NullReferenceException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.statements.IStatement;
import model.states.ProgramState;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;
import repository.Repository;

public class Controller implements IController {
    private final IRepository repository;

    public Controller(ProgramState mainState) {
        repository = new Repository(mainState);
    }

    public Controller(ProgramState mainState, String logFilePath) {
        repository = new Repository(mainState, logFilePath);
    }

    private Set<Integer> getUnusedAddresses(ISymbolsTable symbolsTable, IHeap heap) {
        Set<Integer> addressesNotInUse = heap.getAddresses();

        Consumer<String> removeAddressesInUse = (variableName) -> {
            try {
                IValue value = symbolsTable.getVariableValue(variableName);
                while (value instanceof RefValue) {
                    addressesNotInUse.remove(((RefValue) value).getAddress());
                    value = heap.getValueAt(((RefValue) value).getAddress());
                }
            } catch (MyException err) {

            }
        };

        symbolsTable.getVariableNames().stream().forEach(removeAddressesInUse);
        return addressesNotInUse;
    }

    private void executeGarbageCollector(ProgramState program) {
        Consumer<Integer> deallocateAddress = (address) -> {
            try {
                program.getHeap().deallocate(address);
            } catch (NullReferenceException e) {
            }
        };

        getUnusedAddresses(program.getSymbolsTable(), program.getHeap()).stream().forEach(deallocateAddress);
    }

    @Override
    public ProgramState oneStep(ProgramState program) throws MyException {
        IStatement statement = program.getExecutionStack().pop();
        return statement.execute(program);
    }

    @Override
    public void allStep(boolean display) throws MyException {
        ProgramState program = repository.getCurrentProgram();

        if (display) {
            this.repository.logProgramState(false);
        }

        while (!program.getExecutionStack().isEmpty()) {
            this.oneStep(program);
            if (display) {
                this.repository.logProgramState(false);
            }

            executeGarbageCollector(program);
            if (display) {
                this.repository.logProgramState(true);
            }
        }
    }

    @Override
    public String getOutputLog() throws MyException {
        StringBuilder builder = new StringBuilder("Output log:\n");

        repository.getCurrentProgram().getOutput().getContent().stream()
                .forEach((value) -> builder.append(value + "\n"));

        return builder.toString();
    }
}
