package com.rmessenger.server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmessenger.server.broker.model.Message;

import java.io.IOException;

public class JsonMapper {

    private static ObjectMapper mapper = new ObjectMapper();

    public static Message getMessage(String jsonMessage) throws IOException {
        return mapper.readValue(jsonMessage, Message.class);
    }
}
