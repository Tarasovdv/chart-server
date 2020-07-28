package dao.message;

import lombok.SneakyThrows;
import model.Message;
import model.User;
import utils.Props;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    private final static String DB_URL = Props.getValue("db.url");
    private final static String DB_USER = Props.getValue("db.user");
    private final static String DB_PASSWORD = Props.getValue("db.password");


    @SneakyThrows
    @Override
    public List<Message> findAllByUser(User user) {
        ResultSet resultSet = getStatement().executeQuery("SELECT login, text from message, user WHERE message.id_user = user.id");
        List<Message> messages = new ArrayList<>();
        while (resultSet.next()) {
            if (user.getName().equals(resultSet.getString("login"))) {
                messages.add(new Message(resultSet.getString("text"), user));
            }
        }
        return messages;
    }

    @SneakyThrows
    @Override
    public void createMessage(Message message) {
        ResultSet resultSet = getStatement().executeQuery("SELECT id FROM user WHERE login = '" + message.getUser().getName() + "'");
        resultSet.next();
        int userId = resultSet.getInt("id");
        getStatement().executeUpdate("INSERT INTO message(id_mes,text,id_user) values ('" +
                message.getText() + "','" + userId + "');");
    }



    private Statement getStatement() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
    }


}

