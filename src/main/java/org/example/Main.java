package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        
    }

    public static Connection getConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(
                Paths.get("src/main/resources/database.properties"))) {
            properties.load(in);
        }

        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }

    public static void getResultSetMetaData(ResultSet resultSet){
        try {
            ResultSetMetaData metaData= resultSet.getMetaData();
            System.out.println(metaData.getColumnCount());
            System.out.println(metaData.getColumnName(1));
            System.out.println(metaData.getTableName(1));
            System.out.println(metaData.getCatalogName(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAllEmployees(){
        try {

            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");

            while (resultSet.next()) {
                String name = resultSet.getString("first_name");
                System.out.println(resultSet.isFirst());
                System.out.println(name);
                System.out.println("-----------------------------------------");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getHireDate(){
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");

            while (resultSet.next()) {
                LocalDate date = resultSet.getDate(5).toLocalDate();
                System.out.println(date);
                System.out.println("-----------------------------------------");
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }

}