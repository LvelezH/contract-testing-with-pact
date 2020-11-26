package messageconsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.PlayerMessage;

import java.io.IOException;

public class MessageConsumer {

    private final ObjectMapper objectMapper;

    public MessageConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void consumeStringMessage(String messageString) throws IOException {
        PlayerMessage player = objectMapper.readValue(messageString, PlayerMessage.class);

        //We do whatever with the message
    }

}