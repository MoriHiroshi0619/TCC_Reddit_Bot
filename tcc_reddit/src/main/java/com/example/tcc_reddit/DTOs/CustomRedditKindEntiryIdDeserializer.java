package com.example.tcc_reddit.DTOs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CustomRedditKindEntiryIdDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String subredditId = p.getText();
        if (subredditId.startsWith("t5_")) {
            return subredditId.substring(3); // Remove "t?_"
        }
        return subredditId; // Retorna o ID se não começar com "t?_"
    }
}
