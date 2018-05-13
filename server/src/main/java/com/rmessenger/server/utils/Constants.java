package com.rmessenger.server.utils;

public class Constants {

    public static final String CHAT_APPLICATION_QUEUE = "chat-application-messages";
    public static final String CONVERSATION_OUTGOING_EXHANGE = "converstaion.outgoing";
    public static final String CONVERSTATION_INCOMING_EXCHANGE = "converstaion.incoming";

    final static String CLIENT1_EXCHANGE = "client1.incoming";
    final static String CLIENT2_EXCHANGE = "client2.incoming";

    //DB
    public final static String CONNECTION_STRING = "jdbc:postgresql://localhost/RabbitMessenger";
    public final static String POSTGRES_USER = "rabbitadmin";
    public final static String POSTGRES_USER_PASSWORD = "admin";
}
