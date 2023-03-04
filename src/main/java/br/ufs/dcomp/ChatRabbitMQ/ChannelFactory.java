package br.ufs.dcomp.ChatRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ChannelFactory {
    private final String HOST_IP = "18.207.112.41";
    private final String USERNAME = "seu login";
    private final String PASSWORD = "sua senha";

    public Channel createChannel() throws IOException, TimeoutException {
        var connectionFactory = new ConnectionFactory();

        connectionFactory.setHost(HOST_IP);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        connectionFactory.setVirtualHost("/");

        return connectionFactory.newConnection().createChannel();
    }
}
