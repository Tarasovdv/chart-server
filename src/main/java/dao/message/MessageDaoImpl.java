package dao.message;

import lombok.SneakyThrows;
import model.Message;
import utils.Props;

import java.sql.*;

public class MessageDaoImpl implements MessageDao {
    private final static String DB_URL = Props.getValue("db.url");
    private final static String DB_USER = Props.getValue("db.user");
    private final static String DB_PASSWORD = Props.getValue("db.password");


//    @SneakyThrows
//    @Override
//    public Message findByMessage(Integer idUser) {
//        ResultSet resultSet = getStatement().executeQuery("SELECT text, password from message");
//        while (resultSet.next()) {
//            if (message.getText().equalsIgnoreCase(resultSet.getString("text"))) {
//                return new Message(message.getId(), message.getText(), resultSet.getShort("text"));
//            }
//        }
//        return null;
//    }

    @Override
    public Message findByMessage(Integer idUser) {
        return null;
    }

    @SneakyThrows
    @Override
    public void createMessage(Message message) {
        getStatement().executeUpdate("INSERT INTO message(id,text,idUser) values ('"
                + message.getId() + ',' + message.getText() + "','" + message.getIdUser() + "');");


    }

    @SneakyThrows
    @Override
    public Message deleteMessage(Integer id) {
        getStatement().executeUpdate("DELETE from message WHERE '" + id + "'");
        System.out.println("deleted successful");
        return null;
    }

    private Statement getStatement() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return connection.createStatement();
    }


}

