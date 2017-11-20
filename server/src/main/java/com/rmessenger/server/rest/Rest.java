package com.rmessenger.server.rest;

import com.rmessenger.server.rest.model.UserAuthentication;
import com.rmessenger.server.utils.DbConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Rest {

    @Autowired
    DbConnector connector;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST, consumes = "application/json")
    public String login(@RequestBody UserAuthentication userAuthentication){
        return null;
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST, consumes = "application/json")
    public String register(@RequestBody String json){
        return connector.createStatement();
    }
}
