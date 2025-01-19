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
    private String logFilePath = "build/logs/program_state.log";
    private List<ProgramState> programThreads;

    public Repository(ProgramState mainState) {
        programThreads = new ArrayList<>();
        programThreads.add(mainState);
    }

    public Repository(ProgramState mainThread, String fileName) {
        this(mainThread);
        logFilePath = fileName;
    }

    @Override
    public boolean isEmpty() {
        return programThreads.isEmpty();
    }

    @Override
    public List<ProgramState> getProgramThreads() {
        return new ArrayList<>(programThreads);
    }

    @Override
    public void setProgramThreads(List<ProgramState> programThreads) {
        this.programThreads = programThreads;
    }

    @Override
    public void logProgramState(ProgramState program) throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            logFile.write(program.toString());
        } catch (IOException e) {
            throw new FileException();
        }
    }
}
