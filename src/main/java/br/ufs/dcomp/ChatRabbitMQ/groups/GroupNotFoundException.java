package br.ufs.dcomp.ChatRabbitMQ.groups;

public class GroupNotFoundException extends Exception {
    public GroupNotFoundException(String groupName) {
        super("Não há grupo chamado " + groupName);
    }
}
