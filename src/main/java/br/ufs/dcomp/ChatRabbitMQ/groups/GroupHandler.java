package br.ufs.dcomp.ChatRabbitMQ.groups;

import java.util.HashMap;
import java.util.Map;

public class GroupHandler {
    private static GroupHandler instance;

    private Map<String, Group> groups;
    private GroupHandler() {
        groups = new HashMap<>();
    }

    public static GroupHandler getInstance() {
        if (instance == null) {
            instance = new GroupHandler();
        }

        return instance;
    }

    public void clear() {
        groups.clear();
    }

    public void addGroup(Group group) {
        groups.put(group.name, group);
    }

    public void removeGroup(String name) {
        groups.remove(name);
    }

    public Group getGroup(String name) throws GroupNotFoundException {
        Group group = groups.get(name);

        if (group == null) {
            throw new GroupNotFoundException(name);
        }

        return group;
    }

}
