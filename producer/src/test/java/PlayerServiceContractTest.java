import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactUrl;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import producer.PlayerServiceApplication;

@RunWith(SpringRestPactRunner.class)
@Provider("Provider")
@PactUrl(urls = "src/test/pacts/Consumer-Provider.json")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PlayerServiceApplication.class)
public class PlayerServiceContractTest {
    @TestTarget
    public final Target target = new SpringBootHttpTarget();

    @State("Player 1 exists")
    public void player1Exists() {
    }

    @State("Player 2 does not exist")
    public void player2NotExists() {
    }
}