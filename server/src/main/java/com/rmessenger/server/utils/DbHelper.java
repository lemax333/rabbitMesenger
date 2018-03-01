package com.rmessenger.server.utils;

import com.rmessenger.server.rest.model.UserData;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class DbHelper {

    private Connection connection;

    public DbHelper(){
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
            PreparedStatement st = this.connection.prepareStatement("INSERT INTO users (username, password, exchange) VALUES (?,?,?)");
//            st.setInt(1, newUser.getId());
            st.setString(1, newUser.getUsername());
            st.setString(2, newUser.getPassword());
            st.setString(3, newUser.getExchange());
            st.executeUpdate();
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getUserExchange(String username){
        String result = "";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT exchange FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                result += resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getUserContacts(String username){
        List<String> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(DbStatementsBuilder.buildGettingContacts());
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void createNewConversation(String newConversationName) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO conversation(name) VALUES (?)");
            preparedStatement.setString(1, newConversationName);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUserToConversation(String conversationId, String userId) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO conversation_user_x VALUES (?, ?)");
            preparedStatement.setInt(1, Integer.parseInt(conversationId));
            preparedStatement.setInt(2, Integer.parseInt(userId));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getConversationIdByName(String newConversationName) {
        Integer result = null;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT id FROM conversation WHERE name = ?");
            preparedStatement.setString(1, newConversationName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = (resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

    public String getUserIdByName(String firstUser) {
        String result = "";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT id FROM users WHERE username = ?");
            preparedStatement.setString(1, firstUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result += (resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
