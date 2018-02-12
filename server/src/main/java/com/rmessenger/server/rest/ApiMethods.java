package com.rmessenger.server.rest;

import com.rmessenger.server.rest.model.Conversation;
import com.rmessenger.server.rest.model.UserData;
import com.rmessenger.server.rest.model.request.GetContactsRequest;
import com.rmessenger.server.rest.model.request.LoginRequest;
import com.rmessenger.server.rest.model.response.GetContactsResponse;
import com.rmessenger.server.rest.model.response.LoginResponse;
import com.rmessenger.server.rest.model.response.RegisterResponse;
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

    @RequestMapping(value = "/api/create/conversation", method = RequestMethod.POST, consumes = "application/json")
    public String createConversation(@RequestBody Conversation newConversation){
        //TODO insert new conversation to db
        //TODO insert new user_conversation for each user
        //TODO add routeId(conversation name)for each user exchange
        return null;
    }
}
