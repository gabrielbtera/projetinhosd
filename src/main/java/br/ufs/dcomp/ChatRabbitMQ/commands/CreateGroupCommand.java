package br.ufs.dcomp.ChatRabbitMQ.commands;

import br.ufs.dcomp.ChatRabbitMQ.groups.Group;
import br.ufs.dcomp.ChatRabbitMQ.groups.GroupHandler;

import java.util.List;

public class CreateGroupCommand implements Command {
    @Override
    public void handle(List<String> arguments) {
        String groupName = arguments.get(0);

        Group group = new Group(groupName);
        GroupHandler.getInstance().addGroup(group);
    }
}
