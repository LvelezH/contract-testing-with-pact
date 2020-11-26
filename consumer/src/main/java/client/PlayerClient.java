package client;

import model.Player;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class PlayerClient {
    private final RestTemplate restTemplate;

    public PlayerClient(@Value("${user-service.base-url}") String baseUrl) {
        this.restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public Player getPlayer(String id) {
        return restTemplate.getForObject("/player/" + id, Player.class);
    }
}
