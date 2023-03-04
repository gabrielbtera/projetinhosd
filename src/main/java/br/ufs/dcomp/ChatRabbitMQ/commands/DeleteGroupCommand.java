package br.ufs.dcomp.ChatRabbitMQ.commands;

import br.ufs.dcomp.ChatRabbitMQ.groups.GroupHandler;

import java.util.List;

public class DeleteGroupCommand implements Command {
    @Override
    public void handle(List<String> arguments) {
        String group = arguments.get(0);
        GroupHandler.getInstance().removeGroup(group);
    }
}
