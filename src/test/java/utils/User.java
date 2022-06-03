package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class User {
    private String apiKey;
    private String token;
    private String userId;
    private String email;
}
