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
    private String logFilePath = "program_state.log";
    private List<ProgramState> programThreads;

    public Repository(ProgramState mainThread) {
        this.programThreads = new ArrayList<>();
        this.programThreads.add(mainThread);
    }

    public Repository(ProgramState mainThread, String fileName) {
        this(mainThread);
        this.logFilePath = fileName;
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
