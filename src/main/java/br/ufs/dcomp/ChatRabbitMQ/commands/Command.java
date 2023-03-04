package br.ufs.dcomp.ChatRabbitMQ.commands;

import java.util.List;

public interface Command {
    void handle(List<String> arguments);
}
