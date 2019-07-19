package com.todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TodoStore {
    private String url = "jdbc:sqlite:test.db";
    private Connection connection;

    private String connectToDatabase() {
        String message = "";
        try {
            connection = DriverManager.getConnection(url);
            message = "Connection to SQLite has been established.";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return message;
    }

    //TodoStore Section//
    String initialize() throws SQLException {
        dropTable("todo");
        dropTable("tags");
        dropTable("category");
        dropTable("todoIdTagId");
        String message = connectToDatabase();
        create();
        existingCategories();
        existingTags();
        return message;
    }

    private void create() {
        String sqlForTodoTable = "CREATE TABLE IF NOT EXISTS todo (\n"
                + " todoId integer PRIMARY KEY,\n"
                + " todoName text NOT NULL,\n"
                + " categoryId integer\n"
                + ");";
        String sqlForTagTable = "CREATE TABLE IF NOT EXISTS tags (\n"
                + " tagId integer PRIMARY KEY,\n"
                + " tagName text\n"
                + ");";
        String sqlForCategoryTable = "CREATE TABLE IF NOT EXISTS category (\n"
                + " categoryId integer PRIMARY KEY,\n"
                + " categoryName text\n"
                + ");";
        String sqlForTodoIdTagId = "CREATE TABLE IF NOT EXISTS todoIdTagId (\n"
                + " todoId integer,\n"
                + " tagId integer\n"
                + ");";

        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.execute(sqlForTodoTable);
            statement.execute(sqlForCategoryTable);
            statement.execute(sqlForTagTable);
            statement.execute(sqlForTodoIdTagId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    int add(Todo todo, int categoryId,String categoryName, List<Integer> tagIdList, List<String> tagNameList) throws SQLException {
        connection = DriverManager.getConnection(url);
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int todoId = 0;
        //Adding category
        if (categoryName!=null) {
            String sqlForCategory = "INSERT INTO category (categoryName) VALUES (?)";
            preparedStatement = connection.prepareStatement(sqlForCategory);
            preparedStatement.setString(1, todo.getCategory());
            preparedStatement.executeUpdate();
        }
        //Adding todoId,todoName,categoryId to todo
        String sqlForAddingTodo = "INSERT INTO todo (todoName,categoryId) VALUES (?,?)";
        preparedStatement = connection.prepareStatement(sqlForAddingTodo);
        preparedStatement.setString(1, todo.getAction());
        preparedStatement.setInt(2, categoryId);
        preparedStatement.executeUpdate();
        resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            todoId = resultSet.getInt(1);
        }

        //Adding Tags
        String sqlForTags = "INSERT INTO tags (tagName) VALUES (?)";
        String sqlForTodoTag = "INSERT INTO todoIdTagId (todoId,tagId) VALUES (?,?)";
        int tagId = 0;
        if (tagNameList != null) {
            for (String tagName : tagNameList) {
                    preparedStatement = connection.prepareStatement(sqlForTags);
                    preparedStatement.setString(1, tagName);
                    preparedStatement.executeUpdate();
                    resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet.next()) {
                        tagId = resultSet.getInt(1);
                    }
                    preparedStatement = connection.prepareStatement(sqlForTodoTag);
                    preparedStatement.setInt(1, todoId);
                    preparedStatement.setInt(2, tagId);
                    preparedStatement.executeUpdate();
            }
        }
        if (tagIdList != null) {
            for (Integer tagid : tagIdList) {
                preparedStatement = connection.prepareStatement(sqlForTodoTag);
                preparedStatement.setInt(1, todoId);
                preparedStatement.setInt(2, tagid);
                preparedStatement.executeUpdate();
            }
        }
        return todoId;
    }

    int deleteTodo(int id) throws SQLException {
        int reference = 0;
        connection = DriverManager.getConnection(url);
        String statement = "DELETE FROM todo WHERE todoId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            reference = resultSet.getInt(1);
        }
        return reference;
    }

    void updateTodo(int id, String action) throws SQLException {
        connection = DriverManager.getConnection(url);
        String statement = "UPDATE todo SET todoName=? WHERE todoId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, action);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    private void existingCategories() throws SQLException {
        List<String> categoryNames = Arrays.asList("work", "travel", "vacation", "daily", "personal");
        String sqlForCategory = "INSERT INTO category (categoryName) VALUES (?)";
        for (String categoryName : categoryNames) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlForCategory);
            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();
        }
    }

    private void existingTags() throws SQLException {
        List<String> tags = Arrays.asList("office", "party", "fun", "food", "project");
        String sqlForTags = "INSERT INTO tags (tagName) VALUES (?)";
        for (String tagName : tags) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlForTags);
            preparedStatement.setString(1, tagName);
            preparedStatement.executeUpdate();
        }
    }

    private void dropTable(String tableName) throws SQLException {
        connection = DriverManager.getConnection(url);
        PreparedStatement preparedStatement = connection.prepareStatement(String.format("DROP TABLE IF EXISTS %s", tableName));
        preparedStatement.executeUpdate();
    }

    String getCategory(int id) throws SQLException {
        String categoryName = "";
        connection = DriverManager.getConnection(url);
        String statement = "SELECT categoryName FROM category WHERE categoryId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, id);
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                categoryName = resultSet.getString(i);
            }
        }
        return categoryName;
    }

    List<String> getTags(List<Integer> id) throws SQLException {
        String columnValue = "";
        ResultSet resultSet;
        List<String> tagNames = new ArrayList<String>();
        connection = DriverManager.getConnection(url);
        for (Integer tagId : id) {
            String statement = "SELECT tagName FROM tags WHERE tagId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, tagId);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    columnValue = resultSet.getString(i);
                }
                tagNames.add(columnValue);
            }
        }
        return tagNames;
    }

    List<String> findByCategory(String categoryName) throws SQLException {
        List<String> searchResultList = new ArrayList<String>();
        String statement = "SELECT t.todoId, t.todoName\n"
                + "FROM todo AS t\n"
                + "INNER JOIN category AS c ON t.categoryId=c.categoryId\n"
                + "WHERE c.categoryName=?\n"
                + "ORDER BY t.todoId";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, categoryName);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            StringBuilder listElement = new StringBuilder();
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                listElement.append(" ").append(columnValue);
            }
            searchResultList.add(listElement.toString());
        }
        return searchResultList;
    }

    List<String> findByTag(String tagName) throws SQLException {
        List<String> searchResultList = new ArrayList<String>();
        connection = DriverManager.getConnection(url);
        String statement = "SELECT t.todoId, t.todoName\n"
                + "FROM todo AS t\n"
                + "JOIN todoIdTagId AS bridge ON t.todoId=bridge.todoId \n"
                + "JOIN tags AS tag ON tag.tagId=bridge.tagId\n"
                + "WHERE tag.tagName=?\n"
                + "ORDER BY t.todoId";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, tagName);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            StringBuilder listElement = new StringBuilder();
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                listElement.append(" ").append(columnValue);
            }
            searchResultList.add(listElement.toString());
        }
        return searchResultList;
    }

    List<String> displayExistingCategories() throws SQLException {
        List<String> categoriesList = new ArrayList<String>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from category");
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            StringBuilder listElement = new StringBuilder();
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                listElement.append(" ").append(columnValue);
            }
            categoriesList.add(listElement.toString());
        }
        return categoriesList;
    }

    List<String> displayExistingTags() throws SQLException {
        List<String> tagsList = new ArrayList<String>();
        connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from tags");
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            StringBuilder listElement = new StringBuilder();
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                listElement.append(" ").append(columnValue);
            }
            tagsList.add(listElement.toString());
        }
        return tagsList;
    }

    List<String> displayAllActions() throws SQLException {

        List<String> listofActions = new ArrayList<String>();
        String columnValue = "";
        connection = DriverManager.getConnection(url);
        String statement = "SELECT t.todoId, t.todoName, tags.tagName\n"
                + "FROM todo AS t\n"
                + "JOIN todoIdTagId AS bridge ON t.todoId=bridge.todoId\n"
                + "JOIN tags ON bridge.tagId=tags.tagId\n"
                + "ORDER BY t.todoId";
        System.out.println("All todo list");
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            StringBuilder listElement = new StringBuilder();
            for (int i = 1; i <= columnsNumber; i++) {
                columnValue = resultSet.getString(i);
                listElement.append(" ").append(columnValue);
            }
            listofActions.add(listElement.toString());
        }
        return listofActions;
    }


    void displayExistingTodoTagBridge() throws SQLException {
        connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from todoIdTagId");
        System.out.println("TodoIds TagIds Table");
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + "\t\t");
            }
            System.out.println("");
        }
    }
}