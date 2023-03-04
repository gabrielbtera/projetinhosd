package br.ufs.dcomp.ChatRabbitMQ.commands;

import br.ufs.dcomp.ChatRabbitMQ.groups.GroupNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {
    public static final String COMMAND_PREFIX = "!";
    private Map<String, Command> commands = new HashMap<>();

    public CommandHandler() {
        registerCommands();
    }

    public void handle(String commandStr) throws InvalidCommandException, GroupNotFoundException {
        String parsedCommand = removePrefix(commandStr);
        List<String> tokens = List.of(parsedCommand.split(" "));

        String commandName = tokens.get(0);
        List<String> arguments = tokens.subList(1, tokens.size());

        Command command = commands.get(commandName);

        if (command == null) {
            throw new InvalidCommandException(commandName);
        }

        command.handle(arguments);
    }

    public static String formatCommandWithPrefix(String commandName) {
        return COMMAND_PREFIX + commandName;
    }

    public boolean isCommand(String input) {
        return input.startsWith(COMMAND_PREFIX);
    }

    private String removePrefix(String command) {
        int prefixLength = COMMAND_PREFIX.length();
        return command.substring(prefixLength);
    }

    private void registerCommands() {
        commands.put("newGroup", new CreateGroupCommand());
        commands.put("addUser", new AddUserToGroupCommand());
        commands.put("delFromGroup", new RemoveUserFromGroupCommand());
        commands.put("removeGroup", new DeleteGroupCommand());
    }
}
