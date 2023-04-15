package uz.avaz.instagramclone.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {

    String accessToken;
    String accessTokenType;
    Long userId;
    String username;
    String email;
    List<String> roles;
}
