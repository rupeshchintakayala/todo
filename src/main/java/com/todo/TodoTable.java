package com.todo;

import java.sql.*;
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

    void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS todo (\n"
                + " id integer PRIMARY KEY,\n"
                + " action text NOT NULL,\n"
                + " category text\n"
                + ");";
        String sqlForTagTable="CREATE TABLE IF NOT EXISTS tagTable (\n"
                +" id integer,\n"
                +" tag text\n"
                +");";
        try {
            connection=DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.execute(sql);
            statement.execute(sqlForTagTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    int insertValuesToDatabase(String action, String category, List<String> tags) throws SQLException {
        connection=DriverManager.getConnection(url);
        String statement = "INSERT INTO todo (action,category) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, action);
        preparedStatement.setString(2, category);
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        int todoid=0;
        if (rs.next()){
            todoid=rs.getInt(1);
        }
        for(String tag:tags){
            statement = "INSERT INTO tagTable (todoid,tag) VALUES (?,?)";
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, todoid);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        }
        return todoid;
    }

    void showTable() throws SQLException {
        connection=DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from todo");
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

    void showTags() throws SQLException {
        connection=DriverManager.getConnection(url);
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

    void dropTable() throws SQLException {
        connection=DriverManager.getConnection(url);
        String sql = "DROP TABLE todo;";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void deleteTodo(int id) throws SQLException {
        connection=DriverManager.getConnection(url);
        String statement = "DELETE FROM TODO WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    void updateTodo(int id, String action) throws SQLException {
        connection=DriverManager.getConnection(url);
        String statement = "UPDATE todo SET action=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, action);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    void findByCategory(String category) throws SQLException {
        connection=DriverManager.getConnection(url);
        String statement = "SELECT * FROM todo WHERE category=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,category);
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

    void findByTag(String tag) throws SQLException {
        connection=DriverManager.getConnection(url);
        String statement = "SELECT * FROM todo WHERE tags LIKE ? OR tags LIKE ? OR tags LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,tag+",%");
        preparedStatement.setString(2,"%,"+tag+",%");
        preparedStatement.setString(3,"%,"+tag);
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