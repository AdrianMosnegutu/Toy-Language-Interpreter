package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import exceptions.MyException;
import exceptions.NullReferenceException;
import model.adt.IHeap;
import model.states.ProgramState;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;
import repository.Repository;

public class Controller implements IController {
    private final IRepository repository;
    private ExecutorService executor = null;
    private List<ProgramState> programThreads;

    public Controller(ProgramState mainState) {
        repository = new Repository(mainState);
        executor = Executors.newFixedThreadPool(2);
        programThreads = removeCompletedThreads(repository.getProgramThreads());
    }

    public Controller(ProgramState mainState, String logFilePath) {
        repository = new Repository(mainState, logFilePath);
        executor = Executors.newFixedThreadPool(2);
        programThreads = removeCompletedThreads(repository.getProgramThreads());
    }

    @Override
    public void allStep(boolean display) {
        while (!programThreads.isEmpty()) {
            executeGarbageCollector(programThreads);
            oneStepAll(programThreads);
            programThreads = removeCompletedThreads(programThreads);
        }

        executor.shutdownNow();
        repository.setProgramThreads(programThreads);
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return repository.getProgramThreads();
    }

    @Override
    public void oneStepAllPrograms() {
        if (!programThreads.isEmpty()) {
            executeGarbageCollector(programThreads);
            oneStepAll(programThreads);
            programThreads = removeCompletedThreads(programThreads);
        }
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

    private List<ProgramState> removeCompletedThreads(List<ProgramState> programThreads) {
        return programThreads.stream().filter((thread) -> !thread.isCompleted()).collect(Collectors.toList());
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

    private void oneStepAll(List<ProgramState> programThreads) {
        logAll(programThreads);

        // Create a list of callables for each program thread
        List<Callable<ProgramState>> callList = programThreads.stream()
                .map((program) -> (Callable<ProgramState>) (() -> program.oneStep()))
                .collect(Collectors.toList());

        // Execute all callables and collect the results
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

        // Add the new program threads to the list
        programThreads.addAll(newProgramThreads);

        logAll(programThreads);

        // Save the current program threads
        repository.setProgramThreads(programThreads);
    }
}
