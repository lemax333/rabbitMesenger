package com.rmessenger.server.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostgresDatabaseService {

    @Autowired
    private DatabaseConnection databaseConnection;


}
