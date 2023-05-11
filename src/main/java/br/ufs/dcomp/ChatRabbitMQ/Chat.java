package br.ufs.dcomp.ChatRabbitMQ;


import br.ufs.dcomp.ChatRabbitMQ.api.APIClient;
import com.rabbitmq.client.*;
import java.util.*;
import java.io.IOException;

public class Chat {
  
  public static String userAtual= ""; 

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    // String ipHost = "54.174.241.241";
    // String login = "gabrielbtera";
    // String key = "123456789";

    String ipHost = "54.87.169.97";
    String login= "admin";
    String key = "password";

    factory.setHost(ipHost); // Alterar
    factory.setUsername(login); // Alterar
    factory.setPassword(key); // Alterar
    factory.setVirtualHost("/"); 
    
    
    

    Connection connection = factory.newConnection();

    Channel channel = connection.createChannel();
    Channel channel_arquivos = connection.createChannel();

    Scanner sc = new Scanner(System.in);
    System.out.print("User: ");
    String user = sc.nextLine();

    Grupo grupo = new Grupo(channel, channel_arquivos, new APIClient("http://" + ipHost, login, key), user);

    Mensagem msg = new Mensagem();
    String nomeGrupo = "";
    msg.criarDiretorio(user);
    String QUEUE_NAME = user;
    String QUEUE_NAME_FILE = QUEUE_NAME + "F";

                      //(queue-name, durable, exclusive, auto-delete, params); 
    channel.queueDeclare(QUEUE_NAME, false,   false,     false,       null);
    channel_arquivos.queueDeclare(QUEUE_NAME_FILE, false,   false,     false,       null);


// ------------------------------------ RECEPTOR ------------------------------------
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
       try{
         System.out.println(msg.recebeMessagem(body, user));
       } catch (Exception ex) {
         System.out.println (ex.toString());
       }
       System.out.print(Chat.userAtual + ">> ");
    }
    };

    
// ------------------------------------- CHAT ----------------------------------------- 
    String current = "";
    String prompt = ">> ";
    String line = "";

    channel.basicConsume(QUEUE_NAME, true, consumer);
    channel_arquivos.basicConsume(QUEUE_NAME_FILE, true, consumer);

    while(line.equals(".") == false){
      System.out.print(Chat.userAtual + prompt + current);
      line = sc.nextLine();

      if(line.equals(".") == true) 
        break; //saida do chat
      if(line.charAt(0) == '@'){//Muda usuario no prompt
        Chat.userAtual = line;
        nomeGrupo = "";
      }
      else if(line.charAt(0) == '#'){//mensagem para grupo
        Chat.userAtual = line;
        nomeGrupo = line.substring(1);
      }
      else if(line.charAt(0) == '!'){//verifica comando
        grupo.verificaMensagem(line, user, Chat.userAtual.substring(1), nomeGrupo);
      }
      else if(Chat.userAtual.equals("") == false){  
        if(Chat.userAtual.charAt(0) == '#') //caso o envio seja para um grupo
          msg.enviarMessagem(user, line, "", channel, nomeGrupo);
        else 
          msg.enviarMessagem(user, line, Chat.userAtual.substring(1), channel, ""); //caso o envio seja para outro usuario
      }
    }
    channel.close();
    connection.close();

    
  }
}