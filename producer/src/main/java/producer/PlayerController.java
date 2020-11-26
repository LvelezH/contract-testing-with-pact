package producer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PlayerController {
    private final PlayerService service;

    public PlayerController (PlayerService service) {
        this.service = service;
    }

    @GetMapping("/player/{id}")
    public Player getUser(@PathVariable String id) {
        if (id.equals("1")) {
            return service.findPlayer(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }

    }
}