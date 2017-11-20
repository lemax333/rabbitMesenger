package com.rmessenger.server.utils;

import com.rmessenger.server.rest.model.UserData;
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
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(Constants.CONNECTION_STRING, props);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createNewUser(UserData newUser){
        try {
            PreparedStatement st = this.connection.prepareStatement("INSERT INTO users VALUES (?,?,?,?)");
            st.setInt(1, newUser.getId());
            st.setString(2, newUser.getUsername());
            st.setString(3, newUser.getPassword());
            st.setString(4, newUser.getExchange());
            st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserPassword(String username){
        String result = "";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                result = resultSet.getString(1);
                System.out.println(result);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return result;
    }

    public String getUserExhange(String username){
        String result = "";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT exchange FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                result += resultSet.getString(1);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return result;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
