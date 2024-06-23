package com.example.tcc_reddit.DTOs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CustomRedditKindEntiryIdDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String id = p.getText();
        if (id.startsWith("t5_")) { // prefixo de subrredit's
            return id.substring(3); // Remove "t?_"
        }
        if (id.startsWith("t2_")) { // prefixo de users
            return id.substring(3); // Remove "t?_"
        }
        return id; // Retorna o ID se não começar com "t?_"
    }
}
