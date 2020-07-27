package dao.user;

import lombok.SneakyThrows;
import model.User;
import utils.Props;

import java.sql.*;

public class UserDaoImpl implements UserDao {
    private final static String DB_URL = Props.getValue("db.url");
    private final static String DB_USER = Props.getValue("db.user");
    private final static String DB_PASSWORD = Props.getValue("db.password");

    @SneakyThrows
    @Override
    public User findByName(String name) {
        ResultSet resultSet = getStatement().executeQuery("SELECT login, password from USER");
        while (resultSet.next()) {
            if (name.equalsIgnoreCase(resultSet.getString("login"))) {
                return new User(name, resultSet.getString("password"));
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void createUser(User user) {
        getStatement().executeUpdate("INSERT INTO USER(login, password) VALUES ('"
                + user.getName() + "','" + user.getPassword() + "') ");
    }

    @SneakyThrows
    @Override
    public User deleteUser(User user) {
        getStatement().executeUpdate("DELETE from USER WHERE login='" + user.getName()
                + "'and password='" + user.getPassword() + "'");
        System.out.println("Deleted successful!");
        return null;
    }

    @SneakyThrows
    @Override
    public User setLogin(String oldName, String newName) {
        getStatement().executeUpdate("UPDATE user SET login = '" + newName
                + "' where login = '" + oldName + "'");
        System.out.println("Change login!");

        return null;
    }

    @SneakyThrows
    @Override
    public User setPassword(String oldPassword, String newPassword) {
        getStatement().executeUpdate("UPDATE user SET password = '" + newPassword
                + "' where password = '" + oldPassword + "'");
        System.out.println("Change password!");

        return null;
    }

    private Statement getStatement() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return connection.createStatement();
    }
}


