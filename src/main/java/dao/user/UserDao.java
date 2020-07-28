package dao.user;

import model.User;

public interface UserDao {

    User findByName(String name);
    void createUser(User user);
    User deleteUser(User user);
    void setLogin(User user,String newName);
    void setPassword(User user, String newPassword);
}
