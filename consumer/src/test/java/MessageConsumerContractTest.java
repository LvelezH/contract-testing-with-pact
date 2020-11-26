import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit.MessagePactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.MessagePact;
import com.fasterxml.jackson.databind.ObjectMapper;
import messageconsumer.MessageConsumer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = {MessageConsumer.class, ObjectMapper.class})
public class MessageConsumerContractTest {

    @Rule
    public MessagePactProviderRule mockProvider = new MessagePactProviderRule(this);

    private byte[] currentMessage;

    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private final MessageConsumer messageConsumer = new MessageConsumer(mapper);

    @Pact(provider = "playermessageservice", consumer = "playermessageclient")
    public MessagePact playerMessagePact(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();
        body.stringType("messageUuid");
        body.object("player")
                .stringType("name", "Francisco")
                .stringType("surname", "Martinez")
                .stringType("city", "Madrid")
                .integerType("age", 38)
                .closeObject();

        return builder
                .expectsToReceive("a player message")
                .withContent(body)
                .toPact();
    }

    @Test
    @PactVerification("playerMessagePact")
    public void verifyPlayerMessagePact() throws IOException {
        messageConsumer.consumeStringMessage(new String(this.currentMessage));
    }

    public void setMessage(byte[] message) {
        this.currentMessage = message;
    }

}