package com.rmessenger.server.utils;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Properties;

@Component
public class DbConnector {

    private Connection connection;

    public DbConnector(){
        Properties props = new Properties();
        props.setProperty("user",  Constants.POSTGRES_USER);
        props.setProperty("password", Constants.POSTGRES_USER_PASSWORD);
//        props.setProperty("ssl", "true");
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(Constants.CONNECTION_STRING, props);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String createStatement(){
        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            if (rs.next()){
                String res = rs.getString(2);
                return res;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
