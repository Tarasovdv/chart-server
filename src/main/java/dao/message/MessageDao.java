package dao.message;

import model.Message;

public interface MessageDao {

    Message findByMessage(Integer idUser);

    void createMessage(Message message);

    Message deleteMessage(Integer id);
}
