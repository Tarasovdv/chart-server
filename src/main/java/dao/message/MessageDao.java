package dao.message;

import model.Message;

public interface MessageDao {
    Message makeMessage(String name, String message);
}
