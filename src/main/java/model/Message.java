package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Message {
//    private  int idMes;
    private String text;
//    private int idUser;
    private User user;

}
