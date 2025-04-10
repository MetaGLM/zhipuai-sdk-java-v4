package com.zhipu.oapi.service.v4.realtime;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JasonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper() //
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) //
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static RealtimeClientEvent fromJsonToClientEvent(String json)  {
        try {
            return objectMapper.readValue(json, RealtimeClientEvent.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static String toJsonFromClientEvent(RealtimeClientEvent event)  {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static RealtimeServerEvent fromJsonToServerEvent(String json)  {
        try {
            return objectMapper.readValue(json, RealtimeServerEvent.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static String toJsonFromServerEvent(RealtimeServerEvent event)  {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}