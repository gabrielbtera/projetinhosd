package br.ufs.dcomp.ChatRabbitMQ.commands;

import br.ufs.dcomp.ChatRabbitMQ.groups.Group;
import br.ufs.dcomp.ChatRabbitMQ.groups.GroupHandler;
import br.ufs.dcomp.ChatRabbitMQ.groups.GroupNotFoundException;

import java.util.List;

public class RemoveUserFromGroupCommand implements Command {
    @Override
    public void handle(List<String> arguments) throws GroupNotFoundException {
        String user = arguments.get(0);
        String groupName = arguments.get(1);

        GroupHandler groupHandler = GroupHandler.getInstance();
        Group group = groupHandler.getGroup(groupName);

        group.removeUser(user);
    }
}
