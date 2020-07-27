package dao.user;

import model.User;

public interface UserDao {

    User findByName(String name);
    void createUser(User user);
    User deleteUser(User user);
    User setLogin(String oldName,String newName);
    User setPassword(String password, String setPassword);
}
