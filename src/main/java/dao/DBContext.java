package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    private static final String URL = "jdbc:mysql://localhost:3306/e_fashion";
    private static final String USER = "root";
    private static final String PASSWORD = "123456"; // đổi lại cho đúng máy bạn

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}