package controller;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import exceptions.MyException;
import exceptions.NullReferenceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.adt.IHeap;
import model.states.ProgramState;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;
import repository.Repository;

public class Controller implements IController {
    private final IRepository repository;
    private ExecutorService executor = null;

    public Controller(ProgramState mainState) {
        repository = new Repository(mainState);
        executor = Executors.newFixedThreadPool(2);
    }

    public Controller(ProgramState mainState, String logFilePath) {
        repository = new Repository(mainState, logFilePath);
        executor = Executors.newFixedThreadPool(2);
    }

    @Override
    public void runToCompletion(boolean display) {
        while (!repository.getProgramThreads().isEmpty()) {
            executeOneStep();
        }

        executor.shutdownNow();
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return repository.getProgramThreads();
    }

    @Override
    public void executeOneStep() {
        List<ProgramState> programThreads = repository.getProgramThreads();

        executeGarbageCollector(programThreads);
        logAll(programThreads);

        List<Callable<ProgramState>> callList = programThreads.stream()
                .map((program) -> (Callable<ProgramState>) (() -> program.oneStep()))
                .collect(Collectors.toList());

        List<ProgramState> newProgramThreads;
        try {
            newProgramThreads = executor.invokeAll(callList).stream()
                    .map((future) -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter((programThread) -> programThread != null)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        programThreads.addAll(newProgramThreads);
        logAll(programThreads);

        repository.setProgramThreads(removeCompletedThreads(programThreads));
    }

    @Override
    public boolean executionHasCompleted() {
        return repository.isEmpty();
    }

    private Set<Integer> getUnusedAddresses(List<ProgramState> programThreads) {
        IHeap heap = programThreads.get(0).getHeap();
        Set<Integer> addressesNotInUse = heap.getAddresses();

        programThreads.forEach((programThread) -> {
            programThread.getSymbolsTable().getVariableNames().forEach((variable) -> {
                try {
                    IValue value = programThread.getSymbolsTable().getVariableValue(variable);
                    while (value instanceof RefValue) {
                        addressesNotInUse.remove(((RefValue) value).getAddress());
                        value = heap.getValueAt(((RefValue) value).getAddress());
                    }
                } catch (MyException err) {

                }
            });
        });

        return addressesNotInUse;
    }

    private void executeGarbageCollector(List<ProgramState> programThreads) {
        Set<Integer> unusedAddresses = getUnusedAddresses(programThreads);

        programThreads.forEach((programThread) -> unusedAddresses.forEach((address) -> {
            try {
                programThread.getHeap().deallocate(address);
            } catch (NullReferenceException e) {

            }
        }));
    }

    private ObservableList<ProgramState> removeCompletedThreads(List<ProgramState> programThreads) {
        return FXCollections.observableArrayList(
                programThreads.stream().filter((thread) -> !thread.isCompleted()).collect(Collectors.toList()));
    }

    private void logAll(List<ProgramState> programThreads) {
        programThreads.forEach((program) -> {
            try {
                repository.logProgramState(program);
            } catch (MyException e) {
                e.printStackTrace();
                return;
            }
        });
    }
}
