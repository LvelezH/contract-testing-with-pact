package producer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerMessage {

    private String messageUuid;
    private Player player;

}