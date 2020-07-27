import dao.user.UserDao;
import dao.user.UserDaoImpl;
import services.MyServer;

import java.io.IOException;
import java.util.Properties;

public class MyApp {

    public static void main(String[] args) {


        new MyServer().start();

//        Properties props = new Properties();
//
//        try {
//            props.load(MyApp.class.getResourceAsStream("application.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("props.getProperty(\"user\") = " + props.getProperty("user"));

//        UserDao dao = new UserDaoImpl();
//        dao.createUser("2","2");
//        dao.deleteUser("Dmitriy");


    }
}
