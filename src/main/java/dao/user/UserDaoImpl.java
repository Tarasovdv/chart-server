package dao.user;

import dao.Connect;
import dao.user.UserDao;
import model.User;

import java.sql.*;

public class UserDaoImpl implements UserDao {
//    private Connect conn = new Connect();
    private String url = "jdbc:MySQL://localhost:3306/my_schema?serverTimezone=UTC";
    private String user = "root";
    private String password = "root";


    @Override
    public User findByName(String name) {
        String sql = "SELECT login, password FROM user";

//        conn.connection(url,user,password,sql);

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
//                System.out.println(resultSet.getString("login") + " ");
                if (name.equalsIgnoreCase(resultSet.getString("login"))) {
                    return new User(name, resultSet.getString("password"));
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return null;
    }

    @Override
    public User createUser(String name, String password) {
        String sql = "INSERT INTO my_schema.user(login,password) VALUES('" + name + "','" + password + "')";

        try (Connection connection = DriverManager.getConnection(url,
                user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
//                String login = resultSet.getString("login");
//                String pass = resultSet.getString("password");
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        return null;
    }

    @Override
    public User deleteUser(String name) {
        String sql = "delete from my_schema.user where login='" + name + "'";

        try (Connection connection = DriverManager.getConnection(url,
                user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                if (name.equalsIgnoreCase(resultSet.getString("login"))) {
                    System.out.println("delete");
//                    return new User(name, resultSet.getString("password"));
                } else {
                    System.out.println("not delete");
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return null;
    }

    @Override
    public User setLogin(String name, String setName) {
        String sql = "update my_schema.user set login='" + setName + "' where login='" + name + "'";

        try (Connection connection = DriverManager.getConnection(url,
                user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                if (name.equalsIgnoreCase(resultSet.getString("login"))) {
                    System.out.println("update log");
//                    return new User(name, resultSet.getString("password"));
                } else {
                    System.out.println("not update log");
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        return null;
    }

    @Override
    public User setPassword(String pass, String setPassword) {
        String sql="update my_schema.user set login='" + setPassword + "' where login='" + pass + "'";

        try (Connection connection = DriverManager.getConnection(url,
                user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                if (pass.equalsIgnoreCase(resultSet.getString("login"))) {
                    System.out.println("update pass");
//                    return new User(name, resultSet.getString("password"));
                } else {
                    System.out.println("not update pass");
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return null;
    }


}
