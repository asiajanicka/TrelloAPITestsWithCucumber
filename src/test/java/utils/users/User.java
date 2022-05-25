package utils.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class User {
    private String apiKey;
    private String token;
    private String userId;
}
