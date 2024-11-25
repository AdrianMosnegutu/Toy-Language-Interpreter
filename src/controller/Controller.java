package controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import exceptions.MyException;
import model.statements.IStatement;
import model.states.ProgramState;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;
import repository.Repository;

public class Controller implements IController {
    private final IRepository repository;

    public Controller(ProgramState initialState) {
        repository = new Repository(initialState);
    }

    public Controller(ProgramState initialState, String logFilePath) {
        repository = new Repository(initialState, logFilePath);
    }

    private Set<Integer> getAddressesInUse(Collection<IValue> symbolsTableValues, Map<Integer, IValue> heapContent) {
        Set<Integer> addressesInUse = new HashSet<>();

        symbolsTableValues.stream().forEach(value -> {
            while (value instanceof RefValue) {
                addressesInUse.add(((RefValue) value).getAddress());
                value = heapContent.get(((RefValue) value).getAddress());
            }
        });

        return addressesInUse;
    }

    private Map<Integer, IValue> garbageFreeHeap(Set<Integer> addressesInUse, Map<Integer, IValue> heapContent) {
        return heapContent.entrySet().stream()
                .filter(entry -> addressesInUse.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void executeGarbageCollector(ProgramState program) {
        Map<String, IValue> symbolsTableContent = program.getSymbolsTable().getContent();
        Map<Integer, IValue> heapContent = program.getHeap().getContent();

        Set<Integer> addressesInUse = getAddressesInUse(symbolsTableContent.values(), heapContent);
        program.getHeap().setContent(garbageFreeHeap(addressesInUse, heapContent));
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
        repository.getCurrentProgram().getOutput().getContent().stream().forEach(value -> builder.append(value + "\n"));
        return builder.toString();
    }
}
