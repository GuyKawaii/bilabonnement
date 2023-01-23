package com.example.bilabonnement.utility;

/**
 * @author daniel(GuyKawaii)
 * @author Ian(DatJustino)
 * @author Veronika(Rhod1um)
 */

import com.example.bilabonnement.model.enums.DB_CONNECTION;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.example.bilabonnement.model.enums.DB_CONNECTION.*;

public class DatabaseConnectionManager {
    private static String url;
    private static String username;
    private static String password;
    private static Connection conn;

    private DatabaseConnectionManager() {
    }

    public static Connection getConnection(DB_CONNECTION db_connection) {
        if (conn != null) {
            return conn;
        }
        // azure connect [Default]

        // select release or test db
        if (db_connection == RELEASE_DB) {
            url = System.getenv("azure_url");
            username = System.getenv("azure_username");
            password = System.getenv("azure_password");
        } else {
            url = System.getenv("azure_testurl");
            username = System.getenv("azure_username");
            password = System.getenv("azure_password");
        }
//        // localhost connect
//        url = "jdbc:mysql://localhost:3306/bilabonnement";
//        username = "root";
//        password = "anBguFVWxF#9";

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
