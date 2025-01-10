package view.commands;

public abstract class Command {
    private final String key;
    private final String description;

    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    /**
     * Executes the command. This method should be implemented by subclasses to
     * define the specific behavior of the command when it is executed.
     */
    public abstract void execute();

    /**
     * Retrieves the key associated with this command.
     *
     * @return the key as a String
     */
    public String getKey() {
        return key;
    }

    /**
     * Retrieves the description of the command.
     *
     * @return the description of the command as a String.
     */
    public String getDescription() {
        return description;
    }
}
