import java.util.HashMap;

import controller.Controller;
import controller.IController;
import exceptions.MyException;
import model.statements.IStatement;
import model.states.ProgramExamples;
import model.states.ProgramState;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;

public class Interpreter {
    private final static String LOG_DIR = "build/logs/";

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();

        try {
            menu.addCommand(new ExitCommand());
            menu.addCommand(runPrintValueExample());
            menu.addCommand(runArithmeticOperationsExample());
            menu.addCommand(runIfStatementExample());
            menu.addCommand(runReadFromFileExample());
            menu.addCommand(runAllocateToHeapExample());
            menu.addCommand(runReadFromHeapExample());
            menu.addCommand(runWriteToHeapExample());
            menu.addCommand(runGarbageCollectorExample());
            menu.addCommand(runWhileStatementExample());
            menu.addCommand(runForkStatementExample());
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        menu.show();
    }

    private static RunExampleCommand runPrintValueExample() throws MyException {
        IStatement statement = ProgramExamples.printValueExample();
        statement.typecheck(new HashMap<>());

        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "printValue.log");

        return new RunExampleCommand("1", "Print a value", controller);
    }

    private static RunExampleCommand runArithmeticOperationsExample() throws MyException {
        IStatement statement = ProgramExamples.arithmeticOperationsExample();
        statement.typecheck(new HashMap<>());

        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "arithmeticOperations.log");

        return new RunExampleCommand("2", "Perform arithmetic operations", controller);
    }

    private static RunExampleCommand runIfStatementExample() throws MyException {
        IStatement statement = ProgramExamples.ifStatementExample();
        statement.typecheck(new HashMap<>());

        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "ifStatement.log");

        return new RunExampleCommand("3", "Use the if statement", controller);
    }

    private static RunExampleCommand runReadFromFileExample() throws MyException {
        IStatement statement = ProgramExamples.readFromFileExample();
        statement.typecheck(new HashMap<>());

        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "readFromFile.log");

        return new RunExampleCommand("4", "Read data from a file", controller);
    }

    private static RunExampleCommand runAllocateToHeapExample() throws MyException {
        IStatement statement = ProgramExamples.allocateToHeapExample();
        statement.typecheck(new HashMap<>());

        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "allocateToHeap.log");

        return new RunExampleCommand("5", "Allocate memory to the heap", controller);
    }

    private static RunExampleCommand runReadFromHeapExample() throws MyException {
        IStatement statement = ProgramExamples.readFromHeapExample();
        statement.typecheck(new HashMap<>());

        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "readFromHeap.log");

        return new RunExampleCommand("6", "Read data from the heap", controller);
    }

    private static RunExampleCommand runWriteToHeapExample() throws MyException {
        IStatement statement = ProgramExamples.writeToHeapExample();
        statement.typecheck(new HashMap<>());

        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "writeToHeap.log");

        return new RunExampleCommand("7", "Write data to the heap", controller);
    }

    private static RunExampleCommand runGarbageCollectorExample() throws MyException {
        IStatement statement = ProgramExamples.garbageCollectorExample();
        statement.typecheck(new HashMap<>());

        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "garbageCollector.log");

        return new RunExampleCommand("8", "See the garbage collector in action", controller);
    }

    private static RunExampleCommand runWhileStatementExample() throws MyException {
        IStatement statement = ProgramExamples.whileStatementExample();
        statement.typecheck(new HashMap<>());

        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "whileStatement.log");

        return new RunExampleCommand("9", "Use the while statement", controller);
    }

    private static RunExampleCommand runForkStatementExample() throws MyException {
        IStatement statement = ProgramExamples.forkStatementExample();
        statement.typecheck(new HashMap<>());

        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "forkStatement.log");

        return new RunExampleCommand("10", "Use the fork statement", controller);
    }
}
