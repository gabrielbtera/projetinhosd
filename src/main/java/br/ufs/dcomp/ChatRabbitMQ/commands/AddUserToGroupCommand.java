package br.ufs.dcomp.ChatRabbitMQ.commands;

import java.util.List;

public class AddUserToGroupCommand implements Command {
    @Override
    public void handle(List<String> arguments) {
        String user = arguments.get(0);
        String group = arguments.get(1);
    }
}
