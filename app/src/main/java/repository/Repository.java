package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import exceptions.FileException;
import exceptions.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.states.ProgramState;

public class Repository implements IRepository {
    private String logFilePath = "program_state.log";
    private ObservableList<ProgramState> programThreads;

    public Repository(ProgramState mainState) {
        programThreads = FXCollections.observableArrayList();
        programThreads.add(mainState);
    }

    public Repository(ProgramState mainThread, String fileName) {
        this(mainThread);
        logFilePath = fileName;
    }

    @Override
    public ObservableList<ProgramState> getProgramThreads() {
        return programThreads;
    }

    @Override
    public void setProgramThreads(ObservableList<ProgramState> programThreads) {
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
