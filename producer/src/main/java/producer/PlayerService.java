package producer;

import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    public Player findPlayer(String id) {
        return Player.builder()
                .name("Paco")
                .surname("Garcia")
                .city("Madrid")
                .age(22)
                .build();
    }
}