package com.rmessenger.server.database;

import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
public interface DatabaseConnection {

    public Connection getConnection();
}
