package com.rmessenger.server.utils;

public class DbStatementsBuilder {

    private static String SELECT_BASE = "SELECT %s FROM %s %s";
    private static String CRITERIA_EQUALS_BASE = "WHERE %s = ?";
    private static String CIRTERIA_NOT_EQUALS_BASE = "WHERE %s <> ?";

    public static String prepareGettingUserExchange(){
        String criteria = prepareEqualsCriteria("username");
        return prepareSelectStatement("exchange", "users", criteria);
    }

    public static String buildGettingContacts(){
        String criteria = prepareNotEqualsCriteria("username");
        return prepareSelectStatement("username", "users", criteria);
    }

    private static String prepareSelectStatement(String column, String table, String criteria){
        return String.format(SELECT_BASE, column, table, criteria);
    }

    private static String prepareEqualsCriteria(String column){
        return String.format(CRITERIA_EQUALS_BASE, column);
    }

    private static String prepareNotEqualsCriteria(String column){
        return String.format(CIRTERIA_NOT_EQUALS_BASE, column);
    }
}
