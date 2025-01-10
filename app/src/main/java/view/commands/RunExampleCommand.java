package view.commands;

import controller.IController;
import view.TextMenu;

public class RunExampleCommand extends Command {
    private final IController controller;
    private boolean exampleRan = false;

    public RunExampleCommand(String key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        if (exampleRan) {
            System.out.println("Cannot run example again");
            TextMenu.printSeparatorLine();
            return;
        }

        exampleRan = true;

        try {
            controller.allStep(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
