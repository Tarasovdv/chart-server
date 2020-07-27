package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Message {
    private final int id;
    private String text;
    private int idUser;
}
