package br.ufs.dcomp.ChatRabbitMQ.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ExchangeBinding {
    public String source;
    public String vhost;
    public String destination;
    public String destination_type;
    public String routing_key;
    public Object arguments;
    public String properties_key;

    public static List<ExchangeBinding> deserialize(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ExchangeBinding> bindings = mapper.readValue(json, new TypeReference<List<ExchangeBinding>>(){});

        return bindings;
    }
}
