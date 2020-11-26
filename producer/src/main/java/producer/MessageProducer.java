package producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MessageProducer {

    private final ObjectMapper objectMapper;

    private final MessagePublisher messagePublisher;

    public MessageProducer(
            ObjectMapper objectMapper,
            MessagePublisher messagePublisher) {
        this.objectMapper = objectMapper;
        this.messagePublisher = messagePublisher;
    }

    public void produceUserCreatedMessage(PlayerMessage message)
            throws IOException {

        String stringMessage = objectMapper.writeValueAsString(message);

        messagePublisher.publishMessage(stringMessage, "player.message");
    }

}