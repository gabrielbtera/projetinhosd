package br.ufs.dcomp.ChatRabbitMQ.groups;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupTest {
    @Test
    public void shouldAddAnUserToAGroup() {
        Group group = new Group("4amigos");

        group.addUser("cesar");

        List<String> users = group.getUsers();

        assertEquals("cesar", users.get(0));
    }

    @Test
    public void shouldRemoveAnUserFromTheGroup() {
        Group group = new Group("avengers");

        group.addUser("tony");
        group.addUser("steve");

        group.removeUser("tony");

        List<String> users = group.getUsers();

        assertEquals(1, users.size());
        assertEquals("steve", users.get(0));
    }
}