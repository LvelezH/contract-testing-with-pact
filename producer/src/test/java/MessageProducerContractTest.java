import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactUrl;
import au.com.dius.pact.provider.junit.target.AmqpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import producer.MessageProducer;
import producer.MessagePublisher;
import producer.Player;
import producer.PlayerMessage;

import java.io.IOException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PactRunner.class)
@Provider("playermessageservice")
@PactUrl(urls = "src/test/pacts/playermessageclient-playermessageservice.json")
public class MessageProducerContractTest {

    @TestTarget
    public final Target target = new AmqpTarget();

    private final MessagePublisher publisher =
            Mockito.mock(MessagePublisher.class);

    private final MessageProducer messageProvider =
            new MessageProducer(new ObjectMapper(), publisher);

    @PactVerifyProvider("a player message")
    public String verifyUserCreatedMessage() throws IOException {
        // given
        doNothing()
                .when(publisher)
                .publishMessage(anyString(), eq("player.message"));

        // when
        PlayerMessage message = PlayerMessage.builder()
                .messageUuid(UUID.randomUUID().toString())
                .player(Player.builder()
                        .name("Francisco")
                        .surname("Martinez")
                        .city("Madrid")
                        .age(38)
                        .build())
                .build();

        messageProvider.produceUserCreatedMessage(message);

        // then
        ArgumentCaptor<String> messageCapture =
                ArgumentCaptor.forClass(String.class);

        verify(publisher, times(1))
                .publishMessage(messageCapture.capture(), eq("player.message"));

        return messageCapture.getValue();
    }
}