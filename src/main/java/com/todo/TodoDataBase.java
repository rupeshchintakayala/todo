package com.todo;

import java.sql.*;

class TodoDataBase {
    private String url = "jdbc:sqlite:test.db";
    private Connection connection = null;

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
        String sql = "CREATE TABLE IF NOT EXISTS todoDatabase (\n"
                + " id integer PRIMARY KEY,\n"
                + " action text NOT NULL,\n"
                + " category text,\n"
                + " tags text\n"
                + ");";

        try {
            connection=DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    int insertValuesToDatabase(String action, String category, String tags) throws SQLException {
        connection=DriverManager.getConnection(url);
        String statement = "INSERT INTO todos (action,category,tags) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, action);
        preparedStatement.setString(2, category);
        preparedStatement.setString(3, tags);
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        int id=0;
        if (rs.next()){
            id=rs.getInt(1);
        }
        return id;
    }


    void showTable() throws SQLException {
        connection=DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from todoDatabase");
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
        String sql = "DROP TABLE todoDatabase;";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void deleteTodo(int id) throws SQLException {
        connection=DriverManager.getConnection(url);
        String sql = "DELETE FROM todoDatabase WHERE id=" + id + ";";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void updateTodo(int id, String action) throws SQLException {
        connection=DriverManager.getConnection(url);
        String statement = "UPDATE todoDatabase SET action=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, action);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    void findByCategory(String category) throws SQLException {
        connection=DriverManager.getConnection(url);
        String statement = "SELECT * FROM todoDatabase WHERE category=?";
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
            System.out.println("");
        }
    }

    void findByTag(String tag) throws SQLException {
        connection=DriverManager.getConnection(url);
        String statement = "SELECT * FROM todoDatabase WHERE tags LIKE ? OR tags LIKE ? OR tags LIKE ?";
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
            System.out.println("");
        }
    }


}