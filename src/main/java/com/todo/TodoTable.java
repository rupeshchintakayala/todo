package com.todo;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

class TodoTable {
    private String url = "jdbc:sqlite:test.db";
    private Connection connection;
    void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
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
    }
    //TodoTable Section//
    void createTodoTable() {
        String sqlForTodoTable = "CREATE TABLE IF NOT EXISTS todoTable (\n"
                + " todoId integer PRIMARY KEY,\n"
                + " todoName text NOT NULL,\n"
                + " categoryId integer\n"
                + ");";

        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.execute(sqlForTodoTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    int insertValuesToTodoTable(String todoName,int categoryId) throws SQLException {
        connection = DriverManager.getConnection(url);
        String sqlForCategory="INSERT INTO todoTable (todoName,categoryId) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlForCategory);
        preparedStatement.setString(1, todoName);
        preparedStatement.setInt(2,categoryId);
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        int todoId=0;
        if (rs.next()){
            todoId=rs.getInt(1);
        }
        return todoId;
    }

    void displayExistingTodos() throws SQLException {
        connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from todoTable");
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

    void deleteTodo(int id) throws SQLException {
        connection = DriverManager.getConnection(url);
        String statement = "DELETE FROM todoTable WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    void updateTodo(int id, String action) throws SQLException {
        connection = DriverManager.getConnection(url);
        String statement = "UPDATE todoTable SET action=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, action);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    //Category Section//

    void createCategoryTable() {
        String sqlForTagTable = "CREATE TABLE IF NOT EXISTS categoryTable (\n"
                + " categoryId integer PRIMARY KEY,\n"
                + " categoryName text\n"
                + ");";
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.execute(sqlForTagTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void existingCategories() throws SQLException {
        List<String> categoryNames= Arrays.asList("work","travel","vacation","daily","personal");
        String sqlForCategory="INSERT INTO categoryTable (categoryName) VALUES (?)";
        for(String categoryName:categoryNames)
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlForCategory);
            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();
        }
    }
    int insertValuesToCategoryTable(String categoryName) throws SQLException {
        String sqlForCategory="INSERT INTO categoryTable (categoryName) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlForCategory);
        preparedStatement.setString(1, categoryName);
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        int categoryId=0;
        if (rs.next()){
            categoryId=rs.getInt(1);
        }
        return categoryId;
    }

    void displayExistingCategories() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from categoryTable");
        System.out.println("Existing Categories");
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

    void findByCategory(String categoryName) throws SQLException {
        String statement = "SELECT t.todoId, t.todoName\n"
                +"FROM todoTable AS t\n"
                +"INNER JOIN categoryTable AS c ON t.categoryId=c.categoryId\n"
                +"WHERE c.categoryName=?\n"
                +"ORDER BY t.todoId";

        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,categoryName);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + "\t\t");
            }
            System.out.println();
        }
    }

    //Tag Section//

    void dropTable(String tableName) throws SQLException {
        connection = DriverManager.getConnection(url);
        PreparedStatement preparedStatement = connection.prepareStatement(String.format("DROP TABLE IF EXISTS %s",tableName));
        preparedStatement.executeUpdate();
    }

    void createTagTable() {
        String sqlForTagTable = "CREATE TABLE IF NOT EXISTS tagTable (\n"
                + " tagId integer PRIMARY KEY,\n"
                + " tagName text\n"
                + ");";
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.execute(sqlForTagTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void existingTags() throws SQLException {
        List<String> tags= Arrays.asList("office","party","fun","food","project");
        String sqlForTags="INSERT INTO tagTable (tagName) VALUES (?)";
        for(String tagName:tags)
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlForTags);
            preparedStatement.setString(1, tagName);
            preparedStatement.executeUpdate();
        }
    }
    int insertValuesToTagTable(String tagName) throws SQLException {
        String sqlForTags="INSERT INTO tagTable (tagName) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlForTags);
        preparedStatement.setString(1, tagName);
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        int tagId=0;
        if (rs.next()){
            tagId=rs.getInt(1);
        }
        return tagId;
    }

    void displayExistingTags() throws SQLException {
        connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from tagTable");
        System.out.println("Tags Table");
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

    void findByTag(String tagName) throws SQLException {
        connection = DriverManager.getConnection(url);
        String statement = "SELECT t.todoId, t.todoName\n"
                +"FROM todoTable AS t\n"
                +"JOIN todoIdTagId AS bridge ON t.todoId=bridge.todoId \n"
                +"JOIN tagTable AS tag ON tag.tagId=bridge.tagId\n"
                +"WHERE tag.tagName=?\n"
                +"ORDER BY t.todoId";

        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,tagName);
        System.out.println("Actions that are related to the tag "+tagName+" are:");
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + "\t\t");
            }
            System.out.println();
        }
    }

    //TodoIdTagId Section//

    void createTodoIdTagIdTable() {
        String sqlForTodoIdTagId = "CREATE TABLE IF NOT EXISTS todoIdTagId (\n"
                + " todoId integer,\n"
                + " tagId integer\n"
                + ");";
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.execute(sqlForTodoIdTagId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void insertValuesToTodoIdTagIdTable(int todoId,int tagId) throws SQLException {
        String sqlForTags="INSERT INTO todoIdTagId (todoId,tagId) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlForTags);
        preparedStatement.setInt(1, todoId);
        preparedStatement.setInt(2, tagId);
        preparedStatement.executeUpdate();
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

    void displayAllActions() throws SQLException {
        connection = DriverManager.getConnection(url);
        String statement ="SELECT t.todoId, t.todoName, tag.tagName\n"
                +"FROM todoTable AS t\n"
                +"JOIN todoIdTagId AS bridge ON t.todoId=bridge.todoId\n"
                +"JOIN tagTable AS tag ON bridge.tagId=tag.tagId\n"
                +"ORDER BY t.todoId";
        System.out.println("All todo list");
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + "\t\t");
            }
            System.out.println();
        }
    }
}