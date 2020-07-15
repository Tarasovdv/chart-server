package dao.user;

import model.User;

public interface UserDao {

    User findByName(String name);
    User createUser(String name, String password);
    User deleteUser(String name);
    User setLogin(String name,String setName);
    User setPassword(String password, String setPassword);
}
