package dao.message;

import model.Message;
import model.User;

import java.util.List;

public interface MessageDao {

    List<Message> findAllByUser(User user);

    void createMessage(Message message);


}
