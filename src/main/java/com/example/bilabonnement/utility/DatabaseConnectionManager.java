package com.example.bilabonnement.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static String url;
    private static String username;
    private static String password;
    private static Connection conn;

    private DatabaseConnectionManager() {
    }

    public static Connection getConnection(){
        if(conn != null){
            return conn;
        }
        // azure connect [Default]
        url = System.getenv("azure_url");
        username = System.getenv("azure_username");
        password = System.getenv("azure_password");

        // localhost connect


        // TBA

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
