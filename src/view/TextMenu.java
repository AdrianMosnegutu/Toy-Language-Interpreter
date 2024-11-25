package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import view.commands.Command;
import view.commands.ExitCommand;

public class TextMenu {
    private static final int SEPARATOR_LINE_LENGTH = 75;
    private final Map<String, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    public static void printSeparatorLine() {
        for (int i = 0; i < SEPARATOR_LINE_LENGTH; ++i)
            System.out.print("=");
        System.out.println();
    }

    private void printMenu() {
        System.out.println("Choose which type of program you'd like to run:");
        commands.values().stream()
                .map((command) -> "  " + command.getKey() + " - " + command.getDescription())
                .forEach(System.out::println);
        TextMenu.printSeparatorLine();
    }

    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);

        TextMenu.printSeparatorLine();
        System.out.println("Welcome to the Toy Language Interpreter!");
        TextMenu.printSeparatorLine();

        while (true) {
            printMenu();

            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            TextMenu.printSeparatorLine();

            Command command = commands.get(key);

            if (command == null) {
                System.out.println("Invalid option");
                TextMenu.printSeparatorLine();
                continue;
            }

            if (command instanceof ExitCommand) {
                scanner.close();
            }

            command.execute();
        }
    }
}
