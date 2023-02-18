package br.ufs.dcomp.ChatRabbitMQ;

// import com.rabbitmq.client.*;

// import java.io.IOException;


import com.rabbitmq.client.*;
import java.util.*;
import java.io.IOException;
// import com.google.protobuf.ByteString;
import java.text.*;

public class Chat {
  
  public static String usuarioAtual= ""; 

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    String ipHost = "18.207.112.41";
    String login = "seu login";
    String key = "sua senha";
    
    factory.setHost(ipHost); // Alterar
    factory.setUsername(login); // Alterar
    factory.setPassword(key); // Alterar
    factory.setVirtualHost("/"); 
    
    
    

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    Scanner sc = new Scanner(System.in);

    System.out.print("User: ");
    String user = sc.nextLine();

    String QUEUE_NAME = user;
                      //(queue-name, durable, exclusive, auto-delete, params); 
    channel.queueDeclare(QUEUE_NAME, false,   false,     false,       null);

    Consumer consumer = new DefaultConsumer(channel) {
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println("");
        System.out.println(message);
        System.out.print(Chat.usuarioAtual + ">> ");
      }
    };

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat horaFormat = new SimpleDateFormat("HH:mm");
    Date date = new Date();
    
    String data = dateFormat.format(date);
    String hora = horaFormat.format(date);

    String atual = "";
    String prompt = ">> ";
    String line;
    
    while(true){
      
      channel.basicConsume(QUEUE_NAME, true, consumer);
      System.out.print(atual + prompt);
      
      line = sc.nextLine();
      
      while(line.charAt(0) == '@'){
        
        Chat.usuarioAtual = line;
        
        do{
          System.out.print(atual + Chat.usuarioAtual + prompt);
          
          line = sc.nextLine();
          if(line.charAt(0) != '@' && line.charAt(0) != '!'){
            
            String message = "(" + data + " às " + hora + ") " + Chat.usuarioAtual.substring(1, Chat.usuarioAtual.length()) + " diz: " + line;
            
            channel.basicPublish("",Chat.usuarioAtual.substring(1, Chat.usuarioAtual.length()), null,  message.getBytes("UTF-8"));
          }
        }
        while(line.charAt(0) != '@');
      }
    }
  }
}