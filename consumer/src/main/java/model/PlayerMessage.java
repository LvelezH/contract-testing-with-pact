package model;

import lombok.Data;

@Data
public class PlayerMessage {

    private String messageUuid;
    private Player player;

}