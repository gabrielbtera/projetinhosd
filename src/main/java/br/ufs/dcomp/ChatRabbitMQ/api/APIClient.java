package br.ufs.dcomp.ChatRabbitMQ.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class APIClient {
    private String host = "http://localhost:15672/api/";
    private String username = "guest";
    private String password = "guest";

    public APIClient(String host, String username, String password) {
        this.host = host + ":15672/api";
        this.username = username;
        this.password = password;
    }

    public void getGroups(String queueName) {
        try {
            String url = host + "queues/" + queueName;
            String response = sendRequest(url);
            System.out.println("Exchanges declared in the queue " + queueName + ":");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getUsers(String exchangeName) {
        try {
            String url = host + "/exchanges/%2F/" + exchangeName + "/bindings/source";
            String json = sendRequest(url);
            List<ExchangeBinding> bindings = ExchangeBinding.deserialize(json);
            String usernames = bindings.stream().map(binding -> binding.destination).collect(Collectors.joining(", "));

            System.out.println(usernames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String sendRequest(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Basic Authentication
        String auth = username + ":" + password;
        byte[] encodedAuth = java.util.Base64.getEncoder().encode(auth.getBytes());
        String authHeaderValue = "Basic " + new String(encodedAuth);
        con.setRequestProperty("Authorization", authHeaderValue);

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(con.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();
            return response;
        } else {
            throw new RuntimeException("Failed to get response from RabbitMQ API. HTTP error code: " + responseCode);
        }
    }

}
