package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import exceptions.FileException;
import exceptions.MyException;
import model.states.ProgramState;

public class Repository implements IRepository {
    private int threadIndex = 0;
    private String logFilePath = "program_state.log";
    private final List<ProgramState> programThreads;

    public Repository(ProgramState initialState) {
        this.programThreads = new ArrayList<>();
        this.programThreads.add(initialState);
    }

    public Repository(ProgramState initialState, String fileName) {
        this(initialState);
        this.logFilePath = fileName;
    }

    @Override
    public ProgramState getCurrentProgram() throws MyException {
        return programThreads.get(threadIndex);
    }

    @Override
    public void logProgramState(boolean executedGarbageCollector) throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            if (executedGarbageCollector) {
                logFile.write("After executing the garbage collector:\n\n");
            }
            logFile.write(this.getCurrentProgram().toString());
        } catch (IOException e) {
            throw new FileException();
        }
    }
}
