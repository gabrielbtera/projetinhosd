package br.ufs.dcomp.ChatRabbitMQ.commands;

import br.ufs.dcomp.ChatRabbitMQ.groups.Group;
import br.ufs.dcomp.ChatRabbitMQ.groups.GroupHandler;
import br.ufs.dcomp.ChatRabbitMQ.groups.GroupNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandHandlerTest {
    CommandHandler handler;
    GroupHandler groups;

    @BeforeEach
    public void setup() {
        groups = GroupHandler.getInstance();
        groups.clear();

        handler = new CommandHandler();
    }

    @Test
    public void shouldAddAnUserToAGroup() throws InvalidCommandException, GroupNotFoundException {
        Group group = new Group("silk_sonic");
        groups.addGroup(group);

        handler.handle("!addUser bruno silk_sonic");

        assertEquals("bruno", group.getUsers().get(0));
    }

    @Test
    public void shouldThrowAnException_ifTheUserTriesToAddAnUser_toANonExistentGroup() {
        assertThrows(GroupNotFoundException.class, () -> handler.handle("!addUser bruno silk_sonic"));
    }

    @Test
    public void shouldCreateAGroup() throws InvalidCommandException, GroupNotFoundException {
        handler.handle("!newGroup calcinha_preta");

        assertDoesNotThrow(() -> {
            // Se o grupo não existisse, isto lançaria uma GroupNotFoundException
            groups.getGroup("calcinha_preta");
        });
    }

    @Test
    public void shouldDeleteAGroup() throws InvalidCommandException, GroupNotFoundException {
        groups.addGroup(new Group("calcinha_preta"));

        handler.handle("!removeGroup calcinha_preta");

        assertThrows(GroupNotFoundException.class, () -> groups.getGroup("calcinha_preta"));
    }

    @Test
    public void shouldRemoveAnUserFromAGroup() throws InvalidCommandException, GroupNotFoundException {
        Group group = new Group("calcinha_preta");
        group.addUser("daniel_diau");
        group.addUser("paulinha");

        groups.addGroup(group);

        handler.handle("!delFromGroup daniel_diau calcinha_preta");

        assertEquals(1, group.getUsers().size());
        assertEquals("paulinha", group.getUsers().get(0));
    }

    @Test
    public void shouldThrowAnException_ifTheUserTriesToRemoveAnUser_fromANonExistentGroup() {
        assertThrows(GroupNotFoundException.class, () -> handler.handle("!delFromGroup daniel_diau calcinha_preta"));
    }
}