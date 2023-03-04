package br.ufs.dcomp.ChatRabbitMQ.groups;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Group {
    public final String name;
    private final List<String> users;

    public Group(String name) {
        this.name = name;
        users = new LinkedList<>();
    }

    public void addUser(String user) {
        users.add(user);
    }

    public void removeUser(String user) {
        users.remove(user);
    }

    public List<String> getUsers() {
        return users;
    }
}
