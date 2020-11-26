import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import client.PlayerClient;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import model.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "user-service.base-url:http://localhost:8080",
        classes = PlayerClient.class)
public class RestConsumerContractTest {
     @Rule
     public PactProviderRule mockProvider = new PactProviderRule("Provider", "localhost", 8080, this);

     @Rule
     public ExpectedException expectedException = ExpectedException.none();


     @Autowired
     private PlayerClient playerClient;

     @PactVerification(fragment = "pactPlayerExists")
     @Test
     public void playerExists() {
          Player player = playerClient.getPlayer("1");
          assertEquals(player.getName(), "Pepe");
     }

     @PactVerification(fragment = "pactPlayerNotExists")
     @Test
     public void playerNotExists() {
          expectedException.expect(HttpClientErrorException.class);
          expectedException.expectMessage("404 Not Found");
          playerClient.getPlayer("2");
     }

     @Pact(consumer = "Consumer")
     public RequestResponsePact pactPlayerExists(PactDslWithProvider builder) {
          return builder.given("Player 1 exists")
                  .uponReceiving("A request to /player/1")
                  .path("/player/1")
                  .method("GET")
                  .willRespondWith()
                  .status(200)
                  .body(LambdaDsl.newJsonBody(o -> o
                          .stringType("name", "Pepe")
                          .stringType("surname", "Garcia")
                          .stringType("city", "Madrid")
                          .numberType("age", 22)
                  ).build())
                  .toPact();
     }

     @Pact(consumer = "Consumer")
     public RequestResponsePact pactPlayerNotExists(PactDslWithProvider builder) {
          return builder.given("Player 2 does not exist")
                  .uponReceiving("A request to /player/2")
                  .path("/player/2")
                  .method("GET")
                  .willRespondWith()
                  .status(404)
                  .toPact();
     }
}
