package services;

import dao.message.MessageDao;
import dao.message.MessageDaoImpl;
import dao.user.UserDao;
import dao.user.UserDaoImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import model.Message;
import model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


@RequiredArgsConstructor
public class ClientRunnable implements Runnable, Observer {
    private final Socket clientSocket;
    private final MyServer server;
    private User client;
    private List<Message> text;
    //    private boolean isRegistered = false;
    private final UserDao dao = new UserDaoImpl();
    private  final MessageDao messDao = new MessageDaoImpl();


    @SneakyThrows
    @Override
    public void run() {
        server.addObserver(this);

        BufferedReader readerFromUser = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String messageFromUser = "";
        while (!clientSocket.isClosed() && (messageFromUser = readerFromUser.readLine()) != null) {
            if (messageFromUser.contains("Registration: ") || messageFromUser.contains("Регистрация: ")) {
                registration(messageFromUser);
            } else if (messageFromUser.contains("Authorization: ") || messageFromUser.contains("Авторизация: ")) {
                authorization(messageFromUser);

            } else if (messageFromUser.contains("Delete: ") || messageFromUser.contains("Удаление: ")) {
                delete(messageFromUser);


            } else if (messageFromUser.contains("Change name: ") || messageFromUser.contains("Смена имени: ")) {
                setLogin(messageFromUser);


            } else if (messageFromUser.contains("Change password: ") || messageFromUser.contains("Смена пароля: ")) {
                setPassword(messageFromUser);


            } else if (messageFromUser.contains("->")) {
                messDao.createMessage(new Message(messageFromUser,client));


            }else {
                break;
            }
        }

        if (!clientSocket.isClosed()) {

            do {
                System.out.println(messageFromUser);
                server.notifyObservers(messageFromUser);
            } while ((messageFromUser = readerFromUser.readLine()) != null);
        }

    }


//    @SneakyThrows
//    private void createMessage(String messageFromUser){
//        Message text = messageFromUser.split(" ")[2];
////        String newName = messageFromUser.split(" ")[3];
//        User userFromDao;
//        if ((userFromDao = dao.findByName(oldName)) != null) {
//            client = userFromDao;
//            messDao.createMessage(text,client);
//            notifyObserver("Change login done!");
//        } else {
//            notifyObserver("Change login failed, wrong login");
//            server.deleteObserver(this);
//            clientSocket.close();
//        }
//    }
    @SneakyThrows
    private void setLogin(String messageFromUser){
        String oldName = messageFromUser.split(" ")[2];
        String newName = messageFromUser.split(" ")[3];
        User userFromDao;
        if ((userFromDao = dao.findByName(oldName)) != null) {
            client = userFromDao;
            dao.setLogin(client,newName);
            notifyObserver("Change login done!");
        } else {
            notifyObserver("Change login failed, wrong login");
            server.deleteObserver(this);
            clientSocket.close();
        }
    }

    @SneakyThrows
    private void setPassword(String messageFromUser){
        String name = messageFromUser.split(" ")[2];
        String oldPassword = messageFromUser.split(" ")[3];
        String newPassword = messageFromUser.split(" ")[4];
        User userFromDao;
        if ((userFromDao = dao.findByName(name)) != null) {
            if (userFromDao.getPassword().equals(oldPassword)) {
                client = userFromDao;
                dao.setPassword(client, newPassword);
                notifyObserver("Change password done!");
            }
        } else {
            notifyObserver("Change password failed, wrong login");
            server.deleteObserver(this);
            clientSocket.close();
        }
    }

    @SneakyThrows
    private void authorization(String messageFromUser) {
        String loginFromClient = messageFromUser.split(" ")[1];
        String passwordFromClient = messageFromUser.split(" ")[2];

        User userFromDao;
        if ((userFromDao = dao.findByName(loginFromClient)) != null) {
            if (userFromDao.getPassword().equals(passwordFromClient)) {
                client = userFromDao;
                notifyObserver("Authorization successfully!");
            } else {
                notifyObserver("Authorization failed wrong password");
                server.deleteObserver(this);
                clientSocket.close();
            }
        } else {
            notifyObserver("Authorization failed wrong name");
            server.deleteObserver(this);
            clientSocket.close();
        }

    }

    private void registration(String messageFromUser) {
        System.out.println("Registration:");
        client = new User(messageFromUser.split(" ")[1],
                messageFromUser.split(" ")[2]);
        dao.createUser(client);
        System.out.println("Registration for " + client.getName() + " success!");
        notifyObserver("Registration successfully!");
    }

    @SneakyThrows
    private void delete(String messageFromUser) {
        String loginFromClient = messageFromUser.split(" ")[1];
        String passwordFromClient = messageFromUser.split(" ")[2];

        User userFromDao;
        if ((userFromDao = dao.findByName(loginFromClient)) != null) {
            if (userFromDao.getPassword().equals(passwordFromClient)) {
                client = userFromDao;
                dao.deleteUser(client);
                notifyObserver("Delete successfully!");
            } else {
                notifyObserver("Delete failed wrong password");
                server.deleteObserver(this);
                clientSocket.close();
            }
        } else {
            notifyObserver("Delete failed wrong name");
            server.deleteObserver(this);
            clientSocket.close();
        }

    }

    @SneakyThrows
    @Override
    public void notifyObserver(String message) {
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
//        if (client != null) {
        printWriter.println(message);
        printWriter.flush();
//        }

    }
}
