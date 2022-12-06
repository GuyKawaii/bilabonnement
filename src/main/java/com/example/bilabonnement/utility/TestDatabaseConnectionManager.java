package com.example.bilabonnement.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabaseConnectionManager {

    private static String url;
    private static String username;
    private static String password;
    private static Connection conn;

    private TestDatabaseConnectionManager() {
    }

    public static Connection getConnection(){
      if(conn != null){
        return conn;
      }
      // azure connect [Default]
      url = "jdbc:mysql://keaserver.mysql.database.azure.com:3306/bilabonnement";
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

