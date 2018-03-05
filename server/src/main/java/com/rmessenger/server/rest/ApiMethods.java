package com.rmessenger.server.rest;

import com.rmessenger.server.rest.model.UserData;
import com.rmessenger.server.rest.model.request.CreateConversationRequest;
import com.rmessenger.server.rest.model.request.GetContactsRequest;
import com.rmessenger.server.rest.model.request.GetConversationRequest;
import com.rmessenger.server.rest.model.request.LoginRequest;
import com.rmessenger.server.rest.model.response.*;
import com.rmessenger.server.utils.DbHelper;
import com.rmessenger.server.utils.QueueHelper;
import com.rmessenger.server.utils.StringUtils;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiMethods {

    @Autowired
    DbHelper dbHelper;
    @Autowired
    QueueHelper queueHelper;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST, consumes = "application/json")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        String exchange = "";
        String password = dbHelper.getUserPassword(loginRequest.getUsername());
        if (!password.equals(loginRequest.getPassword())) {
            exchange = "Wrong username or password";
        } else {
            exchange = dbHelper.getUserExchange(loginRequest.getUsername());
        }
        return new LoginResponse(exchange);
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST, consumes = "application/json")
    public RegisterResponse register(@RequestBody UserData newUser){
        String exchangeName = StringUtils.getRandomString(7);
        FanoutExchange exchange = queueHelper.createExchange(exchangeName);
        newUser.setExchange(exchangeName);
        dbHelper.createNewUser(newUser);
        RegisterResponse response = new RegisterResponse();
        response.setExchange(exchangeName);
        return response;
    }

    @RequestMapping(value = "/api/contacts", method = RequestMethod.POST, consumes = "application/json")
    public GetContactsResponse getContacts(@RequestBody GetContactsRequest getContactsRequest){
        List<String> contacts = dbHelper.getUserContacts(getContactsRequest.getUsername());
        GetContactsResponse response = new GetContactsResponse();
        response.setContacts(contacts);
        return response;
    }

    @RequestMapping(value = "/api/conversation", method = RequestMethod.POST, consumes = "application/json")
    public GetConversationResponse getConversation(@RequestBody GetConversationRequest conversationRequest){
        String firstUserId = dbHelper.getUserIdByName(conversationRequest.getFirstUser());
        String secondUserId = dbHelper.getUserIdByName(conversationRequest.getSecondUser());
        String convesationName = dbHelper.getConversationByParticipantsId(Integer.parseInt(firstUserId), Integer.parseInt(secondUserId));
        return new GetConversationResponse(convesationName);
    }

    @RequestMapping(value = "/api/conversation/create", method = RequestMethod.POST, consumes = "application/json")
    public CreateConversationResponse createConversation(@RequestBody CreateConversationRequest createConversationRequest){
        String newConversationName = StringUtils.getRandomString(8);
        dbHelper.createNewConversation(newConversationName);
        dbHelper.addUserToConversation(getConversationIdByName(newConversationName), getUserIdByName(createConversationRequest.getFirstUser()));
        dbHelper.addUserToConversation(getConversationIdByName(newConversationName), getUserIdByName(createConversationRequest.getSecondUser()));
        queueHelper.bindExchangeByConversationId(dbHelper.getUserExchange(createConversationRequest.getFirstUser()),
                newConversationName);
        queueHelper.bindExchangeByConversationId(dbHelper.getUserExchange(createConversationRequest.getSecondUser()),
                newConversationName);
        return new CreateConversationResponse(newConversationName);
    }

    private String getConversationIdByName(String newConversationName) {
        return dbHelper.getConversationIdByName(newConversationName);
    }

    private String getUserIdByName(String firstUser) {
        return dbHelper.getUserIdByName(firstUser);
    }
}
