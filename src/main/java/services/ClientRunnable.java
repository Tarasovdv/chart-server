package services;

import dao.user.UserDao;
import dao.user.UserDaoImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


@RequiredArgsConstructor
public class ClientRunnable implements Runnable, Observer {
    private final Socket clientSocket;
    private final MyServer server;
    private User client;
    //    private boolean isRegistered = false;
    private final UserDao dao = new UserDaoImpl();


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

    @SneakyThrows
    private void setLogin(String messageFromUser){
        String oldName = messageFromUser.split(" ")[1];
        String newName = messageFromUser.split(" ")[2];
        User userFromDao;
        if ((userFromDao = dao.findByName(oldName)) != null) {
            client = userFromDao;
            dao.setLogin(oldName,newName);
            notifyObserver("Change login done!");
//            if (userFromDao.getName().equals(oldName)) {
//            }
//            else {
//                notifyObserver("Authorization failed wrong password");
//                server.deleteObserver(this);
//                clientSocket.close();
//            }
        } else {
            notifyObserver("Change login failed, wrong login");
            server.deleteObserver(this);
            clientSocket.close();
        }

    }

    private void setPassword(String messageFromUser){
        String oldPassword = messageFromUser.split(" ")[1];
        String newPassword = messageFromUser.split(" ")[2];
        dao.setLogin(oldPassword,newPassword);
        notifyObserver("Change password done!");

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
        System.out.println("Rega");
        client = new User(messageFromUser.split(" ")[1],
                messageFromUser.split(" ")[2]);
        dao.createUser(client);
        System.out.println("Rega for " + client.getName() + " success");
        notifyObserver("Registration successfully");
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
