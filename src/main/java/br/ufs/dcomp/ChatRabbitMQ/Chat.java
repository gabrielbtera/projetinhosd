package br.ufs.dcomp.ChatRabbitMQ;


import br.ufs.dcomp.ChatRabbitMQ.commands.CommandHandler;
import com.rabbitmq.client.*;
import java.util.*;
import java.io.IOException;
import java.text.*;

public class Chat {
  public static String currentUser = "";
  private static Scanner scanner = new Scanner(System.in);

  public static void main(String[] argv) throws Exception {
    var channelFactory = new ChannelFactory();
    var channel = channelFactory.createChannel();

    String username = input("User: ");

    final String QUEUE_NAME = username;
    channel.queueDeclare(username, false,   false,     false,       null);

    Consumer consumer = createConsumer(channel);

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat horaFormat = new SimpleDateFormat("HH:mm");
    Date date = new Date();
    
    String data = dateFormat.format(date);
    String hora = horaFormat.format(date);

    String prompt = ">> ";
    String atual = "";
    String line;

    CommandHandler commandHandler = new CommandHandler();

    while (true) {
      channel.basicConsume(QUEUE_NAME, true, consumer);

      line = input(atual + prompt);

      while (line.charAt(0) == '@') {
        currentUser = line;
        
        do {
          line = input(atual + Chat.currentUser + prompt);

          if (commandHandler.isCommand(line)) {
            commandHandler.handle(line);
          }
          
          if (line.charAt(0) != '@' && line.charAt(0) != '!') {
            String message = formatMessage(line);
            channel.basicPublish("", getUsernameNoFormatting(), null,  message.getBytes("UTF-8"));
          }
        }
        while(line.charAt(0) != '@');
      }
    }
  }

  private static String formatMessage(String content) {
    Date now = new Date();

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat hourFormat = new SimpleDateFormat("HH:mm");

    String dateTimePrefix = "(" + dateFormat.format(now) + " às " + hourFormat.format(now) + ")";
    String username = getUsernameNoFormatting();

    return dateTimePrefix + " " + username + " diz: " + content;
  }

  private static String getUsernameNoFormatting() {
    return currentUser.substring(1);
  }

  private static String input(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }

  private static void print(String msg) {
    System.out.print(msg);
  }
  private static void println(String msg) {
    System.out.println(msg);
  }

  private static Consumer createConsumer(Channel channel) {
    return new DefaultConsumer(channel) {
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println("");
        System.out.println(message);
        System.out.print(Chat.currentUser + ">> ");
      }
    };
  }
}