package br.ufs.dcomp.ChatRabbitMQ.commands;

public class InvalidCommandException extends Exception {
    String commandName;

    public InvalidCommandException(String commandName) {
        super(CommandHandler.formatCommandWithPrefix(commandName) + " não é um comando válido.");
        this.commandName = commandName;
    }
}
