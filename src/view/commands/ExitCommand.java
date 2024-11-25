package view.commands;

public class ExitCommand extends Command {
    public ExitCommand() {
        super("0", "Exit the program");
    }

    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
