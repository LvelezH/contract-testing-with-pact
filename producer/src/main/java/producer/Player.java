package producer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player {
    private String name;
    private String surname;
    private String city;
    private int age;
}
