package br.ufs.dcomp.ChatRabbitMQ.commands;

import java.util.List;

public class CreateGroupCommand implements Command {
    @Override
    public void handle(List<String> arguments) {
        String groupName = arguments.get(0);
    }
}
