package br.ufs.dcomp.ChatRabbitMQ.commands;

import br.ufs.dcomp.ChatRabbitMQ.groups.GroupNotFoundException;

import java.util.List;

public interface Command {
    void handle(List<String> arguments) throws GroupNotFoundException;
}
