import controller.Controller;
import controller.IController;
import model.statements.IStatement;
import model.states.ProgramExamples;
import model.states.ProgramState;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;

public class Interpreter {
    private final static String LOG_DIR = "logs/";

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();

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

        menu.show();
    }

    private static RunExampleCommand runPrintValueExample() {
        IStatement statement = ProgramExamples.printValueExample();
        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "printValue.log");

        return new RunExampleCommand("1", "Print a value", controller);
    }

    private static RunExampleCommand runArithmeticOperationsExample() {
        IStatement statement = ProgramExamples.arithmeticOperationsExample();
        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "arithmeticOperations.log");

        return new RunExampleCommand("2", "Perform arithmetic operations", controller);
    }

    private static RunExampleCommand runIfStatementExample() {
        IStatement statement = ProgramExamples.ifStatementExample();
        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "ifStatement.log");

        return new RunExampleCommand("3", "Use the if statement", controller);
    }

    private static RunExampleCommand runReadFromFileExample() {
        IStatement statement = ProgramExamples.readFromFileExample();
        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "readFromFile.log");

        return new RunExampleCommand("4", "Read data from a file", controller);
    }

    private static RunExampleCommand runAllocateToHeapExample() {
        IStatement statement = ProgramExamples.allocateToHeapExample();
        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "allocateToHeap.log");

        return new RunExampleCommand("5", "Allocate memory to the heap", controller);
    }

    private static RunExampleCommand runReadFromHeapExample() {
        IStatement statement = ProgramExamples.readFromHeapExample();
        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "readFromHeap.log");

        return new RunExampleCommand("6", "Read data from the heap", controller);
    }

    private static RunExampleCommand runWriteToHeapExample() {
        IStatement statement = ProgramExamples.writeToHeapExample();
        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "writeToHeap.log");

        return new RunExampleCommand("7", "Write data to the heap", controller);
    }

    private static RunExampleCommand runGarbageCollectorExample() {
        IStatement statement = ProgramExamples.garbageCollectorExample();
        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "garbageCollector.log");

        return new RunExampleCommand("8", "See the garbage collector in action", controller);
    }

    private static RunExampleCommand runWhileStatementExample() {
        IStatement statement = ProgramExamples.whileStatementExample();
        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "whileStatement.log");

        return new RunExampleCommand("9", "Use the while statement", controller);
    }

    private static RunExampleCommand runForkStatementExample() {
        IStatement statement = ProgramExamples.forkStatementExample();
        ProgramState program = new ProgramState(statement);
        IController controller = new Controller(program, LOG_DIR + "forkStatement.log");

        return new RunExampleCommand("10", "Use the fork statement", controller);
    }
}
