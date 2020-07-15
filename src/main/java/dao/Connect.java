package dao;

import model.User;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

    public void connection(String url,String user,String password,String sql){
        try (java.sql.Connection connection = DriverManager.getConnection(url,
                user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

//            while (resultSet.next()) {
////                System.out.println(resultSet.getString("login") + " ");
//                if (name.equalsIgnoreCase(resultSet.getString("login"))) {
//                    return new User(name, resultSet.getString("password"));
//                }
//            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
