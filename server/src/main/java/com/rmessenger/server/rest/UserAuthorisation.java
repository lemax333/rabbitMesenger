package com.rmessenger.server.rest;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthorisation {

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "text/plain")
    public String login(@RequestBody String jsonUser){
        //write user to database
        //createExchange
        //send exchange name
        FanoutExchange exchange = new FanoutExchange("client1", false, true);
        return jsonUser;
    }
}
