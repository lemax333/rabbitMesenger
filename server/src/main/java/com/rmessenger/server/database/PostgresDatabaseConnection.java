package com.rmessenger.server.database;

import com.rmessenger.server.exception.ErrorCode;
import com.rmessenger.server.exception.ServerException;
import com.rmessenger.server.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresDatabaseConnection implements DatabaseConnection {

    private Properties properties;
    private Connection connection;

    public PostgresDatabaseConnection() throws ServerException {
        setDatabaseUserCredentials();
        establishDatabaseConnection();
    }

    private void setDatabaseUserCredentials(){
        properties = new Properties();
        properties.setProperty("user",  Constants.POSTGRES_USER);
        properties.setProperty("password", Constants.POSTGRES_USER_PASSWORD);
    }

    private void establishDatabaseConnection() throws ServerException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(Constants.CONNECTION_STRING, properties);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServerException(ErrorCode.DATABASE_ERROR, e);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
