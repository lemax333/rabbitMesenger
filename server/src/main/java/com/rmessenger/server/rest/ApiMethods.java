package com.rmessenger.server.rest;

import com.rmessenger.server.rest.model.Conversation;
import com.rmessenger.server.rest.model.UserData;
import com.rmessenger.server.utils.DbConnector;
import com.rmessenger.server.utils.QueueHelper;
import com.rmessenger.server.utils.StringUtils;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiMethods {

    @Autowired
    DbConnector connector;
    @Autowired
    QueueHelper queueHelper;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST, consumes = "application/json")
    public String login(@RequestBody UserData userData){
        String resultString = "";
        String password = connector.getUserPassword(userData.getUsername());
        if (!password.equals(userData.getPassword())) {
            resultString = "Wrong username or password";
        } else {
            resultString = connector.getUserExhange(userData.getUsername());
        }
        return resultString;
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST, consumes = "application/json")
    public String register(@RequestBody UserData newUser){
        String exchangeName = StringUtils.getRandomString(7);
        FanoutExchange exchange = queueHelper.createExchange(exchangeName);
        newUser.setExchange(exchangeName);
        connector.createNewUser(newUser);
        return exchangeName;
    }

    @RequestMapping(value = "/api/create/conversation", method = RequestMethod.POST, consumes = "application/json")
    public String createConversation(@RequestBody Conversation newConversation){
        //TODO insert new conversation to db
        //TODO insert new user_conversation for each user
        //TODO add routeId(conversation name)for each user exchange
        return null;
    }
}
